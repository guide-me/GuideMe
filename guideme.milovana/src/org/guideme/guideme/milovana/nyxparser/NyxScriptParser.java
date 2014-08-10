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
		T__39=1, T__38=2, T__37=3, T__36=4, T__35=5, T__34=6, T__33=7, T__32=8, 
		T__31=9, T__30=10, T__29=11, T__28=12, T__27=13, T__26=14, T__25=15, T__24=16, 
		T__23=17, T__22=18, T__21=19, T__20=20, T__19=21, T__18=22, T__17=23, 
		T__16=24, T__15=25, T__14=26, T__13=27, T__12=28, T__11=29, T__10=30, 
		T__9=31, T__8=32, T__7=33, T__6=34, T__5=35, T__4=36, T__3=37, T__2=38, 
		T__1=39, T__0=40, DELAY_STYLE=41, TIME_UNIT=42, QUOTED_STRING=43, PAGE_ID=44, 
		INT=45, NEWLINE=46, WS=47;
	public static final String[] tokenNames = {
		"<INVALID>", "'action:'", "'instruc:'", "'media:pic('", "'mustnot('", 
		"'hidden:sound('", "'id:'", "'vert'", "'mult'", "'target'", "'self:'", 
		"'target:'", "'max:'", "'random('", "'range('", "'yes:'", "'cap'", "'('", 
		"'from:'", "'yn('", "'action'", "','", "'unset('", "'style:'", "'time:'", 
		"'loops:'", "'go('", "'buttons('", "'must('", "'to:'", "':'", "'horiz'", 
		"'delay('", "'text:'", "'no:'", "'page('", "'set('", "'e'", "'min:'", 
		"')'", "'prefix'", "DELAY_STYLE", "TIME_UNIT", "QUOTED_STRING", "PAGE_ID", 
		"INT", "NEWLINE", "WS"
	};
	public static final int
		RULE_guide = 0, RULE_page = 1, RULE_must_not = 2, RULE_must = 3, RULE_page_option = 4, 
		RULE_action = 5, RULE_action_group = 6, RULE_action_prefix = 7, RULE_action_set = 8, 
		RULE_action_unset = 9, RULE_text = 10, RULE_media_pic = 11, RULE_hidden_sound = 12, 
		RULE_action_buttons = 13, RULE_button = 14, RULE_action_delay = 15, RULE_time = 16, 
		RULE_action_go = 17, RULE_action_yn = 18, RULE_yes_button = 19, RULE_no_button = 20, 
		RULE_action_target = 21, RULE_range = 22;
	public static final String[] ruleNames = {
		"guide", "page", "must_not", "must", "page_option", "action", "action_group", 
		"action_prefix", "action_set", "action_unset", "text", "media_pic", "hidden_sound", 
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
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__36) | (1L << T__12) | (1L << PAGE_ID))) != 0)) {
				{
				{
				setState(46); page();
				}
				}
				setState(51);
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
			setState(67);
			switch (_input.LA(1)) {
			case T__36:
				enterOuterAlt(_localctx, 1);
				{
				setState(52); must_not();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(53); must();
				}
				break;
			case PAGE_ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(54); match(PAGE_ID);
				setState(55); match(T__5);
				setState(64);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__39) | (1L << T__38) | (1L << T__37) | (1L << T__35) | (1L << T__18) | (1L << T__7) | (1L << T__4) | (1L << T__3))) != 0)) {
					{
					setState(56); page_option();
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__19) {
						{
						{
						setState(57); match(T__19);
						setState(58); page_option();
						}
						}
						setState(63);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(66); match(T__1);
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
			setState(69); match(T__36);
			setState(70); match(T__30);
			setState(71); match(PAGE_ID);
			setState(77); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(72); match(T__19);
				setState(73); match(T__20);
				setState(74); match(INT);
				setState(75); match(T__10);
				setState(76); match(PAGE_ID);
				}
				}
				setState(79); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__19 );
			setState(81); match(T__1);
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
			setState(83); match(T__12);
			setState(84); match(T__30);
			setState(85); match(PAGE_ID);
			setState(91); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(86); match(T__19);
				setState(87); match(T__20);
				setState(88); match(INT);
				setState(89); match(T__10);
				setState(90); match(PAGE_ID);
				}
				}
				setState(93); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__19 );
			setState(95); match(T__1);
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
			setState(102);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(97); text();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(98); media_pic();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(99); hidden_sound();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(100); action_group();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(101); action();
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
		public Action_setContext action_set() {
			return getRuleContext(Action_setContext.class,0);
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
			setState(110);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(104); action_buttons();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105); action_delay();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(106); action_go();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(107); action_yn();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(108); action_set();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(109); action_unset();
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
			setState(112); action_prefix();
			setState(113);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__33) | (1L << T__32) | (1L << T__9))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(114); match(T__23);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__39) | (1L << T__38) | (1L << T__18) | (1L << T__4) | (1L << T__3))) != 0)) {
				{
				{
				setState(115); action();
				setState(117);
				_la = _input.LA(1);
				if (_la==T__19) {
					{
					setState(116); match(T__19);
					}
				}

				}
				}
				setState(123);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(124); match(T__1);
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
			setState(131);
			switch (_input.LA(1)) {
			case T__39:
				enterOuterAlt(_localctx, 1);
				{
				setState(126); match(T__39);
				}
				break;
			case T__38:
				enterOuterAlt(_localctx, 2);
				{
				setState(127); match(T__38);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 3);
				{
				setState(128); match(T__3);
				setState(129); match(INT);
				setState(130); match(T__10);
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

	public static class Action_setContext extends ParserRuleContext {
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
		public Action_setContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action_set; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).enterAction_set(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof NyxScriptListener ) ((NyxScriptListener)listener).exitAction_set(this);
		}
	}

	public final Action_setContext action_set() throws RecognitionException {
		Action_setContext _localctx = new Action_setContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_action_set);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__39) | (1L << T__38) | (1L << T__3))) != 0)) {
				{
				setState(133); action_prefix();
				}
			}

			setState(136); match(T__4);
			setState(137); match(T__20);
			setState(138); match(INT);
			setState(139); match(T__10);
			setState(140); match(PAGE_ID);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(141); match(T__19);
				setState(142); match(T__20);
				setState(143); match(INT);
				setState(144); match(T__10);
				setState(145); match(PAGE_ID);
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
		enterRule(_localctx, 18, RULE_action_unset);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__39) | (1L << T__38) | (1L << T__3))) != 0)) {
				{
				setState(153); action_prefix();
				}
			}

			setState(156); match(T__18);
			setState(157); match(T__20);
			setState(158); match(INT);
			setState(159); match(T__10);
			setState(160); match(PAGE_ID);
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__19) {
				{
				{
				setState(161); match(T__19);
				setState(162); match(T__20);
				setState(163); match(INT);
				setState(164); match(T__10);
				setState(165); match(PAGE_ID);
				}
				}
				setState(170);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(171); match(T__1);
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
		enterRule(_localctx, 20, RULE_text);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173); match(T__7);
			setState(174); match(QUOTED_STRING);
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
		enterRule(_localctx, 22, RULE_media_pic);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176); match(T__37);
			setState(177); match(T__34);
			setState(178); match(QUOTED_STRING);
			setState(179); match(T__1);
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
		enterRule(_localctx, 24, RULE_hidden_sound);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181); match(T__35);
			setState(182); match(T__34);
			setState(183); match(QUOTED_STRING);
			setState(187);
			_la = _input.LA(1);
			if (_la==T__19) {
				{
				setState(184); match(T__19);
				setState(185); match(T__15);
				setState(186); match(INT);
				}
			}

			setState(189); match(T__1);
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
		enterRule(_localctx, 26, RULE_action_buttons);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191); action_prefix();
			setState(192); match(T__13);
			setState(197); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(193); button();
				setState(195);
				_la = _input.LA(1);
				if (_la==T__19) {
					{
					setState(194); match(T__19);
					}
				}

				}
				}
				setState(199); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__31 );
			setState(201); match(T__1);
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
		enterRule(_localctx, 28, RULE_button);
		try {
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(203); match(T__31);
			setState(204); match(INT);
			}
			setState(206); match(T__10);
			setState(209);
			switch (_input.LA(1)) {
			case T__26:
				{
				setState(207); range();
				}
				break;
			case PAGE_ID:
				{
				setState(208); match(PAGE_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(211); match(T__19);
			{
			setState(212); match(T__24);
			setState(213); match(INT);
			}
			setState(215); match(T__10);
			setState(216); match(QUOTED_STRING);
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
		enterRule(_localctx, 30, RULE_action_delay);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218); action_prefix();
			setState(219); match(T__8);
			setState(220); time();
			setState(221); match(T__19);
			setState(222); action_target();
			setState(226);
			_la = _input.LA(1);
			if (_la==T__19) {
				{
				setState(223); match(T__19);
				setState(224); match(T__17);
				setState(225); match(DELAY_STYLE);
				}
			}

			setState(228); match(T__1);
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
		enterRule(_localctx, 32, RULE_time);
		int _la;
		try {
			setState(249);
			switch ( getInterpreter().adaptivePredict(_input,23,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(230); match(T__16);
				setState(231); match(T__27);
				setState(232); match(T__2);
				setState(233); match(INT);
				setState(235);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(234); match(TIME_UNIT);
					}
				}

				setState(237); match(T__19);
				setState(238); match(T__28);
				setState(239); match(INT);
				setState(241);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(240); match(TIME_UNIT);
					}
				}

				setState(243); match(T__1);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(244); match(T__16);
				setState(245); match(INT);
				setState(247);
				_la = _input.LA(1);
				if (_la==TIME_UNIT) {
					{
					setState(246); match(TIME_UNIT);
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
		enterRule(_localctx, 34, RULE_action_go);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(251); action_prefix();
			setState(252); match(T__14);
			setState(253); action_target();
			setState(254); match(T__1);
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
		enterRule(_localctx, 36, RULE_action_yn);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256); action_prefix();
			setState(257); match(T__21);
			setState(258); yes_button();
			setState(259); match(T__19);
			setState(260); no_button();
			setState(261); match(T__1);
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
		enterRule(_localctx, 38, RULE_yes_button);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(263); match(T__25);
			setState(266);
			switch (_input.LA(1)) {
			case T__26:
				{
				setState(264); range();
				}
				break;
			case PAGE_ID:
				{
				setState(265); match(PAGE_ID);
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
		enterRule(_localctx, 40, RULE_no_button);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268); match(T__6);
			setState(271);
			switch (_input.LA(1)) {
			case T__26:
				{
				setState(269); range();
				}
				break;
			case PAGE_ID:
				{
				setState(270); match(PAGE_ID);
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
		enterRule(_localctx, 42, RULE_action_target);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273); match(T__29);
			setState(276);
			switch (_input.LA(1)) {
			case T__26:
				{
				setState(274); range();
				}
				break;
			case PAGE_ID:
				{
				setState(275); match(PAGE_ID);
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
		enterRule(_localctx, 44, RULE_range);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(278); match(T__26);
			setState(279); match(T__22);
			setState(280); match(INT);
			setState(281); match(T__19);
			setState(282); match(T__11);
			setState(283); match(INT);
			setState(290);
			_la = _input.LA(1);
			if (_la==T__19) {
				{
				setState(284); match(T__19);
				setState(286);
				_la = _input.LA(1);
				if (_la==T__0) {
					{
					setState(285); match(T__0);
					}
				}

				setState(288); match(T__10);
				setState(289); match(QUOTED_STRING);
				}
			}

			setState(292); match(T__1);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\61\u0129\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\7\2\62"+
		"\n\2\f\2\16\2\65\13\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\7\3>\n\3\f\3\16\3A\13"+
		"\3\5\3C\n\3\3\3\5\3F\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\6\4P\n\4\r\4"+
		"\16\4Q\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\6\5^\n\5\r\5\16\5_\3\5"+
		"\3\5\3\6\3\6\3\6\3\6\3\6\5\6i\n\6\3\7\3\7\3\7\3\7\3\7\3\7\5\7q\n\7\3\b"+
		"\3\b\3\b\3\b\3\b\5\bx\n\b\7\bz\n\b\f\b\16\b}\13\b\3\b\3\b\3\t\3\t\3\t"+
		"\3\t\3\t\5\t\u0086\n\t\3\n\5\n\u0089\n\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\7\n\u0095\n\n\f\n\16\n\u0098\13\n\3\n\3\n\3\13\5\13\u009d\n"+
		"\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00a9\n\13"+
		"\f\13\16\13\u00ac\13\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\16\3\16\3\16\5\16\u00be\n\16\3\16\3\16\3\17\3\17\3\17\3\17"+
		"\5\17\u00c6\n\17\6\17\u00c8\n\17\r\17\16\17\u00c9\3\17\3\17\3\20\3\20"+
		"\3\20\3\20\3\20\3\20\5\20\u00d4\n\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20"+
		"\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\5\21\u00e5\n\21\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\22\5\22\u00ee\n\22\3\22\3\22\3\22\3\22\5\22\u00f4\n"+
		"\22\3\22\3\22\3\22\3\22\5\22\u00fa\n\22\5\22\u00fc\n\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25\5\25\u010d"+
		"\n\25\3\26\3\26\3\26\5\26\u0112\n\26\3\27\3\27\3\27\5\27\u0117\n\27\3"+
		"\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u0121\n\30\3\30\3\30\5\30"+
		"\u0125\n\30\3\30\3\30\3\30\2\2\31\2\4\6\b\n\f\16\20\22\24\26\30\32\34"+
		"\36 \"$&(*,.\2\3\4\2\t\n!!\u0137\2\63\3\2\2\2\4E\3\2\2\2\6G\3\2\2\2\b"+
		"U\3\2\2\2\nh\3\2\2\2\fp\3\2\2\2\16r\3\2\2\2\20\u0085\3\2\2\2\22\u0088"+
		"\3\2\2\2\24\u009c\3\2\2\2\26\u00af\3\2\2\2\30\u00b2\3\2\2\2\32\u00b7\3"+
		"\2\2\2\34\u00c1\3\2\2\2\36\u00cd\3\2\2\2 \u00dc\3\2\2\2\"\u00fb\3\2\2"+
		"\2$\u00fd\3\2\2\2&\u0102\3\2\2\2(\u0109\3\2\2\2*\u010e\3\2\2\2,\u0113"+
		"\3\2\2\2.\u0118\3\2\2\2\60\62\5\4\3\2\61\60\3\2\2\2\62\65\3\2\2\2\63\61"+
		"\3\2\2\2\63\64\3\2\2\2\64\3\3\2\2\2\65\63\3\2\2\2\66F\5\6\4\2\67F\5\b"+
		"\5\289\7.\2\29B\7%\2\2:?\5\n\6\2;<\7\27\2\2<>\5\n\6\2=;\3\2\2\2>A\3\2"+
		"\2\2?=\3\2\2\2?@\3\2\2\2@C\3\2\2\2A?\3\2\2\2B:\3\2\2\2BC\3\2\2\2CD\3\2"+
		"\2\2DF\7)\2\2E\66\3\2\2\2E\67\3\2\2\2E8\3\2\2\2F\5\3\2\2\2GH\7\6\2\2H"+
		"I\7\f\2\2IO\7.\2\2JK\7\27\2\2KL\7\26\2\2LM\7/\2\2MN\7 \2\2NP\7.\2\2OJ"+
		"\3\2\2\2PQ\3\2\2\2QO\3\2\2\2QR\3\2\2\2RS\3\2\2\2ST\7)\2\2T\7\3\2\2\2U"+
		"V\7\36\2\2VW\7\f\2\2W]\7.\2\2XY\7\27\2\2YZ\7\26\2\2Z[\7/\2\2[\\\7 \2\2"+
		"\\^\7.\2\2]X\3\2\2\2^_\3\2\2\2_]\3\2\2\2_`\3\2\2\2`a\3\2\2\2ab\7)\2\2"+
		"b\t\3\2\2\2ci\5\26\f\2di\5\30\r\2ei\5\32\16\2fi\5\16\b\2gi\5\f\7\2hc\3"+
		"\2\2\2hd\3\2\2\2he\3\2\2\2hf\3\2\2\2hg\3\2\2\2i\13\3\2\2\2jq\5\34\17\2"+
		"kq\5 \21\2lq\5$\23\2mq\5&\24\2nq\5\22\n\2oq\5\24\13\2pj\3\2\2\2pk\3\2"+
		"\2\2pl\3\2\2\2pm\3\2\2\2pn\3\2\2\2po\3\2\2\2q\r\3\2\2\2rs\5\20\t\2st\t"+
		"\2\2\2t{\7\23\2\2uw\5\f\7\2vx\7\27\2\2wv\3\2\2\2wx\3\2\2\2xz\3\2\2\2y"+
		"u\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|~\3\2\2\2}{\3\2\2\2~\177\7)\2"+
		"\2\177\17\3\2\2\2\u0080\u0086\7\3\2\2\u0081\u0086\7\4\2\2\u0082\u0083"+
		"\7\'\2\2\u0083\u0084\7/\2\2\u0084\u0086\7 \2\2\u0085\u0080\3\2\2\2\u0085"+
		"\u0081\3\2\2\2\u0085\u0082\3\2\2\2\u0086\21\3\2\2\2\u0087\u0089\5\20\t"+
		"\2\u0088\u0087\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b"+
		"\7&\2\2\u008b\u008c\7\26\2\2\u008c\u008d\7/\2\2\u008d\u008e\7 \2\2\u008e"+
		"\u0096\7.\2\2\u008f\u0090\7\27\2\2\u0090\u0091\7\26\2\2\u0091\u0092\7"+
		"/\2\2\u0092\u0093\7 \2\2\u0093\u0095\7.\2\2\u0094\u008f\3\2\2\2\u0095"+
		"\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097\u0099\3\2"+
		"\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7)\2\2\u009a\23\3\2\2\2\u009b\u009d"+
		"\5\20\t\2\u009c\u009b\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009e\3\2\2\2"+
		"\u009e\u009f\7\30\2\2\u009f\u00a0\7\26\2\2\u00a0\u00a1\7/\2\2\u00a1\u00a2"+
		"\7 \2\2\u00a2\u00aa\7.\2\2\u00a3\u00a4\7\27\2\2\u00a4\u00a5\7\26\2\2\u00a5"+
		"\u00a6\7/\2\2\u00a6\u00a7\7 \2\2\u00a7\u00a9\7.\2\2\u00a8\u00a3\3\2\2"+
		"\2\u00a9\u00ac\3\2\2\2\u00aa\u00a8\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ad"+
		"\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\7)\2\2\u00ae\25\3\2\2\2\u00af"+
		"\u00b0\7#\2\2\u00b0\u00b1\7-\2\2\u00b1\27\3\2\2\2\u00b2\u00b3\7\5\2\2"+
		"\u00b3\u00b4\7\b\2\2\u00b4\u00b5\7-\2\2\u00b5\u00b6\7)\2\2\u00b6\31\3"+
		"\2\2\2\u00b7\u00b8\7\7\2\2\u00b8\u00b9\7\b\2\2\u00b9\u00bd\7-\2\2\u00ba"+
		"\u00bb\7\27\2\2\u00bb\u00bc\7\33\2\2\u00bc\u00be\7/\2\2\u00bd\u00ba\3"+
		"\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c0\7)\2\2\u00c0"+
		"\33\3\2\2\2\u00c1\u00c2\5\20\t\2\u00c2\u00c7\7\35\2\2\u00c3\u00c5\5\36"+
		"\20\2\u00c4\u00c6\7\27\2\2\u00c5\u00c4\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6"+
		"\u00c8\3\2\2\2\u00c7\u00c3\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00c7\3\2"+
		"\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\7)\2\2\u00cc"+
		"\35\3\2\2\2\u00cd\u00ce\7\13\2\2\u00ce\u00cf\7/\2\2\u00cf\u00d0\3\2\2"+
		"\2\u00d0\u00d3\7 \2\2\u00d1\u00d4\5.\30\2\u00d2\u00d4\7.\2\2\u00d3\u00d1"+
		"\3\2\2\2\u00d3\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\u00d6\7\27\2\2"+
		"\u00d6\u00d7\7\22\2\2\u00d7\u00d8\7/\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da"+
		"\7 \2\2\u00da\u00db\7-\2\2\u00db\37\3\2\2\2\u00dc\u00dd\5\20\t\2\u00dd"+
		"\u00de\7\"\2\2\u00de\u00df\5\"\22\2\u00df\u00e0\7\27\2\2\u00e0\u00e4\5"+
		",\27\2\u00e1\u00e2\7\27\2\2\u00e2\u00e3\7\31\2\2\u00e3\u00e5\7+\2\2\u00e4"+
		"\u00e1\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\7)"+
		"\2\2\u00e7!\3\2\2\2\u00e8\u00e9\7\32\2\2\u00e9\u00ea\7\17\2\2\u00ea\u00eb"+
		"\7(\2\2\u00eb\u00ed\7/\2\2\u00ec\u00ee\7,\2\2\u00ed\u00ec\3\2\2\2\u00ed"+
		"\u00ee\3\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00f0\7\27\2\2\u00f0\u00f1\7"+
		"\16\2\2\u00f1\u00f3\7/\2\2\u00f2\u00f4\7,\2\2\u00f3\u00f2\3\2\2\2\u00f3"+
		"\u00f4\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00fc\7)\2\2\u00f6\u00f7\7\32"+
		"\2\2\u00f7\u00f9\7/\2\2\u00f8\u00fa\7,\2\2\u00f9\u00f8\3\2\2\2\u00f9\u00fa"+
		"\3\2\2\2\u00fa\u00fc\3\2\2\2\u00fb\u00e8\3\2\2\2\u00fb\u00f6\3\2\2\2\u00fc"+
		"#\3\2\2\2\u00fd\u00fe\5\20\t\2\u00fe\u00ff\7\34\2\2\u00ff\u0100\5,\27"+
		"\2\u0100\u0101\7)\2\2\u0101%\3\2\2\2\u0102\u0103\5\20\t\2\u0103\u0104"+
		"\7\25\2\2\u0104\u0105\5(\25\2\u0105\u0106\7\27\2\2\u0106\u0107\5*\26\2"+
		"\u0107\u0108\7)\2\2\u0108\'\3\2\2\2\u0109\u010c\7\21\2\2\u010a\u010d\5"+
		".\30\2\u010b\u010d\7.\2\2\u010c\u010a\3\2\2\2\u010c\u010b\3\2\2\2\u010d"+
		")\3\2\2\2\u010e\u0111\7$\2\2\u010f\u0112\5.\30\2\u0110\u0112\7.\2\2\u0111"+
		"\u010f\3\2\2\2\u0111\u0110\3\2\2\2\u0112+\3\2\2\2\u0113\u0116\7\r\2\2"+
		"\u0114\u0117\5.\30\2\u0115\u0117\7.\2\2\u0116\u0114\3\2\2\2\u0116\u0115"+
		"\3\2\2\2\u0117-\3\2\2\2\u0118\u0119\7\20\2\2\u0119\u011a\7\24\2\2\u011a"+
		"\u011b\7/\2\2\u011b\u011c\7\27\2\2\u011c\u011d\7\37\2\2\u011d\u0124\7"+
		"/\2\2\u011e\u0120\7\27\2\2\u011f\u0121\7*\2\2\u0120\u011f\3\2\2\2\u0120"+
		"\u0121\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0123\7 \2\2\u0123\u0125\7-\2"+
		"\2\u0124\u011e\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0127"+
		"\7)\2\2\u0127/\3\2\2\2\37\63?BEQ_hpw{\u0085\u0088\u0096\u009c\u00aa\u00bd"+
		"\u00c5\u00c9\u00d3\u00e4\u00ed\u00f3\u00f9\u00fb\u010c\u0111\u0116\u0120"+
		"\u0124";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}