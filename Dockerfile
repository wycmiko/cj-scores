FROM openjdk:8u151-jdk-alpine
VOLUME /tmp:/tmp
COPY ./web/target/web-0.0.1-SNAPSHOT.jar app.jar
RUN /bin/sh -c 'touch /app.jar'
ENV SERVICE_9001_TAGS traefik.tags=cj.com,traefik.frontend.rule=Host:platform.api.loongcent.com.cn;PathPrefix:/v1/scores,traefik.enable=true,traefik.frontend.entryPoints=http,traefik.frontend.passHostHeader=true
ENV SERVICE_9001_CHECK_HTTP /actuator/health
ENV SERVICE_9001_NAME cj-scores
ENV TZ=Asia/Shanghai
ENV LANG C.UTF-8
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 9001
EXPOSE 9091
ENTRYPOINT ["java","-jar","/app.jar"]