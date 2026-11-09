# NSO GAME SERVER — BỘ TÀI LIỆU HIỆN ĐẠI HÓA MÁY CHỦ (SERVER V2 CLONE & REFACTOR)

Thư mục này chứa toàn bộ tài liệu đặc tả kiến trúc, bản đồ nghiệp vụ, thiết kế cơ sở dữ liệu, quy chuẩn giao thức kết nối và lộ trình hiện đại hóa phục vụ cho dự án phát triển **Server V2 (Bản Clone Cải Tiến Độc Lập)** từ nền tảng máy chủ Ninja School Online.

---

> [!IMPORTANT]
> ### NGUYÊN TẮC BẤT BIẾN CỦA DỰ ÁN
> 1. **Bản Gốc V1 (`14444` / `nso_test` / `/home/ubuntu/nso-server`):** Là mốc tham chiếu bất khả xâm phạm (Pristine Reference Anchor). Tuyệt đối **không can thiệp, không sửa code, không đè database** lên bản gốc.
> 2. **Bản Clone V2 (`14445` / `nso_v2` / `/home/ubuntu/nso-server-v2`):** Là môi trường tiến hóa độc lập (Evolutionary Twin Sandbox). Toàn bộ các cải tiến kiến trúc Clean Architecture, cân bằng kỹ năng (`update_skills.sql`), công thức sát thương mới, và chống dupe/crash đều được thử nghiệm và hoàn thiện trên bản clone này.
> 3. **Phương pháp luận:** Áp dụng mô hình **Evolutionary Refactor (Strangler Fig Pattern)** thay vì Greenfield Rewrite. Bắt đầu từ bản clone chạy thông suốt 100% với client J2ME, sau đó bóc tách từng tầng có kiểm chứng.

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
| **00** | [**00_MASTER_PLAN_REWORK_VA_KIEN_TRUC.md**](./00_MASTER_PLAN_REWORK_VA_KIEN_TRUC.md) | **Kế Hoạch Tổng Thể & Kiến Trúc Strangler Fig V2**<br>• Bản gốc V1 an toàn tuyệt đối, V2 clone song song trên Port 14445.<br>• Phân loại: Cái gì giữ nguyên 100%, cái gì lấy công thức, cái gì tái cấu trúc.<br>• Mô hình 4 tầng phân lập (Clean Architecture). |
| **01** | [**01_BAN_DO_HE_SINH_THAI_GAME_NAY_CO_GI.md**](./01_BAN_DO_HE_SINH_THAI_GAME_NAY_CO_GI.md) | **"Game Này Có Gì?" (Game Domain Landscape)**<br>• Bóc tách 8 trụ cột hệ thống: Thế giới, Nhân vật 6 phái, 10 ô trang bị & 162 Option, Combat PvE/PvP, Gia tộc & Tổ đội, Nhiệm vụ, Sự kiện mùa, Hạ tầng kỹ thuật. |
| **02** | [**02_THIET_KE_DATABASE_V2_TINH_GON.md**](./02_THIET_KE_DATABASE_V2_TINH_GON.md) | **Tối Ưu & Chuẩn Hóa Database `nso_v2`**<br>• Clone an toàn từ `nso_test` sang `nso_v2`, không làm gián đoạn game.<br>• Kế hoạch dọn dẹp các bảng rác không sử dụng (thẻ cào, momo, cms).<br>• Chuyển đổi từ raw DBCP sang HikariCP hiệu năng cao. |
| **03** | [**03_QUY_CHUAN_GIAO_THUC_CLIENT_ADAPTER.md**](./03_QUY_CHUAN_GIAO_THUC_CLIENT_ADAPTER.md) | **Quy chuẩn Giao thức Client & Adapter Pattern**<br>• Giữ nguyên 100% định dạng byte socket với Game Client (Hộp đen J2ME).<br>• Vòng đời Client Fetching (CMD -27, -122, -121, -127, -107).<br>• Bóc tách switch-case khổng lồ thành 5 Packet Controllers chuyên biệt. |
| **04** | [**04_DAC_TA_DI_SAN_VA_KE_THUA.md**](./04_DAC_TA_DI_SAN_VA_KE_THUA.md) | **Đặc tả Di Sản & Danh mục Kế Thừa Thông Minh**<br>• 5 Cột trụ Bản sắc / DNA của NSO (Tam trường lục phái, Kenshiko +16, Avatar, Phân thân & Vĩ thú, Không gian tuổi thơ).<br>• Kế thừa Quái vật & Tọa độ Spawn kết hợp kỹ thuật **Zone Hibernation** (tiết kiệm 90% CPU).<br>• Kế thừa Nhiệm vụ 50 cấp cốt truyện kết hợp **Event-Driven Observer** nhẹ nhàng. |
| **05** | [**05_DAC_TA_CHI_TIET_5_TAP_HOP_VAI_TRO.md**](./05_DAC_TA_CHI_TIET_5_TAP_HOP_VAI_TRO.md) | **Đặc tả Chi Tiết 5 Tập Hợp Vai Trò (Cohesive Sets)**<br>• Phân rã thực thể `Char.java` (25k dòng) thành 5 Component chuyên trách: *Spatial, State, Combat, Inventory, Social*.<br>• Ranh giới dữ liệu, trách nhiệm duy nhất (SRP) và các phương thức cốt lõi. |
| **06** | [**06_CAC_CAU_HOI_VA_QUYET_DINH_KIEN_TRUC.md**](./06_CAC_CAU_HOI_VA_QUYET_DINH_KIEN_TRUC.md) | **Bản Khảo Sát & Quyết Định Kiến Trúc (ADR / RFC)**<br>• **Vấn đề 1:** Chiến lược lưu dữ liệu xuống DB.<br>• **Vấn đề 2:** Mô hình đa luồng thế giới game.<br>• **Vấn đề 3:** Cấu trúc lưu trữ options trang bị.<br>• **Vấn đề 4:** Chiến lược Clone Sandbox kiểm thử độc lập. |
| **07** | [**07_CHIEN_LUOC_MILESTONE_VA_UOC_LUONG_PHIEN_BAN.md**](./07_CHIEN_LUOC_MILESTONE_VA_UOC_LUONG_PHIEN_BAN.md) | **Lộ Trình Cải Tiến Từng Phân Đoạn (Evolutionary Milestones)**<br>• Lộ trình 4 Milestone thực chiến trên bản Clone V2.<br>• Mốc 1: Working Baseline V2 (Port 14445).<br>• Mốc 2: Controller Decoupling.<br>• Mốc 3: Domain Decomposition (`Char.java`) & Skill Balance.<br>• Mốc 4: Tối ưu Concurrency, HikariCP & Chống Dupe. |
| **08** | [**08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md**](./08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md) | **Thiết Kế Hệ Thống Trang Bị 5 Tier & Nội Suy Rời Rạc**<br>• Rời rạc hóa chỉ số ngẫu nhiên (Discrete Quantization) thành 5 phẩm chất A1 $\to$ A5.<br>• Bộ trạng thái mở rộng `{a; x; t; ...}` (Core Primitives + Sparse Extensible Payload).<br>• Thuật toán nội suy tuyến tính (Linear Interpolation) tận dụng `[min, max]` DB gốc. |
| **09** | [**09_THIET_KE_ENGINE_CHI_SO_VA_CAN_BANG_COMBAT.md**](./09_THIET_KE_ENGINE_CHI_SO_VA_CAN_BANG_COMBAT.md) | **Thiết Kế Engine Chỉ Số & Cân Bằng Combat**<br>• Đường ống tính toán chỉ số 3 tầng (3-Layer Stat Pipeline) xuất ra Immutable Record.<br>• Từ điển 34 chỉ số đối xứng Công - Thủ.<br>• Cơ chế Miễn thương Nhân tích ($1 - \prod(1 - r_i)$ chống bug bất tử). |
| **10** | [**10_GIAI_THUAT_XAC_SUAT_SINGLE_ROLL_MULTI_VARIABLE.md**](./10_GIAI_THUAT_XAC_SUAT_SINGLE_ROLL_MULTI_VARIABLE.md) | **Giải Thuật Xác Suất Đơn Lần Tung Đầu Ra Đa Biến**<br>• Khắc phục spaghetti If-Else trong tính toán né đòn/chí mạng.<br>• Mô hình trục xác suất tích lũy Single-Roll Table.<br>• Tối ưu hóa mức vi xử lý, ThreadLocalRandom. |
| **11** | [**11_THIET_KE_HE_THONG_TRANG_BI_VA_TIEM_NANG_V2.md**](./11_THIET_KE_HE_THONG_TRANG_BI_VA_TIEM_NANG_V2.md) | **Hệ Thống Trang Bị Đặc Thù, Kích Ẩn Đơn Giản & Ma Trận Tiềm Năng 6 Phái**<br>• Cấu trúc 4 dòng trang bị (Dòng 0 Hệ, Dòng 1 Nhóm, Dòng 2 Đặc thù, Dòng 3 Đột phá +4).<br>• Kích ẩn 2/3/4/5 đồ cùng hệ theo mỏ neo Level đồ nhỏ nhất.<br>• Ma trận quy đổi Tiềm năng riêng biệt khắc họa bản sắc 6 phái. |

---

> *Tài liệu được lưu trữ cố định tại thư mục: `docs/rework/`*
