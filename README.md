# AulEmi (`AlbanEmilioApp`)

Prototipo móvil en **Java (Swing)** con tipografía **Satoshi**, paleta **negro abisal, azul marino y celeste luminoso**, y logotipo PNG minimalista propio (`logo_aulemi.png`) para gestionar tutorías privadas (Álgebra, Cálculo, Fundamentos de Matemática, Fundamentos de Física y Geometría) de 1 a 10 estudiantes.

## Arquitectura del Proyecto

```text
AlbanEmilioApp/
├── src/
│   ├── entidades/        # Clases del dominio (Estudiante, ClaseTutoria, EjercicioPlanificado)
│   ├── datos/            # Repositorio en memoria con listas ArrayList (RepositorioTutorias)
│   ├── controladores/    # Lógica de negocio, validaciones y cálculos (ControladorTutorias)
│   ├── interfaz/         # LogoApp (importa PNG), IconosVectoriales, PaletaMarina (Satoshi),
│   │                     # PanelRedondeado, BotonModerno, VentanaTelefono, Diálogos y Principal
│   └── recursos/
│       ├── logo_aulemi.png
│       └── fuentes/      # Satoshi-Regular.ttf, Satoshi-Medium.ttf, Satoshi-Bold.ttf, Satoshi-Black.ttf
├── compilar_y_ejecutar.bat
├── generar_ejecutable.bat
└── README.md
```

## Cómo ejecutar y sacar el `.jar`

1. **En NetBeans / VS Code:** Copia las 5 carpetas de `src/` (`entidades`, `datos`, `controladores`, `interfaz` y `recursos`) dentro de tu carpeta `src` y ejecuta `interfaz.Principal`.
2. **En Windows con un clic:** Ejecuta `compilar_y_ejecutar.bat`.
3. **Generar el ejecutable `AulEmi.jar`:** Ejecuta `generar_ejecutable.bat`.
