package com.wayne.yuaicodemother.service;

import com.wayne.yuaicodemother.model.ImageResource;

import java.util.List;

/**
 * 图片搜索服务接口
 */
public interface ImageSearchService {

    /**
     * 搜索内容相关的图片（Pexels）
     *
     * @param query 搜索关键词
     * @return 图片资源列表
     */
    List<ImageResource> searchContentImages(String query);

    /**
     * 搜索插画图片（Undraw）
     *
     * @param query 搜索关键词
     * @return 图片资源列表
     */
    List<ImageResource> searchIllustrations(String query);
}

