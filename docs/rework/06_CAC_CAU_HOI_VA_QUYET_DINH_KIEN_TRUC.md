# BẢN KHẢO SÁT & QUYẾT ĐỊNH KIẾN TRÚC (ARCHITECTURE DECISION RECORDS - ADR)
## CÁC ĐIỂM RẼ CHIẾN LƯỢC CẦN THẢO LUẬN & THỐNG NHẤT
**Dự án:** NSO Game Server Modernization  
**Trạng thái tài liệu:** Đang mở thảo luận (Open for Discussion)  
**Mục tiêu:** Lưu trữ các bài toán thiết kế then chốt, phân tích sâu ưu/nhược điểm từng phương án để tác giả thảo luận và lựa chọn giải pháp tối ưu nhất cho hệ thống V2.

---

## VẤN ĐỀ 1: CHIẾN LƯỢC ĐỒNG BỘ DỮ LIỆU XUỐNG DATABASE (PERSISTENCE & FLUSH STRATEGY)

### 1.1. Bối cảnh & Thách thức
Dữ liệu nhân vật trong game biến động liên tục theo từng giây (máu tụt, exp tăng, nhặt rác, dịch chuyển tọa độ). 
Nếu mỗi hành động đều chạy một câu lệnh SQL (`UPDATE`) trực tiếp vào MariaDB:
* Server sẽ bị nghẽn I/O đĩa nghiêm trọng khi có đông người chơi.
* Tăng độ trễ (latency) của game loop.

### 1.2. Các Phương Án Thảo Luận

#### Phương án 1A: Dirty-Tracking + Periodic Flush + Critical Triggers (Chuẩn MMORPG truyền thống)
* **Cơ chế hoạt động:**
  * Toàn bộ dữ liệu nhân vật sống và biến đổi 100% trên bộ nhớ RAM để đạt tốc độ mili-giây.
  * Mỗi khi có trường dữ liệu thay đổi, chỉ bật cờ nội bộ `isDirty = true`.
  * **3 Thời điểm kích hoạt ghi DB (Flush):**
    1. *Định kỳ:* Một luồng nền quét và ghi các bản ghi `isDirty` mỗi 5 phút/lần.
    2. *Chuyển vùng / Thoát:* Khi nhân vật chuyển map hoặc đăng xuất khỏi game.
    3. *Sự kiện Trọng yếu (Critical Triggers):* Khi xảy ra giao dịch tài sản quan trọng (Giao dịch P2P, mua đồ kỳ trân các bằng Lượng, đập đồ thành công) $\to$ Flush ngay lập tức trong một Database Transaction.
* **Ưu điểm:** Tối ưu hiệu năng CPU/Disk tối đa; cực kỳ nhẹ và thanh thoát.
* **Rủi ro:** Nếu máy chủ bị mất điện hoặc crash process đột ngột, người chơi có thể bị lùi (rollback) tối đa vài phút tiến độ chưa kịp lưu.

#### Phương án 1B: Write-Behind Async Queue (Hàng đợi ghi bất đồng bộ)
* **Cơ chế hoạt động:**
  * Mọi thay đổi dữ liệu đều đóng gói thành `DataMutationTask` và đẩy vào một hàng đợi an toàn luồng (`BlockingQueue`).
  * Một nhóm Worker Threads chạy ngầm liên tục rút các task từ hàng đợi ra để thực thi SQL xuống MariaDB.
* **Ưu điểm:** Dữ liệu được ghi xuống đĩa gần như theo thời gian thực (realtime), giảm thiểu tối đa rủi ro rollback khi sập server.
* **Rủi ro:** 
  * Nếu tốc độ ghi DB chậm hơn tốc độ sinh task (ví dụ đĩa HDD chậm), hàng đợi sẽ phình to gây tràn RAM (OutOfMemory).
  * Khó kiểm soát thứ tự xung đột dữ liệu (Race conditions) giữa các worker ghi đè dữ liệu của cùng một nhân vật.

