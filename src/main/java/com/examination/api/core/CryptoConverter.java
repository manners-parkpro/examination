package com.examination.api.core;

import com.examination.api.utils.CryptoUtil;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.StringUtils;

@Converter
public class CryptoConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        try {
            return StringUtils.hasText(attribute) ? CryptoUtil.encrypt(attribute) : attribute;
        } catch (Exception e) {
            throw new RuntimeException("Encryption error", e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        try {
            return CryptoUtil.decrypt(dbData);
        } catch (Exception e) {
            throw new RuntimeException("Decryption error", e);
        }
    }
}
