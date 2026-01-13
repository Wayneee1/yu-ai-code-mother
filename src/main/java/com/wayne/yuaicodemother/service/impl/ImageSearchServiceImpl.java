package com.wayne.yuaicodemother.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.wayne.yuaicodemother.model.ImageResource;
import com.wayne.yuaicodemother.model.enums.ImageCategoryEnum;
import com.wayne.yuaicodemother.service.ImageSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片搜索服务实现
 */
@Slf4j
@Service
public class ImageSearchServiceImpl implements ImageSearchService {

    private static final String PEXELS_API_URL = "https://api.pexels.com/v1/search";
    private static final String UNDRAW_API_URL = "https://undraw.co/_next/data/mMWmJSt23qpgo8cLTD_pB/search/%s.json?term=%s";

    @Value("${pexels.api-key}")
    private String pexelsApiKey;

    @Override
    public List<ImageResource> searchContentImages(String query) {
        List<ImageResource> imageList = new ArrayList<>();
        int searchCount = 12;
        // 调用 API，注意释放资源
        try (HttpResponse response = HttpRequest.get(PEXELS_API_URL)
                .header("Authorization", pexelsApiKey)
                .form("query", query)
                .form("per_page", searchCount)
                .form("page", 1)
                .execute()) {
            if (response.isOk()) {
                JSONObject result = JSONUtil.parseObj(response.body());
                JSONArray photos = result.getJSONArray("photos");
                for (int i = 0; i < photos.size(); i++) {
                    JSONObject photo = photos.getJSONObject(i);
                    JSONObject src = photo.getJSONObject("src");
                    imageList.add(ImageResource.builder()
                            .category(ImageCategoryEnum.CONTENT)
                            .description(photo.getStr("alt", query))
                            .url(src.getStr("medium"))
                            .build());
                }
            }
        } catch (Exception e) {
            log.error("Pexels API 调用失败: {}", e.getMessage(), e);
        }
        return imageList;
    }

    @Override
    public List<ImageResource> searchIllustrations(String query) {
        List<ImageResource> imageList = new ArrayList<>();
        int searchCount = 12;
        String apiUrl = String.format(UNDRAW_API_URL, query, query);

        // 使用 try-with-resources 自动释放 HTTP 资源
        try (HttpResponse response = HttpRequest.get(apiUrl).timeout(10000).execute()) {
            if (!response.isOk()) {
                return imageList;
            }
            JSONObject result = JSONUtil.parseObj(response.body());
            JSONObject pageProps = result.getJSONObject("pageProps");
            if (pageProps == null) {
                return imageList;
            }
            JSONArray initialResults = pageProps.getJSONArray("initialResults");
            if (initialResults == null || initialResults.isEmpty()) {
                return imageList;
            }
            int actualCount = Math.min(searchCount, initialResults.size());
            for (int i = 0; i < actualCount; i++) {
                JSONObject illustration = initialResults.getJSONObject(i);
                String title = illustration.getStr("title", "插画");
                String media = illustration.getStr("media", "");
                if (StrUtil.isNotBlank(media)) {
                    imageList.add(ImageResource.builder()
                            .category(ImageCategoryEnum.ILLUSTRATION)
                            .description(title)
                            .url(media)
                            .build());
                }
            }
        } catch (Exception e) {
            log.error("搜索插画失败：{}", e.getMessage(), e);
        }
        return imageList;
    }
}

