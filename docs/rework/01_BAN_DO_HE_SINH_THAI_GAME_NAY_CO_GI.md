# BẢN ĐỒ HỆ SINH THÁI & DANH MỤC TÍNH NĂNG TOÀN DIỆN
## "GAME NÀY CÓ GÌ?" (GAME DOMAIN LANDSCAPE & FEATURE CATALOG)
**Dự án:** NSO Ninja School Online Server Rework  
**Mục đích:** Định danh toàn bộ các phân hệ, tính năng, quy tắc ngầm và mối quan hệ giữa các thành phần trong game nhằm phục vụ việc tái cấu trúc không bỏ sót nghiệp vụ.

---

## 1. TỔNG QUAN 8 TRỤ CỘT HỆ THỐNG (8 CORE PILLARS)

Ninja School Online là một tựa game MMORPG 2D màn hình ngang truyền thống trên nền tảng J2ME/Android. Hệ thống xoay quanh 8 trụ cột chức năng chính:

```
[HỆ SINH THÁI NSO]
 ├── 1. Thế giới & Không gian (World & Spatial System)
 ├── 2. Nhân vật & Môn phái (Character & Progression)
 ├── 3. Trang bị & Kinh tế (Equipment, Crafting & Economy)
 ├── 4. Chiến đấu & Hoạt động (Combat, PvE & PvP Loops)
 ├── 5. Xã hội & Bang hội (Social, Party & Clan)
 ├── 6. Nhiệm vụ & Vòng lặp hàng ngày (Quests & Daily Retention)
 ├── 7. Sự kiện mùa & LiveOps (Seasonal Events & Mini-games)
 └── 8. Vận hành & Hạ tầng kỹ thuật (Network, Admin & Security)
```

---

## 2. BÓC TÁCH CHI TIẾT TỪNG TRỤ CỘT

### TRỤ CỘT 1: THẾ GIỚI & KHÔNG GIAN (WORLD & SPATIAL)

#### 1.1. Hệ thống Bản đồ tĩnh (Static Maps)
- **Khu an toàn (Peace Zones):**
  - 7 Làng: Làng Tone (khởi đầu), Làng Kojin, Làng Chài, Làng Sanzu, Làng Chakumi, Làng Oshin, Làng Echigo.
  - 3 Trường học: Trường Hirosaki, Trường Haruna, Trường Ookaza.
  - *Đặc điểm:* Không có quái vật, cấm PK/chiến đấu, hồi phục HP/MP tự nhiên, tập trung NPC tiện ích.
- **Vùng Luyện công & Rừng núi (Wilderness & Training Maps):**
  - Phân cấp theo dải level người chơi (1-10, 10-20, 20-30... lên đến 130).
  - Địa hình đồi núi, sơn động, rừng rậm, vách đá phủ tuyết.
- **Khu vực Đặc biệt & Phó bản (Special Maps):**
  - Chiến trường Lãnh Địa Gia Tộc, Hang Động 3x-9x, Đấu trường Lôi Đài, Địa Đạo (Aka Cave, Kugyou Cave).

#### 1.2. Cơ chế Zone (Phân luồng & Cân bằng tải người chơi)
- Mỗi Map được chia thành $N$ khu vực độc lập (thường là Khu 0 đến Khu 29).
- **Cơ chế Spawn:** Quái vật và vật phẩm rơi được quản lý riêng biệt theo từng Zone.
- **Cơ chế Chuyển khu (Switch Zone):** Cho phép người chơi tự chọn khu vắng để farm hoặc tập trung vào khu đông để giao lưu/PK.
- **Broadcast phạm vi nhìn:** Khi một nhân vật di chuyển, đánh quái hoặc chat, gói tin chỉ được gửi tới những người chơi có mặt trong cùng Zone đó.

#### 1.3. Lưới va chạm TileMap (Collision Grid)
- Đọc dữ liệu từ file nhị phân trong `Data/Map/`.
- Xác định trạng thái của từng pixel/tile: mặt đất cứng (đứng được), không khí (rơi tự do), mặt nước, dây leo/thang (leo trèo được).
- Chống hack tọa độ: Server tự động kiểm tra khoảng cách di chuyển giữa 2 gói tin tọa độ của client.

---

### TRỤ CỘT 2: NHÂN VẬT & MÔN PHÁI (CHARACTER & PROGRESSION)

