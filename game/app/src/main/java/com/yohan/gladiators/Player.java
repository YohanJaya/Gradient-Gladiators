package com.yohan.gladiators;


import java.util.ArrayList;
import java.util.List;
public class Player extends Character{


    // Dash settings (numbers you can change later)
    private static final float DASH_DURATION = 2f;          // how long the fast movement lasts
    private static final float DASH_COOLDOWN = 5f;          // wait time, counted from the dash start
    private static final float DASH_SPEED_MULTIPLIER = 2.5f;

    //Sword Swing Setting
    private static final float SWORD_COOLDOWN = 1f;

    private static final float RANGE = 5f;
    private static final float DAMAGE = 30f;
    private final List<Character> TARGETS = new ArrayList<>(); // This should be set to the list of potential targets in the game



    private final Dash dashAbility = new Dash(DASH_COOLDOWN, DASH_DURATION, DASH_SPEED_MULTIPLIER);
    private final SwordSwing attackAbility = new SwordSwing(SWORD_COOLDOWN, RANGE, DAMAGE, TARGETS);

    public Player(String name, float speed, Health health) {
        super(name, speed, health);
        setDash(dashAbility);
        setSwordSwing(attackAbility);
    }

    public void addTarget(Character target) {
        TARGETS.add(target);
    }

      

    
}