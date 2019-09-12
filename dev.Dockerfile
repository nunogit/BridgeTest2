FROM adoptopenjdk/maven-openjdk8

ENV TMP_DIR_BDB /tmp/bridgedb
ENV TMP_DIR_FAC /tmp/bdbfac
WORKDIR $TMP_DIR_BDB

RUN apt-get update
RUN apt-get -y install ant
RUN apt-get -y install git-core

# buld process
COPY  . ./
RUN ant build


WORKDIR $TMP_DIR_FAC

#new machine
FROM tomcat:8.5.35-jre8

ENV TMP_DIR_BDB /tmp/bridgedb
ENV TMP_DIR_FAC /tmp/bdbfac
ENV DIR_BDB /bridgedb/

COPY --from=0 $TMP_DIR_BDB/*.sh $DIR_BDB
COPY --from=0 $TMP_DIR_BDB/*.jar $DIR_BDB
COPY --from=0 $TMP_DIR_BDB/dist/*.jar $DIR_BDB

WORKDIR $DIR_BDB

#prepare Bridge files
RUN mkdir /data/

#this folder needs to added with the needed bridged files and gdb.config
COPY config/ /data/
RUN chmod +x startService.sh
RUN ls $DIR_BDB

EXPOSE 8080
CMD ["./startService.sh", "/data/gdb.ini"]

#CMD ["./startService.sh"]

#CMD ["catalina.sh", "run"]
