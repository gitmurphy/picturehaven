package dev.petermurphy.picturehaven.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class PictureService {
    public static Map<Color, Integer> getDominantColors(BufferedImage image, int maxColors, double range) {
        Map<Color, Integer> colorCount = new HashMap<>();

        try {
            // Loop through pixels and count reappearing colors
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color newColor = new Color(image.getRGB(x, y));
                    boolean foundSimilar = false;
                    for (Map.Entry<Color, Integer> existingColor : colorCount.entrySet()) {
                        // Check if colors are similar based on range
                        if (isSimilarColor(newColor, existingColor.getKey(), range)) {
                            existingColor.setValue(existingColor.getValue() + 1);
                            foundSimilar = true;
                            break; // Stop comparing further if similar color found
                        }
                    }
                    // If no similar color found, add new entry with count of 1
                    if (!foundSimilar) {
                        colorCount.put(newColor, 1);
                        System.out.println("Added new color: " + newColor.toString());
                    }
                }
            }

            // Prioritise the colors by frequency
            PriorityQueue<Map.Entry<Color, Integer>> pq = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
            pq.addAll(colorCount.entrySet());

            // Create map of the most dominant colors
            Map<Color, Integer> dominantColors = new HashMap<>();
            for (int i = 0; i < maxColors && !pq.isEmpty(); i++) {
                Map.Entry<Color, Integer> entry = pq.poll();
                dominantColors.put(entry.getKey(), entry.getValue());
            }

            return dominantColors;
        } catch (Exception e) {
            System.err.println("An error occurred while getting dominant colors: " + e.getMessage());
            return new HashMap<>();
        }
    }

    // Determine if a new color is similar to an existing color within the specified range
    private static boolean isSimilarColor(Color newColor, Color existingColor, double range) {
        int maxRGB = 255;
        double threshold = range * maxRGB;
        // Math.abs() is used to handle negative values
        return Math.abs(newColor.getRed() - existingColor.getRed()) <= threshold &&
                Math.abs(newColor.getGreen() - existingColor.getGreen()) <= threshold &&
                Math.abs(newColor.getBlue() - existingColor.getBlue()) <= threshold;
    }
}
