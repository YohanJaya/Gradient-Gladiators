package com.yohan.gladiators;

public class Dash extends Ability {

    private float duration;          // setting: how long the dash lasts
    private float speedMultiplier;   // setting: how much faster
    private float timeLeft;          // timer: counts down

    public Dash(float cooldown, float duration, float speedMultiplier) {
        super(cooldown);
        this.duration = duration;
        this.speedMultiplier = speedMultiplier;
        this.timeLeft = 0f;
    }

    @Override
    protected void activate(Character user) {
        timeLeft = duration;
        }

    @Override
    public void update(float tpf) {
        super.update(tpf); // Call the parent class's update method to handle cooldown countdown
        
        if(timeLeft > 0){
            timeLeft -= tpf;
            if(timeLeft < 0){
                timeLeft = 0;
            }
        }

    }
    public boolean isActive() {
        if(timeLeft > 0){
            return true;
        } else {
            return false;
        }
    }

    public float getSpeedMultiplier() {
        return speedMultiplier;
    }
}