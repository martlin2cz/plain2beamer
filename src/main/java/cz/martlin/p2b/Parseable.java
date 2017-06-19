package cz.martlin.p2b;

import cz.martlin.p2b.documents.ListItem;

/**
 * Předepisuje způsob, jakým, bude načítat požadované elementy ze vstupního souboru.
 *
 * @author m@rtlin
 */
public interface Parseable {

    /**
     * Pokusí se načíst ẗitulek prezentace z řádku vstupního souboru. Vrací jeho hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readTitle(String line);

    /**
     * Pokusí se načíst autora prezentace z řádku vstupního souboru. Vrací jeho hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readAuthor(String line);

    /**
     * Pokusí se načíst datum prezentace z řádku vstupního souboru. Vrací jeho hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readDate(String line);

    /**
     * Pokusí se načíst označení verze prezentace z řádku vstupního souboru. Vrací její hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readVersion(String line);

    /**
     * Pokusí se načíst název ústavu prezentace z řádku vstupního souboru. Vrací jeho hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readInstitute(String line);

    /**
     * Pokusí se načíst název sekce z řádku vstupního souboru. Vrací jeho hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readSectionName(String line);

    /**
     * Pokusí se načíst název subsekce z řádku vstupního souboru. Vrací jeho hodnotu, nebo null.
     * @param line
     * @return 
     */
    public abstract String readSubSectionName(String line);

    /**
     * Pokusí se načíst nový slajd prezentace z řádku vstupního souboru. Vrací jeho titulek, nebo null.
     * @param line
     * @return 
     */
    public abstract String readNewFrameTitle(String line);

    /**
     * Pokusí se načíst položku seznamu z řádku vstupního souboru. Vrací jeho text a velikost jeho odazení, nebo null.
     * @param line
     * @return 
     */
    public abstract ListItem readListItem(String line);

    /**
     * Vypíše (na stdout) informace o tomto formátu vstupního souboru.
     */
    public abstract void printHelp();
}
