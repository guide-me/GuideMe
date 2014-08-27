package org.guideme.guideme.milovana.tests;

import org.guideme.guideme.milovana.TextUtil;
import static org.junit.Assert.*;
import org.junit.Test;

public class TextUtilTest {

    @Test
    public void textWithoutTextFormatTags() {
        String text = "<P>some text</P>";
        
        assertEquals("<p>some text</p>", TextUtil.sanitizeNyxText(text));
    }

    @Test
    public void textWithSingleTextFormatTags() {
        String text = "<TEXTFORMAT LEADING=\"2\"><P ALIGN=\"CENTER\"><FONT FACE=\"FontSans\" SIZE=\"18\" COLOR=\"#FFFFFF\" LETTERSPACING=\"0\" KERNING=\"0\">some text</FONT></P></TEXTFORMAT>";
        
        assertEquals("<p><font color=\"#FFFFFF\">some text</font></p>", TextUtil.sanitizeNyxText(text));
    }

    @Test
    public void textWithMultipleTextFormatTags() {
        String text = "<TEXTFORMAT LEADING=\"2\"><P>some text</P></TEXTFORMAT><TEXTFORMAT LEADING=\"2\"><P>some other text</P></TEXTFORMAT>";
        
        assertEquals("<p>some text</p><p>some other text</p>", TextUtil.sanitizeNyxText(text));
    }

}
