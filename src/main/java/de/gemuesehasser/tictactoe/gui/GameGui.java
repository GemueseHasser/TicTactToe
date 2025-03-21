package de.gemuesehasser.tictactoe.gui;

import de.gemuesehasser.tictactoe.TicTacToe;
import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.object.Drawable;
import de.gemuesehasser.tictactoe.object.GameField;
import de.gemuesehasser.tictactoe.object.Gui;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * Das Fenster, auf dem das Spiel abgebildet wird.
 */
public final class GameGui extends Gui implements Drawable {

    //<editor-fold desc="CONSTANTS">
    /**
     * Die Breite dieses Fensters.
     */
    public static final int WIDTH = 750;
    /**
     * Die Höhe dieses Fensters.
     */
    public static final int HEIGHT = 500;
    /**
     * Die Größe des Spielfeldes (GAME_SIZE x GAME_SIZE).
     */
    public static final int GAME_SIZE = 3;
    /**
     * Die Größe eines Feldes auf dem Spielfeld (Quadratisch, also Breite = Höhe).
     */
    public static final int FIELD_SIZE = 100;
    /**
     * Die Anzahl der Pixel, die nach allen Berechnungen abgezogen werden, für eine mittige Platzierung des Spielfeldes.
     */
    public static final int Y_SUBTRACTION = 20;
    /**
     * Der Titel dieses Fensters.
     */
    @NotNull
    private static final String TITLE = "Tic Tac Toe";
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /**
     * Das Bild, welches als Hintergrund des Tic-Tac-Toe Spiels verwendet wird.
     */
    @NotNull
    private final BufferedImage backgroundImage;
    /**
     * Das Bild, welches als Icon dient, um die Siege des Nutzers darzustellen.
     */
    @NotNull
    private final BufferedImage winImage;
    /**
     * Das Bild, welches als Icon dient, um die Niederlagen des Nutzers (Siege des Computers) darzustellen.
     */
    @NotNull
    private final BufferedImage loseImage;
    /**
     * Der Button, der angezeigt wird, sobald das Spiel vorbei ist, womit das Spiel neu gestartet werden kann.
     */
    @Getter
    @NotNull
    private final JButton resetButton = new JButton("Nochmal Spielen");
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link GameGui}, welches eine Instanz eines {@link JFrame} darstellt. Auf diesem
     * Fenster wird das Spiel abgebildet.
     */
    public GameGui() {
        super(TITLE, WIDTH, HEIGHT);
        super.addDrawable(this);
        super.addDrawable(TicTacToe.COMPUTER);

        // load image resources
        try (final InputStream backgroundImageStream = getClass().getResourceAsStream("/background.jpg");
             final InputStream loseImageStream = getClass().getResourceAsStream("/lose.jpg");
             final InputStream winImageStream = getClass().getResourceAsStream("/win.png")
        ) {
            assert backgroundImageStream != null;
            assert winImageStream != null;
            assert loseImageStream != null;
            this.backgroundImage = ImageIO.read(backgroundImageStream);
            this.winImage = ImageIO.read(winImageStream);
            this.loseImage = ImageIO.read(loseImageStream);
        } catch (@NotNull final IOException e) {
            throw new RuntimeException(e);
        }

        // initialize reset button
        this.resetButton.setBounds(25, HEIGHT - 100, 140, 35);
        this.resetButton.addActionListener(e -> {
            resetButton.setVisible(false);
            TicTacToe.GAME_FIELD_HANDLER.resetFields();
            TicTacToe.setCurrentUserType(
                    TicTacToe.GAME_FIELD_HANDLER.getLastWinner() == UserType.USER ? UserType.COMPUTER : UserType.USER
            );
            TicTacToe.COMPUTER.setWinCombinationType(null);
            TicTacToe.GAME_GUI.repaint();

            if (TicTacToe.getCurrentUserType() == UserType.COMPUTER) TicTacToe.COMPUTER.place();
        });
        this.resetButton.setVisible(false);

        super.add(resetButton);

        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                final JButton button = getTicTacToeButton(i, j);
                TicTacToe.GAME_FIELD_HANDLER.registerNewField(new GameField(i, j, button));

                super.add(button);
            }
        }
    }
    //</editor-fold>


    //<editor-fold desc="implementation">

    @Override
    public void draw(@NotNull final Graphics2D g) {
        g.setFont(TicTacToe.DEFAULT_FONT.deriveFont(25F));
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.drawImage(winImage, 20, 35, 60, 60, null);
        g.drawString(UserType.USER.getPoints() + "", 90, 75);

        g.drawImage(loseImage, WIDTH - 95, 35, 60, 60, null);
        g.drawString(UserType.COMPUTER.getPoints() + "", WIDTH - 125, 75);

        g.drawImage(
                backgroundImage,
                (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2) - 20,
                (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2) - 20,
                FIELD_SIZE * GAME_SIZE + 40,
                FIELD_SIZE * GAME_SIZE + 40,
                null
        );
        g.drawRect(
                (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2) - 21,
                (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2) - 21,
                FIELD_SIZE * GAME_SIZE + 42,
                FIELD_SIZE * GAME_SIZE + 42
        );

        // draw horizontal lines
        for (int i = 1; i < GAME_SIZE; i++) {
            final int currentY = (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2) + i * FIELD_SIZE;
            g.fillRect(
                    (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2),
                    currentY,
                    FIELD_SIZE * GAME_SIZE,
                    3
            );
        }

        // draw vertical lines
        for (int i = 1; i < GAME_SIZE; i++) {
            final int currentX = (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2) + i * FIELD_SIZE;
            g.fillRect(
                    currentX,
                    (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2),
                    3,
                    FIELD_SIZE * GAME_SIZE
            );
        }
    }
    //</editor-fold>


    //<editor-fold desc="utility">

    /**
     * Gibt einen Button zurück, dessen Hintergrund nicht angezeigt wird und welcher als Grundlage eines Feldes auf dem
     * Spielfeld dieses Tic-Tac-Toe Spiels platziert wird.
     *
     * @param row    Die Zeile auf dem Spielfeld.
     * @param column Die Spalte auf dem Spielfeld.
     * @return Ein Button, dessen Hintergrund nicht angezeigt wird und welcher als Grundlage eines Feldes auf dem Spiel-
     * feld dieses Tic-Tac-Toe Spiels platziert wird.
     */
    @NotNull
    private static JButton getTicTacToeButton(
            @Range(from = 0, to = GAME_SIZE - 1) final int row,
            @Range(from = 0, to = GAME_SIZE - 1) final int column
    ) {
        final JButton button = new JButton();
        button.setBounds(
                (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2) + row * FIELD_SIZE,
                (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2) + column * FIELD_SIZE,
                FIELD_SIZE,
                FIELD_SIZE
        );
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(e -> {
            if (TicTacToe.getCurrentUserType() != UserType.USER) return;

            final GameField gameField = TicTacToe.GAME_FIELD_HANDLER.getField(row, column);

            assert gameField != null;
            if (gameField.getUserType() != null) return;

            gameField.updateUserType(UserType.USER);

            TicTacToe.setCurrentUserType(UserType.COMPUTER);
            TicTacToe.COMPUTER.place();
        });
        return button;
    }
    //</editor-fold>
}
