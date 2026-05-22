package com.wayne.yuaicodemother.service.impl;

import com.wayne.yuaicodemother.ai.ImageCollectionPlanService;
import com.wayne.yuaicodemother.ai.ImageCollectionPlanServiceFactory;
import com.wayne.yuaicodemother.model.ImageCollectionPlan;
import com.wayne.yuaicodemother.model.ImageResource;
import com.wayne.yuaicodemother.service.ArchitectureDiagramService;
import com.wayne.yuaicodemother.service.ImageCollectionService;
import com.wayne.yuaicodemother.service.ImageSearchService;
import com.wayne.yuaicodemother.service.LogoGenerationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片收集服务实现
 */
@Slf4j
@Service
public class ImageCollectionServiceImpl implements ImageCollectionService {

    @Resource
    private ImageSearchService imageSearchService;

    @Resource
    private ImageCollectionPlanServiceFactory imageCollectionPlanServiceFactory;

    @Resource
    private ArchitectureDiagramService architectureDiagramService;

    @Resource
    private LogoGenerationService logoGenerationService;

    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Override
    public List<ImageResource> collectImages(String userPrompt) {
        List<ImageResource> allImages = new ArrayList<>();

        try {
            // 第一步：使用 AI 生成图片收集计划（每次请求创建一个新的服务实例）
            ImageCollectionPlanService planService = imageCollectionPlanServiceFactory.createImageCollectionPlanService();
            ImageCollectionPlan plan = planService.planImageCollection(userPrompt);
            log.info("获取到图片收集计划，开始并发执行图片搜索");

            // 第二步：并发执行各种图片收集任务
            List<CompletableFuture<List<ImageResource>>> futures = new ArrayList<>();

            // 并发执行内容图片搜索
            if (plan.getContentImageTasks() != null && !plan.getContentImageTasks().isEmpty()) {
                for (ImageCollectionPlan.ImageSearchTask task : plan.getContentImageTasks()) {
                    futures.add(CompletableFuture.supplyAsync(
                            () -> imageSearchService.searchContentImages(task.query()),
                            executorService));
                }
            }

            // 并发执行插画图片搜索
            if (plan.getIllustrationTasks() != null && !plan.getIllustrationTasks().isEmpty()) {
                for (ImageCollectionPlan.IllustrationTask task : plan.getIllustrationTasks()) {
                    futures.add(CompletableFuture.supplyAsync(
                            () -> imageSearchService.searchIllustrations(task.query()),
                            executorService));
                }
            }

            // 并发执行架构图生成
            if (plan.getDiagramTasks() != null && !plan.getDiagramTasks().isEmpty()) {
                for (ImageCollectionPlan.DiagramTask task : plan.getDiagramTasks()) {
                    futures.add(CompletableFuture.supplyAsync(
                            () -> architectureDiagramService.generateMermaidDiagram(task.mermaidCode(), task.description()),
                            executorService));
                }
            }

            // 并发执行Logo生成
            if (plan.getLogoTasks() != null && !plan.getLogoTasks().isEmpty()) {
                for (ImageCollectionPlan.LogoTask task : plan.getLogoTasks()) {
                    futures.add(CompletableFuture.supplyAsync(
                            () -> logoGenerationService.generateLogos(task.description()),
                            executorService));
                }
            }

            // 等待所有任务完成并收集结果
            for (CompletableFuture<List<ImageResource>> future : futures) {
                try {
                    List<ImageResource> images = future.join();
                    allImages.addAll(images);
                } catch (Exception e) {
                    log.error("图片搜索任务执行失败: {}", e.getMessage(), e);
                }
            }

            log.info("图片收集完成，共收集 {} 张图片", allImages.size());
        } catch (Exception e) {
            log.error("图片收集失败: {}", e.getMessage(), e);
        }

        return allImages;
    }

    @Override
    public String formatImagesForPrompt(List<ImageResource> images) {
        if (images == null || images.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder("\n\n## 可用图片资源\n");
        sb.append("以下是为您准备的图片资源，请在生成 HTML 代码时使用这些图片 URL：\n\n");

        int index = 1;
        for (ImageResource image : images) {
            sb.append(String.format("%d. [%s] %s\n   URL: %s\n\n",
                    index++,
                    image.getCategory().getText(),
                    image.getDescription(),
                    image.getUrl()));
        }

        sb.append("请根据网站内容选择合适的图片，将图片 URL 直接嵌入到 <img> 标签的 src 属性中。\n");

        return sb.toString();
    }
}

