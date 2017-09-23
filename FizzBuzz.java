package ca.uwo.eng.se2205b;

/**
 * Problem #1: FizzBuzz
 */
public class FizzBuzz  {

    /**
     * Driver method for FizzBuzz
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 1; i <= 100; i++){
            System.out.print(i + " ");
            if(i%3 == 0){
                System.out.print("Fizz");
            }
            if(i%5 == 0){
                System.out.print("Buzz");
            }
            System.out.println();
        }
    }
}
