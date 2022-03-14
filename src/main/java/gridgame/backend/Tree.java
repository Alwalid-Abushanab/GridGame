package gridgame.backend;

import gridgame.controller.ColorTemplate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//based on a Quad Tree
public class Tree {
    public static final int MAX_SIZE = 512;
    private final int MAX_LEVEL;

    Map<Node,DrawableBlock> map = new HashMap<>(); //to store the nodes

    private Node selectedNode; //the current block (the selected node)

    /**
     * public constructor
     * make sure to use ColorTemplate.instance().randomColorPicker() if you need
     * to generate any colors for your squares
     * @param mxLevels the maximum allowable number of levels in the tree
     *                 ex: if mxLevels is 1 then the tree can only have a root (with no children)
     */
    public Tree(int mxLevels)
    {
        MAX_LEVEL = mxLevels;

        Node root = new Node(1, null);
        root.initializeVar(0,0,ColorTemplate.instance().randomColorPicker(),MAX_SIZE, true, true);

        updateMap(root);
        selectedNode = root;
    }
    //PUBLIC INTERFACE

    /**
     * a method to add nodes to the tree or edit them.
     * @param node the node to be added or edited
     */
    public void updateMap(Node node)
    {
        //create a block
        DrawableBlock block = new DrawableBlock(node.x, node.y, node.size, node.color, node.visible, node.selected);

        //replace the block in the map if it's node is already present, otherwise add the node (as a key) and block (as a value to the key) to the map
        if(map.containsKey(node))
            map.replace(node, block);
        else
            map.put(node,block);
    }

    /**
     * break the block signified by the cursor into 4 children with random colors
     * make sure to use ColorTemplate.instance().randomColorPicker() if you need
     * to generate any colors for your squares. in the case the tree already has mxLevels this
     * method does nothing
     */
    public void smash()
    {
        //check that the current block does not have children, and the max level for the tree has not been reached
        if(selectedNode.NW == null && selectedNode.DEPTH != MAX_LEVEL) //every block has either exactly 4 children or none, so checking the NW child is sufficient
        {
            //create the north-west block and add it to the map
            selectedNode.NW = new Node((selectedNode.DEPTH + 1), selectedNode);
            selectedNode.NW.initializeVar(selectedNode.x, selectedNode.y, ColorTemplate.instance().randomColorPicker(), (selectedNode.size/2), false, true);
            updateMap(selectedNode.NW);

            //create the north-east block and add it to the map
            selectedNode.NE = new Node((selectedNode.DEPTH + 1), selectedNode);
            selectedNode.NE.initializeVar((selectedNode.x + selectedNode.size/2), selectedNode.y, ColorTemplate.instance().randomColorPicker(), (selectedNode.size/2), false, true);
            updateMap(selectedNode.NE);

            //create the south-east block and add it to the map
            selectedNode.SE = new Node((selectedNode.DEPTH + 1), selectedNode);
            selectedNode.SE.initializeVar((selectedNode.x + selectedNode.size / 2), (selectedNode.y + selectedNode.size / 2), ColorTemplate.instance().randomColorPicker(), (selectedNode.size/2), false, true);
            updateMap(selectedNode.SE);

            //create the south-west block and add it to the map
            selectedNode.SW = new Node((selectedNode.DEPTH + 1), selectedNode);
            selectedNode.SW.initializeVar(selectedNode.x, (selectedNode.y + selectedNode.size / 2), ColorTemplate.instance().randomColorPicker(), (selectedNode.size/2), false, true);
            updateMap(selectedNode.SW);

            //make the parent node for these children (current node) invisible
            selectedNode.visible = false;
        }
    }

    /**
     * move the cursor to the parent if possible
     */
    public void moveUp(){
        if(selectedNode.DEPTH != 1)//current block is not the first block in the tree
        {
            selectedNode.selected = false;

            selectedNode = selectedNode.PARENT;
            selectedNode.selected = true;
        }
    }

    /**
     * Move the cursor into the NW child if possible
     */
    public void moveDown(){
        if(selectedNode.NW != null) //current block has children
        {
            selectedNode.selected = false;

            selectedNode = selectedNode.NW;
            selectedNode.selected = true;
        }
    }

