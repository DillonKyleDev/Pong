package edu.srjc.finalproject.kyle.dillon.pong;

public class Ball extends GameObject
{
    Ball()
    {
        setImagePath("images/ball.png");
        setColliderDimensions(new Vector2(16,16));
        setFriction(1.0f);
        setSpin(0.00f);
    }

    void serve(Vector2 direction)
    {
        setVelocity(new Vector2(0,0));
        addVelocity(direction);
    }

    void applySpin()
    {
        setVelocity(new Vector2(getVelocity().x, getVelocity().y + m_spin));
    }

    void setSpin(float spin)
    {
        m_spin = spin;
    }

    void applyGameSpeed(float speed)
    {
        setVelocity(new Vector2(getVelocity().x * speed, getVelocity().y * speed));
    }

    void enableLeftCollision()
    {
        m_b_leftPaddleCollisionEnabled = true;
        m_b_rightPaddleCollisionEnabled = false;
    }
    void enableRightCollision()
    {
        m_b_leftPaddleCollisionEnabled = false;
        m_b_rightPaddleCollisionEnabled = true;
    }
    void enableAllCollision()
    {
        m_b_leftPaddleCollisionEnabled = true;
        m_b_rightPaddleCollisionEnabled = true;
    }
    boolean canCollideLeft()
    {
        return m_b_leftPaddleCollisionEnabled;
    }
    boolean canCollideRight()
    {
        return m_b_rightPaddleCollisionEnabled;
    }

    float m_spin = 0;
    private boolean m_b_leftPaddleCollisionEnabled = true;
    private boolean m_b_rightPaddleCollisionEnabled = true;
}
