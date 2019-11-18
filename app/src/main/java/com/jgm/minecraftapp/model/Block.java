package com.jgm.minecraftapp.model;

public class Block {

    private String nameSpace, drop, image, name, tool;
    private Integer stack;
    private boolean transparent;

    public Block(String nameSpace, String drop, String image, String name, String tool, Integer stack, boolean transparent) {
        this.nameSpace = nameSpace;
        this.drop = drop;
        this.image = image;
        this.name = name;
        this.tool = tool;
        this.stack = stack;
        this.transparent = transparent;
    }

    public Block() {
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getDrop() {
        return drop;
    }

    public void setDrop(String drop) {
        this.drop = drop;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public Integer getStack() {
        return stack;
    }

    public void setStack(Integer stack) {
        this.stack = stack;
    }

    public boolean isTransparent() {
        return transparent;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
