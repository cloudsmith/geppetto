/**
 * Copyright (c) 2006-2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css;

import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeStatus;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * An IStyleFacory implementation for graphviz dot language.
 * TODO: Check consistency of all parameter names (copy/paste...)
 */
@Singleton
public class StyleFactory implements IStyleFactory {

	protected static abstract class AbstractStyle<T> implements IStyle<T> {
		private Function<IDomNode, T> function;

		private StyleType style;

		private Set<NodeStatus> types;

		private T value;

		public AbstractStyle(StyleType style, Function<IDomNode, T> function) {
			this.style = style;
			this.function = function;
		}

		public AbstractStyle(StyleType style, T value) {
			this.style = style;
			this.value = value;
		}

		@Override
		public StyleType getStyleType() {
			return style;
		}

		@Override
		public T getValue(IDomNode ge) {
			return function != null
					? function.apply(ge)
					: value;
		}

		@Override
		public boolean isFunction() {
			return function != null;
		}

		protected void setTypes(Set<NodeStatus> types) {
			this.types = types;
		}

		@Override
		public boolean supports(NodeStatus type) {
			return types == null
					? true
					: types.contains(type);
		}

		@Override
		public boolean supports(Set<NodeStatus> types) {
			return types == null
					? true
					: this.types.containsAll(types);
		}
	}

	public static class Align extends AbstractStyle<Alignment> {
		public Align(Alignment align) {
			super(StyleType.align, align);
			setTypes(NodeStatus.nonWhitespaceMask);
		}

		public Align(Function<IDomNode, Alignment> f) {
			super(StyleType.align, f);
			setTypes(NodeStatus.nonWhitespaceMask);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.align(getValue(ge));
		}
	}

	public abstract static class BooleanStyle extends AbstractStyle<Boolean> {
		public BooleanStyle(StyleType style, Boolean value) {
			super(style, value);
		}

		public BooleanStyle(StyleType style, Function<IDomNode, Boolean> value) {
			super(style, value);
		}
	}

	public static class ContainerNamesStyle extends AbstractStyle<Set<String>> {
		public ContainerNamesStyle(Function<IDomNode, Set<String>> f) {
			super(StyleType.containerNames, f);
			setTypes(NodeStatus.nonWhitespaceMask);
		}

		public ContainerNamesStyle(Set<String> spacing) {
			super(StyleType.containerNames, spacing);
			setTypes(NodeStatus.nonWhitespaceMask);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.containerNames(getValue(ge));
		}

	}

	public static class ContainerStyle extends StringStyle {
		public ContainerStyle(Function<IDomNode, String> f) {
			super(StyleType.container, f);
		}

		public ContainerStyle(String spacing) {
			super(StyleType.container, spacing);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.container(getValue(ge));
		}

	}

	public static class DedentStyle extends IntegerStyle {
		public DedentStyle(Function<IDomNode, Integer> f) {
			super(StyleType.dedent, f);
		}

		public DedentStyle(Integer spacing) {
			super(StyleType.dedent, spacing);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.indent(getValue(ge));
		}

	}

	protected abstract static class DoubleStyle extends AbstractStyle<Double> {
		public DoubleStyle(StyleType style, Double value) {
			super(style, value);
		}

		public DoubleStyle(StyleType style, Function<IDomNode, Double> value) {
			super(style, value);
		}

	}

	public static class IndentStyle extends IntegerStyle {
		public IndentStyle(Function<IDomNode, Integer> f) {
			super(StyleType.indent, f);
		}

		public IndentStyle(Integer count) {
			super(StyleType.indent, count);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.indent(getValue(ge));
		}

	}

	protected abstract static class IntegerStyle extends AbstractStyle<Integer> {
		public IntegerStyle(StyleType style, Function<IDomNode, Integer> value) {
			super(style, value);
		}

		public IntegerStyle(StyleType style, Integer value) {
			super(style, value);
		}
	}

	public static class LineBreakStyle extends AbstractStyle<LineBreaks> {
		public LineBreakStyle(Function<IDomNode, LineBreaks> f) {
			super(StyleType.lineBreaks, f);
			setTypes(NodeStatus.whitespaceMask);
		}

		public LineBreakStyle(LineBreaks lineBreaks) {
			super(StyleType.spacing, lineBreaks);
			setTypes(NodeStatus.whitespaceMask);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.lineBreaks(getValue(ge));
		}

	}

