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
package org.cloudsmith.xtext.dommodel.formatter.css;

import java.util.Set;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.IDomNode.NodeType;
import org.cloudsmith.xtext.dommodel.formatter.ILayoutManager;

import com.google.common.base.Function;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * An IStyleFacory implementation for Xtext IDomNode
 */
@Singleton
public class StyleFactory implements IStyleFactory {

	protected static abstract class AbstractStyle<T> implements IStyle<T> {
		private Function<IDomNode, T> function;

		private Set<NodeType> types;

		private T value;

		public AbstractStyle(Function<IDomNode, T> function) {
			this.function = function;
		}

		public AbstractStyle(T value) {
			this.value = value;
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

		protected void setTypes(Set<NodeType> types) {
			this.types = types;
		}

		@Override
		public boolean supports(NodeType type) {
			return types == null
					? true
					: types.contains(type);
		}

		@Override
		public boolean supports(Set<NodeType> types) {
			return types == null
					? true
					: this.types.containsAll(types);
		}
	}

	public static class AlignedSeparatorIndex extends IntegerStyle {
		public AlignedSeparatorIndex(Function<IDomNode, Integer> f) {
			super(f);
		}

		public AlignedSeparatorIndex(Integer count) {
			super(count);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.indent(getValue(ge));
		}

	}

	public static class AlignmentStyle extends AbstractStyle<Alignment> {
		public AlignmentStyle(Alignment align) {
			super(align);
			setTypes(NodeType.nonWhitespaceSet);
		}

		public AlignmentStyle(Function<IDomNode, Alignment> f) {
			super(f);
			setTypes(NodeType.nonWhitespaceSet);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.align(getValue(ge));
		}
	}

	public abstract static class BooleanStyle extends AbstractStyle<Boolean> {
		public BooleanStyle(Boolean value) {
			super(value);
		}

		public BooleanStyle(Function<IDomNode, Boolean> value) {
			super(value);
		}
	}

	public static class DedentStyle extends IntegerStyle {
		public DedentStyle(Function<IDomNode, Integer> f) {
			super(f);
		}

		public DedentStyle(Integer spacing) {
			super(spacing);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.dedent(getValue(ge));
		}

	}

	protected abstract static class DoubleStyle extends AbstractStyle<Double> {
		public DoubleStyle(Double value) {
			super(value);
		}

		public DoubleStyle(Function<IDomNode, Double> value) {
			super(value);
		}

	}

	public static class IndentStyle extends IntegerStyle {
		public IndentStyle(Function<IDomNode, Integer> f) {
			super(f);
		}

		public IndentStyle(Integer count) {
			super(count);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.indent(getValue(ge));
		}

	}

	protected abstract static class IntegerStyle extends AbstractStyle<Integer> {
		public IntegerStyle(Function<IDomNode, Integer> value) {
			super(value);
		}

		public IntegerStyle(Integer value) {
			super(value);
		}
	}

	public static class LayoutManagerStyle extends AbstractStyle<ILayoutManager> {
		public LayoutManagerStyle(Function<IDomNode, ILayoutManager> f) {
			super(f);
		}

		public LayoutManagerStyle(ILayoutManager layout) {
			super(layout);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.layout(getValue(ge));
		}

	}

	public static class LineBreakStyle extends AbstractStyle<LineBreaks> {
		public LineBreakStyle(Function<IDomNode, LineBreaks> f) {
			super(f);
			setTypes(NodeType.whitespaceSet);
		}

		public LineBreakStyle(LineBreaks lineBreaks) {
			super(lineBreaks);
			setTypes(NodeType.whitespaceSet);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.lineBreaks(getValue(ge));
		}

	}

	public static class SpacingStyle extends AbstractStyle<Spacing> {
		public SpacingStyle(Function<IDomNode, Spacing> f) {
			super(f);
			setTypes(NodeType.whitespaceSet);
		}

		public SpacingStyle(Spacing spacing) {
			super(spacing);
			setTypes(NodeType.whitespaceSet);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.spacing(getValue(ge));
		}

	}

	protected abstract static class StringStyle extends AbstractStyle<String> {
		public StringStyle(Function<IDomNode, String> value) {
			super(value);
		}

		public StringStyle(String value) {
			super(value);
		}

	}

	public static class StyleNameStyle extends StringStyle {
		public StyleNameStyle(Function<IDomNode, String> f) {
			super(f);
		}

		public StyleNameStyle(String name) {
			super(name);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.styleName(getValue(ge));
		}

	}

	public static class TokenTextStyle extends StringStyle {
		public TokenTextStyle(Function<IDomNode, String> f) {
			super(f);
		}

		public TokenTextStyle(String spacing) {
			super(spacing);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.tokenText(getValue(ge));
		}

	}

	public static class VerbatimStyle extends BooleanStyle {
		public VerbatimStyle(Boolean value) {
			super(value);
		}

		public VerbatimStyle(Function<IDomNode, Boolean> f) {
			super(f);
		}

