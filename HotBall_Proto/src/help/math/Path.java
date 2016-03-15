/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help.math;

import java.util.List;

/**
 *
 * @author Dromlius
 */
public class Path {

    private final List<Position> path;

    public Path(List<Position> path) {
        this.path = path;
    }

    public boolean iterate(Position.DoublePosition p, double distance) {
        if (isEmpty()) {
            return true;
        }
        Position pathPoint = path.get(0);
        double pointDist = p.getDistance(pathPoint);
        Vector v = new Vector(p.getX() - pathPoint.getX(), p.getY() - pathPoint.getY());
        if (pointDist < distance) {
            v.setLength(pointDist);
            p.addVector(v);
            path.remove(0);
            iterate(p, distance - pointDist);
        } else {
            p.addVector(v);
        }
        return false;
    }

    private boolean isEmpty() {
        return path.isEmpty();
    }
}
