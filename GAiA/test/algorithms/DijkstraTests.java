package algorithms;

import static org.junit.Assert.assertTrue;

import application.DijkstraAlgorithm;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test the DijkstraAlgorithm class.
 * 
 * @author Patricia.
 *
 */
public class DijkstraTests {

  DijkstraAlgorithm graph; // graph to perform algorithm on
  int[][] adjacencyMatrix = { { 0, 7, 2, 0, 0 }, { 7, 0, 3, 1, 0 }, { 2, 3, 0, 8, 5 },
      { 0, 1, 8, 0, 0 }, { 0, 0, 5, 0, 0 } };

  @Before
  public void setUp() throws Exception {
    graph = new DijkstraAlgorithm(adjacencyMatrix);
  }

  /**
   * Test to show whether the setNodes() correctly adds nodes to the list
   * setOfNodes.
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

    assertTrue(graph.getEdgeSet().size() == 6);
  }

  /**
   * Test to show the adjacentNodes list is correctly configured for each matrix
   * node.
   */
  @Test
  public void calculateAdjacentNodesTest() {
    graph.setNodes();
    graph.calculateAdjacentNodes();

    assertTrue(graph.getVertexSet().get(0).getAdjacentNodes().size() == 2);
    assertTrue(graph.getVertexSet().get(1).getAdjacentNodes().size() == 3);
    assertTrue(graph.getVertexSet().get(2).getAdjacentNodes().size() == 4);
    assertTrue(graph.getVertexSet().get(3).getAdjacentNodes().size() == 2);
    assertTrue(graph.getVertexSet().get(4).getAdjacentNodes().size() == 1);
  }

  /**
   * Test to see id the correct solution is shown upon evaluating trivial
   * graphs.
   */
  @Test
  public void dijkstraTrivialTest() {
    int[][] adjacencyMatrix = { { 0, 1, 1 }, { 1, 0, 1 }, { 1, 1, 0 } };
    graph = new DijkstraAlgorithm(adjacencyMatrix);
    graph.setNodes();
    graph.setEdges();
    graph.calculateAdjacentNodes();
    graph.implementDijkstra(graph.getVertexSet().get(0));

    assertTrue(graph.getMinSpanNodes().size() == graph.getVertexSet().size());
    assertTrue(graph.getMinSpanTree().size() == 2);

  }

  /**
   * Test to see if my implementation of dijkstra's algorithm works for simple
   * graphs.
   */
  @Test
  public void dijkstraDefaultImplementationTest() {
    graph.setNodes();
    graph.setEdges();
    graph.calculateAdjacentNodes();
    graph.implementDijkstra(graph.getVertexSet().get(0));

    assertTrue(graph.getMinSpanNodes().size() == graph.getVertexSet().size());
    assertTrue(graph.getMinSpanTree().size() == 4);
  }

  /**
   * Test to see if my implementation of dijkstra's algorithm works for larger
   * graphs.
   */
  @Test
  public void dijkstraLargerImplementationTest() {
    int[][] adjacencyMatrix = { { 0, 2, 4, 1, 0, 0, 0 }, { 2, 0, 0, 3, 10, 0, 0 },
        { 4, 0, 0, 2, 0, 5, 0 }, { 1, 3, 2, 0, 2, 8, 4 }, { 0, 10, 0, 2, 0, 0, 6 },
        { 0, 0, 5, 8, 0, 0, 1 }, { 0, 0, 0, 4, 6, 1, 0 } };
    graph = new DijkstraAlgorithm(adjacencyMatrix);
    graph.setNodes();
    graph.setEdges();
    graph.calculateAdjacentNodes();
    graph.implementDijkstra(graph.getVertexSet().get(0));

    assertTrue(graph.getMinSpanNodes().size() == graph.getVertexSet().size());
    assertTrue(graph.getMinSpanTree().size() == 6);

  }
}
