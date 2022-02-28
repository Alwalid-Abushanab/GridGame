package gridgame.backend;

import gridgame.controller.ColorTemplate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

//based on a Quad Tree
public class Tree {
    public static final int MAX_SIZE = 512;

    /**
     * public constructor
     * make sure to use ColorTemplate.instance().randomColorPicker() if you need
     * to generate any colors for your squares
     * @param mxLevels the maximum allowable number of levels in the tree
     *                 ex: if mxLevels is 1 then the tree can only have a root (with no children)
     */
    public Tree(int mxLevels) {

    }
    //PUBLIC INTERFACE

    /**
     * break the block signified by the cursor into 4 children with random colors
     * make sure to use ColorTemplate.instance().randomColorPicker() if you need
     * to generate any colors for your squares. in the case the tree already has mxLevels this
     * method does nothing
     */
    public void smash(){

    }

    /**
     * move the cursor to the parent if possible
     */
    public void moveUp(){

    }

    /**
     * Move the cursor into the NW child if possible
     */
    public void moveDown(){

    }

    /**
     * move the cursor counter clockwise if possible
     */
    public void moveCCW(){

    }

    /**
     * move the cursor in a clockwise way (if possible)
     */
    public void moveCW(){

    }

    /**
     * swap/flip the structure from the cursor position
     * @param horizontally if true the flip should happen over the a horizonal plane (i.e,. the top flips with the bottom)
     *                     if false the flip should happen over a vertical plane
     */
    public void flip (boolean horizontally) {
    }

    /**
     * Rotate the structure from the cursor position
     * @param clockwise true if the rotation should be clockwise and false for ccw
     */
    public void rotate(boolean clockwise){

    }

    /**
     * Merge the children into the parent
     * The majority color of the children becomes the parent's color (or in the case of no majority
     * the NW child becomes the parent color
     * children that are subdivided should first recursively merge so that the parent can determine
     * if there is a majority
     * The children are deleted after the merge happens
     */
    public void merge() {

    }

    /**
     * Compute player 1's score as the length of border matching the given color
     * @param c the color to match
     * @return player 1's score
     */
    public int borderLengthWithColor(Color c) {
        return 0;
    }

    /**
     * Compute player 2's score (as the rounded length of the diagonal matching the given color)
     * @param c the target color
     * @return player 2's score
     */
    public int diagonalWithColor(Color c) {
        return 0;
    }

    /**
     * @return the List of drawable blocks so they can be rendered to screen
     */
    public ArrayList<DrawableBlock> getBlocks() {
        //Replace this code with your own
        //this generates a random color block and returns it in a list
        DrawableBlock db = new DrawableBlock(0,0,512,ColorTemplate.instance().randomColorPicker(), true,true);
        ArrayList<DrawableBlock> list = new ArrayList<>();
        list.add(db);

        return list;
    }
}
