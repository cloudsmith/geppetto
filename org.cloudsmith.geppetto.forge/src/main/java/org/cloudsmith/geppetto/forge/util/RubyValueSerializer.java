/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package org.cloudsmith.geppetto.forge.util;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class RubyValueSerializer implements ValueSerializer {
	public static final RubyValueSerializer INSTANCE = new RubyValueSerializer();

	@Override
	public void serialize(Appendable out, Object value) throws IOException {
		if(value == null)
			out.append("nil");
		else if(value instanceof String) {
			String str = (String) value;
			out.append('\'');
			int top = str.length();
			for(int idx = 0; idx < top; ++idx) {
				char c = str.charAt(idx);
				switch(c) {
					case '\\':
					case '\'':
						out.append('\\');
						out.append(c);
						break;
					default:
						out.append(c);
				}
			}
			out.append('\'');
		}
		else if(value instanceof Number || value instanceof Boolean)
			out.append(value.toString());
		else if(value instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>) value;
			out.append('{');

			boolean hash = true;
			for(Object key : map.keySet())
				if(!(key instanceof String)) {
					hash = false;
					break;
				}

			boolean first = true;
			for(Map.Entry<?, ?> entry : map.entrySet()) {
				if(first)
					first = false;
				else
					out.append(", ");
				if(hash) {
					out.append(':');
					out.append((String) entry.getKey());
				}
				else
					serialize(out, entry.getKey());
				out.append(" => ");
				serialize(out, entry.getValue());
			}
			out.append('}');
		}
		else if(value instanceof Collection<?>) {
			Collection<?> coll = (Collection<?>) value;
			out.append('[');
			boolean first = true;
			for(Object elem : coll) {
				if(first)
					first = false;
				else
					out.append(", ");
				serialize(out, elem);
			}
			out.append(']');
		}
		else {
			// Use a quoted string for output
			serialize(out, value.toString());
		}
	}
}
