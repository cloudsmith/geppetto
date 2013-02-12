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
package org.cloudsmith.geppetto.validation.runner;

import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.xtext.util.Strings;

import com.google.common.collect.Lists;

/**
 * Describes information about Rakefiles.
 * 
 */
public class RakefileInfo {
	public static class Rakefile {
		private IPath path;

		private List<Raketask> tasks;

		/**
		 * 
		 * @param path - relative path from "root"
		 */
		public Rakefile(IPath path) {
			if(path == null)
				throw new IllegalArgumentException("path can not be null");
			if(path.isAbsolute())
				throw new IllegalArgumentException("path must be relative");
			this.path = path;
			tasks = Lists.newArrayList();
		}

		public void addTask(Raketask task) {
			tasks.add(task);
		}

		/**
		 * The path relative to the root used when dicovering rakefiles.
		 * 
		 * @return
		 */
		public IPath getPath() {
			return path;
		}

		/**
		 * A list of tasks discovered in the rakefile. May be empty, and not contain all tasks, if tasks are
		 * constructed via general purpose ruby logic.
		 * 
		 * @return
		 */
		public List<Raketask> getTasks() {
			return Collections.unmodifiableList(tasks);
		}
	}

	/**
	 * Describes one rake task.
	 * 
	 */
	public static class Raketask {
		private String name;

		private String description;

		public Raketask(String name) {
			if(Strings.isEmpty(name))
				throw new IllegalArgumentException("name can not be null or empty");
			this.name = name;
			this.description = "";
		}

		public Raketask(String name, String description) {
			if(Strings.isEmpty(name))
				throw new IllegalArgumentException("Raketask name must have length > 0");
			if(description == null)
				throw new IllegalArgumentException("Raketask description may not be null");
			this.name = name;
			this.description = description;
		}

		/**
		 * An optional description of the task (if it was discovered), or an empty string. Is never null.
		 * 
		 * @return
		 */
		public String getDescription() {
			return description;
		}

		/**
		 * The name of the task. Is never an empty string.
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}
	}

	private List<Rakefile> rakefiles;

	public RakefileInfo() {
		rakefiles = Lists.newArrayList();
	}

	/**
	 * Adds a Rakfile, ignores null rakefiles.
	 * 
	 * @param rakefile
	 */
	public void addRakefile(Rakefile rakefile) {
		if(rakefile == null)
			return;
		rakefiles.add(rakefile);
	}

	public List<Rakefile> getRakefiles() {
		return rakefiles;
	}
}
