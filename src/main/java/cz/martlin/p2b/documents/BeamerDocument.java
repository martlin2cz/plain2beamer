package cz.martlin.p2b.documents;

import java.io.IOException;
import java.io.Writer;

import cz.martlin.p2b.Plain2beamerMain;

/**
 * Třída preprezentující sestavovaný LaTeXový soubor.
 *
 * @author m@rtlin
 */
public class BeamerDocument {
	//

	private static final String FRAGILE_SPECIFIER = "[fragile]";
	private static final String TITLE_FRAME_TITLE = null; // "Úvodní slajd"
	public static final char PADDING_CHAR = '\t';
	//
	private Writer writer;
	private String title;
	private String author;
	private String date;
	private String version;
	private String institute;
	private StringBuilder additionalHeaders;
	//
	private String section;
	private String subsection;
	//
	private int frameNumber;
	private int padding;
	private static final int INITIAL_PADDING = 0;

	public BeamerDocument(Writer writer) {
		this.writer = writer;
		this.additionalHeaders = new StringBuilder();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public StringBuilder getAdditionalHeaders() {
		return this.additionalHeaders;
	}

	public void addAdditionalHeaders(String header) {
		this.additionalHeaders.append(header).append('\n');
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSubsection() {
		return subsection;
	}

	public void setSubsection(String subsection) {
		this.subsection = subsection;
	}

	public void writeBeamerHeader() throws IOException {
		writer.write("\\documentclass{beamer}\n");
		writer.write("\\usepackage[czech]{babel}\n");
		writer.write("\\usepackage[utf8x]{inputenc}\n");
		writeSeparatingCommentLine();
	}

	public void writeInitCommendHeader() throws IOException {
		writer.write("%% Prezentace: " + title);

		if (author != null || institute != null) {
			writer.write("\n%% ");
			if (author != null) {
				writer.write(author + ", ");
			}
			if (institute != null) {
				writer.write(institute);
			}
		}

		if (date != null || version != null) {
			writer.write("\n%% ");
			if (date != null) {
				writer.write(date + ", ");
			}
			if (version != null) {
				writer.write(version);
			}
		}

		writer.write("\n%%\n");

		writer.write("%% Vygenerováno programem " + Plain2beamerMain.APP_NAME + " " + Plain2beamerMain.VERSION + "\n");
		writeSeparatingCommentLine();
	}

	public void writeDocumentsMetadata() throws IOException {
		if (title != null) {
			writer.write("\\title{" + title + "}\n");
		} else {
			System.out.println("Varování: Nebyl specifikován titulek dokumentu");
		}

		if (author != null) {
			writer.write("\\author{" + author + "}\n");
		} else {
			System.out.println("Varování: Nebyl uvdeden autor dokumentu");
		}

		if (date != null) {
			writer.write("\\date{" + date + "}\n");
		}

		if (institute != null) {
			writer.write("\\institute{" + institute + "}\n");
		}

		if (version != null) {
			writer.write("\\def\\version{" + version + "}\n");
		}

		writeSeparatingCommentLine();
	}

	public void writeAdditionalHeaders() throws IOException {
		if (additionalHeaders.length() != 0) {
			writer.write(additionalHeaders.toString());
			writeSeparatingCommentLine();
		}
	}

	public void writeDocumentBegin() throws IOException {
		frameNumber = 1;
		padding = INITIAL_PADDING;
		writer.write("\\begin{document}\n");

		writeBeginFrame(TITLE_FRAME_TITLE);
		writer.write(PADDING_CHAR + "\\titlepage\n");

		writeNewFrame("Obsah");
		writer.write(PADDING_CHAR + "\\tableofcontents[]\n");
	}

	public void writeDocumentEnd() throws IOException {
		writeEndFrame();
		writer.write("\\end{document}\n");
	}

	public boolean writeLaTeXFileHeaders() {
		try {
			writeInitCommendHeader();
			writeBeamerHeader();
			writeDocumentsMetadata();
			writeAdditionalHeaders();
			writeDocumentBegin();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public boolean writeLaTeXFileFooters() {
		try {
			writeDocumentEnd();
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public void writeSeparatingCommentLine() throws IOException {
		int i;
		final int WIDTH = 78;

		for (i = 0; i < WIDTH; i++) {
			writer.write("%");
		}

		writer.write("\n");
	}

	public void writeNewFrame(String title) throws IOException {
		if (frameNumber == 0) {
			writeLaTeXFileHeaders();
		}

		writeEndFrame();

		tryToWriteSections();

		frameNumber++;
		writeBeginFrame(title);
	}

	public void tryToWriteSections() throws IOException {
		if (section != null) {
			writer.write("\\section{" + section + "}\n");
			section = null;
		}

		if (subsection != null) {
			writer.write("\\subsection{" + subsection + "}\n");
			subsection = null;
		}
	}

	public void writeBeginFrame(String title) throws IOException {
		writeSeparatingCommentLine();

		if (title != null) {
			if (title.endsWith(FRAGILE_SPECIFIER)) {
				title = title.substring(0, title.length() - FRAGILE_SPECIFIER.length());
				writer.write("\\begin{frame}" + FRAGILE_SPECIFIER + "\t%% Slajd " + frameNumber + " - " + title + "\n");
			} else {
				writer.write("\\begin{frame}\t%% Slajd " + frameNumber + " - " + title + "\n");
			}

			writer.write("\\frametitle{" + title + "}\n");
		} else {
			writer.write("\\begin{frame}\t%% Slajd " + frameNumber + "\n");
		}
	}

	public void writeEndFrame() throws IOException {
		fixPadding();

		writer.write("\\end{frame}\n");
		writer.write("\n");
	}

	protected String getSpaces(int count) {
		StringBuilder sb = new StringBuilder();
		int i;

		for (i = 0; i < count; i++) {
			sb.append(PADDING_CHAR);
		}

		return sb.toString();
	}

	public void fixPadding() throws IOException {
		fixPadding(INITIAL_PADDING);
	}

	public void fixPadding(int to) throws IOException {
		if (padding == to) {
			return;
		}

		if (padding > to) {
			for (; padding > to; padding--) {
				writer.write(getSpaces(padding - 1));
				writer.write("\\end{itemize}\n");
			}
		} else {
			if (to >= 4) {
				System.out.println("Varování: na slajdu " + frameNumber + " je použit seznam hloubky " + to + "."
						+ " LaTeX beamer dokument umožňuje maximálně hloubku 3, dokument pravděpodobně nepůjde přeložit.");
			}

			for (; padding < to; padding++) {
				writer.write(getSpaces(padding));
				writer.write("\\begin{itemize}\n");
			}
		}
	}

	public String writeListItem(ListItem listItem) {
		try {
			fixPadding(listItem.getPadding());

			if (listItem.getBullet() != null) {
				writeLine(getSpaces(listItem.getPadding()) + //
						"\\item[" + listItem.getBullet() + "] " + listItem.getText());
			} else {
				writeLine(getSpaces(listItem.getPadding()) + //
						"\\item " + listItem.getText());
			}
		} catch (IOException ex) {
			return "IO chyba 9";
		}

		return null;
	}

	public void writeNormalLine(String line) throws IOException {
		fixPadding();
		writer.write(line + "\n");
	}

	private void writeLine(String line) throws IOException {
		writer.write(line + "\n");
	}
}