> **Trạng thái:** `[ĐÃ CHỐT - ACCEPTED]`  
> **Giải pháp Thống nhất:** **Adaptive Dual-Trigger Hybrid Persistence (Mô hình Lưu trữ Lai Thích ứng Song Điều kiện)**
> 
> **1. Phân tầng Dữ liệu (Risk-based Tiering):**
> * *Tầng Rủi ro Cao (Tài sản / Giao dịch):* Mua đồ shop Lượng, Giao dịch P2P, Cường hóa +16 $\to$ **Transactional Immediate Flush** (Ghi đồng bộ tức thì xuống MariaDB trong DB Transaction, tuyệt đối chống dup/mất đồ).
> * *Tầng Biến động Cao / Rủi ro Thấp:* Máu (HP), Mana (MP), Tọa độ X/Y, EXP, Tiến độ nhiệm vụ đếm quái $\to$ **Dirty-Tracking trên RAM** (Tốc độ mili-giây).
> 
> **2. Cơ chế Xả Thích ứng (The 3 Flush Triggers):**
> * **Trigger 1 (Ngưỡng Watermark - Ý của Tác giả):** Kích hoạt xả ngầm một Batch khi số lượng bản ghi dirty tích lũy $\ge \text{DIRTY\_WATERMARK}$ (Ví dụ: 200 bản ghi). Giúp làm phẳng áp lực I/O đĩa (I/O Smoothing), giữ RAM luôn an toàn cách xa mức trần.
> * **Trigger 2 (Khóa Trần Thời gian - Time Safety Cap):** Dành cho lúc thấp điểm ít người, nếu quá $\text{MAX\_FLUSH\_INTERVAL}$ (Ví dụ: 5 phút) mà chưa chạm ngưỡng số lượng, hệ thống vẫn kích hoạt xả ngầm những gì đang có để khóa trần rủi ro rollback tối đa là 5 phút.
> * **Trigger 3 (Vòng đời Nhân vật):** Tự động flush khi nhân vật chuyển map, rời khu, hoặc đăng xuất.
> 
> **3. Chiến lược Quản lý Tham số Thực nghiệm (Empirical Tuning):**
> * Không hardcode "số ma" trong code. Các tham số: `flush.watermark.dirty-count`, `flush.max-interval-seconds`, `flush.batch-size` được đưa ra file cấu hình (`config.properties`).
> * Các tham số này sẽ được cân chỉnh và tinh chỉnh tối ưu (sweet spot) thông qua các bài đo tải (benchmark/load test) thực tế trên máy chủ.
> 
> **4. Đánh đổi được chấp nhận (Accepted Trade-offs):**
> * Chấp nhận tốn thêm ~2-3% chu kỳ CPU cho việc theo dõi biến đếm và sao chép snapshot ngắn hạn để đổi lấy việc loại bỏ 100% nguy cơ nghẽn đĩa và triệt tiêu lỗi tranh chấp luồng (Race conditions) của mô hình Queue.

---

## VẤN ĐỀ 2: MÔ HÌNH ĐA LUỒNG THẾ GIỚI GAME (GAME LOOP & CONCURRENCY)

### 2.1. Bối cảnh & Thách thức
Mã nguồn cũ chạy hàng chục luồng không quản lý và dùng `Thread.sleep` bừa bãi trong thread pool dẫn đến hiện tượng nghẽn luồng (Thread Starvation). Server V2 cần một mô hình luồng chuẩn mực để xử lý hàng ngàn quái vật và người chơi song song.

### 2.2. Các Phương Án Thảo Luận

#### Phương án 2A: Zone-Isolated Concurrency (Mô hình Phân luồng theo Khu vực / Actor Model)
* **Cơ chế hoạt động:**
  * Mỗi Zone bản đồ (Khu 0, Khu 1, Khu 2...) là một đơn vị xử lý không gian độc lập, được điều phối bởi một tác vụ nền (`ScheduledTask`).
  * Tất cả hành vi của quái vật và người chơi bên trong Zone A sẽ được xử lý tuần tự nội bộ trong luồng của Zone A.
  * Hoạt động ở Zone A hoàn toàn không khóa (lock) hay ảnh hưởng tới hiệu năng của Zone B.
* **Ưu điểm:**
  * Tận dụng tối đa sức mạnh của CPU đa nhân (Multi-core).
  * Cách ly rủi ro: Nếu một Zone bị lag hoặc lỗi exception, các Zone khác vẫn hoạt động mượt mà.
  * Điểm cộng cực lớn khi đưa vào Portfolio (chứng minh tư duy Concurrency hiện đại).
* **Rủi ro:** Cần xử lý khéo léo bài toán chuyển giao (handoff) khi nhân vật bước qua cổng dịch chuyển từ Zone này sang Zone khác.

#### Phương án 2B: Single Master Game Loop (Vòng lặp Chủ Duy Nhất)
* **Cơ chế hoạt động:**
  * Một luồng duy nhất chạy vòng lặp vĩnh cửu theo nhịp cố định (ví dụ 50ms hoặc 100ms một tick).
  * Trong mỗi tick, luồng này duyệt qua toàn bộ danh sách map, quái vật và người chơi để cập nhật trạng thái.
