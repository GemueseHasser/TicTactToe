package de.gemuesehasser.tictactoe.constant;

import de.gemuesehasser.tictactoe.gui.GameGui;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Ein {@link UserType Typ} wird für beide Nutzer erzeugt, die in diesem Spiel teilnehmen sollen. Beim Erzeugen
 * eines Typen wird das Bild direkt geladen, welches dieser Typ auf einem
 * {@link de.gemuesehasser.tictactoe.object.GameField Feld} platzieren kann.
 */
@Getter
public enum UserType {

    //<editor-fold desc="VALUES">
    /** Der Nutzer, der diese Anwendung gestartet hat. */
    USER("o.png"),
    /** Der Computer, also der Gegenspieler des Nutzers, der diese Anwendung gestartet hat. */
    COMPUTER("x.png");
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Das Bild, welches diesen Typen beschreibt. */
    @NotNull
    private final BufferedImage image = new BufferedImage(GameGui.FIELD_SIZE, GameGui.FIELD_SIZE, BufferedImage.TYPE_INT_ARGB);
    /** Die Anzahl an Punkten, die ein {@link UserType Typ} bereits durch gewonnene Runden erzielt hat. */
    @Getter
    private int points;
    //</editor-fold>


    //<editor-fold desc="CONSTRUCTORS">

    /**
     * Erzeugt einen neuen und vollständig unabhängigen {@link UserType}. Ein {@link UserType Typ} wird für beide Nutzer
     * erzeugt, die in diesem Spiel teilnehmen sollen. Beim Erzeugen eines Typen wird das Bild direkt geladen, welches
     * dieser Typ auf einem {@link de.gemuesehasser.tictactoe.object.GameField Feld} platzieren kann.
     *
     * @param imageName Der Name des Bildes, welches mithilfe dieses Typen geladen werden soll.
     */
    UserType(@NotNull final String imageName) {
        try {
            final BufferedImage loadedImage = ImageIO.read(Objects.requireNonNull(
                    getClass().getResourceAsStream("/" + imageName)
            ));
            this.image.getGraphics().drawImage(loadedImage, 10, 10, GameGui.FIELD_SIZE - 20, GameGui.FIELD_SIZE - 20, null);
        } catch (@NotNull final IOException e) {
            throw new RuntimeException(e);
        }
    }
    //</editor-fold>


    /**
     * Erhöht die Anzahl der bisher erlangten Punkte dieses Typen um 1. Die Anzahl der Punkte stellt die Anzahl der
     * gewonnenen Runden im {@link de.gemuesehasser.tictactoe.TicTacToe Spiel} dar.
     */
    public void incrementPoints() {
        this.points++;
    }

}
