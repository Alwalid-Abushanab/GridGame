package gridgame.backend;

import java.awt.Color;

/**
 * The information needed to draw blocks on the screen
 * Your Tree should produce an ArrayList of these so the Controller can send them to the FrontEnd
 *
 * @param x the x coordinate of the top left corner of the block
 * @param y the y coordinate of the top left corner of the block
 * @param size the size of one length of the block (square shape)
 * @param color the color of this block (only important if the block is visible
 * @param isVisible is this block supposed to have its interior color filled in
 * @param isSelected is this the block that has the yellow cursor around it
 */
public record DrawableBlock (int x, int y, int size, Color color, boolean isVisible, boolean isSelected ) { }