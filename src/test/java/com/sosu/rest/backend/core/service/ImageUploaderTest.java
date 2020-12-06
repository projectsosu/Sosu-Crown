/**
 * @author : Oguz Kahraman
 * @since : 12.08.2020
 * <p>
 * Copyright - SoSu Backend
 **/
package com.sosu.rest.backend.core.service;

import com.sosu.rest.backend.core.service.ImageUploader;
import com.sosu.rest.backend.enums.ProductType;
import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.models.results.Result;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageUploaderTest {

    @Mock
    private ImageKit imageKit;

    @InjectMocks
    private ImageUploader imageUploader;

    @Test
    void uploadImage() {
        Result result = new Result();
        result.setUrl("example.com");
        when(imageKit.upload(any())).thenReturn(result);
        String result1 = imageUploader.uploadImage("", "", ProductType.GAME);
        assertEquals("example.com", result1);
    }

    @Test
    void uploadProfileImage() {
        Result result = new Result();
        result.setUrl("example.com");
        when(imageKit.upload(any())).thenReturn(result);
        String result1 = imageUploader.uploadProfileImage("new byte[]".getBytes(), "");
        assertEquals("example.com", result1);
    }
}