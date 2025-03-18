package de.gemuesehasser.tictactoe.handler;

import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.gui.GameGui;
import de.gemuesehasser.tictactoe.object.GameField;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

/**
 * Mithilfe des {@link GameFieldHandler} lässt sich eine Ansammlung an {@link GameField Feldern} verwalten.
 */
@Getter
public final class GameFieldHandler {

    //<editor-fold desc="LOCAL FIELDS">
    /** Alle abgespeicherten Felder, die durch diese Instanz des Handlers verwaltet werden. */
    @NotNull
    private final List<GameField> fields = new ArrayList<>();
    /** Der {@link UserType Typ}, der zuletzt gewonnen hat innerhalb dieser Ansammlung an Feldern. */
    @Getter
    @Setter
    @Nullable
    private UserType lastWinner;
    //</editor-fold>


    /**
     * Resettet alle Felder, die in diesem Handler abgespeichert sind.
     */
    public void resetFields() {
        for (@NotNull final GameField field : fields) {
            field.updateUserType(null);
        }
    }

    /**
     * Registriert ein neues {@link GameField Feld} in diesem Handler.
     *
     * @param field Das Feld, welches registriert bzw. hinzugefügt werden soll.
     */
    public void registerNewField(@NotNull final GameField field) {
        this.fields.add(field);
    }

    /**
     * Sucht anhand der Zeile und der Spalte das entsprechende Feld aus allen registrierten Feldern heraus und gibt
     * dieses zurück, sofern es vorhanden ist.
     *
     * @param row    Die Zeile, in der das Feld herausgesucht werden soll.
     * @param column Die Spalte, in der das Feld herausgesucht werden soll.
     *
     * @return Das Feld mit der entsprechenden Zeile und Spalte. Sollte das Feld mit den entsprechenden Eigenschaften
     *      nicht registriert sein, {@code null}.
     */
    public GameField getField(
            @Range(from = 0, to = GameGui.GAME_SIZE - 1) final int row,
            @Range(from = 0, to = GameGui.GAME_SIZE - 1) final int column
    ) {
        for (final GameField field : this.fields) {
            if (field.getRow() == row && field.getColumn() == column) return field;
        }

        return null;
    }

}
