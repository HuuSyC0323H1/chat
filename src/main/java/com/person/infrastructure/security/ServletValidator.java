package com.person.infrastructure.security;

import com.person.infrastructure.constant.ErrorCode;
import com.person.infrastructure.exception.NVException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Slf4j
@Component
@PropertySource("classpath:application.properties")
@Setter
@Getter
public class ServletValidator {

    @Value("${checksumPrivateKey}")
    private String checksumPrivateKey;

    @Value("${jwt.tokenKey}")
    private String tokenKey;

    public void validate(RequestObject req) {
        if(!req.getIsDebug()) {
            verifyChecksum(req);
        }
    }

    private void verifyChecksum(RequestObject req) {
        String data = req.getDataString() == null ? "" : req.getDataString();
        String expectedChecksum = DigestUtils.md5DigestAsHex(checksumPrivateKey.getBytes());
        if (!req.getChecksum().equals(expectedChecksum)) {
            throw new NVException(ErrorCode.CHECKSUM_INVALID);
        }
    }
}
