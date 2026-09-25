
// infrastructure/config/BeanConfiguration.java
package com.universidad.tareas.infrastructure.config;

import com.universidad.tareas.domain.repository.TaskRepository;
import com.universidad.tareas.usecase.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CrearTareaUseCase crearTareaUseCase(TaskRepository repo) { return new CrearTareaUseCase(repo); }

    @Bean
    public ListarTareasUseCase listarTareasUseCase(TaskRepository repo) { return new ListarTareasUseCase(repo); }

    @Bean
    public BuscarTareaPorIdUseCase buscarTareaPorIdUseCase(TaskRepository repo) { return new BuscarTareaPorIdUseCase(repo); }

    @Bean
    public ActualizarTareaUseCase actualizarTareaUseCase(TaskRepository repo) { return new ActualizarTareaUseCase(repo); }

    @Bean
    public EliminarTareaUseCase eliminarTareaUseCase(TaskRepository repo) { return new EliminarTareaUseCase(repo); }
}