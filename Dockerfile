FROM adoptopenjdk/maven-openjdk8

ENV TMP_DIR_BDB /tmp/bridgedb
ENV TMP_DIR_FAC /tmp/bdbfac
WORKDIR $TMP_DIR_BDB

# caching dependencies - this only runs if pom.xml changes
#COPY pom.xml .

RUN apt-get update
RUN apt-get -y install ant
RUN apt-get -y install git-core

# buld process
COPY  . ./
#RUN ant build
RUN mvn install

#WORKDIR $TMP_DIR_FAC
#RUN git clone https://github.com/nunogit/BridgeDbFacade.git .
#RUN ls -lhisa 
#RUN mvn install 

#RUN ls -lhisa
#RUN du . -h
#WORKDIR org.bridgedb.ws.server
#RUN mvn clean install

#RUN mvn verify clean --fail-never


#RUN mvn clean install

#FROM tomcat:8.5.35-jre8
#COPY --from=0 /tmp/target/*.war $CATALINA_HOME/webapps/data2services.war
#EXPOSE 8080
#CMD ["catalina.sh", "run"]
