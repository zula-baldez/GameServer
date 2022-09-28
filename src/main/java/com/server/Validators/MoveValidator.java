package com.server.Validators;

import com.server.game.process.data.FieldType;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import org.springframework.stereotype.Component;

@Component
public class MoveValidator {

/*
    public boolean ValidateMove(Player player, Card preCard, Card postcard, Suit kozir) {
        if (preCard.Attack < postcard.Attack && preCard.suit == postcard.suit) return true;

        if (preCard.Attack < postcard.Attack && postcard.suit == kozir && !preCard.isPenek) return true;

        player.addFine();
        return false;
    }*/

    public ValidationResponse ValidateDistribution(Room room, Player player, Card preCard, Card postcard, FieldType fieldType) {

        if ((postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 2)) &&
                fieldType == FieldType.ENEMY_HAND) return new ValidationResponse(true, false);
        if ((fieldType == FieldType.ENEMY_HAND)) {
            player.addFine();
            return new ValidationResponse(false, true);
        }
        for (Player enemy : room.getGameManager().getGame().getPlayers()) {
            if (enemy.equals(player)) {
                continue;
            }
            if (enemy.getPlayerHand().size() == 0) continue;
            Card previousCard = enemy.getPlayerHand().get(enemy.getPlayerHand().size() - 1);
            if (previousCard == null || previousCard.isPenek) continue;

            if (postcard.attack - previousCard.attack == 1 || (previousCard.attack == 14 && postcard.attack == 2)) {
                player.addFine();
                return new ValidationResponse(false, true);
            }
        }

        if (postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 2)) {
            return new ValidationResponse(true, false);
        }


        return new ValidationResponse(true, true);
    }


}
