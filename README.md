# Project
This project is to compare Spring MVC and WebFlux using a very low number of threads and making multiples requests at the same time from a client.

## Requirements
- Java 17
- Gradle 7.3

## How to run the test comparation
1. Start the spring webflux server
   - ```./gradlew :server-webflux:bootRun```
2. Start the spring mvc server
    - ```./gradlew :server-mvc:bootRun```
3. Run the client
   - ```./gradlew :client:run```

#### Client Output Example:
```
Response {
    source = http://localhost:8081,
    duration = 5 seconds,
    resultCount = 10,
    message [
        Request: webflux-0 - Thread: nioEventLoopGroup-3-2,
        Request: webflux-1 - Thread: nioEventLoopGroup-3-1,
        Request: webflux-2 - Thread: nioEventLoopGroup-3-1,
        Request: webflux-3 - Thread: nioEventLoopGroup-3-2,
        Request: webflux-4 - Thread: nioEventLoopGroup-3-1,
        Request: webflux-5 - Thread: nioEventLoopGroup-3-1,
        Request: webflux-6 - Thread: nioEventLoopGroup-3-2,
        Request: webflux-7 - Thread: nioEventLoopGroup-3-2,
        Request: webflux-8 - Thread: nioEventLoopGroup-3-2,
        Request: webflux-9 - Thread: nioEventLoopGroup-3-1
    ]
}

Response {
    source = http://localhost:8080,
    duration = 25 seconds,
    resultCount = 10,
    message [
        Request: mvc-0 - Thread: http-nio-8080-exec-2,
        Request: mvc-1 - Thread: http-nio-8080-exec-2,
        Request: mvc-2 - Thread: http-nio-8080-exec-1,
        Request: mvc-3 - Thread: http-nio-8080-exec-1,
        Request: mvc-4 - Thread: http-nio-8080-exec-1,
        Request: mvc-5 - Thread: http-nio-8080-exec-2,
        Request: mvc-6 - Thread: http-nio-8080-exec-2,
        Request: mvc-7 - Thread: http-nio-8080-exec-1,
        Request: mvc-8 - Thread: http-nio-8080-exec-1,
        Request: mvc-9 - Thread: http-nio-8080-exec-2
    ]
}
```

### Change the number of threads on the Spring Applications
- Spring MVC
  - application.properties
    - ```server.tomcat.threads.max=2```
- Spring WebFlux
  - application.properties
    - ```server.netty.threads.max=2```