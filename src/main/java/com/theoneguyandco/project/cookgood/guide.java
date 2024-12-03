package com.theoneguyandco.project.cookgood;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class guide {
    public static void main(String[] args) {

        String filePath = args[0];

        if (args[0].equals("-list")) {
            if (args.length < 2) {
                System.out.println("Error: Please specify at least one recipe file for the shopping list.");
            }
        }

        List<String> filePaths = Arrays.asList(Arrays.copyOfRange(args, 1, args.length));

        // Generate and print the shopping list
        Map<String, String> finalList = shopingList.generateShoppingList(filePaths);
        shopingList.printShoppingList(finalList);

        

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            List<String> ingredients = ingridientDetect.parseIngredients(new BufferedReader(new FileReader(filePath)));


            List<String> utensils = utensilDetect.parseUtensils(new BufferedReader(new FileReader(filePath)));


            List<String> steps = new ArrayList<>();
            int totalTimeMinutes = parseStepsAndCalculateTime(reader, steps);


            System.out.println("Ingredients:");
            for (String ingredient : ingredients) {
                System.out.println("- " + ingredient);
            }

            System.out.println("\nUtensils:");
            for (String utensil : utensils) {
                System.out.println("- " + utensil);
            }

            // Output steps
            System.out.println("\nRecipe Steps:");
            for (int i = 0; i < steps.size(); i++) {
                System.out.println((i + 1) + ". " + steps.get(i));
            }

            // Output total preparation time
            System.out.println("\nTotal Preparation Time: " + totalTimeMinutes + " minutes");

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }


    /**
     * Parses steps and calculates the total preparation time.
     */
    private static int parseStepsAndCalculateTime(BufferedReader reader, List<String> steps) throws IOException {
        String line;
        int totalTimeMinutes = 0;
        Pattern timePattern = Pattern.compile("~\\{(\\d+)%(.+?)\\}");

        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.startsWith("@") && !line.startsWith("#") && !line.isEmpty()) {
                steps.add(line);

                // Extract and accumulate time from step
                Matcher timeMatcher = timePattern.matcher(line);
                if (timeMatcher.find()) {
                    int time = Integer.parseInt(timeMatcher.group(1));
                    String unit = timeMatcher.group(2).trim();
                    totalTimeMinutes += timeCount.convertToMinutes(time, unit);
                }
            }
        }
        return totalTimeMinutes;
    }
}
