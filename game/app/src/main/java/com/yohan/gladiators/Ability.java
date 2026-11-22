package com.yohan.gladiators;

public abstract class Ability {

    private float cooldown;       // how long to wait between uses
    private float cooldownLeft;   // how much of that wait remains

    public Ability(float cooldown) {
        this.cooldown = cooldown;
        this.cooldownLeft = 0f;
    }

    public float getCoolDownLeft(){
        return this.cooldownLeft;
    }

    // Called every frame
    public void update(float tpf) {
        cooldownLeft -= tpf;
        if (cooldownLeft < 0) {
            cooldownLeft = 0; 
        }
    }

    public boolean isReady() {
        if(cooldownLeft <= 0) {
            return true;
        } 
        else {
            return false;
        }
    }

    // The part everyone shares: check readiness, start the cooldown, then do the effect
    public void use(Character user) {

        if(isReady()){
            cooldownLeft = cooldown;
            activate(user);
        }

    }

    // The part that differs: each child class decides what happens
    protected abstract void activate(Character user);
}