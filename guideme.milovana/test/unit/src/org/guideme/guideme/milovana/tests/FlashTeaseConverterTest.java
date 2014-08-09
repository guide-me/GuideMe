package org.guideme.guideme.milovana.tests;

import java.util.List;
import org.guideme.guideme.milovana.FlashTeaseConverter;
import org.guideme.guideme.model.*;
import org.guideme.guideme.model.Delay.Style;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class FlashTeaseConverterTest {

    private FlashTeaseConverter sut;
    private Guide guide;

    @Before
    public void setUp() throws Exception {
        sut = new FlashTeaseConverter();
        guide = new Guide();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void emptyPageWithId() {
        sut.parseScript(guide, "start#page()");

        Page page = guide.getPages().get(0);
        assertEquals("start", page.getId());
    }

    @Test
    public void pageIdStartingWithTheLetterE() {
        // PageIds starting with the letter E were a problem for the old TeaseMe downloader.
        sut.parseScript(guide, "e1#page()");

        assertNotNull(guide.findPage("e1"));
    }

    @Test
    public void twoPages() {
        sut.parseScript(guide, "start#page()\np2#page()\n");

        assertEquals(2, guide.getPages().size());
        assertEquals("p2", guide.getPages().get(1).getId());
    }

    @Test
    public void simpleText() {
        sut.parseScript(guide, "start#page(text:'hello')");

        Page page = guide.getPages().get(0);
        assertEquals("hello", page.getText());
    }

    @Test
    public void textWithMarkup() {
        String markupText = "<TEXTFORMAT LEADING=\"2\"><P ALIGN=\"CENTER\"><FONT FACE=\"FontSans\" SIZE=\"24\" COLOR=\"#FFFFFF\" LETTERSPACING=\"0\" KERNING=\"0\">Welcome to this tease.</FONT></P></TEXTFORMAT>";
        sut.parseScript(guide, "start#page(text:'" + markupText + "')");

        Page page = guide.getPages().get(0);
        assertEquals(markupText, page.getText());
    }

    @Test
    public void textWithQuotes() {
        sut.parseScript(guide, "start#page(text:'hello \"stranger\".')");

        Page page = guide.getPages().get(0);
        assertEquals("hello \"stranger\".", page.getText());
    }

    @Test
    public void simpleImage() {
        sut.parseScript(guide, "start#page(media:pic(id:\"path/to/image.png\"))");

        Image img = guide.getPages().get(0).getImages().get(0);
        assertEquals("path/to/image.png", img.getSrc());
    }
    
    @Test
    public void randomImage() {
        sut.parseScript(guide, "start#page(media:pic(id:\"*.png\"))");

        Image img = guide.getPages().get(0).getImages().get(0);
        assertEquals("*.png", img.getSrc());
    }

    @Test
    public void actionGo() {
        sut.parseScript(guide, "start#page(action:go(target:page2#))");

        Button btn = guide.getPages().get(0).getButtons().get(0);
        assertEquals("page2", btn.getTarget());
        assertEquals("Continue", btn.getText());
    }

    @Test
    public void actionGoRange() {
        sut.parseScript(guide, "start#page(action:go(target:range(from:1,to:4,prefix:'page')))");

        Button btn = guide.getPages().get(0).getButtons().get(0);
        assertEquals("page(1..4)", btn.getTarget());
    }

    @Test
    public void actionGoRangeNoPrefixLabel() {
        sut.parseScript(guide, "start#page(action:go(target:range(from:1,to:4,:'page')))");

        Button btn = guide.getPages().get(0).getButtons().get(0);
        assertEquals("page(1..4)", btn.getTarget());
    }

    @Test
    public void actionGoRangeNoPrefix() {
        sut.parseScript(guide, "start#page(action:go(target:range(from:1,to:4)))");

        Button btn = guide.getPages().get(0).getButtons().get(0);
        assertEquals("(1..4)", btn.getTarget());
    }

    @Test
    public void combinedTextPicActionGo() {
        String line = "start#page(text:'<TEXTFORMAT LEADING=\"2\"><P ALIGN=\"CENTER\"><FONT FACE=\"FontSans\" SIZE=\"24\" COLOR=\"#FFFFFF\" LETTERSPACING=\"0\" KERNING=\"0\">Welcome to this tease.</FONT></P></TEXTFORMAT><TEXTFORMAT LEADING=\"2\"><P ALIGN=\"CENTER\"><FONT FACE=\"FontSans\" SIZE=\"24\" COLOR=\"#FF0000\" LETTERSPACING=\"0\" KERNING=\"0\">Click the &quot;Continue&quot; button.</FONT></P></TEXTFORMAT>',media:pic(id:\"pics01.jpg\"),action:go(target:page2#))";
        sut.parseScript(guide, line);

        Page page = guide.getPages().get(0);
        assertNotNull(page.getText());
        assertEquals(1, page.getImages().size());
        assertEquals(1, page.getButtons().size());
    }

    @Test
    public void actionDelayInSecondsNoStyle() {
        sut.parseScript(guide, "page3#page(action:delay(time:90sec,target:page7#))");

        Delay delay = guide.getPages().get(0).getDelays().get(0);
        assertEquals(90, delay.getPeriodInSeconds());
        assertEquals("page7", delay.getTarget());
        assertEquals(Delay.DEFAULT_STYLE, delay.getStyle());
    }

    @Test
    public void actionDelayInMinutes() {
        sut.parseScript(guide, "page3#page(action:delay(time:2min,target:page13#))");

        Delay delay = guide.getPages().get(0).getDelays().get(0);
        assertEquals(2 * 60, delay.getPeriodInSeconds());
    }

    @Test
    public void actionDelayInHours() {
        sut.parseScript(guide, "page3#page(action:delay(time:2hrs,target:page13#))");

        Delay delay = guide.getPages().get(0).getDelays().get(0);
        assertEquals(2 * 60 * 60, delay.getPeriodInSeconds());
    }

    @Test
    public void actionDelayInSecondSecret() {
        sut.parseScript(guide, "page3#page(action:delay(time:40sec,target:page4#,style:secret))");

        Delay delay = guide.getPages().get(0).getDelays().get(0);
        assertEquals(40, delay.getPeriodInSeconds());
        assertEquals("page4", delay.getTarget());
        assertEquals(Style.Secret, delay.getStyle());
    }

    @Test
    public void actionDelayHidden() {
        sut.parseScript(guide, "page3#page(action:delay(time:40sec,target:page4#,style:hidden))");

        Delay delay = guide.getPages().get(0).getDelays().get(0);
        assertEquals(40, delay.getPeriodInSeconds());
        assertEquals("page4", delay.getTarget());
        assertEquals(Style.Hidden, delay.getStyle());
    }
    
    @Test
    public void delayQuotedStyle() {
        sut.parseScript(guide, "page3#page(action:delay(time:40sec,target:page4#,style:'hidden'))");

        Delay delay = guide.getPages().get(0).getDelays().get(0);
        assertEquals(Style.Hidden, delay.getStyle());
    }
    
    @Test
    public void delayRangeTarget() {
        sut.parseScript(guide, "start#page(action:delay(time:1sec,target:range(from:0,to:6,:'page')))");
        
        assertEquals("page(0..6)", guide.findPage("start").getDelays().get(0).getTarget());
    }
    
    @Test
    public void singleButton() {
        sut.parseScript(guide, "page3#page(action:buttons(target0:past17#,cap0:\"Thank You\"))");
        
        Button button = guide.findPage("page3").getButtons().get(0);
        assertEquals("past17", button.getTarget());
        assertEquals("Thank You", button.getText());
    }
    
    @Test
    public void multipleButtons() {
        sut.parseScript(guide, "start#page(action:buttons(target0:p1#,cap0:\"First\",target1:p2#,cap1:\"Second\",target2:p3#,cap2:\"Third\"))");
        
        List<Button> buttons = guide.findPage("start").getButtons();
        assertEquals(3, buttons.size());
        assertEquals("First", buttons.get(0).getText());
        assertEquals("p1", buttons.get(0).getTarget());
        assertEquals("Second", buttons.get(1).getText());
        assertEquals("p2", buttons.get(1).getTarget());
        assertEquals("Third", buttons.get(2).getText());
        assertEquals("p3", buttons.get(2).getTarget());
    }
    
    @Test
    public void yesNoButtons() {
        sut.parseScript(guide, "start#page(action:yn(yes:past3#,no:dumped#))");

        List<Button> buttons = guide.findPage("start").getButtons();
        assertEquals(2, buttons.size());
        assertEquals("Yes", buttons.get(0).getText());
        assertEquals("past3", buttons.get(0).getTarget());
        assertEquals("No", buttons.get(1).getText());
        assertEquals("dumped", buttons.get(1).getTarget());
    }
    
    @Test
    public void rangeTargetYesNoButtons() {
        sut.parseScript(guide, "start#page(action:yn(yes:range(from:1,to:3,prefix:''),no:range(from:4,to:6,prefix:'p')))");

        List<Button> buttons = guide.findPage("start").getButtons();
        assertEquals(2, buttons.size());
        assertEquals("(1..3)", buttons.get(0).getTarget());
        assertEquals("p(4..6)", buttons.get(1).getTarget());
    }
    
    @Test
    public void actionVert() {
        sut.parseScript(guide, "start#page(action:vert(e0:buttons(target0:page27#,cap0:\"I Came\"),e1:delay(time:10sec,target:page5#,style:hidden)))");
        
        Button button = guide.findPage("start").getButtons().get(0);
        assertEquals("page27", button.getTarget());
        assertEquals("I Came", button.getText());

        Delay delay = guide.findPage("start").getDelays().get(0);
        assertEquals("page5", delay.getTarget());
        assertEquals(10, delay.getPeriodInSeconds());
        assertEquals(Style.Hidden, delay.getStyle());
    }
    
    @Test
    public void hiddenSound() {
        sut.parseScript(guide, "start#page(hidden:sound(id:'60bpm2min.mp3'))");
        
        assertEquals("60bpm2min.mp3", guide.findPage("start").getAudios().get(0).getSrc());
    }

    
    @Test
    public void hiddenSoundWithLoops() {
        sut.parseScript(guide, "start#page(hidden:sound(id:'60bpm2min.mp3',loops:3))");
        
        assertEquals("60bpm2min.mp3", guide.findPage("start").getAudios().get(0).getSrc());
        assertEquals(3, guide.findPage("start").getAudios().get(0).getLoops());
    }
}
