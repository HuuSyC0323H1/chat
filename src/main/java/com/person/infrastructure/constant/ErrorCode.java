package com.person.infrastructure.constant;

import java.io.Serializable;

public enum ErrorCode implements Serializable {
    ERRORS,
    SUCCESS,
    CHECKSUM_INVALID,
    TOKEN_INVALID,
}
