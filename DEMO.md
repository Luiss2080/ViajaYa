# Demo publica

ViajaYa es una app **Android nativa en Java**: no tiene version web, asi que
no hay nada que publicar en Cloudflare Pages. La demo de este proyecto es el
APK.

- **Descarga:** Releases del repo, etiqueta `demo`
- **Tambien:** el APK queda como artefacto de cada corrida del workflow

## Como se genera

La rama `demo` agrega `.github/workflows/apk-demo.yml`. En cada push a `demo`
(o a mano desde la pestana Actions) compila `./gradlew assembleDebug` y deja
`app-debug.apk` publicado en la release `demo`, pisando el anterior.

Es un APK de **depuracion**, sin firmar para produccion: Android va a pedir
permitir la instalacion de origenes desconocidos.

## Alcance

Segun el README del propio proyecto, esto es un prototipo parcial:

- Anda: inicio de sesion, gestion de usuarios y calculadora de tarifas.
- Falta: viajes y conductores.

La rama `master` no se toca: el workflow vive solo en `demo`.
