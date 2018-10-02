package ui;

import application.NoSolutionException;
import application.VertexColouringAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Class launching VertexColouringView fxml file upon invocation as well as
 * handling simple user requests.
 * 
 * @author Patricia.
 *
 */
public class VertexColView extends Application {
  private double startTime;
  private double endTime;
  private double timeTaken;

  @Override
  public void start(Stage secondaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader
          .load(getClass().getResource("VertexColouringView.fxml"));
      Scene scene = new Scene(root, 750, 550);
      scene.getStylesheets().add(getClass().getResource("vertexColouring.css").toExternalForm());
      secondaryStage.setScene(scene);
      secondaryStage.show();

      // set the application icon
      secondaryStage.getIcons().add(new Image("file:resources/images/if_i-cursor_1608366.png"));

      // set application title
      secondaryStage.setTitle("GAiA");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static volatile VertexColView instance = null;

  /**
   * Main method to launch VertexColouring.fxml
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * This method will create a new thread and initialise the GUI.
   */
  public static synchronized VertexColView getInstance() {

    if (instance == null) {
      new Thread(() -> Application.launch(VertexColView.class)).start();
    }
    while (instance == null) {
    }
    return instance;
  }

  public void appendTextTime(String time) {
    timeArea.clear();
    this.timeArea.appendText(time);
  }

  public String getMatrix() {
    return this.adjacencyMatrixColours.getText();
  }

  public void appendTextOutput(String text) {
    solutionColours.clear();
    this.solutionColours.appendText(text);
  }

  /*
   * fx:ids are specified here for important elements of the scene.
   */
  @FXML
  private AnchorPane vertexColouringView;

  @FXML
  private CheckBox showTimeBox;

  @FXML
  private TextArea adjacencyMatrixColours;

  @FXML
  private TextArea solutionColours;

  @FXML
  private TextArea timeArea;

  @FXML
  private TextField numberOfColours;

  @FXML
  private Button calculateColoursBtn;

  @FXML
  private Button clearColoursBtn;

  /**
   * Upon "Calculate" button clicked, runVertexColouring() method is invoked
   * taking the user input as a string, converting this into int[][] form which
   * is evaluated by the Vertex Colouring algorithm class resulting in a path in
   * the output TextArea of the Pane.
   */
  public void calculateColours() {
    startTime = System.nanoTime();

    try {

      String matrixStr = this.getMatrix();

      String[] rows = matrixStr.split("\n");
      int numRows = rows.length;
      int numCols = rows[0].split(",").length;
      
      int[][] adjMatrix = new int[numRows][numCols];

      for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
        String[] matrixRow = rows[rowIndex].split(",");
        
        for (int colIndex = 0; colIndex < numCols; colIndex++) {
          adjMatrix[rowIndex][colIndex] =
              Integer.valueOf(matrixRow[colIndex]);
        }
      }
      int noOfColours = 0;

      // Reads value from a TextField for the number of colours to be used
      try {
        noOfColours = Integer.valueOf(this.numberOfColours.getText());
      } catch (NumberFormatException nfe) {
        solutionColours.appendText("Please input an integer value for the number of colours. ");
        return;
      }

      VertexColouringAlgorithm colours = new VertexColouringAlgorithm(adjMatrix, noOfColours);

      String solution = null;

      try {
        solution = colours.runVertexColouring();
      } catch (NoSolutionException e) {
        e.printStackTrace();
      }

      try {
        this.appendTextOutput(solution);
      } catch (NullPointerException npe) {
        solutionColours
            .appendText("Please increase the number of colours value; no solution found.");
      }

    } catch (IndexOutOfBoundsException iob) {
      solutionColours.appendText("Please adhere to the suggested format");
      return;
    }
    endTime = System.nanoTime();
  }

  /**
   * "Clear" button on pressed will invoke this method clearing the pane.
   */
  public void clearScreen() {
    adjacencyMatrixColours.clear();
    solutionColours.clear();
    timeArea.clear();
    showTimeBox.setSelected(false);
    numberOfColours.clear();
  }

  /**
   * Upon checkbox being checked, time taken for the vertex colouring algorithm
   * will display.
   */
  public void showTime() {
    timeTaken = endTime - startTime;
    String time = "Time taken for algorithm to evaluate: " + timeTaken / 1000000 + " milliseconds.";
    this.appendTextTime(time);
  }

}
