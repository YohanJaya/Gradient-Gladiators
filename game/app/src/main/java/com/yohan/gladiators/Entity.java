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

    public Entity(String name) {
        node = new Node(name);
    }

    public Node getNode() {
        return node;
    }

    public abstract void update(float tpf);
}