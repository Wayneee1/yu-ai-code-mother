package com.wayne.yuaicodemother.service;

import com.wayne.yuaicodemother.model.ImageResource;

import java.util.List;

/**
 * 图片收集服务接口
 */
public interface ImageCollectionService {

    /**
     * 根据用户提示词收集图片
     *
     * @param userPrompt 用户提示词
     * @return 收集到的图片列表
     */
    List<ImageResource> collectImages(String userPrompt);

    /**
     * 将图片列表格式化为字符串，用于传递给 AI
     *
     * @param images 图片列表
     * @return 格式化后的字符串
     */
    String formatImagesForPrompt(List<ImageResource> images);
}

