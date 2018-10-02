package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class implementing the Kruskal's Algorithm to construct a minimal spanning
 * tree.
 * 
 * @author Patricia.
 *
 */
public class KruskalAlgorithm {
  private int[][] adjacencyMatrix;
  private ArrayList<Node> vertexSet;
  private List<Edge> edgeSet;
  private Set<Edge> mstEdges;

  /**
   * Constructor for the class.
   * 
   * @param adjacencyMatrix
   *          Matrix representing the graph.
   */
  public KruskalAlgorithm(int[][] adjacencyMatrix) {
    this.adjacencyMatrix = adjacencyMatrix;
    this.vertexSet = new ArrayList<Node>();
    this.edgeSet = new ArrayList<Edge>();
  }

  public ArrayList<Node> getVertexSet() {
    return vertexSet;
  }

  public List<Edge> getEdgeSet() {
    return edgeSet;
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
   * Method to place all edges into a sorted set.
   */
  public void setEdges() {
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      for (int j = 0; j < adjacencyMatrix.length; j++) {
        int weight = adjacencyMatrix[i][j];
        if (weight > 0 && i > j) {
          Node startNode = this.vertexSet.get(i);
          Node endNode = this.vertexSet.get(j);
          this.edgeSet.add(new Edge(startNode, endNode, weight));
        }
      }
    }

    Collections.sort(this.edgeSet);
  }

  public Set<Edge> calculateMinSpanTree() {
    return this.calculateMinSpanTree(new HashSet<Node>());
  }

  /**
   * Method to recursively calculate the MST.
   */
  public Set<Edge> calculateMinSpanTree(Set<Node> mstset) {
    if (edgeSet.size() == 0) {
      return new HashSet<Edge>();
    }

    Edge minEdge = edgeSet.get(0);

    Node startNode = minEdge.getStartNode();
    Node endNode = minEdge.getEndNode();

    edgeSet.remove(minEdge);

    if (mstset.contains(startNode) && mstset.contains(endNode)) {
      return calculateMinSpanTree(mstset);
    } else {
      mstset.add(startNode);
      mstset.add(endNode);

      Set<Edge> edges = calculateMinSpanTree(mstset);
      edges.add(minEdge);

      this.mstEdges = edges;
      return edges;
    }
  }

  /**
   * User friendly print method.
   * @return minimal spanning tree
   */
  public String printKruskal() {
    StringBuilder result = new StringBuilder();
    
    for (Edge edge : this.mstEdges) {
      result.append(edge);
      result.append("\n");
    }
    
    return result.toString();
  }

  /**
   * Method invoking above methods to calculate MST.
   * 
   * @returns the minimal spanning tree.
   * @throws NoSolutionException
   *           exception is thrown when no solution is found.
   */
  public String runKruskal() throws NoSolutionException {
    setNodes();
    setEdges();
    this.calculateMinSpanTree();
    return printKruskal();
  }

}
