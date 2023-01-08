package com.evolution.bootcamp.assignment.poker;

public enum HandValue
{
STRAIGHT_FLUSH(1),
FOUR_OF_A_KIND(2),
    FULL_HOUSE(3),
    FLUSH(4),
    STRAIGHT(5),
    THREE_OF_A_KIND(6),
    TWO_PAIRS(7),
    PAIR(8),
    HIGH_CARD(9);

    private Integer strength;

    HandValue(int strength)
    {
        this.strength = strength;
    }

    public Integer getStrength()
    {
        return strength;
    }

    public void setStrength(Integer strength)
    {
        this.strength = strength;
    }
}
