public class Default {

    public static final int DEFAULT_NUMBER_OF_ATTRIBUTES = 2;
    private static String numberSeprator = " ";
    public static String getNumberSeparator(){
        return numberSeprator;
    }
    public static void setNumberSeprator(String numberSeprator){
        Default.numberSeprator = numberSeprator;
    }
}
