### DATE: 01-08-2023 ; 30-08-2023

## MATERIALS USED:

- Amigos Code YouTube video

## Annotations

- @RestController
- @GetMapping | @RequestMapping(method =GET) | @GetMapping("/api") | @PostMapping("/api") | @RequestMapping("api/v1/person")
- @RequestParam | @RequestBody | @PathVariable
- @Repository
- @Component
- @Service
- @Qualifier
- @AutoWire
- @JsonProperty
- @NotNull | @NotBlank | @Valid
- @Configuration
- @ConfigurationProperties
- @Bean

## NOTES START

#### The very beginning : Bootstrapping a SpringBoot application

- https://start.spring.io :To bootstrap a SpringBoot application

<!-- .................. -->
<!-- .................. -->

#### SpringBoot Architecture

- API Layer / Controller Layer (HTTP; GET, POST, PUT, DELETE) ===> Service Layer (Business Logic) ===> Data Access Layer (Database)
- api --> service --> dao --> model

- The inclusion of the dao (data access object) is what makes dependency injection possible (where we can switch data source by changing just one line of code). We can omit the dao (an interface and classes extending that interface) and skip straight to model of course, but then we would have a rigid data structure where dependency injection isn't possible.
- Dependency injection in SpringBoot is achieved by defining an interface and multiple classes can inherit this interface - SpringBoot can then use any of the class implementations of the defined interface as a middleman between the 'service' layer and 'model' layer. An example would be an interface 'Foo' which we inherit using two different classes - PostgresClass and MySqlClass. Using dependency injection, we can switch between using a Postgres db and a MySql database.(This may not make much sense when reading later-don't worry).

<!-- .................. -->
<!-- .................. -->

#### Connecting to Postgres Db using Docker

1. Run this command in a terminal to start up a postgres instance

    ```bash
        $ docker run --name myPostgresDB -e POSTGRES_PASSWORD=123456 -d -p 5432:5432 postgres:alpine
    ```
   Flags:
    - -d : detach mode
    - -p : port (expose a port)
    - -e : environment variable e.g password
    - --name : to name the container

   postgres:alpine is a small/light version of Postgres

2. Add dependencies necessary for the database to work
    - Add the following dependencies to pom.xml
        ```xml
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-jdbc</artifactId>
              </dependency>
      
              <dependency>
                  <groupId>org.postgresql</groupId>
                  <artifactId>postgresql</artifactId>
                  <scope>runtime</scope>
              </dependency>
      
              <dependency>
                  <groupId>org.flywaydb</groupId>
                  <artifactId>flyway-core</artifactId>
              </dependency>
        ```
    - jdbc: Java Database Connectivity ==> Provides an API that allows us write SQL statements against our database
    - postgresql: The actual Postgres driver
    - flywaydb: For database migrations

3. Create a 'datasource' package and create a 'class' file in it (PostgresDataSource.java in this case.) This file would contain the Datasource definition/configuration

4. Under 'Resources' folder, rename 'application.properties' to 'application.yml' (or maybe create a separate file)
    - Add all the postgres database connection details to this file (application.yml)

5. Under 'Resources' folder, create a new folder called db. Create another folder in db called migration. Create a file in migration called 'V1__Create_person_table.sql'.
    - This file would contain the database migrations (SQL code) that would be run by flywaydb for our database. Migrations here is similar to that of Django.

6. Create the database in the Postgres container using psql in the command line (steps below)


#### Working with Postgres from the command line (psql)
[https://www.postgresqltutorial.com/postgresql-cheat-sheet/ ] - postgresql/psql cheat sheet

1. Bash into the postgres container/instance
      ```bash 
          docker exec -it [container name/id] bash
      ```
2.  Access the PostgreSQL server using psql with the default user 'postgres':
      ```bash
            psql -U postgres
      ```
3. Create a database named demodb
      ```bash 
            CREATE DATABASE demodb;        // The semi-colon isn't optional
      ```

#### Miscellaneous

- pom.xml is to SpringBoot as package.json is to Node.js
- @Component could be used in place of @Repository
- @Component could be used in place of @Service
- For @NotNull or @NotBlank annotations to work when used in a model (a database variable), @Valid must be used in the controller (for the object/entity coming from the request)
    - Also, the following has to be added to the pom.xml dependencies
      ```xml
             <dependency>
               <groupId>org.springframework.boot</groupId>
               <artifactId>spring-boot-starter-validation</artifactId>
            </dependency>
      ```
- With SpringBoot, you can bundle your entire codebase and dependencies into a .jar file using Maven or Gradle (unlike nodejs where you have to push the entire codebase to the external environment)
    - To do this in IntelliJ IDEA using Maven, click on Maven on the right sidebar, projectName >> Lifecycle >> install
    - Once the bundling/compilation is done, you get a SNAPSHOT.jar file which you can run in the terminal to start up your server or upload to an external environment


#### Spring Beans / Beans
[https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/beans.html]
[https://mindmajix.com/what-is-bean-in-java-spring]
- "In Spring, the objects that form the backbone of your application and that are managed by the Spring IoC container are called beans. A bean is an object that is instantiated, assembled, and otherwise managed by a Spring IoC container. Otherwise, a bean is simply one of many objects in your application. Beans, and the dependencies among them, are reflected in the configuration metadata used by a container."

```java

```

## NOTES END


## Express.js VERSUS SpringBoot

- req.param (e.g. localhost:3000/person/:id) in Express is referred to as @PathVariable in SpringBoot
- req.query (e.g. localhost:3000/person?name="Ade"&age=12) is referred to as @RequestParam in SpringBoot

<!-- .................. -->

<!-- .................. -->