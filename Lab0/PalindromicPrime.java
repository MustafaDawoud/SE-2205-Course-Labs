package ca.uwo.eng.se2205b;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Problem #2: Finds prime numbers and checks if they are palindromes.
 */
public class PalindromicPrime {
    /**
     * Decides whether an incoming number is palindrome
     *
     * @return Non-{@code null} true if palindrome, false otherwise.
     */
    public static boolean isPalindrome(Integer i){
        String str = i.toString();
        for(int k = 1; k <= str.length()/2; k++){
            if (str.charAt(str.length() - k) != str.charAt(k-1)) { return false; }
        }
        return true;
    }

    /**
     * Creates an iterator that returns prime palindrome numbers.
     *
     * @return Non-{@code null} iterator to get palindrome prime numbers.
     */
    public Iterator<Integer> palindromeIterator() {
        return new PrimeIterator();
    }

    public static class PrimeIterator implements Iterator<Integer>{
        private int max = 100000;
        private int itrPos = -1; //-1 because next() increments it by 1 then returns the value at that index
        private int[] palindromeArray = new int[100];

        public PrimeIterator() {
            //Modified Sieve Algorithm which only adds numbers to the array list if the number is prime and palindrome
            boolean[] isComposite = new boolean[max + 1];
            //Set all composite numbers (represented by indices) as true in the boolean
            for (int i = 2; i * i <= max; i++) {
                if (!isComposite[i]) {
                    for (int j = i; i * j <= max; j++) {
                        isComposite[i * j] = true;
                    }
                }
            }
            int index = 0;
            for (int i = 2; i <= max; i++) {
                if (!isComposite[i] && isPalindrome(i)) palindromeArray[index++] = i;
                if(index == 100) break;
            }
        }

        @Override
        public boolean hasNext() {
            return (itrPos+1)!=100;
        }

        @Override
        public Integer next() {
            itrPos++;
            if (itrPos >= 100) {
                throw new NoSuchElementException();
            } else {
                return palindromeArray[itrPos];
            }
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args){
        int counter = 0;
        PalindromicPrime generator = new PalindromicPrime();
        Iterator<Integer> primeIter = generator.palindromeIterator();

        while (primeIter.hasNext()){
            counter++;
            if(counter%10==0){
                System.out.println(primeIter.next() + "\t");
            }
            else{
                System.out.print(primeIter.next() + "\t");
            }
        }
    }
}
