#!/usr/bin/env bash
set -e
set -o pipefail

# Change directory into script folder
cd "$(dirname "${BASH_SOURCE[0]}")" || exit 1

### VARIABLES ###
# BACKEND: node
declare -a nodeBackendFiles=(
    "../backend/app"
    "../backend/package.json"
    "../backend/package-lock.json"
    "../backend/server.js"
)
declare -r nodeTargetFolder="node/files"
# FRONTEND: nginx (statis files)
declare -r nginxFrontendFiles="../frontend/tron_ux"
declare -r nginxTargetFolder="nginx/app"

### FUNCTIONS ###
# remove unneeded files (git, .DS_Store, tmp files etc.)
function removeClutter() {
    declare -a folders=("$@")
    # clutter files
    declare -a clutter=(".DS_Store" ".git")

    for folder in "${folders[@]}"; do
        if [[ -d "${folder}" ]]; then
            for object in "${clutter[@]}"; do
                find "${folder}" -iname "${object}" -exec rm -rf {} +
            done
        else
            echo "${FUNCNAME[0]} WARN: \"${folder}\" is NOT a folder!"
        fi
    done
}

# clean up leftovers
function cleanup () {
    for targetFolder in "${nodeTargetFolder}" "${nginxTargetFolder}"; do
        echo "Clean up: \"${targetFolder}\""
        rm -rvf "${targetFolder:?}" && mkdir -p "${targetFolder}"
    done
}

### MAIN ###
# run cleanup
cleanup

### PRE-BUILD ###
# Pre-Build Frontend (tron_ux)
echo "Building tron_ux Frontend: "
(
    cd "${nginxFrontendFiles}" && \
    npm run build && \
    cd "$(dirname "${BASH_SOURCE[0]}")"
) || exit 1

### COPY FILES ###
# BACKEND: node
echo "Copy NODE Backend ..."
for object in "${nodeBackendFiles[@]}"; do
    if [[ -d "${object}" ]]; then
        cp -rv "${object}" "${nodeTargetFolder}"
    fi
    if [[ -f "${object}" ]]; then
        cp -v "${object}" "${nodeTargetFolder}"
    fi
done
# FRONTEND: nginx (statis files)
echo "Copy builded tron_ux Frontend ..."
if [[ -d "${nginxFrontendFiles}/build" ]]; then
    cp -rv "${nginxFrontendFiles}/build" "${nginxTargetFolder}"
fi


removeClutter "${nodeTargetFolder}" "${nginxTargetFolder}"
