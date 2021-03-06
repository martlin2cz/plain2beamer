package cz.martlin.p2b.converters;

import cz.martlin.p2b.Parseable;
import cz.martlin.p2b.PlainToBeamer;
import cz.martlin.p2b.documents.ListItem;

/**
 * m@rtlinův parser.
 *
 * @author m@rtlin
 */
public class MartlinsParser implements Parseable {

	public static final String TITLE_KEY = "title=";
	public static final String AUTHOR_KEY = "author=";
	public static final String DATE_KEY = "date=";
	public static final String NOW_DATE_VALUE = "now";
	public static final String VERSION_KEY = "version=";
	public static final String INSTITUTE_KEY = "institute=";
	public static final String NEW_FRAME_KEY = "#";
	public static final String SECTION_KEY = "##";
	public static final String SUBSECTION_KEY = "###";
	public static final char LISTS_KEY = '\t';
	private static final String BULLET_TO_TEXT_SEPARATOR = " ";
	private static final String DEFAULT_BULLET = "-";

	public MartlinsParser() {
		super();
	}

	private String tryToReadValueOfKey(String key, String line) {
		int index = line.indexOf(key);

		if (index == 0) {
			return line.substring(key.length()).trim();
		} else {
			return null;
		}
	}

	@Override
	public String readTitle(String line) {
		return tryToReadValueOfKey(TITLE_KEY, line);
	}

	@Override
	public String readAuthor(String line) {
		return tryToReadValueOfKey(AUTHOR_KEY, line);
	}

	@Override
	public String readDate(String line) {
		String date = tryToReadValueOfKey(DATE_KEY, line);

		if (date == null) {
			return null;
		}

		if (date.equalsIgnoreCase(NOW_DATE_VALUE)) {
			return PlainToBeamer.getNow();
		}

		return date;
	}

	@Override
	public String readVersion(String line) {
		return tryToReadValueOfKey(VERSION_KEY, line);
	}

	@Override
	public String readInstitute(String line) {
		return tryToReadValueOfKey(INSTITUTE_KEY, line);
	}

	@Override
	public String readSectionName(String line) {
		if (readSubSectionName(line) == null) {
			return tryToReadValueOfKey(SECTION_KEY, line);
		} else {
			return null;
		}
	}

	@Override
	public String readSubSectionName(String line) {
		return tryToReadValueOfKey(SUBSECTION_KEY, line);
	}

	@Override
	public String readNewFrameTitle(String line) {
		return tryToReadValueOfKey(NEW_FRAME_KEY, line);
	}

	@Override
	public ListItem readListItem(String line) {
		int i;
		for (i = 0; i < line.length(); i++) {
			if (LISTS_KEY == line.charAt(i)) {
				continue;
			} else {
				break;
			}
		}

		int padding = i;
		String rest = line.substring(padding);
		String parts[] = rest.split(BULLET_TO_TEXT_SEPARATOR);

		String bullet = null, text = rest;

		if (parts.length >= 2) {
			if (!parts[0].equals(DEFAULT_BULLET)) {
				bullet = parts[0];
			}

			if (bullet == null) {
				text = rest.substring(DEFAULT_BULLET.length() + BULLET_TO_TEXT_SEPARATOR.length());
			} else {
				text = rest.substring(bullet.length() + BULLET_TO_TEXT_SEPARATOR.length());
			}
		}
		return new ListItem(padding, bullet, text);
	}

	@Override
	public void printHelp() {
		System.out.println("Ukázkový vstupní textový soubor:");
		System.out.println("% Komentáře se zapisují stále stejně");
		System.out.println("% Do souboru lze vkládat libovolný LaTeXový obsah");
		System.out.println();
		System.out.println("% Na počátku dokumentu se můžou zapsat symbolické příkazy pro deklaraci údajů o dokumentu");
		System.out.println("title=titulek prezentace");
		System.out.println("author=autor prezentace");
		System.out.println("date=datum vytvoření prezentace nebo řetězec \"now\" pro aktuální časové razítko");
		System.out.println("version=verze prezentace");
		System.out.println("institute=ústav, pod kterým prezentace vznikla");
		System.out.println();
		System.out.println("% Poté mohou následovat ostatní hlavičky dokumentu, například volby vzhledu:");
		System.out.println("\\mode<presentation> {");
		System.out.println("\t\\usetheme{Madrid}");
		System.out.println("}");
		System.out.println();
		System.out.println(
				"% Při prvním výskytu nového slajdu (#) sekce (##) nebo podsekce (###) začíná obsah prezentace:");
		System.out.println("% Automaticky je vložen úvodní slajd a slajd s obsahem");
		System.out.println("##Sekce první");
		System.out.println("###První podsekce první sekce");
		System.out.println("#Slajd číslo 1");
		System.out.println("% Text bez odsazení se vloží jako občejný text odstavce");
		System.out.println();
		System.out.println(
				"% Odstazený text 1 - 3 tabulátory nebo mezerami se automaticky převede na položky prostředí itemize požadovaného zanoření:");
		System.out.println("\t- položka 1. seznamu zanoření 1");
		System.out.println("\t- položka 2. seznamu zanoření 1");
		System.out.println("\t\t- položka 1. seznamu zanoření 2");
		System.out.println("\t\t\t- položka 1. seznamu zanoření 3");
		System.out.println("\t\t[$\\Rightarrow$] položka 2. seznamu zanoření 2 (šipka)");
		System.out.println();
		System.out.println("% Slajd je ukončen automaticky dalším slajdem, sekcí, podsekcí - nebo koncem souboru.");
		System.out.println("#Další slajd[fragile]");
		System.out.println("Obsah slajdu, třeba matematika: $c^2 = a^2 + b^2$, volitelně lze specifikovat příznak fragile");
		System.out.println();
		System.out.println("% Ani celý soubor není třeba nijak ukončovat");

	}
}
