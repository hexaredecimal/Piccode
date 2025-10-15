package org.piccode.typechecker;

import org.piccode.typechecker.type.Type;

/**
 *
 * @author hexaredecimal
 */
public interface TypeCheckable {
	public Type getType(Context ctx);
}
