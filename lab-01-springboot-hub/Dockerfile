# 建议生产使用，ref: http://blog.tenxcloud.com/?p=1894
#FROM fabric8/java-jboss-openjdk8-jdk
# FROM iron/java:1.8
FROM openjdk:8-jre-alpine

RUN mkdir /data
#RUN chmod 755 /home/agent

USER shuai

# COPY lab-01-springboot-hub-0.0.1-SNAPSHOT.jar /lab-01-springboot-hub.jar
ADD lab-01-springboot-hub-0.0.1-SNAPSHOT.jar /data/lab-01-springboot-hub.jar

#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","-Xmx512m","-Xms512m","/data/ghub-middleware-service.jar"]
ENTRYPOINT ["java","-jar","-Xmx512m","-Xms125m","/data/lab-01-springboot-hub.jar"]