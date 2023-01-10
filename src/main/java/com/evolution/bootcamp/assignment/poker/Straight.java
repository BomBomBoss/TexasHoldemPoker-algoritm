package com.evolution.bootcamp.assignment.poker;

public class Straight
{
    private Enum<Strength> strength;

    public Straight(Enum<Strength> strength)
    {
        this.strength = strength;
    }

    public Enum<Strength> getStrength()
    {
        return strength;
    }

    public void setStrength(Enum<Strength> strength)
    {
        this.strength = strength;
    }
}
