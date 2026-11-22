package com.yohan.gladiators;

import com.jme3.math.Vector3f;

public abstract class Character extends Entity {
    private float speed;
    private Health health;

    // Sets name and movement speed
    public Character(String name, float speed, Health health) {
        super(name);
        this.speed = speed;
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // Translates the node along the given direction scaled by speed and tpf
    public void move(Vector3f direction, float tpf) {
        Vector3f movement = direction.mult(speed * tpf);
        node.move(movement);
    }

    // Rotates the node around the Y-axis by the given angle (radians)
    public void turn(float angle){
        node.rotate(0, angle, 0);
    }

    public Health getHealth() {
        return health;
    }
    public void setHealth(Health health) {
        this.health = health;
    }
    public void takeDamaged(float amount) {
        health.damage(amount);
    }
    public void heal(float amount) {
        health.heal(amount);
    }
    public boolean isDead() {
        return health.isDead();

    }

}
   