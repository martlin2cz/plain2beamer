package cz.martlin.p2b;

import java.io.File;

/**
 *
 * @author m@rtlin
 */
public class Plain2beamerMain {

    public static final String APP_NAME = "plain2beamer";
    public static final String AUTHOR = "m@rtlin";
    public static final String VERSION = "2.1.1";
    public static final String DATE = "19.6.2017";

    public static void printVersion() {
        System.out.println(APP_NAME + " " + VERSION);
        System.out.println(AUTHOR);
        System.out.println(DATE);
    }

    public static void printHelp() {
        System.out.println(APP_NAME + " <soubor plainTextu> <soubor *.tex>");
        System.out.println(APP_NAME + " -v | --version");
        System.out.println(APP_NAME + " -h | --help");
        System.out.println(APP_NAME + " -f | --format (vypíše komentovaný ukázkový vstupně formátovaný soubor)");
        
    }
    
    public static void printFormatSample(Parseable converter) {
        converter.printHelp();
    }


    public static void main(String[] args) {
        Parseable parser = new Parser();

        if (args.length == 1) {
            if (args[0].equals("--help") || args[0].equals("-h")) {
                printHelp();
                System.exit(0);
            }
            if (args[0].equals("--version") || args[0].equals("-v")) {
                printVersion();
                System.exit(0);
            }
            if (args[0].equals("--format") || args[0].equals("-f")) {
                printFormatSample(parser);
                System.exit(0);
            }
        }

        if (args.length != 2) {
            System.err.println("Chybné argumenty, viz help");
            System.exit(1);
        }

        File plainTextFile = new File(args[0]);
        File texBeamerFile = new File(args[1]);
        PlainToBeamer b2b = new PlainToBeamer(plainTextFile, texBeamerFile, parser);


        String errorMsg = b2b.convert();
        if (errorMsg != null) {
            System.err.println("Chyba: " + errorMsg);
            System.exit(2);
        }
    }
}
