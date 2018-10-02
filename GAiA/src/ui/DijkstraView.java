package ui;

import application.DijkstraAlgorithm;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * View class for Dijkstra Algorithm that will launch the DijkstraView.fxml file
 * upon invocation.
 * 
 * @author Patricia.
 *
 */
public class DijkstraView extends Application {
  private double startTime;
  private double endTime;
  private double timeTaken;

  @Override
  public void start(Stage secondaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("DijkstraView.fxml"));
      Scene scene = new Scene(root, 750, 550);
      scene.getStylesheets().add(getClass().getResource("dijkstra.css").toExternalForm());
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

  private static volatile DijkstraView instance = null;

  /**
   * Main method to launch the DijkstraView.fxml
   * 
   * @param args
   *          When an application is launched, the runtime system passes the
   *          command-line arguments to the application's main method via an
   *          array of Strings.
   */
  public static void main(String[] args) {
    launch(args);
  }

  /**
   * This method will create a new thread and initialise the GUI.
   */
  public static synchronized DijkstraView getInstance() {
    if (instance == null) {
      new Thread(() -> Application.launch(DijkstraView.class)).start();
    }
    while (instance == null) {
    }
    return instance;
  }

  public String getMatrix() {
    return this.adjacencyMatrixDijkstra.getText();
  }

  public void appendTextOutput(String text) {
    outputDijkstra.clear();
    this.outputDijkstra.appendText(text);
  }

  public void appendTextTime(String time) {
    timeArea.clear();
    this.timeArea.appendText(time);
  }

  /*
   * fx:ids for important Scene elements in the Pane
   */
  @FXML
  private AnchorPane dijkstraPane;

  @FXML
  private Button calculateDijkstra;

  @FXML
  private Button clearOutput;

  @FXML
  private CheckBox showTimeBox;

  @FXML
  private TextArea adjacencyMatrixDijkstra;

  @FXML
  private TextArea outputDijkstra;

  @FXML
  private TextArea timeArea;

  /**
   * Upon "Calculate" button clicked, calculateDijkstra() method is invoked
   * taking the user input as a string, converting this into int[][] form which
   * is evaluated by the Dijkstra algorithm class resulting in a path in the
   * output TextArea of the Pane.
   */
  public void calculateDijkstra() {
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
      
      

      DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(adjMatrix);

      String solution = null;

      solution = dijkstra.runDijkstra();

      this.appendTextOutput(solution.toString());

    } catch (IndexOutOfBoundsException iobe) {
      outputDijkstra.appendText("Please adhere to the suggested format");
      return;
    }
    endTime = System.nanoTime();
  }

  /**
   * "Clear" button on pressed, the input and output text areas will clear.
   */
  public void clearScreen() {
    adjacencyMatrixDijkstra.clear();
    outputDijkstra.clear();
    timeArea.clear();
    showTimeBox.setSelected(false);
  }

  /**
   * Method to display resulting timer onto the textArea.
   */
  public void showTime() {
    timeTaken = endTime - startTime;
    String time = "Time taken for algorithm to evaluate: " + timeTaken / 1000000 + " milliseconds.";
    this.appendTextTime(time);
  }
}
