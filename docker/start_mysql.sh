#!/usr/bin/env bash

name="mysql-bitpay"
[[ -n "`docker ps -f name=$name | grep $name`" ]] && echo "$name is running!" && exit -1
export curDir="`dirname $0`"
export fullDir="`pwd`/$curDir"

#sqlDir="`dirname $0`/sql"
sqlDir="$fullDir/sql"
echo "Sql dir: $sqlDir"
echo "Should init sql scripts: `ls $sqlDir`"
docker run --restart always --name $name -v $sqlDir:/docker-entrypoint-initdb.d -v $fullDir/mysql/data:/var/lib/mysql -e LANG=C.UTF-8 -e TZ='Asia/Shanghai' -e MYSQL_ROOT_PASSWORD=Xiaoming58 -e MYSQL_USER=hktd -e MYSQL_PASSWORD=hktd_123456 -e MYSQL_DATABASE=bte  -p 3306:3306 -d mysql:8-oracle --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci --default-authentication-plugin=mysql_native_password
#sql="`cat ddl.sql`"
#docker exec  mysql $sql
