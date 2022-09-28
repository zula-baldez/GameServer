package com.server.Validators;

public class ValidationResponse {
    private boolean isTurnRight;
    private boolean isNeedToChangeTurn;
    public ValidationResponse(boolean isTurnRight, boolean isNeedToChangeTurn) {
        this.isNeedToChangeTurn = isNeedToChangeTurn;
        this.isTurnRight = isTurnRight;
    }

    public boolean isTurnRight() {
        return isTurnRight;
    }

    public boolean isNeedToChangeTurn() {
        return isNeedToChangeTurn;
    }
}
