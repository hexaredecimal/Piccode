package org.piccode.ast;

import java.util.List;
import org.piccode.piccodescript.TargetEnvironment;
import org.piccode.rt.PiccodeValue;

/**
 *
 * @author hexaredecimal
 */
public class ImportLift extends Ast{
	public String text;
	public List<Ast> nodes;

	public ImportLift(String text, List<Ast> nodes) {
		this.text = text;
		this.nodes = nodes;
	}


	
	@Override
	public PiccodeValue execute(Integer frame) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}

	@Override
	public String codeGen(TargetEnvironment target) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
	
}
