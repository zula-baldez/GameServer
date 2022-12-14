package com.server.util.dto;

import com.server.game.process.data.ActionTypes;
import com.server.game.process.data.Suit;
import com.server.game.process.util.Card;
import com.server.game.process.util.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Action(ActionTypes actionTypes, Map<Integer, List<Card>> playersHand,
                     List<Card> field, List<Card> anotherCards, int playerIdTurn, Suit kozir) {}

