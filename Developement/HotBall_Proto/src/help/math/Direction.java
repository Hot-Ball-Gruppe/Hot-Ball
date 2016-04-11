/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package help.math;

/**
 *
 * @author Felix
 */
    public enum Direction {

        Up, Left, Down, Right;

        public int dx() {
            switch (this) {
                case Up:
                    return 0;
                case Left:
                    return -1;
                case Down:
                    return 0;
                case Right:
                    return 1;
                default:
                    throw new AssertionError();
            }
        }

        public int dy() {
            switch (this) {
                case Up:
                    return -1;
                case Left:
                    return 0;
                case Down:
                    return 1;
                case Right:
                    return 0;
                default:
                    throw new AssertionError();
            }
        }

        public static Direction fromDxDy(int dx, int dy) {
            switch (dx + 2 * dy) {
                case -2:
                    return Up;
                case -1:
                    return Left;
                case 2:
                    return Down;
                case 1:
                    return Right;
                default:
                    System.out.println("invalid Revelsal from int-int to Dir: " + dx + "/" + dy);
                    return null;
            }
        }

        public static Direction randomDirection() {
            return values()[(int) (Math.random() * values().length)];
        }
    }
