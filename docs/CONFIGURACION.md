# Configuración del Proyecto GoRide

## 🔧 Configuración del Entorno de Desarrollo

### Requisitos Previos
- **Android Studio** Arctic Fox o superior
- **SDK de Android** API Level 21 (Android 5.0) o superior
- **Java** 11 o superior
- **Gradle** 7.0 o superior

### Configuración del Proyecto

#### 1. Clonar el Repositorio
```bash
git clone [URL_DEL_REPOSITORIO]
cd GoRide
```

#### 2. Configuración de Android Studio
1. Abrir Android Studio
2. Importar proyecto existente
3. Seleccionar la carpeta del proyecto
4. Esperar a que Gradle termine de sincronizar

#### 3. Configuración de SDK
- **Target SDK:** 34 (Android 14)
- **Min SDK:** 21 (Android 5.0)
- **Compile SDK:** 34

### 🛠️ Dependencias Principales

#### Core Dependencies
```gradle
implementation 'androidx.appcompat:appcompat:1.6.1'
implementation 'com.google.android.material:material:1.10.0'
implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
implementation 'androidx.cardview:cardview:1.0.0'
```

#### Testing Dependencies
```gradle
testImplementation 'junit:junit:4.13.2'
androidTestImplementation 'androidx.test.ext:junit:1.1.5'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
```

## 🎨 Configuración de Colores y Temas

### Colores Principales
- **Primary Color:** `#B91C3C` (Rojo)
- **Background:** `#F8F9FA` (Gris claro)
- **Text Primary:** `#1A1A1A` (Negro)
- **Text Secondary:** `#6B7280` (Gris)

### Configuración de Material Design
El proyecto utiliza Material Design 3 con componentes personalizados.

## 🔐 Configuración de Seguridad

### Datos de Prueba
- Los datos se inicializan automáticamente al abrir la aplicación
- No se almacenan datos sensibles en texto plano
- Las contraseñas se validan contra un repositorio local

### Base de Datos
- **Tipo:** SQLite local
- **Ubicación:** `/data/data/com.example.goride/databases/`
- **Tablas:** usuarios, roles, sesiones

## 🚀 Compilación y Ejecución

### Debug Build
```bash
./gradlew assembleDebug
```

### Release Build
```bash
./gradlew assembleRelease
```

### Ejecutar Tests
```bash
./gradlew test
```

## 📱 Configuración de Dispositivos de Prueba

### Emulador Recomendado
- **Device:** Pixel 5
- **API Level:** 30 (Android 11)
- **Target:** Google APIs

### Dispositivos Físicos
- Android 5.0 o superior
- Mínimo 2GB RAM
- Resolución mínima: 720x1280

## 🌐 URLs y Servicios

### Endpoints (Futuro)
```
Base URL: https://api.goride.com
Login: /auth/login
User Management: /users
Inventory: /inventory
```

## 📊 Analytics y Monitoring

### Configuración de Logs
- **Nivel:** DEBUG en desarrollo, INFO en producción
- **Ubicación:** Logcat de Android

### Performance Monitoring
- Memory usage tracking
- Network request monitoring
- UI performance metrics

## 🔄 Versionado

### Semantic Versioning
- **Major:** Cambios que rompen compatibilidad
- **Minor:** Nuevas funcionalidades
- **Patch:** Correcciones de bugs

### Branches
- `main`: Código estable
- `develop`: Desarrollo activo
- `feature/*`: Nuevas características

## 🛡️ Variables de Entorno

### Development
```
DEBUG=true
LOG_LEVEL=DEBUG
DATABASE_NAME=goride_dev.db
```

### Production
```
DEBUG=false
LOG_LEVEL=INFO
DATABASE_NAME=goride_prod.db
```

## 📝 Notas Adicionales

- ✅ El proyecto está configurado para desarrollo local
- ✅ No requiere conexión a internet
- ✅ Base de datos se crea automáticamente
- ✅ Datos de prueba incluidos
- ⚠️ No usar en producción sin configuración adicional de seguridad

## 🤝 Contribución

1. Fork el proyecto
2. Crear una rama para la nueva característica
3. Hacer commit de los cambios
4. Push a la rama
5. Abrir un Pull Request

---

**Versión:** 1.0.0  
**Última actualización:** Enero 2026  
**Maintainer:** Equipo de Desarrollo GoRide