* **Ưu điểm:** Cực kỳ đơn giản, không bao giờ lo lắng về Deadlock hay tranh chấp luồng (Race conditions).
* **Rủi ro:** Khi lượng người chơi và quái vật tăng cao, thời gian xử lý một tick có thể vượt quá 100ms $\to$ Gây hiện tượng lag giật tập thể cho toàn bộ thế giới game.

> **Trạng thái:** `[ĐÃ CHỐT - ACCEPTED]`  
> **Giải pháp Thống nhất:** **Phương án 2A — Zone-Isolated Concurrency (Mô hình Phân luồng theo Khu vực / Actor Model)**
> 
> **1. Rationale (Lý do chọn):**
> * Tận dụng tối đa kiến trúc CPU đa nhân hiện đại.
> * Cách ly hoàn toàn rủi ro lag: Một Zone đông người (ví dụ Làng Tone lúc sự kiện) bị nghẽn tải thì các Zone khác (rừng rú, hang động) vẫn chạy mượt mà 60 FPS, không kéo lag cả server.
> * Điểm nhấn kiến trúc lớn cho Portfolio (thể hiện kỹ năng Concurrency cao cấp).
> 
> **2. Quan điểm Thực chiến & Trade-off được chấp nhận (Ý của Tác giả):**
> * Sự thiên lệch chỉ số khi chuyển map (ví dụ: vừa qua cổng dịch chuyển thì nhận 1 đòn sát thương từ map cũ) gắn liền với các chỉ số **ưu tiên chính xác không cao** (HP/MP, giống như nguyên tắc phân tầng ở Vấn đề 1).
> * **Chấp nhận trade-off này ở giai đoạn đầu** để giữ code chuyển giao đơn giản, thanh thoát, tránh tối ưu hóa sớm (Premature Optimization) gây phức tạp luồng.
> * Các biện pháp tinh chỉnh triệt để (như tạo khung bất tử 0.5s - 1s khi vừa load map mới / i-frames) sẽ được đưa vào kế hoạch hoàn thiện sau.

---

## VẤN ĐỀ 3: CẤU TRÚC LƯU TRỮ OPTION TRANG BỊ TRONG DATABASE (`character_items`)

### 3.1. Bối cảnh & Thách thức
Mỗi món trang bị trong NSO có từ 0 đến 10 dòng chỉ số thuộc tính ngẫu nhiên (Ví dụ: `+50 Ngoại công`, `+10 Né tránh`, `+5% Max HP`...). Cần thiết kế cách lưu trữ mảng option này vào bảng `character_items`.

### 3.2. Các Phương Án Thảo Luận

#### Phương án 3A: Mô hình Lai (Hybrid Relational + JSON Options)
* **Cơ chế hoạt động:**
  * Các thông tin định danh và số lượng được lưu thành các cột SQL chuẩn để lập Index và truy vấn: `character_id`, `template_id`, `location` (BAG/BOX/EQUIP), `slot_index`, `upgrade_level`, `expires_at`.
  * Riêng danh sách chỉ số phụ được lưu vào một cột JSON duy nhất:
    `options_json = '[{"id":0,"p":50},{"id":10,"p":10}]'`
* **Ưu điểm:**
  * Đọc/ghi toàn bộ túi đồ (30 món) chỉ bằng **1 câu lệnh SQL duy nhất** (`SELECT * FROM character_items WHERE character_id = ?`).
  * Tránh việc phải JOIN bảng phức tạp.
* **Nhược điểm:** Không thể dùng câu lệnh SQL thuần túy để tìm kiếm người chơi sở hữu dòng option cụ thể (phải dùng hàm JSON của MariaDB nếu cần tra cứu).

#### Phương án 3B: Chuẩn hóa Quan hệ 100% (Pure Normalized - 3NF)
* **Cơ chế hoạt động:**
  * Tách riêng một bảng con: `character_item_options (`item_id`, `option_id`, `param`)`.
  * Mỗi dòng option của trang bị là một record độc lập trong database.
* **Ưu điểm:** Chuẩn mực 100% về mặt lý thuyết cơ sở dữ liệu quan hệ (RDBMS). Dễ dàng chạy query tìm kiếm xem trên toàn server có ai sở hữu option ID = 113 hay không.
* **Nhược điểm:** 
  * Chi phí I/O lớn: Một nhân vật mang 50 món đồ, mỗi món 5 option $\to$ Khi đăng nhập phải load và map **250 dòng dữ liệu** từ 2 bảng khác nhau.

