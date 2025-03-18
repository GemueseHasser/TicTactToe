package de.gemuesehasser.tictactoe.object;

import de.gemuesehasser.tictactoe.gui.GameGui;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Ein {@link GameDraw} stellt ein Zeichen-Objekt für das {@link GameGui} dar, womit alle grafischen Komponenten auf das
 * Fenster gezeichnet werden.
 */
public final class GameDraw extends JLabel {

    //<editor-fold desc="implementation">

    @Override
    protected void paintComponent(@NotNull final Graphics graphics) {
        super.paintComponent(graphics);

        final Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // draw horizontal lines
        for (int i = 1; i < GameGui.GAME_SIZE; i++) {
            final int currentY = (GameGui.HEIGHT / 2) - GameGui.Y_SUBTRACTION - ((GameGui.FIELD_SIZE * GameGui.GAME_SIZE) / 2) + i * GameGui.FIELD_SIZE;
            g.drawLine(
                    (GameGui.WIDTH / 2) - ((GameGui.FIELD_SIZE * GameGui.GAME_SIZE) / 2),
                    currentY,
                    (GameGui.WIDTH / 2) + ((GameGui.FIELD_SIZE * GameGui.GAME_SIZE) / 2),
                    currentY
            );
        }

        // draw vertical lines
        for (int i = 1; i < GameGui.GAME_SIZE; i++) {
            final int currentX = (GameGui.WIDTH / 2) - ((GameGui.FIELD_SIZE * GameGui.GAME_SIZE) / 2) + i * GameGui.FIELD_SIZE;
            g.drawLine(
                    currentX,
                    (GameGui.HEIGHT / 2) - GameGui.Y_SUBTRACTION - ((GameGui.FIELD_SIZE * GameGui.GAME_SIZE) / 2),
                    currentX,
                    (GameGui.HEIGHT / 2) - GameGui.Y_SUBTRACTION + ((GameGui.FIELD_SIZE * GameGui.GAME_SIZE) / 2)
            );
        }
    }
    //</editor-fold>
}
