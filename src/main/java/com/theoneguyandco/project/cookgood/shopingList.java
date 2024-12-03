package com.theoneguyandco.project.cookgood;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class shopingList extends ingridientDetect {

    public static Map<String, String> generateShoppingList(List<String> filePaths) {
        Map<String, String> shoppingList = new HashMap<>();
        Pattern detailPattern = Pattern.compile("\\{(.+?)\\}");

        for (String filePath : filePaths) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    // Process lines that start with '@' (ingredients)
                    if (line.startsWith("@")) {
                        String ingredient = line.substring(1);
                        Matcher matcher = detailPattern.matcher(ingredient);
                        String details = "";

                        if (matcher.find()) {
                            details = matcher.group(1); // Extract quantity/amount
                            ingredient = ingredient.substring(0, matcher.start()).trim();
                        }

                        // Combine quantities for the same ingredient
                        shoppingList.merge(ingredient, details, (oldDetails, newDetails) -> combineQuantities(oldDetails, newDetails));
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + filePath + ": " + e.getMessage());
            }
        }

        return shoppingList;
    }

    /**
     * Combines two quantity descriptions for the same ingredient.
     */
    private static String combineQuantities(String oldDetails, String newDetails) {
        // If both details are empty, return empty
        if (oldDetails.isEmpty()) {
            return newDetails;
        }
        if (newDetails.isEmpty()) {
            return oldDetails;
        }

        // If both have details, concatenate them
        return oldDetails + " + " + newDetails;
    }

    /**
     * Prints the shopping list.
     */
    public static void printShoppingList(Map<String, String> shoppingList) {
        System.out.println("Shopping List:");
        for (Map.Entry<String, String> entry : shoppingList.entrySet()) {
            String ingredient = entry.getKey();
            String quantity = entry.getValue();
            System.out.println("- " + ingredient + (quantity.isEmpty() ? "" : " (" + quantity + ")"));
        }
    }
}
