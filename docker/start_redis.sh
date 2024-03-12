#!/usr/bin/env bash

name="redis-bitpay"
[[ -n "`docker ps -f name=$name | grep $name`" ]] && echo "$name is running!" && exit -1

export curDir="`dirname $0`"
export fullDir="`pwd`/$curDir"

docker run --name $name --restart always -v $fullDir/redis:/data -p 6379:6379 -d redis

