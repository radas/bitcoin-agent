#!/bin/bash

# path to pid file
PID_FILE=/tmp/bitcoin_agent.pid

# pid file exists?
if [ -f ${PID_FILE} ] ; then

    # read PID stored there
    OTHER_PID=$(cat ${PID_FILE})

    # exists such process?
    if kill -0 ${OTHER_PID} &>/dev/null; then
        echo "[$(date)] Bitcoin Agent $OTHER_PID already running"
        exit 1
    fi
fi

# write fresh pid file
echo $$ > ${PID_FILE}

# remove the file on exit
trap "rm -f ${PID_FILE}" INT TERM EXIT



$JAVA_HOME/bin/java -server -Xmx64m -jar bitcoin-agent.jar
