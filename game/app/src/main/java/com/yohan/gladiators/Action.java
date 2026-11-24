// Action.java  (the "order" a brain gives to a body)
package com.yohan.gladiators;

import com.jme3.math.Vector3f;

public class Action {

    public static final Action IDLE = new Action(new Vector3f(0, 0, 0), 0, false, false);

    private final Vector3f moveDirection; // local direction: (0,0,-1) means forward
    private final float turn;             // positive = turn left, negative = turn right
    private final boolean attack;
    private final boolean dash;

    public Action(Vector3f moveDirection, float turn, boolean attack, boolean dash) {
        this.moveDirection = moveDirection;
        this.turn = turn;
        this.attack = attack;
        this.dash = dash;
    }

    public Vector3f getMoveDirection() { return moveDirection; }
    public float getTurn() { return turn; }
    public boolean wantsAttack() { return attack; }
    public boolean wantsDash() { return dash; }

    public static Action idle() {
        return new Action(new Vector3f(0, 0, 0), 0, false, false);
    }
}

