package com.jgm.minecraftapp.model;


public class Block  {

    private String nameSpace;

    private String drop;

    private String img;

    private String nombre;

    private String tool;

    private Integer stack;

    private boolean transparent;

    /*public Block(String nameSpace, String drop, String img, String nombre, String tool, Integer stack, boolean transparent) {
        this.nameSpace = nameSpace;
        this.drop = drop;
        this.img = img;
        this.nombre = nombre;
        this.tool = tool;
        this.stack = stack;
        this.transparent = transparent;
    }*/

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

    public String getImg() {
        return img;
    }

    public void setImg(String image) {
        this.img = image;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String name) {
        this.nombre = name;
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
        return "Block{" +
                "nameSpace='" + nameSpace + '\'' +
                ", drop='" + drop + '\'' +
                ", img='" + img + '\'' +
                ", nombre='" + nombre + '\'' +
                ", tool='" + tool + '\'' +
                ", stack=" + stack +
                ", transparent=" + transparent +
                '}';
    }
}
