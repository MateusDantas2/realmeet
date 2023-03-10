package br.com.sw2you.realmeet.integration;

import static br.com.sw2you.realmeet.utils.TestConstants.*;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newAllocationBuilder;
import static br.com.sw2you.realmeet.utils.TestDataCreator.newRoomBuilder;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import br.com.sw2you.realmeet.api.facade.ReportApi;
import br.com.sw2you.realmeet.core.BaseIntegrationTest;
import br.com.sw2you.realmeet.domain.repository.AllocationRepository;
import br.com.sw2you.realmeet.domain.repository.RoomRepository;
import br.com.sw2you.realmeet.model.DiskReportDispatcherService;
import br.com.sw2you.realmeet.report.enumeration.ReportFormat;
import br.com.sw2you.realmeet.service.ReportDispatcherService;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(ReportApiIntegrationTest.Configuration.class)
class ReportApiIntegrationTest extends BaseIntegrationTest {
    @Autowired
    private ReportApi api;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AllocationRepository allocationRepository;

    @Override
    protected void setupEach() throws Exception {
        setLocalHostBasePath(api.getApiClient(), "/v1");
    }

    @Test
    void testCreateAllocationReportSuccess() {
        persistAllocations(10);
        assertDoesNotThrow(
            () ->
                api.createAllocationReport(
                    TEST_CLIENT_API_KEY,
                    EMAIL_TO,
                    LocalDate.now().minusDays(15),
                    LocalDate.now().plusDays(15),
                    ReportFormat.PDF.name()
                )
        );
    }

    private void persistAllocations(int numberOfAllocations) {
        IntStream
            .range(0, numberOfAllocations)
            .forEach(
                i ->
                    allocationRepository.saveAndFlush(
                        newAllocationBuilder(roomRepository.saveAndFlush(newRoomBuilder().build()))
                            .subject(DEFAULT_ALLOCATION_SUBJECT + "_" + (i + 1))
                            .startAt(DEFAULT_ALLOCATION_START_AT.plusDays(i + 1))
                            .endAt(DEFAULT_ALLOCATION_END_AT.plusDays(i + 1))
                            .build()
                    )
            );
    }

    @TestConfiguration
    static class Configuration {

        @Bean
        public ReportDispatcherService reportDispatcherService() {
            return new DiskReportDispatcherService();
        }
    }
}
