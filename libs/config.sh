#!/bin/bash
LIB_NAMES=(blinkid-core blinkid-ux)
LIB_VERSIONS=()

for i in "${!LIB_NAMES[@]}"; do
    libName=${LIB_NAMES[$i]}
    LIB_VERSIONS[$i]=$(grep --max-count=1 '<version>' pom-$libName.xml | cut -d ">" -f 2 | cut -d '<' -f 1)
    echo $libName version: ${LIB_VERSIONS[$i]}
done