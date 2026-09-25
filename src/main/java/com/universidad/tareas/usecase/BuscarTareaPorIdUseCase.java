// usecase/BuscarTareaPorIdUseCase.java
package com.universidad.tareas.usecase;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.domain.repository.TaskRepository;
import java.util.Optional;

public class BuscarTareaPorIdUseCase {
    private final TaskRepository taskRepository;
    public BuscarTareaPorIdUseCase(TaskRepository taskRepository) { this.taskRepository = taskRepository; }
    public Optional<Task> ejecutar(Long id) { return taskRepository.buscarPorId(id); }
}