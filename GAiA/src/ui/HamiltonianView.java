package ui;

import application.HamiltonianAlgorithm;
import application.NoSolutionException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * View class for the Hamiltonian Algorithm launching its corresponding fxml
 * file as well as handing simple user requests.
 * 
 * @author Patricia.
 *
 */
public class HamiltonianView extends Application {
  private double startTime;
  private double endTime;
  private double timeTaken;

  @Override
  public void start(Stage secondaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader
          .load(getClass().getResource("HamiltonianView.fxml"));
      Scene scene = new Scene(root, 750, 550);
      scene.getStylesheets().add(getClass().getResource("hamiltonian.css").toExternalForm());
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

  private static volatile HamiltonianView instance = null;

  /**
   * Main method to launch the HamiltonianView.fxml
   */
  public static void main(String[] args) {
    launch(args);
  }

  @FXML
  void initialize() {
    instance = this;
  }

  /**
   * This method will create a new thread and initialise the GUI.
   */
  public static synchronized HamiltonianView getInstance() {
    if (instance == null) {
      new Thread(() -> Application.launch(HamiltonianView.class)).start();
    }
    while (instance == null) {
    }
    return instance;
  }

  public String getMatrix() {
    return this.adjacencyMatrixHamiltonian.getText();
  }

  public void appendTextOutput(String text) {
    outputHamiltonian.clear();
    this.outputHamiltonian.appendText(text);
  }

  public void appendTextTime(String time) {
    timeArea.clear();
    this.timeArea.appendText(time);
  }

  /*
   * fx:ids are specified here for important elements of the scene.
   */
  @FXML
  private AnchorPane hamiltonianView;

  @FXML
  private TextArea adjacencyMatrixHamiltonian;

  @FXML
  private TextArea outputHamiltonian;

  @FXML
  private Button calculateHamiltonian;

  @FXML
  private RadioButton hamiltonianCycle;

  @FXML
  private RadioButton hamiltonianPath;

  @FXML
  private Button clearHamiltonian;

  @FXML
  private TextArea timeArea;

  @FXML
  private CheckBox timeCheck;

  /**
   * Upon "Calculate" button clicked, calculateHamiltonian() method is invoked
   * taking the user input as a string, converting this into int[][] form which
   * is evaluated by the Hamiltonian algorithm class resulting in a path in the
   * output TextArea of the Pane.
   */
  public void calculateHamiltonian() {
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

      HamiltonianAlgorithm ham = new HamiltonianAlgorithm(adjMatrix);

      String solution = null;

      try {
        solution = ham.runHamiltonianAlgorithm();
      } catch (NoSolutionException e) {
        e.printStackTrace();
      }

      this.appendTextOutput(solution.toString());

    } catch (IndexOutOfBoundsException iob) {
      outputHamiltonian.appendText("Please adhere to the suggested format");
      return;
    }
    endTime = System.nanoTime();
  }

  /**
   * "Clear" button on pressed will invoke this method clearing the pane.
   */
  public void clearScreen() {
    adjacencyMatrixHamiltonian.clear();
    outputHamiltonian.clear();
    timeArea.clear();
    timeCheck.setSelected(false);
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
