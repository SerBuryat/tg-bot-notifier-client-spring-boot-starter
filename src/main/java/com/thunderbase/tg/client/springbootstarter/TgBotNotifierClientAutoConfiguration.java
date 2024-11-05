package com.thunderbase.tg.client.springbootstarter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "tg-bot-notifier-server.url")
@ComponentScan
public class TgBotNotifierClientAutoConfiguration {

}
