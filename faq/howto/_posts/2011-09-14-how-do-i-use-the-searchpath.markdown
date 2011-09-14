---
layout: post
title: How do I use the search path and environment preferences?
---
Since Geppetto 2.0 it is posible to speecify a search path, and environment name in preferences.
These preferences can be set globally, but also per project (overriding the default).

The search path adds an extra level of visibility constraints, and prioritizes which element to choose should there be several with the same name.

By deault, the path is:
     lib/*:environments/$environment/*:manifests/*:modules/*

Implicitly, the puppet environment is always searched before the search path is consulted.
The search path conists of ':' separated entries, where each entry is a path to a directory relative to a project root.
If the path ends with '/*' content in all subdirectories will become visible. If it does not end with '/*' only the direct contents of the given
directory is made visible.

Note that this path is not the same as the puppet modulepath, as it needs to speciy the directories where the content actually is (.pp and .rb files).

The variable $environment can be used in the path. It will expand to the value of the environment preference. (You can naturally spell out the
name if you want to). The intention is that a default path can be used for all/most projects, and only the environment preference needs to be set
per project.

If you set the search path to a single '*' you will get the same behaviour as in Geppetto 1.0.2 - i.e. everything is visible and there is no
defined search order.

You can not use the search path to point to other projects.
