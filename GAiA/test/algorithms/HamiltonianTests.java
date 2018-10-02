package algorithms;

import static org.junit.Assert.assertTrue;

import application.HamiltonianAlgorithm;
import application.NoSolutionException;
import application.Path;

import org.junit.Before;
import org.junit.Test;

/**
 * Class to test HamiltonianAlgorithm.class
 * 
 * @author Patricia.
 *
 */
public class HamiltonianTests {

  HamiltonianAlgorithm graph; // Graph to perform algorithm on
  int[][] adjacencyMatrix = { { 0, 0, 1, 0 }, { 0, 0, 1, 1 }, { 1, 1, 0, 0 }, { 0, 1, 0, 0 } };
  // Matrix representing the graph

  /**
   * @throws java.lang.Exception
   *           An child exception of its parent class Exception is thrown when a
   *           violation of expected input occurs.
   */
  @Before
  public void setUp() throws Exception {
    graph = new HamiltonianAlgorithm(adjacencyMatrix);
  }

  /**
   * Test to show whether the setNodes() correctly adds nodes to the list
   * setOfNodes.
   */
  @Test
  public void setNodesTest() {
    graph.setNodes();

    assertTrue(graph.getVertexSet().size() == 4);
  }

  /**
   * Test to show the adjacentNodes list is correctly configured for each matrix
   * node.
   */
  @Test
  public void calculateAdjacentNodesTest() {
    graph.setNodes();
    graph.calculateAdjacentNodes();

    assertTrue(graph.getVertexSet().get(0).getAdjacentNodes().size() == 1);
    assertTrue(graph.getVertexSet().get(1).getAdjacentNodes().size() == 2);
    assertTrue(graph.getVertexSet().get(2).getAdjacentNodes().size() == 2);
    assertTrue(graph.getVertexSet().get(3).getAdjacentNodes().size() == 1);
  }

  /**
   * Test to show the correct path is found for simple graphs.
   * 
   * @throws NoSolutionException
   *           Exception is thrown when no path is found.
   */
  @Test
  public void recursiveHamiltonianPathTest() throws NoSolutionException {
    graph.setNodes();
    graph.calculateAdjacentNodes();

    Path path = graph.recursiveHamiltonian(new Path(graph.getVertexSet().get(0)));

    assertTrue(graph.getVertexSet().size() == path.getPathNodes().size());
  }

  /**
   * Test to show the cycle is identified correctly
   * 
   * @throws NoSolutionException
   *           Exception is thrown when no path is found.
   */
  @Test
  public void pathIsCycleTest() throws NoSolutionException {
    graph.setNodes();
    graph.calculateAdjacentNodes();
    Path path = graph.recursiveHamiltonian(new Path(graph.getVertexSet().get(0)));

    assertTrue(!graph.pathIsCycle(path));
  }

  /**
   * Test to see if NoSolutionException is thrown at correct instances in an
   * unconnected graph
   * 
   * @throws NoSolutionException
   *           Exception is thrown when no path is found because of one or more
   *           stray node.
   */
  @Test(expected = NoSolutionException.class)
  public void noSolutionUnconnectedTest() throws NoSolutionException {
    int[][] adjacencyMatrix = { { 0, 0, 0, 0 }, { 0, 0, 1, 1 }, { 1, 1, 0, 0 }, { 0, 0, 0, 0 } };
    graph = new HamiltonianAlgorithm(adjacencyMatrix);
    graph.setNodes();
    graph.calculateAdjacentNodes();
    graph.recursiveHamiltonian(new Path(graph.getVertexSet().get(0)));
  }

  /**
   * Test to see if NoSolutionException is thrown at correct instances in a
   * connected graph.
   * 
   * @throws NoSolutionException
   *           Exception is thrown when no path is found.
   */
  @Test(expected = NoSolutionException.class)
  public void noSolutionConnectedTest() throws NoSolutionException {
    int[][] adjacencyMatrix = { { 1, 1, 1, 1 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 }, { 1, 0, 0, 0 } };
    graph = new HamiltonianAlgorithm(adjacencyMatrix);
    graph.setNodes();
    graph.calculateAdjacentNodes();
    graph.recursiveHamiltonian(new Path(graph.getVertexSet().get(0)));
  }
}