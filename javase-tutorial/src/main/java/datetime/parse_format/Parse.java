package datetime.parse_format;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parse {
    public static void main(String[] args) {
//        if (args.length < 3) {
//            System.out.println("Usage: Parse <3-char month> <day> <4-digit year>");
//            throw new IllegalArgumentException();
//        }
//        String input = args[0] + ' ' + args[1] + ' ' + args[2];
//        String input = "Jun 20 2012";

        // 如果时区是中国
//        String input = "二月 20 2012";  // 错误。与格式 MMM d yyyy 不符
        String input = "2月 20 2012";   // 正确。符合 MMM d yyyy 格式

        try {
            // 使用默认时区如果是中国，而要解析的字符串是英文，则会报错
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d yyyy").withLocale(Locale.ENGLISH);

            // parse 是静态方法，而 format 是实例方法
            LocalDate date = LocalDate.parse(input, formatter);
            System.out.println(date);
        } catch (DateTimeParseException e) {
            System.err.printf("%s is not parsable.%n", input);
            throw e;    // Rethrow the exception.
        }
        // 'date' has been successfully parsed

    }
}