#### 2.1. Bản sắc 3 Trường & 6 Môn phái
- **Trường Hirosaki (Hệ Hỏa):**
  - *Kiếm:* Cận chiến, sinh lực cao, phòng ngự vững chắc, có chiêu áp sát.
  - *Phi tiêu:* Tấn công tầm xa, đơn mục tiêu, tốc độ xuất chiêu cao, sát thương bạo kích lớn.
- **Trường Haruna (Hệ Phong):**
  - *Quạt:* Tấn công phép thuật, sở hữu bộ chiêu buff giáp, hồi máu, giải trừ hiệu ứng cho bản thân và đồng đội.
  - *Cung:* Tấn công tầm xa, gây hiệu ứng làm chậm, đóng băng, khống chế diện rộng.
- **Trường Ookaza (Hệ Băng):**
  - *Đao:* Cận chiến, sát thương diện rộng cực mạnh, đòn đánh uy lực dồn ép đối thủ.
  - *Kunai:* Tấn công tầm trung, biến ảo, có chiêu phân thân/tàng hình, gây choáng và sốc sát thương.

#### 2.2. Điểm Tiềm năng & Thống kê gốc (Base Stats)
Nhân vật khi lên cấp nhận được Điểm Tiềm Năng để phân bổ vào 4 chỉ số:
1. **Sức mạnh (Strength):** Tăng công vật lý và sát thương cơ bản.
2. **Thân pháp (Agility):** Tăng điểm Né tránh (Miss), Chính xác (Exactly) và Tốc độ di chuyển.
3. **Thể lực (Vitality):** Tăng Máu tối đa (Max HP) và phòng ngự cơ bản.
4. **Tinh thần (Spirit):** Tăng Mana tối đa (Max MP) và công phép thuật.

#### 2.3. Cây Kỹ năng (Skill Trees)
- Kỹ năng chia theo các mốc cấp độ: Cấp 1, 10, 15, 20, 25, 30, 35, 40, 50, 60, 70, 80, 100, 120.
- **Phân loại kỹ năng:**
  - *Chủ động (Active):* Đòn đánh thường, đòn đánh lan diện rộng, đòn khống chế (choáng, bỏng, đóng băng).
  - *Bị động (Passive):* Tăng % máu, tăng chính xác, tăng kháng nguyên tố vĩnh viễn.
  - *Hỗ trợ (Buff):* Tăng tốc chạy, tăng công, khiên chắn sát thương.
- **Kỹ năng Bí kíp / Tuyệt kỹ:** Yêu cầu học từ sách cổ (Bí kíp 7x, 10x, 12x).

#### 2.4. Thực thể Phụ trợ & Biến thân
- **Phân thân (Clone Ninja):** Một bản sao xuất hiện chia sẻ một phần trang bị và chỉ số của chủ thể để hỗ trợ chiến đấu trong thời gian giới hạn.
- **Vĩ thú (Bijuu):** Thực thể phong ấn bổ trợ lượng lớn thuộc tính khi thức tỉnh.
- **Thời trang & Cải trang (Fashion):** Cải trang thành NPC hoặc hình thái đặc biệt, cộng thêm các dòng option ẩn.

---

### TRỤ CỘT 3: TRANG BỊ & KINH TẾ (EQUIPMENT, CRAFTING & ECONOMY)

#### 3.1. Phân loại 10 Ô Trang bị cốt lõi
1. **Trang bị tấn công:** Vũ khí (Đao, Kiếm, Kunai, Cung, Tiêu, Quạt).
2. **Trang bị phòng thủ:** Nón, Áo, Quần, Găng tay, Giày.
3. **Trang sức:** Dây chuyền, Nhẫn, Ngọc bội, Bùa.
4. **Trang bị Thú cưỡi (Mount Equipments):** Yên cương, Móng sắt, Giáp thú, Bàn đạp...

#### 3.2. Hệ thống Option (162 Thuộc tính)
- Đã được chuẩn hóa tại `STAT_COMBAT_REFACTOR_SPEC.md`.
- Gồm các nhóm: Sát thương phẳng, Sát thương %, Kháng nguyên tố, Xuyên giáp, Hút máu/mana, Phản đòn, Giảm trừ sát thương cố định, Tăng exp khi đánh quái...

#### 3.3. Hệ thống Cường hóa & Chế tác (Crafting)
- **Đập đồ (+1 đến +16):** Sử dụng Đá cấp 1 -> 12 kết hợp Yên/Xu tại NPC Kenshiko. Càng lên cấp cao, tỷ lệ thành công càng giảm và có rủi ro tụt cấp nếu không dùng bùa bảo hiểm.
- **Luyện ngọc & Khảm ngọc:** Ghép các mảnh ngọc thô thành ngọc cấp cao và đục lỗ trang bị để khảm ngọc tăng chỉ số.
- **Tinh luyện:** Tẩy luyện các dòng thuộc tính trang bị để đạt giá trị tối đa.

