package ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * A class to launch the MainView.fxml. It acts as a bridge between the View and
 * the Controller class in MVC style architecture. This is the View part of the
 * MVC pattern.
 * 
 * @author Patricia.
 *
 */
public class MainView extends Application {

  @Override
  public void start(Stage primaryStage) {
    try {
      AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("MainView.fxml"));
      Scene scene = new Scene(root, 950, 600);
      scene.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.show();

      // set the application icon
      primaryStage.getIcons().add(new Image("file:resources/images/if_i-cursor_1608366.png"));

      // set application title
      primaryStage.setTitle("GAiA");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static volatile MainView instance = null;

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
  public static synchronized MainView getInstance() {
    if (instance == null) {
      new Thread(() -> Application.launch(MainView.class)).start();
    }
    while (instance == null) {
    }
    return instance;
  }

  @FXML
  private AnchorPane mainView;

  @FXML
  private Circle vertexColouringCircle;

  @FXML
  private Circle hamiltonianCircle;

  @FXML
  private Circle kruskalsAlgorithmCircle;

  @FXML
  private Circle dijkstrasAlgorithmCircle;

  @FXML
  private Text kruskalText;

  @FXML
  private Text hamiltonianText;

  @FXML
  private Text vertexColText;

  @FXML
  private Text dijkstraText;

  /**
   * Upon clicking the vertex colouring "bubble", Vertex Colouring view will be
   * launched.
   */
  public void loadVertexColouring() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("VertexColouringView.fxml"));
    Parent vertexCol = null;
    try {
      vertexCol = (Parent) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Stage stage = new Stage();
    stage.setScene(new Scene(vertexCol));
    stage.show();

    // set the application icon
    stage.getIcons().add(new Image("file:resources/images/if_i-cursor_1608366.png"));

    // set application title
    stage.setTitle("GAiA");
  }

  /**
   * Upon clicking the dijkstra "bubble", Dijkstra view will be launched.
   */
  public void loadDijkstras() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("DijkstraView.fxml"));
    Parent dijkstra = null;
    try {
      dijkstra = (Parent) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Stage stage = new Stage();
    stage.setScene(new Scene(dijkstra));
    stage.show();

    // set the application icon
    stage.getIcons().add(new Image("file:resources/images/if_i-cursor_1608366.png"));

    // set application title
    stage.setTitle("GAiA");
  }

  /**
   * Upon clicking the hamiltonian "bubble", Hamiltonian view will be launched.
   */
  public void loadHamiltonian() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("HamiltonianView.fxml"));
    Parent hamiltonian = null;
    try {
      hamiltonian = (Parent) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Stage stage = new Stage();
    stage.setScene(new Scene(hamiltonian));
    stage.show();

    // set the application icon
    stage.getIcons().add(new Image("file:resources/images/if_i-cursor_1608366.png"));

    // set application title
    stage.setTitle("GAiA");
  }

  /**
   * Upon clicking the kruskal "bubble", Kruskal view will be launched.
   */
  public void loadKruskal() {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("KruskalView.fxml"));
    Parent kruskal = null;
    try {
      kruskal = (Parent) loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Stage stage = new Stage();
    stage.setScene(new Scene(kruskal));
    stage.show();

    // set the application icon
    stage.getIcons().add(new Image("file:resources/images/if_i-cursor_1608366.png"));

    // set application title
    stage.setTitle("GAiA");
  }

}
