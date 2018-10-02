package application;

import java.util.ArrayList;

/**
 * Class implementing the Hamiltonian Algorithm constructing a path or a cycle
 * that travels through every node of the graph exactly once.
 * 
 * @author Patricia.
 *
 */
public class HamiltonianAlgorithm {

  private int[][] adjacencyMatrix;
  private ArrayList<Node> vertexSet;
  private Path path;

  /**
   * 
   * @param adjacencyMatrix
   *          Matrix input by user representing the kind of graph they have in
   *          mind.
   */
  public HamiltonianAlgorithm(int[][] adjacencyMatrix) {
    this.adjacencyMatrix = adjacencyMatrix;
    this.vertexSet = new ArrayList<Node>();
  }

  public ArrayList<Node> getVertexSet() {
    return vertexSet;
  }

  /**
   * Method to place all vertices into a set.
   */
  public void setNodes() {
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      this.vertexSet.add(new Node(NodeName.nodeNamer(i + 1), null));
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
          this.vertexSet.get(i).getAdjacentNodes().add(this.vertexSet.get(j));
        }
      }
    }
  }

  /**
   * Method identifying the solution as a path or a cycle.
   * 
   * @param pathnode
   *          A node in a pathNode List.
   * @return true if the starting node is adjacent to the last node.
   */
  public boolean pathIsCycle(Path pathnode) {
    Node firstNode = pathnode.getPathNodes().get(0);
    return firstNode.getAdjacentNodes().contains(pathnode.getCurrentNode());
  }

  /**
   * Method to exhaustively calculate all possible paths.
   * 
   * @param pathnode
   *          An object holding all nodes in order that are part of the
   *          path/cycle.
   * @return path, cycle or throw an exception when no solution is found.
   */
  public Path recursiveHamiltonian(Path pathnode) throws NoSolutionException {
    if (pathnode.getPathNodes().containsAll(vertexSet)) {
      this.path = pathnode;
      return pathnode;
    }

    for (Node node : pathnode.getCurrentNode().getAdjacentNodes()) {
      if (!pathnode.getPathNodes().contains(node)) {
        return this.recursiveHamiltonian(new Path(node, pathnode));
      }
    }

    throw new NoSolutionException();
  }

  /**
   * Print method to output a user friendly string.
   * 
   * @return resulting path
   */
  public String printHamiltonian() {
    StringBuilder result = new StringBuilder();

    boolean firstnode = true;

    for (Node node : this.path.getPathNodes()) {
      if (firstnode) {
        result.append(node);
        firstnode = false;
      } else {
        result.append(" ==> " + node);
      }
    }

    return result.toString();
  }

  /**
   * Method to call all the above methods in order to compute the Hamiltonian
   * path/cycle
   * 
   * @throws NoSolutionException
   *           Exception is thrown when no path is found.
   */
  public String runHamiltonianAlgorithm() throws NoSolutionException {
    setNodes();
    calculateAdjacentNodes();
    recursiveHamiltonian(new Path(vertexSet.get(0)));
    return this.printHamiltonian();
  }
}
