package application;

import java.util.ArrayList;

/**
 * A class for the Node object. The following attributes are all properties of
 * the Node.
 * 
 * @author Patricia.
 */

public class Node {
  private String name;
  private Colour colour;
  private ArrayList<Node> adjacentNodes;
  private ArrayList<Colour> possibleColours;

  /**
   * Setters for the above attributes.
   * 
   * @param name
   *          The name of a node is more so for the user than the system. This
   *          will make the distinction between different nodes on a graph,
   *          chosen by the user.
   * @param possibleColours
   *          A set containing all possible colours for a node.
   */
  public Node(String name, ArrayList<Colour> possibleColours) {
    this.name = name;
    this.adjacentNodes = new ArrayList<Node>();
    this.possibleColours = possibleColours;
  }

  public void setColour(Colour colour) {
    this.colour = colour;
  }

  public ArrayList<Colour> getPossibleColours() {
    return possibleColours;
  }

  public Colour getColour() {
    return this.colour;
  }

  public ArrayList<Node> getAdjacentNodes() {
    return this.adjacentNodes;
  }

  /**
   * Getter for the name attribute.
   * 
   * @returns the name of the node upon request.
   */
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
