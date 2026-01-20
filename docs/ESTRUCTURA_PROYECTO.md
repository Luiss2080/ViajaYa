# Estructura del Proyecto GoRide

## 📁 Arquitectura General

El proyecto GoRide sigue una arquitectura **MVC (Model-View-Controller)** adaptada para Android, con separación clara de responsabilidades.

```
GoRide/
├── 📁 app/                          # Módulo principal de la aplicación
│   ├── 📁 src/
│   │   ├── 📁 main/
│   │   │   ├── 📁 java/com/example/goride/
│   │   │   │   ├── 📁 controlador/           # 🎮 Controllers (Activities)
│   │   │   │   ├── 📁 modelo/               # 📊 Model Layer
│   │   │   │   │   ├── 📁 entidades/       # 🏗️ Data Entities
│   │   │   │   │   ├── 📁 repositorio/     # 💾 Data Access Layer
│   │   │   │   │   └── 📁 utilidades/      # 🔧 Utilities & Helpers
│   │   │   │   └── MainActivity.java        # 🚪 Entry Point
│   │   │   └── 📁 res/                      # 🎨 Resources (Views)
│   │   │       ├── 📁 layout/              # 📱 XML Layouts
│   │   │       ├── 📁 drawable/            # 🖼️ Images & Icons
│   │   │       ├── 📁 values/              # 🎭 Strings, Colors, Styles
│   │   │       └── 📁 mipmap/              # 🔘 App Icons
│   │   ├── 📁 androidTest/                 # 🧪 Instrumented Tests
│   │   └── 📁 test/                        # ⚡ Unit Tests
│   ├── build.gradle.kts                     # 📦 Module Dependencies
│   └── proguard-rules.pro                   # 🔒 Code Obfuscation Rules
├── 📁 docs/                                 # 📚 Documentation
├── 📁 gradle/                               # ⚙️ Gradle Configuration
├── build.gradle.kts                         # 📋 Project Configuration
├── settings.gradle.kts                      # 🔧 Project Settings
└── README.md                                # 📖 Project Overview
```

## 🎮 Capa de Controlador (Controllers)

### ActividadLogin.java
- **Responsabilidad:** Gestión de autenticación
- **Funciones:** Validación de credenciales, navegación, UI feedback
- **Dependencias:** RepositorioUsuario, GestorSesion

### ActividadMenuPrincipal.java
- **Responsabilidad:** Dashboard principal
- **Funciones:** Navegación, gestión de sesión, acceso a módulos

```java
// Ejemplo de estructura de controlador
public class ActividadLogin extends AppCompatActivity {
    // Componentes de UI
    private TextInputEditText campoUsuario;
    private Button botonIngresar;
    
    // Dependencias del modelo
    private RepositorioUsuario repositorioUsuario;
    private GestorSesion gestorSesion;
    
    // Métodos de lifecycle
    protected void onCreate(Bundle savedInstanceState) { }
    
    // Métodos de UI
    private void inicializarVistas() { }
    private void configurarEventos() { }
    
    // Lógica de negocio
    private void iniciarSesion() { }
}
```

## 📊 Capa de Modelo (Model)

### 🏗️ Entidades (entities/)

#### Usuario.java
```java
public class Usuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String nombreCompleto;
    private int idRol;
    // Constructores, getters, setters
}
```

#### Rol.java
```java
public class Rol {
    private int idRol;
    private String nombreRol;
    private String descripcion;
    // Métodos
}
```

### 💾 Repositorios (repositorio/)

#### RepositorioUsuario.java
- **Responsabilidad:** CRUD de usuarios
- **Métodos:** `crear()`, `buscarPorNombre()`, `autenticar()`, `actualizar()`

#### RepositorioBase.java
- **Responsabilidad:** Base común para todos los repositorios
- **Funciones:** Conexión a BD, operaciones comunes

### 🔧 Utilidades (utilidades/)

#### GestorSesion.java
- **Responsabilidad:** Manejo de sesión de usuario
- **Funciones:** `iniciarSesion()`, `cerrarSesion()`, `haySesionActiva()`

#### ValidadorDatos.java
- **Responsabilidad:** Validaciones de entrada
- **Funciones:** `esTextoValido()`, `esEmailValido()`, `esContrasenaSegura()`

#### InicializadorDatos.java
- **Responsabilidad:** Población de datos iniciales
- **Funciones:** `inicializarDatosPrueba()`, `crearUsuariosPorDefecto()`

## 🎨 Capa de Vista (Views)

