package com.yohan.gladiators;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import java.util.List;

public class BruteInputBrain implements Brain {

    private enum State { CHASE, ATTACK, BLOCK }

    private final Character target;   // who to chase (the Knight)

    private final float attackStartRange = 1.5f;  // start attacking below this distance
    private final float attackStopRange  = 2.0f;  // go back to chasing above this distance

    private State state = State.CHASE;

    private List<Brute> allBrutes;
    private float separationMinDistance = 1.5f;
    private float separationStrength    = 0.6f;

    public BruteInputBrain(Character target) {
        this.target = target;
    }

    public void setAllBrutes(List<Brute> brutes, float minDist, float strength) {
        this.allBrutes = brutes;
        this.separationMinDistance = minDist;
        this.separationStrength = strength;
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

        // 4. Separation: push away from nearby brutes
        Vector3f push = new Vector3f(0, 0, 0);
        if (allBrutes != null) {
            for (Brute other : allBrutes) {
                if (other.getNode() == self.getNode()) continue;
                Vector3f otherPos = other.getNode().getWorldTranslation();
                Vector3f diff = brutePos.subtract(otherPos);
                diff.y = 0;
                float dist = diff.length();
                if (dist > 0 && dist < separationMinDistance) {
                    push.addLocal(diff.normalizeLocal());
                }
            }
        }

        // 5. Build the Action for the current state
        if (state == State.CHASE) {
            Vector3f worldDir = toTarget.length() > 0 ? toTarget.normalize() : new Vector3f(0, 0, -1);
            worldDir.addLocal(push.mult(separationStrength));
            if (worldDir.lengthSquared() > 0) worldDir.normalizeLocal();

            // Convert world direction into local space (relative to brute's facing)
            Vector3f localDir = self.getNode().getLocalRotation().inverse().mult(worldDir);
            localDir.y = 0;

            return new Action(localDir, turnAngle, false, false, false);
        } else {
            return new Action(Vector3f.ZERO, turnAngle, true, false, false);   // stand still, face, attack
        }
    }
}