package org.piccode.rt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.piccode.ast.*;
import org.piccode.backend.Compiler;


/**
 *
 * @author hexaredecimal
 */
public class RuntimeTest {
	@Test
	public void function() {
		var code = "add :: (x, y) = x + y";
		var ast = Compiler.program("test", code);

		assertEquals(ast.nodes.size(), 1);
		var func = ast.nodes.getFirst();
	
		assertFalse(!(func instanceof FunctionAst));
	}

	@Test
	public void variable() {
		var code = "foo := 1";
		var ast = Compiler.program("test", code);

		assertEquals(ast.nodes.size(), 1);
		var let = ast.nodes.getFirst();
	
		assertFalse(!(let instanceof VarDecl));
		
		Context.top.pushStackFrame(ast);
		var node = let.execute(null);
		Context.top.dropStackFrame();
		assertTrue(node instanceof PiccodeNumber num && num.toString().equals("1.0"));
	}

	@Test
	public void importModule() {
		var code = "import std.io";
		var ast = Compiler.program("test", code);
		assertEquals(ast.nodes.size(), 1);
		var import_ = ast.nodes.getFirst();
		assertFalse(!(import_ instanceof ImportAst));
	}

	
}
