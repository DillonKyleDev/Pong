package edu.srjc.finalproject.kyle.dillon.pong;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import static edu.srjc.finalproject.kyle.dillon.pong.BoxCollider.checkForCollision;

public class GameManager extends AnimationTimer
{
    enum GameType {
        ONEPLAYER,
        TWOPLAYER,
        NONE
    }
    enum Difficulty {
        EASY,
        HARD,
        NONE
    }

    @Override
    public void handle(long now)
    {
        handleEvents();

        m_ball.applySpin();
        m_ball.updatePosition();
        m_leftPaddle.updatePaddlePosition();

        if (m_gameType == GameType.ONEPLAYER)
        {
            if (m_ball.getVelocity().x > 0)
            {
                m_AI.moveToTarget();
            }
            else
            {
                m_AI.moveToCenter();
            }
        }
        m_rightPaddle.updatePaddlePosition();

        checkForCollisions();
    }

    GameManager()
    {
        String goalAudioPath = getClass().getResource("audio/goal.wav").toString();
        String paddleHitAudioPath = getClass().getResource("audio/paddle.wav").toString();
        String wallHitAudioPath = getClass().getResource("audio/wall.wav").toString();

        Media goalAudio = new Media(goalAudioPath);
        Media paddleAudio = new Media(paddleHitAudioPath);
        Media wallAudio = new Media(wallHitAudioPath);

        m_goalSound  = new MediaPlayer(goalAudio);
        m_paddleHitSound = new MediaPlayer(paddleAudio);
        m_wallHitSound = new MediaPlayer(wallAudio);

        initializeGameObjects();
        m_ball.hide();
        m_leftPaddle.hide();
        m_rightPaddle.hide();

        m_topWall.getCollider().setDimensions(new Vector2(800, 60));
        m_topWall.setPosition(new Vector2(0,0));
        m_bottomWall.getCollider().setDimensions(new Vector2(800, 60));
        m_bottomWall.setPosition(new Vector2(0,598));

        m_leftGoal.getCollider().setDimensions(new Vector2(2, 798));
        m_leftGoal.setPosition(new Vector2(0,0));
        m_rightGoal.getCollider().setDimensions(new Vector2(2, 798));
        m_rightGoal.setPosition(new Vector2(798,0));
    }

    void resetBall()
    {
        float m_ballSize = 16;
        m_gameSpeed = 1.0f;
        m_ball.enableAllCollision();
        m_ball.setSpin(0);
        m_ball.setVelocity(new Vector2(0,0));
        m_ball.setPosition(new Vector2(400 - (m_ballSize / 2), 300 - (m_ballSize / 2)));
        m_ball.serve(m_serveDirection);
    }

    void initializeGameObjects()
    {
        float m_paddleHeight = 128;
        float m_paddleWidth = 32;

        m_leftPaddle.setVelocity(new Vector2(0,0));
        m_rightPaddle.setVelocity(new Vector2(0,0));
        m_leftPaddle.setPosition(new Vector2(30, 300 - (m_paddleHeight / 2)));
        m_rightPaddle.setPosition(new Vector2(800 - 30 - m_paddleWidth, 300 - (m_paddleHeight / 2)));
        resetBall();

        if (m_gameType == GameType.ONEPLAYER)
        {
            m_AI.setPaddle(m_rightPaddle);
            m_AI.setTarget(m_ball);
        }
    }

    void givePlayer1Point()
    {
        m_player1score++;
        text_player1Score.setText(String.valueOf(m_player1score));
        m_serveDirection = new Vector2(-5, 0);
        m_ball.enableLeftCollision();

        if (m_player1score >= m_winningScore)
        {
            showWinner("1");
        }
    }
    void givePlayer2Point()
    {
        m_player2score++;
        text_player2Score.setText(String.valueOf(m_player2score));
        m_serveDirection = new Vector2(5, 0);
        m_ball.enableRightCollision();

        if (m_player2score >= m_winningScore)
        {
            showWinner("2");
        }
    }
    void showWinner(String winningNumber)
    {
        endGameLoop();
        text_winner.setText(winningNumber);
        pane_finalScore.setVisible(true);
        pane_finalScore.setDisable(false);
    }

