# THIẾT KẾ DATABASE V2 TINH GỌN & KẾ HOẠCH DỌN RÁC DỮ LIỆU
## TỪ 70+ BẢNG LEGACY XUỐNG ~18 BẢNG CHUẨN MỰC
**Dự án:** NSO Game Server Modernization  
**Mục tiêu:** Loại bỏ toàn bộ bảng rác, chuẩn hóa cấu trúc dữ liệu người chơi, xóa bỏ anti-pattern "nhồi JSON", thiết lập quan hệ khóa ngoại (Foreign Key) và quy chuẩn Collation UTF8MB4.

---

## 1. PHÂN TÍCH HIỆN TRẠNG & BẢNG BỊ LOẠI BỎ (TRASH INVENTORY)

File SQL hiện tại ([`database/init_nso_clean.sql`](file:///e:/TT/SETUP_LOCAL/SRC_GAME/database/init_nso_clean.sql)) chứa hơn 70 bảng. Đa số là tàn tích của các bộ web nạp thẻ tự động, diễn đàn CMS hoặc bản backup cũ.

### 1.1. Danh sách hơn 40 bảng VỨT BỎ HOÀN TOÀN
| Nhóm Bảng | Danh sách bảng cần loại bỏ | Lý do loại bỏ |
| :--- | :--- | :--- |
| **Cổng Nạp & Thẻ cào** | `charging_drivers`, `charging_fees`, `chargings`, `chargings2`, `chargings_telco`, `ctv_money`, `deposit`, `localbanks`, `momo_accounts`, `napatm`, `napcard`, `payments`, `quydoi`, `transactions`, `biendongsodu` | Thuộc về web nạp tiền của bên thứ 3 cũ, không phục vụ lõi game server. |
| **Hệ thống Web CMS / Forum** | `comments`, `news`, `others`, `giveaways`, `user_logs`, `comments` | Các bảng diễn đàn web cũ, game server không đọc đến. |
| **Quyền hạn Web (Laravel Spatie)** | `roles`, `permissions`, `model_has_roles`, `model_has_permissions`, `role_has_permissions`, `personal_access_tokens`, `migrations` | Dấu vết của framework PHP Laravel dùng cho trang quản trị cũ. |
| **Bảng Rác & Sao lưu thừa** | `item_bk`, `item_check`, `sql_backup`, `chargings2` | Các bảng backup thủ công lưu tạm trong quá khứ. |

### 1.2. Các "hạt sạn" kỹ thuật trong bảng người chơi cũ
* **Collation hỗn tạp:** Bảng `users` dùng `utf8mb3_bin` xen lẫn `utf8mb3_general_ci`, trong khi bảng `players` dùng `utf8mb4_general_ci` và `utf8mb4_bin`. Sự lệch pha này dễ gây lỗi font chữ tiếng Việt khi query JOIN.
* **Anti-pattern "Cả thế giới nằm trong JSON text":**
  Trong bảng `players`, gần như mọi thuộc tính động đều là chuỗi JSON:
  * `bag`, `box`, `equiped`, `fashion`, `mount`, `bijuu`, `friends`, `enemies`, `task`, `event_point`, `mask_box`...
  * **Hậu quả:** Không thể dùng SQL để thống kê, không thể truy vết khi xảy ra lỗi dup đồ, hiệu năng đọc/ghi đĩa kém vì mỗi lần lưu phải serialize chuỗi JSON hàng chục KB.

---

## 2. BẢN THIẾT KẾ DATABASE V2 TINH GỌN (~18 BẢNG)

Database V2 chia làm 3 phân vùng rõ rệt:

```mermaid
graph TD
    subgraph VÙNG 1: DỮ LIỆU GAME TĨNH (READ-ONLY METADATA)
        T1[item_templates]
        T2[skill_templates]
        T3[map_templates]
        T4[monster_templates]
        T5[npc_templates]
        T6[effect_templates]
        T7[task_templates]
    end

    subgraph VÙNG 2: NGƯỜI CHƠI & TÀI SẢN (DYNAMIC PLAYER STATE)
        D1[users]
        D2[characters]
        D3[character_items]
        D4[character_skills]
        D5[clans]
        D6[clan_members]
        D7[social_relationships]
    end

    subgraph VÙNG 3: KINH TẾ & HỆ THỐNG (SYSTEM & ECONOMY)
        S1[stalls_auction]
        S2[giftcodes]
        S3[giftcode_histories]
        S4[game_logs]
    end

    D1 --> D2
    D2 --> D3
    D2 --> D4
    D5 --> D6
    D2 --> D7
    T1 -.-> D3
    T2 -.-> D4
```

### 2.1. Vùng 1: Dữ liệu Game Tĩnh (Templates)
*Kế thừa 100% dữ liệu gốc từ database cũ, chỉ chuẩn hóa lại Collation `utf8mb4_unicode_ci`.*
1. `item_templates`: `id`, `name`, `type`, `gender`, `level_require`, `part`, `icon_id`, `description`.
2. `skill_templates`: `id`, `class_id`, `name`, `type`, `max_point`, `description`.
3. `skill_options`: `skill_template_id`, `point`, `option_id`, `param`.
4. `map_templates`: `id`, `name`, `tile_id`, `bg_id`, `type`.
5. `monster_templates`: `id`, `name`, `type`, `hp_max`, `level`, `icon_id`.
6. `npc_templates`: `id`, `name`, `head_id`, `body_id`, `leg_id`.
7. `task_templates`: `id`, `name`, `detail`, `sub_names`.

### 2.2. Vùng 2: Người chơi & Dữ liệu Nhân vật (Đã tái cấu trúc chuẩn)

#### Bảng `users` (Tài khoản)
```sql
CREATE TABLE `users` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `username` VARCHAR(32) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL, -- Hashing bằng BCrypt
    `role` TINYINT NOT NULL DEFAULT 0, -- 0: User, 1: Mod, 99: Admin
    `status` TINYINT NOT NULL DEFAULT 1, -- 1: Hoạt động, 0: Bị khóa
    `luong` INT NOT NULL DEFAULT 0,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### Bảng `characters` (Nhân vật)
*Tách bỏ các cột JSON khổng lồ sang bảng liên kết.*
```sql
CREATE TABLE `characters` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `user_id` INT NOT NULL,
    `name` VARCHAR(20) NOT NULL UNIQUE,
    `gender` TINYINT NOT NULL DEFAULT 1, -- 0: Nữ, 1: Nam
    `class_id` TINYINT NOT NULL DEFAULT 0, -- 0: Chưa vào phái, 1-6: 6 phái
    `level` INT NOT NULL DEFAULT 1,
    `exp` BIGINT NOT NULL DEFAULT 0,
    `potential_point` INT NOT NULL DEFAULT 0,
    `skill_point` INT NOT NULL DEFAULT 0,
    `potential_str` INT NOT NULL DEFAULT 15,
    `potential_agi` INT NOT NULL DEFAULT 5,
    `potential_vit` INT NOT NULL DEFAULT 5,
    `potential_spi` INT NOT NULL DEFAULT 5,
    `xu` BIGINT NOT NULL DEFAULT 0,
    `yen` BIGINT NOT NULL DEFAULT 0,
    `xu_in_box` BIGINT NOT NULL DEFAULT 0,
    `bag_slots` TINYINT NOT NULL DEFAULT 30,
    `box_slots` TINYINT NOT NULL DEFAULT 30,
    `map_id` INT NOT NULL DEFAULT 22, -- Làng Tone
    `map_x` INT NOT NULL DEFAULT 1741,
    `map_y` INT NOT NULL DEFAULT 264,
    `head_id` SMALLINT NOT NULL DEFAULT 14,
    `pk_point` INT NOT NULL DEFAULT 0,
    `online` TINYINT NOT NULL DEFAULT 0,
    `last_login` TIMESTAMP NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT `fk_char_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    INDEX `idx_char_name` (`name`),
    INDEX `idx_user_chars` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### Bảng `character_items` (Chuẩn hóa toàn bộ Túi / Rương / Trang bị / Thú cưỡi)
*Áp dụng mô hình **Core Primitives + Sparse Extensible Payload {a; x; t; ...}** (Xem chi tiết tại [`08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md`](08_THIET_KE_HE_THONG_TRANG_BI_5_TIER.md)). Triệt tiêu 100% JSON cho đồ cơ bản.*
```sql
CREATE TABLE `character_items` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `character_id` INT NOT NULL,
    `template_id` INT NOT NULL,                 -- [a] Mã template
    `upgrade_level` TINYINT NOT NULL DEFAULT 0,  -- [x] Cường hóa +0 đến +16
    `tier` TINYINT NOT NULL DEFAULT 3,           -- [t] Phẩm chất A1 -> A5 (1 -> 5, mặc định A3)
    `location` ENUM('BAG', 'BOX', 'EQUIP', 'MOUNT', 'FASHION') NOT NULL,
    `slot_index` INT NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    `sys` TINYINT NOT NULL DEFAULT 0,            -- Hệ nguyên tố (1: Hỏa, 2: Băng, 3: Phong)
    `flags` INT UNSIGNED NOT NULL DEFAULT 0,     -- Bitmask: 0x01: Khóa, 0x02: Giám định, 0x04: Sự kiện...
    `yen_price` INT NOT NULL DEFAULT 0,
    `expires_at` BIGINT NOT NULL DEFAULT -1,     -- -1: Vĩnh viễn, hoặc timestamp ms
    `extra_data` JSON DEFAULT NULL,              -- SPARSE PAYLOAD: Gems, thợ rèn... (NULL = 0 byte đĩa)
    CONSTRAINT `fk_item_char` FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE,
    INDEX `idx_char_location` (`character_id`, `location`, `slot_index`),
    INDEX `idx_template` (`template_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### Bảng `clans` & `clan_members` (Gia tộc chuẩn hóa)
```sql
CREATE TABLE `clans` (
    `id` INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(30) NOT NULL UNIQUE,
    `level` INT NOT NULL DEFAULT 1,
    `exp` BIGINT NOT NULL DEFAULT 0,
    `leader_id` INT NOT NULL,
    `coin` BIGINT NOT NULL DEFAULT 0,
    `notice` TEXT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `clan_members` (
    `clan_id` INT NOT NULL,
    `character_id` INT NOT NULL,
    `role` TINYINT NOT NULL DEFAULT 0, -- 0: Dự bị, 1: Thành viên, 2: Trưởng lão, 3: Tộc phó, 4: Tộc trưởng
    `contribute_coin` BIGINT NOT NULL DEFAULT 0,
    `joined_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`clan_id`, `character_id`),
    CONSTRAINT `fk_clan_mem_clan` FOREIGN KEY (`clan_id`) REFERENCES `clans` (`id`) ON DELETE CASCADE,
    CONSTRAINT `fk_clan_mem_char` FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

#### Bảng `social_relationships` (Bạn bè & Kẻ thù)
```sql
CREATE TABLE `social_relationships` (
    `character_id` INT NOT NULL,
    `target_id` INT NOT NULL,
    `type` ENUM('FRIEND', 'ENEMY') NOT NULL,
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`character_id`, `target_id`, `type`),
    CONSTRAINT `fk_rel_char` FOREIGN KEY (`character_id`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 2.3. Vùng 3: Kinh tế, Vận hành & Quản trị
* `stalls_auction`: Chợ và Đấu giá (`id`, `seller_id`, `item_data`, `price`, `type`, `created_at`).
* `giftcodes`: Danh mục code phát quà (`id`, `code`, `rewards_json`, `usage_limit`, `expired_at`).
* `giftcode_histories`: Lịch sử nhận code (`code_id`, `user_id`, `claimed_at`).
* `game_logs`: Nhật ký kiểm soát biến động tài sản lớn (Xu/Lượng/Giao dịch).

---

## 3. CHIẾN LƯỢC MIGRATION (ETL PIPELINE CHUYỂN ĐỔI DỮ LIỆU)

Để đưa dữ liệu người chơi từ Database V1 sang Database V2 mà không mất tài sản:

```
[Database Cũ: nso_clean]
          │
          ▼ (Extract)
[Kịch bản Chuyển đổi ETL (Java / Node.js)]
    - Đọc Users: Giữ nguyên username, hash mật khẩu.
    - Đọc Players: Phân rã potential [15,5,5,5] -> các cột số nguyên.
    - Giải nén JSON: bag, box, equiped -> Bắn thành từng dòng vào character_items.
          │
          ▼ (Transform & Load)
[Database Mới: nso_v2 (Chuẩn hóa 100%)]
```

### Điểm an toàn của phương pháp này:
* Database cũ `nso_clean` **không hề bị chỉnh sửa hay xóa bỏ**.
* Kịch bản ETL có thể chạy thử nghiệm nhiều lần trên môi trường local cho đến khi đối soát khớp 100% tài sản và số lượng trang bị giữa V1 và V2.

