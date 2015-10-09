package nz.co.zzi.samplelogintdd.infrastructure.utils;

/**
 * Created by joao.gavazzi on 9/10/15.
 */
public class Utility {
    public static boolean isEmpty(final String text) {
        return text == null || text.isEmpty();
    }

    public static <T> T requireNonNull(final T obj) {
        if(obj == null) {
            throw new NullPointerException();
        }

        return obj;
    }
}