    /**
     * move the cursor counter clockwise if possible
     */
    public void moveCCW(){
        if(selectedNode.DEPTH != 1)//current block is not the first block in the tree
        {
            //change the status of the current block to not selected
            selectedNode.selected = false;

            //find which block was selected and move from there to the appropriate next block and change its status to selected.
            if(selectedNode.PARENT.NW == selectedNode)
            {
                selectedNode = selectedNode.PARENT.SW;
                selectedNode.selected = true;
            }
            else if(selectedNode.PARENT.NE == selectedNode)
            {
                selectedNode = selectedNode.PARENT.NW;
                selectedNode.selected = true;
            }
            else if(selectedNode.PARENT.SW == selectedNode)
            {
                selectedNode = selectedNode.PARENT.SE;
                selectedNode.selected = true;
            }
            else if(selectedNode.PARENT.SE == selectedNode)
            {
                selectedNode = selectedNode.PARENT.NE;
                selectedNode.selected = true;
            }
        }
    }

    /**
     * move the cursor in a clockwise way (if possible)
     */
    public void moveCW(){
        if(selectedNode.DEPTH != 1)//current block is not the first block in the tree
        {
            //change the status of the current block to not selected
            selectedNode.selected = false;

            //find which block was selected and move from there to the appropriate next block and change its status to selected.
            if(selectedNode.PARENT.NW == selectedNode)
            {
                selectedNode = selectedNode.PARENT.NE;
                selectedNode.selected = true;
            }
            else if(selectedNode.PARENT.NE == selectedNode)
            {
                selectedNode = selectedNode.PARENT.SE;
                selectedNode.selected = true;
            }
            else if(selectedNode.PARENT.SW == selectedNode)
            {
                selectedNode = selectedNode.PARENT.NW;
                selectedNode.selected = true;
            }
            else if(selectedNode.PARENT.SE == selectedNode)
            {
                selectedNode = selectedNode.PARENT.SW;
                selectedNode.selected = true;
            }
        }
    }

    /**
     * swap/flip the structure from the cursor position
     * @param horizontally if true the flip should happen over the a horizonal plane (i.e,. the top flips with the bottom)
     *                     if false the flip should happen over a vertical plane
     */
    public void flip (boolean horizontally) {

        //if the current block has no children, then there is nothing to flip
        if(selectedNode.NW == null)
        {
            return;
        }

        if(horizontally)//flip horizontally
        {
            //change the x and y coordinates of each of the blocks
            selectedNode.NW.x = selectedNode.x;
            selectedNode.NW.y = selectedNode.y + selectedNode.size/2;

            selectedNode.NE.x = selectedNode.x + selectedNode.size/2;
            selectedNode.NE.y = selectedNode.y + selectedNode.size/2;

            selectedNode.SE.x = selectedNode.x + selectedNode.size/2;
            selectedNode.SE.y = selectedNode.y;

            selectedNode.SW.x = selectedNode.x;
            selectedNode.SW.y = selectedNode.y;

            //recursively check if any of the children has children and flip them as well
            selectedNode = selectedNode.NW;
            flip(true);
            selectedNode = selectedNode.PARENT.NE;
            flip(true);
            selectedNode = selectedNode.PARENT.SE;
            flip(true);
            selectedNode = selectedNode.PARENT.SW;
            flip(true);
            selectedNode = selectedNode.PARENT;

            //chang the children's pointers to point to the appropriate block
            Node temp1  = selectedNode.NW;
            Node temp2  = selectedNode.NE;
            selectedNode.NW = selectedNode.SW;
            selectedNode.NE = selectedNode.SE;
            selectedNode.SW = temp1;
            selectedNode.SE = temp2;
        }
        else //flip vertically
        {
            //change the x and y coordinates of each of the blocks
            selectedNode.NW.x = selectedNode.x + selectedNode.size/2;
            selectedNode.NW.y = selectedNode.y;

            selectedNode.NE.x = selectedNode.x;
            selectedNode.NE.y = selectedNode.y;

            selectedNode.SE.x = selectedNode.x;
            selectedNode.SE.y = selectedNode.y + selectedNode.size/2;

            selectedNode.SW.x = selectedNode.x + selectedNode.size/2;
            selectedNode.SW.y = selectedNode.y + selectedNode.size/2;

            //recursively check if any of the children has children and flip them as well
            selectedNode = selectedNode.NW;
            flip(false);
            selectedNode = selectedNode.PARENT.NE;
            flip(false);
            selectedNode = selectedNode.PARENT.SE;
            flip(false);
            selectedNode = selectedNode.PARENT.SW;
            flip(false);
            selectedNode = selectedNode.PARENT;

            //chang the children's pointers to point to the appropriate block
            Node temp1  = selectedNode.NW;
            Node temp2  = selectedNode.SW;
            selectedNode.NW = selectedNode.NE;
            selectedNode.NE = temp1;
            selectedNode.SW = selectedNode.SE;
            selectedNode.SE = temp2;
        }
    }

