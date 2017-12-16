import edu.princeton.cs.algs4.Bag;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by triton on 17-12-16.
 */
public class BruteCollinearPoints {
    private final Point[] points;
    private final Bag<LineSegment> segmentBag;

    public BruteCollinearPoints(final Point[] points) {
        validate(points);

        this.points = Arrays.copyOf(points, points.length);
        validateDuplicate(this.points);
        this.segmentBag = new Bag<>();
        calculateSegments();
    }

    public int numberOfSegments() {
        return segmentBag.size();
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[segmentBag.size()];
        Iterator<LineSegment> iterator = segmentBag.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            segments[index++] = iterator.next();
        }
        return segments;
    }

    private void validate(Point[] array) {
        if (array == null) {
            throw new IllegalArgumentException("null");
        }

        for (Point p : array) {
            if (p == null) {
                throw new IllegalArgumentException("null point");
            }
        }
    }

    private void validateDuplicate(Point[] array) {
        Arrays.sort(array, 0, array.length, Point::compareTo);
        for (int i = 0; i < array.length - 1; ++i) {
            if (array[i].compareTo(array[i + 1]) == 0) {
                throw new IllegalArgumentException("repeated points");
            }
        }
    }

    private void calculateSegments() {
        for (int i = 0; i < points.length - 3; ++i) {
            for (int j = i + 1; j < points.length - 2; ++j) {
                for (int k = j + 1; k < points.length - 1; ++k) {
                    for (int m = k + 1; m < points.length; ++m) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                            && points[i].slopeTo(points[k]) == points[i].slopeTo(points[m])) {
                            segmentBag.add(new LineSegment(points[i], points[m]));
                        }
                    }
                }
            }
        }

    }
}
