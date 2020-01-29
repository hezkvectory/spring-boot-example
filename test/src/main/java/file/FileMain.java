package file;

import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public class FileMain {
    private HashMap<Integer, List<String>> myMap;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws NoSuchFieldException {
        Field field = ReflectionUtils.findField(FileMain.class, "myMap");
//        ResolvableType.forField(field);

        System.out.println(field.getGenericType());

        Thread.currentThread().getContextClassLoader();


//        Method exampleMethod = ReflectionUtils.findMethod(FileMain.class, "example", String.class);
//        ResolvableType exampleMethodReturn = ResolvableType.forMethodReturnType(exampleMethod);
//        System.out.println(exampleMethod.getGenericParameterTypes()[0].getTypeName());
//
//        System.out.println(exampleMethod.getParameterTypes());

//        ResolvableType resolvableType =  ResolvableType.forClass(FileMain.class);
//        System.out.println(resolvableType.getType());
//        FileMain fileMain = new FileMain();
//        fileMain.example();
//        Path p = Paths.get("test", "src", "main", "java", "file", "FileMain.java");
//        System.out.println(p.toAbsolutePath());
//        System.out.println(p.getFileName());
//        List<String> lines = Files.readAllLines(p);
//        for (String line : lines) {
//            System.out.println(line);
//        }
//        Files.createDirectory(p);
    }

    public String example(String name) throws NoSuchFieldException {
        ResolvableType t = ResolvableType.forField(getClass().getDeclaredField("myMap"));
        System.out.println(t.getSuperType());
        System.out.println(t.asMap());
        System.out.println(t.getGeneric(0).resolve());
        System.out.println(t.getGeneric(1).resolve());
        System.out.println(t.getGeneric(1));
        System.out.println(t.resolveGeneric(1, 0));
        t.getSuperType(); // AbstractMap<Integer, List<String>>
        t.asMap(); // Map<Integer, List<String>>
        t.getGeneric(0).resolve(); // Integer
        t.getGeneric(1).resolve(); // List
        t.getGeneric(1); // List<String>
        t.resolveGeneric(1, 0); // String

        return "";
    }
}
