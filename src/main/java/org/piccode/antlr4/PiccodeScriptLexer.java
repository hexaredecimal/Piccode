// Generated from PiccodeScript.g4 by ANTLR 4.9.3

	package org.piccode.antlr4;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PiccodeScriptLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ADD=1, SUB=2, MUL=3, DIV=4, MOD=5, GT=6, GE=7, LT=8, LE=9, EQ=10, NE=11, 
		AND=12, OR=13, SHL=14, SHR=15, BAND=16, BOR=17, EXCLAIM=18, PIPE=19, CC=20, 
		LBRACE=21, RBRACE=22, LPAREN=23, RPAREN=24, LBRACKET=25, RBRACKET=26, 
		COLON=27, COMMA=28, SEMI=29, ARROW=30, TILDE=31, ASSIGN=32, DASSIGN=33, 
		WHEN=34, IMPORT=35, IS=36, IF=37, ELSE=38, MODULE=39, DO=40, NUMBER=41, 
		STRING=42, DOT=43, LINE_COMMENT=44, BLOCK_COMMENT=45, ID=46, WS=47;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", "NE", 
			"AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "CC", "LBRACE", 
			"RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", "COMMA", 
			"SEMI", "ARROW", "TILDE", "ASSIGN", "DASSIGN", "WHEN", "IMPORT", "IS", 
			"IF", "ELSE", "MODULE", "DO", "NUMBER", "HEX_LITERAL", "OCT_LITERAL", 
			"BIN_LITERAL", "DECIMAL_NUMBER", "EXP", "STRING", "DOT", "LINE_COMMENT", 
			"BLOCK_COMMENT", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'=='", "'!='", "'&&'", "'||'", "'>>'", "'<<'", "'&'", "'|'", "'!'", 
			"'|>'", "'::'", "'{'", "'}'", "'('", "')'", "'['", "']'", "':'", "','", 
			"';'", "'->'", "'~'", "'='", "':='", "'when'", "'import'", "'is'", "'if'", 
			"'else'", "'module'", "'do'", null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", 
			"NE", "AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "CC", 
			"LBRACE", "RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", 
			"COMMA", "SEMI", "ARROW", "TILDE", "ASSIGN", "DASSIGN", "WHEN", "IMPORT", 
			"IS", "IF", "ELSE", "MODULE", "DO", "NUMBER", "STRING", "DOT", "LINE_COMMENT", 
			"BLOCK_COMMENT", "ID", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public PiccodeScriptLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PiccodeScript.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\61\u0158\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3"+
		" \3!\3!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3&\3"+
		"&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3*\3*\3*\3*\5*"+
		"\u00df\n*\3+\3+\3+\6+\u00e4\n+\r+\16+\u00e5\3,\3,\3,\6,\u00eb\n,\r,\16"+
		",\u00ec\3-\3-\3-\6-\u00f2\n-\r-\16-\u00f3\3.\6.\u00f7\n.\r.\16.\u00f8"+
		"\3.\3.\6.\u00fd\n.\r.\16.\u00fe\5.\u0101\n.\3.\5.\u0104\n.\3.\3.\6.\u0108"+
		"\n.\r.\16.\u0109\3.\5.\u010d\n.\5.\u010f\n.\3/\3/\5/\u0113\n/\3/\6/\u0116"+
		"\n/\r/\16/\u0117\3\60\3\60\3\60\3\60\7\60\u011e\n\60\f\60\16\60\u0121"+
		"\13\60\3\60\3\60\3\60\3\60\3\60\7\60\u0128\n\60\f\60\16\60\u012b\13\60"+
		"\3\60\5\60\u012e\n\60\3\61\3\61\3\62\3\62\3\62\3\62\7\62\u0136\n\62\f"+
		"\62\16\62\u0139\13\62\3\62\3\62\3\63\3\63\3\63\3\63\7\63\u0141\n\63\f"+
		"\63\16\63\u0144\13\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\7\64\u014d\n"+
		"\64\f\64\16\64\u0150\13\64\3\65\6\65\u0153\n\65\r\65\16\65\u0154\3\65"+
		"\3\65\3\u0142\2\66\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33"+
		"\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U\2W\2Y\2[\2]\2_,a-c.e/"+
		"g\60i\61\3\2\21\4\2ZZzz\5\2\62;CHch\4\2QQqq\3\2\629\4\2DDdd\3\2\62\63"+
		"\3\2\62;\4\2GGgg\4\2--//\4\2$$^^\4\2))^^\4\2\f\f\17\17\5\2C\\aac|\6\2"+
		"\62;C\\aac|\5\2\13\f\17\17\"\"\2\u016a\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2"+
		"\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2"+
		"\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3"+
		"\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3"+
		"\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65"+
		"\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3"+
		"\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2"+
		"\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2"+
		"e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\3k\3\2\2\2\5m\3\2\2\2\7o\3\2\2\2\tq\3"+
		"\2\2\2\13s\3\2\2\2\ru\3\2\2\2\17w\3\2\2\2\21z\3\2\2\2\23|\3\2\2\2\25\177"+
		"\3\2\2\2\27\u0082\3\2\2\2\31\u0085\3\2\2\2\33\u0088\3\2\2\2\35\u008b\3"+
		"\2\2\2\37\u008e\3\2\2\2!\u0091\3\2\2\2#\u0093\3\2\2\2%\u0095\3\2\2\2\'"+
		"\u0097\3\2\2\2)\u009a\3\2\2\2+\u009d\3\2\2\2-\u009f\3\2\2\2/\u00a1\3\2"+
		"\2\2\61\u00a3\3\2\2\2\63\u00a5\3\2\2\2\65\u00a7\3\2\2\2\67\u00a9\3\2\2"+
		"\29\u00ab\3\2\2\2;\u00ad\3\2\2\2=\u00af\3\2\2\2?\u00b2\3\2\2\2A\u00b4"+
		"\3\2\2\2C\u00b6\3\2\2\2E\u00b9\3\2\2\2G\u00be\3\2\2\2I\u00c5\3\2\2\2K"+
		"\u00c8\3\2\2\2M\u00cb\3\2\2\2O\u00d0\3\2\2\2Q\u00d7\3\2\2\2S\u00de\3\2"+
		"\2\2U\u00e0\3\2\2\2W\u00e7\3\2\2\2Y\u00ee\3\2\2\2[\u010e\3\2\2\2]\u0110"+
		"\3\2\2\2_\u012d\3\2\2\2a\u012f\3\2\2\2c\u0131\3\2\2\2e\u013c\3\2\2\2g"+
		"\u014a\3\2\2\2i\u0152\3\2\2\2kl\7-\2\2l\4\3\2\2\2mn\7/\2\2n\6\3\2\2\2"+
		"op\7,\2\2p\b\3\2\2\2qr\7\61\2\2r\n\3\2\2\2st\7\'\2\2t\f\3\2\2\2uv\7@\2"+
		"\2v\16\3\2\2\2wx\7@\2\2xy\7?\2\2y\20\3\2\2\2z{\7>\2\2{\22\3\2\2\2|}\7"+
		">\2\2}~\7?\2\2~\24\3\2\2\2\177\u0080\7?\2\2\u0080\u0081\7?\2\2\u0081\26"+
		"\3\2\2\2\u0082\u0083\7#\2\2\u0083\u0084\7?\2\2\u0084\30\3\2\2\2\u0085"+
		"\u0086\7(\2\2\u0086\u0087\7(\2\2\u0087\32\3\2\2\2\u0088\u0089\7~\2\2\u0089"+
		"\u008a\7~\2\2\u008a\34\3\2\2\2\u008b\u008c\7@\2\2\u008c\u008d\7@\2\2\u008d"+
		"\36\3\2\2\2\u008e\u008f\7>\2\2\u008f\u0090\7>\2\2\u0090 \3\2\2\2\u0091"+
		"\u0092\7(\2\2\u0092\"\3\2\2\2\u0093\u0094\7~\2\2\u0094$\3\2\2\2\u0095"+
		"\u0096\7#\2\2\u0096&\3\2\2\2\u0097\u0098\7~\2\2\u0098\u0099\7@\2\2\u0099"+
		"(\3\2\2\2\u009a\u009b\7<\2\2\u009b\u009c\7<\2\2\u009c*\3\2\2\2\u009d\u009e"+
		"\7}\2\2\u009e,\3\2\2\2\u009f\u00a0\7\177\2\2\u00a0.\3\2\2\2\u00a1\u00a2"+
		"\7*\2\2\u00a2\60\3\2\2\2\u00a3\u00a4\7+\2\2\u00a4\62\3\2\2\2\u00a5\u00a6"+
		"\7]\2\2\u00a6\64\3\2\2\2\u00a7\u00a8\7_\2\2\u00a8\66\3\2\2\2\u00a9\u00aa"+
		"\7<\2\2\u00aa8\3\2\2\2\u00ab\u00ac\7.\2\2\u00ac:\3\2\2\2\u00ad\u00ae\7"+
		"=\2\2\u00ae<\3\2\2\2\u00af\u00b0\7/\2\2\u00b0\u00b1\7@\2\2\u00b1>\3\2"+
		"\2\2\u00b2\u00b3\7\u0080\2\2\u00b3@\3\2\2\2\u00b4\u00b5\7?\2\2\u00b5B"+
		"\3\2\2\2\u00b6\u00b7\7<\2\2\u00b7\u00b8\7?\2\2\u00b8D\3\2\2\2\u00b9\u00ba"+
		"\7y\2\2\u00ba\u00bb\7j\2\2\u00bb\u00bc\7g\2\2\u00bc\u00bd\7p\2\2\u00bd"+
		"F\3\2\2\2\u00be\u00bf\7k\2\2\u00bf\u00c0\7o\2\2\u00c0\u00c1\7r\2\2\u00c1"+
		"\u00c2\7q\2\2\u00c2\u00c3\7t\2\2\u00c3\u00c4\7v\2\2\u00c4H\3\2\2\2\u00c5"+
		"\u00c6\7k\2\2\u00c6\u00c7\7u\2\2\u00c7J\3\2\2\2\u00c8\u00c9\7k\2\2\u00c9"+
		"\u00ca\7h\2\2\u00caL\3\2\2\2\u00cb\u00cc\7g\2\2\u00cc\u00cd\7n\2\2\u00cd"+
		"\u00ce\7u\2\2\u00ce\u00cf\7g\2\2\u00cfN\3\2\2\2\u00d0\u00d1\7o\2\2\u00d1"+
		"\u00d2\7q\2\2\u00d2\u00d3\7f\2\2\u00d3\u00d4\7w\2\2\u00d4\u00d5\7n\2\2"+
		"\u00d5\u00d6\7g\2\2\u00d6P\3\2\2\2\u00d7\u00d8\7f\2\2\u00d8\u00d9\7q\2"+
		"\2\u00d9R\3\2\2\2\u00da\u00df\5U+\2\u00db\u00df\5W,\2\u00dc\u00df\5Y-"+
		"\2\u00dd\u00df\5[.\2\u00de\u00da\3\2\2\2\u00de\u00db\3\2\2\2\u00de\u00dc"+
		"\3\2\2\2\u00de\u00dd\3\2\2\2\u00dfT\3\2\2\2\u00e0\u00e1\7\62\2\2\u00e1"+
		"\u00e3\t\2\2\2\u00e2\u00e4\t\3\2\2\u00e3\u00e2\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6V\3\2\2\2\u00e7\u00e8"+
		"\7\62\2\2\u00e8\u00ea\t\4\2\2\u00e9\u00eb\t\5\2\2\u00ea\u00e9\3\2\2\2"+
		"\u00eb\u00ec\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00edX\3"+
		"\2\2\2\u00ee\u00ef\7\62\2\2\u00ef\u00f1\t\6\2\2\u00f0\u00f2\t\7\2\2\u00f1"+
		"\u00f0\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2"+
		"\2\2\u00f4Z\3\2\2\2\u00f5\u00f7\t\b\2\2\u00f6\u00f5\3\2\2\2\u00f7\u00f8"+
		"\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u0100\3\2\2\2\u00fa"+
		"\u00fc\7\60\2\2\u00fb\u00fd\t\b\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00fe\3"+
		"\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0101\3\2\2\2\u0100"+
		"\u00fa\3\2\2\2\u0100\u0101\3\2\2\2\u0101\u0103\3\2\2\2\u0102\u0104\5]"+
		"/\2\u0103\u0102\3\2\2\2\u0103\u0104\3\2\2\2\u0104\u010f\3\2\2\2\u0105"+
		"\u0107\7\60\2\2\u0106\u0108\t\b\2\2\u0107\u0106\3\2\2\2\u0108\u0109\3"+
		"\2\2\2\u0109\u0107\3\2\2\2\u0109\u010a\3\2\2\2\u010a\u010c\3\2\2\2\u010b"+
		"\u010d\5]/\2\u010c\u010b\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010f\3\2\2"+
		"\2\u010e\u00f6\3\2\2\2\u010e\u0105\3\2\2\2\u010f\\\3\2\2\2\u0110\u0112"+
		"\t\t\2\2\u0111\u0113\t\n\2\2\u0112\u0111\3\2\2\2\u0112\u0113\3\2\2\2\u0113"+
		"\u0115\3\2\2\2\u0114\u0116\t\b\2\2\u0115\u0114\3\2\2\2\u0116\u0117\3\2"+
		"\2\2\u0117\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118^\3\2\2\2\u0119\u011f"+
		"\7$\2\2\u011a\u011e\n\13\2\2\u011b\u011c\7^\2\2\u011c\u011e\13\2\2\2\u011d"+
		"\u011a\3\2\2\2\u011d\u011b\3\2\2\2\u011e\u0121\3\2\2\2\u011f\u011d\3\2"+
		"\2\2\u011f\u0120\3\2\2\2\u0120\u0122\3\2\2\2\u0121\u011f\3\2\2\2\u0122"+
		"\u012e\7$\2\2\u0123\u0129\7)\2\2\u0124\u0128\n\f\2\2\u0125\u0126\7^\2"+
		"\2\u0126\u0128\13\2\2\2\u0127\u0124\3\2\2\2\u0127\u0125\3\2\2\2\u0128"+
		"\u012b\3\2\2\2\u0129\u0127\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012c\3\2"+
		"\2\2\u012b\u0129\3\2\2\2\u012c\u012e\7)\2\2\u012d\u0119\3\2\2\2\u012d"+
		"\u0123\3\2\2\2\u012e`\3\2\2\2\u012f\u0130\7\60\2\2\u0130b\3\2\2\2\u0131"+
		"\u0132\7\61\2\2\u0132\u0133\7\61\2\2\u0133\u0137\3\2\2\2\u0134\u0136\n"+
		"\r\2\2\u0135\u0134\3\2\2\2\u0136\u0139\3\2\2\2\u0137\u0135\3\2\2\2\u0137"+
		"\u0138\3\2\2\2\u0138\u013a\3\2\2\2\u0139\u0137\3\2\2\2\u013a\u013b\b\62"+
		"\2\2\u013bd\3\2\2\2\u013c\u013d\7\61\2\2\u013d\u013e\7,\2\2\u013e\u0142"+
		"\3\2\2\2\u013f\u0141\13\2\2\2\u0140\u013f\3\2\2\2\u0141\u0144\3\2\2\2"+
		"\u0142\u0143\3\2\2\2\u0142\u0140\3\2\2\2\u0143\u0145\3\2\2\2\u0144\u0142"+
		"\3\2\2\2\u0145\u0146\7,\2\2\u0146\u0147\7\61\2\2\u0147\u0148\3\2\2\2\u0148"+
		"\u0149\b\63\2\2\u0149f\3\2\2\2\u014a\u014e\t\16\2\2\u014b\u014d\t\17\2"+
		"\2\u014c\u014b\3\2\2\2\u014d\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014e\u014f"+
		"\3\2\2\2\u014fh\3\2\2\2\u0150\u014e\3\2\2\2\u0151\u0153\t\20\2\2\u0152"+
		"\u0151\3\2\2\2\u0153\u0154\3\2\2\2\u0154\u0152\3\2\2\2\u0154\u0155\3\2"+
		"\2\2\u0155\u0156\3\2\2\2\u0156\u0157\b\65\2\2\u0157j\3\2\2\2\31\2\u00de"+
		"\u00e5\u00ec\u00f3\u00f8\u00fe\u0100\u0103\u0109\u010c\u010e\u0112\u0117"+
		"\u011d\u011f\u0127\u0129\u012d\u0137\u0142\u014e\u0154\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}