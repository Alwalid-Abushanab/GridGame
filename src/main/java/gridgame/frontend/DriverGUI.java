package gridgame.frontend;
import gridgame.backend.Tree;

import javax.swing.*;
import java.awt.*;

/**
 * Main program entry point
 */
public class DriverGUI {

    public static void main(String [] args) {

        JFrame frame = new JFrame("Grid Game");

        GridRenderer renderer = new GridRenderer();

        frame.add(renderer);

        frame.setSize(new Dimension(Tree.MAX_SIZE+3, Tree.MAX_SIZE+175));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
