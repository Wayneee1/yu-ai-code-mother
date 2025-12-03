package com.wayne.yuaicodemother.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.wayne.yuaicodemother.model.dto.chathistory.ChatHistoryQueryRequest;
import com.wayne.yuaicodemother.model.entity.ChatHistory;
import com.wayne.yuaicodemother.model.entity.User;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import java.time.LocalDateTime;

/**
 *  服务层。
 *
 * @author <a href="https://github.com/liyupi">Wayne Zhou</a>
 */
public interface ChatHistoryService extends IService<ChatHistory> {
    /**
     * 添加聊天记录
     * @param appId 应用id
     * @param message 消息
     * @param messageType 消息类型
     * @param userId 用户id
     * @return 是否成功
     */
    boolean addChatMessage(Long appId,String message,String messageType,Long userId);

    /**
     * 删除历史记录
     * @param appId
     * @return
     */
    boolean deleteByAppId(Long appId);


    Page<ChatHistory> listAppChatHistoryByPage(Long appId, int pageSize,
                                               LocalDateTime lastCreateTime,
                                               User loginUser);

    /**
     * 加载对话历史记录到内存
     * @param appId
     * @param chatMemory
     * @param maxCount
     * @return
     */
    int loadChatHistoryToMemory(Long appId, MessageWindowChatMemory chatMemory, int maxCount);

    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);
}
