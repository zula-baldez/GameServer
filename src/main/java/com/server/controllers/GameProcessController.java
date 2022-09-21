package com.server.controllers;

import com.server.exception.NoSuchPlayerException;
import com.server.game_process_util.Player;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RestController
@Component
@ComponentScan

public class GameProcessController {
    private final ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    private final int sleepTime = 100;
    List<DeferredResult<String>> clients = new ArrayList<>();
    @ResponseBody
    @RequestMapping(value = "/connection/enter_room", method = RequestMethod.GET)
    public DeferredResult<String> enterRoom(@RequestParam int id, @RequestParam int roomId) {
        DeferredResult<String> output  = new DeferredResult<>();
        clients.add(output);
        return output;

    }

    public void sendAction() {

    }


}
