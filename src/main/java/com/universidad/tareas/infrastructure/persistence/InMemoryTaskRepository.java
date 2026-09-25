// infrastructure/persistence/InMemoryTaskRepository.java
package com.universidad.tareas.infrastructure.persistence;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.domain.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryTaskRepository implements TaskRepository {
    private final List<Task> memoria = new ArrayList<>();

    @Override
    public Task guardar(Task task) {
        memoria.removeIf(t -> t.getId().equals(task.getId())); // Reemplaza si ya existe (actualizar)
        memoria.add(task);
        return task;
    }

    @Override
    public List<Task> listarTodas() {
        return new ArrayList<>(memoria);
    }

    @Override
    public Optional<Task> buscarPorId(Long id) {
        return memoria.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    @Override
    public void eliminar(Long id) {
        memoria.removeIf(t -> t.getId().equals(id));
    }
}