package com.rzheng.zhengcraft.entities;

import java.util.UUID;

public class Block {

    private UUID id;
    private String material;
    private long blocksPlaced = 0;

    public Block(UUID id, String material, long blocksPlaced) {
        this.id = id;
        this.material = material;
        this.blocksPlaced = blocksPlaced;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public long getBlocksPlaced() {
        return blocksPlaced;
    }

    public void setBlocksPlaced(long blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }
}
