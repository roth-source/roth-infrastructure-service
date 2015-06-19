#!/bin/bash

if [ -z "$1" ];
then
	echo "specify ip";
else
	mvn clean package;
	scp target/roth-infrastructure-1.0.0.war root@$1:/opt/jetty/base/war/;
	ssh root@$1 "systemctl restart jetty";
fi