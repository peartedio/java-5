import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {
        String[] files = new String[]{"test.properties", "test2.properties"};
        for (String filename:
             files) {
            Injector injector = new Injector(filename);
            B b = new B();
            injector.inject(b);
            System.out.println(b.a.getValue());
        }
    }

}
