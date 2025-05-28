package com.mbarca89.DenTracker.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Images {
    private byte[] fullImage;
    private byte[] thumbnail;
    private List<String> paths;

    public Images(byte[] fullImage, byte[] thumbnail) {
        this.fullImage = fullImage;
        this.thumbnail = thumbnail;
    }
}
