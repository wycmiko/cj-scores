FROM openjdk:8u151-jdk-alpine
VOLUME /tmp:/tmp
COPY ./web/target/web-0.0.1-SNAPSHOT.jar app.jar
RUN /bin/sh -c 'touch /app.jar'
ENV SERVICE_TAGS traefik.tags=cj.com,traefik.frontend.rule=Host:platform.api.loongcent.com.cn;PathPrefix:/v1/push,traefik.enable=true,traefik.frontend.entryPoints=http,traefik.frontend.passHostHeader=true
ENV SERVICE_8089_CHECK_HTTP /health
ENV SERVICE_8089_NAME cj-push
ENV TZ=Asia/Shanghai
ENV LANG C.UTF-8
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
EXPOSE 8089
ENTRYPOINT ["java","-jar","/app.jar"]