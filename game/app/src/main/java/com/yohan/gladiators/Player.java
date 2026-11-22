package com.yohan.gladiators;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector3f;

public class Player extends Character implements ActionListener, AnalogListener {

    private boolean forward, backward, left, right;

    // Dash settings (numbers you can change later)
    private static final float DASH_DURATION = 2f;          // how long the fast movement lasts
    private static final float DASH_COOLDOWN = 5f;          // wait time, counted from the dash start
    private static final float DASH_SPEED_MULTIPLIER = 2.5f;

    // Dash timers (they count DOWN toward 0)
    private float dashTimeLeft = 0f;
    private float cooldownLeft = 0f;

    public Player(String name, float speed) {
        super(name, speed);
    }

    // Builds a movement vector from held keys, rotates it to face the knight's direction, then moves
    @Override
    public void update(float tpf) {
        // 1. Count both timers down (never below 0)
        if (dashTimeLeft > 0) {
            dashTimeLeft -= tpf;
        }
        if (cooldownLeft > 0) {
            cooldownLeft -= tpf;
        }

        // 2. Build the direction from the held keys
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

        boolean dashing = dashTimeLeft > 0;

        // 3. While dashing with no keys held, dash straight forward
        if (dashing && direction.lengthSquared() == 0) {
            direction.z -= 1;
        }

        // 4. Move (faster if dashing)
        if (direction.lengthSquared() > 0) {
            direction.normalizeLocal();
            direction = node.getLocalRotation().mult(direction);

            float timeStep = dashing ? tpf * DASH_SPEED_MULTIPLIER : tpf;
            move(direction, timeStep);
        }
    }

    // Binds WASD, arrow keys, mouse X-axis and Shift to named input mappings
    public void registerInput(InputManager inputManager) {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "Forward", "Backward", "Left", "Right");

        inputManager.addMapping("TurnLeft",
                new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("TurnRight",
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "TurnLeft", "TurnRight");

        inputManager.addMapping("Dash",
                new KeyTrigger(KeyInput.KEY_RSHIFT),
                new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener(this, "Dash");
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
            startDash();
        }
    }

    // Starts the dash only if the cooldown is finished
    private void startDash() {
        if (cooldownLeft <= 0) {
            dashTimeLeft = DASH_DURATION;
            cooldownLeft = DASH_COOLDOWN;
            System.out.println("Dash started");
        } else {
            System.out.println("Dash not ready: " + cooldownLeft + "s left");
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