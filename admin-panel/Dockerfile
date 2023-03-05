FROM openjdk:17

VOLUME /resources/images/

COPY target/shop-admin-panel.jar shop-admin-panel.jar

ENTRYPOINT ["java","-jar","/shop-admin-panel.jar"]

EXPOSE 8080
