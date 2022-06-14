import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class is used for DBScan algorithm to run
 * both datapoint and distance Matrix could be used.
 */


public class DBScan {

    enum PointType {
        Core,
        Border,
        Outlier
    }

    //create a distance Matrix
    private double[][] distanceMatrix;
    int currentGroup = 1;
    String[] ptName;
    PointType[] pointType;
    ArrayList<ArrayList<Integer>> closePt = new ArrayList<>();
    ArrayList<Integer> corePt = new ArrayList<>();
    int[] group;

    /**
     * Construct a DBScan object
     * 1. Build up a distance matrix between data points.
     * 2. Given a name for each point to recognize easier.
     * 3. Initial pointType and group array which store respective information of each point.
     */
    public DBScan(double[][] data, String[] ptName, Input.DataType type) {
        if (type == Input.DataType.DataPoint) {
            distanceMatrix = MatrixBuilder.buildDistanceMatrix(data);
        } else {
            distanceMatrix = MatrixBuilder.mirrorDistanceMatrix(data);
        }
        this.ptName = Arrays.copyOf(ptName, ptName.length);
        pointType = new PointType[ptName.length];
        group = new int[ptName.length];
    }

    /**
     * Define if a point is a core, border or outlier (noise) point
     */
    private void definePointType() {
        for (int i = 0; i < pointType.length; i++) {
            int totalPointInRadius = 0;
            ArrayList<Integer> closePtOfOnePt = new ArrayList<>();
            for (int j = 0; j < pointType.length; j++) {
                if (distanceMatrix[i][j] < Input.radius) {
                    totalPointInRadius++;
                    if (i != j) closePtOfOnePt.add(j);
                }
            }
            closePt.add(closePtOfOnePt);
            if (totalPointInRadius < Input.minPts) {
                pointType[i] = PointType.Outlier;
            } else {
                pointType[i] = PointType.Core;
                corePt.add(i);
            }
        }
        for (int i = 0; i < pointType.length; i++) {
            if (pointType[i] != PointType.Core) {
                for (int j = 0; j < closePt.get(i).size(); j++) {
                    if (corePt.contains(closePt.get(i).get(j))) {
                        pointType[i] = PointType.Border;
                    }
                }
            }
        }
    }

    /**
     * Print Summary of each group, print
     * 1.the point
     * 2.the distance of it with other point
     * 3.the point type
     * 4.the group of the point
     * 5.which point is close to it
     */
    private void printSummary() {
        System.out.println("Summary(Group start from 1, 0=no group):");
        System.out.printf("%4s", ",");
        for (int i = 0; i < distanceMatrix.length; i++) {
            System.out.printf("%6s,", ptName[i]);
        }
        System.out.printf("%8s,%6s,%10s%n", "PtType", "Group", "ClosePts:");
        for (int i = 0; i < distanceMatrix.length; i++) {
            System.out.printf("%3s,", ptName[i]);
            for (int j = 0; j < distanceMatrix.length; j++) {
                System.out.printf("%6.2f,", distanceMatrix[i][j]);
            }
            StringBuilder closePtList = new StringBuilder();
            for (int j = 0; j < closePt.get(i).size(); j++) {
                closePtList.append(ptName[closePt.get(i).get(j)]).append("-");
            }
            if (!closePtList.isEmpty()) closePtList.deleteCharAt(closePtList.length() - 1);
            System.out.printf("%8s,%6d, %s%n", pointType[i], group[i], closePtList.toString());
        }
    }


    /**
     * group all core points within the radius of a core point as the same group
     * using recursive method so all linked core point would be grouped as the same group
     * group border point as the same group if the assessing core point is the closest core point for it.
     * @param start = which point to start, it actually doesn't matter besides the group number seq
     * as the current logic is grouping border point to the closest core point.
     */
    private void groupPt(int start) {
        int currentPt;
        group[start] = currentGroup;
        for (int i = 0; i < closePt.get(start).size(); i++) {
            currentPt = closePt.get(start).get(i);
            // if current assessing point is core point and not yet grouped
            if (pointType[currentPt] == PointType.Core && group[currentPt] == 0) {
                group[currentPt] = currentGroup;
                groupPt(currentPt);
            } else {
                if (start == calClosestPoint(currentPt)) {
                    group[currentPt] = currentGroup;
                }
            }

        }
    }

    /**
     * To make sure the start point for method groupPt is a core point, or when no start point
     * is specific, pick a core point to start
     * @param tempStart
     * @return
     */
    private int calStartPoint(int tempStart) {
        if (tempStart == -1 || pointType[tempStart] == PointType.Outlier) {
            return corePt.get(0);
        } else if (pointType[tempStart] == PointType.Border) {
            return calClosestPoint(tempStart);
        } else return tempStart;
    }

    /**
     * to find the closest core point of a given point
     * @param point
     * @return
     */
    private int calClosestPoint(int point) {
        int closestPt = -1, currentPoint;
        double minDistance = Input.MAX_DISTANCE, currentDist;

        for (int i = 0; i < closePt.get(point).size(); i++) {
            currentPoint = closePt.get(point).get(i);
            currentDist = distanceMatrix[point][currentPoint];
            if (currentDist < minDistance && pointType[currentPoint] == PointType.Core) {
                minDistance = currentDist;
                closestPt = currentPoint;
            }
        }
        return closestPt;
    }

    /**
     * run the whole logic of the grouping with DBScan
     */
    public void run() {
        definePointType();

        groupPt(calStartPoint(Input.startPt));
        for (int i = 0; i < corePt.size(); i++) {
            if (group[corePt.get(i)] == 0) {
                currentGroup++;
                groupPt(corePt.get(i));
            }
        }
        printSummary();

        System.out.println(Arrays.toString(group));
    }

}
