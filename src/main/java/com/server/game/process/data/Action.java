package com.server.game.process.data;

import com.server.game.process.util.Card;

import java.util.List;

public record Action(ActionTypes actionTypes, List<Card> deckAfter, Card changedCard) {}

