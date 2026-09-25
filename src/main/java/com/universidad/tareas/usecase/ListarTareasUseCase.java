// usecase/ListarTareasUseCase.java
package com.universidad.tareas.usecase;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.domain.repository.TaskRepository;
import java.util.List;

public class ListarTareasUseCase {
    private final TaskRepository taskRepository;
    public ListarTareasUseCase(TaskRepository taskRepository) { this.taskRepository = taskRepository; }
    public List<Task> ejecutar() { return taskRepository.listarTodas(); }
}