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
package com.puppetlabs.geppetto.pp.pptp.util;

/**
 * Utility class that helps with adding types and fragments and keeping them linked.
 * Linking is optional, but is required to handle dynamic changes in types/fragments.
 * 
 * NOT NEEDED WITH NEW MODEL
 */
public class PPTPLinker {
	//
	// public static void addType(TargetEntry target, Type t) {
	// target.getTypes().add(t);
	// for(TypeFragment f : target.getTypeFragments())
	// if(f.getName().equals(t.getName()))
	// link(f, t);
	//
	// }
	//
	// public static void addTypeFragment(TargetEntry target, TypeFragment f) {
	// target.getTypeFragments().add(f);
	// for(Type t : target.getTypes())
	// if(f.getName().equals(t.getName()))
	// link(f, t);
	// }
	//
	// /**
	// * Find the first TargetEntry contributed from the given file.
	// *
	// * @param theTargetPlatform
	// * @param file
	// * @return a found TargetEntry, or null if not found
	// */
	// public static TargetEntry findTargetEntry(PuppetTarget theTargetPlatform, File file) {
	// if(file == null)
	// throw new IllegalArgumentException("File can not be null!");
	// for(TargetEntry target : theTargetPlatform.getEntries())
	// if(file.getPath().equals(target.getFile().getPath()))
	// return target;
	// return null;
	// }
	//
	// /**
	// * Links properties
	// * TODO: NOT NEEDED USING NEW MODEL ... NOW, NOT LINKING
	// *
	// * @param f
	// * @param t
	// */
	// public static void link(TypeFragment f, Type t) {
	// t.getProperties().addAll(EcoreUtil.copyAll(f.getProperties()));
	// t.getParameters().addAll(EcoreUtil.copyAll(f.getParameters()));
	// // f.getMadeContributionTo().add(t);
	// }
	//
	// /**
	// * Removes and unlinks all entries and elements in the given target t if made from file f.
	// *
	// * @param t
	// * @param f
	// */
	// public static void removeContributionsFromFile(PuppetTarget t, File f) {
	// if(f == null)
	// throw new IllegalArgumentException("File can not be null");
	// ListIterator<TargetEntry> litor = t.getEntries().listIterator();
	// while(litor.hasNext()) {
	// TargetEntry te = litor.next();
	// // scan all entries
	// removeContributionsFromFile(te, f);
	// if(f.equals(te.getFile())) {
	// removeContributionsFromFile(te, null); // remove all
	// litor.remove();
	// }
	// }
	// }
	//
	// /**
	// * Removes and unlinks all elements in the given target entry t, if made from file f.
	// *
	// * @param t
	// * @param f
	// * - if null, remove all elements in TargetEntry
	// */
	// public static void removeContributionsFromFile(TargetEntry t, File f) {
	// ListIterator<? extends TargetElement> fi = t.getFunctions().listIterator();
	// while(fi.hasNext()) {
	// TargetElement elem = fi.next();
	// if(f == null || f.equals(elem.getFile()))
	// fi.remove();
	// }
	// List<Type> typesToRemove = new ArrayList<Type>();
	// if(f == null)
	// typesToRemove.addAll(t.getTypes());
	// else
	// for(Type type : t.getTypes())
	// if(f.equals(type.getFile()))
	// typesToRemove.add(type);
	// for(Type type : typesToRemove)
	// removeType(t, type);
	//
	// List<TypeFragment> fragmentsToRemove = new ArrayList<TypeFragment>();
	// if(f == null)
	// fragmentsToRemove.addAll(t.getTypeFragments());
	// else
	// for(TypeFragment fragment : t.getTypeFragments())
	// if(f.equals(fragment.getFile()))
	// fragmentsToRemove.add(fragment);
	// for(TypeFragment fragment : fragmentsToRemove)
	// removeTypeFragment(t, fragment);
	// }
	//
	// /**
	// * TODO: NOT NEEDED IN NEW MODEL
	// *
	// * @param target
	// * @param t
	// */
	// public static void removeType(TargetEntry target, Type t) {
	// // Types are trivially removed as they have copies of contributions
	// target.getTypes().remove(t);
	// // The contributing fragments should no longer reference this type
	// // t.getContributingFragments().clear();
	// }
	//
	// /**
	// * TODO: NOT NEEDED IN NEW MODEL
	// *
	// * @param target
	// * @param f
	// */
	// public static void removeTypeFragment(TargetEntry target, TypeFragment f) {
	// target.getTypeFragments().remove(f);
	// // for(Type t : f.getMadeContributionTo()) {
	// // for(Property p : f.getProperties()) {
	// // ListIterator<Property> litor = t.getProperties().listIterator();
	// // while(litor.hasNext()) {
	// // Property tp = litor.next();
	// // if(tp.getName().equals(p.getName()))
	// // litor.remove();
	// // }
	// // }
	// // for(Parameter p : f.getParameters()) {
	// // ListIterator<Parameter> litor = t.getParameters().listIterator();
	// // while(litor.hasNext()) {
	// // Parameter tp = litor.next();
	// // if(tp.getName().equals(p.getName()))
	// // litor.remove();
	// // }
	// // }
	// // }
	// // // remove knowledge that this fragment has made contributions
	// // f.getMadeContributionTo().clear();
	// }
}
