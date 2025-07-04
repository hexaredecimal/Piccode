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
		CATCH_TOK=43, NUMBER=44, STRING=45, DOT=46, LINE_COMMENT=47, BLOCK_COMMENT=48, 
		ID=49, WS=50;
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
			"IF", "ELSE", "MODULE", "DO", "USE", "RETURN_TOK", "CATCH_TOK", "NUMBER", 
			"HEX_LITERAL", "OCT_LITERAL", "BIN_LITERAL", "DECIMAL_NUMBER", "EXP", 
			"STRING", "DOT", "LINE_COMMENT", "BLOCK_COMMENT", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", "'<='", 
			"'=='", "'!='", "'&&'", "'||'", "'>>'", "'<<'", "'&'", "'|'", "'!'", 
			"'|>'", "'::'", "'{'", "'}'", "'('", "')'", "'['", "']'", "':'", "','", 
			"';'", "'->'", "'~'", "'='", "':='", "'when'", "'import'", "'is'", "'if'", 
			"'else'", "'module'", "'do'", "'use'", "'return'", "'catch'", null, null, 
			"'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ADD", "SUB", "MUL", "DIV", "MOD", "GT", "GE", "LT", "LE", "EQ", 
			"NE", "AND", "OR", "SHL", "SHR", "BAND", "BOR", "EXCLAIM", "PIPE", "CC", 
			"LBRACE", "RBRACE", "LPAREN", "RPAREN", "LBRACKET", "RBRACKET", "COLON", 
			"COMMA", "SEMI", "ARROW", "TILDE", "ASSIGN", "DASSIGN", "WHEN", "IMPORT", 
			"IS", "IF", "ELSE", "MODULE", "DO", "USE", "RETURN_TOK", "CATCH_TOK", 
			"NUMBER", "STRING", "DOT", "LINE_COMMENT", "BLOCK_COMMENT", "ID", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\64\u016f\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64"+
		"\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\3\2\3\2\3\3\3\3\3\4\3\4\3\5"+
		"\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3"+
		"\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\25\3\26\3\26\3"+
		"\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35\3"+
		"\36\3\36\3\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3"+
		"$\3$\3$\3$\3$\3%\3%\3%\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3("+
		"\3(\3)\3)\3)\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3,\3,\3,\3,\3,\3,\3-\3-"+
		"\3-\3-\5-\u00f6\n-\3.\3.\3.\6.\u00fb\n.\r.\16.\u00fc\3/\3/\3/\6/\u0102"+
		"\n/\r/\16/\u0103\3\60\3\60\3\60\6\60\u0109\n\60\r\60\16\60\u010a\3\61"+
		"\6\61\u010e\n\61\r\61\16\61\u010f\3\61\3\61\6\61\u0114\n\61\r\61\16\61"+
		"\u0115\5\61\u0118\n\61\3\61\5\61\u011b\n\61\3\61\3\61\6\61\u011f\n\61"+
		"\r\61\16\61\u0120\3\61\5\61\u0124\n\61\5\61\u0126\n\61\3\62\3\62\5\62"+
		"\u012a\n\62\3\62\6\62\u012d\n\62\r\62\16\62\u012e\3\63\3\63\3\63\3\63"+
		"\7\63\u0135\n\63\f\63\16\63\u0138\13\63\3\63\3\63\3\63\3\63\3\63\7\63"+
		"\u013f\n\63\f\63\16\63\u0142\13\63\3\63\5\63\u0145\n\63\3\64\3\64\3\65"+
		"\3\65\3\65\3\65\7\65\u014d\n\65\f\65\16\65\u0150\13\65\3\65\3\65\3\66"+
		"\3\66\3\66\3\66\7\66\u0158\n\66\f\66\16\66\u015b\13\66\3\66\3\66\3\66"+
		"\3\66\3\66\3\67\3\67\7\67\u0164\n\67\f\67\16\67\u0167\13\67\38\68\u016a"+
		"\n8\r8\168\u016b\38\38\3\u0159\29\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31"+
		"\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+U,W-Y.[\2]\2"+
		"_\2a\2c\2e/g\60i\61k\62m\63o\64\3\2\21\4\2ZZzz\5\2\62;CHch\4\2QQqq\3\2"+
		"\629\4\2DDdd\3\2\62\63\3\2\62;\4\2GGgg\4\2--//\4\2$$^^\4\2))^^\4\2\f\f"+
		"\17\17\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\2\u0181\2\3\3\2\2"+
		"\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3"+
		"\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2"+
		"W\3\2\2\2\2Y\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3"+
		"\2\2\2\2o\3\2\2\2\3q\3\2\2\2\5s\3\2\2\2\7u\3\2\2\2\tw\3\2\2\2\13y\3\2"+
		"\2\2\r{\3\2\2\2\17}\3\2\2\2\21\u0080\3\2\2\2\23\u0082\3\2\2\2\25\u0085"+
		"\3\2\2\2\27\u0088\3\2\2\2\31\u008b\3\2\2\2\33\u008e\3\2\2\2\35\u0091\3"+
		"\2\2\2\37\u0094\3\2\2\2!\u0097\3\2\2\2#\u0099\3\2\2\2%\u009b\3\2\2\2\'"+
		"\u009d\3\2\2\2)\u00a0\3\2\2\2+\u00a3\3\2\2\2-\u00a5\3\2\2\2/\u00a7\3\2"+
		"\2\2\61\u00a9\3\2\2\2\63\u00ab\3\2\2\2\65\u00ad\3\2\2\2\67\u00af\3\2\2"+
		"\29\u00b1\3\2\2\2;\u00b3\3\2\2\2=\u00b5\3\2\2\2?\u00b8\3\2\2\2A\u00ba"+
		"\3\2\2\2C\u00bc\3\2\2\2E\u00bf\3\2\2\2G\u00c4\3\2\2\2I\u00cb\3\2\2\2K"+
		"\u00ce\3\2\2\2M\u00d1\3\2\2\2O\u00d6\3\2\2\2Q\u00dd\3\2\2\2S\u00e0\3\2"+
		"\2\2U\u00e4\3\2\2\2W\u00eb\3\2\2\2Y\u00f5\3\2\2\2[\u00f7\3\2\2\2]\u00fe"+
		"\3\2\2\2_\u0105\3\2\2\2a\u0125\3\2\2\2c\u0127\3\2\2\2e\u0144\3\2\2\2g"+
		"\u0146\3\2\2\2i\u0148\3\2\2\2k\u0153\3\2\2\2m\u0161\3\2\2\2o\u0169\3\2"+
		"\2\2qr\7-\2\2r\4\3\2\2\2st\7/\2\2t\6\3\2\2\2uv\7,\2\2v\b\3\2\2\2wx\7\61"+
		"\2\2x\n\3\2\2\2yz\7\'\2\2z\f\3\2\2\2{|\7@\2\2|\16\3\2\2\2}~\7@\2\2~\177"+
		"\7?\2\2\177\20\3\2\2\2\u0080\u0081\7>\2\2\u0081\22\3\2\2\2\u0082\u0083"+
		"\7>\2\2\u0083\u0084\7?\2\2\u0084\24\3\2\2\2\u0085\u0086\7?\2\2\u0086\u0087"+
		"\7?\2\2\u0087\26\3\2\2\2\u0088\u0089\7#\2\2\u0089\u008a\7?\2\2\u008a\30"+
		"\3\2\2\2\u008b\u008c\7(\2\2\u008c\u008d\7(\2\2\u008d\32\3\2\2\2\u008e"+
		"\u008f\7~\2\2\u008f\u0090\7~\2\2\u0090\34\3\2\2\2\u0091\u0092\7@\2\2\u0092"+
		"\u0093\7@\2\2\u0093\36\3\2\2\2\u0094\u0095\7>\2\2\u0095\u0096\7>\2\2\u0096"+
		" \3\2\2\2\u0097\u0098\7(\2\2\u0098\"\3\2\2\2\u0099\u009a\7~\2\2\u009a"+
		"$\3\2\2\2\u009b\u009c\7#\2\2\u009c&\3\2\2\2\u009d\u009e\7~\2\2\u009e\u009f"+
		"\7@\2\2\u009f(\3\2\2\2\u00a0\u00a1\7<\2\2\u00a1\u00a2\7<\2\2\u00a2*\3"+
		"\2\2\2\u00a3\u00a4\7}\2\2\u00a4,\3\2\2\2\u00a5\u00a6\7\177\2\2\u00a6."+
		"\3\2\2\2\u00a7\u00a8\7*\2\2\u00a8\60\3\2\2\2\u00a9\u00aa\7+\2\2\u00aa"+
		"\62\3\2\2\2\u00ab\u00ac\7]\2\2\u00ac\64\3\2\2\2\u00ad\u00ae\7_\2\2\u00ae"+
		"\66\3\2\2\2\u00af\u00b0\7<\2\2\u00b08\3\2\2\2\u00b1\u00b2\7.\2\2\u00b2"+
		":\3\2\2\2\u00b3\u00b4\7=\2\2\u00b4<\3\2\2\2\u00b5\u00b6\7/\2\2\u00b6\u00b7"+
		"\7@\2\2\u00b7>\3\2\2\2\u00b8\u00b9\7\u0080\2\2\u00b9@\3\2\2\2\u00ba\u00bb"+
		"\7?\2\2\u00bbB\3\2\2\2\u00bc\u00bd\7<\2\2\u00bd\u00be\7?\2\2\u00beD\3"+
		"\2\2\2\u00bf\u00c0\7y\2\2\u00c0\u00c1\7j\2\2\u00c1\u00c2\7g\2\2\u00c2"+
		"\u00c3\7p\2\2\u00c3F\3\2\2\2\u00c4\u00c5\7k\2\2\u00c5\u00c6\7o\2\2\u00c6"+
		"\u00c7\7r\2\2\u00c7\u00c8\7q\2\2\u00c8\u00c9\7t\2\2\u00c9\u00ca\7v\2\2"+
		"\u00caH\3\2\2\2\u00cb\u00cc\7k\2\2\u00cc\u00cd\7u\2\2\u00cdJ\3\2\2\2\u00ce"+
		"\u00cf\7k\2\2\u00cf\u00d0\7h\2\2\u00d0L\3\2\2\2\u00d1\u00d2\7g\2\2\u00d2"+
		"\u00d3\7n\2\2\u00d3\u00d4\7u\2\2\u00d4\u00d5\7g\2\2\u00d5N\3\2\2\2\u00d6"+
		"\u00d7\7o\2\2\u00d7\u00d8\7q\2\2\u00d8\u00d9\7f\2\2\u00d9\u00da\7w\2\2"+
		"\u00da\u00db\7n\2\2\u00db\u00dc\7g\2\2\u00dcP\3\2\2\2\u00dd\u00de\7f\2"+
		"\2\u00de\u00df\7q\2\2\u00dfR\3\2\2\2\u00e0\u00e1\7w\2\2\u00e1\u00e2\7"+
		"u\2\2\u00e2\u00e3\7g\2\2\u00e3T\3\2\2\2\u00e4\u00e5\7t\2\2\u00e5\u00e6"+
		"\7g\2\2\u00e6\u00e7\7v\2\2\u00e7\u00e8\7w\2\2\u00e8\u00e9\7t\2\2\u00e9"+
		"\u00ea\7p\2\2\u00eaV\3\2\2\2\u00eb\u00ec\7e\2\2\u00ec\u00ed\7c\2\2\u00ed"+
		"\u00ee\7v\2\2\u00ee\u00ef\7e\2\2\u00ef\u00f0\7j\2\2\u00f0X\3\2\2\2\u00f1"+
		"\u00f6\5[.\2\u00f2\u00f6\5]/\2\u00f3\u00f6\5_\60\2\u00f4\u00f6\5a\61\2"+
		"\u00f5\u00f1\3\2\2\2\u00f5\u00f2\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f5\u00f4"+
		"\3\2\2\2\u00f6Z\3\2\2\2\u00f7\u00f8\7\62\2\2\u00f8\u00fa\t\2\2\2\u00f9"+
		"\u00fb\t\3\2\2\u00fa\u00f9\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fa\3\2"+
		"\2\2\u00fc\u00fd\3\2\2\2\u00fd\\\3\2\2\2\u00fe\u00ff\7\62\2\2\u00ff\u0101"+
		"\t\4\2\2\u0100\u0102\t\5\2\2\u0101\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103"+
		"\u0101\3\2\2\2\u0103\u0104\3\2\2\2\u0104^\3\2\2\2\u0105\u0106\7\62\2\2"+
		"\u0106\u0108\t\6\2\2\u0107\u0109\t\7\2\2\u0108\u0107\3\2\2\2\u0109\u010a"+
		"\3\2\2\2\u010a\u0108\3\2\2\2\u010a\u010b\3\2\2\2\u010b`\3\2\2\2\u010c"+
		"\u010e\t\b\2\2\u010d\u010c\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u010d\3\2"+
		"\2\2\u010f\u0110\3\2\2\2\u0110\u0117\3\2\2\2\u0111\u0113\7\60\2\2\u0112"+
		"\u0114\t\b\2\2\u0113\u0112\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0113\3\2"+
		"\2\2\u0115\u0116\3\2\2\2\u0116\u0118\3\2\2\2\u0117\u0111\3\2\2\2\u0117"+
		"\u0118\3\2\2\2\u0118\u011a\3\2\2\2\u0119\u011b\5c\62\2\u011a\u0119\3\2"+
		"\2\2\u011a\u011b\3\2\2\2\u011b\u0126\3\2\2\2\u011c\u011e\7\60\2\2\u011d"+
		"\u011f\t\b\2\2\u011e\u011d\3\2\2\2\u011f\u0120\3\2\2\2\u0120\u011e\3\2"+
		"\2\2\u0120\u0121\3\2\2\2\u0121\u0123\3\2\2\2\u0122\u0124\5c\62\2\u0123"+
		"\u0122\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0126\3\2\2\2\u0125\u010d\3\2"+
		"\2\2\u0125\u011c\3\2\2\2\u0126b\3\2\2\2\u0127\u0129\t\t\2\2\u0128\u012a"+
		"\t\n\2\2\u0129\u0128\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012c\3\2\2\2\u012b"+
		"\u012d\t\b\2\2\u012c\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u012c\3\2"+
		"\2\2\u012e\u012f\3\2\2\2\u012fd\3\2\2\2\u0130\u0136\7$\2\2\u0131\u0135"+
		"\n\13\2\2\u0132\u0133\7^\2\2\u0133\u0135\13\2\2\2\u0134\u0131\3\2\2\2"+
		"\u0134\u0132\3\2\2\2\u0135\u0138\3\2\2\2\u0136\u0134\3\2\2\2\u0136\u0137"+
		"\3\2\2\2\u0137\u0139\3\2\2\2\u0138\u0136\3\2\2\2\u0139\u0145\7$\2\2\u013a"+
		"\u0140\7)\2\2\u013b\u013f\n\f\2\2\u013c\u013d\7^\2\2\u013d\u013f\13\2"+
		"\2\2\u013e\u013b\3\2\2\2\u013e\u013c\3\2\2\2\u013f\u0142\3\2\2\2\u0140"+
		"\u013e\3\2\2\2\u0140\u0141\3\2\2\2\u0141\u0143\3\2\2\2\u0142\u0140\3\2"+
		"\2\2\u0143\u0145\7)\2\2\u0144\u0130\3\2\2\2\u0144\u013a\3\2\2\2\u0145"+
		"f\3\2\2\2\u0146\u0147\7\60\2\2\u0147h\3\2\2\2\u0148\u0149\7\61\2\2\u0149"+
		"\u014a\7\61\2\2\u014a\u014e\3\2\2\2\u014b\u014d\n\r\2\2\u014c\u014b\3"+
		"\2\2\2\u014d\u0150\3\2\2\2\u014e\u014c\3\2\2\2\u014e\u014f\3\2\2\2\u014f"+
		"\u0151\3\2\2\2\u0150\u014e\3\2\2\2\u0151\u0152\b\65\2\2\u0152j\3\2\2\2"+
		"\u0153\u0154\7\61\2\2\u0154\u0155\7,\2\2\u0155\u0159\3\2\2\2\u0156\u0158"+
		"\13\2\2\2\u0157\u0156\3\2\2\2\u0158\u015b\3\2\2\2\u0159\u015a\3\2\2\2"+
		"\u0159\u0157\3\2\2\2\u015a\u015c\3\2\2\2\u015b\u0159\3\2\2\2\u015c\u015d"+
		"\7,\2\2\u015d\u015e\7\61\2\2\u015e\u015f\3\2\2\2\u015f\u0160\b\66\2\2"+
		"\u0160l\3\2\2\2\u0161\u0165\t\16\2\2\u0162\u0164\t\17\2\2\u0163\u0162"+
		"\3\2\2\2\u0164\u0167\3\2\2\2\u0165\u0163\3\2\2\2\u0165\u0166\3\2\2\2\u0166"+
		"n\3\2\2\2\u0167\u0165\3\2\2\2\u0168\u016a\t\20\2\2\u0169\u0168\3\2\2\2"+
		"\u016a\u016b\3\2\2\2\u016b\u0169\3\2\2\2\u016b\u016c\3\2\2\2\u016c\u016d"+
		"\3\2\2\2\u016d\u016e\b8\2\2\u016ep\3\2\2\2\31\2\u00f5\u00fc\u0103\u010a"+
		"\u010f\u0115\u0117\u011a\u0120\u0123\u0125\u0129\u012e\u0134\u0136\u013e"+
		"\u0140\u0144\u014e\u0159\u0165\u016b\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}