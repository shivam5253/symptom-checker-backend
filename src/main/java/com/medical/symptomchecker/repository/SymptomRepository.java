package com.medical.symptomchecker.repository;

import com.medical.symptomchecker.entity.SymptomLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomRepository extends JpaRepository<SymptomLog, Long> {
}

