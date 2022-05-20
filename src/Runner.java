import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.function.DoubleFunction;

public class Runner {

    private static final Scanner stdIn = new Scanner(System.in);

    public static void printMenu(){
        System.out.printf("Please select one of the following option by entering the number\n");
        System.out.printf("0. Exit Program%n");
        System.out.printf("1. Input DataPoint%n");
        System.out.printf("2. Run%n");
        System.out.printf("3. Set Cluster Method (Current = %s)%n",ClusterTool.getCurrentMethod());
        System.out.printf("4. Set Distance Measure (Current = %s)%n",ClusterTool.getCurrentMeasures());
        System.out.printf("5. Set num of attributes%n");
    }

    public static void main(String[] args) {
        //
        //DataPoint dp1 = new DataPoint(2);
        int option=-1;
        while(option!=0){
            printMenu();
            while (true) {
                try  {
                    option = Integer.parseInt(stdIn.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter an integer");
                }
            }
            //System.out.println("tbc");
        }

    }
}
