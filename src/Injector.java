import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Injector {

    private Map<String, String> map = new HashMap<String, String>();

    public Injector(String pathName) throws FileNotFoundException {
        List<String> lines = new BufferedReader(new InputStreamReader(new FileInputStream(pathName), StandardCharsets.UTF_8)).lines().collect(Collectors.toList());
        for (String line : lines) {
            int index = line.indexOf('=');
            map.put(line.substring(0, index), line.substring(index + 1));
        }
    }

    public <T> void inject(T value) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field[] fields = value.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                String className = field.getType().getName();
                Class<?> clazz =getClass().getClassLoader().loadClass(map.get(className));
                field.set(value, clazz.getConstructor().newInstance());
            }
        }
    }

}
