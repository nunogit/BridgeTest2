#!/bin/bash

if [ -z "${CONFIG_FILE}" ]
then
      config="/data/gdb.config";
else
      config="${CONFIG_FILE}";
fi


if [ -z "${MODE}" ]
then
   echo $config
   echo "${ID_LIST}"
   java -cp "*" bridgedbfacade.BridgeDbFacade $config ${ID_LIST}
else
   echo "try opening http://127.0.0.1:8080/xrefs/1053_at as an example or http://127.0.0.1:8080/swagger-ui.html"
   java -cp "*" -jar BridgeDbFacadeWS.jar
fi
