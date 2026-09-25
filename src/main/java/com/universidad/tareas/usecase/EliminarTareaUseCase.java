// usecase/EliminarTareaUseCase.java
package com.universidad.tareas.usecase;

import com.universidad.tareas.domain.repository.TaskRepository;

public class EliminarTareaUseCase {
    private final TaskRepository taskRepository;
    public EliminarTareaUseCase(TaskRepository taskRepository) { this.taskRepository = taskRepository; }
    public void ejecutar(Long id) { taskRepository.eliminar(id); }
}