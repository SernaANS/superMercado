package com.Avansada.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
    @Autowired
    public CloudinaryConfig(@Value("${cloud.key}") String key,
                            @Value("${cloud.secret}") String secret,
                            @Value("${cloud.name}") String cloud) {
        cloudinary = Singleton.getCloudinary();
        cloudinary.config.cloudName=cloud;
        cloudinary.config.apiSecret=secret;
        cloudinary.config.apiKey=key;
    }

    public Map upload(Object file, Map options) {
        try {
            return cloudinary.uploader().upload(file, options);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
