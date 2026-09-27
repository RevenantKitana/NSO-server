package com.nsoz.server;

import com.nsoz.util.Log;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoMaintenance {

    public static void maintenance(int hours, int minutes, int seconds) {
        LocalDateTime localNow = LocalDateTime.now();
        ZoneId currentZone = ZoneId.of("Asia/Ho_Chi_Minh");
        ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
        ZonedDateTime zonedNext = zonedNow.withHour(hours).withMinute(minutes).withSecond(seconds);
        if (zonedNow.compareTo(zonedNext) > 0) {
            zonedNext = zonedNext.plusDays(1);
        }

        Duration duration = Duration.between(zonedNow, zonedNext);
        long initialDelay = duration.getSeconds();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Server.maintance();
                } catch (Throwable t) {
                    Log.logException("AutoMaintenance run error:", AutoMaintenance.class, t);
                } finally {
                    try {
                        String os = System.getProperty("os.name", "").toLowerCase();
                        if (os.contains("win")) {
                            File runBat = new File("run.bat");
                            if (runBat.exists()) {
                                Runtime.getRuntime().exec("cmd.exe /c start run.bat");
                            }
                        }
                    } catch (Throwable t) {
                        Log.logException("Restart execution error:", AutoMaintenance.class, t);
                    } finally {
                        System.out.println("Bảo trì hoàn tất. Đang tắt tiến trình để khởi động lại...");
                        System.exit(0);
                    }
                }
            }
        };

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(runnable, initialDelay, 24 * 60 * 60, TimeUnit.SECONDS);
        System.out.println("Tự động bảo trì " + hours + "h" + minutes);
    }
}
