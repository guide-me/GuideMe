package org.guideme.guideme.milovana;

import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.io.IOUtils;
import org.guideme.guideme.milovana.nyxparser.NyxScriptBaseListener;
import org.guideme.guideme.milovana.nyxparser.NyxScriptLexer;
import org.guideme.guideme.milovana.nyxparser.NyxScriptParser;
import org.guideme.guideme.model.Guide;
import org.guideme.guideme.model.Page;
import org.openide.util.Exceptions;

public class FlashTeaseConverter {
    
    public Guide createGuide(String input) {
        
        try {
            ANTLRInputStream antlrStream = new ANTLRInputStream(IOUtils.toInputStream(input, "UTF-8"));
            NyxScriptLexer lexer = new NyxScriptLexer(antlrStream);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            NyxScriptParser parser = new NyxScriptParser(tokens);
            ParseTree tree = parser.guide();
            
            ParseTreeWalker walker = new ParseTreeWalker();
            NyxScriptGuideBuilder builder = new NyxScriptGuideBuilder();

            walker.walk(builder, tree);
            
            return builder.getGuide();
            
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
            return null;
        }
    }
    
    
    private class NyxScriptGuideBuilder extends NyxScriptBaseListener {

        private final Guide guide;
        
        private Page currentPage;
        
        public NyxScriptGuideBuilder() {
            guide = new Guide();
        }
        
        public Guide getGuide() {
            return guide;
        }

        @Override
        public void enterPage(NyxScriptParser.PageContext ctx) {
            currentPage = new Page();
            
            TerminalNode pageId = ctx.PAGE_ID();
            if (pageId != null) {
                currentPage.setId(pageId.getText());
            }
            super.enterPage(ctx);
        }

        @Override
        public void exitPage(NyxScriptParser.PageContext ctx) {
            if (currentPage != null) {
                guide.addPage(currentPage);
                currentPage = null;
            }
            super.exitPage(ctx);
        }
        
        
        
        
        
    }

}
