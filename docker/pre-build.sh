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
# GAMESERVER:
declare -r gameserverFiles="../gameserver"
declare -r gameserverTargetFolder="gameserver/files"

### FUNCTIONS ###
# remove unneeded files (git, .DS_Store, tmp files etc.)
function removeClutter() {
    declare -a folders=("$@")
    # clutter files
    declare -a clutter=(".DS_Store" ".gitkeep" ".gitignore" ".git")

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
    declare -a folders=("$@")
    for folder in "${folders[@]}"; do
        if [[ -d "${folder}" ]]; then
            echo "Clean up: \"${folder}\""
            rm -rf "${folder:?}" && mkdir -p "${folder}"
        else
            echo "${FUNCNAME[0]} WARN: \"${folder}\" is NOT a folder!"
        fi
    done
}

### MAIN ###
# run cleanup
cleanup "${nodeTargetFolder}" "${nginxTargetFolder}" "${gameserverTargetFolder}"

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
echo "Copy Node Backend ..."
for object in "${nodeBackendFiles[@]}"; do
    if [[ -d "${object}" ]]; then
        cp -r "${object}" "${nodeTargetFolder}"
    fi
    if [[ -f "${object}" ]]; then
        cp "${object}" "${nodeTargetFolder}"
    fi
done
# FRONTEND: nginx (statis files)
echo "Copy builded tron_ux Frontend ..."
if [[ -d "${nginxFrontendFiles}/build" ]]; then
    cp -r "${nginxFrontendFiles}/build" "${nginxTargetFolder}"
fi
# GAMESERVER:
echo "Copy gameserver files ..."
for object in "${gameserverFiles[@]}"; do
    if [[ -d "${object}" ]]; then
        cp -r "${object}" "${gameserverTargetFolder}"
    fi
    if [[ -f "${object}" ]]; then
        cp "${object}" "${gameserverTargetFolder}"
    fi
done

# Remove unneeded files
removeClutter "${nodeTargetFolder}" "${nginxTargetFolder}" "${gameserverTargetFolder}"

echo "$0 DONE"
exit 0
