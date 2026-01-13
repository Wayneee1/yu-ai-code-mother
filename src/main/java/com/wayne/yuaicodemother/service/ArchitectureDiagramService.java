package com.wayne.yuaicodemother.service;

import com.wayne.yuaicodemother.model.ImageResource;

import java.util.List;

/**
 * 架构图生成服务接口
 */
public interface ArchitectureDiagramService {

    /**
     * 根据 Mermaid 代码生成架构图
     *
     * @param mermaidCode Mermaid 图表代码
     * @param description 架构图描述
     * @return 图片资源列表
     */
    List<ImageResource> generateMermaidDiagram(String mermaidCode, String description);
}

