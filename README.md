# <<NOMBRE>>

Aplicación Android académica (Java) inspirada en servicios de transporte tipo
Yango/Uber, organizada con el patrón **Modelo-Vista-Controlador** y persistencia
local con Room (SQLite). Nombre interno del paquete: `com.example.goride`.

> **Estado real del proyecto: prototipo parcial.** Hoy funcionan el inicio de
> sesión, el menú y la gestión de usuarios. Los módulos de roles, conductores,
> viajes y servicios existen solo como capa de datos (entidades, DAOs,
> repositorios y datos de prueba); **no tienen pantallas todavía**.

Este README describe el estado del código con todas las ramas de trabajo
fusionadas (seguridad, correcciones, tests y CI).

## Qué hace hoy (verificado en el código)

| Funcionalidad | Estado |
|---------------|--------|
| Inicio de sesión con usuario + PIN de 4 dígitos | Implementado |
| Sesión persistente (SharedPreferences) y cierre de sesión | Implementado |
| Almacenamiento del PIN con PBKDF2 + sal aleatoria | Implementado |
| Bloqueo temporal (60 s) tras 5 intentos fallidos | Implementado (en memoria) |
| Rechazo de cuentas `Inactivo` | Implementado |
| Menú principal | Implementado (solo "Usuarios" y "Cerrar sesión" hacen algo) |
| CRUD de usuarios (listar, crear, editar, eliminar con confirmación) | Implementado, solo rol Administrador |
| Validación de usuario, PIN, correo, teléfono; unicidad de usuario y correo | Implementado |
| Cálculo de tarifa `base + km * precio_por_km` (`CalculadoraTarifa`) | Implementado, **sin pantalla que lo use** |
| Gestión de roles / conductores / viajes / servicios | Solo entidades, DAOs, repositorios y datos de prueba; los botones del menú están vacíos (`TODO`) |
| Máquina de estados de viajes | **No existe**: `Viaje.estado` es un texto libre (`Pendiente`, `En Curso`, `Completado` en los datos de prueba) |
| Búsqueda y filtros en listados | No existe |

## Arquitectura

```
app/src/main/java/com/example/goride/
├── controlador/        Activities (login, menú, usuarios/lista y formulario)
├── modelo/
│   ├── entidades/      Usuario, Rol, Conductor, Viaje, Servicio (entidades Room)
│   ├── dao/            Interfaces Room (consultas parametrizadas)
│   ├── repositorio/    Fachada sobre cada DAO
│   ├── basedatos/      BaseDatosGoRide (Room, versión 1)
│   └── utilidades/     HashContrasena, LimitadorIntentos, PoliticaAcceso,
│                       ValidadorDatos, CalculadoraTarifa, GestorSesion,
│                       InicializadorDatos (datos de prueba), UtilidadesFecha
└── vista/adaptadores/  Adaptadores de RecyclerView
app/src/main/res/layout/ Vistas XML
```

- Las consultas usan Room con parámetros enlazados: no hay SQL concatenado.
- La lógica sin dependencias de Android (`HashContrasena`, `LimitadorIntentos`,
  `PoliticaAcceso`, `ValidadorDatos`, `CalculadoraTarifa`) se prueba en la JVM.
- Las Activities comprueban sesión y rol al abrirse; no dependen solo de ocultar botones.

## Datos de prueba

Al primer arranque, si no hay roles, `InicializadorDatos` crea 3 roles
(Administrador, Cliente, Conductor), 5 usuarios, 4 servicios, 2 conductores y
4 viajes. Credenciales de demostración en [`docs/CREDENCIALES.md`](docs/CREDENCIALES.md)
(por ejemplo `admin` / `1234`). Son datos de demostración: **no hay flujo de
primer arranque para crear un administrador propio**, así que cualquier uso real
requeriría cambiarlos o eliminarlos.

## Compilar, ejecutar y probar

Requisitos: JDK 17, Android SDK con la plataforma 34 (Android Studio la instala) y
Gradle wrapper incluido (Gradle 8.9, AGP 8.7.3).

```bash
# Definir el SDK (una de las dos)
export ANDROID_HOME=/ruta/al/Android/Sdk      # o crear local.properties con sdk.dir=...

./gradlew test            # pruebas unitarias JVM (JUnit 4)
./gradlew assembleDebug   # APK de depuración
```

También se puede abrir la carpeta en Android Studio y ejecutar en un emulador
(minSdk 24, targetSdk 34). La integración continua (`.github/workflows/ci.yml`)
ejecuta `./gradlew test` y `assembleDebug` con JDK 17.

Pruebas incluidas (`app/src/test`): hash de contraseñas, limitador de intentos,
política de acceso, validadores y calculadora de tarifas. Las pruebas
instrumentadas (`androidTest`) son solo la plantilla por defecto de Android Studio.

## Limitaciones conocidas

- El PIN de 4 dígitos es una credencial débil por diseño de la pantalla de login;
  el bloqueo por intentos mitiga pero no elimina el riesgo, y su contador se
  reinicia al cerrar la app.
- PBKDF2WithHmacSHA1 (60 000 iteraciones) se eligió por compatibilidad con API 24.
- Las consultas de Room se ejecutan en el hilo principal (`allowMainThreadQueries`).
- La base está en versión 1 y sin migraciones: cambios de esquema exigen
  reinstalar o escribir una migración. No se exporta el esquema.
- Un administrador puede desactivarse o cambiarse el rol a sí mismo desde el formulario.
- Sin internet, mapas, pagos ni asignación real de conductores; nada de esto existe.
- No hay pruebas de interfaz ni de los DAO (requieren dispositivo/emulador).

## Licencia

Este repositorio **no incluye ningún archivo de licencia**. Sin licencia explícita,
todos los derechos quedan reservados por el autor y no se concede permiso de
uso, copia ni distribución.
