package com.wayne.yuaicodemother.ai;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * AI服务创建工厂
 */
@Configuration
public class AiCodeGeneratorServiceFactory {
    @Resource
    private ChatModel chatModel;

    @Resource
    private StreamingChatModel streamingChatModel;
    /**
     * 创建AI代码生成器服务
     * @return
     */
    @Bean
    public AiCodeGeneratorService aiCodeGeneratorService() {
        return AiServices.builder(AiCodeGeneratorService.class)
                .chatModel(chatModel)
                .streamingChatModel(streamingChatModel)
                .build();
    }
}
