package com.yohan.gladiators;

import com.jme3.math.Vector3f;

public abstract class Character extends Entity {
    private float speed;

    public Character(String name, float speed) {
        super(name);
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void move(Vector3f direction, float tpf) {
        // Move the node by (direction × speed × tpf).
        Vector3f movement = direction.mult(speed * tpf);
        node.move(movement);
    }

}
   