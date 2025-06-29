package org.piccode.tokens;

/**
 *
 * @author hexaredecimal
 */
public enum TokenKind {
	MULTPLICATIVE_OPERATOR, // * % /
	ADDITIVE_OPERATOR, // +-
	BITWISE_OPERATOR, // >> << & |
	BOOLEAN_OPERATOR, // == != >= <= > < 
	STR_CONCAT_OPERATOR, // ++
	PIPE, // |>
	ASSIGN, // =
	KEYWORD,
	ID,
	NUMBER,
	STRING,
	LPAREN, // (
	RPAREN, // )
	LBRACE, // {
	RBRACE, // }
	LBRACKET, // [
	RBRACKET, // ]
	ARROW, // ->
	COLON, //:
	DBL_COLON, // ::
	COMMA, //,
	SEMI_COLON, // ;
	DOT, // .
	HASH, // #
	QUESTIONMARK, // ? 
	DQ_ASSIGN, // :=
	EOF, 
	ERR, // For Error situations
}
