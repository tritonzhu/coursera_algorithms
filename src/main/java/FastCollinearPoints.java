import edu.princeton.cs.algs4.Bag;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by triton on 17-12-16.
 */
public class FastCollinearPoints {
    private Point[] points;
    private final Bag<LineSegment> segmentBag;

    public FastCollinearPoints(final Point[] points) {
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
        Point[] copy = Arrays.copyOf(points, points.length);

        int low, high;
        for (Point point : copy) {
            points = Arrays.copyOf(copy, copy.length);
            Arrays.sort(points, 0, points.length, point.slopeOrder());

            low = 1;
            high = 2;
            // points[0] would always be copy[i] itself
            while (high < points.length) {
                if (point.slopeTo(points[low]) != point.slopeTo(points[high])) {
                    if (high - low >= 3 && isInOrder(points, low, high - 1)) {
                        segmentBag.add(new LineSegment(points[0], points[high - 1]));
                    }

                    low = high;
                }
                ++high;
            }

            if (high - low >= 3 && isInOrder(points, low, high - 1)) {
                segmentBag.add(new LineSegment(points[0], points[high - 1]));
            }
        }

    }

    private boolean isInOrder(Point[] array, int low, int high) {
        if (array[0].compareTo(array[low]) >= 0) {
            return false;
        }

        for (int i = low; i < high - 1; ++i) {
            if (array[i].compareTo(array[i + 1]) >= 0) {
                return false;
            }
        }
        return true;
    }
}
