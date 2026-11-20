package com.yohan.gladiators;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.math.Vector3f;



public class Player extends Character implements ActionListener,AnalogListener{

    private boolean forward,backward,left,right, turnLeft;

    public Player(String name, float speed) {
        super(name, speed);
    }

    // Builds a movement vector from held keys, rotates it to face the knight's direction, then moves
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
            direction = node.getLocalRotation().mult(direction);   
            move(direction, tpf);
        }
    }

    // Binds WASD, arrow keys, and mouse X-axis to named input mappings
    public void registerInput(InputManager inputManager) {
        inputManager.addMapping("Forward", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("Backward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_D));

        inputManager.addListener(this, "Forward", "Backward", "Left", "Right");

    //     inputManager.addMapping("TurnLeft", new MouseAxisTrigger(MouseInput.AXIS_X, true));
    //     inputManager.addMapping("TurnRight", new MouseAxisTrigger(MouseInput.AXIS_X, false));
    //     inputManager.addListener(this, "TurnLeft", "TurnRight");

        inputManager.addMapping("TurnLeft",
                    new MouseAxisTrigger(MouseInput.AXIS_X, true),
                    new KeyTrigger(KeyInput.KEY_LEFT));
        inputManager.addMapping("TurnRight",
                    new MouseAxisTrigger(MouseInput.AXIS_X, false),
                    new KeyTrigger(KeyInput.KEY_RIGHT));
        inputManager.addListener(this, "TurnLeft", "TurnRight");
    }

    // Updates movement flags when keys are pressed or released
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

    // Turns the knight left or right based on mouse X movement or arrow keys
    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (name.equals("TurnLeft")) {
            turn ( value * 2f);
            
        } 
        else if (name.equals("TurnRight")) {
            turn (-value * 2f);
            
        }
    }

    
}