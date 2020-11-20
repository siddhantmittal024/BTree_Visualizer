package sample;

import btree.Operations;
import javafx.animation.FillTransition;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import btree.Node;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class TreeArea extends Pane {
    private Operations<Integer> btree;
    private double startX, startY;
    private double nodeWidth = 35;
    private double searchNode = -1;

    public TreeArea(Operations<Integer> btree, double startX, double startY) {
        //this.setPrefSize(width, 800);
        //System.out.println("going in tree area constructor");
        this.btree = btree;
        this.startX = startX;
        this.startY = startY;
    }

    private void drawNode(int num, double posX, double posY, boolean isSearching) {
        String s = Integer.toString(num);
        Rectangle node = new Rectangle(posX, posY, nodeWidth, nodeWidth);
        if (isSearching && num == searchNode) {
            node.setFill(Color.GREEN);
        } else {
            node.setFill(Color.rgb(24, 71, 102));
        }
        node.setArcWidth(15);
        node.setArcHeight(15);
        Text nodeValue = new Text(posX + 13 - s.length(), posY + 20, s);
        nodeValue.setStyle("-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 14");
        nodeValue.setFill(Color.WHITE);
        nodeValue.setStrokeWidth(3);
        this.getChildren().addAll(node, nodeValue);
    }

    private void drawTree(Node root, double posX, double posY, double lengthX, double lengthY, boolean isSearch) {

        //if root is not null, hence tree exists
        try {
            if (root != null || root.getLeftElement() == null) {

                //Left element always exist, so rendering it first
                drawNode((Integer) root.getLeftElement(), posX, posY, isSearch);

                //If right element exists, then render that
                if (!(root.getRightElement() == null)) {
                    drawNode((Integer) root.getRightElement(), posX + nodeWidth, posY, isSearch);
                }

                //Going through all the children of the current root
                //ArrayList<Node> children = new ArrayList<>();
                HashMap<Integer, Node> children;
                if (root.getRightElement() == null) {
                    //right exists
                    children = new HashMap<>();
                    if (root.getLeftNode() != null) {
                        System.out.println("Left Node Added");
                        children.put(-1, root.getLeftNode());
                    }

                    if (root.getMidNode() != null) {
                        System.out.println("Middle node added");
                        children.put(1, root.getMidNode());
                    }
//                    if (root.getRightNode() != null) {
//                        System.out.println("Right node added");
//                        children.put(1, root.getRightNode());
//                    }
                } else {
                    children = new HashMap<>();
                    if (root.getLeftNode() != null) {
                        System.out.println("Left Node Added");
                        children.put(-1, root.getLeftNode());
                    }

                    if (root.getMidNode() != null) {
                        System.out.println("Middle node added");
                        children.put(0, root.getMidNode());
                    }

                    if (root.getRightNode() != null) {
                        System.out.println("Right node added");
                        children.put(1, root.getRightNode());
                    }
                }


                if (root.isLeaf()) {
                    //Its a leaf node, so no children, hence no more things need to be rendered
                } else {
                    double updatedY = posY + lengthY;

                    for (int i : children.keySet()) {
                        System.out.println(i);
                        double updatedX = posX + i * lengthX;
                        Line line = new Line(posX+15, posY + nodeWidth, updatedX+10, updatedY);
                        line.setStroke(Color.BLACK);
                        line.setStrokeWidth(3);
                        this.getChildren().add(line);
                        drawTree(children.get(i), updatedX, updatedY, 0.65 * lengthX, 1.2 * lengthY, isSearch);
                    }
                }

            }
        } catch (NullPointerException e) {
            System.out.println("Null pointer error occurred");
        }
    }

    public void searchTree(int nodeValue) {
        try {
            this.searchNode = nodeValue;
            drawTree(btree.getRoot(), startX, startY, 300, 130, true);
        } catch (Exception e) {
            System.out.println("Error Occurred !");
        }
    }

    public void makeTree(Operations<Integer> newBTree) {
        this.btree = new Operations<>();
//        System.out.println("Inorder from paramaterized makeTree");
//        newBTree.inOrder();
//        System.out.println("Making a new tree");
        this.btree = newBTree;
        //System.out.println("InOrder inside make tree");

        this.btree.inOrder();
        drawTree(btree.getRoot(), startX, startY, 300, 130, false);
        //System.out.println(btree.getRoot());
    }

    public void resetTree() {
        this.btree = new Operations<>();
        this.btree.clear();
        drawTree(btree.getRoot(), startX, startY, 300, 130, false);
    }
}
