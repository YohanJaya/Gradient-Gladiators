package com.yohan.gladiators;


// 1. The message: what happened


public class DamageEvent{

    Character hurt;
    float damageAmount;
    float currentHealth;
    float maxHealth;

    public DamageEvent(Character hurt, float damageAmount, float currentHealth, float maxHealth) {
        this.hurt = hurt;
        this.damageAmount = damageAmount;
        this.currentHealth = currentHealth;
        this.maxHealth = maxHealth;
    }

    public Character getHurt() {
        return hurt;
    }

    public float getDamageAmount() {
        return damageAmount;
    }



}