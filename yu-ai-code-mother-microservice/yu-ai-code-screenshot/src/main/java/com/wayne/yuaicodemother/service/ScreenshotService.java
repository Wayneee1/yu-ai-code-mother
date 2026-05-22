package com.wayne.yuaicodemother.service;

/**
 * 截图服务
 */
public interface ScreenshotService {

    /**
     * 通用的截图服务,得到访问地址
     * @param webUrl
     * @return
     */
    String generateAndUploadScreenshot(String webUrl);
}
