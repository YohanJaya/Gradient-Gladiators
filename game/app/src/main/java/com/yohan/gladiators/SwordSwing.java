package com.yohan.gladiators;

import java.util.List;
import com.jme3.math.Vector3f;
public class SwordSwing extends Ability {

    private float range;
    private float damage;
    private List<Character> targets;

    public SwordSwing(float cooldown, float range, float damage, List<Character> targets) {
        super(cooldown);
        this.range = range;
        this.damage = damage;
        this.targets = targets;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }

    @Override
    protected void activate(Character user) {

        Vector3f position = user.getNode().getWorldTranslation();
        Vector3f facing = user.getNode().getLocalRotation().mult(new Vector3f(0, 0, -1));
        
        if(targets == null) {
            System.out.println("No targets available for SwordSwing.");
            return;
        }
        else{
            for (Character target : targets) {

                Vector3f targetPos = target.getNode().getWorldTranslation();
                float distBetween = targetPos.subtract(position).lengthSquared();

                if(distBetween <= range * range) {
                    Vector3f toTarget = targetPos.subtract(position).normalize();
                    float dot = facing.dot(toTarget);

                    if(dot > 0.5f) {
                        target.takeDamage(damage);
                    }
                }
                
            }
        }

    }
}