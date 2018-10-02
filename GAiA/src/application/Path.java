package application;

import java.util.ArrayList;

/**
 * A class containing all objects of type Node in a list to be printed as a
 * solution to Hamiltonian Algorithm.
 * 
 * @author Patricia.
 *
 */
public class Path {

  private ArrayList<Node> pathNodes;
  
  public Path(Node node) {
    this.pathNodes = new ArrayList<Node>();
    this.pathNodes.add(node);
  }

  @SuppressWarnings("unchecked")
  public Path(Node node, Path pathNode) {
    this.pathNodes = (ArrayList<Node>) pathNode.getPathNodes().clone();
    this.pathNodes.add(node);
  }
  
  public Node getCurrentNode() {
    return this.pathNodes.get(this.pathNodes.size() - 1);
  }
  
  public ArrayList<Node> getPathNodes() {
    return pathNodes;
  }
}
