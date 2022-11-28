package br.com.sw2you.realmeet.controller;

import static java.util.concurrent.CompletableFuture.runAsync;
import static java.util.concurrent.CompletableFuture.supplyAsync;

import br.com.sw2you.realmeet.api.facade.RoomsApi;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.api.model.UpdateRoomDTO;
import br.com.sw2you.realmeet.service.RoomService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController implements RoomsApi {
    private final Executor controllersExecutor;
    private final RoomService roomService;

    public RoomController(Executor controllersExecutor, RoomService roomService) {
        this.roomService = roomService;
        this.controllersExecutor = controllersExecutor;
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> getRoom(Long id) {
        return supplyAsync(() -> roomService.getRoom(id), controllersExecutor).thenApply(ResponseEntityUtils::ok);
    }

    @Override
    public CompletableFuture<ResponseEntity<RoomDTO>> createRoom(CreateRoomDTO createRoomDTO) {
        return supplyAsync(() -> roomService.createRoom(createRoomDTO), controllersExecutor)
            .thenApply(ResponseEntityUtils::created);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteRoom(Long id) {
        return runAsync(() -> roomService.deletedRoom(id), controllersExecutor)
            .thenApply(ResponseEntityUtils::noContent);
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateRoom(Long id, UpdateRoomDTO updateRoomDTO) {
        return runAsync(() -> roomService.updateRoom(id, updateRoomDTO), controllersExecutor)
                .thenApply(ResponseEntityUtils::noContent);
    }
}
