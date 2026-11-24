package com.yohan.gladiators;
public class Health {
    private float max;
    private float current;

    public Health(float max) {
        this.max = max;
        this.current = max;
    }

    public void damage(float amount) {
        this.current = this.current - amount;
        if (this.current < 0) {
            this.current = 0;
        }
    }

    public void heal(float amount) {
        this.current = this.current + amount;
        if (this.current > this.max) {
            this.current = this.max;
        }
    }

    public boolean isDead() {
        if (this.current <= 0 ) {
            return true;
        } else {
            return false;   
        }
    }

    public float getFraction() {
        return this.current / this.max;


    }

    public float getCurrent() {
        return this.current;
    }
}