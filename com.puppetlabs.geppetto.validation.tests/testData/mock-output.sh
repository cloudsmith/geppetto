#!/bin/sh
echo "Mock output to stderr - starting"
cat 1>&2 <<EOF
Blah Blah Blah at /somehwere/foo.pp:10 on node nowhere
Blah Blah Blah with:10 number at /somehwere/bar.pp:20 on node nowhere
Could not parse Blah Blah Blah: unterminated string \"'foo
\" expected ' at (/somewhere/bar.pp:30 on node elsewhere
EOF
echo "Mock output to stderr - ending"
