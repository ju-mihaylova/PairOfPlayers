package com.sirma.pairofplayers.services;

import com.sirma.pairofplayers.interfaces.Identifiable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class AbstractService<T extends Identifiable> {
    public boolean doesIdExist(List<T> entities, long id) {
        return entities.stream()
                .anyMatch(entity -> entity.getId() == id);
    }
}
