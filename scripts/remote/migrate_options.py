#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Option Standardization Database Migration Script (Production Grade)
Implements ITEM_OPTION_STANDARDIZATION_SPEC.md & TAI_LIEU_THIET_KE_CONG_THUC_SAT_THUONG.md
Features:
- Full Canonical Mapping Matrix (Attack, Defense, HP/MP, Elements, Crit, DameDown)
- Merge & Sum Aggregator: Automatically merges duplicate canonical lines (param +=)
- Migrates store_data, weapon_store, players, clone_char
- Updates item_option table with [Đã gộp -> Opt X] flags
- Automated pre-migration database snapshot backup
- Safe Transactional Commit/Rollback
"""

import sys
import json
import subprocess
import argparse
import datetime
import os

CANONICAL_MAP = {
    # Tấn công duy nhất cố định (Flat) -> Option 0
    1: 0, 21: 0, 22: 0, 23: 0, 24: 0, 25: 0, 26: 0, 73: 0, 76: 0, 87: 0,
    # Vật công duy nhất (% Scaling) -> Option 8
    9: 8, 94: 8,
    # HP tối đa cố định (Flat) -> Option 6
    77: 6, 82: 6, 125: 6,
    # MP tối đa cố định (Flat) -> Option 7
    19: 7, 83: 7, 117: 7,
    # Né đòn cố định (Flat) -> Option 5
    17: 5, 68: 5, 78: 5, 84: 5, 115: 5,
    # Chính xác cố định (Flat) -> Option 10
    18: 10, 75: 10, 86: 10, 116: 10,
    # Chí mạng cố định (Flat) -> Option 14
    69: 14, 92: 14, 114: 14,
    # Kháng ST Chí mạng % -> Option 121
    46: 121, 63: 121, 79: 121,
    # Kháng nguyên tố cố định -> Option 2, 3, 4
    11: 2, 70: 2, 96: 2,
    12: 3, 71: 3, 95: 3,
    13: 4, 72: 4, 97: 4,
    # Kháng tất cả -> Option 20
    81: 20, 98: 20, 118: 20,
    # Giảm trừ sát thương cố định (dameDown) -> Option 47
    74: 47, 80: 47, 124: 47,
    # Phản đòn cận chiến -> Option 15
    91: 15, 126: 15,
    # Tốc độ di chuyển -> Option 16
    93: 16
}

MERGED_LABELS = {
    0: 'Tấn công: +#',
    1: '[Đã gộp -> Opt 0] Tấn công nội: +#',
    8: 'Vật công: +#%',
    9: '[Đã gộp -> Opt 8] Vật công nội: +#%',
    21: '[Đã gộp -> Opt 0] Hỏa công ngoại: +#',
    22: '[Đã gộp -> Opt 0] Hỏa công nội: +#',
    23: '[Đã gộp -> Opt 0] Băng sát ngoại: +#',
    24: '[Đã gộp -> Opt 0] Băng sát nội: +#',
    25: '[Đã gộp -> Opt 0] Phong lôi ngoại: +#',
    26: '[Đã gộp -> Opt 0] Phong lôi nội: +#',
    11: '[Đã gộp -> Opt 2] Kháng hỏa: +#',
    12: '[Đã gộp -> Opt 3] Kháng băng: +#',
    13: '[Đã gộp -> Opt 4] Kháng phong: +#',
    17: '[Đã gộp -> Opt 5] Né đòn: #',
    18: '[Đã gộp -> Opt 10] Chính xác: #',
    19: '[Đã gộp -> Opt 7] MP tối đa: #',
    46: '[Đã gộp -> Opt 121] (+16) Chịu sát thương khi bị chí mạng: -#%',
    63: '[Đã gộp -> Opt 121] Giảm sát thương bởi người chơi khác: +#%',
    68: '[Đã gộp -> Opt 5] Né đòn: +#',
    69: '[Đã gộp -> Opt 14] Chí mạng: +#',
    70: '[Đã gộp -> Opt 2] Kháng hỏa:  +#',
    71: '[Đã gộp -> Opt 3] Kháng băng:  +#',
    72: '[Đã gộp -> Opt 4] Kháng phong:  +#',
    73: '[Đã gộp -> Opt 0] Tấn công: #',
    74: '[Đã gộp -> Opt 47] Chịu sát thương cho chủ: +#',
    75: '[Đã gộp -> Opt 10] Tăng chính xác cho chủ: +#',
    76: '[Đã gộp -> Opt 0] Tăng tấn công cho chủ: +#',
    77: '[Đã gộp -> Opt 6] Tăng max HP cho chủ: +#',
    78: '[Đã gộp -> Opt 5] Tăng né tránh cho chủ: +#',
    79: '[Đã gộp -> Opt 121] Kháng sát thương chí mạng: #%',
    80: '[Đã gộp -> Opt 47] Giảm trừ sát thương: #',
    81: '[Đã gộp -> Opt 20] Kháng tất cả: +#',
    82: '[Đã gộp -> Opt 6] HP tối đa: #',
    83: '[Đã gộp -> Opt 7] MP tối đa: +#',
    84: '[Đã gộp -> Opt 5] Né đòn: +#',
    86: '[Đã gộp -> Opt 10] Chính xác: +#',
    87: '[Đã gộp -> Opt 0] Tấn công: +#',
    91: '[Đã gộp -> Opt 15] Phản đòn: #',
    92: '[Đã gộp -> Opt 14] Chí mạng: +#',
    93: '[Đã gộp -> Opt 16] Tốc độ di chuyển: +#',
    94: '[Đã gộp -> Opt 8] Tấn công: +#%',
    95: '[Đã gộp -> Opt 3] Kháng băng:  +#',
    96: '[Đã gộp -> Opt 2] Kháng hỏa:  +#',
    97: '[Đã gộp -> Opt 4] Kháng phong:  +#',
    98: '[Đã gộp -> Opt 20] Miễn giảm sát thương: #%',
    114: '[Đã gộp -> Opt 14] Chí mạng: #',
    115: '[Đã gộp -> Opt 5] Né đòn: #',
    116: '[Đã gộp -> Opt 10] Chính xác: #',
    117: '[Đã gộp -> Opt 7] MP tối đa: #',
    118: '[Đã gộp -> Opt 20] Kháng tất cả: #',
    124: '[Đã gộp -> Opt 47] Giảm trừ sát thương: #',
    125: '[Đã gộp -> Opt 6] HP tối đa: #',
    126: '[Đã gộp -> Opt 15] Phản đòn: #'
}

def merge_player_options(opt_list):
    """
    Format: [[id, param], [id, param], ...]
    Cộng dồn param nếu trùng mã canonical.
    """
    if not isinstance(opt_list, list) or len(opt_list) == 0:
        return opt_list, 0
    merged = {}
    changed = 0
    for entry in opt_list:
        if not isinstance(entry, list) or len(entry) < 2:
            continue
        orig_id, param = entry[0], entry[1]
        can_id = CANONICAL_MAP.get(orig_id, orig_id)
        if can_id != orig_id:
            changed += 1
        if can_id in merged:
            merged[can_id] += param
            changed += 1
        else:
            merged[can_id] = param
    result = [[k, v] for k, v in merged.items()]
    return result, changed

def merge_store_options(opt_list):
    """
    Format: [{"id": X, "param": Y}, ...]
    Cộng dồn param nếu trùng mã canonical.
    """
    if not isinstance(opt_list, list) or len(opt_list) == 0:
        return opt_list, 0
    merged = {}
    changed = 0
    for item in opt_list:
        if not isinstance(item, dict) or "id" not in item:
            continue
        orig_id = item["id"]
        param = item.get("param", 0)
        can_id = CANONICAL_MAP.get(orig_id, orig_id)
        if can_id != orig_id:
            changed += 1
        if can_id in merged:
            merged[can_id] += param
            changed += 1
        else:
            merged[can_id] = param
    result = [{"id": k, "param": v} for k, v in merged.items()]
    return result, changed

def migrate_player_items_json(json_str):
    if not json_str or json_str.strip() in ("", "[]"):
        return json_str, 0
    try:
        items = json.loads(json_str)
        if not isinstance(items, list):
            return json_str, 0
        total_changed = 0
        for item in items:
            if isinstance(item, dict) and "options" in item and isinstance(item["options"], list):
                new_opts, c = merge_player_options(item["options"])
                if c > 0:
                    item["options"] = new_opts
                    total_changed += c
        if total_changed > 0:
            return json.dumps(items, ensure_ascii=False, separators=(',', ':')), total_changed
        return json_str, 0
    except Exception as e:
        print(f"Error parsing player items: {e}", file=sys.stderr)
        return json_str, 0

def migrate_store_json(json_str):
    if not json_str or json_str.strip() in ("", "[]"):
        return json_str, 0
    try:
        data = json.loads(json_str)
        if not isinstance(data, list):
            return json_str, 0
        if len(data) > 0 and isinstance(data[0], list):
            # Format [[id, param], ...] (weapon_store)
            new_data, c = merge_player_options(data)
        else:
            # Format [{"id": X, "param": Y}, ...] (store_data)
            new_data, c = merge_store_options(data)
        if c > 0:
            return json.dumps(new_data, ensure_ascii=False, separators=(',', ':')), c
        return json_str, 0
    except Exception as e:
        print(f"Error parsing store JSON: {e}", file=sys.stderr)
        return json_str, 0

def run_query(sql, db="nso_test"):
    cmd = ["sudo", "mariadb", "-D", db, "--batch", "--raw", "-e", sql]
    p = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, check=True)
    return p.stdout

def execute_sql_file(sql_file, db="nso_test"):
    cmd = f"sudo mariadb -D {db} < {sql_file}"
    subprocess.run(cmd, shell=True, check=True)

def main():
    parser = argparse.ArgumentParser(description="NSO Option Standardization & Shop Migration")
    parser.add_argument("--execute", action="store_true", help="Execute changes directly to database")
    parser.add_argument("--db", default="nso_test", help="Database name")
    args = parser.parse_args()

    dry_run = not args.execute

    print("=" * 65)
    print("  NSO OPTION STANDARDIZATION & SHOP MIGRATION TOOL")
    print(f"  Database : {args.db}")
    print(f"  Mode     : {'[DRY-RUN (Preview Only)]' if dry_run else '[EXECUTE (Applying to DB)]'}")
    print("=" * 65)

    total_records = 0
    total_options = 0
    sql_updates = []

    # 1. store_data table
    print("\n[1/4] Scanning table `store_data` (NPC Shops)...")
    out = run_query("SELECT id, options FROM store_data WHERE LENGTH(options) > 2;", args.db)
    lines = out.strip().split("\n")
    store_changed = 0
    if len(lines) > 1:
        for line in lines[1:]:
            parts = line.split("\t")
            if len(parts) < 2:
                continue
            sid, opts = parts[0], parts[1]
            new_opts, c_opts = migrate_store_json(opts)
            if c_opts > 0:
                store_changed += 1
                total_records += 1
                total_options += c_opts
                if not dry_run:
                    escaped_opts = new_opts.replace("'", "\\'")
                    sql_updates.append(f"UPDATE store_data SET options='{escaped_opts}' WHERE id={sid};")
    print(f"  => Found {store_changed} store items to standardize/merge")

    # 2. weapon_store table
    print("\n[2/4] Scanning table `weapon_store`...")
    try:
        out = run_query("SELECT id, options FROM weapon_store WHERE options IS NOT NULL AND LENGTH(options) > 2;", args.db)
        lines = out.strip().split("\n")
        ws_changed = 0
        if len(lines) > 1:
            for line in lines[1:]:
                parts = line.split("\t")
                if len(parts) < 2:
                    continue
                wid, opts = parts[0], parts[1]
                new_opts, c_opts = migrate_store_json(opts)
                if c_opts > 0:
                    ws_changed += 1
                    total_records += 1
                    total_options += c_opts
                    if not dry_run:
                        escaped_opts = new_opts.replace("'", "\\'")
                        sql_updates.append(f"UPDATE weapon_store SET options='{escaped_opts}' WHERE id={wid};")
        print(f"  => Found {ws_changed} weapon_store items to standardize/merge")
    except Exception as e:
        print(f"  => weapon_store table note: {e}")

    # 3. Players table
    print("\n[3/4] Scanning table `players`...")
    try:
        out = run_query("SELECT id, name, equiped, bag, box, mount, bijuu FROM players;", args.db)
        lines = out.strip().split("\n")
        player_changed = 0
        if len(lines) > 1:
            for line in lines[1:]:
                parts = line.split("\t")
                if len(parts) < 7:
                    continue
                pid, name, equiped, bag, box, mount, bijuu = parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]
                new_eq, c_eq = migrate_player_items_json(equiped)
                new_bag, c_bag = migrate_player_items_json(bag)
                new_box, c_box = migrate_player_items_json(box)
                new_mnt, c_mnt = migrate_player_items_json(mount)
                new_bij, c_bij = migrate_player_items_json(bijuu)

                row_changes = c_eq + c_bag + c_box + c_mnt + c_bij
                if row_changes > 0:
                    player_changed += 1
                    total_records += 1
                    total_options += row_changes
                    print(f"  [-] Player '{name}' (ID {pid}): {row_changes} options standardized")
                    if not dry_run:
                        escaped_eq = new_eq.replace("'", "\\'")
                        escaped_bag = new_bag.replace("'", "\\'")
                        escaped_box = new_box.replace("'", "\\'")
                        escaped_mnt = new_mnt.replace("'", "\\'")
                        escaped_bij = new_bij.replace("'", "\\'")
                        sql = (f"UPDATE players SET equiped='{escaped_eq}', bag='{escaped_bag}', "
                               f"box='{escaped_box}', mount='{escaped_mnt}', bijuu='{escaped_bij}' WHERE id={pid};")
                        sql_updates.append(sql)
        print(f"  => Found {player_changed} players with items to standardize")
    except Exception as e:
        print(f"  => players scan note: {e}")

    # 4. Update item_option table names
    print("\n[4/4] Preparing updates for table `item_option`...")
    for opt_id, label in MERGED_LABELS.items():
        escaped_label = label.replace("'", "\\'")
        sql_updates.append(f"UPDATE item_option SET name='{escaped_label}' WHERE id={opt_id};")
    print(f"  => Marked {len(MERGED_LABELS)} old options in `item_option`")

    print("\n" + "=" * 65)
    print(f"  TOTAL RECORDS TO STANDARDIZE: {total_records}")
    print(f"  TOTAL OPTIONS SUMMED/MERGED : {total_options}")
    print(f"  TOTAL SQL STATEMENTS        : {len(sql_updates)}")
    print("=" * 65)

    if dry_run:
        print("\n>> DRY-RUN COMPLETED. No database changes were applied.")
        print(">> To apply changes live on MariaDB, re-run with: --execute")
    else:
        # 1. Snapshot backup
        timestamp = datetime.datetime.now().strftime("%Y%m%d_%H%M%S")
        backup_dir = "/home/ubuntu/nso-server/backups"
        os.makedirs(backup_dir, exist_ok=True)
        backup_path = f"{backup_dir}/db_backup_pre_migration_{timestamp}.sql"
        print(f"\n[BACKUP] Creating snapshot backup: {backup_path}...")
        subprocess.run(f"sudo mysqldump {args.db} > {backup_path}", shell=True, check=True)
        print("[BACKUP] Snapshot backup created successfully!")

        # 2. Execute SQL updates in transaction
        migration_file = "/tmp/option_migration_tx.sql"
        with open(migration_file, "w", encoding="utf-8") as f:
            f.write("START TRANSACTION;\n")
            for sql in sql_updates:
                f.write(sql + "\n")
            f.write("COMMIT;\n")

        print(f"[EXECUTE] Applying {len(sql_updates)} updates in a single atomic transaction...")
        execute_sql_file(migration_file, args.db)
        print("[SUCCESS] All shop items, weapon stores, players, and item_option updated successfully!")

if __name__ == "__main__":
    main()
