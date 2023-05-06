FROM docker.nexus.aetnadigital.net/hccisvc/ssl-support:1.3.0 AS ssl-support
FROM docker.nexus.aetnadigital.net/hccisvc/newrelic-java-agent:7.6.0 AS newrelic-agent
FROM docker.nexus.aetnadigital.net/hccisvc/hc-opentelemetry-java-instrumentation:1.13.1 AS otel-agent
FROM docker.nexus.aetnadigital.net/cvs-distroless/java:11-distroless

ARG jarPath
ARG jarName
ENV jarName=$jarName

ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
ENV _JAVA_OPTIONS="-Djavax.net.ssl.trustStore=/home/nonroot/app/keystore.jks"

COPY --from=ssl-support --chown="nonroot:nonroot" /app/rds.jks /home/nonroot/app/keystore.jks
COPY --from=ssl-support --chown="nonroot:nonroot" /app/rds.pem /home/nonroot/app/rds.pem
COPY --from=otel-agent --chown="nonroot:nonroot" /app/opentelemetry-auto.jar /home/nonroot/app/opentelemetry-auto.jar
COPY --from=newrelic-agent --chown="nonroot:nonroot" /usr/local/newrelic/ /home/nonroot/app/
COPY --chown="nonroot:nonroot" $jarPath/$jarName /home/nonroot/app/app.jar

CMD ["java", "-javaagent:/home/nonroot/app/opentelemetry-auto.jar", "-javaagent:/home/nonroot/app/newrelic-agent.jar", "-jar", "/home/nonroot/app/app.jar" ]

HEALTHCHECK NONE