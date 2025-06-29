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
		WHEN=34, IMPORT=35, IS=36, IF=37, ELSE=38, MODULE=39, DO=40, USE=41, RETURN_TOK=42, 
		NUMBER=43, STRING=44, DOT=45, LINE_COMMENT=46, BLOCK_COMMENT=47, ID=48, 
		WS=49;
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
			"IF", "ELSE", "MODULE", "DO", "USE", "RETURN_TOK", "NUMBER", "HEX_LITERAL", 
			"OCT_LITERAL", "BIN_LITERAL", "DECIMAL_NUMBER", "EXP", "STRING", "DOT", 
			"LINE_COMMENT", "BLOCK_COMMENT", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'=='", "'!='", "'&&'", "'||'", "'>>'", "'<<'", "'&'", "'|'", "'!'", 
			"'|>'", "'::'", "'{'", "'}'", "'('", "')'", "'['", "']'", "':'", "','", 
			"';'", "'->'", "'~'", "'='", "':='", "'when'", "'import'", "'is'", "'if'", 
			"'else'", "'module'", "'do'", "'use'", "'return'", null, null, "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", 
			"NE", "AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "CC", 
			"LBRACE", "RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", 
			"COMMA", "SEMI", "ARROW", "TILDE", "ASSIGN", "DASSIGN", "WHEN", "IMPORT", 
			"IS", "IF", "ELSE", "MODULE", "DO", "USE", "RETURN_TOK", "NUMBER", "STRING", 
			"DOT", "LINE_COMMENT", "BLOCK_COMMENT", "ID", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\63\u0167\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3"+
		"\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$"+
		"\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3)\3"+
		")\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\5,\u00ee\n,\3-\3-\3"+
		"-\6-\u00f3\n-\r-\16-\u00f4\3.\3.\3.\6.\u00fa\n.\r.\16.\u00fb\3/\3/\3/"+
		"\6/\u0101\n/\r/\16/\u0102\3\60\6\60\u0106\n\60\r\60\16\60\u0107\3\60\3"+
		"\60\6\60\u010c\n\60\r\60\16\60\u010d\5\60\u0110\n\60\3\60\5\60\u0113\n"+
		"\60\3\60\3\60\6\60\u0117\n\60\r\60\16\60\u0118\3\60\5\60\u011c\n\60\5"+
		"\60\u011e\n\60\3\61\3\61\5\61\u0122\n\61\3\61\6\61\u0125\n\61\r\61\16"+
		"\61\u0126\3\62\3\62\3\62\3\62\7\62\u012d\n\62\f\62\16\62\u0130\13\62\3"+
		"\62\3\62\3\62\3\62\3\62\7\62\u0137\n\62\f\62\16\62\u013a\13\62\3\62\5"+
		"\62\u013d\n\62\3\63\3\63\3\64\3\64\3\64\3\64\7\64\u0145\n\64\f\64\16\64"+
		"\u0148\13\64\3\64\3\64\3\65\3\65\3\65\3\65\7\65\u0150\n\65\f\65\16\65"+
		"\u0153\13\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\7\66\u015c\n\66\f\66\16"+
		"\66\u015f\13\66\3\67\6\67\u0162\n\67\r\67\16\67\u0163\3\67\3\67\3\u0151"+
		"\28\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35"+
		"\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36"+
		";\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y\2[\2]\2_\2a\2c.e/g\60i\61k\62m\63"+
		"\3\2\21\4\2ZZzz\5\2\62;CHch\4\2QQqq\3\2\629\4\2DDdd\3\2\62\63\3\2\62;"+
		"\4\2GGgg\4\2--//\4\2$$^^\4\2))^^\4\2\f\f\17\17\5\2C\\aac|\6\2\62;C\\a"+
		"ac|\5\2\13\f\17\17\"\"\2\u0179\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3"+
		"\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2c\3\2\2\2\2e\3\2\2"+
		"\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\3o\3\2\2\2\5q\3\2\2\2\7"+
		"s\3\2\2\2\tu\3\2\2\2\13w\3\2\2\2\ry\3\2\2\2\17{\3\2\2\2\21~\3\2\2\2\23"+
		"\u0080\3\2\2\2\25\u0083\3\2\2\2\27\u0086\3\2\2\2\31\u0089\3\2\2\2\33\u008c"+
		"\3\2\2\2\35\u008f\3\2\2\2\37\u0092\3\2\2\2!\u0095\3\2\2\2#\u0097\3\2\2"+
		"\2%\u0099\3\2\2\2\'\u009b\3\2\2\2)\u009e\3\2\2\2+\u00a1\3\2\2\2-\u00a3"+
		"\3\2\2\2/\u00a5\3\2\2\2\61\u00a7\3\2\2\2\63\u00a9\3\2\2\2\65\u00ab\3\2"+
		"\2\2\67\u00ad\3\2\2\29\u00af\3\2\2\2;\u00b1\3\2\2\2=\u00b3\3\2\2\2?\u00b6"+
		"\3\2\2\2A\u00b8\3\2\2\2C\u00ba\3\2\2\2E\u00bd\3\2\2\2G\u00c2\3\2\2\2I"+
		"\u00c9\3\2\2\2K\u00cc\3\2\2\2M\u00cf\3\2\2\2O\u00d4\3\2\2\2Q\u00db\3\2"+
		"\2\2S\u00de\3\2\2\2U\u00e2\3\2\2\2W\u00ed\3\2\2\2Y\u00ef\3\2\2\2[\u00f6"+
		"\3\2\2\2]\u00fd\3\2\2\2_\u011d\3\2\2\2a\u011f\3\2\2\2c\u013c\3\2\2\2e"+
		"\u013e\3\2\2\2g\u0140\3\2\2\2i\u014b\3\2\2\2k\u0159\3\2\2\2m\u0161\3\2"+
		"\2\2op\7-\2\2p\4\3\2\2\2qr\7/\2\2r\6\3\2\2\2st\7,\2\2t\b\3\2\2\2uv\7\61"+
		"\2\2v\n\3\2\2\2wx\7\'\2\2x\f\3\2\2\2yz\7@\2\2z\16\3\2\2\2{|\7@\2\2|}\7"+
		"?\2\2}\20\3\2\2\2~\177\7>\2\2\177\22\3\2\2\2\u0080\u0081\7>\2\2\u0081"+
		"\u0082\7?\2\2\u0082\24\3\2\2\2\u0083\u0084\7?\2\2\u0084\u0085\7?\2\2\u0085"+
		"\26\3\2\2\2\u0086\u0087\7#\2\2\u0087\u0088\7?\2\2\u0088\30\3\2\2\2\u0089"+
		"\u008a\7(\2\2\u008a\u008b\7(\2\2\u008b\32\3\2\2\2\u008c\u008d\7~\2\2\u008d"+
		"\u008e\7~\2\2\u008e\34\3\2\2\2\u008f\u0090\7@\2\2\u0090\u0091\7@\2\2\u0091"+
		"\36\3\2\2\2\u0092\u0093\7>\2\2\u0093\u0094\7>\2\2\u0094 \3\2\2\2\u0095"+
		"\u0096\7(\2\2\u0096\"\3\2\2\2\u0097\u0098\7~\2\2\u0098$\3\2\2\2\u0099"+
		"\u009a\7#\2\2\u009a&\3\2\2\2\u009b\u009c\7~\2\2\u009c\u009d\7@\2\2\u009d"+
		"(\3\2\2\2\u009e\u009f\7<\2\2\u009f\u00a0\7<\2\2\u00a0*\3\2\2\2\u00a1\u00a2"+
		"\7}\2\2\u00a2,\3\2\2\2\u00a3\u00a4\7\177\2\2\u00a4.\3\2\2\2\u00a5\u00a6"+
		"\7*\2\2\u00a6\60\3\2\2\2\u00a7\u00a8\7+\2\2\u00a8\62\3\2\2\2\u00a9\u00aa"+
		"\7]\2\2\u00aa\64\3\2\2\2\u00ab\u00ac\7_\2\2\u00ac\66\3\2\2\2\u00ad\u00ae"+
		"\7<\2\2\u00ae8\3\2\2\2\u00af\u00b0\7.\2\2\u00b0:\3\2\2\2\u00b1\u00b2\7"+
		"=\2\2\u00b2<\3\2\2\2\u00b3\u00b4\7/\2\2\u00b4\u00b5\7@\2\2\u00b5>\3\2"+
		"\2\2\u00b6\u00b7\7\u0080\2\2\u00b7@\3\2\2\2\u00b8\u00b9\7?\2\2\u00b9B"+
		"\3\2\2\2\u00ba\u00bb\7<\2\2\u00bb\u00bc\7?\2\2\u00bcD\3\2\2\2\u00bd\u00be"+
		"\7y\2\2\u00be\u00bf\7j\2\2\u00bf\u00c0\7g\2\2\u00c0\u00c1\7p\2\2\u00c1"+
		"F\3\2\2\2\u00c2\u00c3\7k\2\2\u00c3\u00c4\7o\2\2\u00c4\u00c5\7r\2\2\u00c5"+
		"\u00c6\7q\2\2\u00c6\u00c7\7t\2\2\u00c7\u00c8\7v\2\2\u00c8H\3\2\2\2\u00c9"+
		"\u00ca\7k\2\2\u00ca\u00cb\7u\2\2\u00cbJ\3\2\2\2\u00cc\u00cd\7k\2\2\u00cd"+
		"\u00ce\7h\2\2\u00ceL\3\2\2\2\u00cf\u00d0\7g\2\2\u00d0\u00d1\7n\2\2\u00d1"+
		"\u00d2\7u\2\2\u00d2\u00d3\7g\2\2\u00d3N\3\2\2\2\u00d4\u00d5\7o\2\2\u00d5"+
		"\u00d6\7q\2\2\u00d6\u00d7\7f\2\2\u00d7\u00d8\7w\2\2\u00d8\u00d9\7n\2\2"+
		"\u00d9\u00da\7g\2\2\u00daP\3\2\2\2\u00db\u00dc\7f\2\2\u00dc\u00dd\7q\2"+
		"\2\u00ddR\3\2\2\2\u00de\u00df\7w\2\2\u00df\u00e0\7u\2\2\u00e0\u00e1\7"+
		"g\2\2\u00e1T\3\2\2\2\u00e2\u00e3\7t\2\2\u00e3\u00e4\7g\2\2\u00e4\u00e5"+
		"\7v\2\2\u00e5\u00e6\7w\2\2\u00e6\u00e7\7t\2\2\u00e7\u00e8\7p\2\2\u00e8"+
		"V\3\2\2\2\u00e9\u00ee\5Y-\2\u00ea\u00ee\5[.\2\u00eb\u00ee\5]/\2\u00ec"+
		"\u00ee\5_\60\2\u00ed\u00e9\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ed\u00eb\3\2"+
		"\2\2\u00ed\u00ec\3\2\2\2\u00eeX\3\2\2\2\u00ef\u00f0\7\62\2\2\u00f0\u00f2"+
		"\t\2\2\2\u00f1\u00f3\t\3\2\2\u00f2\u00f1\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4"+
		"\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5Z\3\2\2\2\u00f6\u00f7\7\62\2\2"+
		"\u00f7\u00f9\t\4\2\2\u00f8\u00fa\t\5\2\2\u00f9\u00f8\3\2\2\2\u00fa\u00fb"+
		"\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\\\3\2\2\2\u00fd"+
		"\u00fe\7\62\2\2\u00fe\u0100\t\6\2\2\u00ff\u0101\t\7\2\2\u0100\u00ff\3"+
		"\2\2\2\u0101\u0102\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103"+
		"^\3\2\2\2\u0104\u0106\t\b\2\2\u0105\u0104\3\2\2\2\u0106\u0107\3\2\2\2"+
		"\u0107\u0105\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u010f\3\2\2\2\u0109\u010b"+
		"\7\60\2\2\u010a\u010c\t\b\2\2\u010b\u010a\3\2\2\2\u010c\u010d\3\2\2\2"+
		"\u010d\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0110\3\2\2\2\u010f\u0109"+
		"\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u0113\5a\61\2\u0112"+
		"\u0111\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u011e\3\2\2\2\u0114\u0116\7\60"+
		"\2\2\u0115\u0117\t\b\2\2\u0116\u0115\3\2\2\2\u0117\u0118\3\2\2\2\u0118"+
		"\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u011b\3\2\2\2\u011a\u011c\5a"+
		"\61\2\u011b\u011a\3\2\2\2\u011b\u011c\3\2\2\2\u011c\u011e\3\2\2\2\u011d"+
		"\u0105\3\2\2\2\u011d\u0114\3\2\2\2\u011e`\3\2\2\2\u011f\u0121\t\t\2\2"+
		"\u0120\u0122\t\n\2\2\u0121\u0120\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0124"+
		"\3\2\2\2\u0123\u0125\t\b\2\2\u0124\u0123\3\2\2\2\u0125\u0126\3\2\2\2\u0126"+
		"\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127b\3\2\2\2\u0128\u012e\7$\2\2\u0129"+
		"\u012d\n\13\2\2\u012a\u012b\7^\2\2\u012b\u012d\13\2\2\2\u012c\u0129\3"+
		"\2\2\2\u012c\u012a\3\2\2\2\u012d\u0130\3\2\2\2\u012e\u012c\3\2\2\2\u012e"+
		"\u012f\3\2\2\2\u012f\u0131\3\2\2\2\u0130\u012e\3\2\2\2\u0131\u013d\7$"+
		"\2\2\u0132\u0138\7)\2\2\u0133\u0137\n\f\2\2\u0134\u0135\7^\2\2\u0135\u0137"+
		"\13\2\2\2\u0136\u0133\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u013a\3\2\2\2"+
		"\u0138\u0136\3\2\2\2\u0138\u0139\3\2\2\2\u0139\u013b\3\2\2\2\u013a\u0138"+
		"\3\2\2\2\u013b\u013d\7)\2\2\u013c\u0128\3\2\2\2\u013c\u0132\3\2\2\2\u013d"+
		"d\3\2\2\2\u013e\u013f\7\60\2\2\u013ff\3\2\2\2\u0140\u0141\7\61\2\2\u0141"+
		"\u0142\7\61\2\2\u0142\u0146\3\2\2\2\u0143\u0145\n\r\2\2\u0144\u0143\3"+
		"\2\2\2\u0145\u0148\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0147\3\2\2\2\u0147"+
		"\u0149\3\2\2\2\u0148\u0146\3\2\2\2\u0149\u014a\b\64\2\2\u014ah\3\2\2\2"+
		"\u014b\u014c\7\61\2\2\u014c\u014d\7,\2\2\u014d\u0151\3\2\2\2\u014e\u0150"+
		"\13\2\2\2\u014f\u014e\3\2\2\2\u0150\u0153\3\2\2\2\u0151\u0152\3\2\2\2"+
		"\u0151\u014f\3\2\2\2\u0152\u0154\3\2\2\2\u0153\u0151\3\2\2\2\u0154\u0155"+
		"\7,\2\2\u0155\u0156\7\61\2\2\u0156\u0157\3\2\2\2\u0157\u0158\b\65\2\2"+
		"\u0158j\3\2\2\2\u0159\u015d\t\16\2\2\u015a\u015c\t\17\2\2\u015b\u015a"+
		"\3\2\2\2\u015c\u015f\3\2\2\2\u015d\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e"+
		"l\3\2\2\2\u015f\u015d\3\2\2\2\u0160\u0162\t\20\2\2\u0161\u0160\3\2\2\2"+
		"\u0162\u0163\3\2\2\2\u0163\u0161\3\2\2\2\u0163\u0164\3\2\2\2\u0164\u0165"+
		"\3\2\2\2\u0165\u0166\b\67\2\2\u0166n\3\2\2\2\31\2\u00ed\u00f4\u00fb\u0102"+
		"\u0107\u010d\u010f\u0112\u0118\u011b\u011d\u0121\u0126\u012c\u012e\u0136"+
		"\u0138\u013c\u0146\u0151\u015d\u0163\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}