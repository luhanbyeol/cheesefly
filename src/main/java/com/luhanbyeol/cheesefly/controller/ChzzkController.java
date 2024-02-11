package com.luhanbyeol.cheesefly.controller;

import com.luhanbyeol.cheesefly.vo.ChatPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.chat.ChatEventListener;
import xyz.r2turntrue.chzzk4j.chat.ChzzkChat;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

@Controller
@RequestMapping("/chzzk")
public class ChzzkController {

    @Autowired
    private Chzzk chzzk;

    private ChzzkChat chzzkChat;

    @Value("${chzzk.channel.id}")
    private String channelId;

    private LinkedBlockingQueue<String> queue;

    @PostConstruct
    public void postConstruct() throws IOException {
        queue = new LinkedBlockingQueue<>();

        chzzkChat = chzzk.chat();
        chzzkChat.connectFromChannelId(channelId);
        chzzkChat.addListener(new ChatEventListener() {
            @Override
            public void onConnect() {
                boolean keepAlive = true;
                while (keepAlive) {
                    try {
                        String message = queue.take();
                        chzzkChat.sendChat(message);
                    } catch (InterruptedException e) {
                        keepAlive = false;
                    }
                }
            }
        });
    }

    @PreDestroy
    public void preDestroy() {
        try {
            chzzkChat.close();
        } catch (IllegalStateException e) {

        }
    }

    @PostMapping(path = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> chat(@RequestBody ChatPostVO chatPostVO) throws Exception {
        if (chatPostVO == null || !StringUtils.hasLength(chatPostVO.getMessage())) {
            return ResponseEntity.badRequest().build();
        }

        queue.put(chatPostVO.getMessage());

        return ResponseEntity.ok().build();
    }

}
