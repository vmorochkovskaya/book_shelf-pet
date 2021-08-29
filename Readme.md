<h1 align="center">Book store</h1>

## Technologies
Java, maven, Spring boot, Spring JPA, liquibase, postgre database, thymeleaf

## Description
- Navigation menu is available and works on all pages. All provided links and buttons work and lead to valid pages. 
- Data for books, authors and genres is retrieved from database.
- To change locale use Choose language drop-down in footer. 

## How to run
1. Update spring.datasource.url, spring.datasource.username, spring.datasource.password in resource/application.properties file with your url, username and password accordingly.
2. Update url in resources/liquibase.properties with your database url
3. - For windows execute scripts/run.cmd
   - For linux execute scripts/run.sh
4. Deploy war file
5. Open localhost:8087/books/popular
