package application;

import java.util.ArrayList;
import java.util.List;

/**
 * Class implementing the Vertex Colouring Algorithm to find the minimum number
 * of colours it takes to colour all nodes of the graph such that no adjacent
 * nodes share a colour.
 * 
 * @author Patricia.
 *
 */
public class VertexColouringAlgorithm {

  private int[][] adjacencyMatrix;
  private ArrayList<Colour> colours;
  private ArrayList<Node> setOfNodes;

  /**
   * Constructor for the nodes that already have an array of possible colours
   * 
   * @param adjacencyMatrix
   *          Matrix input by user representing the kind of graph they have in
   *          mind.
   */
  public VertexColouringAlgorithm(int[][] adjacencyMatrix, ArrayList<Colour> colours) {
    this.adjacencyMatrix = adjacencyMatrix;
    this.colours = colours;
    this.setOfNodes = new ArrayList<Node>();
  }

  /**
   * Constructor for nodes that yet do not possess such colours.
   * 
   * @param adjacencyMatrix
   *          Matrix input by user representing the kind of graph they have in
   *          mind.
   * @param colours
   *          colours are assigned on an iterative basis.
   */
  public VertexColouringAlgorithm(int[][] adjacencyMatrix, int colours) {
    this.adjacencyMatrix = adjacencyMatrix;
    this.colours = new ArrayList<Colour>();

    for (int iter = 0; iter < colours; iter++) {
      this.colours.add(new Colour(iter));
    }

    this.setOfNodes = new ArrayList<Node>();
  }

  public ArrayList<Node> getSetOfNodes() {
    return setOfNodes;
  }

  /**
   * Method to add all nodes in the matrix to the setOfNodes List.
   */
  public void setNodes() {
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      @SuppressWarnings("unchecked")
      ArrayList<Colour> clone = (ArrayList<Colour>) this.colours.clone();
      this.setOfNodes.add(new Node(NodeName.nodeNamer(i + 1), clone));
    }
  }

  /**
   * Method to calculate all adjacent nodes of the current node.
   */
  public void calculateAdjacentNodes() {
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      for (int j = 0; j < adjacencyMatrix.length; j++) {
        if (adjacencyMatrix[i][j] > 0) {
          // add node j to the list of i's adjacent nodes
          this.setOfNodes.get(i).getAdjacentNodes().add(this.setOfNodes.get(j));
        }
      }
    }
  }

  /**
   * Method that assigns colour to adjacentNodes recursively. For each adjacent
   * node, if the list of possible colours contains the node's colour, we remove
   * that colour from the list and assign it a colour that is the first element
   * in the remaining possibleColours list. If no colours are left in the set,
   * terminate program throwing user-readable message.
   */
  public List<Node> colourAssignment(Node currentNode) throws NoSolutionException {
    if (currentNode.getColour() != null) {
      return null;
    }

    for (Node node : currentNode.getAdjacentNodes()) {
      if (node.getColour() == null) {
        continue;
      } else if (currentNode.getPossibleColours().contains(node.getColour())) {
        currentNode.getPossibleColours().remove(node.getColour());
      }
    }

    if (currentNode.getPossibleColours().size() >= 1) {
      currentNode.setColour(currentNode.getPossibleColours().get(0));
    } else {
      throw new NoSolutionException();
    }

    for (Node node : currentNode.getAdjacentNodes()) {
      this.colourAssignment(node);
    }

    return this.setOfNodes;
  }

  /**
   * Method that prints the end solution, if and only if the colouring algorithm
   * hasn't terminated the runVertexColouring() early.
   */
  public String printResult() {
    StringBuilder result = new StringBuilder();
    result.append("Solution: ");
    for (Node node : setOfNodes) {
      result.append(", " + node.toString() + " = " + node.getColour().getColour());
    }

    return result.toString();
  }

  /**
   * Method that runs the Colouring Algorithm, calling all the above methods in
   * VertexColouringAlgortithm.java class.
   */
  public String runVertexColouring() throws NoSolutionException {
    this.setNodes();
    this.calculateAdjacentNodes();
    // startNode is the first node in the setOfNodes List
    this.colourAssignment(this.setOfNodes.get(0));
    return this.printResult();
  }
}
