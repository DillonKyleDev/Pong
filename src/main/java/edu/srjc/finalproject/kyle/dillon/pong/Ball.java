/*
Name: Dillon Kyle
Email: dillonkyle95@protonmail.com
Date: 2024-11-26
Project Name: Final Project
Course: CS17.11
Description: This is a class that inherits from GameObject class and specializes in being the Pong ball.
*/

package edu.srjc.finalproject.kyle.dillon.pong;
import javafx.scene.image.Image;
import java.util.Objects;

public class Ball extends GameObject
{
    Ball()
    {
        m_normalBall = new Image(Objects.requireNonNull(getClass().getResource(m_normalBallPath)).toString(), true);
        m_spinningBall = new Image(Objects.requireNonNull(getClass().getResource(m_spinningBallPath)).toString(), true);
        getImageView().setImage(m_normalBall);
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
        if (m_spin != 0)
        {
            getImageView().setImage(m_spinningBall);
        }
        else
        {
            getImageView().setImage(m_normalBall);
        }
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
    final private String m_normalBallPath = "images/ball.png";
    final private String m_spinningBallPath = "images/spinningBall.png";
    private Image m_normalBall;
    private Image m_spinningBall;
}
