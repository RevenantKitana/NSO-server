# TÀI LIỆU ĐẶC TẢ KỸ THUẬT (TECHNICAL SPECIFICATION)
## TÁI CẤU TRÚC HỆ THỐNG CHỈ SỐ & BỘ MÁY TÍNH SÁT THƯƠNG NSO
**Dự án:** NSO Ninja School Online Server (Java / MongoDB / MariaDB)  
**Phiên bản tài liệu:** 1.0 - Sẵn sàng triển khai (Ready for Implementation)  
**Tác giả:** Antigravity Engineering Team  

---

## 1. MỤC TIÊU & NGUYÊN TẮC THIẾT KẾ (DESIGN PRINCIPLES)

### 1.1. Mục tiêu
1. **Dọn sạch mã nguồn:** Loại bỏ hơn 40 Option ID phân mảnh, triệt tiêu toàn bộ "số ma" (magic numbers) và các phép cộng thủ công dài loằng ngoằng trong `AbilityFromEquip.java`.
2. **Sửa dứt điểm các Bug logic ngầm:**
   - Option 12 (Kháng Băng) bị cộng nhầm vào Điểm Chính Xác.
   - Lỗi nhân đôi `basicMp` và vô hiệu hóa `% MP` trong công thức Max MP.
   - Kỹ năng hỗ trợ `optionsSupportSkill[65]` bị tính 2 lần cho Hệ Băng khi nổ chí mạng.
   - Sát thương chuẩn (Option 113) bị trừ bởi Giảm trừ cố định (`dameDown`) và trạng thái bất lợi.
3. **Mở rộng dễ dàng (Scalability):** Thêm trang bị mới, bùa mới, môn phái hoặc kỹ năng mới chỉ trong vài dòng code mà không cần đụng vào công thức tính toán.

### 1.2. Nguyên tắc "Bất biến" (Constraints)
* **Zero-Downtime & Backward Compatibility 100%:**
  * **Database:** Không thay đổi cấu trúc bảng, không sửa dữ liệu JSON túi đồ của người chơi.
  * **Client Protocol:** Giữ nguyên 100% cấu trúc các gói tin socket (`CMD.HAVE_ATTACK_PLAYER`, `CMD.NPC_HP`, `CMD.ME_LOAD_ALL`). Client không cần cập nhật file JAR/APK.

---

## 2. KIẾN TRÚC TỔNG THỂ (ARCHITECTURE DESIGN)

Áp dụng mô hình **Adapter Pattern + Modifier Aggregator** kết hợp **Pipeline Pattern**:

```
[ DỮ LIỆU ĐẦU VÀO ]
Trang bị / Thú cưỡi / Ngọc / Kỹ năng (Mã Option cũ: 0..150)
        │
        ▼
[ TẦNG ÁNH XẠ: OptionMapper ]
Chuyển đổi Option ID cũ ──> StatModifier (StatType, ModType, Value)
        │
        ▼
[ BỘ GOM CHỈ SỐ: StatContainer ]
Tự động gom Flat (+) và Percent (%) cho từng chỉ số
        │
        ▼
[ TỔNG HỢP NHÂN VẬT: AbilityFromEquip ]
Gán ra các thuộc tính Char: damage, damage2, maxHP, maxMP, exactly, miss...
        │
        ▼
[ BỘ MÁY TÍNH SÁT THƯƠNG: DamagePipeline ]
Raw Damage ──> Critical ──> Element ──> Mitigation ──> True Damage ──> Output
        │
        ▼
[ GÓI TIN GỬI CLIENT ]
MapService: attackCharacter / attackMonster
```

---

## 3. THIẾT KẾ CẤU TRÚC DỮ LIỆU MỚI (DATA MODELS)

### 3.1. Enum `StatType.java`
Định vị toàn bộ các loại chỉ số cốt lõi của game:

```java
package com.nsoz.model.stat;

public enum StatType {
    // Sát thương
    PHYSICAL_ATTACK,     // Ngoại công
    MAGIC_ATTACK,        // Nội công
    POTENTIAL_ATTACK,    // Tấn công theo tiềm năng
    ELEMENT_FIRE_ATTACK, // Tấn công hệ Hỏa
    ELEMENT_ICE_ATTACK,  // Tấn công hệ Băng
    ELEMENT_WIND_ATTACK, // Tấn công hệ Phong

    // Sinh tồn
    MAX_HP,              // Máu tối đa
    MAX_MP,              // Mana tối đa
    HP_AFTER_EQUIP,      // % HP sau khi mặc đồ (Option 128)

    // Phòng thủ & Kháng
    DAMAGE_REDUCTION,    // Giảm trừ sát thương cố định (dameDown)
    DAMAGE_MITIGATION,   // % Miễn giảm sát thương toàn diện
    PVP_DAMAGE_MITIGATION,// % Giảm sát thương từ người khác (Opt 63)
    RES_FIRE,            // Kháng Hỏa
    RES_ICE,             // Kháng Băng
    RES_WIND,            // Kháng Phong
    RES_ALL,             // Kháng tất cả các hệ

    // Đối kháng & Tỷ lệ
    ACCURACY,            // Chính xác
    EVASION,             // Né đòn
    CRIT_RATE,           // Tỷ lệ Chí mạng
    CRIT_DAMAGE,         // Sát thương Chí mạng
    RES_CRIT_DAMAGE,     // Kháng / Giảm sát thương chí mạng
    RES_CRIT_PERCENT,    // % Kháng ST Chí mạng (Opt 121)

    // Cơ chế đặc biệt
    TRUE_DAMAGE,         // Sát thương chuẩn (Opt 113)
    PVP_DAMAGE,          // Sát thương lên người (Opt 103)
    REFLECT_MELEE,       // Phản đòn cận chiến
    REFLECT_ICE_CHANCE,  // % Tỷ lệ phản đòn băng giá 20% HP (Opt 135)
    IGNORE_RESISTANCE,   // Xuyên kháng
    MOVE_SPEED           // Tốc độ di chuyển
}
```

### 3.2. Enum `ModType.java`
```java
package com.nsoz.model.stat;

public enum ModType {
    FLAT,    // Cộng điểm cố định (+100)
    PERCENT  // Cộng theo phần trăm (+10%)
}
```

### 3.3. Class `StatModifier.java`
```java
package com.nsoz.model.stat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StatModifier {
    private final StatType statType;
    private final ModType modType;
    private final int value;
}
```

### 3.4. Class `StatContainer.java` (Bộ gom chỉ số tối ưu bộ nhớ)
```java
package com.nsoz.model.stat;

import java.util.EnumMap;
import java.util.Map;

public class StatContainer {
    private final Map<StatType, Integer> flats = new EnumMap<>(StatType.class);
    private final Map<StatType, Integer> percents = new EnumMap<>(StatType.class);

    public void add(StatModifier modifier) {
        if (modifier == null || modifier.getValue() == 0) return;
        if (modifier.getModType() == ModType.FLAT) {
            flats.merge(modifier.getStatType(), modifier.getValue(), Integer::sum);
        } else {
            percents.merge(modifier.getStatType(), modifier.getValue(), Integer::sum);
        }
    }

    public int getFlat(StatType type) {
        return flats.getOrDefault(type, 0);
    }

    public int getPercent(StatType type) {
        return percents.getOrDefault(type, 0);
    }

    /**
     * Tính toán tổng chỉ số: (Base + Flat) * (100 + Percent) / 100
     */
    public int calculate(StatType type, int baseValue) {
        int flat = getFlat(type);
        int percent = getPercent(type);
        return (int) (((long) (baseValue + flat) * (100 + percent)) / 100);
    }

    public void clear() {
        flats.clear();
        percents.clear();
    }
}
```

---

## 4. MA TRẬN ÁNH XẠ OPTION CŨ (OPTION MAPPING MATRIX)

Tạo file `OptionMapper.java` để chuyển đổi toàn bộ Option ID cũ về `StatModifier`:

| Mã Option ID Cũ | Tên Thuộc Tính Cũ | Ánh Xạ Sang `StatType` | `ModType` | Ghi Chú Sửa Lỗi |
| :---: | :--- | :--- | :---: | :--- |
| **`0`** | Ngoại công vũ khí | `PHYSICAL_ATTACK` | `FLAT` | Gom chung |
| **`1`** | Nội công vũ khí | `MAGIC_ATTACK` | `FLAT` | Gom chung |
| **`2, 11, 33, 70, 96`**| Kháng Hỏa trang bị/thú | `RES_FIRE` | `FLAT` | Gom chung |
| **`3, 12, 34, 71, 95`**| Kháng Băng trang bị/thú | `RES_ICE` | `FLAT` | **Fix: Tách Opt 12 khỏi Exactly** |
| **`4, 13, 35, 72, 97`**| Kháng Phong trang bị/thú | `RES_WIND` | `FLAT` | Gom chung |
| **`5, 17, 62, 68, 78, 84, 115`**| Né đòn cố định (8 nguồn) | `EVASION` | `FLAT` | Gom 8 dòng về 1 |
| **`6, 32, 77, 82, 125`**| HP tối đa cố định (5 nguồn)| `MAX_HP` | `FLAT` | Gom 5 dòng về 1 |
| **`7, 19, 29, 83, 117`**| MP tối đa cố định (5 nguồn)| `MAX_MP` | `FLAT` | Gom 5 dòng về 1 |
| **`8`** | % Tăng Ngoại công | `PHYSICAL_ATTACK` | `PERCENT`| Nhân % theo Sức Khỏe |
| **`9`** | % Tăng Nội công | `MAGIC_ATTACK` | `PERCENT`| Nhân % theo Chakra |
| **`10, 18, 75, 86, 116`**| Chính xác cố định (5 nguồn)| `ACCURACY` | `FLAT` | Gom 5 dòng về 1 |
| **`14, 37, 69, 92, 114`**| Chí mạng cố định (5 nguồn)| `CRIT_RATE` | `FLAT` | Gom 5 dòng về 1 |
| **`15, 91, 126`**| Phản đòn cận chiến | `REFLECT_MELEE` | `FLAT` | Gom 3 dòng về 1 |
| **`21, 23, 25`**| Ngoại công trang sức | `PHYSICAL_ATTACK` | `FLAT` | Gom về Physical Flat |
| **`22, 24, 26`**| Nội công trang sức | `MAGIC_ATTACK` | `FLAT` | Gom về Magic Flat |
| **`28, 60`** | % MP tối đa | `MAX_MP` | `PERCENT`| **Fix công thức dấu + thành \*** |
| **`31, 61`** | % HP tối đa | `MAX_HP` | `PERCENT`| Tính theo HP gốc |
| **`36, 118`** | Kháng tất cả các hệ | `RES_ALL` | `FLAT` | Tự động cộng cả 3 hệ |
| **`38`** | Tấn công cộng thêm | `PHYSICAL_ATTACK` / `MAGIC` | `FLAT` | Phân phối theo class |
| **`39, 67`** | % Sát thương chí mạng | `CRIT_DAMAGE` | `PERCENT`| |
| **`46, 79`** | % Giảm ST chí mạng | `RES_CRIT_DAMAGE` | `PERCENT`| |
| **`47, 74, 80, 124`**| Giảm trừ sát thương cố định| `DAMAGE_REDUCTION` | `FLAT` | Gom vào dameDown |
| **`63`** | % Giảm ST từ người khác | `PVP_DAMAGE_MITIGATION` | `PERCENT`| |
| **`73, 76, 87`**| Tấn công phụ trợ | `PHYSICAL_ATTACK` / `MAGIC` | `FLAT` | Gom về Flat Attack |
| **`88, 89, 90`**| Tấn công theo hệ | `ELEMENT_X_ATTACK` | `FLAT` | Tính theo hệ nhân vật |
| **`94`** | % Tấn công tiềm năng | `POTENTIAL_ATTACK` | `PERCENT`| |
| **`98`** | % Miễn giảm sát thương | `DAMAGE_MITIGATION` | `PERCENT`| |
| **`103`** | Sát thương người chơi | `PVP_DAMAGE` | `FLAT` | Thêm tầng cuối PvP |
| **`105`** | Sát thương chí mạng cố định | `CRIT_DAMAGE` | `FLAT` | |
| **`113`** | Sát thương chuẩn | `TRUE_DAMAGE` | `FLAT` | Không bị cản giáp |
| **`121`** | % Kháng ST chí mạng | `RES_CRIT_PERCENT` | `PERCENT`| Cap tối đa 100% |
| **`127, 130, 131`**| % Kháng Hỏa/Băng/Phong | `RES_FIRE/ICE/WIND` | `PERCENT`| |
| **`128`** | % Tăng HP sau mặc đồ | `HP_AFTER_EQUIP` | `PERCENT`| Nhân trên tổng HP |
| **`135`** | % Phản đòn băng giá 20% HP | `REFLECT_ICE_CHANCE`| `PERCENT`| |

---

## 5. TÁI CẤU TRÚC `AbilityFromEquip.java`