#### Phương án 3C: Rời Rạc Hóa 5 Phẩm Chất (Discrete 5-Tier Quantization) + Tuple Mở Rộng `{a; x; t; ...}` (Đột phá V2)
* **Cơ chế hoạt động:**
  * Thay vì random điểm liên tục đòi hỏi phải lưu JSON cho từng item, rời rạc hóa chỉ số thành **5 Tier cố định ($A1 \to A5$)** thông qua thuật toán nội suy tuyến tính dàn đều 4 khoảng từ khoảng $[min, max]$ có sẵn trong DB gốc.
  * Server tính toán và cache sẵn 5 bộ option trong RAM khi khởi động (Bootstrap $O(1)$).
  * Trang bị được biểu diễn dưới dạng tuple mở rộng: `{a; x; t; ...}`:
    * `a` (*template_id*): INT
    * `x` (*upgrade_level*): TINYINT (+0 đến +16)
    * `t` (*tier*): TINYINT (1 đến 5)
    * `flags`: INT (Bitmask: Khóa, Giám định, Sự kiện...)
    * `extra_data`: JSON NULL (Sparse Payload - chỉ dùng khi khảm ngọc hoặc rèn đồ đặc biệt).
* **Ưu điểm:** 
  * Xóa bỏ 100% JSON rác cho 90% trang bị thông thường, bản ghi chỉ tốn ~25 bytes số nguyên.
  * Tốc độ nạp dữ liệu đạt đỉnh cao ($O(1)$ pointer lookup trong RAM).
  * Vẫn bảo tồn hoàn hảo cảm giác "săn đồ max opt" (săn đồ A5 Hoàn Mỹ) và thị trường buôn bán phân cấp.
* **Chi tiết đặc tả:** Xem riêng tại [`docs/rework/08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md`](08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md).

> **Trạng thái:** `[ĐÃ CHỐT - ACCEPTED]`  
> **Giải pháp Thống nhất:** **Phương án 3C — Rời Rạc Hóa 5 Phẩm Chất (Discrete 5-Tier Quantization) + Tuple Mở Rộng `{a; x; t; ...}`**
> 
> **1. Rationale (Lý do chọn):**
> * Vượt trội hơn hoàn toàn so với mô hình JSON thuần túy (3A) và mô hình bảng con (3B): Triệt tiêu hoàn toàn serialization rác cho trang bị đại trà.
> * Database chỉ lưu các trường số nguyên nguyên thủy (`INT`, `TINYINT`), dung lượng giảm 6 lần, đọc ghi siêu tốc.
> * Các tính năng nâng cao (khảm ngọc, tên thợ rèn) được đặt trong tầng Sparse Payload (`extra_data JSON NULL`), giữ hệ thống vừa siêu nhẹ vừa mở rộng vô hạn.
> 
> **2. Đề xuất Tối ưu của Tác giả: "Giảm Ưu Tiên Truy Vấn (Query Deprioritization)"**
> * **Tải bất đồng bộ khi Đăng nhập:** Thông tin cốt lõi nhân vật (Tên, Cấp, Tọa độ) được nạp trước để người chơi vào ngay thế giới game (`CMD -107`). Việc load chi tiết túi đồ diễn ra song song, không làm nghẽn tiến trình mạng.
> * **Không ghi đĩa trực tiếp khi biến đổi option:** Khi cường hóa, đập đồ hay luyện ngọc thay đổi chỉ số, toàn bộ cập nhật diễn ra trên RAM trước và đánh dấu dirty. Việc ghi xuống MariaDB do bộ điều tiết thích ứng (Adaptive Flush ở Vấn đề 1) đảm nhiệm ở luồng ngầm khi rảnh rỗi.
> * **Hóa giải Tử huyệt "Sắp xếp túi đồ":** Khi người chơi sắp xếp lại túi (30 món đổi vị trí), việc hoán đổi slot diễn ra **100% trên RAM**, tuyệt đối không chạy 30 câu lệnh `UPDATE` lẻ gây Deadlock đĩa. Chỉ đánh dấu cờ `isDirty` cho túi đồ và để luồng xả ngầm ghi gộp bằng 1 Batch Update khi máy chủ rảnh.
> * **Truy vấn Admin / Kiểm tra đồ:** Các câu lệnh tìm kiếm đồ phục vụ quản trị (nếu có) được phân luồng ưu tiên thấp (Low-priority background task), sử dụng cơ chế `READ UNCOMMITTED` để tuyệt đối không lock bảng của người chơi đang online.

