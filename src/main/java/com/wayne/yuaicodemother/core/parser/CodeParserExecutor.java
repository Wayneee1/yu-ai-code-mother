package com.wayne.yuaicodemother.core.parser;

import com.wayne.yuaicodemother.exception.BusinessException;
import com.wayne.yuaicodemother.exception.ErrorCode;
import com.wayne.yuaicodemother.model.enums.CodeGenTypeEnum;

/**
 * 代码解析执行器
 * 根据代码生成类型执行相应的解析逻辑
 */
public class CodeParserExecutor {
    private static final HtmlCodeParser htmlCodeParser = new HtmlCodeParser();

    private static final MultiFileCodeParser multiFileCodeParser = new MultiFileCodeParser();

    /**
     * 执行代码解析
     * @param codeContent
     * @param codeGenType
     * @return 解析结果（HtmlCodeResult 或 MultiFileCodeResult）
     */
    public static Object executeParser(String codeContent, CodeGenTypeEnum codeGenType){
        return switch (codeGenType){
            case HTML -> htmlCodeParser.parseCode(codeContent);
            case MULTI_FILE -> multiFileCodeParser.parseCode(codeContent);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR,"不支持的生成类型:" + codeGenType);
        };
    }
}
