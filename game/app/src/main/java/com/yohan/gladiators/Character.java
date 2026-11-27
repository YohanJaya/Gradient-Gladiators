package com.yohan.gladiators;

import com.jme3.math.Vector3f;

public abstract class Character extends Entity {

    private float speed;
    private Health health;
    private Brain brain;
    private Dash dashAbility;
    private SwordSwing swordSwingAbility;
    private ShieldBlock shieldBlockAbility;

    private float blockTimeLeft = 0f;   // seconds of blocking left (0 = not blocking)

    private EventBus eventBus;

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

    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    // Translates the node along the given direction scaled by speed and tpf
    public void move(Vector3f direction, float tpf) {
        Vector3f movement = direction.mult(speed * tpf);

        if (dashAbility != null && dashAbility.isActive()) {
            movement.multLocal(dashAbility.getSpeedMultiplier());
        }

        node.move(movement);
    }

    // Rotates the node around the Y-axis by the given angle (radians)
    public void turn(float angle) {
        node.rotate(0, angle, 0);
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public void takeDamage(float amount) {

        // Blocking: the hit does nothing (no damage, no event)
        if (isBlocking()) {
            return;
        }

        health.damage(amount);

        if (eventBus != null) {
            eventBus.publish(new DamageEvent(this, amount, health.getCurrent(), health.getMax()));
        }
    }

    public void heal(float amount) {
        health.heal(amount);
    }

    public boolean isDead() {
        return health.isDead();
    }

    // ---- Blocking ----

    public void startBlocking(float seconds) {
        this.blockTimeLeft = seconds;
    }

    public boolean isBlocking() {
        return blockTimeLeft > 0f;
    }

    // ---- Abilities ----

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

    public void block() {
        if (shieldBlockAbility != null) {
            shieldBlockAbility.use(this);
        }
    }

    public void update(float tpf) {

        if (swordSwingAbility != null) swordSwingAbility.update(tpf);
        if (dashAbility != null) dashAbility.update(tpf);
        if (shieldBlockAbility != null) shieldBlockAbility.update(tpf);

        // Count the block timer down
        if (blockTimeLeft > 0f) {
            blockTimeLeft = Math.max(0f, blockTimeLeft - tpf);
        }

        if (brain != null) {

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

    public void setShieldBlock(ShieldBlock shieldBlock) {
        this.shieldBlockAbility = shieldBlock;
    }
}