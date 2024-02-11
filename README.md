# CheeseFly

[![Hits](https://hits.sh/github.com/luhanbyeol/cheesefly.svg)](https://hits.sh/github.com/luhanbyeol/cheesefly/)

CheeseFly enhances your streaming experience by sending Chzzk chats through HTTP requests, OBS shortcuts or MIDI!

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