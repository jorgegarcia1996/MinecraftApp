package com.jgm.minecraftapp.model;

public class Mob {

    private String behavior, image, name;
    private Integer attack, health;

    public Mob(String behavior, String image,String name, Integer attack, Integer health) {
        this.behavior = behavior;
        this.image = image;
        this.name = name;
        this.attack = attack;
        this.health = health;
    }

    public Mob() {
    }

    public String getBehavior() {
        return behavior;
    }

    public void setBehavior(String behavior) {
        this.behavior = behavior;
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
        return this.name;
    }
}
