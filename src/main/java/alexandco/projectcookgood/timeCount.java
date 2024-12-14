package alexandco.projectcookgood;

/*
*@author Alex
*/

//this one's mine, supposed to simply count up the minutes from a file and put them on the output.

public interface timeCount {
    public static int convertToMinutes(int time, String unit) {
        return switch (unit.toLowerCase()) {
            case "minutes" ->
                time;
            case "hours" ->
                time * 60;
            default ->
                0;
        };
    }
}
