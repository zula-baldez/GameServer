package com.server.game_process_util;

public class ValidationResponse {
    public boolean valid;
    public boolean needChangeTurn;

    public ValidationResponse(boolean valid, boolean needChangeTurn) {
        this.valid = valid;
        this.needChangeTurn = needChangeTurn;
    }
}
