/*
Name: Dillon Kyle
Email: dillonkyle95@protonmail.com
Date: 2024-11-26
Project Name: Final Project
Course: CS17.11
Description: The Vector2 class is just a container for an x and y value.  Useful for representing positions and
velocity vectors.
*/

package edu.srjc.finalproject.kyle.dillon.pong;

public class Vector2
{
    Vector2()
    {
        x = 0;
        y = 0;
    }
    Vector2(float newX, float newY)
    {
        x = newX;
        y = newY;
    }

    public float x;
    public float y;
}
