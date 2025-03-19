package de.gemuesehasser.tictactoe.gui;

import de.gemuesehasser.tictactoe.TicTacToe;
import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.object.Drawable;
import de.gemuesehasser.tictactoe.object.Gui;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Dieses Fenster wird geöffnet, sobald das Spiel beendet ist. Dabei wird dem Nutzer dann angezeigt, ob er das Spiel
 * gewonnen, verloren oder unentschieden gespielt hat.
 */
public final class GameEndGui extends Gui implements Drawable {

    //<editor-fold desc="CONSTANTS">
    /** Die Breite dieses Fensters. */
    private static final int WIDTH = 300;
    /** Die Höhe dieses Fensters. */
    private static final int HEIGHT = 200;
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Text, der auf diesem Fenster angezeigt werden soll. */
    @NotNull
    private final String text;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt eine neue Instanz eines {@link GameEndGui}, welches eine Abbildung eines {@link Gui} darstellt. Dieses
     * Fenster wird geöffnet, sobald das Spiel beendet ist. Dabei wird dem Nutzer dann angezeigt, ob er das Spiel
     * gewonnen, verloren oder unentschieden gespielt hat.
     *
     * @param title Der Titel dieses Fensters.
     * @param text Der Text, der auf diesem Fenster angezeigt werden soll.
     */
    public GameEndGui(
            @NotNull final String title,
            @NotNull final String text
    ) {
        super(title, WIDTH, HEIGHT);
        super.addDrawable(this);
        this.text = text;

        final JButton reset = new JButton("Nochmal spielen");
        reset.setBounds(WIDTH / 2 - 70, HEIGHT - 100, 140, 35);
        reset.addActionListener(e -> {
            TicTacToe.GAME_FIELD_HANDLER.resetFields();
            TicTacToe.setCurrentUserType(
                    TicTacToe.GAME_FIELD_HANDLER.getLastWinner() == UserType.USER ? UserType.COMPUTER : UserType.USER
            );
            super.dispose();
            TicTacToe.COMPUTER.setWinCombinationType(null);
            TicTacToe.GAME_GUI.repaint();

            if (TicTacToe.getCurrentUserType() == UserType.COMPUTER) TicTacToe.COMPUTER.place();
        });

        super.add(reset);
    }
    //</editor-fold>


    //<editor-fold desc="implementation">

    @Override
    public void draw(@NotNull final Graphics2D g) {
        g.setFont(TicTacToe.DEFAULT_FONT);
        final int textWidth = g.getFontMetrics().stringWidth(text);

        g.drawString(text, WIDTH / 2 - textWidth / 2, HEIGHT / 2 - 40);
    }
    //</editor-fold>
}
