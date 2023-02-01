package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.util.DateUtils.*;
import static br.com.sw2you.realmeet.utils.TestConstants.*;
import static br.com.sw2you.realmeet.utils.TestDataCreator.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sw2you.realmeet.api.facade.AllocationApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.util.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AllocationApiFilterIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private AllocationApi api;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void TestFilterAllAllocations() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        var allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).subject(DEFAULT_ALLOCATION_SUBJECT + 1).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).subject(DEFAULT_ALLOCATION_SUBJECT + 2).build()
        );
        var allocation3 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).subject(DEFAULT_ALLOCATION_SUBJECT + 3).build()
        );

        var allocationDTOList = api.listAllocations(null, null, null, null);

        assertEquals(3, allocationDTOList.size());
        assertEquals(allocation1.getSubject(), allocationDTOList.get(0).getSubject());
        assertEquals(allocation2.getSubject(), allocationDTOList.get(1).getSubject());
        assertEquals(allocation3.getSubject(), allocationDTOList.get(2).getSubject());
    }

    @Test
    void testFilterAllAllocationsByRoomId() {
        var roomA = roomRepository.saveAndFlush(newRoomBuilder().name(DEFAULT_ROOM_NAME + "A").build());
        var roomB = roomRepository.saveAndFlush(newRoomBuilder().name(DEFAULT_ROOM_NAME + "B").build());

        var allocation1 = allocationRepository.saveAndFlush(newAllocationBuilder(roomA).build());
        var allocation2 = allocationRepository.saveAndFlush(newAllocationBuilder(roomA).build());
        allocationRepository.saveAndFlush(newAllocationBuilder(roomB).build());

        var allocationDTOList = api.listAllocations(null, roomA.getId(), null, null);

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllAllocationsByEmployeeEmail() {
        var room = roomRepository.saveAndFlush(newRoomBuilder().build());
        var employee1 = newEmployeeBuilder().email(DEFAULT_EMPLOYEE_EMAIL + 1).build();
        var employee2 = newEmployeeBuilder().email(DEFAULT_EMPLOYEE_EMAIL + 2).build();

        var allocation1 = allocationRepository.saveAndFlush(newAllocationBuilder(room).employee(employee1).build());
        var allocation2 = allocationRepository.saveAndFlush(newAllocationBuilder(room).employee(employee1).build());
        allocationRepository.saveAndFlush(newAllocationBuilder(room).employee(employee2).build());

        var allocationDTOList = api.listAllocations(employee1.getEmail(), null, null, null);

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }

    @Test
    void testFilterAllAllocationsByDateRange() {
        var baseStartAt = now().plusDays(2).withHour(14).withMinute(0);
        var baseEndAt = now().plusDays(4).withHour(20).withMinute(0);

        var room = roomRepository.saveAndFlush(newRoomBuilder().build());

        var allocation1 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).startAt(baseStartAt.plusHours(1)).endAt(baseStartAt.plusHours(2)).build()
        );
        var allocation2 = allocationRepository.saveAndFlush(
            newAllocationBuilder(room).startAt(baseStartAt.plusHours(4)).endAt(baseStartAt.plusHours(5)).build()
        );

        allocationRepository.saveAndFlush(
            newAllocationBuilder(room).startAt(baseEndAt.plusDays(1)).endAt(baseEndAt.plusDays(3).plusHours(1)).build()
        );

        var allocationDTOList = api.listAllocations(null, null,
            baseStartAt.toLocalDate(), baseEndAt.toLocalDate());

        assertEquals(2, allocationDTOList.size());
        assertEquals(allocation1.getId(), allocationDTOList.get(0).getId());
        assertEquals(allocation2.getId(), allocationDTOList.get(1).getId());
    }
}
