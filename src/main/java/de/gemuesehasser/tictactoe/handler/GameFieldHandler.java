package de.gemuesehasser.tictactoe.handler;

import de.gemuesehasser.tictactoe.gui.GameGui;
import de.gemuesehasser.tictactoe.object.GameField;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
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
    private final List<GameField> fields = new ArrayList<>();
    //</editor-fold>


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
            @Range(from = 0, to = GameGui.GAME_SIZE) final int row,
            @Range(from = 0, to = GameGui.GAME_SIZE) final int column
    ) {
        for (final GameField field : this.fields) {
            if (field.getRow() == row && field.getColumn() == column) return field;
        }

        return null;
    }

}