		@Override
		public void visit(IDomNode node, IStyleVisitor visitor) {
			visitor.verbatim(getValue(node));
		}

	}

	public static class WidthStyle extends IntegerStyle {
		public WidthStyle(Function<IDomNode, Integer> f) {
			super(f);
		}

		public WidthStyle(Integer width) {
			super(width);
		}

		@Override
		public void visit(IDomNode ge, IStyleVisitor visitor) {
			visitor.width(getValue(ge));
		}

	}

	private static final SpacingStyle ONESPACE = new SpacingStyle(new Spacing(1, true));

	private static final SpacingStyle ONE_NON_WRAPPABLE_SPACE = new SpacingStyle(new Spacing(1, false));

	private static final SpacingStyle NOSPACE_UNLESS_WRAPPED = new SpacingStyle(new Spacing(0, true));

	private static final SpacingStyle NOSPACE = new SpacingStyle(new Spacing(0, false));

	private static final LineBreakStyle NOLINE = new LineBreakStyle(new LineBreaks(0));

	private static final LineBreakStyle ONELINE = new LineBreakStyle(new LineBreaks(1));

	@Inject
	public StyleFactory() {
	}

	@Override
	public AlignmentStyle align(Alignment x) {
		return new AlignmentStyle(x);
	}

	@Override
	public AlignmentStyle align(Function<IDomNode, Alignment> f) {
		return new AlignmentStyle(f);
	}

	@Override
	public DedentStyle dedent() {
		return new DedentStyle(1);
	}

	@Override
	public DedentStyle dedent(Function<IDomNode, Integer> f) {
		return new DedentStyle(f);
	}

	@Override
	public DedentStyle dedent(Integer i) {
		return new DedentStyle(i);
	}

	@Override
	public IndentStyle indent() {
		return new IndentStyle(1);
	}

	@Override
	public IndentStyle indent(Function<IDomNode, Integer> f) {
		return new IndentStyle(f);
	}

	@Override
	public IndentStyle indent(Integer i) {
		return new IndentStyle(i);
	}

	@Override
	public LayoutManagerStyle layout(Function<IDomNode, ILayoutManager> f) {
		return new LayoutManagerStyle(f);
	}

	@Override
	public LayoutManagerStyle layout(ILayoutManager manager) {
		return new LayoutManagerStyle(manager);
	}

	@Override
	public LineBreakStyle lineBreaks(Function<IDomNode, LineBreaks> f) {
		return new LineBreakStyle(f);
	}

	@Override
	public LineBreakStyle lineBreaks(int min, int normal, int max) {
		return new LineBreakStyle(new LineBreaks(min, normal, max));
	}

	@Override
	public LineBreakStyle lineBreaks(int min, int normal, int max, boolean acceptCommentEndingWithBreak,
			boolean acceptExisting) {
		return new LineBreakStyle(new LineBreaks(min, normal, max, acceptCommentEndingWithBreak, acceptExisting));
	}

	@Override
	public LineBreakStyle noLineBreak() {
		return NOLINE;
	}

	@Override
	public SpacingStyle noSpace() {
		return NOSPACE;
	}

	@Override
	public SpacingStyle noSpaceUnlessWrapped() {
		return NOSPACE_UNLESS_WRAPPED;
	}

	@Override
	public LineBreakStyle oneLineBreak() {
		return ONELINE;
	}

	@Override
	public SpacingStyle oneNonWrappableSpace() {
		return ONE_NON_WRAPPABLE_SPACE;
	}

	@Override
	public SpacingStyle oneSpace() {
		return ONESPACE;
	}

	@Override
	public IndentStyle separatorAlignmentIndex(Function<IDomNode, Integer> f) {
		return new IndentStyle(f);
	}

	@Override
	public IndentStyle separatorAlignmentIndex(Integer i) {
		return new IndentStyle(i);
	}

	@Override
	public SpacingStyle spacing(Function<IDomNode, Spacing> f) {
		return new SpacingStyle(f);
	}

	@Override
	public SpacingStyle spacing(int min, int normal, int max) {
		return new SpacingStyle(new Spacing(min, normal, max));
	}

	@Override
	public StyleNameStyle styleName(Function<IDomNode, String> f) {
		return new StyleNameStyle(f);
	}

	@Override
	public StyleNameStyle styleName(String s) {
		return new StyleNameStyle(s);
	}

	@Override
	public TokenTextStyle tokenText(Function<IDomNode, String> f) {
		return new TokenTextStyle(f);
	}

	@Override
	public TokenTextStyle tokenText(String s) {
		return new TokenTextStyle(s);
	}

	@Override
	public VerbatimStyle verbatim(boolean b) {
		return new VerbatimStyle(b);
	}

	@Override
	public VerbatimStyle verbatim(Function<IDomNode, Boolean> f) {
		return new VerbatimStyle(f);
	}

	@Override
	public WidthStyle width(Function<IDomNode, Integer> f) {
		return new WidthStyle(f);
	}

	@Override
	public WidthStyle width(Integer i) {
		return new WidthStyle(i);
	}

}
