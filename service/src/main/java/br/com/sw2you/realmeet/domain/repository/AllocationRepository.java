package br.com.sw2you.realmeet.domain.repository;

import br.com.sw2you.realmeet.domain.entity.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {}
