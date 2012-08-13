/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ppdoc;

import java.util.List;

import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourceDocumentationAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourceDocumentationAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourcePropertiesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.linking.PPTask;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.nodemodel.BidiTreeIterator;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * Provides handling of documentation comments.
 * 
 */
public class DocumentationAssociator {
	private final PPGrammarAccess ga;

	/**
	 * Expression that may have associated documentation. (TODO: Puppetdoc also lists Nodes global variables, custom facts, and
	 * Puppet plugins located in modules - but don't know which of those are applicable).
	 */
	private static final Class<?>[] documentable = { HostClassDefinition.class, Definition.class, NodeDefinition.class, };

	private static final String[] defaultTaskTags = new String[] { "todo", "fixme" };

	@Inject
	public DocumentationAssociator(IGrammarAccess ga) {
		this.ga = (PPGrammarAccess) ga;
	}

	private void associateDocumentation(EObject semantic, List<INode> commentSequence) {
		StringBuffer buf = new StringBuffer();
		for(INode n : commentSequence)
			buf.append(n.getText());

		ResourceDocumentationAdapter adapter = ResourceDocumentationAdapterFactory.eINSTANCE.adapt(semantic.eResource());
		adapter.put(semantic, commentSequence);

	}

	private void associateTasks(EObject model, List<PPTask> tasks) {
		Resource r = model.eResource();
		if(r == null)
			return; // not in a resource, sorry.
		ResourcePropertiesAdapter adapter = ResourcePropertiesAdapterFactory.eINSTANCE.adapt(r);
		adapter.put(PPDSLConstants.RESOURCE_PROPERTY__TASK_LIST, tasks);
	}

	private void clearDocumentation(EObject model) {
		Resource r = model.eResource();
		if(r == null)
			return;
		ResourceDocumentationAdapter adapter = ResourceDocumentationAdapterFactory.eINSTANCE.adapt(r);
		if(adapter != null)
			adapter.clear();

	}

	public List<INode> getDocumentation(EObject semantic) {
		Resource r = semantic.eResource();
		if(r == null)
			return null;
		ResourceDocumentationAdapter adapter = ResourceDocumentationAdapterFactory.eINSTANCE.adapt(r);
		return adapter != null
				? adapter.get(semantic)
				: null;

	}

	private String[] getTaskTags() {
		return defaultTaskTags;
	}

	/**
	 * @param text
	 * @param node
	 * @return
	 */
	private boolean hasNonWSBeforeStart(String text, INode node) {
		int offset = node.getOffset();
		for(int pos = offset - 1; pos >= 0; pos--) {
			char c = text.charAt(pos);
			if(c == '\r' || c == '\n')
				return false;
			if(c > ' ')
				return true;
		}
		// reached start of parsed text
		return false;
	}

	/**
	 * Links comment nodes to classes listed in {@link #documentable} by collecting them in an
	 * adapter (for later processing by formatter/styler).
	 * 
	 */
	public void linkDocumentation(EObject model) {
		// clear stored tasks
		associateTasks(model, null);
		clearDocumentation(model);
		List<PPTask> tasks = Lists.newArrayList();

		final TerminalRule mlRule = ga.getML_COMMENTRule();
		final TerminalRule slRule = ga.getSL_COMMENTRule();
		final TerminalRule wsRule = ga.getWSRule();

		// a sequence of SL comment or a single ML comment that is immediately (no NL) before
		// a definition, class, or node is taken to be a documentation comment, as is associated with
		// the following semantic object using an adapter.
		//
		ICompositeNode node = NodeModelUtils.getNode(model);
		ICompositeNode root = node.getRootNode();
		List<INode> commentSequence = Lists.newArrayList();
		BidiTreeIterator<INode> itor = root.getAsTreeIterable().iterator();
		COLLECT_LOOP: while(itor.hasNext()) {
			// for(INode x : root.getAsTreeIterable()) {
			INode x = itor.next();
			EObject grammarElement = x.getGrammarElement();
			// process comments
			if(grammarElement == slRule || grammarElement == mlRule) {
				processCommentNode(x, tasks);
				// skip all whitespace unless it contains a break which also breaks collection
				INode sibling = x.getNextSibling();
				while(sibling != null && sibling.getGrammarElement() == wsRule) {
					if(sibling.getText().contains("\n")) {
						commentSequence.clear();
						continue COLLECT_LOOP;
					}
					sibling = sibling.getNextSibling();
				}
				if(sibling == null) {
					commentSequence.clear();
					continue;
				}

				// if adding a ML comment, use only the last, if adding a SL drop a preceding ML rule
				if(commentSequence.size() > 0) {
					if(grammarElement == mlRule)
						commentSequence.clear();
					else if(grammarElement == slRule &&
							commentSequence.get(commentSequence.size() - 1).getGrammarElement() == mlRule)
						commentSequence.clear();
				}
				commentSequence.add(x);

				// if comment has anything but whitespace before its start (on same line), it is not a documentation comment
				if(hasNonWSBeforeStart(root.getText(), x)) {
					commentSequence.clear();
					continue;
				}
				// if next is not a comment, it may be an element that the documentation should be associated with,
				// but keep collecting if next is a comment
				EObject siblingElement = sibling.getGrammarElement();
				if(siblingElement == ga.getSL_COMMENTRule() || siblingElement == ga.getML_COMMENTRule())
					continue; // keep on collecting

				EObject semantic = NodeModelUtils.findActualSemanticObjectFor(sibling);

				// check that a comment inside a structure (i.e. starts after the start of the structure)
				// is not mistaken as following
				EObject commentSemantic = NodeModelUtils.findActualSemanticObjectFor(x);
				if(commentSemantic == semantic &&
						x.getOffset() > NodeModelUtils.findActualNodeFor(semantic).getOffset())
					continue;
				found: {
					for(Class<?> clazz : documentable) {
						if(clazz.isAssignableFrom(semantic.getClass())) {
							// found sequence is documentation for semantic
							associateDocumentation(semantic, commentSequence);
							// need a new sequence, or the one just given away may be cleared
							commentSequence = Lists.newArrayList();
							break found;
						}
					}
					// next was not the right kind of element
					commentSequence.clear();
				}
			}
		}
		associateTasks(model, tasks);

	}

