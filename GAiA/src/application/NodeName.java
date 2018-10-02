package application;

/**
 * Small class to be able to make alphabetical node names.
 * 
 * @author Patricia
 *
 */
public class NodeName {
  
  /**
   * Prints the ASCII value of the number.
   * 
   * @param number the number to convert.
   * @return the ASCII value of the number.
   */
  private static String intToChar(int number) {
    int decValue = number + 64; 
    
    return Character.toString((char) decValue);
  }
  
  /**
   * Converts a number - intended to be 0-25 - to its ASCII representation.
   * 
   * @param number the number to convert.
   * @return the ASCII value of the number.
   */
  public static String nodeNamer(int number) {
    StringBuilder name = new StringBuilder();
    
    int modulus = 0;
    int exponent = 1;
    int currentValue = number;
    
    while (number >= ((int) Math.pow(26, exponent - 1))) {
      modulus = currentValue % (int) Math.pow(26, exponent);
      currentValue -= modulus;
    
      name.append(NodeName.intToChar((modulus / (int) Math.pow(26, exponent - 1))));
      exponent++;
    }
    
    return name.reverse().toString();
  }
}
