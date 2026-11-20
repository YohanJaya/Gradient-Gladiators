package com.yohan.gladiators;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;



public class Player extends Character implements ActionListener{

    private boolean forward,backward,left,right;

    public Player(String name, float speed) {
        super(name, speed);
    }

    @Override
    public void update(float tpf) {
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

        if (direction.lengthSquared() > 0) {
            direction.normalizeLocal();
            move(direction, tpf);
        }
    }

    public void registerInput(InputManager inputManager) {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addListener(this, "Forward", "Backward", "Left", "Right");
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        

        if (name.equals("Forward")) {
            forward = isPressed;
        } 
        else if (name.equals("Backward")) {
            backward = isPressed;
        }
        else if (name.equals("Left")) {
            left = isPressed;
        }
        else if (name.equals("Right")) {
            right = isPressed;
        }
        
    }
}