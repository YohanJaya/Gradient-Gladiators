package com.yohan.gladiators;

import com.jme3.scene.Node;

/**
 * Base class for everything that lives in the game world,
 * such as the Knight, enemies and pillars.
 *
 * Every Entity owns one {@link Node}, which is its body in the scene.
 * Moving or rotating the Node moves everything attached to it.
 * Subclasses decide what the Entity does each frame by
 * implementing {@link #update(float)}.
 */

public abstract class Entity {
    protected Node node;

    // Creates a scene node with the given name
    public Entity(String name) {
        node = new Node(name);
    }

    // Returns the scene node so it can be attached to the scene graph
    public Node getNode() {
        return node;
    }

    // Called every frame with time-per-frame; subclasses define behaviour
    public abstract void update(float tpf);
}