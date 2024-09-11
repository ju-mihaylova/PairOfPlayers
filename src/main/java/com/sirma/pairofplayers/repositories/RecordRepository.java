package com.sirma.pairofplayers.repositories;

import com.sirma.pairofplayers.models.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

}
