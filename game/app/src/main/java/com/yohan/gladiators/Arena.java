package com.yohan.gladiators;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;

public class Arena {

    private static final String LIGHTING = "Common/MatDefs/Light/Lighting.j3md";

    private Material litMaterial(AssetManager assetManager, ColorRGBA color) {
        Material mat = new Material(assetManager, LIGHTING);
        mat.setColor("Diffuse", color);
        mat.setColor("Ambient", color.mult(0.4f));
        mat.setBoolean("UseMaterialColors", true);
        return mat;
    }

    private Geometry helperBuildWall(AssetManager assetManager, String name, float halfX, float halfY, float halfZ) {
        // stone-gray color for walls and pillars
        ColorRGBA stoneGray = new ColorRGBA(0.45f, 0.42f, 0.38f, 1f);
        Box box = new Box(halfX, halfY, halfZ);
        Geometry geometry = new Geometry(name, box);
        geometry.setMaterial(litMaterial(assetManager, stoneGray));
        return geometry;
    }

    private final Node root = new Node("arena");

    public Arena(AssetManager assetManager, GameConfig config) {

        float arenaSize = config.getInt("arena.size");

        // mossy green floor for a Forest Ruins feel
        ColorRGBA mossGreen = new ColorRGBA(0.25f, 0.35f, 0.18f, 1f);
        Box box = new Box(arenaSize / 2f, 0.1f, arenaSize / 2f);
        Geometry geometry = new Geometry("arena", box);
        geometry.setMaterial(litMaterial(assetManager, mossGreen));
        root.attachChild(geometry);
        
        buildWalls(assetManager, arenaSize, config.getInt("arena.wallHeight"), config.getInt("arena.wallThickness"));
        buildPillars(assetManager, arenaSize, config.getInt("arena.pillarSize"), config.getInt("arena.pillarHeight"));
    }

    private void buildWalls(AssetManager assetManager, float size, float height, float thickness) {

        Geometry backWallGeo = helperBuildWall(assetManager, "Back Wall", size / 2f, height / 2f, thickness / 2f);
        backWallGeo.setLocalTranslation(0, height / 2f, -size / 2f);
        root.attachChild(backWallGeo);

        Geometry leftWallGeo = helperBuildWall(assetManager, "Left Wall", thickness / 2f, height / 2f, size / 2f);
        leftWallGeo.setLocalTranslation(-size / 2f, height / 2f, 0);
        root.attachChild(leftWallGeo);

        Geometry frontWallGeo = helperBuildWall(assetManager, "Front Wall", size / 2f, height / 2f, thickness / 2f);
        frontWallGeo.setLocalTranslation(0, height / 2f, size / 2f);
        root.attachChild(frontWallGeo);

        Geometry rightWallGeo = helperBuildWall(assetManager, "Right Wall", thickness / 2f, height / 2f, size / 2f);
        rightWallGeo.setLocalTranslation(size / 2f, height / 2f, 0);
        root.attachChild(rightWallGeo);

    }   


    private void buildPillars(AssetManager assetManager, float size, float pillarSize, float pillarHeight) {
        float half = pillarSize / 2f;
        float y = pillarHeight / 2f;
        float offset = size / 4f;

        float[][] positions = { {offset, offset}, {offset, -offset}, {-offset, offset}, {-offset, -offset} };
        for (int i = 0; i < positions.length; i++) {
            Geometry pillar = helperBuildWall(assetManager, "Pillar" + i, half, y, half);
            pillar.setLocalTranslation(positions[i][0], y, positions[i][1]);
            root.attachChild(pillar);
        }
    }

    public Node getRoot() { return root; }
}