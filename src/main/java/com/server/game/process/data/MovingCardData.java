package com.server.game.process.data;

import com.server.game.process.util.Card;

public record MovingCardData(int roomId, int mainPlayerId, int playerFrom, int playerTo, int card) {
}
