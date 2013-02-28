forge-publisher
===============

The GitHub Publisher for Puppet Forge modules

The publisher is in the form of a Maven plug-in that in turn executes code from the [Geppetto](http://cloudsmith.github.com/geppetto/) code-base. The plug-in can execute two goals, ___validate___ and ___publish___.

## General Operation

The plug-in will scan a _modulesRoot_ directory for files named _Modulefile_ or _metadata.json_. Any directory found that contains such a file with be considered to be a module and will participate in the validation. A check is made whether a module is already published or to avoid that the same version is published twice. In essence, running _validate_ and _publish_ on a code base where no module versions have changed will not cause any attempts to publish. All modules will still be validated though.

## The _validate_ goal

Performs validation_ of the Module. Two types of validations are available

### Geppetto Validation
Geppetto can find a lot of potential problems in your code such as:

* Circular Module Dependency
* Interpolated hyphen without surrounding {}
* Strings containing "false" or "true"
* Missing 'default' in selector
* Assignment to $string

It can also help you find stylistic problems such as:

* Case statement where a 'default' is not last
* Selector expression where a 'default' is not last
* Strings that do not require double quoting
* Strings containing a single interpolation
* Interpolated variables without braces
* Unquoted resource titles
* Comments using /* */
* Right to left relationships using <- or <~
* Resource property ensure is not stated first

In addition to this, Geppetto will also resolve all cross references and report any problems it will find in doing that. A special _checkReferences_ flag will tell the validator to resolve and install all dependent modules before this validation takes place.

The geppetto validation can be enabled using the boolean parameter _enableGeppettoValiation_. A future enhancement will add more detailed control over the options.

### Puppet Lint Validation

The validator is also capable of calling the puppet-lint program to perform additional validations. The puppet-lint program must be installed on the machine in order to to this. Unlike the Geppetto based validation, the puppet-lint program is not embedded in the plug-in.

The puppet-lint validation can be controlled by using the parameter _puppetLintOptions_. Look at the enum _Option_ [in this source](https://github.com/cloudsmith/geppetto/blob/master/org.cloudsmith.geppetto.puppetlint/src/org/cloudsmith/geppetto/puppetlint/PuppetLintRunner.java) for a complete list of options.

The puppet-lint validation can be enabled using the boolean parameter _enablePuppetLintValidation_.

## The _publish_ goal

This goal will perform the actual act of building and publishing the module(s) to the Puppet Forge. The Geppetto Module builder which is responsible for creating the metadata.json file with check-sums etc. will be called when no metadata.json file is found in a module. As a final step, a gzipped tar-ball is created for each module and then uploaded to the forge using the credentials stated for the publisherLogin and publisherPassword parameters.