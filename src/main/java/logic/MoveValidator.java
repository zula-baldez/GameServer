package logic;

import com.server.Card;
import com.server.Player;
import com.server.Suit;
import org.springframework.beans.factory.annotation.Autowired;

public class MoveValidator {
/*
    @Autowired
    private GameManager gameManager;
    public MoveValidator(GameManager gameManager) {
        this.gameManager = gameManager;
    }
    public static boolean ValidateMove(Player player, Card preCard, Card postcard, Suit kozir)
    {
        if (preCard.Attack < postcard.Attack && preCard.suit == postcard.suit) return true;

        if (preCard.Attack < postcard.Attack && postcard.suit == kozir && !preCard.isPenek) return true;

        player.fines++;
        return false;
    }
    public static boolean ValidateRazdacha(Player player, Card preCard, Card postcard, FieldType fieldType)
    {

        if ((postcard.Attack - preCard.Attack == 1 || (preCard.Attack == 14 && postcard.Attack == 2)) &&
                fieldType == FieldType.EnemyHand) return true;
        if ((fieldType == FieldType.EnemyHand))
        {
            _gameManagerScript.ChangeTurn();
            player.fines++;
            return false;
        }
        foreach (var t in _gameManagerScript.CurrentGame.Enemies)
        {
            if (t.PlayerHand.Count == 0) continue;
            Card previousCard = t.PlayerHand[^1];
            if (previousCard == null || previousCard.isPenek) continue;

            if (postcard.Attack - previousCard.Attack == 1 || (previousCard.Attack == 14 && postcard.Attack == 2))
            {
                WarningWindowScript.ShowMessage("Можно было положить карту другому игроку! Хуевая!");
                _gameManagerScript.ChangeTurn();
                player.HuevieAmount++;
                return false;
            }
        }

        if (postcard.Attack - preCard.Attack == 1 || (preCard.Attack == 14 && postcard.Attack == 2))
        {
            return true;
        }


        _gameManagerScript.ChangeTurn();
        return true;
    }

*/

}
