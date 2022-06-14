import java.util.Arrays;
/**
This Class is to provide the main and run different cluster method.
All setup shall be in Class Input, including which cluster method to be use, what kind of data to be used etc.
This java is mainly used to demonstrate the algorithm step by step, therefore having input class is more
handy than having user input or file reading

@ Version 1.0
@ Author: Mandy W
 */


public class Cluster {



    public static void main(String[] args) {

        /*
        There are two types of input data available, one is data point,
        another is pre-calculated distance matrix between data points
        Please refer to Class Input to see the example of them

        There are 2 point name templates, which can changed in class Input via comment and uncomment them
         */
        if (Input.dataInputType ==Input.DataType.DataPoint)
            Input.ptNames= Arrays.copyOf(Input.PT_NAME_TEMPLATE,Input.dataPts.length);
        else Input.ptNames= Arrays.copyOf(Input.PT_NAME_TEMPLATE,Input.distanceMatrix.length);

        /*
        For the K-Mean Cluster, you have to input the initial centroid yourself, and the centroid name
        array initialize depends on the number of centroid points you enter
         */
        Input.centroidNames= Arrays.copyOf(Input.CENTROID_NAME_TEMPLATE,Input.centroids.length);

        /*
        Depends on the algorithm you choose in class "Input" to construct a new object and run the clustering logic
         */
        if (Input.algorithm== Input.Algorithm.Kmean){
            KMean km = new KMean();
            km.loop();
        }else if (Input.algorithm== Input.Algorithm.Hierarchical){
            Hierarchical h;
            if(Input.dataInputType == Input.DataType.DataPoint){
                h = new Hierarchical(Input.dataPts,Input.ptNames, Input.DataType.DataPoint);
            }else{
                h = new Hierarchical(Input.distanceMatrix,Input.ptNames, Input.DataType.Distance);
            }
            h.printMatrix();
            h.loop();
        }else{
            DBScan dbScan;
            if(Input.dataInputType == Input.DataType.DataPoint){
                dbScan = new DBScan(Input.dataPts,Input.ptNames, Input.DataType.DataPoint);
            }else{
                dbScan = new DBScan(Input.distanceMatrix,Input.ptNames, Input.DataType.Distance);
            }
            dbScan.run();

        }




    }



}
