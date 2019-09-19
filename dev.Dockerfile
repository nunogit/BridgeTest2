ARG tmp_dir_bdb=/tmp/bridgedb
ARG tmp_dir_fac=/tmp/bdbfac
ARG tmp_dir_facws=/tmp/bdbfacws
ARG dir_bdb=/bridgedb/

FROM openjdk:8 AS buildenv

ARG tmp_dir_bdb
ARG tmp_dir_fac
ARG tmp_dir_facws

#prepare build environment
RUN apt-get update; apt-get -y install ant; apt-get -y install git-core; apt-get -y install maven

# buld process
RUN mkdir ${tmp_dir_bdb}
WORKDIR  ${tmp_dir_bdb}
COPY . ./
RUN ls -lhisa
RUN mvn clean install
RUN ant build

RUN mkdir ${tmp_dir_fac}
WORKDIR ${tmp_dir_fac}
RUN git clone https://github.com/nunogit/BridgeDbFacade.git .
RUN mvn clean package install dependency:copy-dependencies

RUN mkdir ${tmp_dir_facws}
WORKDIR ${tmp_dir_facws}
RUN git clone https://github.com/nunogit/BridgeDbFacadeWS.git .
RUN  mvn clean package
RUN ls ${tmp_dir_facws}/target/
#RUN apt-get xxx

#new machine
FROM openjdk:8 AS runenv
#FROM tomcat:8.5.35-jre8

ARG tmp_dir_bdb
ARG tmp_dir_fac
ARG tmp_dir_facws
ARG dir_bdb

COPY --from=buildenv ${tmp_dir_bdb}/*.sh ${dir_bdb}
COPY --from=buildenv ${tmp_dir_bdb}/dist/*.jar ${dir_bdb}
COPY --from=buildenv ${tmp_dir_bdb}/target/*.jar ${dir_bdb}
COPY --from=buildenv ${tmp_dir_fac}/target/*.jar ${dir_bdb}
COPY --from=buildenv ${tmp_dir_facws}/target/BridgeDbFacadeWS-0.0.1-SNAPSHOT.jar ${dir_bdb}/BridgeDbFacadeWS.jar

WORKDIR ${dir_bdb}

#prepare Bridge files
RUN mkdir /data/

#this folder needs to added with the needed bridged files and gdb.config
COPY config/ /data/
RUN chmod +x startService.sh

EXPOSE 8080
CMD ["./startService.sh", "/data/gdb.ini"]

