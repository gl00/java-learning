package other;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        // 直接输出中文没乱码
        System.out.println("中文");


        // 测试 Intellij IDEA 使用 JDK 11 控制台输出的异常信息中的中文乱码
        // 编译选项加上 -encoding utf-8 也没用
        // 由于文件不存在，此语句会抛异常
        // Exception in thread "main" java.io.FileNotFoundException: aabc (ϵͳÕҲ»µ½ָ¶)
        FileInputStream in = new FileInputStream("aabc");
    }
}
