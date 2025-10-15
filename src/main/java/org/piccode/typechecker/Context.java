package org.piccode.typechecker;

import java.util.List;
import org.piccode.typechecker.type.FunctionType;
import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public class Context {
	private SymbolTable<FunctionType> functionTable;
	private SymbolTable<List<FunctionType>> binaryOperatorTable;
	private SymbolTable<List<FunctionType>> unaryOperatorTable;
	private SymbolTable<Type> typeTable;
	private SymbolTable<Type> localTable;

	public Context() {
		functionTable = new SymbolTable<>();
		binaryOperatorTable = new SymbolTable<>();
		unaryOperatorTable = new SymbolTable<>();
		typeTable = new SymbolTable<>();
		localTable = new SymbolTable<>();
	}

	public SymbolTable<Type> getLocalTable() {
		return localTable;
	}
	
	public SymbolTable<FunctionType> getFunctionTable() {
		return functionTable;
	}

	public SymbolTable<List<FunctionType>> getBinaryOperatorTable() {
		return binaryOperatorTable;
	}

	public SymbolTable<List<FunctionType>> getUnaryOperatorTable() {
		return unaryOperatorTable;
	}

	public SymbolTable<Type> getTypeTable() {
		return typeTable;
	}
	
}
