# AulEmi (`AlbanEmilioApp`)

Aplicación de escritorio desarrollada en **Java (Swing)** con interfaz en modo oscuro para gestionar tutorías privadas (Álgebra, Cálculo, Fundamentos de Matemática, Fundamentos de Física y Geometría) para grupos de 1 a 10 estudiantes.

## Arquitectura del Proyecto

```text
AlbanEmilioApp/
├── src/
│   ├── entidades/        # Clases del dominio (Estudiante, ClaseTutoria, EjercicioPlanificado)
│   ├── datos/            # Repositorio en memoria con listas ArrayList (RepositorioTutorias)
│   ├── controladores/    # Lógica de negocio, validaciones y cálculos (ControladorTutorias)
│   └── interfaz/         # Interfaz gráfica en modo oscuro con Java Swing (VentanaPrincipal, DialogoNuevaClase, PaletaOscura, Principal)
├── compilar_y_ejecutar.bat
├── generar_ejecutable.bat
└── README.md
```

## Cómo ejecutar y crear el ejecutable

1. **En Apache NetBeans / VS Code:** Abre la carpeta `src` o crea un proyecto Java y copia las 4 carpetas (`entidades`, `datos`, `controladores`, `interfaz`) dentro de `src`. Ejecuta `interfaz.Principal`.
2. **En Windows con un clic:** Ejecuta `compilar_y_ejecutar.bat` para abrir la aplicación directamente.
3. **Generar el archivo ejecutable (`AulEmi.jar`):** Ejecuta `generar_ejecutable.bat`. Esto creará el archivo `AulEmi.jar` listo para compartir o abrir con doble clic.
