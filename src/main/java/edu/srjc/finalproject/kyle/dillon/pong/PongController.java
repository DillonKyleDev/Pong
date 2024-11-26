package edu.srjc.finalproject.kyle.dillon.pong;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class PongController implements Initializable
{
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // There is probably a better way to do this then drilling like this
        m_gameManager.setMenuPane(pane_menu);
        m_gameManager.setLevelPane(pane_level);
        m_gameManager.setFinalScorePane(pane_finalScore);
        m_gameManager.setWinnerTextObject(text_winner);
        m_gameManager.setPlayer1ScoreText(text_player1Score);
        m_gameManager.setPlayer2ScoreText(text_player2Score);
        m_gameManager.setScoreBoard(hbox_scoreBoard);
    }

    // resource used for event handling: https://www.demo2s.com/g/java/how-to-use-javafx-detect-if-a-key-is-being-held-down-in-java.html
    @FXML
    void startGame()
    {
        pane_rootNode.getScene().setOnKeyPressed(e ->
        {
            m_gameManager.addKeyPressed(e);
        });
        pane_rootNode.getScene().setOnKeyReleased(e ->
        {
            m_gameManager.removeKeyPressed(e);
        });

        m_gameManager.startGameLoop();
    }

    @FXML
    void setOnePlayer()
    {
        pane_difficultyMenu.setVisible(true);
        m_gameManager.setGameType(GameManager.GameType.ONEPLAYER);
    }
    @FXML
    void setTwoPlayer()
    {
        pane_difficultyMenu.setVisible(false);
        m_gameManager.setGameType(GameManager.GameType.TWOPLAYER);
    }

    @FXML
    void setEasyMode()
    {
        m_gameManager.setDifficulty(GameManager.Difficulty.EASY);
    }
    @FXML
    void setHardMode()
    {
        m_gameManager.setDifficulty(GameManager.Difficulty.HARD);
    }

    @FXML
    void showMainMenu()
    {
        pane_finalScore.setVisible(false);
        pane_finalScore.setDisable(true);
        pane_menu.setVisible(true);
        pane_menu.setDisable(false);
        hbox_scoreBoard.setVisible(false);
    }

    @FXML
    private Pane pane_rootNode;
    @FXML
    private Pane pane_menu;
    @FXML
    private Pane pane_difficultyMenu;
    @FXML
    private Pane pane_level;
    @FXML
    private Pane pane_finalScore;
    @FXML
    private Text text_winner;
    @FXML
    private Text text_player1Score;
    @FXML
    private Text text_player2Score;
    @FXML
    private HBox hbox_scoreBoard;

    private GameManager m_gameManager = new GameManager();
}