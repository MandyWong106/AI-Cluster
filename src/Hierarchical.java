import java.util.*;

/**
 * Group data point base on the distance Matrix with Hierarchical algorithm
 * TODO add data point version in the future
 */
public class Hierarchical {

    enum Linkage {
        SingleLinkage,
        CompleteLinkage,
        GroupAverage
    }


    private ArrayList<ArrayList<Double>> distanceMatrix;
    private ArrayList<String> ptName;
    private ArrayList<ArrayList<Integer>> cluster=new ArrayList<>();
    private final ArrayList<ArrayList<Double>> INPUT_DISTANCE_MATRIX;
    private int combine_grp1 = -1, combine_grp2 = -1;

    /**
     * To build up a distance Matrix
     * distanceMatrix would be update constantly
     * INPUT_DISTANCE_MATRIX would be final which is used for calculation if GroupAverage
     * method is used
     * @param data
     * @param ptName
     * @param type
     */
    public Hierarchical(double[][] data, String[] ptName, Input.DataType type) {
        double[][] tempDistanceArray;
        if (type == Input.DataType.DataPoint) {
            tempDistanceArray = MatrixBuilder.buildDistanceMatrix(data);
        } else {
            tempDistanceArray = MatrixBuilder.mirrorDistanceMatrix(data);
        }
        //ArrayList is used such that the size of array change more flexibly
        //as each loop of the cluster would reduce one group
        distanceMatrix = arrayToArrayList(tempDistanceArray);
        INPUT_DISTANCE_MATRIX = arrayToArrayList(tempDistanceArray);

        this.ptName = arrayToArrayList(ptName);
        //Initialize such that each data point is in an individual clusterGroup
        for (int i = 0; i<data.length; i++){
            ArrayList<Integer> tempClusterGroup = new ArrayList<>();
            tempClusterGroup.add(i);
            cluster.add(tempClusterGroup);
        }

    }

