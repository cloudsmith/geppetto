#!/bin/bash
COMPACT_BUILD_ID="${BUILD_ID//[_-]}"
COMPACT_BUILD_ID="${COMPACT_BUILD_ID:0:12}"
echo
echo '# build for all platforms'
echo 'target.ws=*'
echo 'target.os=*'
echo 'target.arch=*'
echo
echo '# define the qualifier replacement strategy'
echo 'qualifier.replacement.*=generator:lastModified'
echo "generator.lastModified.format='v'yyyyMMdd-HHmm"
echo
echo '# Define the Build ID (added by Jenkins job definition)'
echo "build.id=${BUILD_TYPE}${COMPACT_BUILD_ID}"
echo
echo '# Define the timestamp to use for changed components (added by Jenkins job definition)'
echo "buckminster.build.timestamp=${BUILD_ID}"
echo
echo '# Define the target platform directory'
echo "tp.root=${WORKSPACE}/tp"
echo
echo '# Define the location of the checked out source'
echo "git.clones.root=${WORKSPACE}/git"
echo
echo '# Define the location of test reports'
echo "reports.root=${WORKSPACE}/reports"
echo
echo '# Prune target platfrom from optional JDT stuff'
echo 'pruned.product.build=true'

