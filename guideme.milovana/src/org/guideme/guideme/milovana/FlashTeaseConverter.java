package org.guideme.guideme.milovana;

import java.io.IOException;
import java.io.InputStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
                // if pageId still ends with a #, strip it.
                String pageIdText =  StringUtils.stripEnd(pageId.getText().trim(), "#");
                currentPage.setId(pageIdText);
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

        @Override
        public void enterText(NyxScriptParser.TextContext ctx) {
            if (currentPage != null) {
                TerminalNode quotedString = ctx.QUOTED_STRING();
                if (quotedString != null) {
                    // Strip surrounding quotes.
                    String textString = StringUtils.strip(quotedString.getText().trim(), "\"'’");
                    currentPage.setText(textString);
                }
            }
            super.enterText(ctx);
        }

    }

}
