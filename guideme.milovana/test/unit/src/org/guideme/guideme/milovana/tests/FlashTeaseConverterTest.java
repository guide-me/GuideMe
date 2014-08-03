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
    public void absoluteTruth() {
        Guide guide = sut.createGuide("start#page()");
        assertEquals("start#", guide.getPages().get(0).getId());
    }
}
