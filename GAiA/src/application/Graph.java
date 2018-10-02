package application;

import java.util.List;

/**
 * Graph object class made up of nodes and edges with its corresponding getters
 * and setters
 * 
 * @author Patricia.
 */

public class Graph {
  private List<Node> nodes;
  private List<Edge> edges;

  /**
   * 
   * @param nodes
   *          A list containing all the nodes on a graph.
   * @param edges
   *          A list containing all edges on a graph.
   */
  public Graph(List<Node> nodes, List<Edge> edges) {
    this.nodes = nodes;
    this.edges = edges;
  }

  /**
   * Getter for the List of all the Nodes.
   * 
   * @returns a list of all nodes in the set
   */
  public List<Node> getNodes() {
    return nodes;
  }

  /**
   * Getter for the List of all Edges.
   * 
   * @returns a list of all edges in the set
   */
  public List<Edge> getEdges() {
    return edges;
  }
}
