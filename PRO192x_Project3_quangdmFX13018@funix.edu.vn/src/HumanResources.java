import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class where the main logic of this program is executed
 * @author QuangKR
 */
public class HumanResources {
  private final static DepartmentList departments = new DepartmentList();
  private final static StaffList staffs = new StaffList();
  private final static Scanner SC = new Scanner(System.in);

  public static void main(String[] args) {

    departments.add(new Department("Developers"));
    departments.add(new Department("Designers"));
    departments.add(new Department("Marketing team"));
    departments.add(new Department("Human resources"));

    staffs.add(new Employee("Quang", 26, "2010-01-01", departments.getDepartmentByName("Developers").get(0).getId(), 5, 10));
    staffs.add(new Employee("Quang", 25, "2016-01-01", departments.getDepartmentByName("Developers").get(0).getId(), 5, 10));
    staffs.add(new Employee("Nhung", 25, "2012-01-01", departments.getDepartmentByName("Designers").get(0).getId(), 4, 13));
    staffs.add(new Manager("Tam", 18, "2018-01-01", departments.getDepartmentByName("Designers").get(0).getId(), 4, Manager.ManagerTitles.BUSINESS_LEADER));

    List<Department> deptQuery = departments.getAll();
    System.out.println(deptQuery);
    departments.remove(departments.getDepartmentById("D-002").get());
    System.out.println(deptQuery);
    System.out.println(departments.getAll());


    List<Staff> staffQuery = staffs.getStaffByName("Quang");
    staffQuery.forEach(s -> System.out.println(s.getId()));
    staffQuery.forEach(s -> System.out.println(s.getJoinDate()));
    staffQuery.forEach(s -> System.out.println(s.getDepartId()));
    staffQuery.forEach(s -> System.out.format("%,.1f\n", s.calculateSalary()));

    // intro messages
    System.out.println("Welcome to Human Resources! Your tool to manage staffs and employees");

    // the main loop
    boolean willContinue = true;
    while (willContinue) {
      System.out.println("What do you want to do?");
      int choice = multipleChoice(new String[] {
        "Exit program",
        "Add a department",
        "List departments"
      });

      switch (choice) {
        case 1:
          willContinue = false;
          break;
        case 2:
          addDepartment();
          break;
        case 3:
          listDepartment();
          break;
        default:
          System.out.println("Please choose a valid action");
          System.out.println();
          break;
      }
    }

    // close the scanner
    SC.close();
  }

  /**
   * Print out a list of actions and let user choose one
   * @param messages the array of messages to be shown
   */
  private static int multipleChoice(String[] messages) {
    // print the messages
    int i;
    for (i = 1; i <= messages.length; i++) {
      System.out.format("  %1$2d. %2$s\n", i, messages[i-1]);
    }

    // wait for user next line of input
    int input = 0;
    boolean willContinue = true;
    String retryMessage = "Please input a valid integer";
    while (willContinue) {
      try {
        System.out.print("Your choice? ");
        input = SC.nextInt();
        // clear everything until end of line
        SC.nextLine();
      } catch (InputMismatchException e) {
        System.out.println(retryMessage);
        continue;
      }

      if (input < 1 || input > messages.length) {
        System.out.println(retryMessage);
      } else {
        willContinue = false;
      }
    }

    return input;
  }

  /**
   * Print out a list of values and let user choose one
   * @param messages the array of messages to be shown
   * @param list the array of values from which would be returned
   */
  private static <T> T multipleChoice(String[] messages, T[] list) {
    if (messages.length != list.length) {
      throw new IllegalArgumentException("Messages and values must be equal in number");
    }

    int input = multipleChoice(messages);

    return list[input - 1];
  }

  private static void addDepartment() {
    System.out.print("Please enter department name: ");
    String nameInput = SC.nextLine();

    departments.add(new Department(nameInput));
    System.out.println("Department added successfully!!");
    System.out.println();
  }

  private static void listDepartment() {
    String headerFormat = "|%1$6s|%2$20s|%3$20s|\n";
    String contentFormat = "|%1$6s|%2$20s|%3$20d|\n";
    System.out.format(headerFormat, "ID", "Name", "Number of staffs");
    System.out.print(String.format(headerFormat, "", "", "").replace(' ', '-'));
    departments.getAll().forEach(d -> System.out.format(contentFormat, d.getId(), d.getName(), d.getNumberOfStaff()));

    System.out.println();
  }

  // pad a string with space to ensure minimal length
  private static String padLeft(String str, int length) {
    // return original string if it's longer than desired length
    if (str.length() >= length) {
      return str;
    }

    // use StringBuilder to append white spaces before original string
    final StringBuilder sb = new StringBuilder();
    while (sb.length() < length - str.length()) {
      sb.append(' ');
    }
    sb.append(str);

    return sb.toString();
  }
}
