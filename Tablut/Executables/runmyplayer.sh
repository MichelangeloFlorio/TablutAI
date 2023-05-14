#!/bin/bash

if [[ $#  -ne 3 ]] ; then
   echo "Need all arguments"
   echo "white or black as first parameter"
   echo "timeout as second parameter"
   echo "server ip as third parameter"
   echo "Example: $0 white 60 192.168.20.254"
   exit 1
fi

java -jar /home/tablut/Documents/TablutAI.jar "$1" "$2" "$3"
