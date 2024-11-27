/*
Name: Dillon Kyle
Email: dillonkyle95@protonmail.com
Date: 2024-11-26
Project Name: Final Project
Course: CS17.11
Description: BoxCollider handles the collision detection between GameObjects using AABB (Axis-Aligned Bounding Box) collision testing.
It checks where the BoxCollider bounds are going to be in the next frame, (based on current velocity), so there is no
clipping through other colliders before detection occurs. https://stackoverflow.com/questions/22512319/what-is-aabb-collision-detection
*/

package edu.srjc.finalproject.kyle.dillon.pong;

// collision detection modelled after my design of my game engine project, FlatEngine: https://github.com/DillonKyleDev/FlatEngine__2D-Game-Engine
public class BoxCollider
{
    BoxCollider()
    {
    }

    static boolean checkForCollision(BoxCollider ball, BoxCollider objectCollider)
    {
        boolean b_colliding = ((ball.m_nextLeftEdge < objectCollider.m_nextRightEdge) && (ball.m_nextRightEdge > objectCollider.m_nextLeftEdge) && (ball.m_nextBottomEdge > objectCollider.m_nextTopEdge) && (ball.m_nextTopEdge < objectCollider.m_nextBottomEdge));

        if (b_colliding)
        {
            float ballCenterPos = ball.m_nextTopEdge + (ball.m_nextBottomEdge - ball.m_nextTopEdge) / 2;
            float paddleCenterPos = objectCollider.m_nextTopEdge + (objectCollider.m_nextBottomEdge - objectCollider.m_nextTopEdge) / 2;
            ball.m_distanceFromCenter = (ballCenterPos - paddleCenterPos) / (objectCollider.m_nextBottomEdge - objectCollider.m_nextTopEdge);
        }

        return b_colliding;
    }

    void updateNextActiveEdges(Vector2 position, Vector2 velocity)
    {
        m_nextLeftEdge = position.x + velocity.x;
        m_nextRightEdge = position.x + velocity.x + m_dimensions.x;
        m_nextTopEdge = position.y + velocity.y;
        m_nextBottomEdge = position.y + velocity.y + m_dimensions.y;
    }

    Vector2 getDimensions()
    {
        return m_dimensions;
    }

    void setDimensions(Vector2 dimensions)
    {
        m_dimensions = dimensions;
    }

    float getDistanceFromCenter()
    {
        return m_distanceFromCenter;
    }

    private float m_nextLeftEdge = 0;
    private float m_nextRightEdge = 0;
    private float m_nextTopEdge = 0;
    private float m_nextBottomEdge = 0;
    private Vector2 m_dimensions = new Vector2();
    private float m_distanceFromCenter = 0;
}
