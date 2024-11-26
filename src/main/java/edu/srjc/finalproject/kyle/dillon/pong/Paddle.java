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

        position = new Vector2(position.x + velocity.x, position.y + velocity.y);

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
