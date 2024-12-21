package ch.hslu.swda.data.entities.order;

// TODO-go: Serialization does not work correctly with enums via messaging
public enum OrderPositionState {
    /**
     * Standardwert, Artikel wird im Inventar geprüft.
     */
    UNKNOWN,

    /**
     * Artikel musste im Zentrallager nachbestellt werden.
     */
    REORDERED,

    /**
     * Artikelposition ist ungültig und muss korrigiert werden.
     */
    FAULTY,

    /**
     * Artikelposition erfolgreich überprüft und mit entsprechender Menge bestätigt.
     */
    READY
}

