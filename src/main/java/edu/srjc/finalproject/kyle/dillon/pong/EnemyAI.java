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

    void setReactionTime(float reactionTime)
    {
        m_reactionTime = reactionTime;
    }

    private GameObject m_target = new GameObject();
    private float m_reactionTime = 1.0f;
    private GameObject m_paddle = new GameObject();
}
