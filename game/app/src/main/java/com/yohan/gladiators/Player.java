package com.yohan.gladiators;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Player extends Character implements ActionListener, AnalogListener {

    private boolean forward, backward, left, right;

    // Dash settings (numbers you can change later)
    private static final float DASH_DURATION = 2f;          // how long the fast movement lasts
    private static final float DASH_COOLDOWN = 5f;          // wait time, counted from the dash start
    private static final float DASH_SPEED_MULTIPLIER = 2.5f;

    //Sword Swing Setting
    private static final float SWORD_COOLDOWN = 1f;
    private static final float RANGE = 5f;
    private static final float DAMAGE = 10f;
    private final List<Character> TARGETS = new ArrayList<>(); // This should be set to the list of potential targets in the game



    private final Dash dashAbility = new Dash(DASH_COOLDOWN, DASH_DURATION, DASH_SPEED_MULTIPLIER);
    private final SwordSwing attackAbility = new SwordSwing(SWORD_COOLDOWN, RANGE, DAMAGE, TARGETS);

    public Player(String name, float speed, Health health) {
        super(name, speed, health);
    }

    public void addTarget(Character target) {
        TARGETS.add(target);
    }

    // Builds a movement vector from held keys, rotates it to face the knight's direction, then moves
    @Override
    public void update(float tpf) {

        dashAbility.update(tpf); 
        attackAbility.update(tpf);

        Vector3f direction = new Vector3f(0, 0, 0);

        if (forward) {
            direction.z -= 1;
        }
        if (backward) {
            direction.z += 1;
        }
        if (left) {
            direction.x -= 1;
        }
        if (right) {
            direction.x += 1;
        }

        boolean dashing = dashAbility.isActive();

        // 3. While dashing with no keys held, dash straight forward
        if (dashing && direction.lengthSquared() == 0) {
            direction.z -= 1;
        }

        // 4. Move (faster if dashing)
        if (direction.lengthSquared() > 0) {
            direction.normalizeLocal();
            direction = node.getLocalRotation().mult(direction);

            if (dashing) {
                float dashtpf = tpf * dashAbility.getSpeedMultiplier();
                move(direction, dashtpf);
                
            }
            else {
                move(direction, tpf);
            }
            
        }
    }

    // Binds WASD, arrow keys, mouse X-axis and Shift to named input mappings
    public void registerInput(InputManager inputManager) {

        //Moving fwd,bwd,left and right
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "Forward", "Backward", "Left", "Right");

        //Turning
        inputManager.addMapping("TurnLeft",
                new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("TurnRight",
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "TurnLeft", "TurnRight");

        //Dashing
        inputManager.addMapping("Dash", new KeyTrigger(KeyInput.KEY_RSHIFT), new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener(this, "Dash");

        //sword attack
        inputManager.addMapping("Attack", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Attack");

    }

    // Updates movement flags when keys are pressed or released
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals("Forward")) {
            forward = isPressed;
        } else if (name.equals("Backward")) {
            backward = isPressed;
        } else if (name.equals("Left")) {
            left = isPressed;
        } else if (name.equals("Right")) {
            right = isPressed;
        } else if (name.equals("Dash") && isPressed) {
            dashAbility.use(this);
        } else if (name.equals("Attack") && isPressed) {
            attackAbility.use(this);
        }
    }


    // Turns the knight left or right based on mouse X movement or arrow keys
    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("TurnLeft")) {
            turn(value * 2f);
        } else if (name.equals("TurnRight")) {
            turn(-value * 2f);
        }
    }
}