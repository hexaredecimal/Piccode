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
		COLON=27, COMMA=28, SEMI=29, ARROW=30, TILDE=31, HASH=32, ASSIGN=33, DASSIGN=34, 
		WHEN=35, IMPORT=36, IS=37, IF=38, ELSE=39, MODULE=40, DO=41, USE=42, RETURN_TOK=43, 
		CATCH_TOK=44, LET=45, IN=46, NUMBER=47, STRING=48, DOT=49, LINE_COMMENT=50, 
		BLOCK_COMMENT=51, ID=52, WS=53;
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
			"SEMI", "ARROW", "TILDE", "HASH", "ASSIGN", "DASSIGN", "WHEN", "IMPORT", 
			"IS", "IF", "ELSE", "MODULE", "DO", "USE", "RETURN_TOK", "CATCH_TOK", 
			"LET", "IN", "NUMBER", "HEX_LITERAL", "OCT_LITERAL", "BIN_LITERAL", "DECIMAL_NUMBER", 
			"EXP", "STRING", "DOT", "LINE_COMMENT", "BLOCK_COMMENT", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'=='", "'!='", "'&&'", "'||'", "'>>'", "'<<'", "'&'", "'|'", "'!'", 
			"'|>'", "'::'", "'{'", "'}'", "'('", "')'", "'['", "']'", "':'", "','", 
			"';'", "'->'", "'~'", "'#'", "'='", "':='", "'when'", "'import'", "'is'", 
			"'if'", "'else'", "'module'", "'do'", "'use'", "'return'", "'catch'", 
			"'let'", "'in'", null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", 
			"NE", "AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "CC", 
			"LBRACE", "RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", 
			"COMMA", "SEMI", "ARROW", "TILDE", "HASH", "ASSIGN", "DASSIGN", "WHEN", 
			"IMPORT", "IS", "IF", "ELSE", "MODULE", "DO", "USE", "RETURN_TOK", "CATCH_TOK", 
			"LET", "IN", "NUMBER", "STRING", "DOT", "LINE_COMMENT", "BLOCK_COMMENT", 
			"ID", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\67\u017e\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\3\2\3\2\3"+
		"\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n"+
		"\3\13\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\20\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34"+
		"\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3#"+
		"\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3(\3(\3(\3("+
		"\3(\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3,\3-"+
		"\3-\3-\3-\3-\3-\3.\3.\3.\3.\3/\3/\3/\3\60\3\60\3\60\3\60\5\60\u0105\n"+
		"\60\3\61\3\61\3\61\6\61\u010a\n\61\r\61\16\61\u010b\3\62\3\62\3\62\6\62"+
		"\u0111\n\62\r\62\16\62\u0112\3\63\3\63\3\63\6\63\u0118\n\63\r\63\16\63"+
		"\u0119\3\64\6\64\u011d\n\64\r\64\16\64\u011e\3\64\3\64\6\64\u0123\n\64"+
		"\r\64\16\64\u0124\5\64\u0127\n\64\3\64\5\64\u012a\n\64\3\64\3\64\6\64"+
		"\u012e\n\64\r\64\16\64\u012f\3\64\5\64\u0133\n\64\5\64\u0135\n\64\3\65"+
		"\3\65\5\65\u0139\n\65\3\65\6\65\u013c\n\65\r\65\16\65\u013d\3\66\3\66"+
		"\3\66\3\66\7\66\u0144\n\66\f\66\16\66\u0147\13\66\3\66\3\66\3\66\3\66"+
		"\3\66\7\66\u014e\n\66\f\66\16\66\u0151\13\66\3\66\5\66\u0154\n\66\3\67"+
		"\3\67\38\38\38\38\78\u015c\n8\f8\168\u015f\138\38\38\39\39\39\39\79\u0167"+
		"\n9\f9\169\u016a\139\39\39\39\39\39\3:\3:\7:\u0173\n:\f:\16:\u0176\13"+
		":\3;\6;\u0179\n;\r;\16;\u017a\3;\3;\3\u0168\2<\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25"+
		")\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O"+
		")Q*S+U,W-Y.[/]\60_\61a\2c\2e\2g\2i\2k\62m\63o\64q\65s\66u\67\3\2\21\4"+
		"\2ZZzz\5\2\62;CHch\4\2QQqq\3\2\629\4\2DDdd\3\2\62\63\3\2\62;\4\2GGgg\4"+
		"\2--//\4\2$$^^\4\2))^^\4\2\f\f\17\17\5\2C\\aac|\6\2\62;C\\aac|\5\2\13"+
		"\f\17\17\"\"\2\u0190\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2"+
		"\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3"+
		"\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2"+
		"\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2"+
		"\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2"+
		"\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2"+
		"\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q"+
		"\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2"+
		"\2\2\2_\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2"+
		"\2u\3\2\2\2\3w\3\2\2\2\5y\3\2\2\2\7{\3\2\2\2\t}\3\2\2\2\13\177\3\2\2\2"+
		"\r\u0081\3\2\2\2\17\u0083\3\2\2\2\21\u0086\3\2\2\2\23\u0088\3\2\2\2\25"+
		"\u008b\3\2\2\2\27\u008e\3\2\2\2\31\u0091\3\2\2\2\33\u0094\3\2\2\2\35\u0097"+
		"\3\2\2\2\37\u009a\3\2\2\2!\u009d\3\2\2\2#\u009f\3\2\2\2%\u00a1\3\2\2\2"+
		"\'\u00a3\3\2\2\2)\u00a6\3\2\2\2+\u00a9\3\2\2\2-\u00ab\3\2\2\2/\u00ad\3"+
		"\2\2\2\61\u00af\3\2\2\2\63\u00b1\3\2\2\2\65\u00b3\3\2\2\2\67\u00b5\3\2"+
		"\2\29\u00b7\3\2\2\2;\u00b9\3\2\2\2=\u00bb\3\2\2\2?\u00be\3\2\2\2A\u00c0"+
		"\3\2\2\2C\u00c2\3\2\2\2E\u00c4\3\2\2\2G\u00c7\3\2\2\2I\u00cc\3\2\2\2K"+
		"\u00d3\3\2\2\2M\u00d6\3\2\2\2O\u00d9\3\2\2\2Q\u00de\3\2\2\2S\u00e5\3\2"+
		"\2\2U\u00e8\3\2\2\2W\u00ec\3\2\2\2Y\u00f3\3\2\2\2[\u00f9\3\2\2\2]\u00fd"+
		"\3\2\2\2_\u0104\3\2\2\2a\u0106\3\2\2\2c\u010d\3\2\2\2e\u0114\3\2\2\2g"+
		"\u0134\3\2\2\2i\u0136\3\2\2\2k\u0153\3\2\2\2m\u0155\3\2\2\2o\u0157\3\2"+
		"\2\2q\u0162\3\2\2\2s\u0170\3\2\2\2u\u0178\3\2\2\2wx\7-\2\2x\4\3\2\2\2"+
		"yz\7/\2\2z\6\3\2\2\2{|\7,\2\2|\b\3\2\2\2}~\7\61\2\2~\n\3\2\2\2\177\u0080"+
		"\7\'\2\2\u0080\f\3\2\2\2\u0081\u0082\7@\2\2\u0082\16\3\2\2\2\u0083\u0084"+
		"\7@\2\2\u0084\u0085\7?\2\2\u0085\20\3\2\2\2\u0086\u0087\7>\2\2\u0087\22"+
		"\3\2\2\2\u0088\u0089\7>\2\2\u0089\u008a\7?\2\2\u008a\24\3\2\2\2\u008b"+
		"\u008c\7?\2\2\u008c\u008d\7?\2\2\u008d\26\3\2\2\2\u008e\u008f\7#\2\2\u008f"+
		"\u0090\7?\2\2\u0090\30\3\2\2\2\u0091\u0092\7(\2\2\u0092\u0093\7(\2\2\u0093"+
		"\32\3\2\2\2\u0094\u0095\7~\2\2\u0095\u0096\7~\2\2\u0096\34\3\2\2\2\u0097"+
		"\u0098\7@\2\2\u0098\u0099\7@\2\2\u0099\36\3\2\2\2\u009a\u009b\7>\2\2\u009b"+
		"\u009c\7>\2\2\u009c \3\2\2\2\u009d\u009e\7(\2\2\u009e\"\3\2\2\2\u009f"+
		"\u00a0\7~\2\2\u00a0$\3\2\2\2\u00a1\u00a2\7#\2\2\u00a2&\3\2\2\2\u00a3\u00a4"+
		"\7~\2\2\u00a4\u00a5\7@\2\2\u00a5(\3\2\2\2\u00a6\u00a7\7<\2\2\u00a7\u00a8"+
		"\7<\2\2\u00a8*\3\2\2\2\u00a9\u00aa\7}\2\2\u00aa,\3\2\2\2\u00ab\u00ac\7"+
		"\177\2\2\u00ac.\3\2\2\2\u00ad\u00ae\7*\2\2\u00ae\60\3\2\2\2\u00af\u00b0"+
		"\7+\2\2\u00b0\62\3\2\2\2\u00b1\u00b2\7]\2\2\u00b2\64\3\2\2\2\u00b3\u00b4"+
		"\7_\2\2\u00b4\66\3\2\2\2\u00b5\u00b6\7<\2\2\u00b68\3\2\2\2\u00b7\u00b8"+
		"\7.\2\2\u00b8:\3\2\2\2\u00b9\u00ba\7=\2\2\u00ba<\3\2\2\2\u00bb\u00bc\7"+
		"/\2\2\u00bc\u00bd\7@\2\2\u00bd>\3\2\2\2\u00be\u00bf\7\u0080\2\2\u00bf"+
		"@\3\2\2\2\u00c0\u00c1\7%\2\2\u00c1B\3\2\2\2\u00c2\u00c3\7?\2\2\u00c3D"+
		"\3\2\2\2\u00c4\u00c5\7<\2\2\u00c5\u00c6\7?\2\2\u00c6F\3\2\2\2\u00c7\u00c8"+
		"\7y\2\2\u00c8\u00c9\7j\2\2\u00c9\u00ca\7g\2\2\u00ca\u00cb\7p\2\2\u00cb"+
		"H\3\2\2\2\u00cc\u00cd\7k\2\2\u00cd\u00ce\7o\2\2\u00ce\u00cf\7r\2\2\u00cf"+
		"\u00d0\7q\2\2\u00d0\u00d1\7t\2\2\u00d1\u00d2\7v\2\2\u00d2J\3\2\2\2\u00d3"+
		"\u00d4\7k\2\2\u00d4\u00d5\7u\2\2\u00d5L\3\2\2\2\u00d6\u00d7\7k\2\2\u00d7"+
		"\u00d8\7h\2\2\u00d8N\3\2\2\2\u00d9\u00da\7g\2\2\u00da\u00db\7n\2\2\u00db"+
		"\u00dc\7u\2\2\u00dc\u00dd\7g\2\2\u00ddP\3\2\2\2\u00de\u00df\7o\2\2\u00df"+
		"\u00e0\7q\2\2\u00e0\u00e1\7f\2\2\u00e1\u00e2\7w\2\2\u00e2\u00e3\7n\2\2"+
		"\u00e3\u00e4\7g\2\2\u00e4R\3\2\2\2\u00e5\u00e6\7f\2\2\u00e6\u00e7\7q\2"+
		"\2\u00e7T\3\2\2\2\u00e8\u00e9\7w\2\2\u00e9\u00ea\7u\2\2\u00ea\u00eb\7"+
		"g\2\2\u00ebV\3\2\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7g\2\2\u00ee\u00ef"+
		"\7v\2\2\u00ef\u00f0\7w\2\2\u00f0\u00f1\7t\2\2\u00f1\u00f2\7p\2\2\u00f2"+
		"X\3\2\2\2\u00f3\u00f4\7e\2\2\u00f4\u00f5\7c\2\2\u00f5\u00f6\7v\2\2\u00f6"+
		"\u00f7\7e\2\2\u00f7\u00f8\7j\2\2\u00f8Z\3\2\2\2\u00f9\u00fa\7n\2\2\u00fa"+
		"\u00fb\7g\2\2\u00fb\u00fc\7v\2\2\u00fc\\\3\2\2\2\u00fd\u00fe\7k\2\2\u00fe"+
		"\u00ff\7p\2\2\u00ff^\3\2\2\2\u0100\u0105\5a\61\2\u0101\u0105\5c\62\2\u0102"+
		"\u0105\5e\63\2\u0103\u0105\5g\64\2\u0104\u0100\3\2\2\2\u0104\u0101\3\2"+
		"\2\2\u0104\u0102\3\2\2\2\u0104\u0103\3\2\2\2\u0105`\3\2\2\2\u0106\u0107"+
		"\7\62\2\2\u0107\u0109\t\2\2\2\u0108\u010a\t\3\2\2\u0109\u0108\3\2\2\2"+
		"\u010a\u010b\3\2\2\2\u010b\u0109\3\2\2\2\u010b\u010c\3\2\2\2\u010cb\3"+
		"\2\2\2\u010d\u010e\7\62\2\2\u010e\u0110\t\4\2\2\u010f\u0111\t\5\2\2\u0110"+
		"\u010f\3\2\2\2\u0111\u0112\3\2\2\2\u0112\u0110\3\2\2\2\u0112\u0113\3\2"+
		"\2\2\u0113d\3\2\2\2\u0114\u0115\7\62\2\2\u0115\u0117\t\6\2\2\u0116\u0118"+
		"\t\7\2\2\u0117\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u0117\3\2\2\2\u0119"+
		"\u011a\3\2\2\2\u011af\3\2\2\2\u011b\u011d\t\b\2\2\u011c\u011b\3\2\2\2"+
		"\u011d\u011e\3\2\2\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2\2\2\u011f\u0126"+
		"\3\2\2\2\u0120\u0122\7\60\2\2\u0121\u0123\t\b\2\2\u0122\u0121\3\2\2\2"+
		"\u0123\u0124\3\2\2\2\u0124\u0122\3\2\2\2\u0124\u0125\3\2\2\2\u0125\u0127"+
		"\3\2\2\2\u0126\u0120\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u0129\3\2\2\2\u0128"+
		"\u012a\5i\65\2\u0129\u0128\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u0135\3\2"+
		"\2\2\u012b\u012d\7\60\2\2\u012c\u012e\t\b\2\2\u012d\u012c\3\2\2\2\u012e"+
		"\u012f\3\2\2\2\u012f\u012d\3\2\2\2\u012f\u0130\3\2\2\2\u0130\u0132\3\2"+
		"\2\2\u0131\u0133\5i\65\2\u0132\u0131\3\2\2\2\u0132\u0133\3\2\2\2\u0133"+
		"\u0135\3\2\2\2\u0134\u011c\3\2\2\2\u0134\u012b\3\2\2\2\u0135h\3\2\2\2"+
		"\u0136\u0138\t\t\2\2\u0137\u0139\t\n\2\2\u0138\u0137\3\2\2\2\u0138\u0139"+
		"\3\2\2\2\u0139\u013b\3\2\2\2\u013a\u013c\t\b\2\2\u013b\u013a\3\2\2\2\u013c"+
		"\u013d\3\2\2\2\u013d\u013b\3\2\2\2\u013d\u013e\3\2\2\2\u013ej\3\2\2\2"+
		"\u013f\u0145\7$\2\2\u0140\u0144\n\13\2\2\u0141\u0142\7^\2\2\u0142\u0144"+
		"\13\2\2\2\u0143\u0140\3\2\2\2\u0143\u0141\3\2\2\2\u0144\u0147\3\2\2\2"+
		"\u0145\u0143\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u0148\3\2\2\2\u0147\u0145"+
		"\3\2\2\2\u0148\u0154\7$\2\2\u0149\u014f\7)\2\2\u014a\u014e\n\f\2\2\u014b"+
		"\u014c\7^\2\2\u014c\u014e\13\2\2\2\u014d\u014a\3\2\2\2\u014d\u014b\3\2"+
		"\2\2\u014e\u0151\3\2\2\2\u014f\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150"+
		"\u0152\3\2\2\2\u0151\u014f\3\2\2\2\u0152\u0154\7)\2\2\u0153\u013f\3\2"+
		"\2\2\u0153\u0149\3\2\2\2\u0154l\3\2\2\2\u0155\u0156\7\60\2\2\u0156n\3"+
		"\2\2\2\u0157\u0158\7\61\2\2\u0158\u0159\7\61\2\2\u0159\u015d\3\2\2\2\u015a"+
		"\u015c\n\r\2\2\u015b\u015a\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2"+
		"\2\2\u015d\u015e\3\2\2\2\u015e\u0160\3\2\2\2\u015f\u015d\3\2\2\2\u0160"+
		"\u0161\b8\2\2\u0161p\3\2\2\2\u0162\u0163\7\61\2\2\u0163\u0164\7,\2\2\u0164"+
		"\u0168\3\2\2\2\u0165\u0167\13\2\2\2\u0166\u0165\3\2\2\2\u0167\u016a\3"+
		"\2\2\2\u0168\u0169\3\2\2\2\u0168\u0166\3\2\2\2\u0169\u016b\3\2\2\2\u016a"+
		"\u0168\3\2\2\2\u016b\u016c\7,\2\2\u016c\u016d\7\61\2\2\u016d\u016e\3\2"+
		"\2\2\u016e\u016f\b9\2\2\u016fr\3\2\2\2\u0170\u0174\t\16\2\2\u0171\u0173"+
		"\t\17\2\2\u0172\u0171\3\2\2\2\u0173\u0176\3\2\2\2\u0174\u0172\3\2\2\2"+
		"\u0174\u0175\3\2\2\2\u0175t\3\2\2\2\u0176\u0174\3\2\2\2\u0177\u0179\t"+
		"\20\2\2\u0178\u0177\3\2\2\2\u0179\u017a\3\2\2\2\u017a\u0178\3\2\2\2\u017a"+
		"\u017b\3\2\2\2\u017b\u017c\3\2\2\2\u017c\u017d\b;\2\2\u017dv\3\2\2\2\31"+
		"\2\u0104\u010b\u0112\u0119\u011e\u0124\u0126\u0129\u012f\u0132\u0134\u0138"+
		"\u013d\u0143\u0145\u014d\u014f\u0153\u015d\u0168\u0174\u017a\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}