    void checkForCollisions()
    {
        boolean b_hitLeftGoal = checkForCollision(m_ball.getCollider(), m_leftGoal.getCollider());
        boolean b_hitRightGoal = checkForCollision(m_ball.getCollider(), m_rightGoal.getCollider());
        boolean b_hitTopWall = checkForCollision(m_ball.getCollider(), m_topWall.getCollider());
        boolean b_hitBottomWall = checkForCollision(m_ball.getCollider(), m_bottomWall.getCollider());
        boolean b_hitLeftPaddle = (checkForCollision(m_ball.getCollider(), m_leftPaddle.getCollider()) && m_ball.canCollideLeft());
        boolean b_hitRightPaddle = (checkForCollision(m_ball.getCollider(), m_rightPaddle.getCollider()) && m_ball.canCollideRight());

        if (b_hitLeftGoal)
        {
            stopAudio();
            m_goalSound.play();
            givePlayer2Point();
            resetBall();
        }
        else if (b_hitRightGoal)
        {
            stopAudio();
            m_goalSound.play();
            givePlayer1Point();
            resetBall();
        }
        else if (b_hitTopWall || b_hitBottomWall)
        {
            stopAudio();
            m_wallHitSound.play();
            m_ball.setVelocity(new Vector2(m_ball.getVelocity().x, m_ball.getVelocity().y * -1));
        }
        else if (b_hitLeftPaddle || b_hitRightPaddle)
        {
            stopAudio();
            m_paddleHitSound.play();
            m_ball.setSpin(0);
            m_gameSpeed += 0.1f;
        }
        if (b_hitLeftPaddle)
        {
            float distFromPaddleCenter = m_ball.getCollider().getDistanceFromCenter() * 10;
            m_ball.setVelocity(new Vector2(5, distFromPaddleCenter));
            m_ball.applyGameSpeed(m_gameSpeed);
            m_ball.enableRightCollision();
            if (m_leftPaddle.getVelocity().y > 5)
            {
                m_ball.setSpin(0.3f);
            }
            else if (m_leftPaddle.getVelocity().y < -5)
            {
                m_ball.setSpin(-0.3f);
            }
        }
        else if (b_hitRightPaddle)
        {
            float distFromPaddleCenter = m_ball.getCollider().getDistanceFromCenter() * 10;
            m_ball.setVelocity(new Vector2(-5, distFromPaddleCenter));
            m_ball.applyGameSpeed(m_gameSpeed);
            m_ball.enableLeftCollision();
            if (m_gameType == GameType.ONEPLAYER)
            {
                if (m_rightPaddle.getVelocity().y > 8)
                {
                    m_ball.setSpin(0.3f);
                } else if (m_rightPaddle.getVelocity().y < -8)
                {
                    m_ball.setSpin(-0.3f);
                }
            }
            else
            {
                if (m_rightPaddle.getVelocity().y > 5)
                {
                    m_ball.setSpin(0.3f);
                } else if (m_rightPaddle.getVelocity().y < -5)
                {
                    m_ball.setSpin(-0.3f);
                }
            }
        }
    }

    // resource used for Audio: https://docs.oracle.com/javase/8/javafx/api/javafx/scene/media/MediaPlayer.html#setOnStopped-java.lang.Runnable-
    void stopAudio()
    {
        m_goalSound.stop();
        m_goalSound.seek(Duration.millis(0));
        m_paddleHitSound.stop();
        m_paddleHitSound.seek(Duration.millis(0));
        m_wallHitSound.stop();
        m_wallHitSound.seek(Duration.millis(0));
    }

