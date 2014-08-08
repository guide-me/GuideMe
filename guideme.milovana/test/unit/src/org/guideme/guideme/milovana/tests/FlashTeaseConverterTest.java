/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.guideme.guideme.milovana.tests;

import org.guideme.guideme.milovana.FlashTeaseConverter;
import org.guideme.guideme.model.Guide;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class FlashTeaseConverterTest {

    private FlashTeaseConverter sut; 
    
    @Before
    public void setUp() throws Exception {
        sut = new FlashTeaseConverter();
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void emptyPageWithId() {
        Guide guide = sut.createGuide("start#page()");
        assertEquals("start", guide.getPages().get(0).getId());
    }
    
    @Test
    public void twoPages() {
        Guide guide = sut.createGuide("start#page()\np2#page()\n");
        assertEquals(2, guide.getPages().size());
        assertEquals("p2", guide.getPages().get(1).getId());
    }
    
    @Test
    public void simpleText() {
        Guide guide = sut.createGuide("start#page(text:'hello')");
        assertEquals("hello", guide.getPages().get(0).getText());
    }
    
    @Test
    public void textWithMarkup() {
        Guide guide = sut.createGuide("start#page(text:'<p>hello</p>')");
        assertEquals("<p>hello</p>", guide.getPages().get(0).getText());
    }
    
    @Test
    public void textWithQuotes() {
        Guide guide = sut.createGuide("start#page(text:'hello \"stranger\".')");
        assertEquals("hello \"stranger\".", guide.getPages().get(0).getText());
    }
    
    @Test
    public void simpleImage() {
        Guide guide = sut.createGuide("start#page(media:pic('path/to/image.png'))");
        assertEquals("path/to/image.png", guide.getPages().get(0).getImages().get(0).getSrc());
    }
}
