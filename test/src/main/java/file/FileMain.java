package file;

import com.sun.tools.attach.AttachNotSupportedException;
import org.springframework.core.ResolvableType;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class FileMain {
    private HashMap<Integer, List<String>> myMap;

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws NoSuchFieldException, IOException, AttachNotSupportedException {
        FileMain fileMain = new FileMain();
        fileMain.testReturn();
//        VirtualMachine virtualMachine = VirtualMachine.attach("8876");
//        for (Object key : virtualMachine.getSystemProperties().keySet()) {
//            System.out.println(key + "\t" + virtualMachine.getSystemProperties().get(key));
//        }
//        InputStream in = ((HotSpotVirtualMachine) virtualMachine).remoteDataDump();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//        in.close();
//        virtualMachine.detach();

//        System.out.println(System.getProperty("java.home"));
//        System.out.println(System.getProperty("catalina.home"));
//        Field field = ReflectionUtils.findField(FileMain.class, "myMap");
////        ResolvableType.forField(field);
//
//        System.out.println(field.getGenericType());
//
//        Thread.currentThread().getContextClassLoader();


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

    public void testReturn() {
        int i = 0;
        try {
            i++;
            System.out.println("22i:" + i);
//            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        i++;
        System.out.println("28--i:" + i);
//        testReturn();
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
