FROM mysql:8.0

ENV LANG=C.UTF-8

COPY files/charset.cnf /etc/mysql/conf.d/
COPY files/database/*.sql /tmp/

RUN cat /tmp/test_data.sql >> /tmp/tron-light-cycles.sql \
    && mv /tmp/tron-light-cycles.sql /docker-entrypoint-initdb.d/ \
    && rm -rf /tmp/test_data.sql
