/*
 * @author Alex
 */

package alexandco.projectcookgood;

import java.io.*; //uses buffer and file read and IO exception
import java.util.*; //uses list, map and hashmap
import java.util.regex.*; //uses matcher and pattern

public class shopingList {

    public static Map<String, String> generateShoppingList(List<String> filePaths) {
        Map<String, Map<String, Double>> shoppingList = new HashMap<>(); // Ingredient -> Unit -> Amount

        Pattern detailPattern = Pattern.compile("\\{(\\d+(\\.\\d+)?)%(.+?)\\}"); //regex uses patterns. this one Matches the {amount%unit} container marking.

        for (String filePath : filePaths) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();

                    // Process ingredients
                    if (line.startsWith("@")) {
                        String ingredient = line.substring(1);
                        Matcher matcher = detailPattern.matcher(ingredient);
                        double quantity = 0;
                        String unit = "";

                        if (matcher.find()) {
                            quantity = Double.parseDouble(matcher.group(1));
                            unit = matcher.group(3).trim();
                            ingredient = ingredient.substring(0, matcher.start()).trim();
                        }

                        shoppingList.putIfAbsent(ingredient, new HashMap<>());
                        shoppingList.get(ingredient).merge(unit, quantity, Double::sum);
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading file " + filePath + ": " + e.getMessage());
            }
        }
        return formatShoppingList(shoppingList);
    }
   //this part stores every ingridient to a hashmap
    private static Map<String, String> formatShoppingList(Map<String, Map<String, Double>> shoppingList) {
        Map<String, String> formattedList = new LinkedHashMap<>();

        for (Map.Entry<String, Map<String, Double>> entry : shoppingList.entrySet()) {
            String ingredient = entry.getKey();
            Map<String, Double> quantities = entry.getValue();

            StringBuilder quantityString = new StringBuilder();
            for (Map.Entry<String, Double> quantityEntry : quantities.entrySet()) {
                String unit = quantityEntry.getKey();
                double amount = quantityEntry.getValue();
                if (quantityString.length() > 0) {
                    quantityString.append(", "); // SHOULD BE EXTENDED TO UNIT CONVERSIONS LATER
                }
                quantityString.append(amount).append(" ").append(unit);
            }
            formattedList.put(ingredient, quantityString.toString());
        }
        return formattedList;
    }

    /**
     * Print the shopping list.
     * @param shoppingList
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

