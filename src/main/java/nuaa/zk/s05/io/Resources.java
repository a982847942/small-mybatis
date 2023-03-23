package nuaa.zk.s05.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/21 20:29
 */
public class Resources {
    public static Reader getResourceAsReader(String resource) throws IOException{
        return new InputStreamReader(getResourceAsInputStream(resource));
    }
    public static InputStream getResourceAsInputStream(String resource) throws IOException {
        ClassLoader[] classLoaders = getClassLoader();
        for (ClassLoader classLoader : classLoaders) {
            InputStream inputStream = classLoader.getResourceAsStream(resource);
            if (inputStream != null){
                return inputStream;
            }
        }
        throw new IOException("Could not find resource " + resource);
    }

    public static ClassLoader[] getClassLoader(){
        return new ClassLoader[]{ClassLoader.getSystemClassLoader(),Thread.currentThread().getContextClassLoader()};
    }
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}
