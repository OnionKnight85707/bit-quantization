#!/usr/bin/env bash


name="nginx"
[[ -n "`docker ps -f name=$name | grep $name`" ]] && echo "$name is running!" && exit -1

export curDir="`dirname $0`"
export fullDir="`pwd`/$curDir"

docker run --restart always --name nginx -p 80:80 -p 443:443 \
 -v /usr/local/app/manager-nginx/nginx.conf:/etc/nginx/nginx.conf \
 -v /usr/local/app/manager-nginx/conf.d:/etc/nginx/conf.d \
 -v /usr/local/app/manager-nginx/html:/usr/share/nginx/html \
 -v /etc/localtime:/etc/localtime:ro \
 -d nginx
