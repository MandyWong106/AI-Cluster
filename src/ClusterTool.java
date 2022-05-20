public class ClusterTool {
    enum ClusterMethod {
        KMeans,
        Hierarchical,
        DBSCAN
    }
    enum Measures {
        Euclidean,
        Manhattan,
        Chebyshev,
        Minkowski
    }
    private static ClusterMethod currentMethod = ClusterMethod.KMeans;
    private static Measures currentMeasures = Measures.Euclidean;
    public static ClusterMethod getCurrentMethod(){
        return currentMethod;
    }
    public static Measures getCurrentMeasures(){
        return currentMeasures;
    }
}
