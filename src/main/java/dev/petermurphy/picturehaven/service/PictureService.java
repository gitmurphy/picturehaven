package dev.petermurphy.picturehaven.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PictureService {
    public static Map<Color, Integer> getDominantColors(BufferedImage image, int maxColors) {
        Map<Color, Integer> colorCount = new HashMap<>();

        try {
            // Loop through pixels and count reappearing colours
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = new Color(image.getRGB(x, y));
                    colorCount.put(color, colorCount.getOrDefault(color, 0) + 1);
                }
            }

            // Prioritise the colours by frequency
            PriorityQueue<Map.Entry<Color, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
            pq.addAll(colorCount.entrySet());

            // Create map of the most dominant colours
            Map<Color, Integer> dominantColors = new HashMap<>();
            for (int i = 0; i < maxColors && !pq.isEmpty(); i++) {
                Map.Entry<Color, Integer> entry = pq.poll();
                dominantColors.put(entry.getKey(), entry.getValue());
            }

            return dominantColors;
        } catch (Exception e) {
            System.err.println("An error occurred while getting dominant colours: " + e.getMessage());
            return new HashMap<>();
        }
    }
}
