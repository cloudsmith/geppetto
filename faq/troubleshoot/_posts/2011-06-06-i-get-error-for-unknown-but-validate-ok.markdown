---
layout: post
title: I get an error for "unknown xxx", but when I vaöidate it is OK. What is wrong?
---
This happens when there are issues with the order in which things are built.
It is possible that when the build was executed for the file in question, the information about the 'unknown' was not yet available.
The most common cause of this problem is that there are circular dependencies between modules. Check if there are any such errors reported and fix those first.

