package de.gemuesehasser.tictactoe.gui;

import de.gemuesehasser.tictactoe.TicTacToe;
import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.object.GameField;
import de.gemuesehasser.tictactoe.object.GameDraw;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Das Fenster, auf dem das Spiel abgebildet wird.
 */
public final class GameGui extends JFrame {

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
    private static final String TITLE = "Tic Tac Toe";
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link GameGui}, welches eine Instanz eines {@link JFrame} darstellt. Auf diesem
     * Fenster wird das Spiel abgebildet.
     */
    public GameGui() {
        super(TITLE);
        super.setBounds(0, 0, WIDTH, HEIGHT);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLayout(null);

        final GameDraw draw = new GameDraw();
        draw.setBounds(0, 0, WIDTH, HEIGHT);

        for (int i = 0; i < GAME_SIZE; i++) {
            for (int j = 0; j < GAME_SIZE; j++) {
                final JButton button = getTicTacToeButton(i, j);
                TicTacToe.GAME_FIELD_HANDLER.registerNewField(new GameField(i, j, button));

                super.add(button);
            }
        }

        super.add(draw);
    }
    //</editor-fold>


    //<editor-fold desc="utility">

    /**
     * Gibt einen Button zurück, dessen Hintergrund nicht angezeigt wird und welcher als Grundlage eines Feldes auf dem
     * Spielfeld dieses Tic-Tac-Toe Spiels platziert wird.
     *
     * @param i Die x-Koordinate auf dem Spielfeld.
     * @param j die y-Koordinate auf dem Spielfeld.
     *
     * @return Ein Button, dessen Hintergrund nicht angezeigt wird und welcher als Grundlage eines Feldes auf dem Spiel-
     *      feld dieses Tic-Tac-Toe Spiels platziert wird.
     */
    @NotNull
    private static JButton getTicTacToeButton(int i, int j) {
        final JButton button = new JButton();
        button.setBounds(
                (WIDTH / 2) - ((FIELD_SIZE * GAME_SIZE) / 2) + i * FIELD_SIZE,
                (HEIGHT / 2) - Y_SUBTRACTION - ((FIELD_SIZE * GAME_SIZE) / 2) + j * FIELD_SIZE,
                FIELD_SIZE,
                FIELD_SIZE
        );
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.addActionListener(e -> {
            if (TicTacToe.getCurrentUserType() != UserType.USER) return;

            final GameField gameField = TicTacToe.GAME_FIELD_HANDLER.getField(i, j);

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
