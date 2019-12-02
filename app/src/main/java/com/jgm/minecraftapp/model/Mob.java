package com.jgm.minecraftapp.model;

public class Mob {

    private String behavior, img, nombre;
    private Integer attack, health;

    public Mob() {
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
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

    public Integer getAttack() {
        return attack;
    }

    public void setAttack(Integer attack) {
        this.attack = attack;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    @Override
    public String toString() {
        return "Mob{" +
                "behavior='" + behavior + '\'' +
                ", img='" + img + '\'' +
                ", nombre='" + nombre + '\'' +
                ", attack=" + attack +
                ", health=" + health +
                '}';
    }
}
