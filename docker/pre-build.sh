#!/usr/bin/env bash
set -e
set -o pipefail

# Change into script folder
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
rm -rvf "${nodeTargetFolder:?}" && mkdir -p "${nodeTargetFolder}"

# Copy node backend files
for object in "${nodeBackendFiles[@]}"; do
    if [[ -d "${object}" ]]; then
        cp -rv "${object}" "${nodeTargetFolder}"
    fi
    if [[ -f "${object}" ]]; then
        cp -v "${object}" "${nodeTargetFolder}"
    fi
done
