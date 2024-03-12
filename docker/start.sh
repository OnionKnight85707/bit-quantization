#!/usr/bin/env bash

export curDir="`dirname $0`"
export fullDir="`pwd`/$curDir"

echo "curDir: $curDir"
echo "fullDir: $fullDir"

chmod +x ${curDir}/*.sh
echo "starting activemq"
${curDir}/start_activemq.sh
echo "starting mysql"
${curDir}/start_mysql.sh
echo "starting zookeeper"
${curDir}/start_zookeeper.sh
echo "starting redis"
${curDir}/start_redis.sh

