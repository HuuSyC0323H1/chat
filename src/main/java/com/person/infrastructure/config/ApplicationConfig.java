package com.person.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author dupd
 */
@Getter
@Component
public class ApplicationConfig {

    @Value("${checksumPrivateKey}")
    private String checksumPrivateKey;

    @Value("${nv.token.header}")
    private String nvTokenHeader;

    @Value("${image.prefix}")
    private String imagePrefix;

    @Value("${user.get.info.url}")
    private String getInfoUrl;

    @Value("${gift.detail.url}")
    private String giftDetailUrl;

    @Value("${image.url}")
    private String saveImageUploadUrl;

    @Value("${image.save.url}")
    private String saveImageSaveUrl;

    @Value("${policy.url}")
    private String policyServiceUrl;

    @Value("${warehouse.api.baseUrl}")
    private String baseUrl;

    @Value("${warehouse.api.create}")
    private String create;

    @Value("${warehouse.api.assignGiftForDeal}")
    private String assignGiftForDeal;

    @Value("${product.warehouse}")
    private String productInWareHouse;

    @Value("${product}")
    private String product;

    @Value("${promotion.type}")
    private String promotionType;
}
