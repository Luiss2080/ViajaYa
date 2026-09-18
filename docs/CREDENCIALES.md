# Credenciales de usuarios de prueba - GoRide

Estos usuarios se crean automáticamente la primera vez que se abre la app
(`InicializadorDatos`), únicamente cuando la tabla de roles está vacía. Son
**datos de demostración**: cámbialos o elimina el sembrado antes de cualquier
uso real.

| Usuario | PIN | Rol |
|---------|------|-----|
| `admin` | `1234` | Administrador |
| `juan_perez` | `5678` | Cliente |
| `maria_lopez` | `5678` | Cliente |
| `carlos_driver` | `9999` | Conductor |
| `ana_driver` | `9999` | Conductor |

## Notas

- La pantalla de login admite un **PIN de exactamente 4 dígitos**.
- Los PIN no se guardan en claro: se almacenan como hash PBKDF2 con sal
  aleatoria (`pbkdf2$iteraciones$sal$hash`).
- Tras 5 intentos fallidos seguidos, el usuario queda bloqueado 60 segundos
  (el contador vive en memoria y se reinicia si se cierra la app).
- Las cuentas en estado `Inactivo` no pueden iniciar sesión.
- El nombre de usuario distingue mayúsculas de minúsculas.
- Solo el rol **Administrador** puede abrir la gestión de usuarios.
- Si tienes una instalación anterior con contraseñas en texto plano, esas
  cuentas se migran a hash en su primer inicio de sesión correcto.
- Los datos se reinician al desinstalar la app.
