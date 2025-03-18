package de.gemuesehasser.tictactoe.gui;

import de.gemuesehasser.tictactoe.TicTacToe;
import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.object.GameField;
import de.gemuesehasser.tictactoe.object.Gui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.swing.*;
import java.awt.*;

/**
 * Das Fenster, auf dem das Spiel abgebildet wird.
 */
public final class GameGui extends Gui {

    //<editor-fold desc="CONSTANTS">
    /** Die Breite dieses Fensters. */
    public static final int WIDTH = 600;
    /** Die Höhe dieses Fensters. */
    public static final int HEIGHT = 450;
    /** Die Größe des Spielfeldes (GAME_SIZE x GAME_SIZE). */
    public static final int GAME_SIZE = 3;
    /** Die Größe eines Feldes auf dem Spielfeld (Quadratisch, also Breite = Höhe). */
    public static final int FIELD_SIZE = 100;
    /** Die Anzahl der Pixel, die nach allen Berechnungen abgezogen werden, für eine mittige Platzierung des Spielfeldes. */
    public static final int Y_SUBTRACTION = 20;
    /** Der Titel dieses Fensters. */
    @NotNull
    private static final String TITLE = "Tic Tac Toe";
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link GameGui}, welches eine Instanz eines {@link JFrame} darstellt. Auf diesem
     * Fenster wird das Spiel abgebildet.
     */
    public GameGui() {
        super(TITLE, WIDTH, HEIGHT);

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
        // draw horizontal lines
        for (int i = 1; i < GAME_SIZE; i++) {
            final int currentY = (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2) + i * FIELD_SIZE;
            g.drawLine(
                    (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2),
                    currentY,
                    (WIDTH / 2) + ((FIELD_SIZE * GAME_SIZE) / 2),
                    currentY
            );
        }

        // draw vertical lines
        for (int i = 1; i < GAME_SIZE; i++) {
            final int currentX = (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2) + i * FIELD_SIZE;
            g.drawLine(
                    currentX,
                    (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2),
                    currentX,
                    (HEIGHT / 2) - Y_SUBTRACTION + ((FIELD_SIZE * GAME_SIZE) / 2)
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
     *
     * @return Ein Button, dessen Hintergrund nicht angezeigt wird und welcher als Grundlage eines Feldes auf dem Spiel-
     *      feld dieses Tic-Tac-Toe Spiels platziert wird.
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
