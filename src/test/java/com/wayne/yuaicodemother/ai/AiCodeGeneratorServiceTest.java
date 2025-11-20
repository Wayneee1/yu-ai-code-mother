package com.wayne.yuaicodemother.ai;

import com.wayne.yuaicodemother.ai.model.HtmlCodeResult;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AiCodeGeneratorServiceTest {
    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;
    @Test
    void generateHtmlCode() {
        HtmlCodeResult htmlCodeResult = aiCodeGeneratorService.generateHtmlCode("请生成一个HTML页面，内容是：<h1>Hello World</h1>");
        Assertions.assertNotNull(htmlCodeResult);
    }

    @Test
    void generateMultiFileCode() {
    }
}