    // Just turn 2 dimension array to 2 dimension arraylist
    private ArrayList<ArrayList<Double>> arrayToArrayList(double[][] array) {
        ArrayList<ArrayList<Double>> arrayListReturn = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            ArrayList<Double> perRow = new ArrayList<>();
            for (int j = 0; j < array.length; j++) {
                perRow.add(array[i][j]);
            }
            arrayListReturn.add(perRow);
        }
        return arrayListReturn;
    }

    //turn an array to an ArrayList
    private ArrayList<String> arrayToArrayList(String[] array) {
        ArrayList<String> arrayListReturn = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            arrayListReturn.add(array[i]);
        }
        return arrayListReturn;
    }

    //Print the current Matrix
    //TODO in future version, shall have a flexible size of paddling
    public void printMatrix() {
        System.out.println("Distance Matrix:");
        System.out.printf("%12s",",");
        for (int i = 0; i < distanceMatrix.size(); i++) {
            System.out.printf("%15s,", ptName.get(i));
        }
        System.out.println();
        for (int i = 0; i < distanceMatrix.size(); i++) {
            System.out.printf("%11s,", ptName.get(i));
            for (int j = 0; j < distanceMatrix.size(); j++) {
                System.out.printf("%15.2f,", distanceMatrix.get(i).get(j));
            }
            System.out.println();
        }
    }

    /**
     * update the Matrix base on the specific grouping linkage
     * row, column is the index of group that's the closest at the moment and shall
     * be group together
     */
    private void updateMatrix() {
        ArrayList<Double> newClusterDistance= new ArrayList<>();
        int groupAvgCounter=0;
        for (int i=0;i<distanceMatrix.size();i++){
            // for each group (i) beside the group to be combined (combine_grp1,2)
            if (i!= combine_grp1 &&i!= combine_grp2){
                // if using singleLinkage, then put the shortest distance among 1. i and combine_grp1, 2. i and combine_grp2
                // as the distance between group i and the new combined group
                if (Input.linkageType==Linkage.SingleLinkage){
                    if(distanceMatrix.get(i).get(combine_grp1)<distanceMatrix.get(i).get(combine_grp2)){
                        newClusterDistance.add(distanceMatrix.get(i).get(combine_grp1));
                    }else{
                        newClusterDistance.add(distanceMatrix.get(i).get(combine_grp2));
                    }
                // if using CompleteLinkage, then put the longest distance among 1. i and combine_grp1, 2. i and combine_grp2
                // as the distance between group i and the new combined group
                } else if (Input.linkageType==Linkage.CompleteLinkage){
                    if(distanceMatrix.get(i).get(combine_grp1)>distanceMatrix.get(i).get(combine_grp2)){
                        newClusterDistance.add(distanceMatrix.get(i).get(combine_grp1));
                    }else{
                        newClusterDistance.add(distanceMatrix.get(i).get(combine_grp2));
                    }
                // if using GroupAverage, then calculate average of the distance between group i and the all point within the new group
                }else/*GroupAverage*/{
                    double sum=0, value =0;
                    int totalLink=0;
                    //groupAvgCounter is used instead of i as the group of cluster of combine_grp1, and 2 is removed in method clustering().
                    //it is actually representing the index for group i in cluster
                    for(int j =0; j<cluster.get(groupAvgCounter).size(); j++){
                        // the lastCluster is the newly grouped Cluster
                        int lastClusterIndex =cluster.size()-1;
                        //sum up all distance between group i and pt in new group
                        for (int k = 0; k<cluster.get(lastClusterIndex).size(); k++){
                            value = INPUT_DISTANCE_MATRIX.get(cluster.get(groupAvgCounter).get(j)).get(cluster.get(lastClusterIndex).get(k));
                            sum+= value;
                            totalLink++;
                        }
                    }
                    //average the distance
                    newClusterDistance.add(Math.round(sum*100/totalLink)/100.0);
                    groupAvgCounter++;

                }
            }
        }
        //add the diagonal distance
        newClusterDistance.add(0.0);

        //remove the row of the groups to be combined, must remove 2 before 1 as they are index
        distanceMatrix.remove(combine_grp2);
        distanceMatrix.remove(combine_grp1);
        distanceMatrix.add(newClusterDistance);

        //remove the column of the groups to be combined, must remove 2 before 1 as they are index
        for (int i =0; i<distanceMatrix.size()-1;i++){
            distanceMatrix.get(i).remove(combine_grp2);
            distanceMatrix.get(i).remove(combine_grp1);
            distanceMatrix.get(i).add(newClusterDistance.get(i));
        }


    }

    /**
     * Find the index of groups to be combined by finding the shortest distance within the latest distance matrix
     * assign index of the group to be combined to var combine_grp1,2
     * combine the 2 group and put the new name to ptName at the end,
     * and the points to cluster at the end
     */
    private void clustering() {

        combine_grp1 = -1;
        combine_grp2 = -1;
        double min = Input.MAX_DISTANCE;

        for (int i = 0; i < distanceMatrix.size(); i++) {
            //this is a loop to reduce non-clustered point
            for (int j = 1 + i; j < distanceMatrix.size(); j++) {
                if (distanceMatrix.get(i).get(j) < min) {
                    min = distanceMatrix.get(i).get(j);
                    combine_grp1 = i;
                    combine_grp2 = j;
                }
            }
        }
        System.out.printf("Group pt (%s) and (%s) with height = %.2f%n", ptName.get(combine_grp1), ptName.get(combine_grp2), min);
        ptName.add(ptName.get(combine_grp1) + "-" + ptName.get(combine_grp2));
        ptName.remove(combine_grp2);
        ptName.remove(combine_grp1);
        //cluster contain the original index of the data point which can be used in the INPUT_DISTANCE_MATRIX
        ArrayList<Integer> newCluster = (ArrayList<Integer>) cluster.get(combine_grp1).clone();
        newCluster.addAll(cluster.get(combine_grp2));
//        System.out.printf("row is %d, column is %d",row, column);
//
//        System.out.println("newcluster include");
//        for (int i = 0; i< newCluster.size();i++){
//
//            System.out.println(newCluster.get(i));
//        }
//        System.out.println("newcluster end");

        cluster.add(newCluster);
        cluster.remove(combine_grp2);
        cluster.remove(combine_grp1);
    }


    public void loop() {
        int counter = 1;
        while (ptName.size() > 1) {
            System.out.printf("///////////////round %d start///////////////%n", counter);
            clustering();
            updateMatrix();
            printMatrix();
            System.out.printf("///////////////round %d end///////////////%n%n", counter);
            counter++;
        }
    }
}