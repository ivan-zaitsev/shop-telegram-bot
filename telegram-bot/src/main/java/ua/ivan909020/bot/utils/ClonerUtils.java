package ua.ivan909020.bot.utils;

import com.mchange.v2.ser.SerializableUtils;

import java.io.IOException;

public final class ClonerUtils {

    private ClonerUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T cloneObject(T object) {
        try {
            return (T) SerializableUtils.deepCopy(object);
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Failed to clone object", e);
        }
    }

}
