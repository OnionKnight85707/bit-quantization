#!/usr/bin/env bash
ifconfig |  grep -oE "inet\s+(address\s+){0,1}([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -oE "([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -v "127.0.0.1" | head -n 1 > ip.tmp
export HOST_IP=`cat ip.tmp`
echo "$HOST_IP"%
