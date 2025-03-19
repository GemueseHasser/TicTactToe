package de.gemuesehasser.tictactoe.object;

import de.gemuesehasser.tictactoe.TicTacToe;
import de.gemuesehasser.tictactoe.constant.CombinationType;
import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.gui.GameEndGui;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Ein {@link Computer} kann eigenständig den besten Platz zum Setzen herausfinden und dort sein Symbol platzieren.
 * Dabei überprüft der Computer vor und nach jedem seiner Züge, ob das Spiel bereits gewonnen oder unentschieden ist.
 */
@Setter
public final class Computer implements Drawable {

    //<editor-fold desc="CONSTANTS">
    /** Der Scheduler, wodurch das Platzieren des Computers verzögert wird, um die Darstellung dynamischer zu machen. */
    @NotNull
    private static final ScheduledExecutorService SCHEDULER = Executors.newScheduledThreadPool(1);
    //</editor-fold>


    //<editor-fold desc="LOCAL FIELDS">
    /** Der Typ der Kombination, mit der ein bestimmter {@link UserType} gewonnen hat (Standardmäßig {@code null}). */
    @Nullable
    private CombinationType winCombinationType;
    //</editor-fold>


    /**
     * Platziert das Symbol des Computers an der günstigsten Stelle und prüft sowohl vorher als auch nachher, ob das
     * Spiel bereits gewonnen bzw. unentschieden ist.
     */
    public void place() {
        SCHEDULER.schedule(() -> {
            final CombinationType userWinCombination = checkWin(UserType.USER);
            if (userWinCombination != null) {
                winCombinationType = userWinCombination;
                handleGameEnd(UserType.USER);
                return;
            }

            if (isIndecisive()) {
                handleGameEnd(null);
                return;
            }

            final Point bestPlace = getBestPlacement();
            final GameField bestPlaceField = TicTacToe.GAME_FIELD_HANDLER.getField(bestPlace.x, bestPlace.y);

            assert bestPlaceField != null;
            bestPlaceField.updateUserType(UserType.COMPUTER);

            final CombinationType computerWinCombination = checkWin(UserType.COMPUTER);
            if (computerWinCombination != null) {
                winCombinationType = computerWinCombination;
                handleGameEnd(UserType.COMPUTER);
                return;
            }

            if (isIndecisive()) {
                handleGameEnd(null);
                return;
            }

            TicTacToe.setCurrentUserType(UserType.USER);
        }, 500, TimeUnit.MILLISECONDS);
    }


    /**
     * Öffnet das {@link GameEndGui} für den Sieg eines bestimmten {@link UserType Typen}. Wenn der {@link UserType Typ}
     * {@code null} ist, wird das Fenster für ein Unentschieden geöffnet. Außerdem wird der letzte Gewinner gesetzt,
     * damit beim Neustart des Spiels ermittelt werden kann, wer den ersten Zug machen darf. Sollte das Spiel
     * unentschieden sein, wird der letzte Gewinner automatisch auf den Typen des Computers gesetzt, damit der Nutzer
     * den ersten Zug machen darf. Zuletzt wird der Punktestand des jeweiligen Typen des Gewinners um 1 erhöht - sofern
     * das Spiel nicht unentschieden ausgegangen ist. Die Grafik-Instanz des
     * {@link de.gemuesehasser.tictactoe.gui.GameGui}, welches als Konstante in der Haupt- und Main-Klasse dieser
     * Anwendung abgespeichert ist, wird aktualisiert, um immer den aktuellen Punktestand anzuzeigen.
     *
     * @param userType Der {@link UserType Typ} des Siegers bzw. {@code null} wenn das Spiel unentschieden ist.
     */
    private void handleGameEnd(@Nullable final UserType userType) {
        final String title = (userType == null ? "Unentschieden" : userType == UserType.USER ? "Gewonnen" : "Verloren");
        final String text = (userType == null ? "Es ist unentschieden!" : userType == UserType.USER ? "Du hast das Spiel gewonnen!" : "Du hast das Spiel verloren!");

        final GameEndGui gui = new GameEndGui(title, text);
        gui.open();

        TicTacToe.GAME_FIELD_HANDLER.setLastWinner(userType == null ? UserType.COMPUTER : userType);

        if (userType == null) return;

        userType.incrementPoints();
        TicTacToe.GAME_GUI.repaint();
    }

    /**
     * Prüft, ob ein bestimmter {@link UserType Typ} das Spiel gewonnen hat anhand aller Werte des
     * {@link CombinationType}. Sollte der Typ gewonnen haben, wird der Typ der Kombination zurückgegeben, mit der der
     * {@link UserType Typ} gewonnen hat.
     *
     * @param userType Der {@link UserType Typ}, für den ein Sieg des Spiels überprüft werden soll.
     *
     * @return Wenn der {@link UserType Typ} das Spiel gewonnen hat, wird der {@link CombinationType Kombinations-Typ}
     *      zurückgegeben, ansonsten {@code null}.
     */
    @Nullable
    private CombinationType checkWin(@NotNull final UserType userType) {
        for (@NotNull final CombinationType combinationType : CombinationType.values()) {
            boolean win = true;

            for (final Point combinationPoint : combinationType.getCombinationPoints()) {
                final GameField field = TicTacToe.GAME_FIELD_HANDLER.getField(combinationPoint.x, combinationPoint.y);

                assert field != null;
                if (field.getUserType() == null || field.getUserType() != userType) {
                    win = false;
                    break;
                }
            }

            if (win) return combinationType;
        }

        return null;
    }