Quy trình rút gọn từ 260 dòng spaghetti code xuống ~50 dòng logic trong sáng:

```java
public void setAbility(Char owner) {
    StatContainer stats = new StatContainer();

    // 1. Gom modifier từ tất cả trang bị, thú cưỡi, ngọc, mặt nạ
    collectItemModifiers(owner, stats);

    // 2. Gom modifier từ kỹ năng bị động (Passive Skills)
    collectPassiveSkillModifiers(owner, stats);

    // 3. Tính toán Tiềm Năng Chuẩn
    int potentialBonus = stats.getFlat(StatType.POTENTIAL);
    int potentialPercent = stats.getPercent(StatType.POTENTIAL); // Opt 58

    int basePotential = (owner.getSideClass() == 0) ? owner.potential[0] : owner.potential[3];
    int effectivePotential = (basePotential + potentialBonus) * (100 + potentialPercent) / 100;

    // 4. Tính toán Sát Thương Tối Đa (damage) & Tối Thiểu (damage2)
    StatType atkType = (owner.getSideClass() == 0) ? StatType.PHYSICAL_ATTACK : StatType.MAGIC_ATTACK;
    int baseAtk = effectivePotential; // Không lặp double dipping
    owner.damage = stats.calculate(atkType, baseAtk);
    
    // Thêm các nguồn phụ trợ
    owner.damage += (effectivePotential * 4) * stats.getPercent(StatType.POTENTIAL_ATTACK) / 100;
    owner.damage2 = owner.damage * 90 / 100; // Min dame = 90% Max dame

    // 5. Tính toán HP Chuẩn
    int effThểLực = (owner.potential[2] + potentialBonus) * (100 + potentialPercent) / 100;
    int basicHp = effThểLực * 10;
    owner.maxHP = stats.calculate(StatType.MAX_HP, basicHp);
    if (stats.getPercent(StatType.HP_AFTER_EQUIP) > 0) {
        owner.maxHP += owner.maxHP * stats.getPercent(StatType.HP_AFTER_EQUIP) / 100;
    }

    // 6. Tính toán MP Chuẩn (ĐÃ SỬA BUG DẤU CỘNG & CÂN BẰNG LẠM PHÁT MP)
    int effChakra = (owner.potential[3] + potentialBonus) * (100 + potentialPercent) / 100;
    int mpPerPoint = (owner.getSideClass() == 1) ? 5 : 8; // Nội công: 5 MP, Ngoại công: 8 MP
    int basicMp = effChakra * mpPerPoint;
    owner.maxMP = stats.calculate(StatType.MAX_MP, basicMp);

    // 7. Tính Né Đòn & Chính Xác (ĐÃ GỠ OPTION 12 KHÁNG BĂNG)
    int effThânPháp = (owner.potential[1] + potentialBonus) * (100 + potentialPercent) / 100;
    owner.miss = stats.calculate(StatType.EVASION, effThânPháp * 150 / 100);
    owner.exactly = stats.calculate(StatType.ACCURACY, effThânPháp);

    // 8. Kháng 3 Hệ (Gộp tự động Kháng tất cả Opt 36 & 118)
    int resAll = stats.getFlat(StatType.RES_ALL);
    owner.resFire = stats.getFlat(StatType.RES_FIRE) + resAll;
    owner.resIce = stats.getFlat(StatType.RES_ICE) + resAll;
    owner.resWind = stats.getFlat(StatType.RES_WIND) + resAll;

    // 9. Giảm trừ cố định & Phản đòn
    owner.dameDown = stats.getFlat(StatType.DAMAGE_REDUCTION);
    owner.reactDame = stats.getFlat(StatType.REFLECT_MELEE);
    owner.fatal = stats.getFlat(StatType.CRIT_RATE);
}
```

---

## 6. TÁI CẤU TRÚC BỘ MÁY GIAO TRANH `DamagePipeline.java`

Tách rời logic tính sát thương khỏi `Char.java` vào một service riêng biệt `CombatService.java`:

### 6.1. Quy trình xử lý theo từng Step chuẩn:
1. **Step 1 - Roll Base Damage:** `dameHit = random(attacker.damage2, attacker.damage)`.
2. **Step 2 - Fire Burn Status:** Nếu mục tiêu bị bỏng (`isFire`), `dameHit += dameBasic`.
3. **Step 3 - Critical Strike:**
   * Tính bạo kích: `dameFatal = dameBasic * (1 + %STCM) + fatalDame`.
   * Trừ Kháng STCM của B: `dameFatal = dameFatal * (1 - min(100, B.opt121) / 100)`.
