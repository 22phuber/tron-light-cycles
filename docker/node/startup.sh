#!/usr/bin/env sh
set -e

mysqlHost="mysql"

if [ "$1" = 'node' ]; then
    echo 'Wait for MySQL';
    while ! nc -zv ${mysqlHost} 3306; do
        sleep 2
        echo '...';
    done
fi

exec "${@}"
