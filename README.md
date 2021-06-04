# readingisgood

"ReadingIsGood is an online books retail firm which operates only on the Internet. Main
target of ReadingIsGood is to deliver books from its one centralized warehouse to their
customers within the same day. That is why stock consistency is the first priority for their
vision operations."


# building
`$ git clone https://github.com/pelinfg/reading-is-good.git`

`$ cd reading-is-good`

`$ mvn install`

# database setup
configure following lines in src/main/resources/application.properties:

spring.datasource.url=jdbc:h2:mem:readingisgood

spring.datasource.driverClassName=org.h2.Driver

spring.datasource.username=root

spring.datasource.password=root


# run application

`$ java -jar target/getir-readingisgood-0.0.1-SNAPSHOT.jar`

or

`$ mvn spring-boot:run`

# postman collection

https://www.getpostman.com/collections/c3ea862c13f8dde87132

# authentication

username: admin

password: admin

