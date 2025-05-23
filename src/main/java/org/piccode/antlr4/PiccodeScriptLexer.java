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
		AND=12, OR=13, SHL=14, SHR=15, BAND=16, BOR=17, EXCLAIM=18, PIPE=19, LBRACE=20, 
		RBRACE=21, LPAREN=22, RPAREN=23, LBRACKET=24, RBRACKET=25, COLON=26, COMMA=27, 
		SEMI=28, ARROW=29, TILDE=30, ASSIGN=31, LET=32, FUNCTION=33, WHEN=34, 
		IMPORT=35, IS=36, IF=37, ELSE=38, MODULE=39, DO=40, NUMBER=41, STRING=42, 
		DOT=43, LINE_COMMENT=44, BLOCK_COMMENT=45, ID=46, WS=47;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", "NE", 
			"AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "LBRACE", 
			"RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", "COMMA", 
			"SEMI", "ARROW", "TILDE", "ASSIGN", "LET", "FUNCTION", "WHEN", "IMPORT", 
			"IS", "IF", "ELSE", "MODULE", "DO", "NUMBER", "HEX_LITERAL", "OCT_LITERAL", 
			"BIN_LITERAL", "DECIMAL_NUMBER", "EXP", "STRING", "DOT", "LINE_COMMENT", 
			"BLOCK_COMMENT", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'=='", "'!='", "'&&'", "'||'", "'>>'", "'<<'", "'&'", "'|'", "'!'", 
			"'|>'", "'{'", "'}'", "'('", "')'", "'['", "']'", "':'", "','", "';'", 
			"'->'", "'~'", "'='", "'let'", "'function'", "'when'", "'import'", "'is'", 
			"'if'", "'else'", "'module'", "'do'", null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", 
			"NE", "AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "LBRACE", 
			"RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", "COMMA", 
			"SEMI", "ARROW", "TILDE", "ASSIGN", "LET", "FUNCTION", "WHEN", "IMPORT", 
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\61\u015f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3"+
		"\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16"+
		"\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23"+
		"\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!"+
		"\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$"+
		"\3$\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3"+
		"(\3)\3)\3)\3*\3*\3*\3*\5*\u00e6\n*\3+\3+\3+\6+\u00eb\n+\r+\16+\u00ec\3"+
		",\3,\3,\6,\u00f2\n,\r,\16,\u00f3\3-\3-\3-\6-\u00f9\n-\r-\16-\u00fa\3."+
		"\6.\u00fe\n.\r.\16.\u00ff\3.\3.\6.\u0104\n.\r.\16.\u0105\5.\u0108\n.\3"+
		".\5.\u010b\n.\3.\3.\6.\u010f\n.\r.\16.\u0110\3.\5.\u0114\n.\5.\u0116\n"+
		".\3/\3/\5/\u011a\n/\3/\6/\u011d\n/\r/\16/\u011e\3\60\3\60\3\60\3\60\7"+
		"\60\u0125\n\60\f\60\16\60\u0128\13\60\3\60\3\60\3\60\3\60\3\60\7\60\u012f"+
		"\n\60\f\60\16\60\u0132\13\60\3\60\5\60\u0135\n\60\3\61\3\61\3\62\3\62"+
		"\3\62\3\62\7\62\u013d\n\62\f\62\16\62\u0140\13\62\3\62\3\62\3\63\3\63"+
		"\3\63\3\63\7\63\u0148\n\63\f\63\16\63\u014b\13\63\3\63\3\63\3\63\3\63"+
		"\3\63\3\64\3\64\7\64\u0154\n\64\f\64\16\64\u0157\13\64\3\65\6\65\u015a"+
		"\n\65\r\65\16\65\u015b\3\65\3\65\3\u0149\2\66\3\3\5\4\7\5\t\6\13\7\r\b"+
		"\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26"+
		"+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S"+
		"+U\2W\2Y\2[\2]\2_,a-c.e/g\60i\61\3\2\21\4\2ZZzz\5\2\62;CHch\4\2QQqq\3"+
		"\2\629\4\2DDdd\3\2\62\63\3\2\62;\4\2GGgg\4\2--//\4\2$$^^\4\2))^^\4\2\f"+
		"\f\17\17\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\2\u0171\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3"+
		"\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2_\3\2\2"+
		"\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\3k\3\2\2\2\5"+
		"m\3\2\2\2\7o\3\2\2\2\tq\3\2\2\2\13s\3\2\2\2\ru\3\2\2\2\17w\3\2\2\2\21"+
		"z\3\2\2\2\23|\3\2\2\2\25\177\3\2\2\2\27\u0082\3\2\2\2\31\u0085\3\2\2\2"+
		"\33\u0088\3\2\2\2\35\u008b\3\2\2\2\37\u008e\3\2\2\2!\u0091\3\2\2\2#\u0093"+
		"\3\2\2\2%\u0095\3\2\2\2\'\u0097\3\2\2\2)\u009a\3\2\2\2+\u009c\3\2\2\2"+
		"-\u009e\3\2\2\2/\u00a0\3\2\2\2\61\u00a2\3\2\2\2\63\u00a4\3\2\2\2\65\u00a6"+
		"\3\2\2\2\67\u00a8\3\2\2\29\u00aa\3\2\2\2;\u00ac\3\2\2\2=\u00af\3\2\2\2"+
		"?\u00b1\3\2\2\2A\u00b3\3\2\2\2C\u00b7\3\2\2\2E\u00c0\3\2\2\2G\u00c5\3"+
		"\2\2\2I\u00cc\3\2\2\2K\u00cf\3\2\2\2M\u00d2\3\2\2\2O\u00d7\3\2\2\2Q\u00de"+
		"\3\2\2\2S\u00e5\3\2\2\2U\u00e7\3\2\2\2W\u00ee\3\2\2\2Y\u00f5\3\2\2\2["+
		"\u0115\3\2\2\2]\u0117\3\2\2\2_\u0134\3\2\2\2a\u0136\3\2\2\2c\u0138\3\2"+
		"\2\2e\u0143\3\2\2\2g\u0151\3\2\2\2i\u0159\3\2\2\2kl\7-\2\2l\4\3\2\2\2"+
		"mn\7/\2\2n\6\3\2\2\2op\7,\2\2p\b\3\2\2\2qr\7\61\2\2r\n\3\2\2\2st\7\'\2"+
		"\2t\f\3\2\2\2uv\7@\2\2v\16\3\2\2\2wx\7@\2\2xy\7?\2\2y\20\3\2\2\2z{\7>"+
		"\2\2{\22\3\2\2\2|}\7>\2\2}~\7?\2\2~\24\3\2\2\2\177\u0080\7?\2\2\u0080"+
		"\u0081\7?\2\2\u0081\26\3\2\2\2\u0082\u0083\7#\2\2\u0083\u0084\7?\2\2\u0084"+
		"\30\3\2\2\2\u0085\u0086\7(\2\2\u0086\u0087\7(\2\2\u0087\32\3\2\2\2\u0088"+
		"\u0089\7~\2\2\u0089\u008a\7~\2\2\u008a\34\3\2\2\2\u008b\u008c\7@\2\2\u008c"+
		"\u008d\7@\2\2\u008d\36\3\2\2\2\u008e\u008f\7>\2\2\u008f\u0090\7>\2\2\u0090"+
		" \3\2\2\2\u0091\u0092\7(\2\2\u0092\"\3\2\2\2\u0093\u0094\7~\2\2\u0094"+
		"$\3\2\2\2\u0095\u0096\7#\2\2\u0096&\3\2\2\2\u0097\u0098\7~\2\2\u0098\u0099"+
		"\7@\2\2\u0099(\3\2\2\2\u009a\u009b\7}\2\2\u009b*\3\2\2\2\u009c\u009d\7"+
		"\177\2\2\u009d,\3\2\2\2\u009e\u009f\7*\2\2\u009f.\3\2\2\2\u00a0\u00a1"+
		"\7+\2\2\u00a1\60\3\2\2\2\u00a2\u00a3\7]\2\2\u00a3\62\3\2\2\2\u00a4\u00a5"+
		"\7_\2\2\u00a5\64\3\2\2\2\u00a6\u00a7\7<\2\2\u00a7\66\3\2\2\2\u00a8\u00a9"+
		"\7.\2\2\u00a98\3\2\2\2\u00aa\u00ab\7=\2\2\u00ab:\3\2\2\2\u00ac\u00ad\7"+
		"/\2\2\u00ad\u00ae\7@\2\2\u00ae<\3\2\2\2\u00af\u00b0\7\u0080\2\2\u00b0"+
		">\3\2\2\2\u00b1\u00b2\7?\2\2\u00b2@\3\2\2\2\u00b3\u00b4\7n\2\2\u00b4\u00b5"+
		"\7g\2\2\u00b5\u00b6\7v\2\2\u00b6B\3\2\2\2\u00b7\u00b8\7h\2\2\u00b8\u00b9"+
		"\7w\2\2\u00b9\u00ba\7p\2\2\u00ba\u00bb\7e\2\2\u00bb\u00bc\7v\2\2\u00bc"+
		"\u00bd\7k\2\2\u00bd\u00be\7q\2\2\u00be\u00bf\7p\2\2\u00bfD\3\2\2\2\u00c0"+
		"\u00c1\7y\2\2\u00c1\u00c2\7j\2\2\u00c2\u00c3\7g\2\2\u00c3\u00c4\7p\2\2"+
		"\u00c4F\3\2\2\2\u00c5\u00c6\7k\2\2\u00c6\u00c7\7o\2\2\u00c7\u00c8\7r\2"+
		"\2\u00c8\u00c9\7q\2\2\u00c9\u00ca\7t\2\2\u00ca\u00cb\7v\2\2\u00cbH\3\2"+
		"\2\2\u00cc\u00cd\7k\2\2\u00cd\u00ce\7u\2\2\u00ceJ\3\2\2\2\u00cf\u00d0"+
		"\7k\2\2\u00d0\u00d1\7h\2\2\u00d1L\3\2\2\2\u00d2\u00d3\7g\2\2\u00d3\u00d4"+
		"\7n\2\2\u00d4\u00d5\7u\2\2\u00d5\u00d6\7g\2\2\u00d6N\3\2\2\2\u00d7\u00d8"+
		"\7o\2\2\u00d8\u00d9\7q\2\2\u00d9\u00da\7f\2\2\u00da\u00db\7w\2\2\u00db"+
		"\u00dc\7n\2\2\u00dc\u00dd\7g\2\2\u00ddP\3\2\2\2\u00de\u00df\7f\2\2\u00df"+
		"\u00e0\7q\2\2\u00e0R\3\2\2\2\u00e1\u00e6\5U+\2\u00e2\u00e6\5W,\2\u00e3"+
		"\u00e6\5Y-\2\u00e4\u00e6\5[.\2\u00e5\u00e1\3\2\2\2\u00e5\u00e2\3\2\2\2"+
		"\u00e5\u00e3\3\2\2\2\u00e5\u00e4\3\2\2\2\u00e6T\3\2\2\2\u00e7\u00e8\7"+
		"\62\2\2\u00e8\u00ea\t\2\2\2\u00e9\u00eb\t\3\2\2\u00ea\u00e9\3\2\2\2\u00eb"+
		"\u00ec\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ec\u00ed\3\2\2\2\u00edV\3\2\2\2"+
		"\u00ee\u00ef\7\62\2\2\u00ef\u00f1\t\4\2\2\u00f0\u00f2\t\5\2\2\u00f1\u00f0"+
		"\3\2\2\2\u00f2\u00f3\3\2\2\2\u00f3\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4"+
		"X\3\2\2\2\u00f5\u00f6\7\62\2\2\u00f6\u00f8\t\6\2\2\u00f7\u00f9\t\7\2\2"+
		"\u00f8\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb"+
		"\3\2\2\2\u00fbZ\3\2\2\2\u00fc\u00fe\t\b\2\2\u00fd\u00fc\3\2\2\2\u00fe"+
		"\u00ff\3\2\2\2\u00ff\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0107\3\2"+
		"\2\2\u0101\u0103\7\60\2\2\u0102\u0104\t\b\2\2\u0103\u0102\3\2\2\2\u0104"+
		"\u0105\3\2\2\2\u0105\u0103\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0108\3\2"+
		"\2\2\u0107\u0101\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010a\3\2\2\2\u0109"+
		"\u010b\5]/\2\u010a\u0109\3\2\2\2\u010a\u010b\3\2\2\2\u010b\u0116\3\2\2"+
		"\2\u010c\u010e\7\60\2\2\u010d\u010f\t\b\2\2\u010e\u010d\3\2\2\2\u010f"+
		"\u0110\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111\u0113\3\2"+
		"\2\2\u0112\u0114\5]/\2\u0113\u0112\3\2\2\2\u0113\u0114\3\2\2\2\u0114\u0116"+
		"\3\2\2\2\u0115\u00fd\3\2\2\2\u0115\u010c\3\2\2\2\u0116\\\3\2\2\2\u0117"+
		"\u0119\t\t\2\2\u0118\u011a\t\n\2\2\u0119\u0118\3\2\2\2\u0119\u011a\3\2"+
		"\2\2\u011a\u011c\3\2\2\2\u011b\u011d\t\b\2\2\u011c\u011b\3\2\2\2\u011d"+
		"\u011e\3\2\2\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f^\3\2\2\2"+
		"\u0120\u0126\7$\2\2\u0121\u0125\n\13\2\2\u0122\u0123\7^\2\2\u0123\u0125"+
		"\13\2\2\2\u0124\u0121\3\2\2\2\u0124\u0122\3\2\2\2\u0125\u0128\3\2\2\2"+
		"\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0129\3\2\2\2\u0128\u0126"+
		"\3\2\2\2\u0129\u0135\7$\2\2\u012a\u0130\7)\2\2\u012b\u012f\n\f\2\2\u012c"+
		"\u012d\7^\2\2\u012d\u012f\13\2\2\2\u012e\u012b\3\2\2\2\u012e\u012c\3\2"+
		"\2\2\u012f\u0132\3\2\2\2\u0130\u012e\3\2\2\2\u0130\u0131\3\2\2\2\u0131"+
		"\u0133\3\2\2\2\u0132\u0130\3\2\2\2\u0133\u0135\7)\2\2\u0134\u0120\3\2"+
		"\2\2\u0134\u012a\3\2\2\2\u0135`\3\2\2\2\u0136\u0137\7\60\2\2\u0137b\3"+
		"\2\2\2\u0138\u0139\7\61\2\2\u0139\u013a\7\61\2\2\u013a\u013e\3\2\2\2\u013b"+
		"\u013d\n\r\2\2\u013c\u013b\3\2\2\2\u013d\u0140\3\2\2\2\u013e\u013c\3\2"+
		"\2\2\u013e\u013f\3\2\2\2\u013f\u0141\3\2\2\2\u0140\u013e\3\2\2\2\u0141"+
		"\u0142\b\62\2\2\u0142d\3\2\2\2\u0143\u0144\7\61\2\2\u0144\u0145\7,\2\2"+
		"\u0145\u0149\3\2\2\2\u0146\u0148\13\2\2\2\u0147\u0146\3\2\2\2\u0148\u014b"+
		"\3\2\2\2\u0149\u014a\3\2\2\2\u0149\u0147\3\2\2\2\u014a\u014c\3\2\2\2\u014b"+
		"\u0149\3\2\2\2\u014c\u014d\7,\2\2\u014d\u014e\7\61\2\2\u014e\u014f\3\2"+
		"\2\2\u014f\u0150\b\63\2\2\u0150f\3\2\2\2\u0151\u0155\t\16\2\2\u0152\u0154"+
		"\t\17\2\2\u0153\u0152\3\2\2\2\u0154\u0157\3\2\2\2\u0155\u0153\3\2\2\2"+
		"\u0155\u0156\3\2\2\2\u0156h\3\2\2\2\u0157\u0155\3\2\2\2\u0158\u015a\t"+
		"\20\2\2\u0159\u0158\3\2\2\2\u015a\u015b\3\2\2\2\u015b\u0159\3\2\2\2\u015b"+
		"\u015c\3\2\2\2\u015c\u015d\3\2\2\2\u015d\u015e\b\65\2\2\u015ej\3\2\2\2"+
		"\31\2\u00e5\u00ec\u00f3\u00fa\u00ff\u0105\u0107\u010a\u0110\u0113\u0115"+
		"\u0119\u011e\u0124\u0126\u012e\u0130\u0134\u013e\u0149\u0155\u015b\3\2"+
		"\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}