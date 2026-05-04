/*****************************************************
 * @software: IntelliJ IDEA
 * @author: Beau Dean
 * @contact: https://fairy.host
 * @organization: https://github.com/FairylandFuture
 * @datetime: 2025-08-25 13:04:29 UTC+08:00
 ****************************************************/
package com.fairyland.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author Beau Dean
 */
public class DateTimeUtils {
    
    public static LocalDateTime timestampToLocalDateTime(Long timestamp) {
        if (timestamp == null) return null;
        
        try {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        } catch (Exception exception) {
            return null;
        }
    }
    
    public static LocalDateTime timestampToLocalDateTime(String timestamp) {
        if (timestamp == null) return null;
        
        try {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(timestamp)), ZoneId.systemDefault());
        } catch (Exception exception) {
            return null;
        }
    }
}
