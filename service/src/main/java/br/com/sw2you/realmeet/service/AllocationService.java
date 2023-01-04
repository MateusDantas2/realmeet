package br.com.sw2you.realmeet.service;

import static br.com.sw2you.realmeet.util.DateUtils.*;
import static java.util.concurrent.CompletableFuture.runAsync;

import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.AllocationCannotBeDeleteException;
import br.com.sw2you.realmeet.exception.AllocationNotFoundException;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.AllocationMapper;
import br.com.sw2you.realmeet.util.DateUtils;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import br.com.sw2you.realmeet.validator.AllocationValidator;
import java.util.concurrent.CompletableFuture;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllocationService {
    private final RoomRepository roomRepository;
    private final AllocationRepository allocationRepository;
    private final AllocationValidator allocationValidator;
    private final AllocationMapper allocationMapper;

    public AllocationService(
        RoomRepository roomRepository,
        AllocationRepository allocationRepository,
        AllocationValidator allocationValidator,
        AllocationMapper allocationMapper
    ) {
        this.roomRepository = roomRepository;
        this.allocationRepository = allocationRepository;
        this.allocationValidator = allocationValidator;
        this.allocationMapper = allocationMapper;
    }

    public AllocationDTO createAllocation(CreateAllocationDTO createAllocationDTO) {
        var room = roomRepository
            .findById(createAllocationDTO.getRoomId())
            .orElseThrow(() -> new RoomNotFoundException("Room not found: " + createAllocationDTO.getRoomId()));
        allocationValidator.validate(createAllocationDTO);

        var allocation = allocationMapper.fromCreateAllocationDTOToEntity(createAllocationDTO, room);
        allocationRepository.save(allocation);
        return allocationMapper.fromEntityToAllocationDTO(allocation);
    }

    public void deleteAllocation(Long allocationId) {
        var allocation = allocationRepository
            .findById(allocationId)
            .orElseThrow(() -> new AllocationNotFoundException("Allocation not found: " + allocationId));

        if (allocation.getEndAt().isBefore(now())) {
            throw new AllocationCannotBeDeleteException();
        }

        allocationRepository.delete(allocation);
    }
}
