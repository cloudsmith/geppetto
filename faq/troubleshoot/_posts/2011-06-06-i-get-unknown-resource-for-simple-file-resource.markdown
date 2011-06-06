---
layout: post
title: I get "Unknown resource for a imple file resource". What is wrong?
---
In the example below, the 'file' is flagged with an error marker.
Surely Geppetto should understand what 'file' is!

    file { 'foo': }

This can happen when the project that contains the manifest in question does not have the Puppet Nature set.
You can set this nature via the project's context menu.
The wizards, which create Puppet projects from with an empty module or from an existing 
module in the Forge, automatically set the Puppet Nature on creation, but if you create
a General project or import a project from from Git (or another repository), 
Geppetto does not assume that the Puppet code in the project should be linked.

Also see [Issue #85](https://github.com/cloudsmith/geppetto/issues/85)
