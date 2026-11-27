package com.yohan.gladiators;

public class ShieldBlock extends Ability {

    private final float duration;   // how long one block lasts (seconds)

    public ShieldBlock(float cooldown, float duration) {
        super(cooldown);
        this.duration = duration;
    }

    @Override
    protected void activate(Character user) {
        user.startBlocking(duration);
    }
}