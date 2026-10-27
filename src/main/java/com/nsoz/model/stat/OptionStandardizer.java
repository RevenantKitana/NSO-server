package com.nsoz.model.stat;

import com.nsoz.option.ItemOption;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Bộ chuẩn hóa mã Option & Gom nhóm chỉ số trang bị NSO.
 *
 * Triết lý Big Update:
 * 1. Tinh gọn triệt để: Gộp các mã phân mảnh và dòng ẩn Type 3..7 về Canonical ID duy nhất.
 * 2. Thuật toán Cộng Dồn (Param +=): Nếu mã chuẩn đã tồn tại trên trang bị thì cộng dồn chỉ số.
 * 3. BẢO VỆ ĐỘC LẬP TẤN CÔNG: Tuyệt đối không lẫn giữa Tấn công cố định (Flat 0/1) và % Vật công (8/9/94).
 */
public class OptionStandardizer {

    // Ma trận ánh xạ chuyển đổi: [Mã cũ/phân mảnh/ẩn] -> [Mã Canonical ID chuẩn]
    private static final Map<Integer, Integer> CANONICAL_MAP = new HashMap<>();

    static {
        // ==========================================
        // 1. SÁT THƯƠNG TẤN CÔNG CỐ ĐỊNH (FLAT)
        // ==========================================
        // Ngoại công cố định (Flat Attack) -> Option 0
        CANONICAL_MAP.put(21, 0); // Hỏa công ngoại (flat)
        CANONICAL_MAP.put(23, 0); // Băng sát ngoại (flat)
        CANONICAL_MAP.put(25, 0); // Phong lôi ngoại (flat)
        CANONICAL_MAP.put(73, 0); // Tấn công thú/pet (flat)
        CANONICAL_MAP.put(76, 0); // Tăng tấn công cho chủ (flat)
        CANONICAL_MAP.put(87, 0); // Tấn công trang bị thú (flat)

        // Nội công cố định (Flat Attack) -> Option 1
        CANONICAL_MAP.put(22, 1); // Hỏa công nội (flat)
        CANONICAL_MAP.put(24, 1); // Băng sát nội (flat)
        CANONICAL_MAP.put(26, 1); // Phong lôi nội (flat)

        // LƯU Ý BẤT BIẾN:
        // Option 8 (% Vật công Ngoại), Option 9 (% Vật công Nội), Option 94 (% Tấn công tiềm năng)
        // KHÔNG ĐƯỢC ÁNH XẠ VÀO 0 HAY 1, GIỮ NGUYÊN RIÊNG BIỆT 100%!

        // ==========================================
        // 2. MÁU (HP) & MANA (MP)
        // ==========================================
        // HP tối đa cố định (Flat HP) -> Option 6
        CANONICAL_MAP.put(32, 6);  // (+12) HP tối đa
        CANONICAL_MAP.put(77, 6);  // Tăng max HP cho chủ
        CANONICAL_MAP.put(82, 6);  // HP tối đa (thú)
        CANONICAL_MAP.put(125, 6); // HP tối đa (ngọc)

        // MP tối đa cố định (Flat MP) -> Option 7
        CANONICAL_MAP.put(19, 7);  // MP tối đa
        CANONICAL_MAP.put(29, 7);  // (+12) MP tối đa
        CANONICAL_MAP.put(83, 7);  // MP tối đa (thú)
        CANONICAL_MAP.put(117, 7); // MP tối đa

        // % Tỉ lệ HP tối đa -> Option 31
        CANONICAL_MAP.put(61, 31); // (+8) Tỉ lệ HP tối đa

        // % Tỉ lệ MP tối đa -> Option 28
        CANONICAL_MAP.put(60, 28); // (+4) Tỉ lệ MP tối đa

        // ==========================================
        // 3. NÉ ĐÒN & CHÍNH XÁC (FLAT)
        // ==========================================
        // Né đòn cố định -> Option 5
        CANONICAL_MAP.put(17, 5);  // Né đòn
        CANONICAL_MAP.put(62, 5);  // (+12) Né đòn
        CANONICAL_MAP.put(68, 5);  // Né đòn
        CANONICAL_MAP.put(78, 5);  // Tăng né tránh cho chủ
        CANONICAL_MAP.put(84, 5);  // Né đòn (thú)
        CANONICAL_MAP.put(115, 5); // Né đòn

        // Chính xác cố định -> Option 10
        CANONICAL_MAP.put(18, 10);  // Chính xác
        CANONICAL_MAP.put(75, 10);  // Tăng chính xác cho chủ
        CANONICAL_MAP.put(86, 10);  // Chính xác (thú)
        CANONICAL_MAP.put(116, 10); // Chính xác

        // ==========================================
        // 4. CHÍ MẠNG & KHÁNG CHÍ MẠNG
        // ==========================================
        // Tỉ lệ chí mạng cố định -> Option 14
        CANONICAL_MAP.put(37, 14);  // (+8) Chí mạng
        CANONICAL_MAP.put(69, 14);  // Chí mạng
        CANONICAL_MAP.put(92, 14);  // Chí mạng
        CANONICAL_MAP.put(114, 14); // Chí mạng

        // % Tăng ST Chí mạng -> Option 39
        CANONICAL_MAP.put(67, 39);  // Tấn công khi đánh chí mạng %

        // % Kháng ST Chí mạng -> Option 121
        CANONICAL_MAP.put(46, 121); // (+16) Chịu ST khi bị chí mạng -#%
        CANONICAL_MAP.put(63, 121); // Giảm sát thương bởi người khác % (PvP)
        CANONICAL_MAP.put(79, 121); // Kháng ST chí mạng %

        // ==========================================
        // 5. KHÁNG NGUYÊN TỐ & KHÁNG TẤT CẢ
        // ==========================================
        // Kháng Hỏa cố định -> Option 2
        CANONICAL_MAP.put(11, 2); // Kháng hỏa
        CANONICAL_MAP.put(33, 2); // (+14) Kháng hỏa
        CANONICAL_MAP.put(70, 2); // Kháng hỏa
        CANONICAL_MAP.put(96, 2); // Kháng hỏa

        // Kháng Băng cố định -> Option 3 (Fix lỗi Option 12 bị nhầm)
        CANONICAL_MAP.put(12, 3); // Kháng băng (sửa lỗi kinh điển)
        CANONICAL_MAP.put(34, 3); // (+14) Kháng băng
        CANONICAL_MAP.put(71, 3); // Kháng băng
        CANONICAL_MAP.put(95, 3); // Kháng băng

        // Kháng Phong cố định -> Option 4
        CANONICAL_MAP.put(13, 4); // Kháng phong
        CANONICAL_MAP.put(35, 4); // (+14) Kháng phong
        CANONICAL_MAP.put(72, 4); // Kháng phong
        CANONICAL_MAP.put(97, 4); // Kháng phong

        // Kháng tất cả các hệ -> Option 20
        CANONICAL_MAP.put(36, 20);  // (+14) Kháng tất cả
        CANONICAL_MAP.put(81, 20);  // Kháng tất cả (thú)
        CANONICAL_MAP.put(118, 20); // Kháng tất cả

        // ==========================================
        // 6. PHÒNG THỦ, GIẢM TRỪ & PHẢN ĐÒN
        // ==========================================
        // Giảm trừ sát thương cố định (dameDown) -> Option 47
        CANONICAL_MAP.put(74, 47);  // Chịu sát thương cho chủ
        CANONICAL_MAP.put(80, 47);  // Giảm trừ sát thương
        CANONICAL_MAP.put(124, 47); // Giảm trừ sát thương

        // Phản đòn cận chiến -> Option 15
        CANONICAL_MAP.put(91, 15);  // Phản đòn
        CANONICAL_MAP.put(126, 15); // Phản đòn

        // Tốc độ di chuyển -> Option 16
        CANONICAL_MAP.put(93, 16);
    }

