# NSO GAME SERVER — BỘ TÀI LIỆU TÁI CẤU TRÚC TOÀN DIỆN (REWORK V2)

Thư mục này chứa toàn bộ tài liệu đặc tả kiến trúc, bản đồ nghiệp vụ, thiết kế cơ sở dữ liệu, quy chuẩn giao thức kết nối và đặc tả di sản phục vụ cho dự án Rework từ Backend tới Database.

---

## TIÊU CHUẨN THIẾT KẾ CỦA TÁC GIẢ (AUTHOR'S DESIGN PRINCIPLES)

Dự án được định hướng như một **Showpiece Portfolio** chất lượng cao, tuân thủ 4 nguyên tắc kỹ thuật cốt lõi:

1. **"Dùng gì gọi nấy — Hỏi gì trả đúng cái nấy":**
   * Triệt tiêu hoàn toàn tác dụng phụ ngầm (No Hidden Side-effects): Hàm tính toán không tự ý ghi DB hay bắn socket.
   * Dữ liệu tường minh (No Over-fetching): Không tải cả cụm JSON khổng lồ chỉ để đọc 1 chỉ số.
   * Giao tiếp giữa các tầng bằng Java 17 `record` rõ ràng, minh bạch.
2. **"Gom nhóm theo vai trò — Chống phân mảnh cực đoan":**
   * Không rơi vào cái bẫy Over-engineering (chia nhỏ mỗi hành động một file li ti gây bùng nổ class và áp lực GC).
   * Gom các thành phần có chung ngữ cảnh và tập dữ liệu thành **5 Tập Hợp Vai Trò Cốt Lõi (5 Cohesive Sets)**: *Spatial, Character State, Inventory, Combat, Social*.
   * Tầng Mạng tinh giản thành **5 Packet Controller Routers** tương ứng thay vì 135 file lẻ.
3. **Bảo tồn "Di sản" (Game DNA) của Ninja School Online:**
   * Không sa đà micro-manage ID nhỏ lẻ hay công thức vi mô (để tác giả tự do refactor/cân bằng).
   * Bảo tồn trọn vẹn 5 Cột trụ Bản sắc (Tam trường lục phái, Kenshiko +16 hào quang, Ghép mảnh Avatar Part, Phân thân & Vĩ thú, Map tuổi thơ).
4. **Kế thừa thực dụng (Pragmatic Inheritance):**
   * Kế thừa 100% Quái vật & Tọa độ Spawn kết hợp kỹ thuật **Zone Hibernation** (ngủ đông khu vực vắng, giảm 90% CPU).
   * Kế thừa 100% Nhiệm vụ 50 cấp cốt truyện kết hợp **Event-Driven Observer Pattern** nhẹ nhàng.

---

## MỤC LỤC TÀI LIỆU KIẾN TRÚC