---

## VẤN ĐỀ 4: PHẠM VI BẢN KIỂM CHỨNG ĐẦU TIÊN (MILESTONE 1 - VERTICAL SLICE)

### 4.1. Bối cảnh & Mục tiêu Portfolio
Để dự án nhanh chóng có một bản demo thực chiến "chạy được, nhìn thấy được và kiểm chứng được" nhằm đưa vào Portfolio mà không bị ngập trong hàng tá tính năng phụ, chúng ta cần khoanh vùng phạm vi cho **Milestone 1 (Bản Alpha)**.

### 4.2. Đề xuất Phân kỳ

#### Nhóm Tính năng BẮT BUỘC cho Milestone 1 (Bản Alpha kiểm chứng):
1. **Network & Auth:** Bắt tay mã hóa socket (`CMD -27`), Fetch Metadata (`CMD -122, -121`), Đăng nhập tài khoản & Hiển thị danh sách nhân vật (`CMD -127, 2`).
2. **Spatial & World:** Chọn nhân vật vào map Làng Tone (`CMD -107 Me Load All`), nhân vật di chuyển mượt mà, va chạm địa hình TileMap chuẩn xác.
3. **Multiplayer Visibility:** Hai client kết nối cùng lúc nhìn thấy nhau di chuyển và chat kênh Làng được với nhau.
4. **Inventory & Equipment:** Mở túi đồ, nhấp mặc một món vũ khí/trang bị $\to$ Hình thể nhân vật đổi sprite, điểm thuộc tính tăng ngay lập tức.
5. **Combat Core:** Đánh 1 con Ốc sên bằng chiêu thức cơ bản $\to$ Bộ máy `NewDamageEngine` tính sát thương, hiện số sát thương nổ trên đầu quái, quái chết rơi vật phẩm nhặt được vào túi.

#### Nhóm Tính năng DỜI SANG Milestone 2 (Mở rộng):
* Hệ thống Cường hóa đập đồ Kenshiko (+1 -> +16).
* Hệ thống Bang hội / Gia tộc chiến / Lãnh địa.
* Hệ thống Giao dịch P2P, Chợ sạp (Stall), Đấu giá Shinwa.
* Hệ thống Thú cưỡi, Vĩ thú và Phân thân.
* Hệ thống Hang động phó bản 3x-9x và Nhiệm vụ cốt truyện.

> **Trạng thái:** `[ĐÃ CHỐT - ACCEPTED]`  
> **Giải pháp Thống nhất:** **Chiến lược Cục bộ 4 Milestone & Phiên bản hóa theo Chuẩn Semantic Versioning**
> 
> **1. Rationale (Lý do chọn):**
> * Vì đây là dự án **Tái hiện / Tái cấu trúc Di sản (Legacy Rework)** chứ không phải Newbuild mù mờ, toàn bộ khế ước client và dữ liệu đã biết trước 100%. Do đó, việc ước lượng phiên bản và phân kỳ từng Milestone độc lập là hoàn toàn khả thi và chặt chẽ.
> * Mỗi Milestone là một phiên bản game thu nhỏ (Mini-NSO) tự đứng vững, có thể biên dịch, chạy độc lập và quay demo ngay lập tức.
> 
> **2. Lộ trình Phân kỳ 4 Cột mốc Chính thức:**
> * **`v0.1.0-alpha` (Milestone 1):** Lõi Động cơ & Vòng lặp Sơ khai (Netty -27, Login, Làng Tone di chuyển/chat, mặc kiếm, đánh ốc sên). Kèm 2 lệnh GM chat `/item` và `/level`.
> * **`v0.2.0-beta` (Milestone 2):** Kinh tế, Cây kỹ năng 6 phái & Cường hóa Kenshiko +16, Giao dịch P2P, Sắp xếp túi đồ trên RAM.
> * **`v0.3.0-rc` (Milestone 3):** Quái vật toàn server (Zone Hibernation), Nhiệm vụ cốt truyện 50 cấp (Observer), Tổ đội & Gia tộc.
> * **`v0.4.0` (Milestone 4):** Phân thân, Vĩ thú, Thú cưỡi, Hang động 3x-9x, ETL Data Migration $\to$ Sẵn sàng đạt chuẩn **`v1.0.0` Production Ready**.
> 
> *(Chi tiết đặc tả và tiêu chuẩn hoàn thành DoD xem tại tài liệu `07_CHIEN_LUOC_MILESTONE_VA_UOC_LUONG_PHIEN_BAN.md`).*

