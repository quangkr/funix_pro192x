import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeStudent {
  public static void main(String[] args) {
    // initialize a scanner for stdin
    Scanner input = new Scanner(System.in);
    // print out the intro message
    begin();

    System.out.print("array size? ");
    int size = input.nextInt();
    input.nextLine();
    String inputStr = readInputLine(input);
    ArrayList<Integer> intArr = stringToArrayList(inputStr);
    intArr.forEach(n -> System.out.println(n));

    // close the scanner
    input.close();
  }

  // read a single line from stdin
  private static String readInputLine(Scanner input) {
    return input.nextLine();
  }

  private static ArrayList<Integer> stringToArrayList(String inputStr) {
    Scanner input = new Scanner(inputStr);
    ArrayList<Integer> result = new ArrayList<Integer>();

    // loop through the input
    while (input.hasNext()) {
      try {
        // add the integer to the result array
        result.add(input.nextInt());
      } catch(InputMismatchException e) {
        // skip non-integer tokens
        input.next();
      }
    }
    input.close();

    return result;
  }

  // print out the intro message
  private static void begin() {
    System.out.println("This program reads exam/homework scores and reports");
    System.out.println("your overall courses grade");
  }

  private static void midTerm() {
  }

  private static void finalTerm() {
  }

  private static void homework() {
  }

  private static void report() {
  }
}