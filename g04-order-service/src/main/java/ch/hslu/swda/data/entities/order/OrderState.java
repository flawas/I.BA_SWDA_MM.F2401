package ch.hslu.swda.data.entities.order;

public enum OrderState {
    /**
     * Bestellung eingegangen/erhalten.
     */
    RECEIVED,

    /**
     * Gewisse Artikel versandbereit.
     */
    PARTIALLY_READY,

    /**
     * Fehlerhafte Positionen gefunden, wodurch die Bestellung fehlerhaft ist und geprüft werden muss.
     */
    FAULTY,

    /**
     * Die Bonität des Kunden wird überprüft.
     */
    PAYMENT_CHECK,

    /**
     * Bestellung wäre versandbereit, jedoch hat der Kunde noch unbezahlte Rechnungen und die Bestellung muss manuell freigegeben werden.
     */
    NEEDS_APPROVAL,

    /**
     * Alle Artikel versandbereit.
     */
    READY,

    /**
     * Versandt.
     */
    SHIPPED,

    /**
     * Abgeschlossen.
     */
    COMPLETED,

    /**
     * Storniert oder abgebrochen.
     */
    CANCELED,
}
