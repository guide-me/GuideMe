// Generated from NyxScript.g4 by ANTLR 4.4
package org.guideme.guideme.milovana.nyxparser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class NyxScriptParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.4", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__38=1, T__37=2, T__36=3, T__35=4, T__34=5, T__33=6, T__32=7, T__31=8, 
		T__30=9, T__29=10, T__28=11, T__27=12, T__26=13, T__25=14, T__24=15, T__23=16, 
		T__22=17, T__21=18, T__20=19, T__19=20, T__18=21, T__17=22, T__16=23, 
		T__15=24, T__14=25, T__13=26, T__12=27, T__11=28, T__10=29, T__9=30, T__8=31, 
		T__7=32, T__6=33, T__5=34, T__4=35, T__3=36, T__2=37, T__1=38, T__0=39, 
		DELAY_STYLE=40, TIME_UNIT=41, QUOTED_STRING=42, PAGE_ID=43, INT=44, NEWLINE=45, 
		WS=46;
	public static final String[] tokenNames = {
		"<INVALID>", "'action:'", "'instruc:'", "'media:pic('", "'mustnot('", 
		"'hidden:sound('", "'id:'", "'vert'", "'mult'", "'target'", "'self:'", 
		"'target:'", "'max:'", "'random('", "'range('", "'yes:'", "'cap'", "'('", 
		"'from:'", "'yn('", "'action'", "','", "'unset('", "'style:'", "'time:'", 
		"'loops:'", "'go('", "'buttons('", "'must('", "'to:'", "':'", "'horiz'", 
		"'delay('", "'text:'", "'no:'", "'page('", "'e'", "'min:'", "')'", "'prefix'", 
		"DELAY_STYLE", "TIME_UNIT", "QUOTED_STRING", "PAGE_ID", "INT", "NEWLINE", 
		"WS"
	};
	public static final int
		RULE_guide = 0, RULE_page = 1, RULE_must_not = 2, RULE_must = 3, RULE_page_option = 4, 
		RULE_action = 5, RULE_action_group = 6, RULE_action_prefix = 7, RULE_action_unset = 8, 
		RULE_text = 9, RULE_media_pic = 10, RULE_hidden_sound = 11, RULE_action_buttons = 12, 
		RULE_button = 13, RULE_action_delay = 14, RULE_time = 15, RULE_action_go = 16, 
		RULE_action_yn = 17, RULE_yes_button = 18, RULE_no_button = 19, RULE_action_target = 20, 
		RULE_range = 21;
	public static final String[] ruleNames = {
		"guide", "page", "must_not", "must", "page_option", "action", "action_group", 
		"action_prefix", "action_unset", "text", "media_pic", "hidden_sound", 
		"action_buttons", "button", "action_delay", "time", "action_go", "action_yn", 
		"yes_button", "no_button", "action_target", "range"
	};

	@Override
	public String getGrammarFileName() { return "NyxScript.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public NyxScriptParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GuideContext extends ParserRuleContext {
		public List<PageContext> page() {
			return getRuleContexts(PageContext.class);
		}
		public PageContext page(int i) {
			return getRuleContext(PageContext.class,i);
		}
		public GuideContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_guide; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterGuide(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitGuide(this);
		}
	}

	public final GuideContext guide() throws RecognitionException {
		GuideContext _localctx = new GuideContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_guide);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__11) | (1L << PAGE_ID))) != 0)) {
				{
				{
				setState(44); page();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PageContext extends ParserRuleContext {
		public Must_notContext must_not() {
			return getRuleContext(Must_notContext.class,0);
		}
		public Page_optionContext page_option(int i) {
			return getRuleContext(Page_optionContext.class,i);
		}
		public MustContext must() {
			return getRuleContext(MustContext.class,0);
		}
		public TerminalNode PAGE_ID() { return getToken(NyxScriptParser.PAGE_ID, 0); }
		public List<Page_optionContext> page_option() {
			return getRuleContexts(Page_optionContext.class);
		}
		public PageContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_page; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterPage(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitPage(this);
		}
	}

	public final PageContext page() throws RecognitionException {
		PageContext _localctx = new PageContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_page);
		int _la;
		try {
			setState(65);
			switch (_input.LA(1)) {
			case T__35:
				enterOuterAlt(_localctx, 1);
				{
				setState(50); must_not();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(51); must();
				}
				break;
			case PAGE_ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(52); match(PAGE_ID);
				setState(53); match(T__4);
				setState(62);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__37) | (1L << T__36) | (1L << T__34) | (1L << T__17) | (1L << T__6) | (1L << T__3))) != 0)) {
					{
					setState(54); page_option();
					setState(59);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__18) {
						{
						{
						setState(55); match(T__18);
						setState(56); page_option();
						}
						}
						setState(61);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(64); match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Must_notContext extends ParserRuleContext {
		public TerminalNode INT(int i) {
			return getToken(NyxScriptParser.INT, i);
		}
		public TerminalNode PAGE_ID(int i) {
			return getToken(NyxScriptParser.PAGE_ID, i);
		}
		public List<TerminalNode> PAGE_ID() { return getTokens(NyxScriptParser.PAGE_ID); }
		public List<TerminalNode> INT() { return getTokens(NyxScriptParser.INT); }
		public Must_notContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_must_not; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterMust_not(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitMust_not(this);
		}
	}

	public final Must_notContext must_not() throws RecognitionException {
		Must_notContext _localctx = new Must_notContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_must_not);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67); match(T__35);
			setState(68); match(T__29);
			setState(69); match(PAGE_ID);
			setState(75); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(70); match(T__18);
				setState(71); match(T__19);
				setState(72); match(INT);
				setState(73); match(T__9);
				setState(74); match(PAGE_ID);
				}
				}
				setState(77); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__18 );
			setState(79); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MustContext extends ParserRuleContext {
		public TerminalNode INT(int i) {
			return getToken(NyxScriptParser.INT, i);
		}
		public TerminalNode PAGE_ID(int i) {
			return getToken(NyxScriptParser.PAGE_ID, i);
		}
		public List<TerminalNode> PAGE_ID() { return getTokens(NyxScriptParser.PAGE_ID); }
		public List<TerminalNode> INT() { return getTokens(NyxScriptParser.INT); }
		public MustContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_must; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterMust(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitMust(this);
		}
	}

	public final MustContext must() throws RecognitionException {
		MustContext _localctx = new MustContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_must);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81); match(T__11);
			setState(82); match(T__29);
			setState(83); match(PAGE_ID);
			setState(89); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(84); match(T__18);
				setState(85); match(T__19);
				setState(86); match(INT);
				setState(87); match(T__9);
				setState(88); match(PAGE_ID);
				}
				}
				setState(91); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__18 );
			setState(93); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Page_optionContext extends ParserRuleContext {
		public Hidden_soundContext hidden_sound() {
			return getRuleContext(Hidden_soundContext.class,0);
		}
		public Action_groupContext action_group() {
			return getRuleContext(Action_groupContext.class,0);
		}
		public Media_picContext media_pic() {
			return getRuleContext(Media_picContext.class,0);
		}
		public TextContext text() {
			return getRuleContext(TextContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public Page_optionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_page_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterPage_option(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitPage_option(this);
		}
	}

	public final Page_optionContext page_option() throws RecognitionException {
		Page_optionContext _localctx = new Page_optionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_page_option);
		try {
			setState(100);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(95); text();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(96); media_pic();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(97); hidden_sound();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(98); action_group();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(99); action();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionContext extends ParserRuleContext {
		public Action_ynContext action_yn() {
			return getRuleContext(Action_ynContext.class,0);
		}
		public Action_goContext action_go() {
			return getRuleContext(Action_goContext.class,0);
		}
		public Action_unsetContext action_unset() {
			return getRuleContext(Action_unsetContext.class,0);
		}
		public Action_buttonsContext action_buttons() {
			return getRuleContext(Action_buttonsContext.class,0);
		}
		public Action_delayContext action_delay() {
			return getRuleContext(Action_delayContext.class,0);
		}
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_action);
		try {
			setState(107);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(102); action_buttons();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(103); action_delay();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(104); action_go();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(105); action_yn();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(106); action_unset();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_groupContext extends ParserRuleContext {
		public Action_prefixContext action_prefix() {
			return getRuleContext(Action_prefixContext.class,0);
		}
		public List<ActionContext> action() {
			return getRuleContexts(ActionContext.class);
		}
		public ActionContext action(int i) {
			return getRuleContext(ActionContext.class,i);
		}
		public Action_groupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_group(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_group(this);
		}
	}

	public final Action_groupContext action_group() throws RecognitionException {
		Action_groupContext _localctx = new Action_groupContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_action_group);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109); action_prefix();
			setState(110);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__31) | (1L << T__8))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(111); match(T__22);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__37) | (1L << T__17) | (1L << T__3))) != 0)) {
				{
				{
				setState(112); action();
				setState(114);
				_la = _input.LA(1);
				if (_la==T__18) {
					{
					setState(113); match(T__18);
					}
				}

				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_prefixContext extends ParserRuleContext {
		public TerminalNode INT() { return getToken(NyxScriptParser.INT, 0); }
		public Action_prefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_prefix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_prefix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_prefix(this);
		}
	}

	public final Action_prefixContext action_prefix() throws RecognitionException {
		Action_prefixContext _localctx = new Action_prefixContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_action_prefix);
		try {
			setState(128);
			switch (_input.LA(1)) {
			case T__38:
				enterOuterAlt(_localctx, 1);
				{
				setState(123); match(T__38);
				}
				break;
			case T__37:
				enterOuterAlt(_localctx, 2);
				{
				setState(124); match(T__37);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(125); match(T__3);
				setState(126); match(INT);
				setState(127); match(T__9);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_unsetContext extends ParserRuleContext {
		public TerminalNode INT(int i) {
			return getToken(NyxScriptParser.INT, i);
		}
		public Action_prefixContext action_prefix() {
			return getRuleContext(Action_prefixContext.class,0);
		}
		public TerminalNode PAGE_ID(int i) {
			return getToken(NyxScriptParser.PAGE_ID, i);
		}
		public List<TerminalNode> INT() { return getTokens(NyxScriptParser.INT); }
		public List<TerminalNode> PAGE_ID() { return getTokens(NyxScriptParser.PAGE_ID); }
		public Action_unsetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_unset; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_unset(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_unset(this);
		}
	}

	public final Action_unsetContext action_unset() throws RecognitionException {
		Action_unsetContext _localctx = new Action_unsetContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_action_unset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__37) | (1L << T__3))) != 0)) {
				{
				setState(130); action_prefix();
				}
			}

			setState(133); match(T__17);
			setState(134); match(T__19);
			setState(135); match(INT);
			setState(136); match(T__9);
			setState(137); match(PAGE_ID);
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(138); match(T__18);
				setState(139); match(T__19);
				setState(140); match(INT);
				setState(141); match(T__9);
				setState(142); match(PAGE_ID);
				}
				}
				setState(147);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(148); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TextContext extends ParserRuleContext {
		public TerminalNode QUOTED_STRING() { return getToken(NyxScriptParser.QUOTED_STRING, 0); }
		public TextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_text; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitText(this);
		}
	}

	public final TextContext text() throws RecognitionException {
		TextContext _localctx = new TextContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150); match(T__6);
			setState(151); match(QUOTED_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Media_picContext extends ParserRuleContext {
		public TerminalNode QUOTED_STRING() { return getToken(NyxScriptParser.QUOTED_STRING, 0); }
		public Media_picContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_media_pic; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterMedia_pic(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitMedia_pic(this);
		}
	}

	public final Media_picContext media_pic() throws RecognitionException {
		Media_picContext _localctx = new Media_picContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_media_pic);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153); match(T__36);
			setState(154); match(T__33);
			setState(155); match(QUOTED_STRING);
			setState(156); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Hidden_soundContext extends ParserRuleContext {
		public TerminalNode QUOTED_STRING() { return getToken(NyxScriptParser.QUOTED_STRING, 0); }
		public TerminalNode INT() { return getToken(NyxScriptParser.INT, 0); }
		public Hidden_soundContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hidden_sound; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterHidden_sound(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitHidden_sound(this);
		}
	}

	public final Hidden_soundContext hidden_sound() throws RecognitionException {
		Hidden_soundContext _localctx = new Hidden_soundContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_hidden_sound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158); match(T__34);
			setState(159); match(T__33);
			setState(160); match(QUOTED_STRING);
			setState(164);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(161); match(T__18);
				setState(162); match(T__14);
				setState(163); match(INT);
				}
			}

			setState(166); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_buttonsContext extends ParserRuleContext {
		public Action_prefixContext action_prefix() {
			return getRuleContext(Action_prefixContext.class,0);
		}
		public ButtonContext button(int i) {
			return getRuleContext(ButtonContext.class,i);
		}
		public List<ButtonContext> button() {
			return getRuleContexts(ButtonContext.class);
		}
		public Action_buttonsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_buttons; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_buttons(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_buttons(this);
		}
	}

	public final Action_buttonsContext action_buttons() throws RecognitionException {
		Action_buttonsContext _localctx = new Action_buttonsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_action_buttons);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); action_prefix();
			setState(169); match(T__12);
			setState(174); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(170); button();
				setState(172);
				_la = _input.LA(1);
				if (_la==T__18) {
					{
					setState(171); match(T__18);
					}
				}

				}
				}
				setState(176); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__30 );
			setState(178); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ButtonContext extends ParserRuleContext {
		public TerminalNode INT(int i) {
			return getToken(NyxScriptParser.INT, i);
		}
		public TerminalNode QUOTED_STRING() { return getToken(NyxScriptParser.QUOTED_STRING, 0); }
		public List<TerminalNode> INT() { return getTokens(NyxScriptParser.INT); }
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public TerminalNode PAGE_ID() { return getToken(NyxScriptParser.PAGE_ID, 0); }
		public ButtonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_button; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterButton(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitButton(this);
		}
	}

	public final ButtonContext button() throws RecognitionException {
		ButtonContext _localctx = new ButtonContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_button);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(180); match(T__30);
			setState(181); match(INT);
			}
			setState(183); match(T__9);
			setState(186);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(184); range();
				}
				break;
			case PAGE_ID:
				{
				setState(185); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(188); match(T__18);
			{
			setState(189); match(T__23);
			setState(190); match(INT);
			}
			setState(192); match(T__9);
			setState(193); match(QUOTED_STRING);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_delayContext extends ParserRuleContext {
		public Action_prefixContext action_prefix() {
			return getRuleContext(Action_prefixContext.class,0);
		}
		public TerminalNode DELAY_STYLE() { return getToken(NyxScriptParser.DELAY_STYLE, 0); }
		public TimeContext time() {
			return getRuleContext(TimeContext.class,0);
		}
		public Action_targetContext action_target() {
			return getRuleContext(Action_targetContext.class,0);
		}
		public Action_delayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_delay; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_delay(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_delay(this);
		}
	}

	public final Action_delayContext action_delay() throws RecognitionException {
		Action_delayContext _localctx = new Action_delayContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_action_delay);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195); action_prefix();
			setState(196); match(T__7);
			setState(197); time();
			setState(198); match(T__18);
			setState(199); action_target();
			setState(203);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(200); match(T__18);
				setState(201); match(T__16);
				setState(202); match(DELAY_STYLE);
				}
			}

			setState(205); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TimeContext extends ParserRuleContext {
		public TerminalNode INT(int i) {
			return getToken(NyxScriptParser.INT, i);
		}
		public List<TerminalNode> INT() { return getTokens(NyxScriptParser.INT); }
		public List<TerminalNode> TIME_UNIT() { return getTokens(NyxScriptParser.TIME_UNIT); }
		public TerminalNode TIME_UNIT(int i) {
			return getToken(NyxScriptParser.TIME_UNIT, i);
		}
		public TimeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_time; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterTime(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitTime(this);
		}
	}

	public final TimeContext time() throws RecognitionException {
		TimeContext _localctx = new TimeContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_time);
		int _la;
		try {
			setState(226);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(207); match(T__15);
				setState(208); match(T__26);
				setState(209); match(T__2);
				setState(210); match(INT);
				setState(212);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(211); match(TIME_UNIT);
					}
				}

				setState(214); match(T__18);
				setState(215); match(T__27);
				setState(216); match(INT);
				setState(218);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(217); match(TIME_UNIT);
					}
				}

				setState(220); match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(221); match(T__15);
				setState(222); match(INT);
				setState(224);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(223); match(TIME_UNIT);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_goContext extends ParserRuleContext {
		public Action_prefixContext action_prefix() {
			return getRuleContext(Action_prefixContext.class,0);
		}
		public Action_targetContext action_target() {
			return getRuleContext(Action_targetContext.class,0);
		}
		public Action_goContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_go; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_go(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_go(this);
		}
	}

	public final Action_goContext action_go() throws RecognitionException {
		Action_goContext _localctx = new Action_goContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_action_go);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(228); action_prefix();
			setState(229); match(T__13);
			setState(230); action_target();
			setState(231); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_ynContext extends ParserRuleContext {
		public Action_prefixContext action_prefix() {
			return getRuleContext(Action_prefixContext.class,0);
		}
		public Yes_buttonContext yes_button() {
			return getRuleContext(Yes_buttonContext.class,0);
		}
		public No_buttonContext no_button() {
			return getRuleContext(No_buttonContext.class,0);
		}
		public Action_ynContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_yn; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_yn(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_yn(this);
		}
	}

	public final Action_ynContext action_yn() throws RecognitionException {
		Action_ynContext _localctx = new Action_ynContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_action_yn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(233); action_prefix();
			setState(234); match(T__20);
			setState(235); yes_button();
			setState(236); match(T__18);
			setState(237); no_button();
			setState(238); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Yes_buttonContext extends ParserRuleContext {
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public TerminalNode PAGE_ID() { return getToken(NyxScriptParser.PAGE_ID, 0); }
		public Yes_buttonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yes_button; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterYes_button(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitYes_button(this);
		}
	}

	public final Yes_buttonContext yes_button() throws RecognitionException {
		Yes_buttonContext _localctx = new Yes_buttonContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_yes_button);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(240); match(T__24);
			setState(243);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(241); range();
				}
				break;
			case PAGE_ID:
				{
				setState(242); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class No_buttonContext extends ParserRuleContext {
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public TerminalNode PAGE_ID() { return getToken(NyxScriptParser.PAGE_ID, 0); }
		public No_buttonContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_no_button; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterNo_button(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitNo_button(this);
		}
	}

	public final No_buttonContext no_button() throws RecognitionException {
		No_buttonContext _localctx = new No_buttonContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_no_button);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245); match(T__5);
			setState(248);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(246); range();
				}
				break;
			case PAGE_ID:
				{
				setState(247); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Action_targetContext extends ParserRuleContext {
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public TerminalNode PAGE_ID() { return getToken(NyxScriptParser.PAGE_ID, 0); }
		public Action_targetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_target; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_target(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_target(this);
		}
	}

	public final Action_targetContext action_target() throws RecognitionException {
		Action_targetContext _localctx = new Action_targetContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_action_target);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(250); match(T__28);
			setState(253);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(251); range();
				}
				break;
			case PAGE_ID:
				{
				setState(252); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RangeContext extends ParserRuleContext {
		public TerminalNode INT(int i) {
			return getToken(NyxScriptParser.INT, i);
		}
		public TerminalNode QUOTED_STRING() { return getToken(NyxScriptParser.QUOTED_STRING, 0); }
		public List<TerminalNode> INT() { return getTokens(NyxScriptParser.INT); }
		public RangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_range; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitRange(this);
		}
	}

	public final RangeContext range() throws RecognitionException {
		RangeContext _localctx = new RangeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_range);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255); match(T__25);
			setState(256); match(T__21);
			setState(257); match(INT);
			setState(258); match(T__18);
			setState(259); match(T__10);
			setState(260); match(INT);
			setState(261); match(T__18);
			setState(263);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(262); match(T__0);
				}
			}

			setState(265); match(T__9);
			setState(266); match(QUOTED_STRING);
			setState(267); match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\60\u0110\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\7\2\60\n\2\f\2"+
		"\16\2\63\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3<\n\3\f\3\16\3?\13\3\5\3"+
		"A\n\3\3\3\5\3D\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\6\4N\n\4\r\4\16\4O"+
		"\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\6\5\\\n\5\r\5\16\5]\3\5\3\5\3"+
		"\6\3\6\3\6\3\6\3\6\5\6g\n\6\3\7\3\7\3\7\3\7\3\7\5\7n\n\7\3\b\3\b\3\b\3"+
		"\b\3\b\5\bu\n\b\7\bw\n\b\f\b\16\bz\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5"+
		"\t\u0083\n\t\3\n\5\n\u0086\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n"+
		"\7\n\u0092\n\n\f\n\16\n\u0095\13\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f"+
		"\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00a7\n\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\5\16\u00af\n\16\6\16\u00b1\n\16\r\16\16\16\u00b2\3\16\3\16\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\5\17\u00bd\n\17\3\17\3\17\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\5\20\u00ce\n\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\5\21\u00d7\n\21\3\21\3\21\3\21\3\21\5\21\u00dd"+
		"\n\21\3\21\3\21\3\21\3\21\5\21\u00e3\n\21\5\21\u00e5\n\21\3\22\3\22\3"+
		"\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24\5\24\u00f6"+
		"\n\24\3\25\3\25\3\25\5\25\u00fb\n\25\3\26\3\26\3\26\5\26\u0100\n\26\3"+
		"\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27\u010a\n\27\3\27\3\27\3\27"+
		"\3\27\3\27\2\2\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,\2\3\4"+
		"\2\t\n!!\u011b\2\61\3\2\2\2\4C\3\2\2\2\6E\3\2\2\2\bS\3\2\2\2\nf\3\2\2"+
		"\2\fm\3\2\2\2\16o\3\2\2\2\20\u0082\3\2\2\2\22\u0085\3\2\2\2\24\u0098\3"+
		"\2\2\2\26\u009b\3\2\2\2\30\u00a0\3\2\2\2\32\u00aa\3\2\2\2\34\u00b6\3\2"+
		"\2\2\36\u00c5\3\2\2\2 \u00e4\3\2\2\2\"\u00e6\3\2\2\2$\u00eb\3\2\2\2&\u00f2"+
		"\3\2\2\2(\u00f7\3\2\2\2*\u00fc\3\2\2\2,\u0101\3\2\2\2.\60\5\4\3\2/.\3"+
		"\2\2\2\60\63\3\2\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\3\3\2\2\2\63\61\3\2"+
		"\2\2\64D\5\6\4\2\65D\5\b\5\2\66\67\7-\2\2\67@\7%\2\28=\5\n\6\29:\7\27"+
		"\2\2:<\5\n\6\2;9\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>A\3\2\2\2?=\3\2"+
		"\2\2@8\3\2\2\2@A\3\2\2\2AB\3\2\2\2BD\7(\2\2C\64\3\2\2\2C\65\3\2\2\2C\66"+
		"\3\2\2\2D\5\3\2\2\2EF\7\6\2\2FG\7\f\2\2GM\7-\2\2HI\7\27\2\2IJ\7\26\2\2"+
		"JK\7.\2\2KL\7 \2\2LN\7-\2\2MH\3\2\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2\2PQ"+
		"\3\2\2\2QR\7(\2\2R\7\3\2\2\2ST\7\36\2\2TU\7\f\2\2U[\7-\2\2VW\7\27\2\2"+
		"WX\7\26\2\2XY\7.\2\2YZ\7 \2\2Z\\\7-\2\2[V\3\2\2\2\\]\3\2\2\2][\3\2\2\2"+
		"]^\3\2\2\2^_\3\2\2\2_`\7(\2\2`\t\3\2\2\2ag\5\24\13\2bg\5\26\f\2cg\5\30"+
		"\r\2dg\5\16\b\2eg\5\f\7\2fa\3\2\2\2fb\3\2\2\2fc\3\2\2\2fd\3\2\2\2fe\3"+
		"\2\2\2g\13\3\2\2\2hn\5\32\16\2in\5\36\20\2jn\5\"\22\2kn\5$\23\2ln\5\22"+
		"\n\2mh\3\2\2\2mi\3\2\2\2mj\3\2\2\2mk\3\2\2\2ml\3\2\2\2n\r\3\2\2\2op\5"+
		"\20\t\2pq\t\2\2\2qx\7\23\2\2rt\5\f\7\2su\7\27\2\2ts\3\2\2\2tu\3\2\2\2"+
		"uw\3\2\2\2vr\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2"+
		"{|\7(\2\2|\17\3\2\2\2}\u0083\7\3\2\2~\u0083\7\4\2\2\177\u0080\7&\2\2\u0080"+
		"\u0081\7.\2\2\u0081\u0083\7 \2\2\u0082}\3\2\2\2\u0082~\3\2\2\2\u0082\177"+
		"\3\2\2\2\u0083\21\3\2\2\2\u0084\u0086\5\20\t\2\u0085\u0084\3\2\2\2\u0085"+
		"\u0086\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\7\30\2\2\u0088\u0089\7"+
		"\26\2\2\u0089\u008a\7.\2\2\u008a\u008b\7 \2\2\u008b\u0093\7-\2\2\u008c"+
		"\u008d\7\27\2\2\u008d\u008e\7\26\2\2\u008e\u008f\7.\2\2\u008f\u0090\7"+
		" \2\2\u0090\u0092\7-\2\2\u0091\u008c\3\2\2\2\u0092\u0095\3\2\2\2\u0093"+
		"\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094\u0096\3\2\2\2\u0095\u0093\3\2"+
		"\2\2\u0096\u0097\7(\2\2\u0097\23\3\2\2\2\u0098\u0099\7#\2\2\u0099\u009a"+
		"\7,\2\2\u009a\25\3\2\2\2\u009b\u009c\7\5\2\2\u009c\u009d\7\b\2\2\u009d"+
		"\u009e\7,\2\2\u009e\u009f\7(\2\2\u009f\27\3\2\2\2\u00a0\u00a1\7\7\2\2"+
		"\u00a1\u00a2\7\b\2\2\u00a2\u00a6\7,\2\2\u00a3\u00a4\7\27\2\2\u00a4\u00a5"+
		"\7\33\2\2\u00a5\u00a7\7.\2\2\u00a6\u00a3\3\2\2\2\u00a6\u00a7\3\2\2\2\u00a7"+
		"\u00a8\3\2\2\2\u00a8\u00a9\7(\2\2\u00a9\31\3\2\2\2\u00aa\u00ab\5\20\t"+
		"\2\u00ab\u00b0\7\35\2\2\u00ac\u00ae\5\34\17\2\u00ad\u00af\7\27\2\2\u00ae"+
		"\u00ad\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2\2\2\u00b0\u00ac\3\2"+
		"\2\2\u00b1\u00b2\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00b5\7(\2\2\u00b5\33\3\2\2\2\u00b6\u00b7\7\13\2"+
		"\2\u00b7\u00b8\7.\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bc\7 \2\2\u00ba\u00bd"+
		"\5,\27\2\u00bb\u00bd\7-\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bb\3\2\2\2\u00bd"+
		"\u00be\3\2\2\2\u00be\u00bf\7\27\2\2\u00bf\u00c0\7\22\2\2\u00c0\u00c1\7"+
		".\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\7 \2\2\u00c3\u00c4\7,\2\2\u00c4"+
		"\35\3\2\2\2\u00c5\u00c6\5\20\t\2\u00c6\u00c7\7\"\2\2\u00c7\u00c8\5 \21"+
		"\2\u00c8\u00c9\7\27\2\2\u00c9\u00cd\5*\26\2\u00ca\u00cb\7\27\2\2\u00cb"+
		"\u00cc\7\31\2\2\u00cc\u00ce\7*\2\2\u00cd\u00ca\3\2\2\2\u00cd\u00ce\3\2"+
		"\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00d0\7(\2\2\u00d0\37\3\2\2\2\u00d1\u00d2"+
		"\7\32\2\2\u00d2\u00d3\7\17\2\2\u00d3\u00d4\7\'\2\2\u00d4\u00d6\7.\2\2"+
		"\u00d5\u00d7\7+\2\2\u00d6\u00d5\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d8"+
		"\3\2\2\2\u00d8\u00d9\7\27\2\2\u00d9\u00da\7\16\2\2\u00da\u00dc\7.\2\2"+
		"\u00db\u00dd\7+\2\2\u00dc\u00db\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00de"+
		"\3\2\2\2\u00de\u00e5\7(\2\2\u00df\u00e0\7\32\2\2\u00e0\u00e2\7.\2\2\u00e1"+
		"\u00e3\7+\2\2\u00e2\u00e1\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e5\3\2"+
		"\2\2\u00e4\u00d1\3\2\2\2\u00e4\u00df\3\2\2\2\u00e5!\3\2\2\2\u00e6\u00e7"+
		"\5\20\t\2\u00e7\u00e8\7\34\2\2\u00e8\u00e9\5*\26\2\u00e9\u00ea\7(\2\2"+
		"\u00ea#\3\2\2\2\u00eb\u00ec\5\20\t\2\u00ec\u00ed\7\25\2\2\u00ed\u00ee"+
		"\5&\24\2\u00ee\u00ef\7\27\2\2\u00ef\u00f0\5(\25\2\u00f0\u00f1\7(\2\2\u00f1"+
		"%\3\2\2\2\u00f2\u00f5\7\21\2\2\u00f3\u00f6\5,\27\2\u00f4\u00f6\7-\2\2"+
		"\u00f5\u00f3\3\2\2\2\u00f5\u00f4\3\2\2\2\u00f6\'\3\2\2\2\u00f7\u00fa\7"+
		"$\2\2\u00f8\u00fb\5,\27\2\u00f9\u00fb\7-\2\2\u00fa\u00f8\3\2\2\2\u00fa"+
		"\u00f9\3\2\2\2\u00fb)\3\2\2\2\u00fc\u00ff\7\r\2\2\u00fd\u0100\5,\27\2"+
		"\u00fe\u0100\7-\2\2\u00ff\u00fd\3\2\2\2\u00ff\u00fe\3\2\2\2\u0100+\3\2"+
		"\2\2\u0101\u0102\7\20\2\2\u0102\u0103\7\24\2\2\u0103\u0104\7.\2\2\u0104"+
		"\u0105\7\27\2\2\u0105\u0106\7\37\2\2\u0106\u0107\7.\2\2\u0107\u0109\7"+
		"\27\2\2\u0108\u010a\7)\2\2\u0109\u0108\3\2\2\2\u0109\u010a\3\2\2\2\u010a"+
		"\u010b\3\2\2\2\u010b\u010c\7 \2\2\u010c\u010d\7,\2\2\u010d\u010e\7(\2"+
		"\2\u010e-\3\2\2\2\34\61=@CO]fmtx\u0082\u0085\u0093\u00a6\u00ae\u00b2\u00bc"+
		"\u00cd\u00d6\u00dc\u00e2\u00e4\u00f5\u00fa\u00ff\u0109";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}