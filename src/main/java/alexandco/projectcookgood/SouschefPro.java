package alexandco.projectcookgood;

/**
 * @author John
 * @author Alex
 */

import java.io.*; //uses scanner and file read and IO exception
import java.util.*; //uses list, map and hashmap
import java.util.regex.*; //uses matcher and pattern


public class SouschefPro {
    public static void main(String[] args) {
          
      File file;
      Scanner readFromFile;
      String line;

      /*
      Error Checking For Command Line Arguments..
      If no command line arguments are entered,
      the size of the String array args is zero (0)
      */

      if(args.length == 0){
         System.out.println("ERROR: Please enter the file name as the first commandline argument!");
         //end the program
         System.exit(1);
      }
      //connect to a file (does NOT create a new file) -john
      file = new File(args[0]);

      //create a Scanner object to read from the file
      try { 
   readFromFile = new Scanner(file);
   
   List<String> words = new ArrayList<>();
   List<String> words2 = new ArrayList<>();
   List<String> steps = new ArrayList<>();

   Pattern toolPattern = Pattern.compile("\\{(.+?)\\}"); //supposed to capture the square brackets after the markers if any

   //parsing time needed for the recipe - alex
    int totalTimeMinutes = 0;
    Pattern timePattern = Pattern.compile("~\\{(\\d+)%(.+?)\\}");      
    
   //if made connection to file, read from file
   /*
   In order to print double quotes("),
   the escape sequence for double quotes (\") must be used.                
   */
   //should clean the output function with regex later, john's error logs are passible. but ill have to fix the output logic.
   
   // alt
   //char startChar = '@';
   //char startChar2 = '#';
   //char endChar = '}';
   // int startIndex,startIndex2;
   // int endIndex,endIndex2;
   // String result, strN, strN2;
   // @SuppressWarnings("Convert2Diamond")
   // List<String> words;
   // words = new ArrayList<>();
   // @SuppressWarnings("Convert2Diamond")
   //  List<String> words2;
   //  words2 = new ArrayList<>();
    
   System.out.print("Reading from file \"" + args[0] + "\":\n");

   //keeps looping if file has more lines..

        // Declaring a string variable
        // Condition holds true till
        // there is character in a string - john

      while (readFromFile.hasNextLine()) {
      line = readFromFile.nextLine();
         if (line.startsWith("@")) {
         // if it starts with an ingredient
         words.add(parseDetails(line.substring(1), toolPattern, true));
          } else if (line.startsWith("#")) {
         // if it starts with a utensil
         words2.add(parseDetails(line.substring(1), toolPattern, true));
        // cleaning the output for the steps
          } else if (!line.startsWith("@") || !line.startsWith("#") || !line.startsWith("~") || !line.isEmpty()) {
         steps.add(parseDetails(line, toolPattern, false));
          }
      // Check for time markers and extract values
      Matcher timeMatcher = timePattern.matcher(line);
      while (timeMatcher.find()) {
        int time = Integer.parseInt(timeMatcher.group(1));
        String unit = timeMatcher.group(2).trim();
        totalTimeMinutes += timeCount.convertToMinutes(time, unit);
        }
    }
      //output the stuff out
      System.out.println("\nIngridients:\n ");
      for (String allWords: words) {
     System.out.println("- " + allWords);
      }

       System.out.println("\nUtensils:\n ");
       for (String allWords2: words2) {
      System.out.println("- " + allWords2);
       }   
                
      System.out.println("\nTotal Time: " + totalTimeMinutes + " minutes \n ");

      // Output steps, formated with counts.
      System.out.println("\nRecipe Steps: \n ");
      for (int i = 0; i < steps.size(); i++) {
                System.out.println((i + 1) + ". " + steps.get(i));
      }
      
      } catch (FileNotFoundException e) {
         System.out.print("ERROR: File not found for \"");
         System.out.println(args[0]+"\"");
      //end the program
         System.exit(1);
      }
  
    //parsing the shoping list from the other java class, my part uses regex formating. - alex
    boolean isListMode = Arrays.asList(args[0]).contains("-list");

    if (isListMode) {
      int listIndex = Arrays.asList(args[0]).indexOf("-list");
      if (args.length <= listIndex + 1) {
        System.out.println("ERROR: Please enter at least one recipe file for the shopping list!");
        System.exit(1);
    }
    List<String> filePaths = Arrays.asList(Arrays.copyOfRange(args, listIndex + 1, args.length));
    Map<String, String> finalList = shopingList.generateShoppingList(filePaths);
    shopingList.printShoppingList(finalList);
    System.exit(0);
    }
  
  } //end of main() method

   //this private method should clean out the marker parts when parsing the list for ingridients and utensils, and later the steps in. - alex
       private static String parseDetails(String text, Pattern pattern, boolean includeUnits) {
        Matcher matcher = pattern.matcher(text);
        StringBuilder result = new StringBuilder();
        
        if (matcher.find()) {
            // Add the main part before the details
            result.append(text, 0, matcher.start());
    
            // Add the content inside {} only if includeUnits is true
            if (includeUnits) {
            String name = text.substring(0, matcher.start()).trim();
            String details = matcher.group(1).trim();
            return name + " (" + details + ")";
            }
        } else {
            result.append(text);
            //this pattern should remove the extra details from the markers - alex
            return text.replaceAll("\\{.*?\\}", "").trim();
        }
        return result.toString();
    }
       
}//end of class
