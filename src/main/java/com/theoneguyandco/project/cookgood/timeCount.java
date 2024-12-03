package com.theoneguyandco.project.cookgood;

public class timeCount {

    public static int convertToMinutes(int time, String unit) {
        return switch (unit.toLowerCase()) {
            case "minutes" ->
                time;
            case "hours" ->
                time * 60;
            default ->
                0;
        }; // Unsupported or unknown units
    }
}
