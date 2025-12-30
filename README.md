# Parcial2 - Sistema de Gestión Universitario

Backend Spring Boot para gestión de asistencia, aulas, carreras, facultades, grupos, horarios y materias en un sistema universitario.

## 📋 Requisitos Previos

- **Java 17** o superior
- **Maven 3.9+**
- **PostgreSQL 12+** (local o Neon)
- **Docker** (opcional, para containerización)

## 🚀 Instalación Local

### 1. Clonar el repositorio

```bash
git clone <repository-url>
cd BackendUnivSys
```

### 2. Configurar variables de entorno

Copia el archivo de ejemplo y configura tus credenciales:

```bash
cp src/main/resources/application.properties_example src/main/resources/application.properties
```

Edita `application.properties` con tus valores reales:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tu_base_datos
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
```

### 3. Ejecutar la aplicación

**Opción A: Con Maven**

```bash
./mvnw spring-boot:run
```

**Opción B: Compilar y ejecutar JAR**

```bash
./mvnw clean package
java -jar target/parcial2-0.0.1-SNAPSHOT.jar
```

La aplicación estará disponible en: `http://localhost:8080`

## 🐳 Ejecución con Docker

### 1. Construir imagen Docker

```bash
docker build -t parcial2-backend .
```

### 2. Ejecutar contenedor

Necesitas una base de datos PostgreSQL accesible. Puedes:

**Opción A: Usar Neon (PostgreSQL en la nube)**

```bash
docker run -d \
  --name parcial2-app \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL="postgresql://user:password@ep-xxxxx.neon.tech/database" \
  -e SPRING_DATASOURCE_USERNAME="user" \
  -e SPRING_DATASOURCE_PASSWORD="password" \
  parcial2-backend
```

**Opción B: Usar PostgreSQL local**

```bash
# Primero inicia PostgreSQL en tu máquina
docker run -d \
  --name parcial2-app \
  -p 8080:8080 \
  --network host \
  -e SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/unisyc" \
  -e SPRING_DATASOURCE_USERNAME="postgres" \
  -e SPRING_DATASOURCE_PASSWORD="tu_contraseña" \
  parcial2-backend
```

Verificar logs:

```bash
docker logs parcial2-app
```

## 🌐 Desplegar en Render

### 1. Preparar repositorio

Asegúrate de que está en GitHub con:
- ✅ `Dockerfile`
- ✅ `src/main/resources/application.properties_example`
- ✅ `.gitignore` (ignora `application.properties`)

### 2. Crear servicio en Render

1. Ve a [render.com](https://render.com)
2. Conecta tu repositorio GitHub
3. Crea un nuevo **Web Service**
4. Configura:
   - **Build Command**: `./mvnw clean package`
   - **Start Command**: `java -jar target/parcial2-0.0.1-SNAPSHOT.jar`

### 3. Agregar variables de entorno

En **Environment** del servicio, añade:

```
SPRING_DATASOURCE_URL=postgresql://user:password@host:port/database
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=password
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

**Si usas Neon para la base de datos:**

1. Crea una base de datos en [Neon](https://neon.tech)
2. Copia la URL de conexión
3. Pega en `SPRING_DATASOURCE_URL`

### 4. Deploy

Render detectará el `Dockerfile` automáticamente y desplegará tu aplicación.

## 📁 Estructura del Proyecto

```
BackendUnivSys/
├── src/
│   ├── main/
│   │   ├── java/com/si2/parcial2/
│   │   │   ├── controllers/          # Endpoints REST
│   │   │   ├── entities/             # Modelos JPA
│   │   │   ├── repositories/         # Acceso a datos
│   │   │   ├── services/             # Lógica de negocio
│   │   │   ├── security/             # Configuración JWT
│   │   │   └── validation/           # Validaciones
│   │   └── resources/
│   │       ├── application.properties (NO SUBIR - crear localmente)
│   │       └── application.properties_example
│   └── test/
├── pom.xml                           # Dependencias Maven
├── Dockerfile                        # Configuración Docker
├── .gitignore
└── README.md
```

## 🔒 Seguridad

- **Credenciales**: Nunca hagas commit de `application.properties` con datos reales
- **Variables de entorno**: Usa variables de entorno en producción
- **JWT**: Configurado en `security/`

## 🛠️ Endpoints Principales

- `GET /api/asistencia` - Lista asistencias
- `GET /api/aulas` - Lista aulas
- `GET /api/carreras` - Lista carreras
- `GET /api/facultades` - Lista facultades
- `GET /api/grupos` - Lista grupos
- `GET /api/horarios` - Lista horarios
- `GET /api/materias` - Lista materias
- `GET /api/modulos` - Lista módulos
- `GET /api/profesores` - Lista profesores
- `POST /api/auth/login` - Autenticación

Consulta la documentación Swagger en `http://localhost:8080/swagger-ui.html` (si está habilitado)

## 🐛 Solución de Problemas

**Error de conexión a base de datos**
- Verifica que PostgreSQL está corriendo
- Confirma credenciales en `application.properties`
- Asegúrate de que la base de datos existe

**Puerto 8080 en uso**
```bash
# Cambiar puerto
java -Dserver.port=8081 -jar target/parcial2-0.0.1-SNAPSHOT.jar
```

**Docker: No puede conectarse a PostgreSQL**
- Si usas `--network host`, PostgreSQL debe estar en la máquina host
- Usa Neon para evitar problemas de red

## 📝 Licencia

Este proyecto es parte de un parcial académico.

## 👤 Autor

Alejandro

