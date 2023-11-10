# John-Cricket-Coding-Challenge-24---Realtime-Chat-Client-and-Server

Realtime client-server chat system that works through the commandline.

Built throught the java.net package, ServerSocket and Sockets.

# ToDo
- Transition to using the __java.nio__ for non-blocking io and for scalabity since the current implementation creates a Thread for each client connection. Using the Selectors which will handle multiple connectios will be more scalable.

# To run the server
```sh
make server
```

# To run the client
```sh
make client
```

