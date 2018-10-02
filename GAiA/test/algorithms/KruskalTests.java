package algorithms;

import static org.junit.Assert.assertTrue;

import application.KruskalAlgorithm;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the KruskalAlgorithm class.
 * 
 * @author Patricia.
 * 
 */
public class KruskalTests {

  KruskalAlgorithm graph; // graph to perform algorithm on
  int[][] adjacencyMatrix = { { 0, 3, 0, 1, 0 }, { 3, 0, 8, 13, 12 }, { 0, 8, 0, 2, 0 },
      { 1, 13, 2, 0, 6 }, { 0, 12, 0, 6, 0 } }; // matrix representing the graph

  /**
   * @throws java.lang.Exception
   *           An child exception of its parent class Exception is thrown when a
   *           violation of expected input occurs.
   */
  @Before
  public void setUp() throws Exception {
    graph = new KruskalAlgorithm(adjacencyMatrix);
  }

  /**
   * Test to see whether the nodes are identified correctly from the matrix; no duplicates.
   */
  @Test
  public void setNodesTest() {
    graph.setNodes();
    assertTrue(graph.getVertexSet().size() == 5);
  }

  /**
   * Test to see whether the edges are identified from the matrix correctly.
   */
  @Test
  public void setEdgesTest() {
    graph.setNodes();
    graph.setEdges();
    assertTrue(graph.getEdgeSet().size() == 7);
  }
  
  /**
   * Test for a correct evaluation of a trivial matrix.
   */
  @Test
  public void trivialTest() {
    int[][] adjacencyMatrix = { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } };
    KruskalAlgorithm graph = new KruskalAlgorithm(adjacencyMatrix);
    graph.setNodes();
    graph.setEdges();
    
    assertTrue(graph.getVertexSet().size() == 3);
    assertTrue(graph.getEdgeSet().size() == 3);
  }

  /**
   * Tests whether a graph with empty edge set is evaluated correctly.
   */
  @Test
  public void calculateMinSpanTreeTest() {
    graph.setNodes();
    graph.setEdges();
    
    assertTrue(graph.calculateMinSpanTree().size() == 4);
  }

  /**
   * Tests whether the recursive method executing Kruskal's algorithm evaluates
   * the solution correctly.
   */
  @Test
  public void calculateMinSpanTreeTestEmpty() {
    graph.setNodes();
    graph.setEdges();
    graph.getEdgeSet().clear();
    assertTrue(graph.calculateMinSpanTree().size() == 0);
  }

}
