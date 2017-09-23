package ca.uwo.eng.se2205b.lab5.model;

import com.google.common.base.MoreObjects;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

/**
 * Represents an Account with a list of time organized transactions.
 */
@ParametersAreNonnullByDefault
public final class Account {

    Set<Transaction> account;//sdasdasdasdadad

    public Account() {
        account = new HashSet<>();
    }

    /**
     * Add a transaction to the account, updating the balance
     * @param transaction
     */
    public void addTransaction(Transaction transaction) {
        // TODO SE2205B
        if(transaction == null)
            throw new NullPointerException();
        account.add(transaction);
    }

    /**
     * Get the Balance of this account
     * @return Current balance
     */
    public double getBalance() {
        Double returnValue = 0.0;
        for(Transaction transcript : account){
            returnValue += transcript.getAmount();
        }
        return returnValue;
    }

    @Override
    public int hashCode(){
        return account.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (o == null || (this.getClass()!=o.getClass())) return false;
        return this.hashCode()==o.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("account", account)
                .toString();
    }
}
