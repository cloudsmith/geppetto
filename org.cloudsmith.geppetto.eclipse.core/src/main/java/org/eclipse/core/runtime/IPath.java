/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.core.runtime;

/**
 * A path is an ordered collection of string segments,
 * separated by a standard separator character, "/".
 * A path may also have a leading and/or a trailing separator.
 * Paths may also be prefixed by an optional device id, which includes
 * the character(s) which separate the device id from the rest 
 * of the path. For example, "C:" and "Server/Volume:" are typical
 * device ids.
 * A device independent path has <code>null</code> for a device id.
 * <p>
 * Note that paths are value objects; all operations on paths 
 * return a new path; the path that is operated on is unscathed.
 * </p>
 * <p>
 * UNC paths are denoted by leading double-slashes such 
 * as <code>//Server/Volume/My/Path</code>. When a new path
 * is constructed all double-slashes are removed except those
 * appearing at the beginning of the path.
 * </p>
 * <p>
 * This interface can be used without OSGi running.
 * </p><p>
 * This interface is not intended to be implemented by clients.
 * </p>
 * @see Path
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IPath extends Cloneable {

	/**
	 * Path separator character constant "/" used in paths.
	 */
	public static final char SEPARATOR = '/';

	/** 
	 * Device separator character constant ":" used in paths.
	 */
	public static final char DEVICE_SEPARATOR = ':';

	/**
	 * Returns a new path which is the same as this path but with
	 * the given file extension added.  If this path is empty, root or has a 
	 * trailing separator, this path is returned.  If this path already
	 * has an extension, the existing extension is left and the given
	 * extension simply appended.  Clients wishing to replace
	 * the current extension should first remove the extension and
	 * then add the desired one.
	 * <p>
	 * The file extension portion is defined as the string
	 * following the last period (".") character in the last segment.
	 * The given extension should not include a leading ".".
	 * </p>
	 *
	 * @param extension the file extension to append
	 * @return the new path
	 */
	public IPath addFileExtension(String extension);

	/**
	 * Returns a path with the same segments as this path
	 * but with a trailing separator added.
	 * This path must have at least one segment.
	 * <p>
	 * If this path already has a trailing separator,
	 * this path is returned.
	 * </p>
	 *
	 * @return the new path
	 * @see #hasTrailingSeparator()
	 * @see #removeTrailingSeparator()
	 */
	public IPath addTrailingSeparator();

	/**
	 * Returns the canonicalized path obtained from the
	 * concatenation of the given string path to the
	 * end of this path. The given string path must be a valid
	 * path. If it has a trailing separator, 
	 * the result will have a trailing separator.
	 * The device id of this path is preserved (the one
	 * of the given string is ignored). Duplicate slashes
	 * are removed from the path except at the beginning
	 * where the path is considered to be UNC.
	 * 
	 * @param path the string path to concatenate
	 * @return the new path
	 * @see #isValidPath(String)
	 */
	public IPath append(String path);

	/**
	 * Returns the canonicalized path obtained from the 
	 * concatenation of the given path's segments to the
	 * end of this path.  If the given path has a trailing
	 * separator, the result will have a trailing separator.
	 * The device id of this path is preserved (the one
	 * of the given path is ignored). Duplicate slashes
	 * are removed from the path except at the beginning
	 * where the path is considered to be UNC.
	 *
	 * @param path the path to concatenate
	 * @return the new path
	 */
	public IPath append(IPath path);

	/**
	 * Returns a copy of this path.
	 *
	 * @return the cloned path
	 */
	public Object clone();

	/**
	 * Returns whether this path equals the given object.
	 * <p>
	 * Equality for paths is defined to be: same sequence of segments,
	 * same absolute/relative status, and same device.
	 * Trailing separators are disregarded.
	 * Paths are not generally considered equal to objects other than paths.
	 * </p>
	 *
	 * @param obj the other object
	 * @return <code>true</code> if the paths are equivalent,
	 *    and <code>false</code> if they are not
	 */
	public boolean equals(Object obj);

	/**
	 * Returns the device id for this path, or <code>null</code> if this
	 * path has no device id. Note that the result will end in ':'.
	 *
	 * @return the device id, or <code>null</code>
	 * @see #setDevice(String)
	 */
	public String getDevice();

	/**
	 * Returns the file extension portion of this path, 
	 * or <code>null</code> if there is none.
	 * <p>
	 * The file extension portion is defined as the string
	 * following the last period (".") character in the last segment.
	 * If there is no period in the last segment, the path has no
	 * file extension portion. If the last segment ends in a period,
	 * the file extension portion is the empty string.
	 * </p>
	 *
	 * @return the file extension or <code>null</code>
	 */
	public String getFileExtension();

	/**
	 * Returns whether this path has a trailing separator.
	 * <p>
	 * Note: In the root path ("/"), the separator is considered to
	 * be leading rather than trailing.
	 * </p>
	 *
	 * @return <code>true</code> if this path has a trailing
	 *    separator, and <code>false</code> otherwise
	 * @see #addTrailingSeparator()
	 * @see #removeTrailingSeparator()
	 */
	public boolean hasTrailingSeparator();

	/**
	 * Returns whether this path is an absolute path (ignoring
	 * any device id).
	 * <p>
	 * Absolute paths start with a path separator.
	 * A root path, like <code>/</code> or <code>C:/</code>, 
	 * is considered absolute.  UNC paths are always absolute.
	 * </p>
	 *
	 * @return <code>true</code> if this path is an absolute path,
	 *    and <code>false</code> otherwise
	 */
	public boolean isAbsolute();

	/**
	 * Returns whether this path has no segments and is not
	 * a root path.
	 *
	 * @return <code>true</code> if this path is empty,
	 *    and <code>false</code> otherwise
	 */
	public boolean isEmpty();

	/**
	 * Returns whether this path is a prefix of the given path.
	 * To be a prefix, this path's segments must
	 * appear in the argument path in the same order,
	 * and their device ids must match.
	 * <p>
	 * An empty path is a prefix of all paths with the same device; a root path is a prefix of 
	 * all absolute paths with the same device.
	 * </p>
	 * @param anotherPath the other path
	 * @return <code>true</code> if this path is a prefix of the given path,
	 *    and <code>false</code> otherwise
	 */
	public boolean isPrefixOf(IPath anotherPath);

	/**
	 * Returns whether this path is a root path.
	 * <p>
	 * The root path is the absolute non-UNC path with zero segments; 
	 * e.g., <code>/</code> or <code>C:/</code>.
	 * The separator is considered a leading separator, not a trailing one.
	 * </p>
	 *
	 * @return <code>true</code> if this path is a root path,
	 *    and <code>false</code> otherwise
	 */
	public boolean isRoot();

	/**
	 * Returns a boolean value indicating whether or not this path
	 * is considered to be in UNC form. Return false if this path
	 * has a device set or if the first 2 characters of the path string
	 * are not <code>Path.SEPARATOR</code>.
	 * 
	 * @return boolean indicating if this path is UNC
	 */
	public boolean isUNC();

	/**
	 * Returns whether the given string is syntactically correct as
	 * a path.  The device id is the prefix up to and including the device
	 * separator for the local file system; the path proper is everything to 
	 * the right of it, or the entire string if there is no device separator. 
	 * When the platform location is a file system with no meaningful device
	 * separator, the entire string is treated as the path proper.
	 * The device id is not checked for validity; the path proper is correct 
	 * if each of the segments in its canonicalized form is valid.
	 *
	 * @param path the path to check
	 * @return <code>true</code> if the given string is a valid path,
	 *    and <code>false</code> otherwise
	 * @see #isValidSegment(String)
	 */
	public boolean isValidPath(String path);

	/**
	 * Returns whether the given string is valid as a segment in 
	 * a path. The rules for valid segments are as follows:
	 * <ul>
	 * <li> the empty string is not valid
	 * <li> any string containing the slash character ('/') is not valid
	 * <li>any string containing segment or device separator characters
	 * on the local file system, such as the backslash ('\') and colon (':')
	 * on some file systems.
	 * </ul>
	 *
	 * @param segment the path segment to check
	 * @return <code>true</code> if the given path segment is valid,
	 *    and <code>false</code> otherwise
	 */
	public boolean isValidSegment(String segment);

	/**
	 * Returns the last segment of this path, or
	 * <code>null</code> if it does not have any segments.
	 *
	 * @return the last segment of this path, or <code>null</code> 
	 */
	public String lastSegment();

	/**
	 * Returns an absolute path with the segments and device id of this path.
	 * Absolute paths start with a path separator. If this path is absolute, 
	 * it is simply returned.
	 *
	 * @return the new path
	 */
	public IPath makeAbsolute();

	/**
	 * Returns a relative path with the segments and device id of this path.
	 * Absolute paths start with a path separator and relative paths do not. 
	 * If this path is relative, it is simply returned.
	 *
	 * @return the new path
	 */
	public IPath makeRelative();

	/**
	 * Returns a path equivalent to this path, but relative to the given base path if possible. 
	 * <p>
	 * The path is only made relative if the base path if both paths have the same device 
	 * and have a non-zero length common prefix. If the paths have different devices,
	 * or no common prefix, then this path is simply returned. If the path is successfully
	 * made relative, then appending the returned path to the base will always produce
	 * a path equal to this path.
	 * </p>
	 * @param base The base path to make this path relative to
	 * @return A path relative to the base path, or this path if it could
	 * not be made relative to the given base
	 * @since org.eclipse.equinox.common 3.5
	 */
	public IPath makeRelativeTo(IPath base);

	/**
	 * Return a new path which is the equivalent of this path converted to UNC
	 * form (if the given boolean is true) or this path not as a UNC path (if the given
	 * boolean is false). If UNC, the returned path will not have a device and the 
	 * first 2 characters of the path string will be <code>Path.SEPARATOR</code>. If not UNC, the
	 * first 2 characters of the returned path string will not be <code>Path.SEPARATOR</code>.
	 * 
	 * @param toUNC true if converting to UNC, false otherwise
	 * @return the new path, either in UNC form or not depending on the boolean parameter
	 */
	public IPath makeUNC(boolean toUNC);

	/**
	 * Returns a count of the number of segments which match in
	 * this path and the given path (device ids are ignored),
	 * comparing in increasing segment number order.
	 *
	 * @param anotherPath the other path
	 * @return the number of matching segments
	 */
	public int matchingFirstSegments(IPath anotherPath);

	/**
	 * Returns a new path which is the same as this path but with
	 * the file extension removed.  If this path does not have an 
	 * extension, this path is returned.
	 * <p>
	 * The file extension portion is defined as the string
	 * following the last period (".") character in the last segment.
	 * If there is no period in the last segment, the path has no
	 * file extension portion. If the last segment ends in a period,
	 * the file extension portion is the empty string.
	 * </p>
	 *
	 * @return the new path
	 */
	public IPath removeFileExtension();

	/**
	 * Returns a copy of this path with the given number of segments
	 * removed from the beginning. The device id is preserved. 
	 * The number must be greater or equal zero.
	 * If the count is zero, this path is returned.
	 * The resulting path will always be a relative path with respect
	 * to this path.  If the number equals or exceeds the number
	 * of segments in this path, an empty relative path is returned.
	 *
	 * @param count the number of segments to remove
	 * @return the new path
	 */
	public IPath removeFirstSegments(int count);

	/**
	 * Returns a copy of this path with the given number of segments
	 * removed from the end. The device id is preserved.
	 * The number must be greater or equal zero.
	 * If the count is zero, this path is returned.
	 * <p>
	 * If this path has a trailing separator, it will still
	 * have a trailing separator after the last segments are removed
	 * (assuming there are some segments left).  If there is no
	 * trailing separator, the result will not have a trailing
	 * separator.
	 * If the number equals or exceeds the number
	 * of segments in this path, a path with no segments is returned.
	 * </p>
	 *
	 * @param count the number of segments to remove
	 * @return the new path
	 */
	public IPath removeLastSegments(int count);

	/**
	 * Returns a path with the same segments as this path
	 * but with a trailing separator removed.
	 * Does nothing if this path does not have at least one segment.
	 * The device id is preserved.
	 * <p>
	 * If this path does not have a trailing separator,
	 * this path is returned.
	 * </p>
	 *
	 * @return the new path
	 * @see #addTrailingSeparator()
	 * @see #hasTrailingSeparator()
	 */
	public IPath removeTrailingSeparator();

	/**
	 * Returns the specified segment of this path, or
	 * <code>null</code> if the path does not have such a segment.
	 *
	 * @param index the 0-based segment index
	 * @return the specified segment, or <code>null</code> 
	 */
	public String segment(int index);

	/**
	 * Returns the number of segments in this path.
	 * <p> 
	 * Note that both root and empty paths have 0 segments.
	 * </p>
	 *
	 * @return the number of segments
	 */
	public int segmentCount();

	/**
	 * Returns the segments in this path in order.
	 *
	 * @return an array of string segments
	 */
	public String[] segments();

	/**
	 * Returns a new path which is the same as this path but with 
	 * the given device id.  The device id must end with a ":".
	 * A device independent path is obtained by passing <code>null</code>.
	 * <p>
	 * For example, "C:" and "Server/Volume:" are typical device ids.
	 * </p>
	 *
	 * @param device the device id or <code>null</code>
	 * @return a new path
	 * @see #getDevice()
	 */
	public IPath setDevice(String device);

	/**
	 * Returns a <code>java.io.File</code> corresponding to this path.
	 *
	 * @return the file corresponding to this path
	 */
	public java.io.File toFile();

	/**
	 * Returns a string representation of this path which uses the
	 * platform-dependent path separator defined by <code>java.io.File</code>.
	 * This method is like <code>toString()</code> except that the
	 * latter always uses the same separator (<code>/</code>) regardless of platform.
	 * <p>
	 * This string is suitable for passing to <code>java.io.File(String)</code>.
	 * </p>
	 *
	 * @return a platform-dependent string representation of this path
	 */
	public String toOSString();

	/**
	 * Returns a platform-neutral string representation of this path. The 
	 * format is not specified, except that the resulting string can be 
	 * passed back to the <code>Path#fromPortableString(String)</code> 
	 * constructor to produce the exact same path on any platform.
	 * <p>
	 * This string is suitable for passing to <code>Path#fromPortableString(String)</code>.
	 * </p>
	 *
	 * @return a platform-neutral string representation of this path
	 * @see Path#fromPortableString(String)
	 * @since 3.1
	 */
	public String toPortableString();

	/**
	 * Returns a string representation of this path, including its
	 * device id.  The same separator, "/", is used on all platforms.
	 * <p>
	 * Example result strings (without and with device id):
	 * <pre>
	 * "/foo/bar.txt"
	 * "bar.txt"
	 * "/foo/"
	 * "foo/"
	 * ""
	 * "/"
	 * "C:/foo/bar.txt"
	 * "C:bar.txt"
	 * "C:/foo/"
	 * "C:foo/"
	 * "C:"
	 * "C:/"
	 * </pre>
	 * This string is suitable for passing to <code>Path(String)</code>.
	 * </p>
	 *
	 * @return a string representation of this path
	 * @see Path
	 */
	public String toString();

	/**
	 * Returns a copy of this path truncated after the
	 * given number of segments. The number must not be negative.
	 * The device id is preserved.
	 * <p>
	 * If this path has a trailing separator, the result will too
	 * (assuming there are some segments left). If there is no
	 * trailing separator, the result will not have a trailing
	 * separator.
	 * Copying up to segment zero simply means making an copy with
	 * no path segments.
	 * </p>
	 *
	 * @param count the segment number at which to truncate the path
	 * @return the new path
	 */
	public IPath uptoSegment(int count);
}
