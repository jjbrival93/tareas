// usecase/ActualizarTareaUseCase.java
package com.universidad.tareas.usecase;

import com.universidad.tareas.domain.model.Task;
import com.universidad.tareas.domain.repository.TaskRepository;

public class ActualizarTareaUseCase {
    private final TaskRepository taskRepository;
    public ActualizarTareaUseCase(TaskRepository taskRepository) { this.taskRepository = taskRepository; }

    public Task ejecutar(Long id, String titulo, boolean completada) {
        Task tareaExistente = taskRepository.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
        tareaExistente.setTitulo(titulo);
        tareaExistente.setCompletada(completada);
        
        return taskRepository.guardar(tareaExistente);
    }
}