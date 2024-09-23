package com.anupam.eventManagement.utils;

import java.util.Random;

public class RandomImageGenerator {

    // Array of image source URLs that generate random images
    private static final String[] IMAGE_SOURCES = {
            "https://rb.gy/vbt4l3",
            "https://rb.gy/xmkyjt",
            "https://rb.gy/dz5qn3",
            "https://rb.gy/08gjuw",
            "https://t.ly/9b6cv"  // Seed for different images each time
    };

    // Method to get a random image URL
    public static String getRandomImageUrl() {
        Random random = new Random();
        int index = random.nextInt(IMAGE_SOURCES.length);
        return IMAGE_SOURCES[index];
    }

    public static void main(String[] args) {
        // Generate and print a random image URL
        String randomImageUrl = getRandomImageUrl();
        System.out.println("Random Image URL: " + randomImageUrl);
    }
}
