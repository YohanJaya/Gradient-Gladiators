package com.yohan.gladiators;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

public class BruteInputBrain implements Brain {

    private enum State { CHASE, ATTACK }

    private final Character target;   // who to chase (the Knight)

    private final float attackStartRange = 2.0f;  // start attacking below this distance
    private final float attackStopRange  = 2.5f;  // go back to chasing above this distance

    private State state = State.CHASE;

    public BruteInputBrain(Character target) {
        this.target = target;
    }

    @Override
    public Action decide(Character self, float tpf) {

        // 1. Direction and distance to the Knight (flat on the floor)
        Vector3f brutePos = self.getNode().getWorldTranslation();
        Vector3f knightPos = target.getNode().getWorldTranslation();

        Vector3f toTarget = knightPos.subtract(brutePos);
        toTarget.y = 0;

        float distance = toTarget.length();

        // 2. Turn toward the Knight (used in BOTH states)
        Vector3f localToTarget = self.getNode().getLocalRotation().inverse().mult(toTarget);
        float turnAngle = FastMath.atan2(-localToTarget.x, -localToTarget.z);

        // 3. Update the state (two different ranges = no flickering)
        State oldState = state;
        if (state == State.CHASE && distance < attackStartRange) {
            state = State.ATTACK;
        } else if (state == State.ATTACK && distance > attackStopRange) {
            state = State.CHASE;
        }

        if (state != oldState) {
            System.out.println("Brute state: " + state);   // temporary, remove later
        }

        // 4. Build the Action for the current state
        if (state == State.CHASE) {
            Vector3f forward = new Vector3f(0, 0, -1);   // relative to facing = walk forward
            return new Action(forward, turnAngle, false, false);
        } else {
            return new Action(Vector3f.ZERO, turnAngle, true, false);   // stand still, face, attack
        }
    }
}