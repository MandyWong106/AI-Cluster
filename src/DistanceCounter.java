/**
 * Calculate the distance base on different distance calculation method
 */
public class DistanceCounter {

    enum Measures {
        Euclidean,
        Manhattan,
        Chebyshev,
        Minkowski
    }



    public static double calDistance(double[] point1, double[] point2){
        if (Input.distanceType==Measures.Euclidean){
            return calEuclidean(point1,point2);
        }
        if (Input.distanceType==Measures.Manhattan){
            return calManhattan(point1,point2);
        }
        if (Input.distanceType==Measures.Chebyshev){
            return calChebyshev(point1,point2);
        }
        if (Input.distanceType==Measures.Minkowski){
            return calMinkowski(point1,point2,Input.degree);
        }
        return 0;
    }

    private static double calEuclidean(double[] point1, double[] point2){
        return calMinkowski(point1,point2,2);
    }
    private static double calManhattan(double[] point1, double[] point2){
        double sum=0;
        for (int i = 0; i<point1.length;i++){
            sum+=Math.abs(point1[i]-point2[i]);
        }
        return sum;
    }
    private static double calChebyshev(double[] point1, double[] point2){
        double sum=0;
        for (int i = 0; i<point1.length;i++){
            sum=(Math.abs(point1[i]-point2[i])>sum?Math.abs(point1[i]-point2[i]):sum);
        }
        return sum;
    }
    private static double calMinkowski(double[] point1, double[] point2, int degree){
        double sum=0;
        for (int i = 0; i<point1.length;i++){
            sum+=Math.pow(Math.abs(point1[i]-point2[i]),degree);
        }
        return Math.pow(sum,1.0/degree);
    }
}
