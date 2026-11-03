import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestClientPacket {

    public static Object createMsg(byte cmd, byte[] data) throws Exception {
        Class<?> ehClass = Class.forName("eh");
        Object msg = ehClass.getConstructor().newInstance();

        Field cField = ehClass.getField("c");
        cField.set(msg, cmd);

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bais);

        Field eField = ehClass.getDeclaredField("e");
        eField.setAccessible(true);
        eField.set(msg, bais);

        Field aField = ehClass.getDeclaredField("a");
        aField.setAccessible(true);
        aField.set(msg, dis);

        return msg;
    }

    public static void main(String[] args) {
        try {
            System.out.println("Initializing MicroEmulator Headless runtime...");
            new org.microemu.app.Headless();

            Class<?> cfClass = Class.forName("cf");
            Object cfInstance = cfClass.getMethod("d").invoke(null);
            Method handleMethod = cfClass.getMethod("a", Class.forName("eh"));

            System.out.println("=== 1. Testing OPEN_UI_BOX (31) ===");
            byte[] boxData;
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream ds = new DataOutputStream(baos);
                ds.writeInt(0);
                ds.writeByte(30);
                for (int i = 0; i < 30; i++) ds.writeShort(-1);
                ds.flush();
                boxData = baos.toByteArray();
            }
            try {
                handleMethod.invoke(cfInstance, createMsg((byte) 31, boxData));
                System.out.println("[PASS] OPEN_UI_BOX handled successfully.");
            } catch (Throwable t) {
                System.out.println("[FAIL] OPEN_UI_BOX threw exception:");
                t.getCause().printStackTrace(System.out);
            }

            System.out.println("\n=== 2. Testing ME_LOAD_ALL (-30, sub -127) ===");
            byte[] loadAllData;
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream ds = new DataOutputStream(baos);
                ds.writeByte(-127); // CMD_ME_LOAD_ALL
                ds.writeInt(1); // charId
                ds.writeUTF(""); // Clan
                ds.writeByte(0); // Task ID
                ds.writeByte(1); // Gender
                ds.writeShort(14); // Head
                ds.writeByte(7); // Speed
                ds.writeUTF("admin_v2"); // Name
                ds.writeByte(0); // PK
                ds.writeByte(0); // Type PK
                ds.writeInt(100); // maxHp
                ds.writeInt(100); // hp
                ds.writeInt(50); // maxMp
                ds.writeInt(50); // mp
                ds.writeLong(5000); // exp
                ds.writeLong(0); // exp down
                ds.writeShort(0); // eff5hp
                ds.writeShort(0); // eff5mp
                ds.writeByte(0); // classId
                ds.writeShort(0); // potPoint
                ds.writeShort(15); // str
                ds.writeShort(5); // agi
                ds.writeInt(5); // vit
                ds.writeInt(5); // spi
                ds.writeShort(0); // skillPoint
                ds.writeByte(0); // skills count
                ds.writeInt(1000000); // xu
                ds.writeInt(500000); // yen
                ds.writeInt(0); // luong

                // Bag 30 slots empty
                ds.writeByte(30);
                for (int i = 0; i < 30; i++) ds.writeShort(-1);

                // Equip 16 slots empty
                for (int i = 0; i < 16; i++) ds.writeShort(-1);

                // Trailer
                ds.writeBoolean(true); // isHuman
                ds.writeBoolean(false); // isNhanBan
                ds.writeShort(14); // head
                ds.writeShort(-1); // weapon
                ds.writeShort(-1); // body
                ds.writeShort(-1); // leg

                // thoiTrang 10 slots
                for (int i = 0; i < 10; i++) ds.writeShort(-1);

                // fashion 16 slots
                for (int i = 0; i < 16; i++) ds.writeShort(-1);

                ds.flush();
                loadAllData = baos.toByteArray();
            }
            try {
                handleMethod.invoke(cfInstance, createMsg((byte) -30, loadAllData));
                System.out.println("[PASS] ME_LOAD_ALL handled successfully.");
            } catch (Throwable t) {
                System.out.println("[FAIL] ME_LOAD_ALL threw exception:");
                t.getCause().printStackTrace(System.out);
            }

            System.out.println("\n=== 3. Testing ME_LOAD_ACTIVE (-28, sub -106) ===");
            byte[] activeData;
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream ds = new DataOutputStream(baos);
                ds.writeByte(-106);
                ds.writeByte(0);
                ds.flush();
                activeData = baos.toByteArray();
            }
            try {
                handleMethod.invoke(cfInstance, createMsg((byte) -28, activeData));
                System.out.println("[PASS] ME_LOAD_ACTIVE handled successfully.");
            } catch (Throwable t) {
                System.out.println("[FAIL] ME_LOAD_ACTIVE threw exception:");
                t.getCause().printStackTrace(System.out);
            }

            System.out.println("\n=== 4. Testing MAP_INFO (-18) with 3 monsters ===");
            byte[] mapInfoData;
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream ds = new DataOutputStream(baos);
                ds.writeByte(22); // mapId
                ds.writeByte(2);  // tileId
                ds.writeByte(0);  // bgId
                ds.writeByte(0);  // type
                ds.writeUTF("Làng Tone"); // name
                ds.writeByte(0);  // zoneId
                ds.writeShort(1741); // x
                ds.writeShort(264);  // y

                // Waypoints (2 waypoints from DB)
                ds.writeByte(2);
                // wp 1
                ds.writeShort(0); ds.writeShort(144); ds.writeShort(24); ds.writeShort(168);
                // wp 2
                ds.writeShort(2784); ds.writeShort(0); ds.writeShort(2832); ds.writeShort(24);

                // Monsters (3 snails templateId=0)
                ds.writeByte(3);
                for (int i = 0; i < 3; i++) {
                    ds.writeBoolean(false); // isDisable
                    ds.writeBoolean(false); // isDontMove
                    ds.writeBoolean(false); // isFire
                    ds.writeBoolean(false); // isIce
                    ds.writeBoolean(false); // isWind
                    ds.writeByte(0); // templateId
                    ds.writeByte(0); // sys
                    ds.writeInt(40); // hp
                    ds.writeByte(1); // level
                    ds.writeInt(40); // maxHp
                    ds.writeShort(1900 + i * 20); // x
                    ds.writeShort(240); // y
                    ds.writeByte(0); // status
                    ds.writeByte(0); // levelBoss
                    ds.writeBoolean(false); // isBoss
                }

                // Bu nhin
                ds.writeByte(0);
                // npcs
                ds.writeByte(0);
                // items on ground
                ds.writeByte(0);

                // map name & location stand
                ds.writeUTF("Làng Tone");
                ds.writeByte(0);

                ds.flush();
                mapInfoData = baos.toByteArray();
            }
            try {
                handleMethod.invoke(cfInstance, createMsg((byte) -18, mapInfoData));
                System.out.println("[PASS] MAP_INFO handled successfully.");
            } catch (Throwable t) {
                System.out.println("[FAIL] MAP_INFO threw exception:");
                t.getCause().printStackTrace(System.out);
            }

        } catch (Throwable t) {
            t.printStackTrace(System.out);
        }
    }
}
