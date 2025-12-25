package com.jpmc.midascore;

import com.jpmc.midascore.entity.UserRecord;

// File 1: UserRecordRepository.java
public interface UserRecordRepository extends org.springframework.data.repository.CrudRepository<UserRecord, Long> {
    UserRecord findById(long id);
    UserRecord findByName(String name);
}