package com.yohan.gladiators;


// 1. The message: what happened


public class DamageEvent{

    Character hurtCharacter;
    float damageAmount;
    float currentHealth;
    float maxHealth;

    public DamageEvent(Character hurtCharacter, float damageAmount, float currentHealth, float maxHealth) {
        this.hurtCharacter = hurtCharacter;
        this.damageAmount = damageAmount;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public Character getHurtCharacter() {
        return hurtCharacter;
    }

    public float getDamageAmount() {
        return damageAmount;
    }



}