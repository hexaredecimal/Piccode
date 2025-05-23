package org.piccode.ast;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.piccode.backend.Compiler;

/**
 *
 * @author hexaredecimal
 */
public class TopLevel {
	@Test
	public void function() {
		var code = "function add(x, y) = x + y";
		var ast = Compiler.program(code);

		assertEquals(ast.nodes.size(), 1);
		var func = ast.nodes.getFirst();
	
		assertFalse(!(func instanceof FunctionAst));
		var node = (FunctionAst) func;
		assertEquals(node.name, "add");
		assertEquals(node.arg.size(), 2);
		assertTrue(node.body instanceof BinOpAst);
	}

	@Test
	public void variable() {
		var code = "let foo = 1";
		var ast = Compiler.program(code);

		assertEquals(ast.nodes.size(), 1);
		var let = ast.nodes.getFirst();
	
		assertFalse(!(let instanceof VarDecl));
		var node = (VarDecl) let;
		assertEquals(node.name, "foo");
		assertTrue(node.value instanceof NumberAst num && num.text.equals("1"));
	}

	@Test
	public void module() {
		var code = 
		"""
  module Foo {
		function bar () = ()
  }
    """;
		var ast = Compiler.program(code);

		assertEquals(ast.nodes.size(), 1);
		var mod = ast.nodes.getFirst();
	
		assertFalse(!(mod instanceof ModuleAst));
		var node = (ModuleAst) mod;
		assertEquals(node.name, "Foo");
		assertEquals(node.nodes.size(), 1);

		var inner = node.nodes.getFirst();
		assertTrue(inner instanceof FunctionAst);
	}

	@Test
	public void importModule() {
		var code = "import pkg:io";
		var ast = Compiler.program(code);
		assertEquals(ast.nodes.size(), 1);
		var import_ = ast.nodes.getFirst();
		assertFalse(!(import_ instanceof ImportAst));
		var node = (ImportAst) import_;
		assertEquals(node.pkg, "pkg");
		assertEquals(node.module, "io");
	}

}