    /**
     * Tra cứu mã chuẩn Canonical ID cho một Option ID bất kỳ.
     */
    public static int getCanonicalId(int optionId) {
        return CANONICAL_MAP.getOrDefault(optionId, optionId);
    }

    /**
     * Kiểm tra xem mã Option có thuộc danh sách cần chuẩn hóa gộp hay không.
     */
    public static boolean isMergedOption(int optionId) {
        return CANONICAL_MAP.containsKey(optionId);
    }

    /**
     * Thuật toán Gộp & Cộng Dồn (Merge & Sum Aggregator).
     *
     * 1. Tra cứu Canonical ID của từng dòng.
     * 2. Nếu Canonical ID đã có trong danh sách -> cộng dồn param (existing.param += opt.param).
     * 3. Nếu chưa có -> thêm mới với mã Canonical ID và param hiện tại.
     * 4. Dùng LinkedHashMap để bảo toàn thứ tự tự nhiên của thuộc tính trên trang bị.
     *
     * @param rawOptions Danh sách option thô (có thể chứa mã phân mảnh / mã ẩn)
     * @return Danh sách option đã gộp tinh gọn và chuẩn hóa
     */
    public static ArrayList<ItemOption> mergeAndStandardize(List<ItemOption> rawOptions) {
        if (rawOptions == null || rawOptions.isEmpty()) {
            return rawOptions instanceof ArrayList ? (ArrayList<ItemOption>) rawOptions : new ArrayList<>();
        }

        Map<Integer, ItemOption> mergedMap = new LinkedHashMap<>();

        for (ItemOption opt : rawOptions) {
            if (opt == null) continue;
            int originalId = opt.optionTemplate.id;
            int canonicalId = CANONICAL_MAP.getOrDefault(originalId, originalId);

            if (mergedMap.containsKey(canonicalId)) {
                // Đã tồn tại -> CỘNG DỒN CHỈ SỐ VÀO DÒNG ĐÃ CÓ
                ItemOption existing = mergedMap.get(canonicalId);
                existing.param += opt.param;
            } else {
                // Chưa tồn tại -> ĐỔI THÀNH MÃ CHUẨN MỚI
                mergedMap.put(canonicalId, new ItemOption(canonicalId, opt.param));
            }
        }

        return new ArrayList<>(mergedMap.values());
    }
}
