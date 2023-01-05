package com.evolution.bootcamp.assignment.poker;

public enum HandValue
{
STRAIGHT_FLUSH(1),
FOUR_OF_A_KIND(2),
    FULL_HOUSE(3),
    FLUSH(4),
    STRAIGHT(5);

private int strength;

    HandValue(int strength)
    {
        this.strength = strength;
    }
}
