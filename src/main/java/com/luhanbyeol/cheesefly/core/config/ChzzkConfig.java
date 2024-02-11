package com.luhanbyeol.cheesefly.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import xyz.r2turntrue.chzzk4j.Chzzk;
import xyz.r2turntrue.chzzk4j.ChzzkBuilder;

@Component
public class ChzzkConfig {

    @Value("${chzzk.nid.aut}")
    private String nidAut;

    @Value("${chzzk.nid.ses}")
    private String nidSes;

    @Value("${chzzk.channel.id}")
    private String channelId;

    @Bean
    public Chzzk chzzk() throws Exception {
        if (!StringUtils.hasLength(nidAut)) {
            throw new Exception("The 'chzzk.nid.aut' argument is not provided!");
        }

        if (!StringUtils.hasLength(nidSes)) {
            throw new Exception("The 'chzzk.nid.ses' argument is not provided!");
        }

        if (!StringUtils.hasLength(channelId)) {
            throw new Exception("The 'chzzk.channel.id' argument is not provided!");
        }

        return new ChzzkBuilder(nidAut, nidSes).build();
    }

}