4. **Step 4 - Elemental & Counter:**
   * Trừ kháng hệ cố định (bỏ qua nếu có Xuyên kháng).
   * Trừ % kháng hệ.
   * Cộng thưởng khắc hệ (nếu hệ A khắc hệ B).
5. **Step 5 - Percent Damage Mitigation:**
   * `dameHit = dameHit * (1 - min(75, B.miễn_giảm_percent) / 100)` *(Áp trần tối đa 75% để chống bất tử)*.
6. **Step 6 - PvP Scale & Level Difference:**
   * `dameHit = (dameHit + dameFatal / 3) * (6 - (LevelB - LevelA) / 40) / 100`.
7. **Step 7 - Reflection (Phản đòn):**
   * Nếu nổ Opt 135: phản 20% HP hiện tại của A.
   * Nếu không: phản `B.reactDame`. Trừ máu A trước.
8. **Step 8 - Accuracy vs Evasion Check:**
   * Xúc xắc: `random(B.miss + 100) > random(A.exactly + 100)` $\rightarrow$ Hụt (`isMiss = true`).
9. **Step 9 - True Damage & Flat Reduction (Chuẩn hóa thứ tự):**
   * Trừ suy yếu / đóng băng trên sát thương thông thường.
   * Trừ Giảm trừ cố định `dameDown`.
   * **CỘNG SÁT THƯƠNG CHUẨN (Option 113) TẠI ĐÂY:** Sát thương chuẩn cộng trực tiếp vào kết quả cuối cùng, không bị trừ bởi `dameDown` hay suy yếu.

---

## 7. KẾ HOẠCH TRIỂN KHAI THEO 4 GIAI ĐOẠN (ROADMAP)

```
[ Phase 1: Models & Mapper ] (1 ngày)
  ├── Tạo package com.nsoz.model.stat
  ├── Viết StatType, ModType, StatModifier, StatContainer
  └── Viết OptionMapper ánh xạ toàn bộ Option ID cũ
        │
[ Phase 2: Refactor AbilityFromEquip ] (1 ngày)
  ├── Thay thế logic mảng trong AbilityFromEquip
  ├── Fix bug Opt 12 (Kháng Băng), bug Max MP
  └── Kiểm tra màn hình chỉ số (Panel C) trên Local Client
        │
[ Phase 3: Refactor Combat Engine ] (2 ngày)
  ├── Tạo DamageCalculator / CombatService
  ├── Chuyển logic từ Char.java sang service mới
  ├── Fix bug sát thương chuẩn & áp trần Miễn giảm 75%
  └── Tách biệt code PvE (đánh quái) và PvP (đánh người)
        │
[ Phase 4: Verification & Stress Test ] (1 ngày)
  ├── Test PK 1vs1 giữa các phái
  ├── Test Đánh Boss / Quái thường
  └── Đóng gói build và deploy lên Cloud VM
```

---

## 8. CHECKLIST KIỂM THỬ (QA / TEST CRITERIA)

| Hạng mục kiểm tra | Tiêu chí ĐẠT (Pass) |
| :--- | :--- |
| **1. Đeo / Tháo Giày Kháng Băng** | Điểm Kháng Băng tăng đúng, **Điểm Chính Xác không được thay đổi**. |
| **2. Bảng Max MP** | Điểm Max MP phản ánh đúng `1 Chakra = 5 MP` (Nội công) / `8 MP` (Ngoại công), các dòng `% MP` tăng đúng tỷ lệ. |
| **3. Điểm Tấn Công (Panel C)** | Tấn công Min - Max hiển thị mượt mà, không bị nhảy số vô lý khi bấm đổi chiêu. |
| **4. Thử nghiệm Bất Tử** | Mặc đồ full Opt 63 + 98: Sát thương nhận vào vẫn tối thiểu 25% (nhờ trần 75%), không còn bị bất tử 0 dame. |
| **5. Sát thương chuẩn (Opt 113)** | Dù đối thủ có 10.000 `dameDown` hay người đánh bị suy yếu, sát thương chuẩn vẫn gây đúng bằng giá trị ghi trên đồ. |
| **6. Độ ổn định Server** | Chạy 500 bot tấn công liên tục: RAM ổn định, không xuất hiện `OutOfMemoryError` hay nghẽn GC. |
