#!/usr/bin/env bash

name="rabbitmq-bitpay"
[[ -n "`docker ps -f name=$name | grep $name`" ]] && echo "$name is running!" && exit -1

export curDir="`dirname $0`"
export fullDir="`pwd`/$curDir"

#docker run --name $name -v $fullDir/rabbitmq/data:/data -v $fullDir/zookeeper/datalog:/datalog --restart always -p 2181:2181 -d zookeeper

docker run -d --hostname rabbit -p 5672:5672 -v $fullDir/rabbitmq/data:/data --name rabbit -e RABBITMQ_DEFAULT_USER=root -e RABBITMQ_DEFAULT_PASS=Xiaoming58 -e RABBITMQ_DEFAULT_VHOST=127.0.0.1 rabbitmq:3-management

#docker run --name 'zookeeper' -v /usr/local/app/zookeeper/data:/data -v /usr/local/app/zookeeper/datalog:/datalog --restart always -p 2181:2181 -d zookeeper

