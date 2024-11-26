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

    static Vector2 normalize(float x, float y, float scale)
    {
        float magnitude = (float)(Math.sqrt((double)(x * x) + (double)(y * y)));
        return new Vector2(x/magnitude*scale, y/magnitude*scale);
    }

    public float x = 0;
    public float y = 0;
}
