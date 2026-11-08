package com.nsoz2.event.bus;

import com.nsoz2.util.Log;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Domain Event Bus nhẹ, bất đồng bộ/đồng bộ, thread-safe theo Observer Pattern
 * Đặc tả tại Spec 04 (Mục 4.2: Tách rời Hệ thống Nhiệm vụ khỏi Combat & Inventory)
 */
public final class EventBus {

    private static final Map<Class<?>, List<Consumer<Object>>> listeners = new ConcurrentHashMap<>();

    private EventBus() {}

    /**
     * Đăng ký lắng nghe sự kiện
     */
    @SuppressWarnings("unchecked")
    public static <T> void subscribe(Class<T> eventClass, Consumer<T> consumer) {
        listeners.computeIfAbsent(eventClass, k -> new CopyOnWriteArrayList<>())
                .add((Consumer<Object>) consumer);
    }

    /**
     * Bắn Domain Event đến các Listener đã đăng ký
     */
    public static void publish(Object event) {
        if (event == null) {
            return;
        }
        List<Consumer<Object>> list = listeners.get(event.getClass());
        if (list != null && !list.isEmpty()) {
            for (Consumer<Object> consumer : list) {
                try {
                    consumer.accept(event);
                } catch (Exception ex) {
                    Log.logException("Error handling event: " + event.getClass().getSimpleName(), EventBus.class, ex);
                }
            }
        }
    }

    /**
     * Xóa toàn bộ listeners (phục vụ reset/reload)
     */
    public static void clear() {
        listeners.clear();
    }
}

