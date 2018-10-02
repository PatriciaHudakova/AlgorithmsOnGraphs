package algorithms;

import static org.junit.Assert.assertTrue;

import application.Colour;
import application.NoSolutionException;
import application.VertexColouringAlgorithm;

import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test VertexColouring.class
 * 
 * @author Patricia.
 *
 */
public class VertexColouringTests {
  
  VertexColouringAlgorithm graph; // Graph to perform algorithm on
  ArrayList<Colour> colours; // Array of node colours
  int[][] adjacencyMatrix = { { 0, 1, 1 }, { 1, 0, 0 }, { 1, 0, 0 } };
  // Matrix representing a graph

  /**
   * @throws java.lang.Exception
   *           An child exception of its parent class Exception is thrown when a
   *           violation of expected input occurs.
   */
  @Before
  public void setUp() throws Exception {
    this.colours = new ArrayList<Colour>();
    colours.add(new Colour(0));
    colours.add(new Colour(1));
    colours.add(new Colour(2));

    graph = new VertexColouringAlgorithm(adjacencyMatrix, colours);
  }

  /**
   * Test to show whether the setNodes() correctly adds nodes to the list
   * setOfNodes.
   */
  @Test
  public void setNodesTest() {
    graph.setNodes();

    assertTrue(graph.getSetOfNodes().size() == 3);
  }

  /**
   * Test to show the adjacentNodes list is correctly configured for each matrix
   * node.
   */
  @Test
  public void calculateAdjacentNodesTest() {
    graph.setNodes();
    graph.calculateAdjacentNodes();

    assertTrue(graph.getSetOfNodes().get(0).getAdjacentNodes().size() == 2);
    assertTrue(graph.getSetOfNodes().get(1).getAdjacentNodes().size() == 1);
    assertTrue(graph.getSetOfNodes().get(2).getAdjacentNodes().size() == 1);
  }

  /**
   * Test to show that the nodes are being coloured obeying the restrictions
   * imposed. It also tests that the used colours are correctly removed from the
   * list of possible colours of their respective nodes.
   * 
   * @throws NoSolutionException
   *           NoSolutionExcption is thrown when the number of colours is
   *           insufficient to colour the graph.
   */
  @Test
  public void colourAssignmentTest() throws NoSolutionException {
    graph = new VertexColouringAlgorithm(adjacencyMatrix, colours);
    graph.setNodes();
    graph.calculateAdjacentNodes();
    graph.colourAssignment(graph.getSetOfNodes().get(0));

    assertTrue(graph.getSetOfNodes().get(0).getColour() == this.colours.get(0));
    assertTrue(graph.getSetOfNodes().get(1).getPossibleColours().size() == 2);
    assertTrue(graph.getSetOfNodes().get(1).getColour() == this.colours.get(1));
    assertTrue(graph.getSetOfNodes().get(2).getPossibleColours().size() == 2);
    assertTrue(graph.getSetOfNodes().get(2).getColour() == this.colours.get(1));
  }

  /**
   * Test showing the exception being thrown at correct instance.
   * 
   * @throws NoSolutionException
   *           NoSolutionExcption is thrown when the number of colours is
   *           insufficient to colour the graph.
   */
  @Test(expected = NoSolutionException.class)
  public void noSolutionExceptionTest() throws NoSolutionException {
    graph = new VertexColouringAlgorithm(adjacencyMatrix, colours);
    colours.remove(2);
    colours.remove(1);
    graph.setNodes();
    graph.calculateAdjacentNodes();
    graph.colourAssignment(graph.getSetOfNodes().get(0));
  }
}