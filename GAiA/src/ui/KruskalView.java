package ui;

import application.KruskalAlgorithm;
import application.NoSolutionException;

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
 * View class for the Kruskal Algorithm handling basic user events and launching
 * its corresponding fxml file upon request.
 * 
 * @author Patricia.
 *
 */
public class KruskalView extends Application {
  private long startTime;
  private long endTime;
  private long timeTaken;

  @Override
  public void start(Stage secondaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("KruskalView.fxml"));
      Scene scene = new Scene(root, 750, 550);
      scene.getStylesheets().add(getClass().getResource("kruskal.css").toExternalForm());
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

  private static volatile KruskalView instance = null;

  /**
   * Main method to launch the MainView.fxml
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
  public static synchronized KruskalView getInstance() {
    if (instance == null) {
      new Thread(() -> Application.launch(KruskalView.class)).start();
    }
    while (instance == null) {
    }
    return instance;
  }

  public String getMatrix() {
    return this.adjacencyMatrixKruskal.getText();
  }

  public void appendTextOutput(String text) {
    outputKruskal.clear();
    this.outputKruskal.appendText(text);
  }

  public void appendTextTime(String time) {
    timeArea.clear();
    this.timeArea.appendText(time);
  }

  /*
   * fx:ids are specified here for important elements of the scene.
   */
  @FXML
  private AnchorPane kruskalPane;

  @FXML
  private Button calculateKruskal;

  @FXML
  private Button clearKruskal;

  @FXML
  private CheckBox showTimeBox;

  @FXML
  private TextArea adjacencyMatrixKruskal;

  @FXML
  private TextArea outputKruskal;

  @FXML
  private TextArea timeArea;

  /**
   * Upon "Calculate" button clicked, calculateKruskal() method is invoked
   * taking the user input as a string, converting this into int[][] form which
   * is evaluated by the Kruskal algorithm class resulting in a path in the
   * output TextArea of the Pane.
   */
  public void calculateKruskal() {
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
          adjMatrix[rowIndex][colIndex] = Integer.valueOf(matrixRow[colIndex]);
        }
      }

      KruskalAlgorithm kruskal = new KruskalAlgorithm(adjMatrix);

      String solution = null;

      try {
        solution = kruskal.runKruskal();
      } catch (NoSolutionException e) {
        e.printStackTrace();
      }

      this.appendTextOutput(solution.toString());

      endTime = System.nanoTime();

    } catch (IndexOutOfBoundsException iobe) {
      outputKruskal.appendText("PLease adhere to the suggested format");
      return;
    }
  }

  /**
   * "Clear" button on pressed will invoke this method clearing the pane.
   */
  public void clearScreen() {
    adjacencyMatrixKruskal.clear();
    outputKruskal.clear();
    timeArea.clear();
    showTimeBox.setSelected(false);
  }

  /**
   * Upon checkbox being ticked on the Kruskal interface, the time will display.
   */
  public void showTime() {
    timeTaken = endTime - startTime;
    String time = "Time taken for algorithm to evaluate: " + timeTaken / 1000000 + " milliseconds.";
    this.appendTextTime(time);
  }
}
