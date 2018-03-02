package dsekercioglu.server;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Geometry {

    public static Line2D[] getRectangleLines(Rectangle2D rect) {
        Line2D[] lines = new Line2D[4];
        lines[0] = new Line2D.Double(rect.getMinX(), rect.getMinY(), rect.getMinX(), rect.getMaxY());
        lines[1] = new Line2D.Double(rect.getMaxX(), rect.getMinY(), rect.getMaxX(), rect.getMaxY());
        lines[2] = new Line2D.Double(rect.getMinX(), rect.getMinY(), rect.getMaxX(), rect.getMinY());
        lines[3] = new Line2D.Double(rect.getMinX(), rect.getMaxY(), rect.getMaxX(), rect.getMaxY());
        return lines;
    }

    public static Line2D[] rotateCenter(Rectangle2D rect, double rotation) {
        double centerX = rect.getX() + rect.getWidth() / 2;
        double centerY = rect.getY() + rect.getHeight() / 2;
        Point2D.Double center = new Point2D.Double(centerX, centerY);
        Line2D[] lines = getRectangleLines(rect);
        for (Line2D line : lines) {
            line.setLine(rotate(center, line.getP1(), rotation), rotate(center, line.getP2(), rotation));
        }
        return lines;
    }

    private static Point2D.Double rotate(Point2D.Double center, Point2D point, double rotation) {
        double angle = Math.atan2(point.getX() - center.x, point.getY() - center.y);
        return project(center, angle + rotation, center.distance(point));
    }

    public static Point2D.Double project(Point2D.Double source, double angle, double distance) {
        return new Point2D.Double(source.x + Math.sin(angle) * distance, source.y + Math.cos(angle) * distance);
    }

    public static boolean intersects(Line2D[] r1, Line2D[] r2) {
        for(int i = 0; i < r1.length; i++) {
            for(int j = 0; j < r2.length; j++) {
                if(r1[i].intersectsLine(r2[j])) {
                    return true;
                }
            }
        }
        return false;
    }
}