#### 3.4. Hệ sinh thái Tiền tệ & Giao thương
- **3 Đơn vị tiền tệ:**
  - **Xu (Silver/Gold):** Tiền tự do lưu thông giữa người chơi, rơi ra khi đánh quái hoặc giao dịch.
  - **Yên (Bound Silver):** Tiền khóa dùng cho các tác vụ nâng cấp, cường hóa cá nhân, không thể giao dịch.
  - **Lượng (Gems):** Tiền tệ cao cấp dùng mua vật phẩm đặc biệt trong Tiệm Kỳ Trân Các.
- **Các kênh giao thương:**
  - *Giao dịch P2P:* Mở cửa sổ trao đổi trực tiếp vật phẩm và xu giữa 2 nhân vật cạnh nhau.
  - *Mở sạp hàng (Stall):* Bày sạp bán đồ tự động tại Làng.
  - *Đấu giá Shinwa (Auction):* Treo đồ lên sàn đấu giá liên server.

---

### TRỤ CỘT 4: CHIẾN ĐẤU & GAMEPLAY LOOPS (COMBAT, PVE & PVP)

#### 4.1. Bộ máy Tính Sát thương (Damage Pipeline)
- Quy trình chuẩn hóa:
  $$\text{Raw Damage} \longrightarrow \text{Critical/Pierce} \longrightarrow \text{Elemental Multiplier} \longrightarrow \text{Mitigation/Armor} \longrightarrow \text{True Damage}$$
- Khắc chế hệ nguyên tố: Hỏa khắc Phong, Phong khắc Băng, Băng khắc Hỏa. Đòn đánh khắc hệ được gia tăng 20% sát thương.

#### 4.2. Vòng lặp PvE (Đánh quái & Săn Boss)
- **Quái thường:** Tự động hồi sinh theo chu kỳ (Respawn interval) tại tọa độ cố định.
- **Quái Tinh Anh & Thủ Lĩnh:** Máu gấp 10-20 lần quái thường, có hào quang đặc biệt, tỷ lệ rơi trang bị quý hiếm và đá cấp cao.
- **Boss Thế Giới & Dị Thú:** Xuất hiện theo khung giờ cố định hoặc gọi bằng vật phẩm triệu hồi. Yêu cầu nhiều người chơi hoặc gia tộc cùng phối hợp tiêu diệt.

#### 4.3. Hệ thống Hang động & Phó bản
- Hang động chia theo các cấp: Hang 3x, 4x, 5x, 6x, 7x, 9x.
- Giới hạn lượt đi hàng ngày, yêu cầu đi theo tổ đội hoặc cá nhân, vượt qua các tầng ải để diệt Boss tầng cuối nhận rương báu.

#### 4.4. Hệ thống Đối kháng (PvP)
- **Chế độ Bật Cờ:** Cờ màu (trắng, đen, đỏ, xanh...) cho phép các phe đối lập tấn công lẫn nhau ngoài khu an toàn.
- **Cừu sát (PK tự do):** Tuyên chiến tiêu diệt người chơi khác. Điểm hiếu chiến (PK point) tăng cao sẽ bị phạt: không thể mua đồ shop, khi chết bị rơi đồ hoặc mất exp.
- **Lôi đài Tỉ thí:** Đặt cược xu thách đấu 1-1 tại Làng mà không bị tính điểm hiếu chiến.
- **Thiên Địa Bảng:** Bảng xếp hạng thi đấu võ đài phân hạng Nhẫn giả cao cấp.

---

### TRỤ CỘT 5: XÃ HỘI & GIA TỘC (SOCIAL & CLAN)

#### 5.1. Tổ đội (Party)
- Tối đa 6 thành viên trong một đội.
- Điều kiện chia sẻ kinh nghiệm: Cùng đứng trong 1 Zone và nằm trong bán kính khoảng cách cho phép.
- Hiệu ứng Buff đội: Kỹ năng buff của phái Quạt có tác dụng lên toàn bộ thành viên trong đội.

