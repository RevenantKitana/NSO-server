#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Option Standardization Database Migration Script
Implements ITEM_OPTION_STANDARDIZATION_SPEC.md (Section 4)
Safely parses and updates nested JSON options in players, clone_char, store_data, weapon_store.
"""

import sys
import json
import subprocess
import argparse

REPLACEMENT_MAP = {
    # Né đòn -> 5
    17: 5, 68: 5, 78: 5, 84: 5, 115: 5,
    # HP tối đa -> 6
    77: 6, 82: 6, 125: 6,
    # MP tối đa -> 7
    19: 7, 83: 7, 117: 7,
    # Chính xác -> 10
    18: 10, 75: 10, 86: 10, 116: 10,
    # Chí mạng -> 14
    69: 14, 92: 14, 114: 14,
    # Phản đòn cận chiến -> 15
    91: 15, 126: 15,
    # Kháng tất cả -> 20
    81: 20, 118: 20,
    # Kháng hỏa -> 2
    11: 2, 70: 2, 96: 2,
    # Kháng băng -> 3
    12: 3, 71: 3, 95: 3,
    # Kháng phong -> 4
    13: 4, 72: 4, 97: 4,
    # Giảm trừ sát thương -> 47
    74: 47, 80: 47, 124: 47,
    # Kháng STCM -> 121
    79: 121
}

def migrate_item_array(json_str):
    if not json_str or json_str.strip() in ("", "[]"):
        return json_str, 0
    try:
        items = json.loads(json_str)
        if not isinstance(items, list):
            return json_str, 0
        replaced = 0
        for item in items:
            if isinstance(item, dict) and "options" in item and isinstance(item["options"], list):
                for opt in item["options"]:
                    if isinstance(opt, list) and len(opt) >= 2 and isinstance(opt[0], int):
                        if opt[0] in REPLACEMENT_MAP:
                            opt[0] = REPLACEMENT_MAP[opt[0]]
                            replaced += 1
        if replaced > 0:
            return json.dumps(items, ensure_ascii=False, separators=(',', ':')), replaced
        return json_str, 0
    except Exception as e:
        print(f"Error parsing item array: {e}", file=sys.stderr)
        return json_str, 0

def migrate_store_options(json_str):
    if not json_str or json_str.strip() in ("", "[]"):
        return json_str, 0
    try:
        data = json.loads(json_str)
        if not isinstance(data, list):
            return json_str, 0
        replaced = 0
        for elem in data:
            if isinstance(elem, dict) and "id" in elem and isinstance(elem["id"], int):
                if elem["id"] in REPLACEMENT_MAP:
                    elem["id"] = REPLACEMENT_MAP[elem["id"]]
                    replaced += 1
            elif isinstance(elem, list) and len(elem) >= 2 and isinstance(elem[0], int):
                if elem[0] in REPLACEMENT_MAP:
                    elem[0] = REPLACEMENT_MAP[elem[0]]
                    replaced += 1
        if replaced > 0:
            return json.dumps(data, ensure_ascii=False, separators=(',', ':')), replaced
        return json_str, 0
    except Exception as e:
        print(f"Error parsing store options: {e}", file=sys.stderr)
        return json_str, 0

def run_query(sql, db="nso_test"):
    cmd = ["sudo", "mariadb", "-D", db, "--batch", "--raw", "-e", sql]
    p = subprocess.run(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True, check=True)
    return p.stdout

def execute_sql_file(sql_file, db="nso_test"):
    cmd = f"sudo mariadb -D {db} < {sql_file}"
    subprocess.run(cmd, shell=True, check=True)

def main():
    parser = argparse.ArgumentParser(description="NSO Option Standardization Migration Script")
    parser.add_argument("--execute", action="store_true", help="Execute changes directly to database")
    parser.add_argument("--db", default="nso_test", help="Database name")
    args = parser.parse_args()

    dry_run = not args.execute

    print("=" * 60)
    print("  NSO OPTION STANDARDIZATION MIGRATION SCRIPT")
    print(f"  Database : {args.db}")
    print(f"  Mode     : {'[DRY-RUN (Preview Only)]' if dry_run else '[EXECUTE (Writing to DB)]'}")
    print("=" * 60)

    total_records = 0
    total_options = 0
    sql_updates = []

    # 1. Players table
    print("\n[1/4] Scanning table `players`...")
    out = run_query("SELECT id, name, equiped, bag, box, mount, bijuu FROM players;", args.db)
    lines = out.strip().split("\n")
    if len(lines) > 1:
        headers = lines[0].split("\t")
        for line in lines[1:]:
            parts = line.split("\t")
            if len(parts) < 7:
                continue
            pid, name, equiped, bag, box, mount, bijuu = parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]
            
            new_eq, c_eq = migrate_item_array(equiped)
            new_bag, c_bag = migrate_item_array(bag)
            new_box, c_box = migrate_item_array(box)
            new_mnt, c_mnt = migrate_item_array(mount)
            new_bij, c_bij = migrate_item_array(bijuu)

            row_changes = c_eq + c_bag + c_box + c_mnt + c_bij
            if row_changes > 0:
                total_records += 1
                total_options += row_changes
                print(f"  [-] Player '{name}' (ID {pid}): {row_changes} options standardized")
                if not dry_run:
                    # Escape quotes for SQL string literal
                    escaped_eq = new_eq.replace("'", "\\'")
                    escaped_bag = new_bag.replace("'", "\\'")
                    escaped_box = new_box.replace("'", "\\'")
                    escaped_mnt = new_mnt.replace("'", "\\'")
                    escaped_bij = new_bij.replace("'", "\\'")
                    sql = (f"UPDATE players SET equiped='{escaped_eq}', bag='{escaped_bag}', "
                           f"box='{escaped_box}', mount='{escaped_mnt}', bijuu='{escaped_bij}' WHERE id={pid};")
                    sql_updates.append(sql)

    # 2. Clone char table
    print("\n[2/4] Scanning table `clone_char`...")
    try:
        out = run_query("SELECT id, equiped, fashion, bijuu, mount FROM clone_char;", args.db)
        lines = out.strip().split("\n")
        if len(lines) > 1:
            for line in lines[1:]:
                parts = line.split("\t")
                if len(parts) < 5:
                    continue
                cid, equiped, fashion, bijuu, mount = parts[0], parts[1], parts[2], parts[3], parts[4]
                new_eq, c_eq = migrate_item_array(equiped)
                new_fas, c_fas = migrate_item_array(fashion)
                new_bij, c_bij = migrate_item_array(bijuu)
                new_mnt, c_mnt = migrate_item_array(mount)
                row_changes = c_eq + c_fas + c_bij + c_mnt
                if row_changes > 0:
                    total_records += 1
                    total_options += row_changes
                    print(f"  [-] Clone Char ID {cid}: {row_changes} options standardized")
                    if not dry_run:
                        escaped_eq = new_eq.replace("'", "\\'")
                        escaped_fas = new_fas.replace("'", "\\'")
                        escaped_bij = new_bij.replace("'", "\\'")
                        escaped_mnt = new_mnt.replace("'", "\\'")
                        sql = (f"UPDATE clone_char SET equiped='{escaped_eq}', fashion='{escaped_fas}', "
                               f"bijuu='{escaped_bij}', mount='{escaped_mnt}' WHERE id={cid};")
                        sql_updates.append(sql)
    except Exception as e:
        print(f"  Note: clone_char check skipped or empty: {e}")

    # 3. Store data table
    print("\n[3/4] Scanning table `store_data`...")
    out = run_query("SELECT id, options FROM store_data WHERE LENGTH(options) > 2;", args.db)
    lines = out.strip().split("\n")
    store_changed = 0
    if len(lines) > 1:
        for line in lines[1:]:
            parts = line.split("\t")
            if len(parts) < 2:
                continue
            sid, opts = parts[0], parts[1]
            new_opts, c_opts = migrate_store_options(opts)
            if c_opts > 0:
                store_changed += 1
                total_records += 1
                total_options += c_opts
                if not dry_run:
                    escaped_opts = new_opts.replace("'", "\\'")
                    sql = f"UPDATE store_data SET options='{escaped_opts}' WHERE id={sid};"
                    sql_updates.append(sql)
    print(f"  => Found {store_changed} store items to standardize")

    # 4. Weapon store table
    print("\n[4/4] Scanning table `weapon_store`...")
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
                new_opts, c_opts = migrate_store_options(opts)
                if c_opts > 0:
                    ws_changed += 1
                    total_records += 1
                    total_options += c_opts
                    if not dry_run:
                        escaped_opts = new_opts.replace("'", "\\'")
                        sql = f"UPDATE weapon_store SET options='{escaped_opts}' WHERE id={wid};"
                        sql_updates.append(sql)
        print(f"  => Found {ws_changed} weapon_store items to standardize")
    except Exception:
        print("  => weapon_store table not present or empty")

    print("\n" + "=" * 60)
    print(f"  TOTAL RECORDS TO UPDATE : {total_records}")
    print(f"  TOTAL OPTIONS STANDARDIZED: {total_options}")
    print("=" * 60)

    if dry_run:
        print("\n>> DRY-RUN COMPLETE. No database changes were applied.")
        print(">> To apply changes, re-run with: python3 migrate_options.py --execute")
    else:
        if sql_updates:
            print(f"\n>> Applying {len(sql_updates)} updates within transaction...")
            migration_file = "/tmp/option_migration_tx.sql"
            with open(migration_file, "w", encoding="utf-8") as f:
                f.write("START TRANSACTION;\n")
                for sql in sql_updates:
                    f.write(sql + "\n")
                f.write("COMMIT;\n")
            execute_sql_file(migration_file, args.db)
            print(">> TRANSACTION COMMITTED SUCCESSFULLY!")
        else:
            print(">> All items already canonical. Nothing to update.")

if __name__ == "__main__":
    main()
