package org.piccode.tokens;

/**
 *
 * @author hexaredecimal
 */
public class Token {
	public TokenKind kind;
	public String text;
	public Span span;

	public Token(TokenKind kind, String text, Span span) {
		this.kind = kind;
		this.text = text;
		this.span = span;
	}

	public Token(TokenKind kind, String text) {
		this.kind = kind;
		this.text = text;
	}


	@Override
	public String toString() {
		return "Token{" + "kind=" + kind + ", text=" + text + ", span=" + span + '}';
	}

	public Token clone() {
		return new Token(kind, new String(text), span);
	}
}