#### 5.2. Gia tộc (Clan System)
- **Cấp bậc thành viên:** Tộc trưởng, Tộc phó, Trưởng lão, Thành viên chính thức, Thành viên dự bị.
- **Kỹ năng Gia tộc:** Sử dụng quỹ gia tộc để nâng cấp các kỹ năng bị động (Tăng HP, Tăng tấn công, Tăng exp) áp dụng cho mọi thành viên.
- **Kho & Quỹ gia tộc:** Nơi lưu trữ vật phẩm và xu đóng góp từ các thành viên.
- **Ải Gia tộc & Lãnh địa:** Phó bản riêng cho gia tộc đi săn boss hàng tuần.
- **Gia tộc chiến (Clan War):** Trận đánh quy mô lớn giữa 2 gia tộc đối địch tranh giành danh vọng và tài nguyên.

#### 5.3. Mối quan hệ cá nhân
- **Bạn bè (Friends):** Thêm bạn bè, theo dõi trạng thái online, nhắn tin riêng, dịch chuyển tới vị trí bạn bè.
- **Kẻ thù (Enemies):** Tự động ghi nhớ tên người đã cừu sát mình để báo thù.

---

### TRỤ CỘT 6: NHIỆM VỤ & VÒNG LẶP HÀNG NGÀY (QUESTS & DAILY LOOPS)

#### 6.1. Nhiệm vụ Cốt truyện Chính tuyến (Main Story Quests)
- Dẫn dắt người chơi từ lúc nhập học tại Làng Tone, làm quen các thầy cô, vượt qua các thử thách nhập môn, tiêu diệt quái theo cấp, mở khóa tính năng bang hội và phó bản.

#### 6.2. Nhiệm vụ Hàng ngày & Tuần hoàn
- **Nhiệm vụ Săn quái hàng ngày:** Nhận tại NPC Hiệu trưởng mỗi ngày, tiêu diệt số lượng quái cùng cấp để nhận lượng lớn EXP và Yên.
- **Nhiệm vụ Tà thú:** Săn lùng quái tà thú ẩn nấp ngẫu nhiên trong các map dã ngoại.
- **Nhiệm vụ Danh vọng:** Hoàn thành để tích lũy điểm danh vọng đổi các trang bị danh vọng quý hiếm.

---

### TRỤ CỘT 7: SỰ KIỆN MÙA & LIVEOPS (SEASONAL EVENTS)

#### 7.1. Các sự kiện theo mùa định kỳ
- **Tết Nguyên Đán:** Nấu bánh chưng, bánh tét, bắn pháo hoa, thu thập bao lì xì.
- **Lễ Giáng Sinh (Noel):** Làm hộp quà, làm khúc cây chocolate, săn Boss Người Tuyết / Tuần Lộc.
- **Sự kiện Hè & Trung Thu:** Làm bánh trung thu thập cẩm/đậu xanh, thả đèn trời, làm kem giải nhiệt.
- **Sự kiện Ngày Phụ Nữ (8/3 & 20/10):** Làm bó hoa hồng tặng NPC đổi thưởng.

#### 7.2. Cơ chế Core Loop của Event
$$\text{Đánh quái rơi nguyên liệu thô} \longrightarrow \text{Nấu/Chế tác tại NPC} \longrightarrow \text{Mở hộp quà ngẫu nhiên} \longrightarrow \text{Tích điểm đua Top Event}$$

#### 7.3. Minigame & Vòng quay May mắn (Lucky Draw)
- Sử dụng xu hoặc lượng để quay số ngẫu nhiên trúng các vật phẩm quý hiếm hoặc giải Jackpot tích lũy.

---

### TRỤ CỘT 8: HẠ TẦNG KỸ THUẬT & QUẢN TRỊ (INFRASTRUCTURE & ADMIN)

#### 8.1. Tầng Giao thức Mạng (Netty Socket Core)
- Kết nối TCP Socket hướng sự kiện, mã hóa byte stream 2 chiều.
- Xử lý phiên làm việc (`Session`), phát hiện flood packet/ddos và tự động ngắt kết nối xấu.

#### 8.2. Hệ thống Quản trị Game Master (GM)
- Lệnh chat GM trong game: `dame`, `hp`, `level`, `buff`, `item`, `boss`, `baotri`, `kick`.
- Lập lịch bảo trì an toàn: Tự động lưu toàn bộ dữ liệu nhân vật lên đĩa trước khi shutdown process.

#### 8.3. Hệ thống Cổng Web & Dịch vụ Ngoài
- API Bridge bảo mật đóng kín Port 3306.
- Quản lý mã Giftcode phát thưởng tự động.
- Hệ thống mã xác thực OTP 90 phút dùng để đăng ký tài khoản có kiểm soát.

