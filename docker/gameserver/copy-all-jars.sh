# Change into script folder
cd "$(dirname "${BASH_SOURCE[0]}")" || exit 1


# Copy all needed jars into a targetFolder
declare -r targetFolder="tron-exec"

mkdir "${targetFolder}"

find . -name "*.jar" -exec cp {} ./"${targetFolder}" \;


# Get names of basenames of jars and concatenate
cd "${targetFolder}" || exit 1

arr=( * )

modules=""

for name in "${arr[@]}"; do
	if [ -z "$modules" ]; then
		modules+=$name
	else
		modules+=":${name}"
	fi
done


# Get command string

command="java --module-path ${modules} -m ch.tron.appmain/ch.tron.appmain.GameServer"


# Run command

cd "$(dirname "${BASH_SOURCE[0]}")" || exit 1

eval $command