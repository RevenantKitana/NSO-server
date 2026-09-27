# TÀI LIỆU TOÀN DIỆN VỀ HỆ THỐNG KỸ NĂNG 6 MÔN PHÁI NSO

> **Phiên bản máy chủ**: NSO Ninja School Private 2026  
> **Cơ sở dữ liệu**: Bảng `skill_template`, `skill`, `clazz` (`nso_test`)  
> **Hệ thống phân cấp**: 1x, 2x, 3x, 4x, 5x, 6x, 7x, 8x (Phân thân), 10x, 12x, 13x

---

## MỤC LỤC
1. [Tổng Quan Cấu Trúc Cây Kỹ Năng](#1-tổng-quan-cấu-trúc-cây-kỹ-năng)
2. [Chi Tiết Kỹ Năng Phái 1: NINJA KIẾM (Katana - Hệ Hỏa)](#2-chi-tiết-kỹ-năng-phái-1-ninja-kiếm)
3. [Chi Tiết Kỹ Năng Phái 2: NINJA TIÊU (Shuriken - Hệ Hỏa)](#3-chi-tiết-kỹ-năng-phái-2-ninja-tiêu)
4. [Chi Tiết Kỹ Năng Phái 3: NINJA KUNAI (Đoản Đao - Hệ Băng)](#4-chi-tiết-kỹ-năng-phái-3-ninja-kunai)
5. [Chi Tiết Kỹ Năng Phái 4: NINJA CUNG (Cung Tên - Hệ Băng)](#5-chi-tiết-kỹ-năng-phái-4-ninja-cung)
6. [Chi Tiết Kỹ Năng Phái 5: NINJA ĐAO (Bản Đao - Hệ Phong)](#6-chi-tiết-kỹ-năng-phái-5-ninja-đao)
7. [Chi Tiết Kỹ Năng Phái 6: NINJA QUẠT (Phiến - Hệ Phong)](#7-chi-tiết-kỹ-năng-phái-6-ninja-quạt)
8. [Bảng Phân Loại Kỹ Năng Theo Cấp Mở Khóa](#8-bảng-phân-loại-kỹ-năng-theo-cấp-mở-khóa)

---

## 1. TỔNG QUAN CẤU TRÚC CÂY KỸ NĂNG

Mỗi môn phái trong NSO sở hữu một cây kỹ năng hoàn chỉnh bao gồm **4 loại hình chiêu thức**:
1. **Chiêu thức Chủ động Tấn công (`type = 1`)**: Xuất chiêu gây sát thương trực tiếp (đơn mục tiêu hoặc đánh lan nhiều mục tiêu).
2. **Nhẫn thuật Tâm pháp Bị động (`type = 0`)**: Luôn luôn kích hoạt ẩn, tăng trực tiếp các chỉ số công, thủ, kháng, máu, mana, tốc độ chạy.
3. **Chiêu thức Hỗ trợ / Buff Bản thân & Đồng đội (`type = 2, 4`)**: Tàng hình, hồi máu, hồi sinh, tăng công thủ, hóa giải hiệu ứng.
4. **Chiêu thức Khống chế & Bắt quái (`type = 3`)**: Hóa thức ăn, làm chậm, đóng băng hoặc làm choáng.

---

## 2. CHI TIẾT KỸ NĂNG PHÁI 1: NINJA KIẾM

* **Trường**: Hirosaki | **Hệ**: Hỏa 🔥 | **Loại hình**: Cận chiến / Ngoại công (Sức Khỏe)  
* **Đặc trưng**: Sát thương bạo kích cực lớn, tỉ lệ nổ chí mạng cao, đòn đánh dồn dập thiêu đốt rút máu.

| Cấp | Skill ID | Tên Kỹ Năng | Loại Chiêu | Mô Tả Tác Dụng & Hiệu Ứng |
| :---: | :---: | :--- | :---: | :--- |
| **10** | `1` | **Chiêu Hiyoko** | Chủ động (1 mục tiêu) | Kiếm pháp nhập môn - vung kiếm chém mạnh vào 1 mục tiêu gây sát thương hỏa công. |
| **15** | `2` | **Chiêu Amagedon** | Bị động (Tâm pháp) | Nâng cao toàn diện: Lực tấn công, độ chính xác, né đòn và tốc độ di chuyển. |
| **20** | `3` | **Chiêu Jizokuzan** | Chủ động (1 mục tiêu) | Chém liên hoàn 3 nhát kiếm cực nhanh vào một mục tiêu chỉ định. |
| **25** | `4` | **Chiêu Kakyuu** | Khống chế quái vật | Sử dụng hỏa thuật thiêu đốt quái vật biến chúng thành thức ăn hồi phục HP. |
| **30** | `5` | **Chiêu X Zangeki** | Chủ động (Đánh lan 3 mục tiêu) | Xuất ra đường kiếm đan chéo hình chữ X quét sát thương tối đa 3 mục tiêu. |
| **35** | `6` | **Chiêu Raikou** | Ẩn thân / Buff | Tàng hình trước kẻ thù, nhát chém đầu tiên khi hiện hình **100% nổ Chí Mạng**. |
| **40** | `7` | **Chiêu Hihebun** | Chủ động (Dồn damage) | Mưa kiếm hỏa long giáng từ trên trời xuống, dồn lượng sát thương cực khủng vào 1 mục tiêu. |
| **45** | `8` | **Chiêu Shuurai** | Bị động (Kháng tính) | Tăng mạnh điểm Kháng Phong và rút ngắn thời gian bị dính hiệu ứng Choáng (Stun). |
| **50** | `9` | **Chiêu Choukouhirenzo** | Chủ động (Đánh lan 4 mục tiêu) | Xuất ra 3 hỏa kiếm cường đại quét sạch phạm vi rộng tối đa 4 mục tiêu. |
| **60** | `55` | **Chiêu Pawaraikou** | Bị động (Bí thuật 6x) | Nâng cao hỏa sát, tăng tỷ lệ giữ chân và tăng sức công phá của toàn bộ kiếm pháp. |
| **70** | `61` | **Chiêu Maajizangeki** | Tuyệt kỹ 7x | Vung nhát kiếm Hỏa Long Viêm Khí gây sát thương bộc phá và thiêu đốt diện rộng. |
| **80** | `67` | **Nhẫn thuật Kage Bunshin** | Phân thân | Triệu hồi một ảo ảnh phân thân hoàn hảo chiến đấu song song cùng bản chính. |
| **100**| `73` | **Chiêu Ikkakujuu** | Tuyệt kỹ 10x | Triệu hồi Kỳ Lân Lửa huyễn hóa trút bão lửa hủy diệt kẻ thù. |
| **120**| `79` | **Chiêu Enko Bakusatsu** | Tuyệt kỹ 12x | Kiếm thuật Hỏa Long Viêm Trảm chém rách không gian. |
| **130**| `85` | **Kiếm Hỏa Bộc Phá** | Tuyệt đỉnh 13x | Triệu hồi Thiên Hỏa Bộc Phá giáng đòn chí tử lên mục tiêu. |

---

## 3. CHI TIẾT KỸ NĂNG PHÁI 2: NINJA TIÊU

* **Trường**: Hirosaki | **Hệ**: Hỏa 🔥 | **Loại hình**: Viễn chiến / Nội công (Chakra)  
* **Đặc trưng**: Tốc độ ném phi tiêu nhanh nhất game, tầm xa linh hoạt, phân thân thả diều đối thủ.

| Cấp | Skill ID | Tên Kỹ Năng | Loại Chiêu | Mô Tả Tác Dụng & Hiệu Ứng |
| :---: | :---: | :--- | :---: | :--- |
| **10** | `10` | **Chiêu Otama** | Chủ động (1 mục tiêu) | Phóng phi tiêu hỏa thuật nhập môn gây sát thương tầm xa lên 1 mục tiêu. |
| **15** | `11` | **Chiêu Itotama** | Bị động (Tâm pháp) | Tăng lực tấn công nội, tỷ lệ phản đòn và độ chính xác khi phóng tiêu. |
| **20** | `12` | **Chiêu Kasoushuriken** | Chủ động (1 mục tiêu) | Phóng liên tiếp 2 phi tiêu bốc cháy về phía mục tiêu. |
| **25** | `13` | **Chiêu Taiyoutama** | Buff / Vòng lửa | Triệu hồi vòng lửa vô hình bao quanh, thiêu đốt liên tục các mục tiêu bị bản thân tấn công. |
| **30** | `14` | **Chiêu Bikoushuriken** | Chủ động (Đánh lan 3 mục tiêu) | Vận hỏa thuật đốt cháy phi tiêu phóng đi đánh trúng tối đa 3 mục tiêu. |
| **35** | `15` | **Chiêu Hoshitama** | Ẩn thân / Hồi phục | Ẩn thân trở nên vô hình, đồng thời tự động hồi phục HP và MP trong thời gian tàng hình. |
| **40** | `16` | **Chiêu Hinotama** | Chủ động (Dồn damage) | Trong chớp mắt phóng 2 đại phi tiêu rực lửa gây ra lượng sát thương cực lớn. |
| **45** | `17` | **Chiêu Hijoukai** | Bị động (Kháng tính) | Tăng điểm Kháng Phong và giúp cơ thể nhanh chóng hồi phục khi bị làm choáng. |
| **50** | `18` | **Chiêu Choukou shuriken**| Chủ động (Đánh lan 4 mục tiêu) | Phóng ra Đại Phi Tiêu kèm theo hỏa diễm xoay tròn càn quét 4 mục tiêu. |
| **60** | `56` | **Chiêu Totogai** | Bị động (Bí thuật 6x) | Nâng cao tầm ném, gia tăng nội công hỏa hệ và tốc độ xuất chiêu. |
| **70** | `62` | **Chiêu Baaningufukiya** | Tuyệt kỹ 7x | Bão phi tiêu thiêu đốt diện rộng kèm theo hiệu ứng giảm giáp đối phương. |
| **80** | `68` | **Nhẫn thuật Kage Bunshin** | Phân thân | Tạo ra phân thân Kage Bunshin cùng xả phi tiêu áp đảo kẻ địch. |
| **100**| `78` | **Chiêu Hibashiri** | Tuyệt kỹ 10x | Hỏa Điệp Phi Tiêu biến hóa quỹ đạo khó lường gây sát thương chí mạng. |
| **120**| `83` | **Chiêu Tsumabeni** | Tuyệt kỹ 12x | Tiêu thuật Hỏa Phụng Liêu Nguyên đốt cháy toàn bộ mục tiêu cản đường. |
| **130**| `86` | **Hỏa Diệp Tiêu** | Tuyệt đỉnh 13x | Triệu hồi Bão Hỏa Diệp cuồng nộ hủy diệt mục tiêu. |

---

## 4. CHI TIẾT KỸ NĂNG PHÁI 3: NINJA KUNAI

* **Trường**: Ookaza | **Hệ**: Băng ❄️ | **Loại hình**: Cận chiến / Ám sát / Ngoại công (Sức Khỏe)  
* **Đặc trưng**: Đột kích chớp nhoáng, khống chế làm chậm và đóng băng kẻ địch, khả năng né tránh vô địch.

| Cấp | Skill ID | Tên Kỹ Năng | Loại Chiêu | Mô Tả Tác Dụng & Hiệu Ứng |
| :---: | :---: | :--- | :---: | :--- |
| **10** | `19` | **Chiêu Yokobatan** | Chủ động (1 mục tiêu) | Kunai nhập môn - đâm chớp nhoáng gây sát thương băng hệ lên mục tiêu. |
| **15** | `20` | **Chiêu Akaiame** | Bị động (Tâm pháp) | Tăng tấn công, tăng cực mạnh chỉ số **Né đòn (`miss`)** và độ chính xác. |
| **20** | `21` | **Chiêu Mizuibatan** | Chủ động (1 mục tiêu) | Tung 2 nhát đâm liên hoàn kèm hàn băng làm chậm mục tiêu. |
| **25** | `22` | **Chiêu Aoiame** | Khống chế Băng | Băng thuật làm đông cứng quái vật, khiến chúng bất động không thể di chuyển. |
| **30** | `23` | **Chiêu Uzubatan** | Chủ động (Đánh lan 3 mục tiêu) | Xoay tròn lưỡi Kunai tạo thành vòng xoáy băng giá tấn công 3 mục tiêu lân cận. |
| **35** | `24` | **Chiêu Hibikou** | Ẩn thân / Ám sát | Tàng hình né tránh mọi đòn tấn công và chuẩn bị cho pha áp sát kết liễu. |
| **40** | `25` | **Chiêu Kogoeru** | Chủ động (Dồn damage) | Đâm kích bộc phá hàn băng gây sát thương cực lớn và đóng băng đối thủ. |
| **45** | `26` | **Chiêu Jotente** | Bị động (Kháng tính) | Tăng điểm Kháng Hỏa và giảm thời gian cơ thể bị dính hiệu ứng Bỏng. |
| **50** | `27` | **Chiêu Choukoukogo** | Chủ động (Đánh lan 4 mục tiêu) | Vung Kunai phóng ra luồng hàn khí cực mạnh quét sạch 4 mục tiêu. |
| **60** | `57` | **Chiêu Kitsukemaguma** | Bị động (Bí thuật 6x) | Nâng cao băng sát, gia tăng khả năng đóng băng bất động đối thủ trong PvP. |
| **70** | `63` | **Chiêu Furiizukatto** | Tuyệt kỹ 7x | Vết cắt Băng Giá gây sát thương chí tử và đông cứng kẻ địch tức thì. |
| **80** | `69` | **Nhẫn thuật Kage Bunshin** | Phân thân | Tạo phân thân Kunai áp sát cùng phối hợp tấn công. |
| **100**| `75` | **Chiêu Saihyoken** | Tuyệt kỹ 10x | Băng Long Phá Toái giáng xuống đè nát phòng ngự đối phương. |
| **120**| `81` | **Chiêu Shabondama** | Tuyệt kỹ 12x | Kunai thuật Hàn Băng Tuyệt Ảnh ảo diệu khôn lường. |
| **130**| `87` | **Băng Phong Đoản Đao** | Tuyệt đỉnh 13x | Triệu hồi Bão Tuyết Vĩnh Cửu đông cứng toàn bộ mục tiêu xung quanh. |

---

## 5. CHI TIẾT KỸ NĂNG PHÁI 4: NINJA CUNG

* **Trường**: Ookaza | **Hệ**: Băng ❄️ | **Loại hình**: Viễn chiến / Tầm xa / Nội công (Chakra)  
* **Đặc trưng**: Tầm bắn xa nhất trò chơi, chính xác tuyệt đối, khóa chân mục tiêu từ ngoài tầm nhìn.

| Cấp | Skill ID | Tên Kỹ Năng | Loại Chiêu | Mô Tả Tác Dụng & Hiệu Ứng |
| :---: | :---: | :--- | :---: | :--- |
| **10** | `28` | **Chiêu Uzusa** | Chủ động (1 mục tiêu) | Tiễn thuật cơ bản - bắn mũi tên tầm xa gây sát thương băng hệ. |
| **15** | `29` | **Chiêu Washihitomi** | Bị động (Tâm pháp Ưng Nhãn)| Tăng tầm nhìn, tăng cực mạnh độ chính xác và tỉ lệ đòn đánh Chí Mạng. |
| **20** | `30` | **Chiêu Kinkinsa** | Chủ động (1 mục tiêu) | Giương cung bắn liên tiếp 2 mũi tên băng xuyên phá vào mục tiêu. |
| **25** | `31` | **Chiêu Sogekihei** | Khống chế / Xuyên thấu | Bắn mũi tên xuyên thấu khiến quái vật bị bất động và giảm khả năng chống cự. |
| **30** | `32` | **Chiêu Nikinkinsa** | Chủ động (Đánh lan 3 mục tiêu) | Bắn ra chùm mưa tên băng giá càn quét 3 mục tiêu cùng lúc. |
| **35** | `33` | **Chiêu Joutenhitomi** | Ẩn thân / Tàng hình | Hòa nhập vào môi trường trở nên vô hình trước tầm mắt kẻ địch. |
| **40** | `34` | **Chiêu Kogosa** | Chủ động (Dồn damage) | Mũi Băng Tiễn Khổng Lồ xuyên thủng phòng ngự gây sát thương cực lớn. |
| **45** | `35` | **Chiêu Kyshouma** | Bị động (Kháng tính) | Tăng điểm Kháng Hỏa và nhanh chóng hóa giải trạng thái bị thiêu đốt. |
| **50** | `36` | **Chiêu Chousoukinkinsa**| Chủ động (Đánh lan 4 mục tiêu) | Mưa Băng Tiễn liên hoàn trút xuống tiêu diệt 4 mục tiêu diện rộng. |
| **60** | `58` | **Chiêu Totaaigo** | Bị động (Bí thuật 6x) | Gia tăng tối đa tầm bắn xa và cường hóa sức xuyên thấu của tiễn thuật. |
| **70** | `64` | **Chiêu Furoozunkyuusen**| Tuyệt kỹ 7x | Băng Long Cự Tiễn phóng đi với tốc độ siêu thanh gây sát thương hủy diệt. |
| **80** | `70` | **Nhẫn thuật Kage Bunshin** | Phân thân | Tạo phân thân Cung Thủ đứng từ xa hỗ trợ hỏa lực. |
| **100**| `76` | **Chiêu Aisu Meiku** | Tuyệt kỹ 10x | Bắn liên tục những mũi tên băng tạo ra các vòng xoáy bão tuyết. |
| **120**| `82` | **Chiêu Kogoraseru** | Tuyệt kỹ 12x | Tiễn thuật Long Tiễn Vong Nhân đoạt mạng kẻ địch từ xa. |
| **130**| `88` | **Tiễn Ưng Vĩnh Cửu** | Tuyệt đỉnh 13x | Đưa Băng Chakra vào mũi tên phát nổ tạo quả cầu năng lượng khổng lồ. |

---

## 6. CHI TIẾT KỸ NĂNG PHÁI 5: NINJA ĐAO

* **Trường**: Haruna | **Hệ**: Phong 🌪️ | **Loại hình**: Tanker / Đấu sĩ / Ngoại công (Sức Khỏe + Thể Lực)  
* **Đặc trưng**: Máu dày, giáp trâu nhất game, sát thương sấm sét gây Choáng (Stun) diện rộng.

| Cấp | Skill ID | Tên Kỹ Năng | Loại Chiêu | Mô Tả Tác Dụng & Hiệu Ứng |
| :---: | :---: | :--- | :---: | :--- |
| **10** | `37` | **Chiêu Enchokuto** | Chủ động (1 mục tiêu) | Đao pháp nhập môn - chém mạnh bản đao gây sát thương phong lôi. |
| **15** | `38` | **Chiêu Konoitoame** | Bị động (Tâm pháp) | **Tăng lượng lớn HP tối đa**, lực tấn công và tốc độ di chuyển. |
| **20** | `39` | **Chiêu Maroyakato** | Chủ động (1 mục tiêu) | Chém 2 nhát đao sấm sét uy lực liên tiếp vào mục tiêu. |
| **30** | `41` | **Chiêu Chousouto** | Chủ động (Đánh lan 3 mục tiêu) | Vung đao xuất ra luồng phong trảm càn quét 3 mục tiêu lân cận. |
| **35** | `42` | **Chiêu Aisubaagu** | Dịch chuyển / Áp sát | Dịch phong thuật lướt chớp nhoáng áp sát và chém thẳng vào mục tiêu. |
| **40** | `43` | **Chiêu Hayateto** | Chủ động (Dồn damage) | Nhát chém sấm sét cuồng phong giáng thẳng gây sát thương cực mạnh. |
| **45** | `44` | **Chiêu Zenpanteki** | Bị động (Kháng tính) | Tăng Kháng Băng và giúp bản thân nhanh chóng thoát khỏi đóng băng. |
| **50** | `45` | **Chiêu Raikouto** | Chủ động (Đánh lan 4 mục tiêu) | Vung đao xuất ra luồng sét giáng trúng 4 mục tiêu kèm tỷ lệ gây choáng. |
| **60** | `59` | **Chiêu Ikennotto** | Bị động (Bí thuật 6x) | Huyễn hóa lôi điện tăng mạnh HP tối đa và tỷ lệ làm choáng đối thủ. |
| **70** | `65` | **Chiêu Baasutosutoomu** | Tuyệt kỹ 7x | Phong quyển lôi điện cuồng bạo gây sát thương lớn và làm choáng diện rộng. |
| **80** | `71` | **Nhẫn thuật Kage Bunshin** | Phân thân | Tạo phân thân Đao Tiên phong đỡ đòn cho đội hình. |
| **100**| `74` | **Chiêu Kaminari** | Tuyệt kỹ 10x | Phóng ra những tia lôi điện giáng những đòn chí mạng hủy diệt. |
| **120**| `80` | **Chiêu Raijin** | Tuyệt kỹ 12x | Lôi thuật Lôi Mã Phong Vân Khởi uy chấn bốn phương. |
| **130**| `89` | **Vũ Đao Thiên Lôi** | Tuyệt đỉnh 13x | Triệu hồi Thiên Lôi giáng đòn hủy diệt toàn bộ khu vực. |

---

## 7. CHI TIẾT KỸ NĂNG PHÁI 6: NINJA QUẠT

* **Trường**: Haruna | **Hệ**: Phong 🌪️ | **Loại hình**: Pháp sư / Hỗ trợ / Nội công (Chakra + Thể Lực)  
* **Đặc trưng**: Khống chế bão lốc xoáy, **duy nhất có khả năng Hồi máu (Heal), Buff Công Thủ và Hồi sinh đồng đội**.

| Cấp | Skill ID | Tên Kỹ Năng | Loại Chiêu | Mô Tả Tác Dụng & Hiệu Ứng |
| :---: | :---: | :--- | :---: | :--- |
| **10** | `46` | **Chiêu Ouchia** | Chủ động (1 mục tiêu) | Quạt mạnh tạo luồng gió tấn công thẳng vào 1 mục tiêu. |
| **15** | `47` | **Chiêu Suishou** | **Hồi Máu (Heal)** | Trong mỗi giây **hồi phục lượng lớn HP liên tục** cho bản thân và toàn đội. |
| **20** | `48` | **Chiêu Oouchia** | Bị động (Tâm pháp) | Tăng HP, MP tối đa và **tăng % EXP nhận được khi đánh quái**. |
| **25** | `49` | **Chiêu Kusenmono** | **Hồi Sinh (Revive)** | **Hồi sinh tức thì đồng đội kiệt sức**, đồng thời gia tăng né đòn cho mục tiêu. |
| **30** | `50` | **Chiêu Bakkuuchiha** | Chủ động (Đánh lan 3 mục tiêu) | Ném quạt xoay tròn trước mắt gây sát thương lên 3 mục tiêu. |
| **35** | `51` | **Chiêu Hayatemi** | **Buff Công Thủ** | Tăng đồng thời khả năng tấn công và phòng ngự cho toàn bộ đồng đội trong nhóm. |
| **40** | `52` | **Chiêu Bousouhayate** | **Hóa Giải Hiệu Ứng** | Xóa bỏ và giảm trừ toàn bộ các hiệu ứng khống chế bất lợi cho toàn đội. |
| **45** | `53` | **Chiêu Toruneedo** | Bị động (Tâm pháp) | Tăng tỉ lệ né đòn hoàn toàn và gia tăng mạnh điểm Kháng Băng. |
| **50** | `54` | **Chiêu Tatsumaki** | Chủ động (Đánh lan 4 mục tiêu) | Tạo ra các cơn lốc xoáy sấm sét tấn công 4 mục tiêu diện rộng. |
| **60** | `60` | **Chiêu Ooenjo** | Bị động (Bí thuật 6x) | Kêu gọi linh phong tự nhiên dung nhập cơ thể, gia tăng uy lực hồi phục và buff. |
| **70** | `66` | **Chiêu Kougekitenrai** | Tuyệt kỹ 7x | Vụ nổ phong lôi nguyên tố cực mạnh gây sát thương và làm choáng diện rộng. |
| **80** | `72` | **Nhẫn thuật Kage Bunshin** | Phân thân | Tạo phân thân Quạt hỗ trợ hồi máu và khống chế quái vật. |
| **100**| `77` | **Chiêu Kokaze** | Tuyệt kỹ 10x | Triệu hồi đại lốc xoáy lôi điện quét sạch toàn bộ chiến trường. |
| **120**| `84` | **Chiêu Kamikaze** | Tuyệt kỹ 12x | Phiến thuật Phong Long Khiếu Thiên áp đảo vạn vật. |
| **130**| `90` | **Thuật Phiến Cuồng Phong**| Tuyệt đỉnh 13x | Triệu hồi Phong Lôi Cuồng Bạo gây sát thương kinh hoàng. |

---

## 8. BẢNG PHÂN LOẠI KỸ NĂNG THEO CẤP MỞ KHÓA

| Cấp Độ | Kiếm (Katana) | Tiêu (Shuriken) | Kunai | Cung (Bow) | Đao (Blade) | Quạt (Fan) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| **Level 10** | Hiyoko | Otama | Yokobatan | Uzusa | Enchokuto | Ouchia |
| **Level 15** | Amagedon *(Bị động)* | Itotama *(Bị động)* | Akaiame *(Bị động)* | Washihitomi *(Bị động)* | Konoitoame *(Bị động)* | **Suishou *(Hồi máu)*** |
| **Level 20** | Jizokuzan | Kasoushuriken | Mizuibatan | Kinkinsa | Maroyakato | Oouchia *(Bị động)* |
| **Level 25** | Kakyuu | Taiyoutama | Aoiame | Sogekihei | Magumandari | **Kusenmono *(Hồi sinh)*** |
| **Level 30** | X Zangeki | Bikoushuriken | Uzubatan | Nikinkinsa | Chousouto | Bakkuuchiha |
| **Level 35** | Raikou *(Tàng hình)* | Hoshitama *(Ẩn thân)* | Hibikou *(Tàng hình)* | Joutenhitomi *(Tàng hình)* | Aisubaagu *(Dịch chuyển)* | **Hayatemi *(Buff Công Thủ)*** |
| **Level 40** | Hihebun *(Dồn ST)* | Hinotama *(Dồn ST)* | Kogoeru *(Dồn ST)* | Kogosa *(Dồn ST)* | Hayateto *(Dồn ST)* | **Bousouhayate *(Xóa Debuff)*** |
| **Level 45** | Shuurai *(Bị động)* | Hijoukai *(Bị động)* | Jotente *(Bị động)* | Kyshouma *(Bị động)* | Zenpanteki *(Bị động)* | Toruneedo *(Bị động)* |
| **Level 50** | Choukouhirenzo | Choukou shuriken | Choukoukogo | Chousoukinkinsa | Raikouto | Tatsumaki |
| **Level 60** | Pawaraikou *(Bí thuật)* | Totogai *(Bí thuật)* | Kitsukemaguma *(Bí thuật)* | Totaaigo *(Bí thuật)* | Ikennotto *(Bí thuật)* | Ooenjo *(Bí thuật)* |
| **Level 70** | **Maajizangeki (7x)** | **Baaningufukiya (7x)** | **Furiizukatto (7x)** | **Furoozunkyuusen (7x)** | **Baasutosutoomu (7x)** | **Kougekitenrai (7x)** |
| **Level 80** | **Phân thân (8x)** | **Phân thân (8x)** | **Phân thân (8x)** | **Phân thân (8x)** | **Phân thân (8x)** | **Phân thân (8x)** |
| **Level 100**| **Ikkakujuu (10x)** | **Hibashiri (10x)** | **Saihyoken (10x)** | **Aisu Meiku (10x)** | **Kaminari (10x)** | **Kokaze (10x)** |
| **Level 120**| **Enko Bakusatsu (12x)**| **Tsumabeni (12x)** | **Shabondama (12x)** | **Kogoraseru (12x)** | **Raijin (12x)** | **Kamikaze (12x)** |
| **Level 130**| **Kiếm Hỏa Bộc Phá** | **Hỏa Diệp Tiêu** | **Băng Phong Đoản Đao**| **Tiễn Ưng Vĩnh Cửu** | **Vũ Đao Thiên Lôi** | **Thuật Phiến Cuồng Phong** |
