package com.yohan.gladiators;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class BruteInputBrain implements Brain {
    private final Character target;   // who to chase (the Knight)

    public BruteInputBrain(Character target) {
        this.target = target;
    }

    @Override
    public Action decide(Character self, float tpf) {

        Vector3f brutePos = self.getNode().getWorldTranslation();
        Vector3f knightPos= target.getNode().getWorldTranslation();

        Vector3f toTarget = knightPos.subtract(brutePos);

        toTarget.y = 0;

        Vector3f local = self.getNode().getLocalRotation().inverse().mult(toTarget);

        float turnAngle = FastMath.atan2(-local.x, -local.z);
        
        Vector3f forward = new Vector3f(0, 0, -1);
        return new Action(forward, turnAngle, false, false);




    }
}