    /**
     * Rotate the structure from the cursor position
     * @param clockwise true if the rotation should be clockwise and false for ccw
     */
    public void rotate(boolean clockwise)
    {
        //if the current block has no children, then there is nothing to rotate
        if(selectedNode.NW == null)
        {
            return;
        }

        //change the x and y coordinates of each of the blocks depending on whither the rotation is clockwise or not
        if(clockwise)
        {
            selectedNode.NW.x = selectedNode.x + selectedNode.size/2;
            selectedNode.NW.y = selectedNode.y;

            selectedNode.NE.x = selectedNode.x + selectedNode.size/2;
            selectedNode.NE.y = selectedNode.y + selectedNode.size/2;

            selectedNode.SE.x = selectedNode.x;
            selectedNode.SE.y = selectedNode.y + selectedNode.size/2;

            selectedNode.SW.x = selectedNode.x;
            selectedNode.SW.y = selectedNode.y;
        }
        else
        {
            selectedNode.NW.x = selectedNode.x;
            selectedNode.NW.y = selectedNode.y + selectedNode.size/2;
            updateMap(selectedNode.NW);

            selectedNode.NE.x = selectedNode.x;
            selectedNode.NE.y = selectedNode.y;

            selectedNode.SE.x = selectedNode.x + selectedNode.size/2;
            selectedNode.SE.y = selectedNode.y;

            selectedNode.SW.x = selectedNode.x + selectedNode.size/2;
            selectedNode.SW.y = selectedNode.y + selectedNode.size/2;
        }

        //recursively check if any of the children has children and flip them as well
        selectedNode = selectedNode.NW;
        rotate(clockwise);
        selectedNode = selectedNode.PARENT.NE;
        rotate(clockwise);
        selectedNode = selectedNode.PARENT.SW;
        rotate(clockwise);
        selectedNode = selectedNode.PARENT.SE;
        rotate(clockwise);
        selectedNode = selectedNode.PARENT;

        //chang the children's pointers to point to the appropriate block
        if(clockwise)
        {
            Node temp1 = selectedNode.NW;
            Node temp2 = selectedNode.NE;
            selectedNode.NW = selectedNode.SW;
            selectedNode.NE = temp1;
            selectedNode.SW = selectedNode.SE;
            selectedNode.SE = temp2;
        }
        else
        {
            Node temp1 = selectedNode.NW;
            Node temp2 = selectedNode.SW;
            selectedNode.NW = selectedNode.NE;
            selectedNode.NE = selectedNode.SE;
            selectedNode.SE = temp2;
            selectedNode.SW = temp1;
        }

    }

