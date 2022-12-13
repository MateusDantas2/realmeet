package br.com.sw2you.realmeet.controller;

import br.com.sw2you.realmeet.api.facade.AllocationsApi;
import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateRoomDTO;
import br.com.sw2you.realmeet.api.model.RoomDTO;
import br.com.sw2you.realmeet.service.AllocationService;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import static java.util.concurrent.CompletableFuture.supplyAsync;

@RestController
public class AllocationController implements AllocationsApi {
    private final Executor controllersExecutor;
    private final AllocationService allocationService;

    public AllocationController(Executor controllersExecutor, AllocationService allocationService) {
        this.controllersExecutor = controllersExecutor;
        this.allocationService = allocationService;
    }

    @Override
    public CompletableFuture<ResponseEntity<AllocationDTO>> createAllocation(CreateAllocationDTO createAllocationDTO) {
        return supplyAsync(() -> allocationService.createAllocation(createAllocationDTO), controllersExecutor)
                .thenApply(ResponseEntityUtils::created);
    }
}
