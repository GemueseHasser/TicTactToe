package de.gemuesehasser.tictactoe.object;

import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.gui.GameGui;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import javax.swing.*;

/**
 * Ein {@link GameField} stellt ein Feld auf dem Spielfeld dar, welches im Hintergrund abgespeichert wird.
 */
@Getter
@RequiredArgsConstructor
public final class GameField {

    //<editor-fold desc="LOCAL FIELDS">
    /** Die Zeile, in der sich dieses Feld auf dem Spielfeld befindet. */
    @Range(from = 0, to = GameGui.GAME_SIZE - 1)
    private final int row;
    /** Die Spalte, in der sich dieses Feld auf dem Spielfeld befindet. */
    @Range(from = 0, to = GameGui.GAME_SIZE - 1)
    private final int column;
    /** Der Button, der dieses Feld auf dem Spielfeld in dem Fenster repräsentiert. */
    @NotNull
    private final JButton button;
    /** Der Typ des Benutzers, welcher dieses Feld belegt bzw. {@code null}, wenn dieses Feld noch nicht belegt ist. */
    @Nullable
    private UserType userType;
    //</editor-fold>


    /**
     * Aktualisiert den Typen des Benutzers, der dieses Feld belegt.
     *
     * @param userType Der neue Typ des Benutzers, der dieses Feld belegen soll.
     */
    public void updateUserType(@Nullable final UserType userType) {
        this.userType = userType;

        if (userType == null) {
            this.button.setIcon(null);
            return;
        }

        this.button.setIcon(new ImageIcon(userType.getImage()));
    }

}
