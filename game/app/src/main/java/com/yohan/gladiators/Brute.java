package com.yohan.gladiators;
import java.util.ArrayList;
import java.util.List;

public class Brute extends Character {

    private float SWORD_SWING_COOLDOWN = 1.0f; // seconds
    private float SWORD_SWING_RANGE = 5.0f; // units
    private float SWORD_SWING_DAMAGE = 10.0f; // damage points

    private List<Character> TARGETS = new ArrayList<>(); // This should be set to the list of potential targets in the game
    
    SwordSwing swordSwingAbility = new SwordSwing(SWORD_SWING_COOLDOWN, SWORD_SWING_RANGE, SWORD_SWING_DAMAGE, TARGETS);

    public Brute(String name, float speed, Health health) {
        super(name, speed, health);
        setSwordSwing(swordSwingAbility);
        
    }

    public void addTarget(Character target) {
        TARGETS.add(target);
    }
}