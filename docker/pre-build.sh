#!/usr/bin/env bash
set -e
set -o pipefail

#
cd "$(dirname "${BASH_SOURCE[0]}")" || exit 1

# VARS
declare -a nodeBackendFiles=(
    "../backend/app"
    "../backend/package.json"
    "../backend/package-lock.json"
    "../backend/server.js"
)
declare -r nodeTargetFolder="node/files"

# Clean up
cleanUpTarget="${nodeTargetFolder}/"
rm -rf "${cleanUpTarget:=empty}"

# Copy node backend files
for object in "${nodeBackendFiles[@]}"; do
    if [[ -d "${object}" ]]; then
        cp -rv "${object}" "${nodeTargetFolder}"
    fi
    if [[ -f "${object}" ]]; then
        cp -v "${object}" "${nodeTargetFolder}"
    fi
done
