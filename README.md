# Project-CookGood
Project-CookGood is a simple java project that IO's external files into a readable format within it, and later on be inspired by the infamous Nintendo DS Game, "Cooking Guide: Can't Decide What to Eat?".

## Usage
included with every release is a pre-made .jar executable and sample recipe files, for now you can open a command line on the folder you installed it and use the following command to open a file:
```bash
java -jar CookGood.jar alexandco.projectcookgood.SouschefPro.app sample.reci
```
there's also the option to list down a shopping list from multiple files using the `-list` parameter too:
```bash
java -jar CookGood.jar alexandco.projectcookgood.SouschefPro.app -list sample1.reci sample2.reci sample3.cook ...
```
to open in gui mode, you would type this on the command line and open the files from there.
```bash
java -jar CookGood.jar alexandco.projectcookgood.MainGUI.app
```

> (for now `.cook`, `.reci`, and regular `.txt` based files are supported.)

building and packaging has been done with the latest version of maven (optional wrapper included), but regular java commands like `javac` and `jar` can be done with as well, which is mostly recomended to build with. 

## File Details
while it does comprise of basic java dependencies for now, the logic is split into two java files:

- `SouschefPro`
- `shoppingList`

and both work kinda simillar but diffrent.
the first one acts as the main java file with args enabled, handling the input processing and output, whilst the second one when gets called on the comand line, does the output of a dedicated list made within it and deplays it instead.

first, the file, when being read by the scanner object, get's to the parsing stage. 
then it reads for markings handled either by the main class (or the other class when `-list` get's called on the command line) using specific regex patterns, matching them, triming them, and adds them to their seperate lists (one for the object and one for the info in the brackets using the parseDetails method outside from the main class, the `-list` part uses a hash map afterwards to generate it) to be called later.
if it has no data within it, or you didn't specify a recipe file then an exception error get's thrown.
(more info on the coments of the source code).

everything is handled under the try method of the scanner object.
then the printing happens right after it and the array lists get called. ('allwords' and 'allwords2' are ingridients and utensils in this case).

the only parsing being the outlier here is when parsing the time marker and the steps.
with stringbuilder, it cleans out everything from the specific regex pattern for time from begining to end of each line, and then adds them to their specific lists. but then again it has to count up the time markers, that's where `timeCount` comes in.
the simple interface gets called at the end where everything gets converted to minutes, even when hours is listed in the recipe.

Now, `shoppingList`'s parse logic goes entirely on a different process.
when the `-list` gets called on the line, the main java file assumes that listMode gets enabled (or in Boolean terms, turns true but whatevs).
it then uses a special regex pattern for the ingridient, and calls everything marking the ingridient mark to a hashmap.
it then trims them in the same way as the main class, but also adds up the amount of their unit if the same ingridient appears on another recipe accordingly, formatting them in the process.
for stuff like that it turns all of them to keys and values.
then as per usual it prints all of the ingridients out and get called back to the main class.

## Recipe format 
anyone to make any recipe file for this tool are advised to follow how the structure works inside on one of the sample `.reci`  files provided to better support it. the content are as follows.

|Mark Name|Mark Type|Description|
|--|--|--|
|Ingridient / `@ingredient{amount%unit}` |string array int|the overall edible tool. if no info is provided on the brackets then it doesn't include it. should only be marked once on the file.
|Utensil / `#utensil{}` |string array|the usable tool. should only be marked once on the file.
|Time / `~{time%unit}` | int string |the time needed for a specific part of a recipe. if none are provided then the preparation time would be zero by default, so try to at least include some.
|Steps|string|the overall text seperated by at least one empty line


## Credits
Alexis L (it2023092): lead co-developer, Repo host, Documentation

Ioanis K (it2023091): lead developer, beta tester

## ToDo List by Phase 2
- add the required dependencies for phase two
- further optimize the parsing logic
- ~~unit conversions to `gr` and `ml`  *(for solid and liquid ingredients respectively)*~~
- ~~make it only exclusively-compatible with the proprietery `.reci` format, and the `.cook` format~~
- ~~add a print option for amount of servings~~
- ~~make the clean up of markings it's own seperate interface class~~

