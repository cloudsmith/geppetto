---
layout: post
title: Update stops with 'out of PermGenSpace' error. How can I fix this?
---
According to [eclipse issue 92250](https://bugs.eclipse.org/bugs/show_bug.cgi?id=92250) this is caused by certain java runtimes using too much of the
perm-gen memory space. 

The workaround is to simply increase the amount of available perm-gen space.

To do this, find the file called `geppetto.ini` and add the
following line after the line containing the text `-vmargs`:

    -XX:MaxPermSize=128m

On OSX you find the `geppetto.ini` file inside the `Geppetto.app`. Using the
command line it is trivial to find the file inside the app directory, but if
you want to do this from within finder, you need to do the following:

- Right click on the Geppetto app icon
- Select `Show Pacakge Content`
- Navigate to `Contents` > `MacOS`
- Right click on `geppetto.ini` and select `Open With` (and select TextEdit).
 
