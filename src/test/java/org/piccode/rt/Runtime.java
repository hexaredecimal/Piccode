package org.piccode.rt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.piccode.ast.*;
import org.piccode.backend.Compiler;


/**
 *
 * @author hexaredecimal
 */
public class Runtime {
	@Test
	public void function() {
		var code = "function add(x, y) = x + y";
		var ast = Compiler.program(code);

		assertEquals(ast.nodes.size(), 1);
		var func = ast.nodes.getFirst();
	
		assertFalse(!(func instanceof FunctionAst));
		var node = func.execute();
		assertTrue(node instanceof PiccodeClosure);
	}

	@Test
	public void variable() {
		var code = "let foo = 1";
		var ast = Compiler.program(code);

		assertEquals(ast.nodes.size(), 1);
		var let = ast.nodes.getFirst();
	
		assertFalse(!(let instanceof VarDecl));
		
		Context.top.pushStack();
		var node = let.execute();
		Context.top.dropStackFrame();
		assertTrue(node instanceof PiccodeNumber num && num.toString().equals("1"));
	}

	@Test
	public void importModule() {
		var code = "import pkg:io";
		var ast = Compiler.program(code);
		assertEquals(ast.nodes.size(), 1);
		var import_ = ast.nodes.getFirst();
		assertFalse(!(import_ instanceof ImportAst));
		var node = import_.execute();
		assertTrue(node instanceof PiccodeBoolean bool && bool.toString().equals("true"));
	}

	
}
