package br.com.sw2you.realmeet.domain.repository;

import br.com.sw2you.realmeet.domain.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(
        "UPDATE Allocation a SET " +
                "a.subject = :subject, " +
                "a.startAt = :startAt, " +
                "a.endAt = :endAt " +
                "WHERE a.id = :allocationId"
    )
    void updateAllocation(
            @Param("allocationId") Long allocationId,
            @Param("subject") String subject,
            @Param("startAt") OffsetDateTime startAt,
            @Param("endAt") OffsetDateTime endAt
    );
}
