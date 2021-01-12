###### Este proyecto utiliza como base el código de FILO1701

# Plataforma base

## Prerequisitos

* Java 1.8 (Oracle JDK 8)
* PostgreSQL 9.5
* Maven 3
* Gradle
* Node v4.3 en adelante
	* Con la versión 8 parece que da problemas relacionados con Python


## Configuración

* Asignar JDK 1.8 en Module Settings.
* Crear la base de datos y configurar la conexión en el archivo `application-x.yml`.
    * Para inicializar la BD, ejecutar los scripts que se encuentran en `src/sql/create` (`01-creacion.sql` y `02-datos.sql`).

### Server

* `gradlew bootRun -Pdev` despliegua la aplicación para desarrollo. También se puede ejecutar el fichero `build.gradle` directamente.
	* En **Linux** dar permisos de ejecución al archivo gradlew (`chmod +x gradlew`) y ejecutar el comando `./gradlew bootRun -Pdev`.
* **(Método recomendado)** Para debuggear en IntelliJ/Eclipse ejecutar la clase `Application.java` en modo debug.
    * Añadir `-Dspring.profiles.active=dev` a `JAVA_OPTS` o a la configuración de la ejecución (`VM options`).  

### Client

* `npm install` instala todas las dependencias necesarias.
* `npm run serve` despliegua la aplicación para desarrollo.
    * ¡Ojo! Lanzar siempre en [`http://localhost:9000`](http://localhost:9000).


* Para añadir nuevas dependencias de Bower se puede añadir a `bower.json` y luego hacer un `npm install` o directamente hacer `bower install <nombre> -S`.

### JavaMelody

La configuración de JavaMelody no es necesaria cambiarla a no ser que se esté creando un nuevo proyecto a partir de este código.

* La configuración se encuentra en el archivo `JavaMelodyConfiguration.java`. En él se indican el usuario, la contraseña, la URL de acceso ([`http://localhost:8080/management/monitoring`]()), el nombre de la aplicación y el directorio en el que se guardará la información.


## Desarrollo

### Probar en Tomcat

Antes de hacer una actualización en producción hay que probar el WAR generado en un Tomcat en local. Para automatizar este proceso hay que:

* Adaptar el archivo `lanzar_tomcat.bat` que esta en la raíz del proyecto con las rutas al WAR generado y a la carpeta de Tomcat.
* Ejecutar.


## Despliegue

* `npm run build` compila la aplicación para producción en la carpeta `dist`.
* `gradlew war` genera el war de la aplicación para producción en la carpeta `build/libs`.
* Para **sistemas**:
    * Instalación:
        * Utilizar Java 8
        * La conexión a la base de datos se encuentra en el archivo `/WEB-INF/classes/config/application-prod.yml` y los logs en `/WEB-INF/classes/logback-spring.xml`.
    * Para actualizaciones:
        * Desplegar el nuevo war manteniendo la carpeta `/upload` y los archivos: `/WEB-INF/classes/config/application-prod.yml`, `/WEB-INF/classes/logback-spring.xml`.

