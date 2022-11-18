#!/bin/bash
i=1;
while sleep 1;
do 
    echo $i >> monitor.txt;
    kubectl get hpa >> monitor.txt;
    kubectl top pods >> monitor.txt;
    i=$((i + 1))
done
