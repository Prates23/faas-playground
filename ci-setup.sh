#!/bin/bash

# This function checks if the service is in Running state
check_service_is_running() {
    local SERVICE_NAME=$1
    local STATE=$(docker service ps --format '{{json .CurrentState}}' $SERVICE_NAME)
    if [[ $STATE = \"Running* ]]; then
      echo 1
    else
      echo 0
    fi
} 

# This function waits for the service to become available.
# Retries for 10 times and 3 second interval (hard coded for now)
wait_for_service_to_start() {
    local n=1
    local max=10
    local delay=3

    local SERVICE_NAME=$1
    local SERVICE_IS_RUNNING=0
    while [  "$SERVICE_IS_RUNNING" -eq 0 ]; do
      if [[ $n -gt $max ]]; then
        echo "ERROR: Retried $(($n-1)) times but $SERVICE_NAME didn't start. Exiting" >&2
        exit 1
      fi
      SERVICE_IS_RUNNING=$(check_service_is_running $SERVICE_NAME)
      echo "Waiting for $SERVICE_NAME to start"
      n=$[$n+1]
      sleep $delay
    done
    echo "$SERVICE_NAME is Running"
}

# deploy the stack to swarm
#./deploy_stack.sh

# build the functions 
faas-cli build -f ./../hello-java.yml

# we can't deploy unless the gateway is ready so wait
wait_for_service_to_start func_gateway

# login on gateway
#echo -n <some_hash_secret> | faas-cli login --username=admin --password-stdin

# and then deploy
faas-cli deploy -f ./../hello-java.yml

# wait for functions to become ready for testing
wait_for_service_to_start hello-java
