package org.piccode.ast;

import java.util.ArrayList;
import java.util.List;
import org.piccode.errors.PiccodeException;
import org.piccode.typechecker.Context;

/**
 *
 * @author hexaredecimal
 */
public class TypedFunction extends Ast {

	public FunctionTypeDeclAst declaration;
	public List<FunctionAst> functions; 

	public TypedFunction(FunctionTypeDeclAst declaration, List<FunctionAst> functions) {
		this.declaration = declaration;
		this.functions = functions;
	}

	public TypedFunction(FunctionTypeDeclAst declaration, FunctionAst function) {
		this.declaration = declaration;
		this.functions = new ArrayList<>();
		this.functions.add(function);
	}


	public void typeCheckHeader(Context ctx) {
		declaration.getType(ctx);
	}
	
	public void typeCheck(Context ctx) {
		if (functions.size() == 1) {
			var func = functions.getFirst();
			verifyFunction(ctx, func);

			if (hasPatternParam(func.arg)) {
				var newArgs = getNewArgs(func.arg);

				var whenCases = new ArrayList<WhenCase>();
				var cases = new ArrayList<Ast>();
				
				var caseTuple= new TupleAst(func.arg);
				cases.add(caseTuple);
				whenCases.add(new WhenCase(cases, func.body));
				
				var whenAst = new WhenAst(new TupleAst(newArgs), whenCases, new ErrorNodeExpr("Failed to match"));
				
				var newFunc = new FunctionAst(func.name, newArgs, whenAst);
				newFunc = (FunctionAst) Ast.finalizeNode(newFunc, func);
				var functionTable = ctx.getFunctionTable();
				var functionType = functionTable.getValueForSymbol(func.name);
				functionType.function = newFunc;
				newFunc.getType(ctx);
				return;
			} 

			func.getType(ctx);
			return;
		}

		throw new PiccodeException(file, line, column, "TODO: Implement functions with clauses");
	}

	private void verifyFunction(Context ctx, FunctionAst func) {
		if (!func.name.equals(declaration.name)) {
			var error = new PiccodeException(func.file, func.line, func.column, "Function `" + func.name + "` does not match the declared function. Names are different.");
			error.addNote(new PiccodeException(declaration.file, declaration.line, declaration.column, "The function is declared here"));
			throw error;
		}
		
		var functionTable = ctx.getFunctionTable();
		var functionType = functionTable.getValueForSymbol(func.name);
		if (functionType == null) {
			throw new PiccodeException(func.file, func.line, func.column, "Function `" + func.name + "` is not declared");
		}

		var expected = functionType.args.size();
		var found = func.arg.size();

		if (expected != found) {
			var error = new PiccodeException(func.file, func.line, func.column, "Function `" + func.name + "` has " + found + " parameters but the declaration has " + expected);
			var node = functionType.getDeclaringNode();
			error.addNote(new PiccodeException(node.file, node.line, node.column, "The function is declared here"));
			throw error;
		}
	}
	
	private boolean hasPatternParam(List<Ast> params) {
		for (var param : params) {
			if (!(param instanceof Arg)) {
				return true;
			}
		}
		return false;
	}

	private List<Ast> getNewArgs(List<Ast> params) {
		var newArgs = new ArrayList<Ast>();
		var count = 0;
		for (var param : params) {
			if (!(param instanceof Arg)) {
				var arg = new Arg("ignored_Arg_" + count++);
				arg.file = param.file;
				arg.column = param.column;
				arg.line = param.line;
				newArgs.add(arg);
			} else {
				newArgs.add(param);
			}
		}

		return newArgs;
	}
}