    /**
     * Merge the children into the parent
     * The majority color of the children becomes the parent's color (or in the case of no majority
     * the NW child becomes the parent color
     * children that are subdivided should first recursively merge so that the parent can determine
     * if there is a majority
     * The children are deleted after the merge happens
     */
    public void merge()
    {
        Color result;

        //if the current block has no children, then there is nothing to merge
        if(selectedNode.NW == null) {
            return;
        }
        else
        {
            //recursively merge the children first
            selectedNode = selectedNode.NW;
            merge();
            selectedNode = selectedNode.PARENT.NE;
            merge();
            selectedNode = selectedNode.PARENT.SW;
            merge();
            selectedNode = selectedNode.PARENT.SE;
            merge();
            selectedNode = selectedNode.PARENT;
        }

        /*
        if each 2 blocks of the 4 have the same color, then the block at position NW will be the resulting color
        if each block has different color, then the NW will be the resulting color
        if 2 or more blocks have the same color, then that color will be the resulting color
         */
        if(selectedNode.NW.color == selectedNode.NE.color || selectedNode.NW.color == selectedNode.SW.color || selectedNode.NW.color == selectedNode.SE.color)
        {
            result = selectedNode.NW.color;
        }
        else if(selectedNode.NE.color == selectedNode.SE.color || selectedNode.NE.color == selectedNode.SW.color)
        {
            result = selectedNode.NE.color;
        }
        else if(selectedNode.SE.color == selectedNode.SW.color)
        {
            result = selectedNode.SE.color;
        }
        else
        {
            result = selectedNode.NW.color;
        }

        //change the node color and make it visible
        selectedNode.color = result;
        selectedNode.visible = true;

        //destroy all the children
        map.remove(selectedNode.NW);
        selectedNode.NW = null;
        map.remove(selectedNode.NE);
        selectedNode.NE = null;
        map.remove(selectedNode.SW);
        selectedNode.SW = null;
        map.remove(selectedNode.SE);
        selectedNode.SE = null;
    }

    /**
     * Compute player 1's score as the length of border matching the given color
     * @param c the color to match
     * @return player 1's score
     */
    public int borderLengthWithColor(Color c)
    {
        int score = 0;

        //go through the map node by node and look for the blocks on the diagonal
        for(Node elem:  map.keySet())
        {
            if(elem.color == c && elem.visible) //the block has the same as the given color, and it is visible
            {
                if(elem.x == 0)//left border
                    score+= elem.size;

                if((elem.size + elem.x == MAX_SIZE))//right border
                    score+= elem.size;

                if(elem.y == 0)//top border
                    score+= elem.size;

                if((elem.size + elem.y == MAX_SIZE))//bottom border
                    score+= elem.size;

            }
        }
        return score;
    }

    /**
     * Compute player 2's score (as the rounded length of the diagonal matching the given color)
     * @param c the target color
     * @return player 2's score
     */
    public int diagonalWithColor(Color c)
    {
        int score = 0;

        //go through the map node by node and look for the blocks on the border
        for(Node elem: map.keySet())
        {
            if(elem.color == c && elem.visible) //the block has the same as the given color, and it is visible
            {
                //check if it's on th diagonal
                if(elem.x == elem.y)
                {
                    score+= Math.round(elem.size * Math.sqrt(2));
                }

                if(elem.x == MAX_SIZE - (elem.y  + elem.size))
                {
                    score+= Math.round(elem.size * Math.sqrt(2));
                }
            }
        }
        return score;
    }

    /**
     * @return the List of drawable blocks, so they can be rendered to screen
     */
    public ArrayList<DrawableBlock> getBlocks()
    {
        ArrayList<DrawableBlock> list = new ArrayList<>();

        //go through the map node by node and store them in an arraylist
        for(Node elem: map.keySet())
        {
            updateMap(elem);
            list.add(map.get(elem));
        }

        return list;
    }

    /**
     * this Node class is to mimic a tree.
     * each node can have a parent node, and children nodes.
     * each node has variables for coordinates, visibility, color, size, and selection.
     */
    private static class Node{

        private final int DEPTH;
        private int x,y,size;

        /*
            each node can have a parent, and 4 or 0 children.4
            The children blocks are represented as follows

                NW          NE

                SW          SE
         */
        private final Node PARENT;
        private Node NW,NE,SW,SE;

        private Color color;

        private boolean selected;
        private boolean visible;

        /**
         * a constructor method to create a node
         * @param depth the depth of this node
         * @param parent the parent node for this node, null if it does not have a parent
         */
        public Node(int depth, Node parent)
        {
            this.DEPTH =  depth;
            this.PARENT = parent;
        }

        /**
         * a method to initialize the variables of the node
         * @param x the x coordinate for the node
         * @param y the y coordinate for the node
         * @param color the color of the node
         * @param size the size of the node
         * @param selected whither the node is selected or not (true for selected)
         * @param visible whither the node is visible or not (true for visible)
         */
        public void initializeVar(int x, int y, Color color, int size, boolean selected, boolean visible)
        {
            this.x = x;
            this.y = y;
            this.color = color;
            this.size = size;
            this.selected = selected;
            this.visible = visible;
        }
    }
}
