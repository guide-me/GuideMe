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
		RULE_action_yn = 17, RULE_action_target = 18, RULE_range = 19;
	public static final String[] ruleNames = {
		"guide", "page", "must_not", "must", "page_option", "action", "action_group", 
		"action_prefix", "action_unset", "text", "media_pic", "hidden_sound", 
		"action_buttons", "button", "action_delay", "time", "action_go", "action_yn", 
		"action_target", "range"
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
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__35) | (1L << T__11) | (1L << PAGE_ID))) != 0)) {
				{
				{
				setState(40); page();
				}
				}
				setState(45);
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
			setState(61);
			switch (_input.LA(1)) {
			case T__35:
				enterOuterAlt(_localctx, 1);
				{
				setState(46); must_not();
				}
				break;
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(47); must();
				}
				break;
			case PAGE_ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(48); match(PAGE_ID);
				setState(49); match(T__4);
				setState(58);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__37) | (1L << T__36) | (1L << T__34) | (1L << T__17) | (1L << T__6) | (1L << T__3))) != 0)) {
					{
					setState(50); page_option();
					setState(55);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__18) {
						{
						{
						setState(51); match(T__18);
						setState(52); page_option();
						}
						}
						setState(57);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(60); match(T__1);
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
			setState(63); match(T__35);
			setState(64); match(T__29);
			setState(65); match(PAGE_ID);
			setState(71); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(66); match(T__18);
				setState(67); match(T__19);
				setState(68); match(INT);
				setState(69); match(T__9);
				setState(70); match(PAGE_ID);
				}
				}
				setState(73); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__18 );
			setState(75); match(T__1);
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
			setState(77); match(T__11);
			setState(78); match(T__29);
			setState(79); match(PAGE_ID);
			setState(85); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(80); match(T__18);
				setState(81); match(T__19);
				setState(82); match(INT);
				setState(83); match(T__9);
				setState(84); match(PAGE_ID);
				}
				}
				setState(87); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__18 );
			setState(89); match(T__1);
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
			setState(96);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(91); text();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(92); media_pic();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(93); hidden_sound();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(94); action_group();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(95); action();
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
			setState(103);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(98); action_buttons();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(99); action_delay();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(100); action_go();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(101); action_yn();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(102); action_unset();
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
			setState(105); action_prefix();
			setState(106);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__32) | (1L << T__31) | (1L << T__8))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(107); match(T__22);
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__37) | (1L << T__17) | (1L << T__3))) != 0)) {
				{
				{
				setState(108); action();
				setState(110);
				_la = _input.LA(1);
				if (_la==T__18) {
					{
					setState(109); match(T__18);
					}
				}

				}
				}
				setState(116);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(117); match(T__1);
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
			setState(124);
			switch (_input.LA(1)) {
			case T__38:
				enterOuterAlt(_localctx, 1);
				{
				setState(119); match(T__38);
				}
				break;
			case T__37:
				enterOuterAlt(_localctx, 2);
				{
				setState(120); match(T__37);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(121); match(T__3);
				setState(122); match(INT);
				setState(123); match(T__9);
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
			setState(127);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__38) | (1L << T__37) | (1L << T__3))) != 0)) {
				{
				setState(126); action_prefix();
				}
			}

			setState(129); match(T__17);
			setState(130); match(T__19);
			setState(131); match(INT);
			setState(132); match(T__9);
			setState(133); match(PAGE_ID);
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__18) {
				{
				{
				setState(134); match(T__18);
				setState(135); match(T__19);
				setState(136); match(INT);
				setState(137); match(T__9);
				setState(138); match(PAGE_ID);
				}
				}
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(144); match(T__1);
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
			setState(146); match(T__6);
			setState(147); match(QUOTED_STRING);
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
			setState(149); match(T__36);
			setState(150); match(QUOTED_STRING);
			setState(151); match(T__1);
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
			setState(153); match(T__34);
			setState(154); match(T__33);
			setState(155); match(QUOTED_STRING);
			setState(159);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(156); match(T__18);
				setState(157); match(T__14);
				setState(158); match(INT);
				}
			}

			setState(161); match(T__1);
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
			setState(163); action_prefix();
			setState(164); match(T__12);
			setState(169); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(165); button();
				setState(167);
				_la = _input.LA(1);
				if (_la==T__18) {
					{
					setState(166); match(T__18);
					}
				}

				}
				}
				setState(171); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__30 );
			setState(173); match(T__1);
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
			setState(175); match(T__30);
			setState(176); match(INT);
			}
			setState(178); match(T__9);
			setState(181);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(179); range();
				}
				break;
			case PAGE_ID:
				{
				setState(180); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(183); match(T__18);
			{
			setState(184); match(T__23);
			setState(185); match(INT);
			}
			setState(187); match(T__9);
			setState(188); match(QUOTED_STRING);
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
			setState(190); action_prefix();
			setState(191); match(T__7);
			setState(192); time();
			setState(193); match(T__18);
			setState(194); action_target();
			setState(198);
			_la = _input.LA(1);
			if (_la==T__18) {
				{
				setState(195); match(T__18);
				setState(196); match(T__16);
				setState(197); match(DELAY_STYLE);
				}
			}

			setState(200); match(T__1);
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
			setState(221);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(202); match(T__15);
				setState(203); match(T__26);
				setState(204); match(T__2);
				setState(205); match(INT);
				setState(207);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(206); match(TIME_UNIT);
					}
				}

				setState(209); match(T__18);
				setState(210); match(T__27);
				setState(211); match(INT);
				setState(213);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(212); match(TIME_UNIT);
					}
				}

				setState(215); match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(216); match(T__15);
				setState(217); match(INT);
				setState(219);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(218); match(TIME_UNIT);
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
			setState(223); action_prefix();
			setState(224); match(T__13);
			setState(225); action_target();
			setState(226); match(T__1);
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
		public TerminalNode PAGE_ID(int i) {
			return getToken(NyxScriptParser.PAGE_ID, i);
		}
		public RangeContext range(int i) {
			return getRuleContext(RangeContext.class,i);
		}
		public List<RangeContext> range() {
			return getRuleContexts(RangeContext.class);
		}
		public List<TerminalNode> PAGE_ID() { return getTokens(NyxScriptParser.PAGE_ID); }
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
			setState(228); action_prefix();
			setState(229); match(T__20);
			setState(230); match(T__24);
			setState(233);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(231); range();
				}
				break;
			case PAGE_ID:
				{
				setState(232); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(235); match(T__18);
			setState(236); match(T__5);
			setState(239);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(237); range();
				}
				break;
			case PAGE_ID:
				{
				setState(238); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(241); match(T__1);
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
		enterRule(_localctx, 36, RULE_action_target);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(243); match(T__28);
			setState(246);
			switch (_input.LA(1)) {
			case T__25:
				{
				setState(244); range();
				}
				break;
			case PAGE_ID:
				{
				setState(245); match(PAGE_ID);
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
		enterRule(_localctx, 38, RULE_range);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(248); match(T__25);
			setState(249); match(T__21);
			setState(250); match(INT);
			setState(251); match(T__18);
			setState(252); match(T__10);
			setState(253); match(INT);
			setState(254); match(T__18);
			setState(256);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(255); match(T__0);
				}
			}

			setState(258); match(T__9);
			setState(259); match(QUOTED_STRING);
			setState(260); match(T__1);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\60\u0109\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\7\2,\n\2\f\2\16\2/\13\2\3\3\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\7\38\n\3\f\3\16\3;\13\3\5\3=\n\3\3\3\5\3@\n\3\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\6\4J\n\4\r\4\16\4K\3\4\3\4\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\6\5X\n\5\r\5\16\5Y\3\5\3\5\3\6\3\6\3\6\3\6\3\6\5\6c\n\6"+
		"\3\7\3\7\3\7\3\7\3\7\5\7j\n\7\3\b\3\b\3\b\3\b\3\b\5\bq\n\b\7\bs\n\b\f"+
		"\b\16\bv\13\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\5\t\177\n\t\3\n\5\n\u0082\n"+
		"\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n\u008e\n\n\f\n\16\n\u0091"+
		"\13\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\5"+
		"\r\u00a2\n\r\3\r\3\r\3\16\3\16\3\16\3\16\5\16\u00aa\n\16\6\16\u00ac\n"+
		"\16\r\16\16\16\u00ad\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\5\17\u00b8"+
		"\n\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\20\3\20\5\20\u00c9\n\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\5\21\u00d2"+
		"\n\21\3\21\3\21\3\21\3\21\5\21\u00d8\n\21\3\21\3\21\3\21\3\21\5\21\u00de"+
		"\n\21\5\21\u00e0\n\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23"+
		"\5\23\u00ec\n\23\3\23\3\23\3\23\3\23\5\23\u00f2\n\23\3\23\3\23\3\24\3"+
		"\24\3\24\5\24\u00f9\n\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\5\25"+
		"\u0103\n\25\3\25\3\25\3\25\3\25\3\25\2\2\26\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(\2\3\4\2\t\n!!\u0116\2-\3\2\2\2\4?\3\2\2\2\6A\3\2\2"+
		"\2\bO\3\2\2\2\nb\3\2\2\2\fi\3\2\2\2\16k\3\2\2\2\20~\3\2\2\2\22\u0081\3"+
		"\2\2\2\24\u0094\3\2\2\2\26\u0097\3\2\2\2\30\u009b\3\2\2\2\32\u00a5\3\2"+
		"\2\2\34\u00b1\3\2\2\2\36\u00c0\3\2\2\2 \u00df\3\2\2\2\"\u00e1\3\2\2\2"+
		"$\u00e6\3\2\2\2&\u00f5\3\2\2\2(\u00fa\3\2\2\2*,\5\4\3\2+*\3\2\2\2,/\3"+
		"\2\2\2-+\3\2\2\2-.\3\2\2\2.\3\3\2\2\2/-\3\2\2\2\60@\5\6\4\2\61@\5\b\5"+
		"\2\62\63\7-\2\2\63<\7%\2\2\649\5\n\6\2\65\66\7\27\2\2\668\5\n\6\2\67\65"+
		"\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:=\3\2\2\2;9\3\2\2\2<\64\3\2\2"+
		"\2<=\3\2\2\2=>\3\2\2\2>@\7(\2\2?\60\3\2\2\2?\61\3\2\2\2?\62\3\2\2\2@\5"+
		"\3\2\2\2AB\7\6\2\2BC\7\f\2\2CI\7-\2\2DE\7\27\2\2EF\7\26\2\2FG\7.\2\2G"+
		"H\7 \2\2HJ\7-\2\2ID\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2LM\3\2\2\2MN"+
		"\7(\2\2N\7\3\2\2\2OP\7\36\2\2PQ\7\f\2\2QW\7-\2\2RS\7\27\2\2ST\7\26\2\2"+
		"TU\7.\2\2UV\7 \2\2VX\7-\2\2WR\3\2\2\2XY\3\2\2\2YW\3\2\2\2YZ\3\2\2\2Z["+
		"\3\2\2\2[\\\7(\2\2\\\t\3\2\2\2]c\5\24\13\2^c\5\26\f\2_c\5\30\r\2`c\5\16"+
		"\b\2ac\5\f\7\2b]\3\2\2\2b^\3\2\2\2b_\3\2\2\2b`\3\2\2\2ba\3\2\2\2c\13\3"+
		"\2\2\2dj\5\32\16\2ej\5\36\20\2fj\5\"\22\2gj\5$\23\2hj\5\22\n\2id\3\2\2"+
		"\2ie\3\2\2\2if\3\2\2\2ig\3\2\2\2ih\3\2\2\2j\r\3\2\2\2kl\5\20\t\2lm\t\2"+
		"\2\2mt\7\23\2\2np\5\f\7\2oq\7\27\2\2po\3\2\2\2pq\3\2\2\2qs\3\2\2\2rn\3"+
		"\2\2\2sv\3\2\2\2tr\3\2\2\2tu\3\2\2\2uw\3\2\2\2vt\3\2\2\2wx\7(\2\2x\17"+
		"\3\2\2\2y\177\7\3\2\2z\177\7\4\2\2{|\7&\2\2|}\7.\2\2}\177\7 \2\2~y\3\2"+
		"\2\2~z\3\2\2\2~{\3\2\2\2\177\21\3\2\2\2\u0080\u0082\5\20\t\2\u0081\u0080"+
		"\3\2\2\2\u0081\u0082\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0084\7\30\2\2"+
		"\u0084\u0085\7\26\2\2\u0085\u0086\7.\2\2\u0086\u0087\7 \2\2\u0087\u008f"+
		"\7-\2\2\u0088\u0089\7\27\2\2\u0089\u008a\7\26\2\2\u008a\u008b\7.\2\2\u008b"+
		"\u008c\7 \2\2\u008c\u008e\7-\2\2\u008d\u0088\3\2\2\2\u008e\u0091\3\2\2"+
		"\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u008f"+
		"\3\2\2\2\u0092\u0093\7(\2\2\u0093\23\3\2\2\2\u0094\u0095\7#\2\2\u0095"+
		"\u0096\7,\2\2\u0096\25\3\2\2\2\u0097\u0098\7\5\2\2\u0098\u0099\7,\2\2"+
		"\u0099\u009a\7(\2\2\u009a\27\3\2\2\2\u009b\u009c\7\7\2\2\u009c\u009d\7"+
		"\b\2\2\u009d\u00a1\7,\2\2\u009e\u009f\7\27\2\2\u009f\u00a0\7\33\2\2\u00a0"+
		"\u00a2\7.\2\2\u00a1\u009e\3\2\2\2\u00a1\u00a2\3\2\2\2\u00a2\u00a3\3\2"+
		"\2\2\u00a3\u00a4\7(\2\2\u00a4\31\3\2\2\2\u00a5\u00a6\5\20\t\2\u00a6\u00ab"+
		"\7\35\2\2\u00a7\u00a9\5\34\17\2\u00a8\u00aa\7\27\2\2\u00a9\u00a8\3\2\2"+
		"\2\u00a9\u00aa\3\2\2\2\u00aa\u00ac\3\2\2\2\u00ab\u00a7\3\2\2\2\u00ac\u00ad"+
		"\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae\u00af\3\2\2\2\u00af"+
		"\u00b0\7(\2\2\u00b0\33\3\2\2\2\u00b1\u00b2\7\13\2\2\u00b2\u00b3\7.\2\2"+
		"\u00b3\u00b4\3\2\2\2\u00b4\u00b7\7 \2\2\u00b5\u00b8\5(\25\2\u00b6\u00b8"+
		"\7-\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9"+
		"\u00ba\7\27\2\2\u00ba\u00bb\7\22\2\2\u00bb\u00bc\7.\2\2\u00bc\u00bd\3"+
		"\2\2\2\u00bd\u00be\7 \2\2\u00be\u00bf\7,\2\2\u00bf\35\3\2\2\2\u00c0\u00c1"+
		"\5\20\t\2\u00c1\u00c2\7\"\2\2\u00c2\u00c3\5 \21\2\u00c3\u00c4\7\27\2\2"+
		"\u00c4\u00c8\5&\24\2\u00c5\u00c6\7\27\2\2\u00c6\u00c7\7\31\2\2\u00c7\u00c9"+
		"\7*\2\2\u00c8\u00c5\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca"+
		"\u00cb\7(\2\2\u00cb\37\3\2\2\2\u00cc\u00cd\7\32\2\2\u00cd\u00ce\7\17\2"+
		"\2\u00ce\u00cf\7\'\2\2\u00cf\u00d1\7.\2\2\u00d0\u00d2\7+\2\2\u00d1\u00d0"+
		"\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d4\7\27\2\2"+
		"\u00d4\u00d5\7\16\2\2\u00d5\u00d7\7.\2\2\u00d6\u00d8\7+\2\2\u00d7\u00d6"+
		"\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00e0\7(\2\2\u00da"+
		"\u00db\7\32\2\2\u00db\u00dd\7.\2\2\u00dc\u00de\7+\2\2\u00dd\u00dc\3\2"+
		"\2\2\u00dd\u00de\3\2\2\2\u00de\u00e0\3\2\2\2\u00df\u00cc\3\2\2\2\u00df"+
		"\u00da\3\2\2\2\u00e0!\3\2\2\2\u00e1\u00e2\5\20\t\2\u00e2\u00e3\7\34\2"+
		"\2\u00e3\u00e4\5&\24\2\u00e4\u00e5\7(\2\2\u00e5#\3\2\2\2\u00e6\u00e7\5"+
		"\20\t\2\u00e7\u00e8\7\25\2\2\u00e8\u00eb\7\21\2\2\u00e9\u00ec\5(\25\2"+
		"\u00ea\u00ec\7-\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ea\3\2\2\2\u00ec\u00ed"+
		"\3\2\2\2\u00ed\u00ee\7\27\2\2\u00ee\u00f1\7$\2\2\u00ef\u00f2\5(\25\2\u00f0"+
		"\u00f2\7-\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f0\3\2\2\2\u00f2\u00f3\3\2"+
		"\2\2\u00f3\u00f4\7(\2\2\u00f4%\3\2\2\2\u00f5\u00f8\7\r\2\2\u00f6\u00f9"+
		"\5(\25\2\u00f7\u00f9\7-\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f7\3\2\2\2\u00f9"+
		"\'\3\2\2\2\u00fa\u00fb\7\20\2\2\u00fb\u00fc\7\24\2\2\u00fc\u00fd\7.\2"+
		"\2\u00fd\u00fe\7\27\2\2\u00fe\u00ff\7\37\2\2\u00ff\u0100\7.\2\2\u0100"+
		"\u0102\7\27\2\2\u0101\u0103\7)\2\2\u0102\u0101\3\2\2\2\u0102\u0103\3\2"+
		"\2\2\u0103\u0104\3\2\2\2\u0104\u0105\7 \2\2\u0105\u0106\7,\2\2\u0106\u0107"+
		"\7(\2\2\u0107)\3\2\2\2\34-9<?KYbipt~\u0081\u008f\u00a1\u00a9\u00ad\u00b7"+
		"\u00c8\u00d1\u00d7\u00dd\u00df\u00eb\u00f1\u00f8\u0102";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}