	private void processCommentNode(INode node, List<PPTask> taskList) {
		final String commentText = node.getText();
		final String loweredText = commentText.toLowerCase();
		if(commentText == null || commentText.length() == 0)
			return;

		int line = node.getStartLine();

		for(int startPos = 0; startPos < commentText.length(); /* repeat value in loop */) {
			int firstTagIndex = commentText.length();
			int previousTagIndex = 0;
			int firstTagLength = 0;
			for(String tag : getTaskTags()) {
				int idx = loweredText.indexOf(tag, startPos);
				if(idx >= 0 && idx < firstTagIndex) {
					firstTagIndex = idx;
					firstTagLength = tag.length();
				}
			}
			if(firstTagIndex == commentText.length())
				return; // no tags

			// msg is text to end, or up to (but not including) the newline.
			int endIndex = commentText.indexOf("\n", firstTagIndex);
			String msg = commentText.substring(firstTagIndex, endIndex < 0
					? commentText.length()
					: endIndex);

			// trim ending */ from message if ML comment?
			msg = msg.trim(); // remove trailing spaces (there can be to leading)

			EObject ge = node.getGrammarElement();
			if(ge instanceof TerminalRule) {
				if("ML_COMMENT".equals(((TerminalRule) ge).getName())) {
					if(msg.endsWith("*/")) {
						msg = msg.substring(0, msg.length() - 2);
						msg = msg.trim();
					}
				}
			}

			// if message is empty, or only contains ! take text before
			// unless, ML comment where no newline was found in msg, take text both before and after
			//
			String taskMsg = msg;
			boolean isImportant = msg.contains("!");
			String checkForBang = isImportant
					? msg.replace('!', ' ').trim()
					: msg;
			if(checkForBang.length() <= firstTagLength) {
				// System.out.println("EMPTY TODO MSG");
				// scan backwards from tag start
				String allText = node.getRootNode().getText();
				int offset = node.getOffset() + firstTagIndex + firstTagLength - 1;
				StringBuilder builder = new StringBuilder();
				for(int o = offset; o >= 0; o--) {
					char c = allText.charAt(o);
					if(c == '\n')
						break;
					builder.append(c);
				}
				builder.reverse();
				taskMsg = builder.toString();
				taskMsg = taskMsg.trim();
			}
			// increase lines seen by those that were passed between previous found and this.
			for(int i = previousTagIndex; i < firstTagIndex; i++)
				if(commentText.charAt(i) == '\n')
					line++;

			PPTask task = new PPTask(taskMsg, line, node.getOffset() + firstTagIndex, msg.length(), isImportant);
			taskList.add(task);

			if(endIndex < 0)
				return; // done, no more tags could be found

			startPos = endIndex + 1;
			previousTagIndex = firstTagIndex;
		}
	}

	/**
	 * Validate the state of documentation in model.
	 * TODO: Implement this (currently does nothing).
	 * 
	 * @param model
	 * @param acceptor
	 */
	public void validateDocumentation(EObject model, IMessageAcceptor acceptor) {

	}
}
