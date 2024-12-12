
# Project Cook Good - Documentation

## Overview
This project is a Java-based application for managing recipes, generating shopping lists, and calculating total preparation time for recipes. The main components of the project are:

1. `shopingList.java`: Manages the generation and formatting of shopping lists based on ingredient data.
2. `SouschefPro.java`: The main entry point of the application, handling file input and parsing recipe data.
3. `timeCount.java`: Provides utility methods for converting time units to minutes.

---

## File Details

### 1. `shopingList.java`

#### Purpose
Generates a shopping list by combining ingredients from multiple recipe files.

#### Key Classes and Methods
- `generateShoppingList(List<String> filePaths)`:
  - Reads recipe files to find ingredient details.
  - Combines ingredients by type and unit.
  - Handles file reading errors and parses ingredient quantities using regex.
- `formatShoppingList(Map<String, Map<String, Double>> shoppingList)`:
  - Creates a user-friendly shopping list format.
- `printShoppingList(Map<String, String> shoppingList)`:
  - Displays the shopping list on the console.

#### Dependencies
- Uses `java.io` for reading files.
- Uses `java.util` for managing data structures.
- Uses `java.util.regex` for pattern matching.

---

### 2. `SouschefPro.java`

#### Purpose
Acts as the main application class. It parses input files for recipes, manages command-line arguments, and outputs ingredient lists, utensils, steps, and total preparation time.

#### Key Features
- Command-line Argument Handling:
  - Validates the presence of arguments.
  - Exits gracefully if no file is specified or files are missing.
- Modes of Operation:
  - `-list`: Aggregates and generates a shopping list from recipe files.
- Recipe Parsing:
  - Extracts ingredients, utensils, and recipe steps using predefined markers (`@`, `#`, `~`).
  - Parses and formats recipe steps sequentially.
- Time Calculation:
  - Uses `timeCount` to convert and aggregate preparation times from the recipe data.

#### Dependencies
- Relies on `shopingList` and `timeCount` for shopping list generation and time calculations.
- Handles file operations with `java.io`.
- Uses `java.util.regex` for pattern matching.

---

### 3. `timeCount.java`

#### Purpose
An interface for converting time units to minutes.

#### Key Methods
- `convertToMinutes(int time, String unit)`:
  - Converts a time value into minutes based on the unit.
  - Supported units: `minutes`, `hours`.

#### Dependencies
- None.

---

## Usage Notes
1. Input Format:
   - Recipes should follow a structured format:
     - `@ingredient {amount%unit}` for ingredients.
     - `#utensil` for utensils.
     - `~{time%unit}` for preparation time.
     - Steps as plain text lines.
2. Error Handling:
   - Handles missing files gracefully.
   - Outputs appropriate error messages for invalid input.
3. Extensibility:
   - `timeCount` can be extended to support additional time units.
   - `shopingList` could be enhanced with unit conversion logic.

---

## Contributors
- Alex: Primary developer for `shopingList` and `timeCount`.
- John: Contributor to `SouschefPro` and project architecture.
- Mike: Contributor to Documentation and recipe writer.

---

## Future Enhancements
- Add support for recipe file validation.
- Extend time conversion to include days, seconds, etc.
- Implement unit conversion (e.g., grams to kilograms).
- Add a GUI for user-friendly interaction.