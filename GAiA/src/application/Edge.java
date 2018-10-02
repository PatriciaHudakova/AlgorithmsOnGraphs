package application;

import java.util.Collection;
import java.util.HashSet;

/**
 * A class for the Edge object. The following attributes are all properties of
 * all instances of 'Edge'.
 * 
 * @author Patricia.
 */

public class Edge implements Comparable<Edge> {
  private static long currentId = 0;
  
  private final long id;
  private Node startNode;
  private Node endNode;
  
  private Collection<Node> nodes;
  
  private int weight;

  /**
   * Constructor for the above attributes
   * 
   * @param startNode
   *          This parameter will represent the Node that the edge originates
   *          from and will be useful in cases that use directed or undirected
   *          edges. We will be using this parameter to get the out-flow of a
   *          Node.
   * @param endNode
   *          This parameter will represent the Node that the edge ends up on
   *          and will be useful in cases that use directed or undirected edges.
   *          We will be using this parameter to get the in-flow to a Node.
   * @param weight
   *          The weight parameter will represent the value assigned to an edge.
   *          In un-weighted graphs, this value will be assigned a final null
   *          value.
   * 
   */
  public Edge(Node startNode, Node endNode, int weight) {
    this.id = Edge.currentId++;
    this.startNode = startNode;
    this.endNode = endNode;
    
    this.nodes = new HashSet<Node>();
    
    this.nodes.add(startNode);
    this.nodes.add(endNode);
    
    this.weight = weight;
  }

  /**
   * Getter for the id attribute.
   * 
   * @returns the id value upon call
   */
  public long getId() {
    return id;
  }

  /**
   * Getter for the source node attribute.
   * 
   * @returns the starting node upon call
   */
  public Node getStartNode() {
    return startNode;
  }

  /**
   * Getter for the destination node attribute.
   * 
   * @returns the destination node upon call
   */
  public Node getEndNode() {
    return endNode;
  }

  /**
   * Getter for the value of the weight attribute.
   * 
   * @returns the weight of an edge upon call
   */
  public int getWeight() {
    return weight;
  }

  @Override
  public String toString() {
    return startNode + " -- " + endNode + " ,with edge weight of " + weight;
  }

  /**
   * Method to compare edge weights.
   */
  @Override
  public int compareTo(Edge edge) {
    int weight = this.weight;
    int nextWeight = edge.getWeight();
    return (weight < nextWeight ? -1 : (weight == nextWeight ? 0 : 1));
  }

  public Collection<Node> getNodes() {
    return nodes;
  }
}
