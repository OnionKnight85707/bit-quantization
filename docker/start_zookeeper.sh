#!/usr/bin/env bash

name="zookeeper-bitpay"
[[ -n "`docker ps -f name=$name | grep $name`" ]] && echo "$name is running!" && exit -1

export curDir="`dirname $0`"
export fullDir="`pwd`/$curDir"

docker run --name $name -v $fullDir/zookeeper/data:/data -v $fullDir/zookeeper/datalog:/datalog --restart always -p 2181:2181 -d zookeeper

#docker run --name 'zookeeper' -v /usr/local/app/zookeeper/data:/data -v /usr/local/app/zookeeper/datalog:/datalog --restart always -p 2181:2181 -d zookeeper

