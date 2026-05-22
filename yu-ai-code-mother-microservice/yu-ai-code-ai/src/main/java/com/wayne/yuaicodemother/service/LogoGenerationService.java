package com.wayne.yuaicodemother.service;

import com.wayne.yuaicodemother.model.ImageResource;

import java.util.List;

/**
 * Logo生成服务接口
 */
public interface LogoGenerationService {

    /**
     * 根据描述生成 Logo 设计图片
     *
     * @param description Logo 设计描述，如名称、行业、风格等
     * @return 图片资源列表
     */
    List<ImageResource> generateLogos(String description);
}

