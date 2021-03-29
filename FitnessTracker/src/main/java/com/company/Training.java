package com.company;

public abstract class Training {

    private static final int PUSH_UPS_CALORIES = 200;
    private static final int SIT_DOWN_CALORIES = 250;
    private static final int SKIPPING_ROPE_CALORIES = 300;

    public static long getCalories(int seconds, String trainingType) {
        long spentCalories = 0;
        switch (trainingType) {
            case "Анжуманя":
                spentCalories = (long) seconds * PUSH_UPS_CALORIES / 60 / 60;
            case "Приседания":
                spentCalories = (long) seconds * SIT_DOWN_CALORIES / 60 / 60;
            case "Скакалка":
                spentCalories = (long) seconds * SKIPPING_ROPE_CALORIES / 60 / 60;
            default:
        }
        return spentCalories;
    }

}
