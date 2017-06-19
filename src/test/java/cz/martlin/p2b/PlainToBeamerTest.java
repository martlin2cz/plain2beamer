package cz.martlin.p2b;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Test;

import cz.martlin.p2b.Parseable;
import cz.martlin.p2b.Parser;
import cz.martlin.p2b.PlainToBeamer;

public class PlainToBeamerTest {

	@Test
	public void test() {
		String input = "" + "author=me\n" + "title=something\n" + "\n" + "#Hello slide\n" + "- this is an slide\n";
		String out = test(input);

		System.out.println(out);
	}

	private String test(String input){
		//TODO FIXME somehow not working :-O
		Parseable parser = new Parser();
		PlainToBeamer ptb = new PlainToBeamer(null, null, parser);

		StringReader r = new StringReader(input);
		StringWriter w = new StringWriter();
		BufferedReader br = new BufferedReader(r);
		BufferedWriter bw = new BufferedWriter(w);

		ptb.convert(br, bw);

		String out = new String(w.toString());	
		return out;
	}

}
