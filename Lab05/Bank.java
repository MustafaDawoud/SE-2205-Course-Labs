package ca.uwo.eng.se2205b.lab5.model;

import ca.uwo.eng.se2205b.lab5.MyHashMap;
import com.google.common.base.MoreObjects;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Bank
 */
@ParametersAreNonnullByDefault
public final class Bank {

    private MyHashMap<Person, Set<Account>> bank;
    private int bankID;

    public Bank() {
        bank = new MyHashMap<>();
        bankID = 0;
    }

    public Bank(int bankID) {
        bank = new MyHashMap<>();
        this.bankID = bankID;
    }

    /**
     * Get all of the accounts for a person
     * @param person Person whom has an account
     * @return Possibly empty {@code Set} of accounts for a Person
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public Set<Account> getAccounts(Person person) {
        if(person == null)
            throw new NullPointerException();
        return (Set<Account>) bank.get(person);
    }

    /**
     * Opens a new {@link Account} for an individual.
     * @param person Person who is on an account
     * @return New {@code Account}
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public Account openAccount(Person person) {
        if(person == null)
            throw new NullPointerException();
        Account newAccount = new Account();
        if(!bank.containsKey(person)){
            Set<Account> newSet = new HashSet<>();
            newSet.add(newAccount);
            bank.put(person, newSet);
        }
        else{
            Set<Account> personAccounts = getAccounts(person);
            personAccounts.add(newAccount);
        }
        return newAccount;
    }

    /**
     * Closes an account for an individual
     * @param person Person who is on an account
     * @param account Account to close
     *
     * @throws NullPointerException if {@code person} or {@code account} is {@code null}
     * @throws AccountCloseException if the Account can not be closed
     */
    public void closeAccount(Person person, Account account) throws AccountCloseException {
        if(person == null || account == null)
            throw new NullPointerException();
        Set<Account> personAccounts = getAccounts(person);
        if(!personAccounts.remove(account)){
            throw new AccountCloseException(account, "");
        }
    }

    /**
     * Get <em>all</em> of the wealth of a person, the total of all of a person's accounts.
     * @param person Non-{@code null} person to get the total wealth of.
     * @return Total wealth
     *
     * @throws NullPointerException if {@code person} is {@code null}
     */
    public double getTotalWealth(Person person) {
        if(person == null)
            throw new NullPointerException();
        double wealth = 0.0;
        for(Account i : getAccounts(person)){
            wealth += i.getBalance();
        }
        return wealth;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("bank", bank)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = bank.hashCode();
        result = 31 * result + bankID;
        return result;
    }
}
