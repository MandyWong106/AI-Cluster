import javax.xml.crypto.Data;
/**
Instead of using a user input or file reading approach.
Input are put in this class. This is because IDE(IntelliJ) is used,
it is easy to select the option which is enum.
There is TODO to remind you what usually shall be changed.
 */
public class Input {


//    enum BorderHandle{
//        Closest,
//        Earlier
//    }

    //TODO - update it you use datapoint instead of Distance
    //public static DataType dataInputType = DataType.Distance;
    public static DataType dataInputType = DataType.DataPoint;
    //TODO - update the Algorithm
    public static Algorithm algorithm = Algorithm.Hierarchical;
    //If hierarchical but not using distance, use below link maybe better
    //http://jydelort.appspot.com/resources/figue/demo.html

    //TODO - update it if the distance is different
    public static DistanceCounter.Measures distanceType = DistanceCounter.Measures.Euclidean;
    public static int degree = 3;//This is the degree if calMinkowski distance.

    // TODO - centriod is for Kmean
    public static double[][] centroids = {{1,0},{4,0}};

    // TODO - linkageType for Hierarchical
    public static Hierarchical.Linkage linkageType = Hierarchical.Linkage.CompleteLinkage;

    //TODO - DBSCAN parameter
    public static double radius=2;
    public static int minPts =3;
    //If no specific startPt, then use startPt =-1 mean start from smallest core point
    //0 = 1st start point, 1 = 2nd start point (it is entering the index)
    public static int startPt =-1;
//    public static BorderHandle borderHandle=BorderHandle.Closest;
    //there would be more than one faster implementation so only work for closest


    //TODO - update it if you need to use
    public static double[][] dataPts = {
            {3,4}      // A 1
            ,{5,4}     // B 2
            ,{4,2}     // C 3
            ,{6,5}     // D 4
            ,{8,5}     // E 5
            ,{9,5}    // F 6
            ,{8,3}     // G 7
            ,{8,4}     // H 8
//            ,{7,4}     // I 9
//            ,{4,5}     // J 10
//            ,{4,5.5}     // K 11
//            ,{4.5,6}     // L 12
//            ,{3.5,6}     // M 13
//            ,{10,10}     // N 14
//            ,{11,11}     // O 15
//            ,{10.5,10.5} // P 16
//            ,{12,12}     // Q 17
    };

    //TODO- update the distance matrix, both formats below work but must include 0 (diagonal) for both type
    //Type 1 matrix, as in top right
    public static double[][] distanceMatrix ={
            {0,0.2,0.15,0.76,0.54,0.31},
                {0,0.89,0.18,0.66,0.27},
                     {0,0.82,0.73,0.56},
                          {0,0.42,0.39},
                               {0,0.51},
                                    {0}
    };

    //Type 2 matrix, as in bottom left
//    public static double[][] distanceMatrix ={
//            {0},
//            {0.2,0},
//            {0.15,0.89,0},
//            {0.76,0.18,0.82,0},
//            {0.54,0.66,0.73,0.42,0},
//            {0.31,0.27,0.56,0.39,0.51,0}
//    };


//    //TODO comment the Character Name if want to use Num Pt, or vice versus
//    public final static String[] PT_NAME_TEMPLATE ={
//            "A","B","C","D","E","F"
//            ,"G","H","I","J","K","L"
//            ,"M","N","O","P","Q","R"
//    };

    public final static String[] PT_NAME_TEMPLATE ={
            "p1","p2","p3","p4","p5","p6"
            ,"p7","p8","9","10","11","12"
            ,"13","14","15","16","17","18"
            ,"19"
    };

    ///////Unlikely need to update below///////////////
    public static final double MAX_DISTANCE = 999999;
    public static String[] ptNames;
    public static String[] centroidNames;
    public final static String[] CENTROID_NAME_TEMPLATE ={
            "I"
            ,"II"
            ,"III"
            ,"IV"
            ,"V"
    };

    enum DataType {
        DataPoint,
        Distance
    }

    enum Algorithm{
        Kmean,
        Hierarchical,
        DBScan
    }
}
