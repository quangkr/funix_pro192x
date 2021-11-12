import java.util.Scanner;

public class LuckyNumber {
  public static void main(String[] args) {
    // some data that will be printed out when the game is over
    int totalGames = 0;
    int totalGuesses = 0;
    int bestGame = -1;

    // maximum value of the lucky number
    int max = 100;
    boolean willContinue = true;
    Scanner input = new Scanner(System.in);

    // call play() method until user no longer wants to play
    while (willContinue) {
      totalGames++;
      int thisGuess = play(input, max);
      totalGuesses += thisGuess;

      /* always store the first game's number
         after that only store the best (smallest) guess */
      if (bestGame < 0 || bestGame > thisGuess) {
        bestGame = thisGuess;
      }

      // continue playing if only the user type in y or yes, case-insensitively
      System.out.print("Do you want to play again? (y/N) ");
      String playAgainInput = input.next();
      if (!(playAgainInput.toLowerCase().equals("y") || playAgainInput.toLowerCase().equals("yes"))) {
        willContinue = false;
      }
    }

    // print out the summary
    System.out.println();
    System.out.println("Overall results:");
    System.out.println("Total games   = " + totalGames);
    System.out.println("Total guesses = " + totalGuesses);
    System.out.println("Guesses/game  = " + (double)totalGuesses / totalGames);
    System.out.println("Best game     = " + bestGame);
  }

  public static int play(Scanner input, int max) {
    // generate the lucky number
    final int luckyNumber = (int) (Math.random() * max);
    // initialize the number of guesses, we'll need to return it
    int numberOfGuesses = 0;

    System.out.println("I'm thinking of a number between 0 and " + max + "...");

    // ask for input until it matches the lucky number
    boolean isCorrect = false;
    while (!isCorrect) {
      System.out.print("Your guess? ");

      /* Scanner.hasNextInt() check if next input is an integer
      * then Scanner.nextLine() consume any invalid input */
      while (!input.hasNextInt()) {
        System.out.print("Please enter a valid integer. Your guess? ");
        input.nextLine();
      }
      int guess = input.nextInt();
      numberOfGuesses++;

      // now check the input against the lucky number
      if (guess > luckyNumber) {
        System.out.println("It's lower");
      } else if (guess < luckyNumber) {
        System.out.println("It's higher");
      } else {
        System.out.print("You got it right in " + numberOfGuesses + " guesses!\n");
        isCorrect = true;
      }
    }

    return numberOfGuesses;
  }
}
