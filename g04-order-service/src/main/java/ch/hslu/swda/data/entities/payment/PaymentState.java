package ch.hslu.swda.data.entities.payment;

public enum PaymentState {

    /**
     * Unpaid, failed or otherwise unfulfilled payment.
     */
    PENDING,

    /**
     * Paid and finished payment.
     */
    COMPLETED,
}
