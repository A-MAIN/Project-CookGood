package alexandco.projectcookgood;

/**
 * @author John
 * @author Alex
 */

import java.io.*;
import java.util.*;
import java.util.regex.*;


public class SouschefPro {
    public static void main(String[] args) {
        
        
      File file;
      Scanner readFromFile = null;
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
   //connect to a file (does NOT create a new file)
      file = new File(args[0]);
      try {
         //create a Scanner object to read from the file
         readFromFile = new Scanner(file);
      }
      catch (FileNotFoundException exception) {
      /*
      Print error message.
      In order to print double quotes("),
      the escape sequence for double quotes (\") must be used.
      */
         System.out.print("ERROR: File not found for \"");
         System.out.println(args[0]+"\"");
      //end the program
         System.exit(1);
      }

     //parsing the shoping list, my part uses regex formating.
     boolean isListMode = Arrays.asList(args).contains("-list");

    if (isListMode) {
      int listIndex = Arrays.asList(args).indexOf("-list");
      if (args.length <= listIndex + 1) {
        System.out.println("ERROR: Please specify at least one recipe file for the shopping list.");
        System.exit(1);
    }
    List<String> filePaths = Arrays.asList(Arrays.copyOfRange(args, listIndex + 1, args.length));
    Map<String, String> finalList = shopingList.generateShoppingList(filePaths);
    shopingList.printShoppingList(finalList);
}  
      
         
   //if made connection to file, read from file
   /*
   In order to print double quotes("),
   the escape sequence for double quotes (\") must be used.                
   */
   char startChar = '@';
   char startChar2 = '#';
   char endChar = '}';
        int startIndex,startIndex2;
        int endIndex,endIndex2;
        String result, strN, strN2;
      @SuppressWarnings("Convert2Diamond")
      List<String> words;
      words = new ArrayList<String>();
      @SuppressWarnings("Convert2Diamond")
      List<String> words2;
      words2 = new ArrayList<String>();
      System.out.print("Reading from file \"" + args[0] + "\":\n");
   //keeps looping if file has more lines..

        // Declaring a string variable
        // Condition holds true till
        // there is character in a string

     System.out.println("\nIngridients:\n");
      while (readFromFile.hasNextLine()) {
      //get a line of text..
         line = readFromFile.nextLine();  
         startIndex = line.indexOf(startChar);
         startIndex2 = line.indexOf(startChar2);
         endIndex = line.indexOf(endChar, startIndex + 1);
         endIndex2 = line.indexOf(endChar, startIndex2 + 1);
         if (startIndex != -1 && endIndex != -1) {
            // extract the substring between the start and end characters
            result = line.substring(startIndex + 1, endIndex);
            strN= result.replace("{", " ");
            strN2= strN.replace("%", " ");
            // print the result
            words.addAll(Arrays.asList(strN2));
          //System.out.println(strN2);
        }
         else if (startIndex2 != -1 && endIndex2 != -1) {
           result = line.substring(startIndex2 + 1, endIndex2);
            strN= result.replace("{", " ");
            // print the result
            words2.addAll(Arrays.asList(strN));
            //System.out.println(strN2);
         }
         
      //display a line of text to screen..
      // words.addAll(Arrays.asList(line.split("\\s+")));
      //  System.out.println(line);

      List<String> steps = new ArrayList<>();
      
      //cleaning the output for the steps
      while (readFromFile.hasNextLine()) {
      line = readFromFile.nextLine().trim();
      if (!line.startsWith("@") && !line.startsWith("#") && !line.isEmpty()) {
      steps.add(line);
      }
    }
          
    //parsing time needed for the recipe - alex
    int totalTimeMinutes = 0;
    Pattern timePattern = Pattern.compile("~\\{(\\d+)%(.+?)\\}");
    Matcher timeMatcher = timePattern.matcher(line);
    if (timeMatcher.find()) {
        int time = Integer.parseInt(timeMatcher.group(1));
        String unit = timeMatcher.group(2).trim();
        totalTimeMinutes += timeCount.convertToMinutes(time, unit);
        }
      
      System.out.println("\nIngridients:\n ");
      for (String allWords: words) {
     System.out.println(allWords);
      }

       System.out.println("\nUtensils: \n ");
       for (String allWords2: words2) {
      System.out.println(allWords2);
       }   
                
      System.out.println("\nTotal Time: " + totalTimeMinutes + " minutes \n ");

      // Output steps, formated with counts.
      System.out.println("\nRecipe Steps: \n ");
      for (int i = 0; i < steps.size(); i++) {
                System.out.println((i + 1) + ". " + steps.get(i));
      }
   }

}//end of main() method
}//end of class

        
        
      
