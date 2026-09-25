#!/bin/bash

echo "🚀 Iniciando limpieza total de Docker..."

# 1. Detener todos los contenedores en ejecución
if [ "$(docker ps -q)" ]; then
    echo "🛑 Deteniendo contenedores..."
    docker stop $(docker ps -q)
else
    echo "✅ No hay contenedores ejecutándose."
fi

# 2. Eliminar todos los contenedores
if [ "$(docker ps -aq)" ]; then
    echo "🗑️ Eliminando contenedores..."
    docker rm $(docker ps -aq)
fi

# 3. Eliminar todas las imágenes
if [ "$(docker images -q)" ]; then
    echo "🖼️ Eliminando imágenes..."
    docker rmi -f $(docker images -q)
fi

# 4. Limpieza profunda (Volúmenes, Redes y Cache)
echo "🧹 Limpiando volúmenes, redes y caché de construcción..."
docker system prune -a --volumes -f

echo "✨ ¡Docker está como nuevo!"