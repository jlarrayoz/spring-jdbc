# spring-jdbc
En este proyecto vamos a agregar el uso de persistencia (SPRING-JDBC) al proyecto de pedidos de pizza


**Vamos a trabajar con SPRING-JDBC y a grandes rasgos vamos a realizar los siguientes pasos:**

<ol>
	<li>Agregar la dependencia de spring JDBC</li>
	<li>Agergar dependencia para el DBMS lightweight <a href="https://www.h2database.com/html/main.html">Página oficial H2</a></li>
	<li>Prefijar el nombre de la BD en el archivo <b>applications.properties</b></li>
	<li>Agregar un campo id a los objetos del dominio que no lo tengan (Del tipo Long)</li>
	<li>Crear el archivo schema.sql con el DDL de las tablas y ponerlo en /src/main/resources</li>
	<li>Crear el archivo data.sql con los datos precargados de las tablas y ponerlo en /src/main/resources</li> 
	<li>Cambiar el controller DiseniarPizzaController para que utilice el repo de Ingredientes</li>
	<li>También debemos hacer lo mismo con el Converter IngredienteConverter</li>
	<li>Vamos a modificar el controller OrdenPizzaController para persistir una orden de pizza</li>
</ol>


1 - Debemos agregar en el pom.xml las siguientes dependencias:

```xml
<!-- Dependencia para usar JDBC -->
<dependency> 
	<groupId>org.springframework.boot</groupId> <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<!-- Dependecia para utilizar el DBMS H2 -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>

```

3 - El nombre de la base de datos H2 se debe configurar en el archivo applications.properties (Si no se fija el nombre, se crea de forma aleatoria)

En este ejemplo vamos y para proposito de desarrollo vamos a utilizar la BD embebida H2.

El archiv applications se encuentra en la carpeta "src/main/resources".

Podemos substituir este archivo por "application.yml" y expresar lo mismo en este nuevo formato:

```yml
spring:
  datasource:
    generate-unique-name: false
    name: pizzadb
```

La URL de acceso a la BD va a ser **jdbc:h2:mem:pizzadb**


## Acceder a la consola web de H2

Para poder tener acceso a la BD debemos habilitar la consola embebida que provee h2.

Para eso, vamoa a editar el archivo application.yml y agregar las siguientes lineas:

```yml
  h2:
    console.enabled: true
```

Para acceder a la consola, una vez levantada la app debemos ir a la siguiente URL en el browser:

[http://localhost:8080/h2-console/](http://localhost:8080/h2-console/)


La URL de acceso a la bd del proyecto es la siguiente:

jdbc:h2:mem:pizzadb