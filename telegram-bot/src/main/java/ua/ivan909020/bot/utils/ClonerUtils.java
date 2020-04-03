package ua.ivan909020.bot.utils;

import com.rits.cloning.Cloner;

public final class ClonerUtils {

    private static final Cloner CLONER = new Cloner();

    private ClonerUtils() {
    }

    public static <T> T cloneObject(T object) {
        return CLONER.deepClone(object);
    }

}
