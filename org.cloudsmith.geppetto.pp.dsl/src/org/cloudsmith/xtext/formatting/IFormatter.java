/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.xtext.formatting;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;

import com.google.inject.internal.Nullable;

/**
 * @author henrik
 * 
 */
interface IFormatter {
	ReplaceRegion format(/* @NotNull */IDomNode root, @Nullable ITextRegion regionToFormat, IFormattingContext ctx);
}
