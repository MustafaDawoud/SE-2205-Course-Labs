package ca.uwo.eng.se2205b.lab5.model;

import com.google.common.base.MoreObjects;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import java.time.LocalDateTime;
import java.util.Currency;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Represents a Transaction
 */
@ParametersAreNonnullByDefault @Immutable
public final class Transaction {

    private final LocalDateTime dateTime;
    private final Double amount;

    public Transaction(LocalDateTime dateTime, Double amount) {
        this.dateTime = checkNotNull(dateTime, "dateTime == null");
        this.amount = checkNotNull(amount, "amount == null");
    }

    /**
     * The time this transaction took place.
     * @return Date and time of a transaction.
     */
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * The size of the transaction
     * @return
     */
    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return this.hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int result = dateTime.hashCode();
        result = 31 * result + amount.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("dateTime", dateTime)
                .add("amount", amount)
                .toString();
    }
}
