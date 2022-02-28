import gridgame.backend.Tree;
import gridgame.controller.ColorTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Note the graders will fully test your program to ensure it works
 * These are just some tests to ensure some basics are there
 */

@DisplayName("Some basic checks and balances")
public class Tester {

    @Nested
    @DisplayName("One Level Tests")
    class OneLevel {
        ColorTemplate ct;
        Tree tree;
        @BeforeEach
        void init() {
            ct = ColorTemplate.instance();
            ct.setSeed(1);
            tree = new Tree(1);
        }

        @Test
        @DisplayName("1 DrawableBlock on creation")
        void oneDrawable() {
            assertEquals(1, tree.getBlocks().size());
        }

        @Test
        @DisplayName("one level tree no smash")
        void smashOneLevel() {
            tree.smash();
            assertEquals(1, tree.getBlocks().size());
        }

        @Test
        @DisplayName("one level tree rotate doesn't crash")
        void rotateOneLevel() {
            tree.rotate(false);
            assertEquals(1, tree.getBlocks().size());
        }

        @Test
        @DisplayName("one level tree merge doesn't crash")
        void mergeOneLevel() {
            tree.merge();
            assertEquals(1, tree.getBlocks().size());

        }

        @Test
        @DisplayName("one level tree swap doesn't crash")
        void swapOneLevel() {
            tree.flip(false);
            assertEquals(1, tree.getBlocks().size());
        }

    }

    @Nested
    @DisplayName("Basic Tests")
    class Basics {
        ColorTemplate ct;
        Tree tree;
        @BeforeEach
        void init() {
            ct = ColorTemplate.instance();
            ct.setSeed(1);
            tree = new Tree(5);
        }

        @Test
        @DisplayName("scoring one block only")
        void initialScore(){
            int p1Score= tree.borderLengthWithColor(ColorTemplate.ACTIVE_COLORS[0]);
            int p2Score = tree.diagonalWithColor(ColorTemplate.ACTIVE_COLORS[0]);

            Color c = tree.getBlocks().get(0).color();

            int expectP1 = 0;
            int expectP2 = 0;
            if(c == ColorTemplate.ACTIVE_COLORS[0]) {
                expectP1 = 1024;
                expectP2 = 724;
            }

            assertEquals(expectP1, p1Score);
            assertEquals(expectP2, p2Score);
        }

        @Test
        @DisplayName("smash move down cursor set")
        void smashDown() {
            tree.smash();
            tree.moveDown();
            var blocks = tree.getBlocks();
            int count = 0;
            for(var b : blocks) {
                if (b.isSelected()) {
                    count++;
                }
            }
            assertEquals(1, count);
        }

        @Test
        @DisplayName("smash move down moves into NW")
        void smashDownNW() {
            tree.smash();
            tree.moveDown();
            var blocks = tree.getBlocks();
            int count = 0;
            for(var b : blocks) {
                if (b.isSelected()) {
                    count++;
                    assertEquals(0, b.x());
                    assertEquals(0, b.y());
                }
            }
        }

        @Test
        @DisplayName("move down then back up")
        void upDown() {
            tree.smash();
            tree.moveDown();
            tree.moveUp();
            var blocks = tree.getBlocks();
            int count = 0;
            for(var b : blocks) {
                if (b.isSelected()) {
                    count++;
                    assertEquals(0, b.x());
                    assertEquals(0, b.y());
                    assertFalse(b.isVisible());
                }
            }
            assertEquals(1, count);
        }

        @Test
        @DisplayName("move down then cw")
        void downCW() {
            tree.smash();
            tree.moveDown();
            tree.moveCW();
            var blocks = tree.getBlocks();
            int count = 0;
            for(var b : blocks) {
                if (b.isSelected()) {
                    count++;
                    assertEquals(256, b.x());
                    assertEquals(0, b.y());
                }
            }
            assertEquals(1, count);
        }

        @Test
        @DisplayName("move down then ccw")
        void downCCW() {
            tree.smash();
            tree.moveDown();
            tree.moveCCW();
            var blocks = tree.getBlocks();
            int count = 0;
            for(var b : blocks) {
                if (b.isSelected()) {
                    count++;
                    assertEquals(0, b.x());
                    assertEquals(256, b.y());
                }
            }
            assertEquals(1, count);
        }
    }
}
