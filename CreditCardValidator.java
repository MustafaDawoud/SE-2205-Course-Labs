package ca.uwo.eng.se2205b;

import java.util.Scanner;

/**
 * Problem #3: Validates a Credit Card
 */
public class CreditCardValidator {

    public enum CreditCard{
        VisaCard, MasterCard, AmericanExpressCard, DiscoveryCard
    }

    /**
     * Adds two time every second number of the credit card number going from right to left
     * If two times the number is a two digit number, instead of adding that number, add the sum of digit1 and digit2
     *
     * @return Non-{@code null} the final sum.
     */
    public static long sumEven(Integer[] creditArray){

        long sumO = 0;
        String str = "";
        Integer temp1 = 0;

        for(Integer i = 1; i < creditArray.length; i+=2){
            temp1 = creditArray[i]*2;
            str = temp1.toString();
            //If 2 times the number is less than 2 digits, add 2 times the number, else, add digit 1 and digit 2
            if(str.length() == 1){
                sumO += 2*creditArray[i];
            }
            else {
                sumO += temp1/10 + temp1%10;
            }
        }
        return sumO;
    }

    /**
     * Adds all the odd positioned numbers of the credit card number going from right to left
     *
     * @return Non-{@code null} the final sum.
     */
    public static long sumOdd(Integer[] creditArray){
        long sumE = 0;
        for(Integer i = 0; i < creditArray.length; i+=2){
            sumE += creditArray[i];
        }
        return sumE;
    }

    /**
     * Compute if the number is a valid Credit Card Number.
     * @param number Credit Card number to validate.
     * @return Non-{@code null} enum of the type of credit card.
     */
    public static CreditCard validator(long number){
        CreditCard cardType = null;
        String temp = "";
        Integer[] cardArray;
        long totalSum = 0;
        temp = Long.toString(number);
        cardArray = new Integer[temp.length()];

        for(int i = 0; i < temp.length(); i++){
            cardArray[i] = temp.charAt(temp.length()-(i+1)) - '0'; //Copies over the number backwards
        }

        totalSum = sumOdd(cardArray)+ sumEven(cardArray);

        if(totalSum%10 == 0){
            if(cardArray[cardArray.length-1] == 4){
                cardType = CreditCard.VisaCard;
                return cardType;
            }
            else if(cardArray[cardArray.length-1] == 5){
                cardType = CreditCard.MasterCard;
                return cardType;
            }
            else if(cardArray[cardArray.length-1] == 3){
                cardType = CreditCard.AmericanExpressCard;
                return cardType;
            }
            else {
                cardType = CreditCard.DiscoveryCard;
                return cardType;
            }
        }
        return null;
    }

    public static void printValidation(CreditCard cardType ){
        if(cardType == CreditCard.VisaCard){
            System.out.println(", is Valid and is of type: Visa Card");
        }
        else if(cardType == CreditCard.MasterCard){
            System.out.println(", is Valid and is of type: Master Card");
        }
        else if(cardType == CreditCard.AmericanExpressCard){
            System.out.println(", is Valid and is of type: American Express Card");
        }
        else if(cardType == CreditCard.DiscoveryCard){
            System.out.println(", is Valid and is of type: Discovery Card");
        }
        else{
            System.out.println(", is Invalid");
        }
    }
    public int i = 1;
    public final void hi(){}
    /*
    public static void main(String[] args) {
        CreditCard cardType = null;
        String inputNumber = "";
        long creditNumber = 0;
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your Credit Card Number: ");
        while (true) {
            inputNumber = sc.nextLine();
            try {
                creditNumber = Long.parseLong(inputNumber);
                break;
            } catch (Exception e) {
                System.out.println("Your input was invalid, please try again: ");
            }
        }

        System.out.print("The credit card number: " + inputNumber);
        cardType = validator(creditNumber);
        printValidation(cardType);
    }
*/
    public static void main(String[] args) {
        CreditCard card = null;
        String temp = "";
        String[] listOfNumber = {"54321", "4388576018402626", "4111111111111111",
                "5500000000000004", "340000000000009"};

        for (int i = 0; i < 5; i++) {
            System.out.print("The credit card number: " + listOfNumber[i]);
            card = validator(Long.parseLong(listOfNumber[i]));
            printValidation(card);
        }
    }
}
