package com.server.game.process.data;

import com.server.game.process.util.Card;

public record MovingCardData(int playerId, int dropPlayerId, Card card) {
}
