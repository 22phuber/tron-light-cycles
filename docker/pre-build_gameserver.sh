#!/usr/bin/env bash
set -e
set -o pipefail

# Change into script folder
cd "$(dirname "${BASH_SOURCE[0]}")" || exit 1

# VARS
declare -a gameserverFiles=(
    "../gameserver"
)

declare -r gameserverTargetFolder="gameserver/files"

# Clean up
rm -rvf "${gameserverTargetFolder:?}" && mkdir -p "${gameserverTargetFolder}"

# Copy node backend files
for object in "${gameserverFiles[@]}"; do
    if [[ -d "${object}" ]]; then
        cp -r "${object}" "${gameserverTargetFolder}"
    fi
    if [[ -f "${object}" ]]; then
        cp -v "${object}" "${gameserverTargetFolder}"
    fi
done