	public static class SpacingStyle extends AbstractStyle<Spacing> {
		public SpacingStyle(Function<IDomNode, Spacing> f) {
			super(StyleType.spacing, f);
			setTypes(NodeStatus.whitespaceMask);
		}

		public SpacingStyle(Spacing spacing) {
			super(StyleType.spacing, spacing);
			setTypes(NodeStatus.whitespaceMask);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.spacing(getValue(ge));
		}

	}

	protected abstract static class StringStyle extends AbstractStyle<String> {
		public StringStyle(StyleType style, Function<IDomNode, String> value) {
			super(style, value);
		}

		public StringStyle(StyleType style, String value) {
			super(style, value);
		}

	}

	public static class StyleNameStyle extends StringStyle {
		public StyleNameStyle(Function<IDomNode, String> f) {
			super(StyleType.styleName, f);
		}

		public StyleNameStyle(String name) {
			super(StyleType.styleName, name);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.styleName(getValue(ge));
		}

	}

	public static class TokenTextStyle extends StringStyle {
		public TokenTextStyle(Function<IDomNode, String> f) {
			super(StyleType.tokenText, f);
		}

		public TokenTextStyle(String spacing) {
			super(StyleType.tokenText, spacing);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.tokenText(getValue(ge));
		}

	}

	private IFunctionFactory functions;

	private static final IStyle<Spacing> ONESPACE = new SpacingStyle(new Spacing(1));

	private static final IStyle<Spacing> NOSPACE = new SpacingStyle(new Spacing(0));

	private static final IStyle<LineBreaks> NOLINE = new LineBreakStyle(new LineBreaks(0));

	private static final IStyle<LineBreaks> ONELINE = new LineBreakStyle(new LineBreaks(0));

	@Inject
	public StyleFactory(IFunctionFactory factory) {
		this.functions = factory;
	}

	@Override
	public Align align(Alignment x) {
		return new Align(x);
	}

	@Override
	public Align align(Function<IDomNode, Alignment> f) {
		return new Align(f);
	}

	@Override
	public IStyle<String> container(Function<IDomNode, String> f) {
		return new ContainerStyle(f);
	}

	@Override
	public IStyle<String> container(String containerName) {
		return new ContainerStyle(containerName);
	}

	@Override
	public IStyle<Set<String>> containerNames(Function<IDomNode, Set<String>> f) {
		return new ContainerNamesStyle(f);
	}

	@Override
	public IStyle<Set<String>> containerNames(Set<String> containerNames) {
		return new ContainerNamesStyle(containerNames);
	}

	@Override
	public IStyle<Integer> dedent() {
		return new DedentStyle(1);
	}

	@Override
	public IStyle<Integer> dedent(Function<IDomNode, Integer> f) {
		return new DedentStyle(f);
	}

	@Override
	public IStyle<Integer> dedent(Integer i) {
		return new DedentStyle(i);
	}

	@Override
	public IStyle<Integer> indent() {
		return new IndentStyle(1);
	}

	@Override
	public IStyle<Integer> indent(Function<IDomNode, Integer> f) {
		return new IndentStyle(f);
	}

	@Override
	public IStyle<Integer> indent(Integer i) {
		return new IndentStyle(i);
	}

	@Override
	public IStyle<LineBreaks> lineBreaks(Function<IDomNode, LineBreaks> f) {
		return new LineBreakStyle(f);
	}

	@Override
	public IStyle<LineBreaks> noLineBreak() {
		return NOLINE;
	}

	@Override
	public IStyle<Spacing> noSpace() {
		return NOSPACE;
	}

	@Override
	public IStyle<LineBreaks> oneLineBreak() {
		return ONELINE;
	}

	@Override
	public IStyle<Spacing> oneSpace() {
		return ONESPACE;
	}

	@Override
	public IStyle<Spacing> spacing(Function<IDomNode, Spacing> f) {
		return new SpacingStyle(f);
	}

	@Override
	public IStyle<String> styleName(Function<IDomNode, String> f) {
		return new StyleNameStyle(f);
	}

	@Override
	public IStyle<String> styleName(String s) {
		return new StyleNameStyle(s);
	}

	@Override
	public IStyle<String> tokenText(Function<IDomNode, String> f) {
		return new TokenTextStyle(f);
	}

	@Override
	public IStyle<String> tokenText(String s) {
		return new TokenTextStyle(s);
	}

}
