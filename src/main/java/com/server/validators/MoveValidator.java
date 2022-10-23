package com.server.validators;

import com.server.game.process.PlayerHandler;
import com.server.game.process.data.FieldType;
import com.server.game.process.data.Suit;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;
import com.server.rooms.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class MoveValidator {

    public ValidationResponse ValidatePlayMove(Room room, Player player, Card preCard, Card postcard) {
        ValidationResponse val = null;
        val = validatePrecardPick(room, player, preCard, postcard);
        if(val != null) return val;

        val = validateIfPrecardIsKozir(room, player, preCard, postcard);
        if(val != null) return val;

        val = validateIfPrecardIsNotKozirOrPick(room, player, preCard, postcard);
        return val;

    }



    private ValidationResponse validatePrecardPick(Room room, Player player, Card preCard, Card postcard) {
        if (preCard.suit == Suit.PICK) {
            if (postcard.suit == Suit.PICK && postcard.attack > preCard.attack) {
                return new ValidationResponse(true, true);
            } else {
                 PlayerHandler.addFine(player);
                return new ValidationResponse(false, false);
            }
        }
        return null;
    }

    private ValidationResponse validateIfPrecardIsKozir(Room room, Player player, Card preCard, Card postcard) {
        if (preCard.suit == room.getGameManager().getGame().getKozir()) {
            if (postcard.suit == room.getGameManager().getGame().getKozir() && postcard.attack > preCard.attack) {
                return new ValidationResponse(true, true);
            } else {

                PlayerHandler.addFine(player);
                return new ValidationResponse(false, false);
            }
        }
        return null;
    }

    private ValidationResponse validateIfPrecardIsNotKozirOrPick(Room room, Player player, Card preCard, Card postcard) {
        if (postcard.suit == preCard.suit && postcard.attack > preCard.attack || postcard.suit == room.getGameManager().getGame().getKozir()) {
            return new ValidationResponse(true, true);

        } else {

            PlayerHandler.addFine(player);
            return new ValidationResponse(false, false);
        }
    }










    public ValidationResponse ValidateDistribution(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        ValidationResponse val = null;

        val = checkIfFieldBeforeEnemy(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;


        val = checkIfDroppingToAnotherPlayerCorrect(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;


        val = checkIfPlayerCouldPutCardToAnotherPlayer(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;


        val = checkIfPlayerGettingCardFromField(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;

        val = checkIfMovingCardFromFieldToEnemyIsCorrect(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;

        val = checkIfPLayerCouldPutCardToEnemy(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;

        val = checkIfPuttingCardToSelfDontChangeTurn(room, player, preCard, postcard, fieldTypeAfter, fieldTypeBefore);
        if (val != null) return val;

        //player put card to himself and there was no reason to put card to another player
        return new ValidationResponse(true, true);
    }

    private ValidationResponse checkIfFieldBeforeEnemy(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        if (fieldTypeBefore == FieldType.ENEMY_HAND) {
            PlayerHandler.addFine(player);

            return new ValidationResponse(false, false);
        }
        return null;
    }

    private ValidationResponse checkIfDroppingToAnotherPlayerCorrect(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        if (fieldTypeBefore == FieldType.SELF_HAND &&
                fieldTypeAfter == FieldType.ENEMY_HAND && (postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 6))) {
            return new ValidationResponse(true, false);
        }
        return null;
    }

    private ValidationResponse checkIfPlayerCouldPutCardToAnotherPlayer(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
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
                if (postCard.isPenek || previousCard == null || previousCard.isPenek || player.getPlayerHand().size() == player.getAmountOfPenki() + 1)
                    continue;


                if (postCard.attack - previousCard.attack == 1 || (previousCard.attack == 14 && postCard.attack == 6)) {
                    PlayerHandler.addFine(player);
                    return new ValidationResponse(false, false);
                }
            }
        }
        return null;
    }

    private ValidationResponse checkIfPlayerGettingCardFromField(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        if (fieldTypeBefore == FieldType.SELF_HAND) {

            PlayerHandler.addFine(player);
            return new ValidationResponse(false, false);
        }
        return null;
    }

    private ValidationResponse checkIfMovingCardFromFieldToEnemyIsCorrect(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        if ((postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 6)) &&
                fieldTypeAfter == FieldType.ENEMY_HAND && fieldTypeBefore == FieldType.FIELD)
            return new ValidationResponse(true, false);
        if ((fieldTypeAfter == FieldType.ENEMY_HAND)) {
            PlayerHandler.addFine(player);
            return new ValidationResponse(false, false);
        }

        return null;
    }
    private ValidationResponse checkIfPLayerCouldPutCardToEnemy(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        for (Player enemy : room.getGameManager().getGame().getPlayers()) {
            if (enemy.getId() == player.getId()) {
                continue;
            }
            if (enemy.getPlayerHand().size() == 0) continue;
            Card prCard;
            prCard = enemy.getPlayerHand().get(enemy.getPlayerHand().size() - 1);
            if (prCard == null || prCard.isPenek) continue;

            if (postcard.attack - prCard.attack == 1 || (prCard.attack == 14 && postcard.attack == 6)) {
                PlayerHandler.addFine(player);
                return new ValidationResponse(false, false);
            }
        }
        return null;
    }

    private ValidationResponse checkIfPuttingCardToSelfDontChangeTurn(Room room, Player player, Card preCard, Card postcard, FieldType
            fieldTypeAfter, FieldType fieldTypeBefore) {
        if (postcard.attack - preCard.attack == 1 || (preCard.attack == 14 && postcard.attack == 6)) {
            return new ValidationResponse(true, false);
        }

        return null;
    }
}
