// domain/repository/TaskRepository.java
package com.universidad.tareas.domain.repository;

import com.universidad.tareas.domain.model.Task;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    Task guardar(Task task);
    List<Task> listarTodas();
    Optional<Task> buscarPorId(Long id);
    void eliminar(Long id);
}