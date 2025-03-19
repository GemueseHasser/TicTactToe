package de.gemuesehasser.tictactoe.object;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Ein {@link Gui} stellt eine individuelle Anpassung eines {@link JFrame Fensters} dar, welches in seinen Eigenschaften
 * so individualisiert wurde, dass das Fenster sehr einfach für diese Anwendung initialisiert werden kann und automatisch
 * mit einem Zeichen-Objekt ausgerüstet ist.
 */
public class Gui extends JFrame {

    //<editor-fold desc="LOCAL FIELDS">
    /** Alle {@link Drawable Objekte}, dessen Grafik-Implementation auf dieses Fenster gezeichnet wird. */
    @NotNull
    private final List<Drawable> drawables = new ArrayList<>();
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt mithilfe eines Titels und einer Größe eine neue Instanz eines {@link Gui}. Ein {@link Gui} stellt eine
     * individuelle Anpassung eines {@link JFrame Fensters} dar, welches in seinen Eigenschaften so individualisiert
     * wurde, dass das Fenster sehr einfach für diese Anwendung initialisiert werden kann und automatisch mit einem
     * Zeichen-Objekt ausgerüstet ist.
     *
     * @param title Der Titel dieses Fensters.
     * @param width Die Breite dieses Fensters.
     * @param height Die Höhe dieses Fensters.
     */
    public Gui(
            @NotNull final String title,
            @Range(from = 0, to = Integer.MAX_VALUE) final int width,
            @Range(from = 0, to = Integer.MAX_VALUE) final int height
    ) {
        super(title);
        super.setBounds(0, 0, width, height);
        super.setLocationRelativeTo(null);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setResizable(false);
        super.setLayout(null);
    }
    //</editor-fold>


    /**
     * Instanziiert das jeweilige Zeichen-Objekt dieses Fensters, fügt dieses dem Fenster hinzu und öffnet dann das
     * Fenster.
     */
    public void open() {
        final Draw draw = new Draw();
        draw.setBounds(0, 0, super.getWidth(), super.getHeight());
        super.add(draw);

        super.setVisible(true);
    }

    /**
     * Fügt ein {@link Drawable} zu diesem Fenster hinzu, welche bei jedem repaint() Aufruf gezeichnet wird.
     *
     * @param drawable Das {@link Drawable}, welches diesem Fenster hinzugefügt werden soll.
     */
    protected void addDrawable(@NotNull final Drawable drawable) {
        this.drawables.add(drawable);
    }


    //<editor-fold desc="Draw">

    /**
     * Ein {@link Draw} stellt eine Zeichen-Instanz dar, mit dessen Hilfe alle grafischen Komponenten auf dieses Fenster
     * gezeichnet werden.
     */
    public final class Draw extends JLabel {

        //<editor-fold desc="implementation">
        @Override
        protected void paintComponent(@NotNull final Graphics g) {
            super.paintComponent(g);

            final Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            for (final Drawable drawable : drawables) {
                drawable.draw(g2d);
            }
        }
        //</editor-fold>
    }
    //</editor-fold>

}
