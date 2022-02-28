package gridgame.frontend;

import javax.swing.*;

import gridgame.backend.DrawableBlock;
import java.awt.*;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The game board
 *
 * Do not make changes in this file
 */
public class GridGUI extends JComponent {

    private ArrayList<DrawableBlock> rectangles;

    public GridGUI(int width, int height) {
        super();
        setSize(width, height);
    }

    //update the squares and redraw
    public void updateDrawing(ArrayList<DrawableBlock> rects) {
        rectangles = rects;
        repaint();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        if(rectangles != null) {
            DrawableBlock selected = rectangles.get(0);
            for(DrawableBlock r : rectangles) {
                if (r.isSelected()) {
                    selected = r;
                }
                if(r.isVisible()) {
                    Color clr = r.color();
                    g.setColor(clr);
                    int x = r.x();
                    int y = r.y();
                    g.fillRect(x, y, r.size(), r.size());
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, r.size(), r.size());
                }
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(5));
            g2.setColor(Color.YELLOW);
            g2.drawRect(selected.x(), selected.y(), selected.size(), selected.size());
        }
    }
}
