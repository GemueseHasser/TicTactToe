package de.gemuesehasser.tictactoe;

import de.gemuesehasser.tictactoe.constant.UserType;
import de.gemuesehasser.tictactoe.gui.GameGui;
import de.gemuesehasser.tictactoe.handler.GameFieldHandler;
import de.gemuesehasser.tictactoe.object.Computer;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * Die Haupt- und Main-Klasse dieser Anwendung. Diese Anwendung stellt ein Tic-Tac-Toe Spiel dar, welches automatisiert
 * durch einen {@link Computer Bot} mit dem Nutzer spielen kann.
 */
public class TicTacToe {

    //<editor-fold desc="CONSTANTS">
    /** Mithilfe dieses Handlers werden alle Felder auf dem Spielfeld verwaltet. */
    @NotNull
    public static final GameFieldHandler GAME_FIELD_HANDLER = new GameFieldHandler();
    /** Der Computer in diesem Spiel, welcher automatisch arbeitet. */
    @NotNull
    public static final Computer COMPUTER = new Computer();
    /** Die Standard-Schriftart die in dieser Anwendung genutzt wird. */
    @NotNull
    public static final Font DEFAULT_FONT = new Font("Arial", Font.PLAIN, 18);
    /** Das {@link de.gemuesehasser.tictactoe.object.Gui Fenster}, in dem das eigentliche Spiel dargestellt wird. */
    @NotNull
    public static final GameGui GAME_GUI = new GameGui();
    //</editor-fold>


    //<editor-fold desc="STATIC FIELDS">
    /** Der Typ des Benutzers, der aktuell an der Reihe ist in diesem Spiel. */
    @Getter
    @Setter
    @NotNull
    private static UserType currentUserType = UserType.USER;
    //</editor-fold>


    //<editor-fold desc="main">

    /**
     * Die Main-Methode dieser Anwendung, die als allererstes von der JRE aufgerufen wird.
     *
     * @param args Die Argumente, die beim Starten der Anwendung von der JRE übergeben werden.
     */
    public static void main(@NotNull final String @NotNull [] args) {
        GAME_GUI.open();
    }
    //</editor-fold>

}
