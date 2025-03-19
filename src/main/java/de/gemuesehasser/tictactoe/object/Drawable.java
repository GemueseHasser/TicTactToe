package de.gemuesehasser.tictactoe.object;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Ein {@link Drawable} beschreibt eine Instanz eines Zeichen-Objekts, das auf ein {@link Gui} gezeichnet wird.
 */
public interface Drawable {

    /**
     * Zeichnet mithilfe eines übergebenen Grafik-Objekts eine Grafik auf ein Fenster, zu dem dieses {@link Drawable}
     * hinzugefügt wurde.
     *
     * @param g Das Zeichen-Objekt, welches genutzt wird, um die Grafiken zu zeichnen.
     */
    void draw(@NotNull final Graphics2D g);

}
