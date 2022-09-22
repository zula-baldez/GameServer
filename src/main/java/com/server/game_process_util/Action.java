package com.server.game_process_util;

import java.util.List;

public record Action(ActionTypes actionTypes, List<Card> deckAfter, Card changedCard) {}

