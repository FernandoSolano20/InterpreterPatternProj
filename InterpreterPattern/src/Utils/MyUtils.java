
package Utils;

import java.lang.reflect.Method;

/**
 *
 * @author Hugo
 */
public class MyUtils {

    public static Method getMethodByName(String name, Method[] methods) {
        for (Method m : methods){
            if (m.getName().equals(name)){
                return m;
            }
        }
        return null;
    }
}
