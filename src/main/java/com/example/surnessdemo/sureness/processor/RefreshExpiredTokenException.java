package com.example.surnessdemo.sureness.processor;

import com.usthe.sureness.processor.exception.SurenessAuthenticationException;

/**
 * refresh token message
 */
public class RefreshExpiredTokenException extends SurenessAuthenticationException {
    public RefreshExpiredTokenException(String message) {
        super(message);
    }
}
