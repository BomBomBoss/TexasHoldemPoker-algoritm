package com.evolution.bootcamp.assignment.poker;

public enum Strength
{
    STRONG(1), /* for all straight combinations excluding A2345 */
    WEAK(0); /* for straight combination A2345 */

    private int strength;

    Strength(int strength)
    {
        this.strength = strength;
    }

    public int getStrength()
    {
        return strength;
    }

    public void setStrength(int strength)
    {
        this.strength = strength;
    }
}
