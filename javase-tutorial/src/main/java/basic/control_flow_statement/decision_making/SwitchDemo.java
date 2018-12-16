package basic.control_flow_statement.decision_making;

public class SwitchDemo {
  public static void main(String[] args) {
    if (args.length != 1) {
      System.err.println("Usage: java SwitchDemo <month>");
      throw new IllegalArgumentException();
    }

    int month;
    try {
      month = Integer.parseInt(args[0]);
      if (month < 1 || month > 12) {
        System.err.println("Invalid month: " + args[0]);
        throw new IllegalArgumentException();
      }
    } catch (NumberFormatException e) {
      System.err.println("Invalid month: " + args[0]);
      throw new IllegalArgumentException();
    }

    String monthString;
    switch (month) {
      // default 语句不一定要放在最后。但逻辑不变。default总是处理例外情况。
      // 不过通常把default放在最后会比较好，会更自然及好的可读性。
      default:
        monthString = "Invalid month";
        break;
      case 1:
        monthString = "January";
        break;
      case 2:
        monthString = "February";
        break;
      case 3:
        monthString = "March";
        break;
      case 4:
        monthString = "April";
        break;
      case 5:
        monthString = "May";
        break;
      case 6:
        monthString = "June";
        break;
      case 7:
        monthString = "July";
        break;
      case 8:
        monthString = "August";
        break;
      case 9:
        monthString = "September";
        break;
      case 10:
        monthString = "October";
        break;
      case 11:
        monthString = "November";
        break;
      case 12:
        monthString = "December";
        break;

    }
    System.out.println(monthString);
  }
}