| STT | Tên Tài Liệu | Nội Dung Chính |
| :---: | :--- | :--- |
| **00** | [**00_MASTER_PLAN_REWORK_VA_KIEN_TRUC.md**](./00_MASTER_PLAN_REWORK_VA_KIEN_TRUC.md) | **Tổng quan Kế hoạch & Nguyên tắc 80/20**<br>• Phân loại: Cái gì giữ nguyên 100%, cái gì lấy công thức, cái gì vứt bỏ.<br>• Mô hình 4 tầng phân lập (Clean Architecture).<br>• Lộ trình 4 giai đoạn thực hiện song song (Dual-Stack Sandbox). |
| **01** | [**01_BAN_DO_HE_SINH_THAI_GAME_NAY_CO_GI.md**](./01_BAN_DO_HE_SINH_THAI_GAME_NAY_CO_GI.md) | **"Game Này Có Gì?" (Game Domain Landscape)**<br>• Bóc tách 8 trụ cột hệ thống: Thế giới, Nhân vật 6 phái, 10 ô trang bị & 162 Option, Combat PvE/PvP, Gia tộc & Tổ đội, Nhiệm vụ, Sự kiện mùa, Hạ tầng kỹ thuật. |
| **02** | [**02_THIET_KE_DATABASE_V2_TINH_GON.md**](./02_THIET_KE_DATABASE_V2_TINH_GON.md) | **Thiết kế Database V2 Tinh Gọn (~18 Bảng)**<br>• Danh sách hơn 40 bảng rác loại bỏ (thẻ cào, momo, cms, laravel).<br>• Xóa bỏ anti-pattern "nhồi JSON", chuẩn hóa `character_items`.<br>• Quy chuẩn UTF8MB4 toàn diện & Chiến lược ETL Migration dữ liệu cũ. |
| **03** | [**03_QUY_CHUAN_GIAO_THUC_CLIENT_ADAPTER.md**](./03_QUY_CHUAN_GIAO_THUC_CLIENT_ADAPTER.md) | **Quy chuẩn Giao thức Client & Adapter Pattern**<br>• Giữ nguyên 100% định dạng byte socket với Game Client (Hộp đen).<br>• Vòng đời Client Fetching (CMD -27, -122, -121, -127, -107).<br>• Triệt tiêu switch-case 135 nhánh bằng PacketHandler Registry. |
| **04** | [**04_DAC_TA_DI_SAN_VA_KE_THUA.md**](./04_DAC_TA_DI_SAN_VA_KE_THUA.md) | **Đặc tả Di Sản & Danh mục Kế Thừa Thông Minh**<br>• 5 Cột trụ Bản sắc / DNA của NSO (Tam trường lục phái, Kenshiko +16 hào quang, Ghép mảnh avatar, Phân thân & Vĩ thú, Không gian tuổi thơ).<br>• Kế thừa Quái vật & Tọa độ Spawn kết hợp kỹ thuật **Zone Hibernation** (tiết kiệm 90% CPU).<br>• Kế thừa Nhiệm vụ 50 cấp cốt truyện kết hợp **Event-Driven Observer** nhẹ nhàng. |
| **05** | [**05_DAC_TA_CHI_TIET_5_TAP_HOP_VAI_TRO.md**](./05_DAC_TA_CHI_TIET_5_TAP_HOP_VAI_TRO.md) | **Đặc tả Chi Tiết 5 Tập Hợp Vai Trò (Cohesive Sets)**<br>• Phân rã thực thể `PlayerCharacter` thành 5 Component chuyên trách.<br>• Ranh giới dữ liệu, trách nhiệm duy nhất (SRP) và các phương thức cốt lõi.<br>• Mô hình Domain Event Bus chống phụ thuộc chéo (Circular Dependency). |
| **06** | [**06_CAC_CAU_HOI_VA_QUYET_DINH_KIEN_TRUC.md**](./06_CAC_CAU_HOI_VA_QUYET_DINH_KIEN_TRUC.md) | **Bản Khảo Sát & Quyết Định Kiến Trúc (ADR / RFC)**<br>• **Vấn đề 1:** Chiến lược lưu dữ liệu xuống DB (Dirty-Tracking vs Write-Behind Queue).<br>• **Vấn đề 2:** Mô hình đa luồng thế giới game (Zone-Isolated Actor vs Master Loop).<br>• **Vấn đề 3:** Cấu trúc lưu trữ options trang bị (Hybrid JSON vs Pure 3NF).<br>• **Vấn đề 4:** Phạm vi bản Alpha kiểm chứng đầu tiên (Milestone 1 Scope). |
| **07** | [**07_CHIEN_LUOC_MILESTONE_VA_UOC_LUONG_PHIEN_BAN.md**](./07_CHIEN_LUOC_MILESTONE_VA_UOC_LUONG_PHIEN_BAN.md) | **Chiến Lược Cục Bộ Milestone & Ước Lượng Phiên Bản**<br>• Chuẩn Semantic Versioning cho dự án Rework Di sản: `v0.1.0-alpha` $\to$ `v1.0.0`.<br>• Phân định 4 Cột mốc độc lập có thể chạy được (Playable Releases).<br>• Định lượng phạm vi, thời gian ước lượng và Tiêu chuẩn Nghiệm thu (DoD). |
| **08** | [**08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md**](./08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md) | **Thiết Kế Hệ Thống Trang Bị 5 Tier & Nội Suy Rời Rạc**<br>• Rời rạc hóa chỉ số ngẫu nhiên (Discrete Quantization) thành 5 phẩm chất A1 $\to$ A5.<br>• Bộ trạng thái mở rộng `{a; x; t; ...}` (Core Primitives + Sparse Extensible Payload).<br>• Thuật toán nội suy tuyến tính (Linear Interpolation) tận dụng `[min, max]` DB gốc.<br>• Xóa sạch 100% JSON rác, Flyweight RAM Cache $O(1)$ zero allocation. |
| **09** | [**09_THIET_KE_ENGINE_CHI_SO_VA_CAN_BANG_COMBAT.md**](./09_THIET_KE_ENGINE_CHI_SO_VA_CAN_BANG_COMBAT.md) | **Thiết Kế Engine Chỉ Số & Cân Bằng Combat**<br>• Đường ống tính toán chỉ số 3 tầng (3-Layer Stat Pipeline) xuất ra Immutable Record.<br>• Đặc tả 4 nhóm thuộc tính Bảng Thông Tin Nhân Vật khớp 100% Client J2ME (CMD 93, -127).<br>• Chiến lược Stat Squish, hàm sinh HP quái kiểm soát đạo hàm $f'(x) > 0$.<br>• Lớp lọc thiên lệch trình độ ($\Delta L$) kết hợp Zero-Branch Lookup Table $O(1)$ trong L1 Cache. |
| **10** | [**10_GIAI_THUAT_XAC_SUAT_SINGLE_ROLL_MULTI_VARIABLE.md**](./10_GIAI_THUAT_XAC_SUAT_SINGLE_ROLL_MULTI_VARIABLE.md) | **Giải Thuật Xác Suất Đơn Lần Tung Đầu Ra Đa Biến**<br>• Bóc trần và khắc phục 3 tử huyệt Spaghetti If-Else của `Char.java` bản cũ.<br>• Mô hình trục xác suất tích lũy Single-Roll Table (Né / Chí Mạng / Đánh thường).<br>• Kỹ thuật giải quyết hiệu ứng phái trực giao (Orthogonal Multi-Variable) & Cắt đôi Entropy.<br>• Tối ưu hóa mức phần cứng vi xử lý (Zero Branch Misprediction, ThreadLocalRandom). |

---

> *Tài liệu được lưu trữ cố định tại thư mục: `docs/rework/`*
