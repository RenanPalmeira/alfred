# alfred-nightwatch

Helpful to check if your public services are respond

## Configuration 

- create a `application.conf` in src/main/resources/ 

Follow a example of this file:

```
http {
    port = 8080
    port = ${?HTTP_PORT}
}

bot {
    token = "YOUR BOT TOKEN HERE"
    chatId = "The chat id to receive messages here (e.g a private/public channel of Telegram)"
}

routes {
    services = [
        "YourServiceHere",
        "YourOtherServiceHere",
    ]
}
```

## Running

`sbt assembly`

`java -jar java -jar target/scala-2.12/nightwatch-0.1.0-SNAPSHOT.jar`
