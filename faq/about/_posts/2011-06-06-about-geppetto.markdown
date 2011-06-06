---
title: What is Geppetto?
layout: post 
---
Geppetto is an integrated set of tools aimed to simplify the process of developing
and consuming Puppet modules and manifests.

The project's foundation is a model of the Puppet DSL, along with parsers, validators,
and formatters that translate between module and manifest models and their editable
representation. 

Geppetto generates and validates modules and manifests directly from editors, as well as streamline
module publication/consumption to/from the Puppet Forge (http://forge.puppetlabs.com/).

Editors provide syntax highlighting, content assistance, error tracing/debugging and similar
capabilities, as appropriate.

The project has three near-term objectives: (1) flatten the learning curve associated with using the Puppet DSL to create manifests, (2) support Puppet best practices for working with manifests in the form of modules, and (3) encourage sharing of modules within the community using the Puppet Forge. A longer-term objective will be to make it easier for a wider range of tools to interoperate with Puppet by using Geppetto's modeling foundation as a means of interchange; potential synergies with the new p(0) project (http://github.com/lak/pzero) will be explored for this purpose.

