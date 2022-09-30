package com.server.Validators;

import com.server.game.process.data.FieldType;
import com.server.game.process.data.Suit;
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
    public ValidationResponse ValidatePlayMove(Room room, Player player, Card preCard, Card postcard, FieldType fieldTypeAfter, FieldType fieldTypeBefore) {
        if (preCard.suit == Suit.PICK) {
            if (postcard.suit == Suit.PICK && postcard.attack > preCard.attack) {
                return new ValidationResponse(true, true);
            } else {
                player.addFine();
                return new ValidationResponse(false, false);
            }
        } else if (preCard.suit == room.getGameManager().getGame().getKozir()) {
            if (postcard.suit == room.getGameManager().getGame().getKozir() && postcard.attack > preCard.attack) {
                return new ValidationResponse(true, true);
            } else {

                player.addFine();
                return new ValidationResponse(false, false);
            }
        } else {
            if (postcard.suit == preCard.suit && postcard.attack > preCard.attack || postcard.suit == room.getGameManager().getGame().getKozir()) {
                return new ValidationResponse(true, true);

            } else {

                player.addFine();
                return new ValidationResponse(false, false);
            }
        }

    }


    public ValidationResponse ValidateDistribution(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        System.out.println(fieldTypeAfter);
        System.out.println(fieldTypeBefore);
        System.out.println(preCard.attack);
        System.out.println(postcard.attack);
        if (fieldTypeBefore == FieldType.ENEMY_HAND) {
            return new ValidationResponse(false, false);
        }
        //check for dropping card from hand to another player
        if (
                fieldTypeBefore == FieldType.SELF_HAND &&
                        fieldTypeAfter == FieldType.ENEMY_HAND && (postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 2))) {
            return new ValidationResponse(true, false);
        }

        System.out.println("test0 passed!");
        //check if player could drop his cards
        if (player.getPlayerHand().size() != 0) {
            for (Player enemy : room.getGameManager().getGame().getPlayers()) {
                if (enemy.equals(player)) {
                    continue;
                }
                Card postCard = player.getPlayerHand().get(player.getPlayerHand().size() - 1);
                Card previousCard;
                if (enemy.getPlayerHand().size() > 0)
                    previousCard = enemy.getPlayerHand().get(enemy.getPlayerHand().size() - 1);
                else
                    previousCard = null;
                if (postCard.isPenek || previousCard == null || previousCard.isPenek || player.getPlayerHand().size() == player.getAmountOfPenki() + 1) continue;


                if (postCard.attack - previousCard.attack == 1 || (previousCard.attack == 14 && postCard.attack == 2)) {
                    player.addFine();
                    return new ValidationResponse(false, false);
                }
            }
        }

        System.out.println("test1 passed!");

        if (fieldTypeBefore == FieldType.SELF_HAND) {

            player.addFine();
            return new ValidationResponse(false, false);
        }


        System.out.println("test2 passed!");
        //check if moving card from field is correct
        if ((postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 2)) &&
                fieldTypeAfter == FieldType.ENEMY_HAND && fieldTypeBefore == FieldType.FIELD)
            return new ValidationResponse(true, false);
        if ((fieldTypeAfter == FieldType.ENEMY_HAND)) {
            player.addFine();
            return new ValidationResponse(false, false);
        }

        System.out.println("test3 passed!");

        for (Player enemy : room.getGameManager().getGame().getPlayers()) {
            if (enemy.getId() == player.getId()) {
                continue;
            }
            if (enemy.getPlayerHand().size() == 0) continue;
            Card prCard;
            prCard = enemy.getPlayerHand().get(enemy.getPlayerHand().size() - 1);
            if (prCard == null || prCard.isPenek) continue;

            if (postcard.attack - prCard.attack == 1 || (prCard.attack == 14 && postcard.attack == 2)) {
                player.addFine();
                return new ValidationResponse(false, false);
            }
        }

        System.out.println("test4 passed!");
        if (postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 2)) {
            return new ValidationResponse(true, false);
        }


        System.out.println("test5 passed!");
        return new ValidationResponse(true, true);
    }


}
