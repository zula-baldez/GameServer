package com.server.game_process;

import com.server.game_process_util.Card;
import com.server.game_process_util.FieldType;
import com.server.game_process_util.Player;
import com.server.game_process_util.Suit;
import com.server.game_process_util.ValidationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MoveValidator {
/*
    @Autowired
    private GameManager gameManager;

    public boolean ValidateMove(Player player, Card preCard, Card postcard, Suit kozir) {
        if (preCard.Attack < postcard.Attack && preCard.suit == postcard.suit) return true;

        if (preCard.Attack < postcard.Attack && postcard.suit == kozir && !preCard.isPenek) return true;

        player.addFine();
        return false;
    }

    public ValidationResponse ValidateDistribution(Player player, Card preCard, Card postcard, FieldType fieldType) {

        if ((postcard.Attack - preCard.Attack == 1 || (preCard.Attack == 14 && postcard.Attack == 2)) &&
                fieldType == FieldType.EnemyHand) return new ValidationResponse(true, false);
        if ((fieldType == FieldType.EnemyHand)) {
            player.addFine();
            return new ValidationResponse(false, true);
        }
        for (Player enemy : gameManager.getGame().getPlayers()) {
            if (enemy.equals(player)) {
                continue;
            }
            if (enemy.getPlayerHand().size() == 0) continue;
            Card previousCard = enemy.getPlayerHand().get(enemy.getPlayerHand().size() - 1);
            if (previousCard == null || previousCard.isPenek) continue;

            if (postcard.Attack - previousCard.Attack == 1 || (previousCard.Attack == 14 && postcard.Attack == 2)) {
                player.addFine();
                return new ValidationResponse(false, true);
            }
        }

        if (postcard.Attack - preCard.Attack == 1 || (preCard.Attack == 14 && postcard.Attack == 2)) {
            return new ValidationResponse(true, false);
        }


        return new ValidationResponse(true, true);
    }*/


}
