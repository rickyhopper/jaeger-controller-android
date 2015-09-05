package com.rickyhopper.trirhomjaegerparty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by rickyh on 1/24/15.
 */
public class KaijuGenerator {
    private static List<String> kaijus = new ArrayList<String>(Arrays.asList(
            "WALL OF LIFE: Each drift partner must have one hand flat on a wall for the duration of the fight.",
            "THE FLOOR IS KAIJU BLUE: No drift partner may touch the floor for the duration of the fight.",
            "DOUBLE EVENT: Each partner must have a hand on each other's glass, and both partners must drink at the same time.",
            "DRIFT COMPATIBILITY: Drift partners must have a hand on each other at all times; the more contact, the better.",
            "REACTOR LEAK: Drops of Tabasco must be added to drinks before drinking them.",
            "THE KAI-BREW: Only beer drinks deal damage for this fight. (If you didn't bring beer, then you get a different Kaiju.)",
            "DON'T CHASE THE RABBIT: Only shots deal damage for this fight.",
            "THUNDERCLOUD FORMATION: One arm from each partner will be tied to the others, forming three arms in all."
    ));

    public KaijuGenerator() {

    }

    public static String getRandomKaiju() {
        for (int i = 0; i < 10; i++) {
            int randomNumber = (int) ((Math.random() * 64) % 8);
            String k = kaijus.remove(randomNumber);
            kaijus.add(k);
        }
        return kaijus.get((int) ((Math.random() * 64) % 8));
    }
}
