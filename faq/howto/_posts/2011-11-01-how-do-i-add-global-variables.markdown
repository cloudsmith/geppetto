---
layout: post
title: How do I make global variables known to Geppetto?
---
Starting with Geppetto 2.1, variables are validated and warnings will be issued for variables that Geppetto can not find.
The reccomendation is to use global references, i.e. use $::operatingsystem instead of $operatingsystem.
If you for some reason do not want to make these changes (or can't), you may want to teach Geppetto about the existence
of such variables.

What you need is to create a file with '.pptp' extension and place it in Geppetto's special project for "target platform".
You can do this in Geppetto by:

* Use the Open View > Navigator
* Open the project .org_cloudsmith_geppetto_pptp_target
* Create a new file called something like "myvariables.pptp"
* Enter the text below

     <?xml version="1.0" encoding="ASCII"?>
       <pptp:PuppetDistribution xmi:version="2.0"
         xmlns:xmi="http://www.omg.org/XMI"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:pptp="http://www.cloudsmith.org/geppetto/1.0.0/PPTP"
         description="Adds Global vars"
         version=""
         label="puppet ">
          <contents xsi:type="pptp:TPVariable"
            name="myvar1"
            documentation="This is variable 1"/>
          <contents xsi:type="pptp:TPVariable"
            name="myvar2"
            documentation="This is variable 2"/>
       </pptp:PuppetDistributiom>

Add as many variables as you need.

If you have variables in a custom name-space, you can add that too by wrapping the variables in a "NameSpace" entry like so:

     <contents xsi:type="pptp:NameSpace" name="myNameSpace" reserved="true">
         <contents xsi:type="pptp:TPVariable"
          name="myName"
          documentation="myNameSpace::myName is a..."/>
     </contents>

After having added the file, you may need to perform a "Build Clean" to make it take effect in files where errors/warnings have already been reported.