### 📱 Layouts (layout/)

```xml
<!-- Estructura típica de layout -->
<?xml version="1.0" encoding="utf-8"?>
<ScrollView>
    <LinearLayout>
        <!-- Header -->
        <ImageView android:id="@+id/logo" />
        
        <!-- Content -->
        <androidx.cardview.widget.CardView>
            <!-- Form components -->
            <com.google.android.material.textfield.TextInputLayout>
                <com.google.android.material.textfield.TextInputEditText />
            </com.google.android.material.textfield.TextInputLayout>
            
            <!-- Action buttons -->
            <Button android:id="@+id/botonIngresar" />
        </androidx.cardview.widget.CardView>
        
        <!-- Footer -->
        <TextView android:text="Version info" />
    </LinearLayout>
</ScrollView>
```

### 🖼️ Recursos Gráficos (drawable/)

- `gradient_background.xml` - Fondo degradado
- `password_box_selector.xml` - Estado de cajas de contraseña
- `ic_user_red.xml` - Iconos vectoriales
- `logo.png` - Logo de la aplicación

### 🎭 Estilos y Valores (values/)

#### colors.xml
```xml
<resources>
    <color name="primary_red">#B91C3C</color>
    <color name="background_light">#F8F9FA</color>
    <color name="text_primary">#1A1A1A</color>
    <color name="text_secondary">#6B7280</color>
</resources>
```

#### strings.xml
```xml
<resources>
    <string name="app_name">GoRide</string>
    <string name="titulo_login">Iniciar Sesión</string>
    <string name="usuario">Usuario</string>
</resources>
```

## 🔄 Flujo de Datos

### 1. Autenticación
```
Usuario ingresa credenciales
    ↓
ActividadLogin valida formato
    ↓
RepositorioUsuario consulta BD
    ↓
GestorSesion guarda estado
    ↓
Navegación a menú principal
```

### 2. Gestión de Sesión
```
App inicia
    ↓
MainActivity verifica sesión activa
    ↓
Si hay sesión: ir a menú
Si no hay sesión: ir a login
```

## 📦 Dependencias y Librerías

### Core Android
- `androidx.appcompat` - Compatibilidad
- `androidx.constraintlayout` - Layouts avanzados
- `com.google.android.material` - Material Design

### Base de Datos
- `SQLiteOpenHelper` - Gestión de BD local
- `SharedPreferences` - Preferencias de usuario

### UI/UX
- `CardView` - Tarjetas con elevación
- `TextInputLayout` - Campos de entrada mejorados
- `ViewPropertyAnimator` - Animaciones

## 🧪 Estructura de Testing

### Unit Tests (test/)
```java
public class ValidadorDatosTest {
    @Test
    public void validarTexto_conTextoValido_retornaTrue() {
        assertTrue(ValidadorDatos.esTextoValido("usuario123"));
    }
    
    @Test
    public void validarTexto_conTextoVacio_retornaFalse() {
        assertFalse(ValidadorDatos.esTextoValido(""));
    }
}
```

### Integration Tests (androidTest/)
```java
public class ActividadLoginTest {
    @Test
    public void login_conCredencialesValidas_navegaAMenuPrincipal() {
        // Prueba de flujo completo
    }
}
```

## 📋 Convenciones de Código

### Nomenclatura
- **Clases:** PascalCase (ej: `ActividadLogin`)
- **Métodos:** camelCase (ej: `inicializarVistas`)
- **Variables:** camelCase (ej: `campoUsuario`)
- **Constantes:** UPPER_SNAKE_CASE (ej: `MENSAJE_ERROR`)

### Organización de Archivos
- Un archivo por clase
- Imports ordenados alfabéticamente
- Métodos públicos antes que privados
- Documentación JavaDoc para métodos públicos

## 🔒 Consideraciones de Seguridad

### Datos Sensibles
- Contraseñas hasheadas (implementar en producción)
- Tokens de sesión seguros
- Validación en cliente y servidor

### Permisos
```xml
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

## 📈 Escalabilidad

### Modularización Futura
- Separar por features (auth, inventory, reports)
- Usar arquitectura MVVM con LiveData
- Implementar Dependency Injection (Dagger/Hilt)

### Base de Datos
- Migrar a Room Database
- Implementar sincronización con servidor
- Cache inteligente para offline mode

---

**Nota:** Esta estructura está diseñada para ser escalable y mantenible. Cada capa tiene responsabilidades específicas y las dependencias fluyen hacia abajo siguiendo principios SOLID.
