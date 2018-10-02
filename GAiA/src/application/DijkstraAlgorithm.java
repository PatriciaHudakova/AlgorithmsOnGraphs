package application;

import application.FibonacciHeap.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Class implementing the Dijkstra's Algorithm using a Fibonacci Heap.
 * 
 * @author Patricia.
 *
 */
public class DijkstraAlgorithm {
  private int[][] adjacencyMatrix;

  private List<Edge> edgeSet;
  private List<Node> vertexSet;

  private List<Edge> minSpanTree;
  private List<Node> minSpanNodes;

  private Map<Node, FibonacciHeap<Node>> heapMap;
  private Map<FibonacciHeap<Node>, List<Entry<Node>>> entryMap;

  /**
   * Constructor for the Dijkstra Algorithm class
   * 
   * @param adjacencyMatrix
   *          Matrix input by user representing the kind of graph they have in
   *          mind.
   */
  public DijkstraAlgorithm(int[][] adjacencyMatrix) {
    this.adjacencyMatrix = adjacencyMatrix;
    
    this.vertexSet = new ArrayList<Node>();
    this.edgeSet = new ArrayList<Edge>();

    this.minSpanTree = new ArrayList<Edge>();
    this.minSpanNodes = new ArrayList<Node>();

    this.heapMap = new HashMap<Node, FibonacciHeap<Node>>();
    this.entryMap = new HashMap<FibonacciHeap<Node>, List<Entry<Node>>>();
  }

  public List<Node> getVertexSet() {
    return vertexSet;
  }

  public List<Edge> getEdgeSet() {
    return edgeSet;
  }

  public List<Edge> getMinSpanTree() {
    return minSpanTree;
  }

  public List<Node> getMinSpanNodes() {
    return minSpanNodes;
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
   * All edges are added into an edge set.
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

  /**
   * Adjacent nodes of all vertices in the graph are retrieved.
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
   * My implementation of Dijkstra's Algorithm using a Fibonacci Heap.
   * 
   * @param startNode
   *          User chooses a startNode from which the algorithm begins.
   */
  public void implementDijkstra(Node startNode) {
    /*
     * Step 1: Associate all the nodes and the Fibonacci heaps, as well as
     * associating all the Fibonacci heaps and their entries.
     */
    for (Node node : this.vertexSet) {
      FibonacciHeap<Node> fibheap = new FibonacciHeap<Node>();
      List<Entry<Node>> heapEntries = new ArrayList<Entry<Node>>();

      for (Node adjNode : node.getAdjacentNodes()) {
        heapEntries.add(fibheap.enqueue(adjNode, Integer.MAX_VALUE));
      }

      this.heapMap.put(node, fibheap);
      this.entryMap.put(fibheap, heapEntries);
    }

    this.minSpanNodes.add(startNode);

    Node lastNode = startNode;
    double lastDistance = 0;

    // Whilst the MST isn't yet full
    while (!this.minSpanNodes.containsAll(vertexSet)) {
      /*
       * Step 2: Reduce the priorities of all the nodes attached to the last
       * node we added to the MST.
       */

      // For node adjacent to the last node added to the MST
      for (Node node : lastNode.getAdjacentNodes()) {
        if (this.minSpanNodes.contains(node)) {
          continue;
        }
        /*
         *  If it's already in the MST, continue to the next adjacent node
         *  (we don't care about the weight any more)
         */

        Entry<Node> nodeEntry = null;
        FibonacciHeap<Node> fibheap = this.heapMap.get(lastNode);

        for (Entry<Node> entry : this.entryMap.get(fibheap)) {
          if (entry.getValue() == node) {
            nodeEntry = entry;
            break;
          }
        }

        double sourceDist = Integer.MAX_VALUE;

        for (Edge edge : this.edgeSet) {
          if (edge.getNodes().contains(lastNode) && edge.getNodes().contains(node)) {
            sourceDist = edge.getWeight() + lastDistance;
            break;
          }
          // Extract the edge's weight
        }

        try {
          fibheap.decreaseKey(nodeEntry, sourceDist);
        } catch (IllegalArgumentException iae) {
          // do nothing
        }
      }
      
      /*
       * Step 3: Pick a node of minimum distance to add to the minimum spanning tree.
       */

      FibonacciHeap<Node> minHeap = null;

      for (FibonacciHeap<Node> fibheap : this.entryMap.keySet()) {
        try {
          boolean notInTree = !this.minSpanNodes.contains(fibheap.min().getValue());
          boolean goodHeap =
              minHeap == null || fibheap.min().getPriority() < minHeap.min().getPriority();
          
          if (notInTree && goodHeap) {
            minHeap = fibheap;
          }
        } catch (NoSuchElementException nsee) {
          // do nothing?
        }
      }

      Entry<Node> minEntry = minHeap.dequeueMin();

      Edge newEdge = null;

      Node first = minEntry.getValue();
      Node second = null;

      for (Node node : heapMap.keySet()) {
        if (heapMap.get(node) == minHeap) {
          second = node;
        }
      }

      for (Edge edge : this.edgeSet) {
        if (edge.getNodes().contains(first) && edge.getNodes().contains(second)) {
          newEdge = edge;
        }
      }

      lastNode = minEntry.getValue();
      lastDistance = minEntry.getPriority();

      minSpanNodes.add(lastNode);
      minSpanTree.add(newEdge);
    }
  }
  
  /**
   * User friendly print method.
   * @return minimal spanning tree
   */
  public String printDijkstra() {
    StringBuilder result = new StringBuilder();
    
    for (Edge edge : this.minSpanTree) {
      result.append(edge);
      result.append("\n");
    }
    
    return result.toString();
  }

  /**
   * Method that calls above methods in correct order to run the algorithm.
   * 
   * @return the Minimal Spanning Tree.
   */
  public String runDijkstra() {
    setNodes();
    setEdges();
    calculateAdjacentNodes();
    implementDijkstra(this.vertexSet.get(0));
    return printDijkstra();
  }
}
