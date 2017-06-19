package plain2beamer.converters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import plain2beamer.documents.ListItem;

public class MartlinsParserTest {
	private final MartlinsParser parser = new MartlinsParser();

	@Test
	public void testReadTitle() {
		// TODO
	}

	@Test
	public void testReadAuthor() {
		// TODO
	}

	@Test
	public void testReadDate() {
		// TODO
	}

	@Test
	public void testReadVersion() {
		// TODO
	}

	@Test
	public void testReadInstitute() {
		// TODO
	}

	@Test
	public void testReadSectionName() {
		// TODO
	}

	@Test
	public void testReadSubSectionName() {
		// TODO
	}

	@Test
	public void testReadNewFrameTitle() {
		// TODO
	}

	@Test
	public void testReadListItem() {
		ListItem item1 = parser.readListItem("- basic");
		assertEquals(new ListItem(0, null, "basic"), item1);

		ListItem item2 = parser.readListItem("\t\t- padded and with spaces");
		assertEquals(new ListItem(2, null, "padded and with spaces"), item2);

		ListItem item3 = parser.readListItem("1) custom and with spaces");
		assertEquals(new ListItem(0, "1)", "custom and with spaces"), item3);

		ListItem item4 = parser.readListItem("\tII. custom - and with spaces and dash");
		assertEquals(new ListItem(1, "II.", "custom - and with spaces and dash"), item4);
	}

}
