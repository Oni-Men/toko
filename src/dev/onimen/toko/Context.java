package dev.onimen.toko;

import java.util.HashMap;
import java.util.Map;

public class Context {

    public @interface ContextClass { }

    private static Context instance = null;

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private final Map<Class, Object> contextMap;

    private Context() {
        contextMap = new HashMap<>();
    }

    public <T> T get(Class<T> type) {
        return (T) contextMap.get(type);
    }

    public <T> void put(Class<T> type, T object) {
        if (contextMap.containsKey(type)) {
            throw new RuntimeException(String.format("Type \"%s\" has already been registered.", type.getCanonicalName()));
        }
        contextMap.put(type, object);
    }

}
