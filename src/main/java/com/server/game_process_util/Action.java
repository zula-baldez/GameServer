package com.server.game_process_util;

import java.util.List;

public class Action {
    private ActionTypes actionTypes;
    private List<Card> deckAfter;
    private Card changedCard;

    public Action(ActionTypes actionTypes, List<Card> deckAfter, Card changedCard) {
        this.actionTypes = actionTypes;
        this.deckAfter = deckAfter;
        this.changedCard = changedCard;
    }

    public ActionTypes getActionTypes() {
        return actionTypes;
    }

    public void setActionTypes(ActionTypes actionTypes) {
        this.actionTypes = actionTypes;
    }

    public List<Card> getDeckAfter() {
        return deckAfter;
    }

    public void setDeckAfter(List<Card> deckAfter) {
        this.deckAfter = deckAfter;
    }

    public Card getChangedCard() {
        return changedCard;
    }

    public void setChangedCard(Card changedCard) {
        this.changedCard = changedCard;
    }
}
