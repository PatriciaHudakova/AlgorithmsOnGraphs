package application;

/**
 * @author Patricia.
 *
 */
@SuppressWarnings("serial")
public class NoSolutionException extends Exception {

  /**
   * Parameterless constructor.
   */
  public NoSolutionException() {

  }

  /**
   * Constructor that accepts a message
   * 
   * @param message
   *          This message appears when no solution is found; i.e. the length of
   *          possibleColours set is 0.
   */
  public NoSolutionException(String message) {
    System.out.print("No solution found; try increasing the number of colours");
  }

}
