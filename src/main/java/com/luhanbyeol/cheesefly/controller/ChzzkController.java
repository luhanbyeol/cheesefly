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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/chzzk")
public class ChzzkController {

    @Autowired
    private Chzzk chzzk;

    @Value("${chzzk.channel.id}")
    private String channelId;

    @PostMapping(path = "/chat", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> chat(@RequestBody ChatPostVO chatPostVO) throws Exception {
        if (chatPostVO == null || !StringUtils.hasLength(chatPostVO.getMessage())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            CountDownLatch latch = new CountDownLatch(1);

            ChzzkChat chzzkChat = chzzk.chat();
            chzzkChat.connectFromChannelId(channelId);
            chzzkChat.addListener(new ChatEventListener() {
                @Override
                public void onConnect() {
                    chzzkChat.sendChat(chatPostVO.getMessage());

                    latch.countDown();
                }
            });

            latch.await(5, TimeUnit.SECONDS);

            chzzkChat.close();
        } catch (IllegalStateException ignored) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }

}
