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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.folding;

import java.util.List;
import java.util.regex.Matcher;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.AssertionFailedException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.xtext.ui.editor.folding.DefaultFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionAcceptor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.internal.Lists;

/**
 * A folding RegionProvider that provides folding of sequence of SL Comment
 * 
 */
public class PPFoldingRegionProvider extends DefaultFoldingRegionProvider {
	private static final Logger log = Logger.getLogger(PPFoldingRegionProvider.class);

	@Override
	protected void computeCommentFolding(IXtextDocument xtextDocument,
			IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor) {
		computeMLCommentFolding(xtextDocument, foldingRegionAcceptor);
		computeSLCommentFolding(xtextDocument, foldingRegionAcceptor);
	}

	protected void computeMLCommentFolding(IXtextDocument xtextDocument,
			IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor) {
		try {
			ITypedRegion[] typedRegions = xtextDocument.computePartitioning(
				IDocumentExtension3.DEFAULT_PARTITIONING, 0, xtextDocument.getLength(), false);
			for(ITypedRegion typedRegion : typedRegions) {
				if(TerminalsTokenTypeToPartitionMapper.COMMENT_PARTITION.equals(typedRegion.getType())) {
					int offset = typedRegion.getOffset();
					int length = typedRegion.getLength();
					String content = xtextDocument.get(offset, length);
					if(content.endsWith("\r\n"))
						length -= 2;
					else if(content.endsWith("\n"))
						length -= 1;

					Matcher matcher = getTextPatternInComment().matcher(content);
					if(matcher.find()) {
						TextRegion significant = new TextRegion(offset + matcher.start(), 0);
						foldingRegionAcceptor.accept(offset, length, significant);
					}
					else {
						foldingRegionAcceptor.accept(offset, length);
					}
				}
			}
		}
		catch(BadLocationException e) {
			log.error(e, e);
		}
		catch(BadPartitioningException e) {
			log.error(e, e);
		}
		catch(AssertionFailedException e) {
			// partioning failed
			log.error(e, e);
		}
	}

	/**
	 * @param xtextDocument
	 * @param foldingRegionAcceptor
	 */
	private void computeSLCommentFolding(IXtextDocument xtextDocument,
			IFoldingRegionAcceptor<ITextRegion> foldingRegionAcceptor) {
		try {
			ITypedRegion[] typedRegions = xtextDocument.computePartitioning(
				IDocumentExtension3.DEFAULT_PARTITIONING, 0, xtextDocument.getLength(), false);

			List<ITypedRegion> slComments = Lists.newArrayList();
			List<List<ITypedRegion>> commentGroups = Lists.newArrayList();

			for(ITypedRegion typedRegion : typedRegions)
				if(TerminalsTokenTypeToPartitionMapper.SL_COMMENT_PARTITION.equals(typedRegion.getType())) {
					if(slComments.isEmpty())
						slComments.add(typedRegion);
					else {
						int index = slComments.size() - 1;
						ITypedRegion previous = slComments.get(index);
						if(previous.getOffset() + previous.getLength() == typedRegion.getOffset()) {
							slComments.add(typedRegion);
						}
						else {
							// not a continuation, do we have a folding region?
							if(slComments.size() > 1)
								commentGroups.add(slComments);
							slComments = Lists.newArrayList();
							slComments.add(typedRegion);
						}
					}
				}
			if(slComments.size() > 1)
				commentGroups.add(slComments);

			for(List<ITypedRegion> group : commentGroups) {
				int startOffset = -1;
				int length = 0;
				for(ITypedRegion typedRegion : group) {
					if(startOffset == -1)
						startOffset = typedRegion.getOffset();
					length += typedRegion.getLength();
				}
				String content = xtextDocument.get(startOffset, length);

				if(content.endsWith("\r\n"))
					length -= 2;
				else if(content.endsWith("\n"))
					length -= 1;

				Matcher matcher = getTextPatternInComment().matcher(content);
				if(matcher.find()) {
					TextRegion significant = new TextRegion(startOffset + matcher.start(), 0);
					foldingRegionAcceptor.accept(startOffset, length, significant);
				}
				else {
					foldingRegionAcceptor.accept(startOffset, length);
				}
			}

		}
		catch(BadLocationException e) {
			log.error(e, e);
		}
		catch(BadPartitioningException e) {
			log.error(e, e);
		}
		catch(AssertionFailedException e) {
			// partitioning failed
			log.error(e, e);
		}
	}
}
