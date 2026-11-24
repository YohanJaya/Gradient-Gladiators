package com.yohan.gladiators;

import com.jme3.math.Vector3f;

public abstract class Character extends Entity{
    private float speed;
    private Health health;
    private Brain brain;
    private Dash dashAbility;    
    private SwordSwing swordSwingAbility;


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

        if(dashAbility != null && dashAbility.isActive()) {
            movement.multLocal(dashAbility.getSpeedMultiplier());
        }

        
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
    public void takeDamage(float amount) {
        health.damage(amount);
    }
    public void heal(float amount) {
        health.heal(amount);
    }
    public boolean isDead() {
        return health.isDead();

    }

    public void attack() {
        if (swordSwingAbility != null) {
            swordSwingAbility.use(this);
        }
    }

    public void dash() {
        if (dashAbility != null) {
            dashAbility.use(this);
        }
    }

    public void update(float tpf) {

        if (swordSwingAbility != null) swordSwingAbility.update(tpf);
        if (dashAbility != null) dashAbility.update(tpf);

        if(brain != null) {

            Action action = brain.decide(this, tpf);

            Vector3f worldDirection = node.getLocalRotation().mult(action.getMoveDirection());
            move(worldDirection, tpf);

            turn(action.getTurn());

            if (action.wantsAttack()) {
                attack();
            }
            if (action.wantsDash()) {
                dash();
                
            }
        }

      
    }

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public void setSwordSwing(SwordSwing swordSwing) {
    this.swordSwingAbility = swordSwing;
    }

    public void setDash(Dash dash) {
        this.dashAbility = dash;
    }
        

    

}
   