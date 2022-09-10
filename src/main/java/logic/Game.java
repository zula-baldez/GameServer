package logic;

import com.server.Card;
import com.server.Player;
import com.server.Suit;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Card> field = new ArrayList<>();

    private Suit kozir = Suit.PICK;
    private List<Player> players = new ArrayList<>();
    private List<Card> deck = CardManager.getDeck();
    private int playersAmount = 0;
    public Game() {

    }



}



