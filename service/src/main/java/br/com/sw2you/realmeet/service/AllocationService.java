package br.com.sw2you.realmeet.service;

import static br.com.sw2you.realmeet.domain.entity.Allocation.SORTABLE_FIELDS;
import static br.com.sw2you.realmeet.util.Constants.ALLOCAITONS_MAX_FILTER_LIMIT;
import static br.com.sw2you.realmeet.util.DateUtils.*;
import static br.com.sw2you.realmeet.util.PageUtils.newPageable;
import static java.util.Objects.*;
import static java.util.concurrent.CompletableFuture.runAsync;

import br.com.sw2you.realmeet.api.model.AllocationDTO;
import br.com.sw2you.realmeet.api.model.CreateAllocationDTO;
import br.com.sw2you.realmeet.api.model.UpdateAllocationDTO;
import br.com.sw2you.realmeet.domain.entity.Allocation;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.exception.AllocationCannotBeDeleteException;
import br.com.sw2you.realmeet.exception.AllocationCannotBeUpdatedException;
import br.com.sw2you.realmeet.exception.AllocationNotFoundException;
import br.com.sw2you.realmeet.exception.RoomNotFoundException;
import br.com.sw2you.realmeet.mapper.AllocationMapper;
import br.com.sw2you.realmeet.util.DateUtils;
import br.com.sw2you.realmeet.util.PageUtils;
import br.com.sw2you.realmeet.util.ResponseEntityUtils;
import br.com.sw2you.realmeet.validator.AllocationValidator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AllocationService {
    private final RoomRepository roomRepository;
    private final AllocationRepository allocationRepository;
    private final AllocationValidator allocationValidator;
    private final AllocationMapper allocationMapper;
    private final int maxLimit;

    public AllocationService(
        RoomRepository roomRepository,
        AllocationRepository allocationRepository,
        AllocationValidator allocationValidator,
        AllocationMapper allocationMapper,
        @Value(ALLOCAITONS_MAX_FILTER_LIMIT) int maxLimit
    ) {
        this.roomRepository = roomRepository;
        this.allocationRepository = allocationRepository;
        this.allocationValidator = allocationValidator;
        this.allocationMapper = allocationMapper;
        this.maxLimit = maxLimit;
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
        var allocation = getAllocationOrThrow(allocationId);

        if (isAllocationInThePast(allocation)) {
            throw new AllocationCannotBeDeleteException();
        }

        allocationRepository.delete(allocation);
    }

    @Transactional
    public void updateAllocation(Long allocationId, UpdateAllocationDTO updateAllocationDTO) {
        var allocation = getAllocationOrThrow(allocationId);

        if (isAllocationInThePast(allocation)) {
            throw new AllocationCannotBeUpdatedException();
        }

        allocationValidator.validate(allocationId, updateAllocationDTO);

        allocationRepository.updateAllocation(
            allocationId,
            updateAllocationDTO.getSubject(),
            updateAllocationDTO.getStartAt(),
            updateAllocationDTO.getEndAt()
        );
    }

    public List<AllocationDTO> listAllocations(
        String employeeEmail,
        Long roomId,
        LocalDate startAt,
        LocalDate endAt,
        String orderBy,
        Integer limit,
        Integer page
    ) {
        Pageable pageable = newPageable(page, limit, maxLimit, orderBy, SORTABLE_FIELDS);

        var allocations = allocationRepository.findAllWithFilters(
            employeeEmail,
            roomId,
            isNull(startAt) ? null : startAt.atTime(LocalTime.MIN).atOffset(DEFAULT_TIMEZONE),
            isNull(endAt) ? null : endAt.atTime(LocalTime.MAX.atOffset(DEFAULT_TIMEZONE)),
            pageable
        );

        return allocations.stream().map(allocationMapper::fromEntityToAllocationDTO).collect(Collectors.toList());
    }

    private Allocation getAllocationOrThrow(Long allocationId) {
        return allocationRepository
            .findById(allocationId)
            .orElseThrow(() -> new AllocationNotFoundException("Allocation not found: " + allocationId));
    }

    public boolean isAllocationInThePast(Allocation allocation) {
        return allocation.getEndAt().isBefore(now());
    }
}
