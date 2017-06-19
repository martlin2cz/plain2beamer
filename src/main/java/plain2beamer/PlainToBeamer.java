package plain2beamer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;
import plain2beamer.documents.BeamerDocument;

/**
 * Třída provádějcí otevření vstupního souboru a překlad do výstupního.
 *
 * @author m@rtlin
 */
public class PlainToBeamer {

    private File plainTextFile;
    private File texBeamerFile;
    private Parseable parser;
    boolean inHeader;

    public PlainToBeamer(File plainTextFile, File texBeamerFile, Parseable parser) {
        this.plainTextFile = plainTextFile;
        this.texBeamerFile = texBeamerFile;
        this.parser = parser;
    }

    public String convert() {

        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = openInputFile(plainTextFile);
        } catch (IOException ex) {
            return ex.getMessage();
        }

        try {
            writer = openOutputFile(texBeamerFile);
        } catch (IOException ex) {
            try {
                reader.close();
            } catch (IOException ex1) {
                return "IO Chyba číslo 1";
            }
            return ex.getMessage();
        }

        String result = convert(reader, writer);

        try {
            reader.close();
        } catch (IOException ex) {
            return "IO Chyba číslo 2";
        }
        try {
            writer.close();
        } catch (IOException ex) {
            return "IO Chyba číslo 3";

        }

        return result;

    }

    private BufferedReader openInputFile(File file) throws IOException {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader buffReader = new BufferedReader(fileReader);
            return buffReader;
        } catch (FileNotFoundException ex) {
            throw new IOException("Soubor nenalezen: " + file);
        }
    }

    private BufferedWriter openOutputFile(File file) throws IOException {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter buffWriter = new BufferedWriter(fileWriter);
            return buffWriter;
        } catch (FileNotFoundException ex) {
            throw new IOException("Soubor nenalezen: " + file);
        }
    }

    private boolean tryToConvertHeaders(String line, BeamerDocument document) {
        String title = parser.readTitle(line);
        if (title != null) {
            document.setTitle(title);
            return true;
        }

        String author = parser.readAuthor(line);
        if (author != null) {
            document.setAuthor(author);
            return true;
        }

        String date = parser.readDate(line);
        if (date != null) {
            document.setDate(date);
            return true;
        }

        String version = parser.readVersion(line);
        if (version != null) {
            document.setVersion(version);
            return true;
        }

        String institute = parser.readInstitute(line);
        if (institute != null) {
            document.setInstitute(institute);
            return true;
        }

        return false;
    }

    private boolean tryToConvertStructures(String line, BeamerDocument document) throws IOException {
        String section = parser.readSectionName(line);
        if (section != null) {
            document.setSection(section);
            return true;
        }

        String subsection = parser.readSubSectionName(line);
        if (subsection != null) {
            document.setSubsection(subsection);
            return true;
        }

        String frameTitle = parser.readNewFrameTitle(line);
        if (frameTitle != null) {
            document.writeNewFrame(frameTitle);
            return true;
        }

        return false;
    }

    protected String convert(BufferedReader reader, BufferedWriter writer) {
        String result = null;
        BeamerDocument document = new BeamerDocument(writer);

        inHeader = true;

        try {
            while (reader.ready()) {
                String line = reader.readLine();

                result = processLine(line, document);
                if (result != null) {
                    break;
                }
            }
        } catch (IOException ex) {
            return "IO chyba 5";
        }

        if (!inHeader) {
            boolean footersResult = document.writeLaTeXFileFooters();
            if (!footersResult) {
                return "IO Chyba 7";
            }
        } else {
            System.out.println("Varování: Nenalezen žádný obsah dokumentu");
        }
        return result;

    }

    private String processLine(String line, BeamerDocument document) throws IOException {
        String result = null;

        if (line.length() == 0) {
            return result;
        }

        if (inHeader) {
            result = processHeader(line, document);
        } else {
            result = processContent(line, document);
        }

        return result;
    }

    private String processHeader(String line, BeamerDocument document) {
        boolean headersConverted = tryToConvertHeaders(line, document);
        if (headersConverted) {
            return null;
        }

        try {
            boolean structConverted = tryToConvertStructures(line, document);
            if (structConverted) {
                inHeader = false;
                return null;
            }
        } catch (IOException ex) {
            return "IO Chyba jánevím kolik";
        }

        document.addAdditionalHeaders(line);

        return null;
    }

    private String processContent(String line, BeamerDocument document) throws IOException {
        boolean structsResult = tryToConvertStructures(line, document);
        if (structsResult) {
            return null;
        }

        Entry<String, Integer> lineItem = parser.readListItem(line);

        if (lineItem.getValue() == 0) {
            document.writeNormalLine(line);
        } else {
            document.writeListItem(lineItem.getKey(), lineItem.getValue());
        }

        return null;
    }

    public static String getNow() {
        String result;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("d. M. y");
        result = sdf.format(date);

        return result;
    }
}
