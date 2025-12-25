package com.jpmc.midascore;

import com.jpmc.midascore.foundation.TransactionRecord;

// File 2: TransactionRecordRepository.java
public interface TransactionRecordRepository extends org.springframework.data.repository.CrudRepository<TransactionRecord, Long> {
}