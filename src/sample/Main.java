package sample;

import btree.Operations;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;

public class Main extends Application {

    //Initializing necessary variables
    private Operations<Integer> btree;
    private TextField addInput = new TextField();
    private TreeArea BTREEPane;
    private int addValue = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //primaryStage.setScene(new Scene(root, 300, 275));
        btree = new Operations<>();
        double screenWidth = 1500;
        double screenHeight = 700;
        primaryStage.setTitle("B Tree Visualizer");
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);

        //Starting Borderpane
        BorderPane root = new BorderPane();

        //Horizontal Box containing all buttons and input field
        HBox upperArea = new HBox();
        upperArea.setPadding(new Insets(15, 12, 15, 12));
        upperArea.setSpacing(20);
        upperArea.setStyle("-fx-background-color: #4e99e3;");

        //Horizontal Box containing Height and Vertices
        HBox lowerArea =  new HBox();
        lowerArea.setPadding(new Insets(15,12,15,12));
        lowerArea.setSpacing(20);
        lowerArea.setStyle("-fx-background-color: #4e99e3;");


        //Buttons and input fields
        addInput.setText("");
        addInput.setPrefWidth(50);
        Label lbl = new Label("ENTER NODE : ");
        Button insert = new Button("INSERT");
        Button delete = new Button("DELETE");
        Button reset = new Button("RESET");
        Button search = new Button("SEARCH");
        lbl.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14");
        insert.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-font-weight: bold;");
        delete.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-font-weight: bold;");
        reset.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-font-weight: bold;");
        search.setStyle("-fx-background-color: slateblue; -fx-text-fill: white; -fx-font-weight: bold;");
        upperArea.getChildren().addAll(lbl, addInput, insert, delete, search, reset);
        upperArea.setAlignment(Pos.CENTER);
        root.setTop(upperArea);
        //.getChildren().addAll(upperArea);

        //Displaying Height and Vertices
        Label l = new Label("HEIGHT: ");
        //String ss = String.valueOf(BTREEPane.displayHeight(this.btree)) ;
        TextField txt1 = new TextField();
        txt1.setPrefWidth(40);
        txt1.setText("0");
        TextField txt2 = new TextField();
        txt2.setPrefWidth(40);
        txt2.setText("0");
        System.out.println(btree.getHeight());
        Label l2 = new Label("VERTICES: ");
        l.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14");
        l2.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14");
        lowerArea.getChildren().addAll(l,txt1,l2,txt2);
        lowerArea.setAlignment(Pos.CENTER);
        root.setBottom(lowerArea);

        //setting up the handler functions
        insert.setOnMouseClicked(e-> {
            insertNode(addInput.getText());
            txt1.setText(String.valueOf(btree.getHeight()));
            txt2.setText(String.valueOf(btree.getVertices()));
                    });
        delete.setOnMouseClicked(e-> {
            deleteNode(addInput.getText());
            txt1.setText(String.valueOf(btree.getHeight()));
            txt2.setText(String.valueOf(btree.getVertices()));
        });
        search.setOnMouseClicked(e -> searchNode(addInput.getText()));
        reset.setOnMouseClicked(e -> {
            resetTree();
            txt1.setText("0");
            txt2.setText("0");
        });

        AnchorPane anchorPane = new AnchorPane();
        //ScrollPane
        ScrollPane sp = new ScrollPane();
        BTREEPane = new TreeArea(btree, screenWidth/2, 80);
        //sp.setPrefSize(1000, 800);
        sp.setContent(BTREEPane);
        sp.fitToHeightProperty().set(false);
        sp.fitToWidthProperty().set(false);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        sp.setPannable(true);
        //Tree Viewing Section
        //anchorPane.getChildren().addAll(sp);

        root.setCenter(sp);
        // root.getChildren().addAll(sp);
        Scene scene1 = new Scene(root);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }

    private void insertNode(String s) {
        try {
            int num = Integer.parseInt(s);
            System.out.println("Node added is " + num);
            this.btree.add(num);
            System.out.println("First inorder");
            this.btree.inOrder();
            System.out.println("");
            if (this.btree.isEmpty()) {
                System.out.println("its empty qeqeqweqe ");
            }
            System.out.println("Getting root node inside insert node main function");
            System.out.println(this.btree.getRoot().getRightElement());
            if (this.btree.isEmpty()) {
                System.out.println("its empty qeqeqweqasdasdasdasdase ");
            }
            BTREEPane.getChildren().clear();

            System.out.println("Checking what children the tree has");
            System.out.println("Root element is : " + this.btree.getRoot().getLeftElement());
            System.out.println("Root, right element is : " + this.btree.getRoot().getRightElement());
            System.out.println("Root, left child is : " + this.btree.getRoot().getLeftNode());
            System.out.println("Root, middle child is : " + this.btree.getRoot().getMidNode());
            System.out.println("Root, right child is : " + this.btree.getRoot().getRightNode());

            BTREEPane.makeTree(this.btree);
            addInput.setText("");
            System.out.println("Goes here");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format !");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input format !");
            alert.show();
        }
    }

    private void searchNode(String s) {
        try {
            int num = Integer.parseInt(s);
            System.out.println("Node searched is " + num);

            if(btree.contains(num)){
                System.out.println("Searching Now");
                BTREEPane.searchTree(num);
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "The element does not exist !");
                alert.show();
            }


            //BTREEPane.getChildren().clear();
            //BTREEPane.makeTree(btree);
            addInput.setText("");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format !");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input format !");
            alert.show();
        }
    }

    private void deleteNode(String s) {
        try {
            int num = Integer.parseInt(s);
            System.out.println("Node deleted is " + num);
            btree.remove(num);
            BTREEPane.getChildren().clear();
            BTREEPane.makeTree(btree);
            addInput.setText("");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input format !");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid input format !");
            alert.show();
        }
    }


    private void resetTree() {
        try {
            System.out.println("Tree Reset");
            btree.clear();
            BTREEPane.resetTree();
            BTREEPane.getChildren().clear();
            addInput.setText("");

        } catch (Exception e) {
            System.out.println("Error occurred !");
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error occurred !");
            alert.show();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
