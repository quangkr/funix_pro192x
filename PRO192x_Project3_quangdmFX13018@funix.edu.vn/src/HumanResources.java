import java.util.*;
import java.util.function.Function;

/**
 * This is the class where the main logic of this program is executed
 * @author QuangKR
 */
public class HumanResources {
  private final static StaffsManager staffsManager = new StaffsManager();
  private final static Scanner SC = new Scanner(System.in);

  public static void main(String[] args) {
    // intro messages
    System.out.println("Welcome to Human Resources! Your tool to manage staffs and employees");

    // the main loop
    boolean willContinue = true;
    while (willContinue) {
      System.out.println("What do you want to do?");
      int choice = multipleChoice(new String[] {
        "Exit program",
        "Add a department",
        "Remove a department",
        "List departments",
        "Add an employee",
        "Add a manager",
        "List staffs"
      });

      switch (choice) {
        case 1 -> willContinue = false;
        case 2 -> addDepartment();
        case 3 -> removeDepartment();
        case 4 -> listDepartment();
        case 5 -> addEmployee();
        case 6 -> addManager();
        case 7 -> listStaffs();
        default -> {
          System.out.println("Please choose a valid action");
          System.out.println();
        }
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
    return getInputLineInt(
      "Your choice? ",
      "Please input a valid integer",
      n -> (n >= 1 && n <= messages.length)
    );
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

    staffsManager.addDepartment(new Department(nameInput));
    System.out.println("Department added successfully!!");
    System.out.println();
  }
  private static void removeDepartment() {
    List<Department> departments = new ArrayList<>(staffsManager.getAllDepartments());
    List<String> departmentNames = new ArrayList<>(departments.stream().map(d -> d.getName()).toList());

    // add an option to remove nothing
    departments.add(null);
    departmentNames.add("Return");

    Department chosenDepartment = multipleChoice(departmentNames.toArray(new String[0]), departments.toArray(new Department[0]));

    staffsManager.removeDepartment(chosenDepartment);
    System.out.println("Department removed successfully!!");
    System.out.println();
  }
  private static void listDepartment() {
    String headerFormat = "|%1$6s|%2$20s|%3$20s|\n";
    String contentFormat = "|%1$6s|%2$20s|%3$20d|\n";
    System.out.format(headerFormat, "ID", "Name", "Number of staffs");
    System.out.print(String.format(headerFormat, "", "", "").replace(' ', '-'));
    staffsManager.getAllDepartments().forEach(d -> System.out.format(contentFormat, d.getId(), d.getName(), d.getNumberOfStaff()));

    System.out.println();
  }

  private static void addEmployee() {
    System.out.print("Please enter staff name: ");
    String name = SC.nextLine();
    System.out.print("Please enter staff join date: ");
    String joinDate = SC.nextLine();

    System.out.println("Please choose staff department: ");
    List<Department> departments = staffsManager.getAllDepartments();
    String[] departmentNames = departments.stream().map(d -> d.getName()).toArray(String[]::new);
    String[] departmentIds = departments.stream().map(d -> d.getId()).toArray(String[]::new);
    String departmentId = multipleChoice(departmentNames, departmentIds);

    int age = getInputLineInt(
      "Please enter staff age: ",
      "Staff age should be a positive integer. Please try again",
      n -> n > 0
    );
    double salaryMultiplier = getInputLineDouble(
      "Please enter staff salary multiplier: ",
      "Staff salary multiplier should be a positive number. Please try again",
      n -> n > 0
    );

    double extraHours = getInputLineDouble(
      "Please enter staff extra hours: ",
      "Staff extra hours should be a positive number. Please try again",
      n -> n > 0
    );

    staffsManager.addStaff(new Employee(
      name,
      age,
      joinDate,
      departmentId,
      salaryMultiplier,
      extraHours
    ));
    System.out.println("Employee added successfully!!");
    System.out.println();
  }
  private static void addManager() {
    System.out.print("Please enter staff name: ");
    String name = SC.nextLine();
    System.out.print("Please enter staff join date: ");
    String joinDate = SC.nextLine();

    System.out.println("Please choose staff department: ");
    List<Department> departments = staffsManager.getAllDepartments();
    String[] departmentNames = departments.stream().map(d -> d.getName()).toArray(String[]::new);
    String[] departmentIds = departments.stream().map(d -> d.getId()).toArray(String[]::new);
    String departmentId = multipleChoice(departmentNames, departmentIds);

    int age = getInputLineInt(
      "Please enter staff age: ",
      "Staff age should be a positive integer. Please try again",
      n -> n > 0
    );
    double salaryMultiplier = getInputLineDouble(
      "Please enter staff salary multiplier: ",
      "Staff salary multiplier should be a positive number. Please try again",
      n -> n > 0
    );

    ManagerTitles[] titles = ManagerTitles.values();
    String[] titlesName = Arrays.stream(titles).map(t -> t.value()).toArray(String[]::new);
    ManagerTitles title = multipleChoice(titlesName, titles);

    staffsManager.addStaff(new Manager(
      name,
      age,
      joinDate,
      departmentId,
      salaryMultiplier,
      title
    ));
    System.out.println("Manager added successfully!!");
    System.out.println();
  }
  private static void listStaffs() {
    String headerFormat = "|%1$11s|%2$22s|%3$4s|%4$11s|%5$17s|%6$18s|%7$12s|\n";
    String contentFormat = "|%1$11s|%2$22s|%3$4d|%4$11s|%5$17s|%6$18.1f|%7$12.1f|\n";
    System.out.format(headerFormat, "ID", "Name", "Age", "Join Date", "Title", "Salary multiplier", "Extra hours");
    System.out.print(String.format(headerFormat, "", "", "", "", "", "", "").replace(' ', '-'));
    staffsManager.getAllStaffs().forEach(s -> System.out.format(
      contentFormat,
      s.getId(),
      s.getName(),
      s.getAge(),
      s.getJoinDate(),
      (s instanceof Manager) ? ((Manager) s).getTitle().value() : "",
      s.getSalaryMultiplier(),
      (s instanceof Employee) ? ((Employee) s).getExtraHours() : 0.0
    ));

    System.out.println();
  }

  private static int getInputLineInt(String message, String retryMessage, Function<Integer, Boolean> additionalConstraint) {
    int input = 0;
    boolean willContinue = true;
    while (willContinue) {
      try {
        System.out.print(message);
        input = SC.nextInt();
      } catch (InputMismatchException e) {
        System.out.println(retryMessage);
        continue;
      } finally {
        // clear everything until end of line
        SC.nextLine();
      }

      if (!additionalConstraint.apply(input)) {
        System.out.println(retryMessage);
      } else {
        willContinue = false;
      }
    }

    return input;
  }
  private static int getInputLineInt(String message, String retryMessage) {
    return getInputLineInt(message, retryMessage, n -> true);
  }
  private static double getInputLineDouble(String message, String retryMessage, Function<Double, Boolean> additionalConstraint) {
    // wait for user next line of input
    double input = 0;
    boolean willContinue = true;
    while (willContinue) {
      try {
        System.out.print(message);
        input = SC.nextDouble();
      } catch (InputMismatchException e) {
        System.out.println(retryMessage);
        continue;
      } finally {
        // clear everything until end of line
        SC.nextLine();
      }

      if (!additionalConstraint.apply(input)) {
        System.out.println(retryMessage);
      } else {
        willContinue = false;
      }
    }

    return input;
  }
  private static double getInputLineDouble(String message, String retryMessage) {
    return getInputLineDouble(message, retryMessage, n -> true);
  }
}
