package gridgame.frontend;

import gridgame.backend.DrawableBlock;
import gridgame.backend.Tree;
import gridgame.controller.ColorTemplate;
import gridgame.controller.GridController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//class to draw the Grid onto the Screen
//DO NOT MAKE CHANGES IN THIS FILE
public class GridRenderer extends JPanel implements GridController.Updater {

    JLabel scoreFieldP1;
    JLabel scoreLabelP1;

    JLabel scoreFieldP2;
    JLabel scoreLabelP2;

    JLabel currentTurn;
    JLabel turnsRemaining;

    JPanel northPanel;
    JPanel southPanel;

    GridGUI gameBoard;

    GridController controller;

    public GridRenderer() {
        super();

        setFocusable(true);

        //setBackground(new Color(252, 246, 212, 80));
        setLayout(new BorderLayout());

        JLabel curTurn = new JLabel ("Current Turn:");
        currentTurn = new JLabel("P1");

        scoreFieldP1 = new JLabel("0");
        scoreLabelP1 = new JLabel("P1 Score:");


        JLabel spacesSm = new JLabel("   ");
        JLabel spacesLg = new JLabel("   ");

        scoreFieldP2 = new JLabel("0");
        scoreLabelP2 = new JLabel("P2 Score:");

        JLabel remaining = new JLabel("Turns Remaining:");
        turnsRemaining = new JLabel(String.valueOf(GridController.TOTAL_TURNS));

        northPanel = new JPanel();
        northPanel.setBackground(new Color(252, 246, 212, 80));


        northPanel.add(curTurn);
        northPanel.add(currentTurn);
        northPanel.add(spacesLg);
        northPanel.add(scoreLabelP1);
        northPanel.add(scoreFieldP1);
        northPanel.add(spacesSm);

        northPanel.add(scoreLabelP2);
        northPanel.add(scoreFieldP2);

        northPanel.add(remaining);
        northPanel.add(turnsRemaining);

        add(northPanel, BorderLayout.NORTH);

        setGrid();
        setInstructions();
    }

    @Override
    public boolean isFocusable() {
        return true;
    }

    private void setGrid() {

        gameBoard = new GridGUI(Tree.MAX_SIZE , Tree.MAX_SIZE);
        controller = new GridController(this);
        addKeyListener(controller);
        add(gameBoard, BorderLayout.CENTER);
    }

    private void setInstructions() {

        String targetColor = "Player 1 score is total length of outside edges matching: ";

        JLabel p1Instructions = new JLabel(targetColor);

        String targetBlob = "Player 2 score is max total connected blob size matching: ";
        JLabel p2Instructions = new JLabel(targetBlob);

        String spaces = "    ";
        JLabel blue = new JLabel(spaces);

        blue.setOpaque(true);
        blue.setBackground(ColorTemplate.ACTIVE_COLORS[0]);

        JLabel blue2 = new JLabel(spaces);

        blue2.setOpaque(true);
        blue2.setBackground(ColorTemplate.ACTIVE_COLORS[0]);

        String up = "\u2191 mv up";
        String left = "\u2190 mv CCW";
        String right = "\u2192 mv CW";
        String down = "\u2193 mv down";

        String smash = "S smash";
        String rotateCW = "C rot CW";
        String rotateCCW = "W rot CCW";
        String swapH = "H flip horizonal";
        String swapV = "V flip vertical";
        String merge = "M merge";

        JLabel mergeLabel = new JLabel(merge);
        JLabel smashLabel = new JLabel(smash);
        JLabel rotCLabel = new JLabel(rotateCW);
        JLabel rotCCLabel = new JLabel(rotateCCW);
        JLabel swapHLabel = new JLabel(swapH);
        JLabel swapVLabel = new JLabel(swapV);
        mergeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        smashLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rotCLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        rotCCLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        swapHLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        swapVLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel upLabel = new JLabel(up);
        upLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel dnLabel = new JLabel(down);
        dnLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel rightLabel = new JLabel(right);
        rightLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JLabel leftLabel = new JLabel(left);
        leftLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel southSouth = new JPanel();
        southSouth.add(upLabel);
        southSouth.add(dnLabel);
        southSouth.add(rightLabel);
        southSouth.add(leftLabel);

        JPanel southSouthSouth = new JPanel();
        southSouthSouth.add(smashLabel);
        southSouthSouth.add(rotCCLabel);
        southSouthSouth.add(rotCLabel);
        southSouthSouth.add(swapHLabel);
        southSouthSouth.add(swapVLabel);
        southSouthSouth.add(mergeLabel);

        JPanel northNorthSouth = new JPanel();

        northNorthSouth.add(p1Instructions);
        northNorthSouth.add(blue);

        JPanel northSouthSouth = new JPanel();
        northSouthSouth.add(p2Instructions);
        northSouthSouth.add(blue2);

        JPanel northSouth = new JPanel();
        northSouth.add(northNorthSouth);
        northSouth.add(northSouthSouth);
        northSouth.setLayout(new BoxLayout(northSouth, BoxLayout.Y_AXIS));

        southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(northSouth);
        southPanel.add(southSouth);
        southPanel.add(southSouthSouth);

        southPanel.setBackground(new Color(252, 246, 212, 80));

        add(southPanel, BorderLayout.SOUTH);
    }

    public void updateDrawing(ArrayList<DrawableBlock> rectangles) {
        gameBoard.updateDrawing(rectangles);
    }

    public void updateScore(int p1Score, int p2Score) {
        scoreFieldP1.setText(String.valueOf(p1Score));
        scoreFieldP2.setText(String.valueOf(p2Score));
    }

    public void updatePlayer(boolean playerTurn, int turnsLeft) {
        if(playerTurn == GridController.P1_TURN) {
            currentTurn.setText("P1");
        }
        else {
            currentTurn.setText("P2");
        }
        turnsRemaining.setText(String.valueOf(turnsLeft));
    }

}
