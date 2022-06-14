/**
 * Either build distance Matrix base on the data point
 * or mirror a distance Matrix (only top right or bottom left) to a complete one.
 */

public class MatrixBuilder {


    public static double[][] buildDistanceMatrix(double[][] dataPt) {
        double[][] distanceMatrix = new double[dataPt.length][dataPt.length];
        for (int i = 0; i < dataPt.length; i++) {
            for (int j = 0; j < dataPt.length; j++) {
                distanceMatrix[i][j] = DistanceCounter.calDistance(dataPt[i], dataPt[j]);
            }
        }
        return distanceMatrix;
    }

    public static double[][] mirrorDistanceMatrix(double[][] distanceMatrix) {
        double[][] newdistanceMatrix = new double[distanceMatrix.length][distanceMatrix.length];
        if (distanceMatrix[0].length == 1) {
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = i + 1; j < distanceMatrix.length; j++) {
                    newdistanceMatrix[i][j] = distanceMatrix[j][i];
                }
            }
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = 0; j < i; j++) {
                    newdistanceMatrix[i][j] = distanceMatrix[i][j];
                }
            }
        } else {
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = 0; j < distanceMatrix.length - i; j++) {
                    newdistanceMatrix[i][j + i] = distanceMatrix[i][j];
                }
            }
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = i + 1; j < distanceMatrix.length; j++) {
                    newdistanceMatrix[j][i] = newdistanceMatrix[i][j];
                }
            }
            for (int i = 0; i < distanceMatrix.length; i++) {
                for (int j = 0; j < i; j++) {
                    newdistanceMatrix[j][i] = newdistanceMatrix[j][i];
                }
            }
        }
        return newdistanceMatrix;
    }

}
