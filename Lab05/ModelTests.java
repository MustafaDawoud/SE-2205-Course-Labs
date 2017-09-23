package ca.uwo.eng.se2205b.lab5;

import ca.uwo.eng.se2205b.lab5.model.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertEquals;

/**
 * Includes testing for the Banking Model
 */
public class ModelTests {
    @org.junit.Test
    public void testBank(){
        Bank RBC= new Bank();
        Bank MONT= new Bank();
        Person Dawoud = new Person("The", "Wood");
        //get open close account
        Account account1 = RBC.openAccount(Dawoud);
        account1.addTransaction(new Transaction(LocalDateTime.MIN, 50.0));
        Account account2 = RBC.openAccount(Dawoud);
        account2.addTransaction(new Transaction(LocalDateTime.MAX, 500.0));
        Set<Account> expected = new HashSet<>();
        expected.add(account1);
        expected.add(account2);
        assertEquals(expected, RBC.getAccounts(Dawoud));
        //get wealth
        assertEquals(550.0, RBC.getTotalWealth(Dawoud));
        //equals, hashcode, to string
        assertFalse(RBC.equals(MONT));
        account1 = MONT.openAccount(Dawoud);
        account1.addTransaction(new Transaction(LocalDateTime.MIN, 50.0));
        account2 = MONT.openAccount(Dawoud);
        account2.addTransaction(new Transaction(LocalDateTime.MAX, 500.0));
        assertTrue(RBC.equals(MONT));
        System.out.print(RBC.toString());
    }

    @org.junit.Test
    public void testPerson(){
        Person person1 = new Person("Test", "Student");
        Person person2 = new Person("Test", "Student");
        //equals, hashcode, to string
        assertTrue(person1.equals(person2));
        assertFalse(person1.equals(null));
        assertFalse(person1.equals(this));
        System.out.print(person1.toString());
    }

    @org.junit.Test
    public void testAccount(){
        Account account1 = new Account();
        Account account2 = new Account();
        assertEquals(0.0, account1.getBalance());
        Transaction temp = new Transaction(LocalDateTime.now(), 50.0);
        //add transaction
        account1.addTransaction(temp);
        account2.addTransaction(temp);
        //get balance
        assertEquals(50.0, account1.getBalance());
        temp = new Transaction(LocalDateTime.now(), -25.0);
        account1.addTransaction(temp);
        assertEquals(25.0, account1.getBalance());
        //equals, hashcode, to string
        assertFalse(account1.equals(account2));
        account2.addTransaction(temp);
        assertTrue(account1.equals(account2));
        System.out.print(account1.toString());
    }

    @org.junit.Test
    public void testTransaction(){
        Transaction transaction1 = new Transaction(LocalDateTime.MAX, 100.0);
        Transaction transaction2 = new Transaction(LocalDateTime.MAX, 100.0);
        Transaction transaction3 = new Transaction(LocalDateTime.MIN, 100.0);
        //get date and time
        assertEquals(LocalDateTime.MAX, transaction1.getDateTime());
        //get amount
        assertEquals(100.0, transaction1.getAmount());
        //equals, hashcode, to string
        assertTrue(transaction1.equals(transaction2));
        assertFalse(transaction1.equals(transaction3));
        System.out.print(transaction1.toString());
    }
}
