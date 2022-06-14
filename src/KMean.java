import java.util.Arrays;
import static java.lang.Math.round;

/**
 * Group cluster base on the pre-defined starting centroid point
 * Most method used the parameter approach, but setting attribute shall be a better way
 * as the individual method is not likely to be used outside the class
 * TODO allow random centroid point in the future
 */

public class KMean {

    /**
     * Calculate the new Centroid Point
     * @param dataPt
     * @param clusterGroup
     * @param clusterName
     * @return
     */
    private double[][] calCentroid(double[][] dataPt, int[] clusterGroup, String[] clusterName){
        int centroidNum=Input.centroids.length;
        int attrNum=Input.centroids[0].length;
        int dataPtInOneGroup;
        double[] sumOfAttributes = new double[attrNum];
        double[][] centroid = new double[centroidNum][attrNum];
        for (int i = 0; i<centroidNum; i++){
            dataPtInOneGroup=0;
            for (int k = 0; k<attrNum;k++){
                sumOfAttributes[k]=0;
            }
            for (int j = 0; j<dataPt.length;j++){
                if (clusterGroup[j]==i){
                    dataPtInOneGroup++;
                    for (int k = 0; k<attrNum;k++){
                        sumOfAttributes[k]+=dataPt[j][k];
                    }
                }
            }
            for (int k = 0; k<attrNum;k++){
                centroid[i][k]=Math.round(sumOfAttributes[k]*100/dataPtInOneGroup)/100.0;
            }
            System.out.printf("Centroid %s now is: %s%n",clusterName[i], Arrays.toString(centroid[i]));
        }
        return centroid;
    }

    /**
     * Assign Cluster Group base on the current centroid
     * @param dataPt
     * @param centroid
     * @param ptName
     * @param clusterName
     * @return
     */
    private int[] assignClusterGroup(double[][] dataPt, double[][] centroid, String[] ptName, String[] clusterName){
        int[] result = new int[dataPt.length];
        int clusterGroup;
        double minimumDist, currDist;

        for (int i = 0; i<dataPt.length;i++){
            clusterGroup =-1; //reset clusterGroup for each dataPoint
            minimumDist = Input.MAX_DISTANCE;
            System.out.printf("Distance with centroids for pt, %s, %s, ",ptName[i],Arrays.toString(dataPt[i]));
            for (int j =0; j<centroid.length;j++){
                currDist = DistanceCounter.calDistance(dataPt[i],centroid[j]);
                System.out.printf("%.2f, ",currDist);
                if(currDist<minimumDist){
                  clusterGroup=j;
                  minimumDist=currDist;
                }
            }
            result[i]=clusterGroup;
            System.out.printf(" grouped in, %s%n",clusterName[clusterGroup]);
        }
        return result;
    }

    //Calculate the inclusterVariance
    private void inClusterVariance(double[][] dataPt, int[] clusterGrp,
                                   double[][] centroid,String[] ptName, String[] clusterName){
        double sum=0, var;
        for (int i =0; i<centroid.length;i++){
            for (int j = 0; j<dataPt.length;j++){
                if(i==clusterGrp[j]){
                    var = Math.pow(DistanceCounter.calDistance(dataPt[j],centroid[i]),2);
                    sum+=var;
                    System.out.printf("Var between pt %s and centroid %s is %.2f%n",ptName[j],clusterName[i],var);
                }
            }
        }
        System.out.printf("Total var is %.2f %n",sum);
    }

    //Display the grouping and new centroid per cluster round, at the end display the invariance too.
    public void loop(){
        int[] currentClusterGroup=new int[Input.dataPts.length];
        int counter =1;
        while(true){
            System.out.printf("///////////////round %d start///////////////%n",counter);
            int[] newClusterGroup=assignClusterGroup(Input.dataPts,Input.centroids,Input.ptNames,Input.centroidNames);
            Input.centroids=calCentroid(Input.dataPts,newClusterGroup,Input.centroidNames);
            System.out.printf("///////////////round %d end///////////////%n%n",counter);

            if (Arrays.compare(newClusterGroup,currentClusterGroup)==0){
                break;
            }else{
                currentClusterGroup=Arrays.copyOf(newClusterGroup,newClusterGroup.length);
            }
            if(counter==50)break;
            counter++;
        }
        System.out.println("Cluster complete");
        System.out.println();
        inClusterVariance(Input.dataPts,currentClusterGroup,Input.centroids,Input.ptNames,Input.centroidNames);
    }

}
