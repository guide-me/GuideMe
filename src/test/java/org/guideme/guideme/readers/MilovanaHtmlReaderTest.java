package org.guideme.guideme.readers;

import static org.junit.Assert.*;

import org.guideme.guideme.model.Guide;
import org.junit.Test;

public class MilovanaHtmlReaderTest {

	@Test
	public void test() {
		MilovanaHtmlReader sut = new MilovanaHtmlReader();
		Guide guide = sut.loadFromUrl("http://www.milovana.com/webteases/showtease.php?id=200");
		
		assertNotNull(guide);
	}

}
