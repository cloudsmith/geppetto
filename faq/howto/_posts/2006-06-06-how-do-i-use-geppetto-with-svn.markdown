---
layout: post
title: How do I use Geppetto with SVN?
---
Geppetto comes with built in SVN support.
One thing to remember after having imported a project from a SVN repository is to
turn on the Puppet Nature for the project, as this is required for Geppetto to know how to handle the manifest files and metadata in the project.
If you check-in the `.project` file into your repository, the next time you or anyone else checks out the project, the nature will be remembered.

For more help on using SVN, see the embedded help in Geppetto.