    /**
     * Prüft, ob alle Felder bereits belegt sind, also ob das Spiel unentschieden ist.
     *
     * @return Wenn alle Felder des Spielfeldes belegt sind {@code true}, ansonsten {@code false}.
     */
    private boolean isIndecisive() {
        for (@NotNull final GameField field : TicTacToe.GAME_FIELD_HANDLER.getFields()) {
            if (field.getUserType() == null) return false;
        }

        return true;
    }

    /**
     * Gibt den günstigsten Punkt zum Setzen zurück. Dabei wird zuerst überprüft, ob es einen Punkt gibt, mit dem der
     * Computer gewinnen kann. Wenn es da keinen Punkt gibt, wird überprüft, ob es einen Punkt gibt, wodurch der Nutzer
     * gewinnen kann. Sollte es diesen auch nicht geben, wird ein zufälliger Punkt auf dem Spielfeld zurückgegeben.
     *
     * @return Der günstigste Punkt zum Setzen.
     */
    @NotNull
    private Point getBestPlacement() {
        // check if computer can win
        final Point computerWinPoint = getWinPoint(UserType.COMPUTER);

        if (computerWinPoint != null) return computerWinPoint;

        // check if user can win
        final Point userWinPoint = getWinPoint(UserType.USER);

        if (userWinPoint != null) return userWinPoint;

        // check middle field
        final GameField middleField = TicTacToe.GAME_FIELD_HANDLER.getField(1, 1);

        assert middleField != null;
        if (middleField.getUserType() == null) return new Point(1, 1);

        // place random
        final int randomBounds = TicTacToe.GAME_FIELD_HANDLER.getFields().size();
        GameField randomField;

        do {
            final int random = ThreadLocalRandom.current().nextInt(randomBounds);
            randomField = TicTacToe.GAME_FIELD_HANDLER.getField(random % 3, random / 3);
        } while (randomField == null || randomField.getUserType() != null);

        return new Point(randomField.getRow(), randomField.getColumn());
    }

    /**
     * Prüft, ob es einen Punkt gibt, mit dem ein bestimmter {@link UserType Typ} gewinnen kann. Wenn dies der Fall ist,
     * wird dieser Punkt zurückgegeben, ansonsten {@code null}.
     *
     * @param userType Der {@link UserType Typ}, für den überprüft werden soll, ob es einen Punkt gibt, mit dem dieser
     *                 das Spiel gewinnen kann.
     * @return Der Punkt, mit dem ein bestimmter {@link UserType Typ} gewinnen kann. Wenn kein entsprechender Punkt
     * existiert, {@code null}.
     */
    @Nullable
    private Point getWinPoint(@NotNull final UserType userType) {
        for (@NotNull final CombinationType combinationType : CombinationType.values()) {
            final Point[] combinationPoints = combinationType.getCombinationPoints();
            if (isWinCombination(combinationPoints[0], combinationPoints[1], combinationPoints[2], userType))
                return combinationPoints[2];
            if (isWinCombination(combinationPoints[0], combinationPoints[2], combinationPoints[1], userType))
                return combinationPoints[1];
            if (isWinCombination(combinationPoints[1], combinationPoints[2], combinationPoints[0], userType))
                return combinationPoints[0];
        }

        return null;
    }

    /**
     * Prüft, ob ein bestimmter {@link UserType Typ} durch einen bestimmten Punkt auf dem Spielfeld gewinnen kann.
     *
     * @param p1            Der erste Punkt, der schon zu dem {@link UserType Typ} gehören muss.
     * @param p2            Der zweite Punkt, der schon zu dem {@link UserType Typ} gehören muss.
     * @param possiblePlace Der Punkt, der überprüft werden soll, ob der {@link UserType Typ} durch diesen gewinnen
     *                      kann.
     * @param userType      Der {@link UserType Typ}, für den diese Kombination überprüft werden soll.
     * @return Wenn die drei übergebenen Punkte so zusammenhängen, dass der Nutzer durch das {@code possiblePlace}
     * gewinnen kann {@code true}, ansonsten {@code false}.
     */
    private boolean isWinCombination(
            @NotNull final Point p1,
            @NotNull final Point p2,
            @NotNull final Point possiblePlace,
            @NotNull final UserType userType
    ) {
        final GameField field1 = TicTacToe.GAME_FIELD_HANDLER.getField(p1.x, p1.y);
        final GameField field2 = TicTacToe.GAME_FIELD_HANDLER.getField(p2.x, p2.y);
        final GameField possibleField = TicTacToe.GAME_FIELD_HANDLER.getField(possiblePlace.x, possiblePlace.y);

        assert field1 != null;
        assert field2 != null;
        assert possibleField != null;
        return field1.getUserType() == userType && field2.getUserType() == userType && possibleField.getUserType() == null;
    }

    //<editor-fold desc="implementation">

    @Override
    public void draw(@NotNull final Graphics2D g) {
        if (this.winCombinationType == null) return;

        for (@NotNull final Point point : this.winCombinationType.getCombinationPoints()) {
            final GameField field = TicTacToe.GAME_FIELD_HANDLER.getField(point.x, point.y);

            assert field != null;
            g.setColor(Color.GREEN);
            g.fillOval(
                    field.getButton().getX() + 5,
                    field.getButton().getY() + 5,
                    field.getButton().getWidth() - 10,
                    field.getButton().getHeight() - 10
            );
        }
    }
    //</editor-fold>
}
