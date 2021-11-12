import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeStudent {
  // initialize a scanner for stdin
  private static final Scanner stdIn = new Scanner(System.in);

  public static void main(String[] args) {
    // print out the intro message
    begin();

    // we need to keep track of accumulated weight
    int accumulatedWeight = 0;
    // temporary variable to store each group of score
    double[] termScore;

    // get midterm
    System.out.println();
    System.out.println("Midterm:");
    termScore = midAndFinalTerm(accumulatedWeight);
    accumulatedWeight += termScore[0];
    double midTermScore = termScore[1];

    // do it similarly for final term
    System.out.println();
    System.out.println("Final:");
    termScore = midAndFinalTerm(accumulatedWeight);
    accumulatedWeight += termScore[0];
    double finalTermScore = termScore[1];

    // homework
    System.out.println();
    System.out.println("Homework:");
    double homeworkScore = homework(accumulatedWeight);

    // print out the report
    System.out.println();
    report(new double[] {midTermScore, finalTermScore, homeworkScore});

    // close the main scanner
    stdIn.close();
  }

  // read a single line from stdin
  private static String readInputLine(Scanner input) {
    return input.nextLine();
  }

  // convert provided string to an ArrayList of integers, discarding any non-integer tokens
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

  // check if provided array is in correct size
  private static Boolean checkArraySize(ArrayList<Integer> arr, int size) {
    if (arr.size() < size) {
      System.out.println("Insufficient information provided. Please try again");
      return false;
    } else if (arr.size() > size) {
      System.out.println("Excess information provided. Please try again");
      return false;
    } else {
      return true;
    }
  }

  private static Integer[] getInputInt(int size) {
    ArrayList<Integer> result = new ArrayList<Integer>();
    do {
      result = stringToArrayList(readInputLine(stdIn));
    } while (!checkArraySize(result, size));

    return result.toArray(new Integer[size]);
  }

  private static Integer getInputInt() {
    Integer[] result = getInputInt(1);
    return result[0];
  }

  private static double calcMinGrade(double percentage) {
    if (percentage >= 85.0) { return 3.0; }
    if (percentage >= 75.0) { return 2.0; }
    if (percentage >= 60.0) { return 1.0; }
    return 0.0;
  }

  // print out the intro message
  private static void begin() {
    System.out.println("This program reads exam/homework scores and reports");
    System.out.println("your overall courses grade");
  }

  // total weight must be UNDER 100
  private static double[] midAndFinalTerm(int currentWeight) {
    int weight, earnedScore, isScoreShifted, shiftedScore;
    // prompt for input until total weight is UNDER 100
    do {
      System.out.print("Weight (0-100)? ");
      weight = getInputInt();
    } while ((weight + currentWeight) >= 100);
    System.out.print("Score earned? ");
    earnedScore = getInputInt();
    do {
      System.out.print("Were score shifted? (1=yes, 2=no) ");
      isScoreShifted = getInputInt();
    } while (isScoreShifted != 1 && isScoreShifted != 2);
    if (isScoreShifted == 1) {
      System.out.print("Shifted amount? ");
      shiftedScore = getInputInt();
    } else {
      shiftedScore = 0;
    }

    // doing some maths, make sure totalScore don't exceed 100
    int totalScore = Math.min(earnedScore + shiftedScore, 100);
    double totalPercentage = totalScore/100.0;
    // keep only one digit after decimal point
    double weightedScore = (int) (totalPercentage * weight * 10) / 10.0;

    // print out results
    System.out.println("Total points = " + totalScore + " / 100");
    System.out.println("Weighted score = " + weightedScore + " / " + weight);

    return new double[] {weight, weightedScore};
  }

  /* total weight must be EXACTLY 100
  * moreover, we don't need to return the weight */
  private static double homework(int currentWeight) {
    int weight, numOfAssignments, sectionScore;
    int totalScore = 0;
    int totalMax = 0;
    // prompt for input until total weight is EXACTLY 100
    do {
      System.out.print("Weight (0-100)? ");
      weight = getInputInt();
    } while ((weight + currentWeight) != 100);
    System.out.print("Number of assignments? ");
    numOfAssignments = getInputInt();
    // create a new 2d array
    Integer[][] assignmentScores = new Integer[numOfAssignments][2];
    for (int i = 0; i < assignmentScores.length; i++) {
      System.out.print("Assignment " + (i+1) + " score and max? ");
      assignmentScores[i] = getInputInt(2);
      totalScore += assignmentScores[i][0] < assignmentScores[i][1] ? assignmentScores[i][0] : assignmentScores[i][1];
      totalMax += assignmentScores[i][1];
    }
    System.out.print("How many sections did you attend? ");
    sectionScore = getInputInt() * 5;

    // make sure the scores is in correct range
    sectionScore = Math.min(sectionScore, 30);
    totalScore = Math.min(totalScore, 150);
    totalMax = Math.min(totalMax, 150);

    // doing some math
    double totalPercentage = (double) (sectionScore + totalScore) / (totalMax + 30);
    // keep only one digit after decimal point
    double weightedScore = (int) (totalPercentage * weight * 10) / 10.0;

    // print out results
    System.out.println("Section points = " + sectionScore + " / 30");
    System.out.println("Total points = " + totalScore + " / " + totalMax);
    System.out.println("Weighted score = " + weightedScore + " / " + weight);

    return weightedScore;
  }

  private static void report(double[] weightedScores) {
    double overallPercentage = Arrays.stream(weightedScores).sum();
    System.out.println("Overall percentage = " + overallPercentage);
    System.out.println("Your grade will be at least: " + calcMinGrade(overallPercentage));
  }
}