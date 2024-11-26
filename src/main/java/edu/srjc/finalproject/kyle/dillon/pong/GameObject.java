package edu.srjc.finalproject.kyle.dillon.pong;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.Objects;

public class GameObject
{
    GameObject()
    {
    }

    void setImagePath(String path)
    {
        Image newImage = new Image(Objects.requireNonNull(getClass().getResource(path)).toString(), true);
        m_sprite = new ImageView(newImage);
    }

    ImageView getImageView()
    {
        return m_sprite;
    }

    void updatePosition()
    {
        applyFriction();

        m_position = new Vector2(m_position.x + m_velocity.x, m_position.y + m_velocity.y);
        m_sprite.setTranslateX(m_position.x);
        m_sprite.setTranslateY(m_position.y);
        m_boxCollider.updateNextActiveEdges(m_position, m_velocity);
    }

    void setPosition(Vector2 position)
    {
        m_position = position;
        m_sprite.setTranslateX(m_position.x);
        m_sprite.setTranslateY(m_position.y);
        m_boxCollider.updateNextActiveEdges(position, m_velocity);
    }

    Vector2 getPosition()
    {
        return m_position;
    }

    // "physics" modelled after my design of my personal game engine project, FlatEngine: https://github.com/DillonKyleDev/FlatEngine__2D-Game-Engine
    void setVelocity(Vector2 velocity)
    {
        m_velocity = velocity;
    }

    void addVelocity(Vector2 velocity)
    {
        m_velocity = new Vector2(m_velocity.x + velocity.x, m_velocity.y + velocity.y);
    }

    Vector2 getVelocity()
    {
        return m_velocity;
    }

    void applyFriction()
    {
        m_velocity = new Vector2(m_velocity.x * m_friction, m_velocity.y * m_friction);
    }

    void setFriction(float friction)
    {
        m_friction = friction;
    }

    void setColliderDimensions(Vector2 dimensions)
    {
        m_boxCollider.setDimensions(dimensions);
    }

    BoxCollider getCollider()
    {
        return m_boxCollider;
    }

    void hide()
    {
        m_sprite.setVisible(false);
    }

    void show()
    {
        m_sprite.setVisible(true);
    }

    private Vector2 m_position = new Vector2();
    private Vector2 m_velocity = new Vector2(0,0);
    private float m_friction = 1;
    private ImageView m_sprite = new ImageView();
    private BoxCollider m_boxCollider = new BoxCollider();
}
