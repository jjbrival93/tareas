// usecase/CrearTareaUseCase.java
package com.universidad.tareas.usecase;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.domain.repository.TaskRepository;

public class CrearTareaUseCase {
    private final TaskRepository taskRepository;

    public CrearTareaUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task ejecutar(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        Long id = System.currentTimeMillis(); // ID automático simple
        Task nuevaTask = new Task(id, titulo, false);
        return taskRepository.guardar(nuevaTask);
    }
}