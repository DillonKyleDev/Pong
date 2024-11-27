/*
Name: Dillon Kyle
Email: dillonkyle95@protonmail.com
Date: 2024-11-26
Project Name: Final Project
Course: CS17.11
Description: The Paddle extends GameObject and is the representation for the paddles in the Pong game.
*/

package edu.srjc.finalproject.kyle.dillon.pong;

public class Paddle extends GameObject
{
    Paddle()
    {
        setImagePath("images/paddle.png");
        setColliderDimensions(new Vector2(32,128));
        setFriction(0.9f);
    }

    void updatePaddlePosition()
    {
        Vector2 position = getPosition();
        Vector2 velocity = getVelocity();
        BoxCollider boxCollider = getCollider();
        applyFriction();

        if (velocity.y < -10)
        {
            velocity.y = -10;
        }
        else if (velocity.y > 10)
        {
            velocity.y = 10;
        }

        position = new Vector2(position.x + velocity.x, position.y + velocity.y);

        // keeps the paddles inside the play space
        if (position.y > 600 - boxCollider.getDimensions().y)
        {
            setVelocity(new Vector2(velocity.x, 0));
            setPosition(new Vector2(position.x, 600 - boxCollider.getDimensions().y));
        }
        else if (position.y < 60)
        {
            setVelocity(new Vector2(velocity.x, 0));
            setPosition(new Vector2(position.x, 60));
        }
        else
        {
            setPosition(position);
        }
    }
}
