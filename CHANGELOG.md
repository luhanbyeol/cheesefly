# 0.0.3 (21 Mar 2024)

## Dependency Updates

- Bump `chzzk4j` from 0.0.3 to 0.0.4 to resolve timeout exception

# 0.0.2 (12 Feb 2024)

## Bug fixes

- Potential `IllegalStateException` when sending chats

# 0.0.1 (11 Feb 2024)

## Requirements

- Java 17+

## Configuration

- `chzzk.nid.aut`: The value of `NID_AUT` cookie.
- `chzzk.nid.ses`: The value of `NID_SES` cookie.
- `chzzk.channel.id`: The channel id of Chzzk.

## Launch

```shell
$ java -jar cheesefly-0.0.1.jar --chzzk.nid.aut=... --chzzk.nid.ses=... --chzzk.channel.id=...
```

## Features

### Sending chat

- POST http://localhost:8929/chzzk/chat
  ```json
  {
    "message": "Hello, World!"
  }
  ```

## Packaging
```
$ mvn clean package
```