    void startGameLoop()
    {
        hbox_scoreBoard.setVisible(true);
        m_ball.show();
        m_leftPaddle.show();
        m_rightPaddle.show();

        pane_menu.setVisible(false);
        pane_menu.setDisable(true);
        m_AI = new EnemyAI();

        initializeGameObjects();

        if (m_gameType == GameType.ONEPLAYER)
        {
            switch (m_difficulty)
            {
                case Difficulty.EASY:
                    m_AI.setReactionTime(0.01f);
                    break;
                case Difficulty.HARD:
                    m_AI.setReactionTime(0.05f);
                    break;
            }
        }

        start();
    }
    void endGameLoop()
    {
        stop();
        m_serveDirection = new Vector2(-5, 0);
        m_player1score = 0;
        m_player2score = 0;
        text_player1Score.setText(String.valueOf(m_player1score));
        text_player2Score.setText(String.valueOf(m_player2score));
        m_leftPaddle.hide();
        m_rightPaddle.hide();
        m_ball.hide();
    }

    void handleEvents()
    {
        boolean isWKeyPressed = m_pressedKeys.contains("W");
        boolean isSKeyPressed = m_pressedKeys.contains("S");
        boolean isOKeyPressed = m_pressedKeys.contains("O");
        boolean isLKeyPressed = m_pressedKeys.contains("L");

        if (isWKeyPressed)
        {
            m_leftPaddle.addVelocity(new Vector2(0,-1));
        }
        if (isSKeyPressed)
        {
            m_leftPaddle.addVelocity(new Vector2(0,1));
        }
        if (isOKeyPressed && m_gameType == GameType.TWOPLAYER)
        {
            m_rightPaddle.addVelocity(new Vector2(0,-1));
        }
        if (isLKeyPressed && m_gameType == GameType.TWOPLAYER)
        {
            m_rightPaddle.addVelocity(new Vector2(0,1));
        }
    }
    void addKeyPressed(KeyEvent event)
    {
        String code = event.getCode().toString();
        m_pressedKeys.add(code);
    }
    void removeKeyPressed(KeyEvent event)
    {
        String code = event.getCode().toString();
        m_pressedKeys.remove(code);
    }

    void setMenuPane(Pane pane)
    {
        pane_menu = pane;
    }
    void setLevelPane(Pane pane)
    {
        pane_level = pane;
        pane_level.getChildren().add(m_ball.getImageView());
        pane_level.getChildren().add(m_leftPaddle.getImageView());
        pane_level.getChildren().add(m_rightPaddle.getImageView());
    }
    void setFinalScorePane(Pane pane)
    {
        pane_finalScore = pane;
    }
    void setWinnerTextObject(Text text)
    {
        text_winner = text;
    }
    void setPlayer1ScoreText(Text text)
    {
        text_player1Score = text;
    }
    void setPlayer2ScoreText(Text text)
    {
        text_player2Score = text;
    }
    void setScoreBoard(HBox hbox)
    {
        hbox_scoreBoard = hbox;
    }

    void setGameType(GameType type)
    {
        m_gameType = type;
    }

    void setDifficulty(Difficulty difficulty)
    {
        m_difficulty = difficulty;
    }

    @FXML
    public Pane pane_menu;
    @FXML
    public Pane pane_level;
    @FXML
    public Pane pane_finalScore;
    @FXML
    private Text text_winner;
    @FXML
    private Text text_player1Score;
    @FXML
    private Text text_player2Score;
    @FXML
    private HBox hbox_scoreBoard;

    private long m_player1score = 0;
    private long m_player2score = 0;
    private Vector2 m_serveDirection = new Vector2(-5, 0);
    private long m_winningScore = 7;
    private float m_gameSpeed = 1;
    private GameType m_gameType = GameType.TWOPLAYER;
    private Set<String> m_pressedKeys = new HashSet<>();
    private Ball m_ball = new Ball();
    private Paddle m_leftPaddle = new Paddle();
    private Paddle m_rightPaddle = new Paddle();
    private EnemyAI m_AI = new EnemyAI();
    private Difficulty m_difficulty = Difficulty.EASY;
    private GameObject m_topWall = new GameObject();
    private GameObject m_bottomWall = new GameObject();
    private GameObject m_leftGoal = new GameObject();
    private GameObject m_rightGoal = new GameObject();
    private MediaPlayer m_goalSound;
    private MediaPlayer m_paddleHitSound;
    private MediaPlayer m_wallHitSound;
}
