import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    staffsManager.addDepartment(new Department("Developers"));
    staffsManager.addDepartment(new Department("Designers"));
    staffsManager.addDepartment(new Department("Human Resources"));
    staffsManager.addStaff(new Employee("Quang", 26, "2015-01-01", "D-001", 8.7, 2));
    staffsManager.addStaff(new Employee("Nhung", 25, "2016-01-01", "D-002", 6.5, 6));
    staffsManager.addStaff(new Employee("Tam", 18, "2020-01-01", "D-003", 9.5, 1));
    staffsManager.addStaff(new Manager("Dang Quang", 26, "2015-01-01", "D-001", 7, ManagerTitles.BUSINESS_LEADER));
    staffsManager.addStaff(new Manager("Kieu Nhung", 25, "2016-01-01", "D-002", 9.5, ManagerTitles.PROJECT_LEADER));
    staffsManager.addStaff(new Manager("Kieu Tam", 18, "2020-01-01", "D-003", 10, ManagerTitles.TECHNICAL_LEADER));

    // the main loop
    boolean willContinue = true;
    while (willContinue) {
      String[] choices = new String[] {
        "Add a department",
        "Remove a department",
        "List departments",
        "Add an employee",
        "Add a manager",
        "Remove a staff",
        "List staffs",
        "List staffs by department",
        "Search staff by ID",
        "Search staff by name",
        "List staff salary",
        "Exit program"
      };

      int choice = getInputLineInt(String.format("What do you want to do? (0-%d, 0 to show help menu) ", choices.length),
        "Please input a valid integer",
        i -> i <= choices.length);
      if (choice == 0) {
        choice = multipleChoice(choices);
      }

      switch (choice) {
        case  1 -> addDepartment();
        case  2 -> removeDepartment();
        case  3 -> listAllDepartment();
        case  4 -> addEmployee();
        case  5 -> addManager();
        case  6 -> removeStaff();
        case  7 -> listAllStaffs();
        case  8 -> listStaffsByDepartment();
        case  9 -> searchStaffById();
        case 10 -> searchStaffByName();
        case 11 -> listStaffSalary();
        case 12 -> willContinue = false;
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
    String nameInput = getInputLine("Please enter department name: ");

    staffsManager.addDepartment(new Department(nameInput));
    System.out.println("Department added successfully!!\n");
  }
  private static void removeDepartment() {
    List<Department> departments = new ArrayList<>(staffsManager.getAllDepartments());
    List<String> departmentNames = new ArrayList<>(departments.stream().map(d -> d.getName()).toList());

    // add an option to remove nothing
    departments.add(0, null);
    departmentNames.add(0, "Back");

    System.out.println("\nWhich department do you want to remove?");
    Department chosenDepartment = multipleChoice(departmentNames.toArray(new String[0]), departments.toArray(new Department[0]));
    if (chosenDepartment != null && chosenDepartment.getNumberOfStaff() > 0) {
      System.out.println("Warning: Department must have no staff before removal. Please try again!\n");
      return;
    }

    staffsManager.removeDepartment(chosenDepartment);
    System.out.println("Department removed successfully!!\n");
  }
  private static void listAllDepartment() {
    String headerFormat  = "|%6s|%20s|%20s|\n";
    String contentFormat = "|%6s|%20s|%20d|\n";
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
  private static void removeStaff() {
    List<Staff> staffs = new ArrayList<>(staffsManager.getAllStaffs());
    List<String> messages = new ArrayList<>(staffs.stream()
      .map(s -> String.format("%s (ID: %s, Department: %s)",
        s.getName(),
        s.getId(),
        staffsManager.getDepartmentById(s.getDepartmentId()).get().getName()))
      .toList());

    // add an option to remove nothing
    staffs.add(0, null);
    messages.add(0, "Back");

    System.out.println("\nWhich staff do you want to remove?");
    Staff chosenStaff = multipleChoice(messages.toArray(new String[0]), staffs.toArray(new Staff[0]));

    staffsManager.removeStaff(chosenStaff);
    System.out.println("Staff removed successfully!!\n");
  }
  private static void listStaffs(Collection<Staff> staffsToPrint) {
    String headerFormat  = "|%11s|%22s|%4s|%11s|%20s|%17s|%18s|%12s|\n";
    String contentFormat = "|%11s|%22s|%4d|%11s|%20s|%17s|%18.1f|%12.1f|\n";
    System.out.format(headerFormat, "ID", "Name", "Age", "Join Date", "Department", "Title", "Salary multiplier", "Extra hours");
    System.out.print(String.format(headerFormat, "", "", "", "", "", "", "", "").replace(' ', '-'));
    staffsToPrint.forEach(s -> System.out.format(
      contentFormat,
      s.getId(),
      s.getName(),
      s.getAge(),
      s.getJoinDate(),
      staffsManager.getDepartmentById(s.getDepartmentId()).get().getName(),
      (s instanceof Manager) ? ((Manager) s).getTitle().value() : "",
      s.getSalaryMultiplier(),
      (s instanceof Employee) ? ((Employee) s).getExtraHours() : 0.0
    ));

    System.out.println();
  }
  private static void listAllStaffs() {
    listStaffs(staffsManager.getAllStaffs());
  }
  private static void listStaffsByDepartment() {
    System.out.println("Please choose department: ");
    List<Department> departments = staffsManager.getAllDepartments();
    String[] departmentNames = departments.stream().map(d -> d.getName()).toArray(String[]::new);
    String[] departmentIds = departments.stream().map(d -> d.getId()).toArray(String[]::new);
    String departmentId = multipleChoice(departmentNames, departmentIds);

    List<Staff> staffsToPrint = staffsManager.getAllStaffs().stream() // turn to a stream
      .collect(Collectors.groupingBy(Staff::getDepartmentId))               // use groupingBy collector to group staffs by department
      .get(departmentId);                                                   // only get desired department

    listStaffs(staffsToPrint);
  }
  private static void searchStaffById() {
    String inputId = getInputLine("\nStaffID to search? ");
    Optional<Staff> searchResult = staffsManager.getStaffById(inputId.toUpperCase());

    if (searchResult.isPresent()) {
      System.out.println("Found 1 staff:");
      searchResult.get().displayInformation();
    } else {
      System.out.println("There is no staff with specified ID!");
    }
    System.out.println();
  }
  private static void searchStaffByName() {
    String inputName = getInputLine("\nStaff name to search? ");
    List<Staff> searchResult = staffsManager.getStaffByName(inputName);

    int size = searchResult.size();
    if (size >= 1) {
      System.out.println("Found " + size + " staff:");
      searchResult.forEach(s -> s.displayInformation());
    } else {
      System.out.println("There is no staff with specified name!");
    }
    System.out.println();
  }
  private static void listStaffSalary() {
    System.out.println("\nHow do you want to sort staffs' salary? ");
    int sortPreference = multipleChoice(new String[] {"Ascending", "Descending"});
    List<Staff> staffs = new ArrayList<>(staffsManager.getAllStaffs());
    if (sortPreference == 2) {
      staffs.sort((x, y) -> Double.compare(y.calculateSalary(), x.calculateSalary()));
    } else {
      staffs.sort((x, y) -> Double.compare(x.calculateSalary(), y.calculateSalary()));
    }

    System.out.println();
    String headerFormat  = "|%11s|%22s|%11s|%20s|%17s|%18s|%12s|%13s|\n";
    String contentFormat = "|%11s|%22s|%11s|%20s|%17s|%18.1f|%12.1f|%,13.1f|\n";
    System.out.format(headerFormat, "ID", "Name", "Join Date", "Department", "Title", "Salary multiplier", "Extra hours", "Salary");
    System.out.print(String.format(headerFormat, "", "", "", "", "", "", "", "").replace(' ', '-'));
    staffs.forEach(s -> System.out.format(
      contentFormat,
      s.getId(),
      s.getName(),
      s.getJoinDate(),
      staffsManager.getDepartmentById(s.getDepartmentId()).get().getName(),
      (s instanceof Manager) ? ((Manager) s).getTitle().value() : "",
      s.getSalaryMultiplier(),
      (s instanceof Employee) ? ((Employee) s).getExtraHours() : 0.0,
      s.calculateSalary()
    ));
    System.out.println();
  }

  private static String getInputLine(String message) {
    System.out.print(message);
    return SC.nextLine();
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
}
