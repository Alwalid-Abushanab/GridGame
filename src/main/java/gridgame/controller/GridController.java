package gridgame.controller;

import gridgame.backend.DrawableBlock;
import gridgame.backend.Tree;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;


//class to handle the commands for the Game
public class GridController implements KeyListener {

    public static int TOTAL_TURNS = 20;
    public static final boolean P1_TURN = false;
    public static final boolean P2_TURN = true;

    private boolean playerTurn = P1_TURN;


    /**
     * keyboard commands
     */
    public final int UP = 38;
    public final int LEFT = 37;
    public final int RIGHT = 39;
    public final int DOWN = 40;

    public final int SMASH = 83;
    public final int ROTATE_CW = 67;
    public final int ROTATE_CCW = 87;
    public final int SWAP_H = 72;
    public final int SWAP_V = 86;
    public final int MERGE = 77;

    //keep track of turnsLeft
    private int turnsLeft = TOTAL_TURNS;

    //listen for keyboard presses and force redraws

    private Tree grid;

    private WeakReference<Updater> delegate;

    private Color [] gameColors;

    public interface Updater {
        void updateDrawing(ArrayList<DrawableBlock> gameBoard);
        void updateScore(int p1Score, int p2Score);
        void updatePlayer(boolean player, int turnsLeft);
    }

    public GridController(Updater u) {
        gameColors = ColorTemplate.ACTIVE_COLORS;

        delegate = new WeakReference<Updater>(u);
        grid = new Tree(6);

        delegate.get().updateDrawing(grid.getBlocks());
        int p1Score = grid.borderLengthWithColor(gameColors[0]);
        int p2Score = grid.diagonalWithColor(gameColors[0]);

        delegate.get().updateScore(p1Score,p2Score);
    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(turnsLeft == 0) {
            return;
        }

        boolean turnOver = false;

        switch(e.getKeyCode()) {

            case UP: grid.moveUp();
                     System.out.println("up");
                     break;

            case DOWN:  grid.moveDown();
                        System.out.println("dn");
                        break;

            case LEFT:  grid.moveCCW();
                        System.out.println("ccw");
                        break;

            case RIGHT: grid.moveCW();
                        System.out.println("cw");
                        break;

            case SMASH: grid.smash();
                        System.out.println("smash");
                        turnOver = true;
                        break;

            case ROTATE_CCW: grid.rotate(false);
                             System.out.println("rotate ccw");
                             turnOver = true;
                             break;

            case ROTATE_CW: grid.rotate(true);
                            System.out.println("rotate cw");
                            turnOver = true;
                            break;

            case SWAP_H: grid.flip(true);
                         System.out.println("flip h");
                         turnOver = true;
                         break;

            case SWAP_V: grid.flip(false);
                         System.out.println("flip v");
                         turnOver = true;
                         break;

            case MERGE:  grid.merge();
                         System.out.println("merge");
                         turnOver = true;
                         break;

            default: System.out.println("Invalid Key");
        }

        if (turnOver) {
            if(playerTurn == P2_TURN) {
                turnsLeft--;
            }
            playerTurn = !playerTurn;
            delegate.get().updatePlayer(playerTurn, turnsLeft);
        }

        delegate.get().updateDrawing(grid.getBlocks());

        int p1Score = grid.borderLengthWithColor(gameColors[0]);
        int p2Score = grid.diagonalWithColor(gameColors[0]);

        delegate.get().updateScore(p1Score,p2Score);
    }
}
