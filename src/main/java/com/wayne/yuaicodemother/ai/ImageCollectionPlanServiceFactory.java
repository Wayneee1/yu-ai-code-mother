package com.wayne.yuaicodemother.ai;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 图片收集计划服务工厂
 */
@Configuration
public class ImageCollectionPlanServiceFactory {

    @Resource(name = "openAiChatModel")
    private ChatModel chatModel;

    /**
     * 创建图片收集计划服务
     *
     * @return 图片收集计划服务
     */
    @Bean
    public ImageCollectionPlanService imageCollectionPlanService() {
        return AiServices.builder(ImageCollectionPlanService.class)
                .chatModel(chatModel)
                .build();
    }
}

