package com.wayne.yuaicodemother.ai;

import com.wayne.yuaicodemother.ai.model.HtmlCodeResult;
import com.wayne.yuaicodemother.ai.model.MultiFileCodeResult;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import reactor.core.publisher.Flux;

public interface AiCodeGeneratorService {
    /**
     * 生成 HTML 代码
     * @param userMessage
     * @return Ai的输出结果
     */
    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    HtmlCodeResult generateHtmlCode(@MemoryId int memoryId, String userMessage);
    /**0
     * 生成多文件代码
     * @param userMessage
     * @return Ai的输出结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    MultiFileCodeResult generateMultiFileCode(String userMessage);

    /**
     * 生成 HTML 代码（流式）
     * @param userMessage
     * @return Ai的输出结果
     */
    @SystemMessage(fromResource = "prompt/codegen-html-system-prompt.txt")
    Flux<String> generateHtmlCodeStream(String userMessage);
    /**
     * 生成多文件代码(流式)
     * @param userMessage
     * @return Ai的输出结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    Flux<String> generateMultiFileCodeStream(String userMessage);

    /**
     * 生成Vue代码(流式)
     * @param userMessage 用户提示词
     * @return Ai的输出结果
     */
    @SystemMessage(fromResource = "prompt/codegen-multi-file-system-prompt.txt")
    Flux<String> generateVueProjectCodeStream(@MemoryId long appId,@UserMessage String userMessage);

}
