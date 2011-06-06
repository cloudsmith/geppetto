---
layout: post
title: What does "Expected to comply with String rule" mean?
---
This means that a single quoted string does not consist of a valid sequence of characters.
If you believe Geppetto is in error, please open an issue as it should not really be possible to construct an illegal string with the editor.
In case of a single quoted string, this error is shown when there is an unescaped single quote inside of the string as in these example:

    'Chunga's revenge'
    'Chunga\\'s revenge'

The error message is not very helpful, and has been changed to: "Contains illegal character(s). Probably an unescaped single quote." in version 1.0.1.
