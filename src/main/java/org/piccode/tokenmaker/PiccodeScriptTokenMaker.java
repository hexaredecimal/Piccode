package org.piccode.tokenmaker;

import java.util.List;
import javax.swing.text.Segment;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.fife.ui.rsyntaxtextarea.Token;
import org.piccode.antlr4.PiccodeScriptLexer;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMaker;
import org.fife.ui.rsyntaxtextarea.TokenMap;

/**
 *
 * @author hexaredecimal
 */

public class PiccodeScriptTokenMaker extends AbstractTokenMaker {
	private static List<String> importants = List.of("pkg", "remote", "super", "true", "false");

	@Override
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {
		resetTokenList();

		// Correct way to extract the exact text from the segment
		String lineText = new String(text.array, text.offset, text.count);
		CharStream input = CharStreams.fromString(lineText);
		var lexer = new PiccodeScriptLexer(input);
		lexer.removeErrorListeners(); // Optional cleanup

		org.antlr.v4.runtime.Token antlrToken = lexer.nextToken();
		while (antlrToken.getType() != org.antlr.v4.runtime.Token.EOF) {
			int type = mapAntlrToRSyntaxType(antlrToken.getType(), String.copyValueOf(text.array));

			int start = antlrToken.getStartIndex();
			int end = antlrToken.getStopIndex();

			// Important bounds check: prevent crashes if ANTLR returns weird offsets
			if (start >= 0 && end < text.offset + text.count) {
				addToken(text.array, text.offset + start, text.offset + end, type, startOffset + start, false);
			}

			antlrToken = lexer.nextToken();
		}

		addNullToken(); // Always end the token chain
		return firstToken;
	}

	private int mapAntlrToRSyntaxType(int antlrType, String text) {
		switch (antlrType) {
			case PiccodeScriptLexer.FUNCTION:
			case PiccodeScriptLexer.IF:
			case PiccodeScriptLexer.ELSE:
			case PiccodeScriptLexer.WHEN:
			case PiccodeScriptLexer.LET:
			case PiccodeScriptLexer.IMPORT:
			case PiccodeScriptLexer.IS:
			case PiccodeScriptLexer.MODULE:
			case PiccodeScriptLexer.DO:
				return Token.RESERVED_WORD;
			case PiccodeScriptLexer.STRING:
				return Token.LITERAL_STRING_DOUBLE_QUOTE;
			case PiccodeScriptLexer.ID:
				if (importants.contains(text.trim())) {
					return Token.RESERVED_WORD;
				}
				return Token.IDENTIFIER;
			case PiccodeScriptLexer.NUMBER:
				return Token.LITERAL_NUMBER_DECIMAL_INT;
			case PiccodeScriptLexer.WS:
				return Token.WHITESPACE;
			case PiccodeScriptLexer.LINE_COMMENT:
			case PiccodeScriptLexer.BLOCK_COMMENT:
				return Token.COMMENT_DOCUMENTATION;
			case PiccodeScriptLexer.LPAREN:
			case PiccodeScriptLexer.RPAREN:
			case PiccodeScriptLexer.LBRACE:
			case PiccodeScriptLexer.RBRACE:
			case PiccodeScriptLexer.COLON:
			case PiccodeScriptLexer.SEMI:
			case PiccodeScriptLexer.COMMA:
				return Token.SEPARATOR;
			default:
				return Token.IDENTIFIER;
		}
	}

	@Override
	public TokenMap getWordsToHighlight() {
		TokenMap tokenMap = new TokenMap();
		var keywords = List.of("function", "import", "let", "when", "is", "if", "else", "module", "do");
		for (var kw : keywords) {
			tokenMap.put(kw, Token.RESERVED_WORD);
		}
		for (var kw : importants) {
			tokenMap.put(kw, Token.RESERVED_WORD);
		}
		return tokenMap;
	}

	@Override
	public void addToken(Segment segment, int start, int end, int tokenType, int startOffset) {
		if (tokenType == Token.RESERVED_WORD || tokenType == Token.IDENTIFIER) {
			int value = wordsToHighlight.get(segment, start, end);
			if (value != -1) {
				tokenType = value;
			}
		}
		super.addToken(segment, start, end, tokenType, startOffset);
	}
}
