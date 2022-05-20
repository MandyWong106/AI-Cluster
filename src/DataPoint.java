public class DataPoint {

    private double[] attributeValues;

    public DataPoint(int noOfAttribute){
        attributeValues = new double[noOfAttribute];
    }

    public void setAttributeValues(){
            System.out.printf("Please enter %s values with seperator \"%s\"\n",attributeValues.length,Default.getNumberSeparator());

    }

    public double[] getAttributeValues(){
        return attributeValues;
    }

}
