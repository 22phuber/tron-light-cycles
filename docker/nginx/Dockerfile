FROM nginx:1.17-alpine

RUN rm -rf "/etc/nginx/conf.d/default.conf" && mkdir -p "/app"

COPY files "/etc/nginx/"
COPY app "/app"