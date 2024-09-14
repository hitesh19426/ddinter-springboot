FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/ddinter-0.0.1-SNAPSHOT.jar ddinter-springboot.jar
EXPOSE 27017

ENV MONGODB_URI=mongodb+srv://hitesh19426:Yaoj5LYhWuXKBFkr@cluster0.lmtgovu.mongodb.net/?retryWrites=true&w=majority
ENV MONGODB_DATABASE=ddinter_springboot
ENTRYPOINT ["java","-jar","/ddinter-springboot.jar"]