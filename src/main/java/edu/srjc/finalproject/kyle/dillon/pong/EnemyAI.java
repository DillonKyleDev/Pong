/*
Name: Dillon Kyle
Email: dillonkyle95@protonmail.com
Date: 2024-11-26
Project Name: Final Project
Course: CS17.11
Description: A rudimentary AI for the right paddle when 1 Player mode is active.  All it does is chase the balls y-position
at a speed dependent on the game's difficulty mode.  The AI's max speed is the same as the player's.
*/

package edu.srjc.finalproject.kyle.dillon.pong;

public class EnemyAI
{
    EnemyAI()
    {
    }

    void setPaddle(GameObject paddle)
    {
        m_paddle = paddle;
    }

    void moveToTarget()
    {
        float paddleCenterPos = m_paddle.getPosition().y + (m_paddle.getCollider().getDimensions().y / 2);
        float distanceToBall = m_target.getPosition().y - 16 - paddleCenterPos;
        m_paddle.addVelocity(new Vector2(0, distanceToBall * m_reactionTime));
    }

    void moveToCenter()
    {
        float paddleCenterPos = m_paddle.getPosition().y + (m_paddle.getCollider().getDimensions().y / 2);
        float distanceToCenter = 300 - paddleCenterPos;
        m_paddle.addVelocity(new Vector2(0, distanceToCenter * 0.001f));
    }

    void setTarget(GameObject target)
    {
        m_target = target;
    }

    void setEasyMode()
    {
        m_reactionTime = 0.015f;
    }

    void setHardMode()
    {
        m_reactionTime = 0.03f;
    }

    private GameObject m_target = new GameObject();
    private float m_reactionTime = 1.0f;
    private GameObject m_paddle = new GameObject();
}
