package de.gemuesehasser.tictactoe.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Ein {@link CombinationType Typ} stellt eine Kombination dar, mit der man das Spiel gewinnen kann.
 */
@Getter
@RequiredArgsConstructor
public enum CombinationType {

    //<editor-fold desc="VALUES">
    /** Horizontale Kombination in der ersten Zeile. */
    WIN_COMBINATION_1(new Point @NotNull []{new Point(0, 0), new Point(1, 0), new Point(2, 0)}),
    /** Horizontale Kombination in der zweiten Zeile. */
    WIN_COMBINATION_2(new Point @NotNull []{new Point(0, 1), new Point(1, 1), new Point(2, 1)}),
    /** Horizontale Kombination in der dritten Zeile. */
    WIN_COMBINATION_3(new Point @NotNull []{new Point(0, 2), new Point(1, 2), new Point(2, 2)}),
    /** Vertikale Kombination in der ersten Spalte. */
    WIN_COMBINATION_4(new Point @NotNull []{new Point(0, 0), new Point(0, 1), new Point(0, 2)}),
    /** Vertikale Kombination in der zweiten Spalte. */
    WIN_COMBINATION_5(new Point @NotNull []{new Point(1, 0), new Point(1, 1), new Point(1, 2)}),
    /** Vertikale Kombination in der dritten Spalte. */
    WIN_COMBINATION_6(new Point @NotNull []{new Point(2, 0), new Point(2, 1), new Point(2, 2)}),
    /** Diagonale Kombination von oben links nach unten rechts. */
    WIN_COMBINATION_7(new Point @NotNull []{new Point(0, 0), new Point(1, 1), new Point(2, 2)}),
    /** Diagonale Kombination von unten links nach oben rechts. */
    WIN_COMBINATION_8(new Point @NotNull []{new Point(2, 0), new Point(1, 1), new Point(0, 2)});
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Die Ansammlung von Punkten, aus der die Kombination besteht. */
    @NotNull
    private final Point @NotNull [] combinationPoints;
    //</editor-fold>

}
