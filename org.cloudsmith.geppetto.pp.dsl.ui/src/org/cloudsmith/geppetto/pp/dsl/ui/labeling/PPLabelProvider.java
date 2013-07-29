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
package org.cloudsmith.geppetto.pp.dsl.ui.labeling;

import java.util.List;

import org.cloudsmith.geppetto.pp.AppendExpression;
import org.cloudsmith.geppetto.pp.AtExpression;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DoubleQuotedString;
import org.cloudsmith.geppetto.pp.Expression;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.ImportExpression;
import org.cloudsmith.geppetto.pp.LiteralList;
import org.cloudsmith.geppetto.pp.LiteralName;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.ParenthesisedExpression;
import org.cloudsmith.geppetto.pp.PuppetManifest;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SingleQuotedString;
import org.cloudsmith.geppetto.pp.TextExpression;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapter;
import org.cloudsmith.geppetto.pp.adapters.ClassifierAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.validation.PPJavaValidator;
import org.cloudsmith.geppetto.pp.pptp.Function;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.xtext.Keyword;
import org.eclipse.xtext.ui.label.DefaultEObjectLabelProvider;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class PPLabelProvider extends DefaultEObjectLabelProvider implements IIconNames {
	private static class DefaultStyler extends Styler {
		private final String fForegroundColorName;

		private final String fBackgroundColorName;

		public DefaultStyler(String foregroundColorName, String backgroundColorName) {
			fForegroundColorName = foregroundColorName;
			fBackgroundColorName = backgroundColorName;
		}

		@Override
		public void applyStyles(TextStyle textStyle) {
			ColorRegistry colorRegistry = JFaceResources.getColorRegistry();
			if(fForegroundColorName != null) {
				textStyle.foreground = colorRegistry.get(fForegroundColorName);
			}
			if(fBackgroundColorName != null) {
				textStyle.background = colorRegistry.get(fBackgroundColorName);
			}
		}
	}

	@Inject
	private PPJavaValidator validator;

	// public static final String UNIT = "obj16/unit_obj.gif";

	/**
	 * Length after which a label is truncated with '...'
	 */
	private static final int LABEL_LIMIT = 20;

	public static final Styler ERROR_STYLER = new DefaultStyler(JFacePreferences.ERROR_COLOR, null);

	public static final Styler EXPR_STYLER = new DefaultStyler(JFacePreferences.QUALIFIER_COLOR, null);

	@Inject
	public PPLabelProvider(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	private StyledString appendStyled(StyledString s, Object a) {
		return appendStyled(s, a, null);
	}

	private StyledString appendStyled(StyledString s, Object a, Styler styler) {
		if(a instanceof String)
			s.append((String) a, styler);
		else if(a instanceof StyledString) {
			if(styler != null)
				s.append(a.toString(), styler); // restyle
			else
				s.append((StyledString) a);
		}
		else
			s.append(a.toString(), styler);
		return s;
	}

	public Object image(AppendExpression o) {
		return APPEND;
	}

	public Object image(Definition o) {
		return DEFINITION;
	}

	public Object image(Expression o) {
		if(o.eContainer() instanceof ImportExpression)
			return IMPORT;
		return null;
	}

	public Object image(Function o) {
		return FUNCTION;
	}

	public Object image(HostClassDefinition element) {
		return CLASS;
	}

	public Object image(ImportExpression element) {
		if(element.getValues().size() <= 1)
			return IMPORT;
		return IMPORTS;
	}

	public Object image(Keyword o) {
		return BLUE_CIRCLE;
	}

	public Object image(NodeDefinition o) {
		return NODE;
	}

	public Object image(PuppetManifest o) {
		return FILE;
	}

	public Object image(ResourceBody o) {
		return image((ResourceExpression) o.eContainer());
	}

	public Object image(ResourceExpression o) {
		int resourceClassifier = ClassifierAdapterFactory.eINSTANCE.adapt(o).getClassifier();
		if(resourceClassifier == ClassifierAdapter.UNKNOWN)
			validator.checkResourceExpression(o);
		resourceClassifier = ClassifierAdapterFactory.eINSTANCE.adapt(o).getClassifier();
		switch(resourceClassifier) {
			case ClassifierAdapter.UNKNOWN:
				return RESOURCE_UNKNOWN;
			case ClassifierAdapter.RESOURCE_IS_BAD:
				return RESOURCE_UNKNOWN;
			case ClassifierAdapter.RESOURCE_IS_DEFAULT:
				return RESOURCE_DEFAULTS;
			case ClassifierAdapter.RESOURCE_IS_OVERRIDE:
				return RESOURCE_OVERRIDE;
			default:
				return RESOURCE_REGULAR;
		}
	}

	private StyledString literalNames(List<? extends EObject> exprs) {
		StyledString result = new StyledString();
		boolean first = true;
		for(EObject expr : exprs) {
			Object label = doGetText(expr);
			if(first)
				first = false;
			else
				result.append(", ");

			if(label instanceof String)
				result.append((String) label);
			else if(label instanceof StyledString)
				result.append((StyledString) label);
			else
				result.append("?");
		}
		// truncate long list (bad way, destroys individual styles)
		if(result.length() > LABEL_LIMIT)
			return new StyledString(result.getString().substring(0, LABEL_LIMIT) + "...");
		return result;
	}

	private String nullSafeString(String s) {
		return s == null
				? ""
				: s;
	}

	StyledString text(AtExpression o) {
		StyledString label = new StyledString();
		Object lo = doGetText(o.getLeftExpr());
		appendStyled(label, lo);
		label.append("[");

		for(Expression expr : o.getParameters()) {
			appendStyled(label, doGetText(expr));
		}
		label.append("]");
		return label;
	}

	StyledString text(Definition ele) {
		StyledString bld = new StyledString(ele.getClassName());

		bld.append(" : Definition", StyledString.DECORATIONS_STYLER);
		return bld;
	}

	StyledString text(DoubleQuotedString ele) {
		StyledString result = new StyledString();
		for(TextExpression te : ele.getStringPart()) {
			if(te instanceof VerbatimTE)
				appendStyled(result, nullSafeString(((VerbatimTE) te).getText()));
			else if(te instanceof VariableTE)
				appendStyled(result, nullSafeString(((VariableTE) te).getVarName()), EXPR_STYLER);
			else if(te instanceof ExpressionTE) {
				ExpressionTE exprTe = (ExpressionTE) te;
				Expression expr = exprTe.getExpression();
				if(expr instanceof ParenthesisedExpression)
					expr = ((ParenthesisedExpression) expr).getExpr();
				Object label = doGetText(expr);
				if(label == null)
					label = "<expr>";
				String stringLabel = label.toString();
				if(stringLabel.length() > LABEL_LIMIT)
					stringLabel = "${" + stringLabel.substring(0, LABEL_LIMIT) + "[...]}";
				else
					stringLabel = "${" + stringLabel + "}";
				appendStyled(result, stringLabel, EXPR_STYLER);
			}
		}
		return result;
	}

	StyledString text(HostClassDefinition ele) {
		StyledString bld = new StyledString(ele.getClassName());

		bld.append(" : Class", StyledString.DECORATIONS_STYLER);
		return bld;
	}

	StyledString text(ImportExpression ele) {
		// StyledString label = new StyledString("import declaration" + (ele.getValues().size() >1 ? "s" : ""));
		List<EObject> names = Lists.newArrayList();
		names.addAll(ele.getValues());
		StyledString label = new StyledString("import ");
		appendStyled(label, literalNames(names), EXPR_STYLER);
		return label;
	}

	/**
	 * Literal lists can be used where names can appear - i.e. something has "a list of names" as name.
	 * 
	 * @param ele
	 * @return
	 */
	StyledString text(LiteralList ele) {
		StyledString label = new StyledString();
		label.append("[", EXPR_STYLER);
		label.append(literalNames(ele.getElements()));
		label.append("]", EXPR_STYLER);
		return label;
	}

	String text(LiteralName ele) {
		return ele.getValue();
	}

	String text(LiteralNameOrReference ele) {
		return ele.getValue();
	}

	StyledString text(NodeDefinition ele) {
		StyledString bld = literalNames(ele.getHostNames());

		bld.append(" : Node", StyledString.DECORATIONS_STYLER);
		return bld;
	}

	String text(PuppetManifest ele) {
		String s = ele.eResource().getURI().lastSegment();
		return Strings.isEmpty(s)
				? "<unnamed>"
				: URI.decode(s);
	}

	StyledString text(ResourceBody ele) {
		StyledString label = new StyledString();
		appendStyled(label, doGetText(ele.getNameExpr()));
		return label;
	}

	StyledString text(ResourceExpression ele) {
		int resourceClassifier = ClassifierAdapterFactory.eINSTANCE.adapt(ele).getClassifier();
		if(resourceClassifier == ClassifierAdapter.UNKNOWN)
			validator.checkResourceExpression(ele);
		resourceClassifier = ClassifierAdapterFactory.eINSTANCE.adapt(ele).getClassifier();
		StyledString label = new StyledString();

		Object name = doGetText(ele.getResourceExpr());
		appendStyled(label, name);

		final EList<ResourceBody> bodyList = ele.getResourceData();
		final int bodyListSize = bodyList.size();
		if(resourceClassifier == ClassifierAdapter.RESOURCE_IS_REGULAR) {
			// append the first named resource body, and add , ... if more than one

			if(bodyList.size() >= 1) {
				Object bodyLabel = doGetText(bodyList.get(0).getNameExpr());
				label.append(" ");
				appendStyled(label, bodyLabel, StyledString.QUALIFIER_STYLER);
				if(bodyListSize > 1)
					label.append(", ...", StyledString.QUALIFIER_STYLER);
			}
		}

		StyledString typeLabel = new StyledString();
		switch(resourceClassifier) {
			case ClassifierAdapter.UNKNOWN:
				typeLabel.append(" : ", StyledString.DECORATIONS_STYLER);
				typeLabel.append("Unknown type of Resource expression", ERROR_STYLER);
				break;
			case ClassifierAdapter.RESOURCE_IS_BAD:
				typeLabel.append(" : ", StyledString.DECORATIONS_STYLER);
				typeLabel.append("Invalid Resource", ERROR_STYLER);
				break;
			case ClassifierAdapter.RESOURCE_IS_DEFAULT:
				typeLabel.append(" : Resource Defaults", StyledString.DECORATIONS_STYLER);
				break;
			case ClassifierAdapter.RESOURCE_IS_OVERRIDE:
				typeLabel.append(" : Resource Override", StyledString.DECORATIONS_STYLER);
				break;

			case ClassifierAdapter.RESOURCE_IS_REGULAR:
				typeLabel.append(" : Resource" + (bodyListSize > 1
						? "s"
						: ""), StyledString.DECORATIONS_STYLER);
				break;
		}
		label.append(typeLabel);
		return label;
	}

	String text(SingleQuotedString ele) {
		return ele.getText();
	}

	String text(VariableExpression ele) {
		return ele.getVarName();
	}
}
