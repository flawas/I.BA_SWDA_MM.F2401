package ch.hslu.swda.data.entities;

public enum OrderState {
    RECEIVED,           // Bestellung eingegangen/erhalten
    PARTIALLY_READY,    // Gewisse Artikel versandbereit
    FAULTY,             // Fehlerhafte Positionen gefunden, wodurch die Bestellung fehlerhaft ist und geprüft werden muss
    NEEDS_APPROVAL,     // Bestellung wäre versandbereit, jedoch hat der Kunde noch unbezahlte Rechnungen und die Bestellung muss manuell freigegeben werden.
    READY,              // Alle Artikel versandbereit
    SHIPPED,            // Versandt
    COMPLETED,          // Abgeschlossen
    CANCELED,           // Storniert oder abgebrochen
}
