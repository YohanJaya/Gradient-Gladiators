// PlayerInputBrain.java
package com.yohan.gladiators;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector3f;

public class PlayerInputBrain implements Brain, ActionListener, AnalogListener {

    // Held keys: stay true for as long as the key is down
    private boolean forward, backward, left, right;

    // One-time wishes: set when the key is pressed, cleared after decide() reads them
    private boolean attackRequested, dashRequested;

    // Mouse/arrow turning collected since the last frame
    private float turnAmount; // Default implementation does nothing; subclasses can override

    public PlayerInputBrain(InputManager inputManager) {
        registerInput(inputManager);
    }

    // Same key bindings as your old Player class
    private void registerInput(InputManager inputManager) {
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
                new KeyTrigger(KeyInput.KEY_RSHIFT), new KeyTrigger(KeyInput.KEY_LSHIFT));
        inputManager.addListener(this, "Dash");

        inputManager.addMapping("Attack", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(this, "Attack");
    }

    // Key pressed or released: only REMEMBER it, do not act on it
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
            dashRequested = true;
        } else if (name.equals("Attack") && isPressed) {
            attackRequested = true;
        }
    }

    // Mouse or arrow keys: add up the turning (same numbers as before)
    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("TurnLeft")) {
            turnAmount += value * 2f;
        } else if (name.equals("TurnRight")) {
            turnAmount -= value * 2f;
        }
    }

    // Called once per frame by the body. Turns the remembered keys into one Action.
    @Override
    public Action decide(Character self, float tpf) {

        Vector3f direction = new Vector3f(0, 0, 0);
        if (forward)  direction.z -= 1;
        if (backward) direction.z += 1;
        if (left)     direction.x -= 1;
        if (right)    direction.x += 1;

        direction.normalizeLocal();

        Action action = new Action(direction, turnAmount, attackRequested, dashRequested);

        // Clear the one-time wishes, so one key press causes only ONE attack or dash
        attackRequested = false;
        dashRequested = false;
        turnAmount = 0f;

        return action;
    }
}