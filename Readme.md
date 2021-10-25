<h1 align="center">Book store</h1>

## Technologies
Java, maven, Spring boot, Spring JPA, Spring security, liquibase, postgre database, thymeleaf

## Description
- Navigation menu is available and works on all pages. All provided links and buttons work and lead to valid pages. 
- Data for books, authors and genres is retrieved from database.
- To change locale use Choose language drop-down in footer. 
- To observe all books on Main page use slider arrow. There are 6 books available initially in each section on Main page.
- To observe the Popular books navigate to Popular item in header menu. There are 20 books available initially. To load more books click on Show more button.
- To observe the Recent books navigate to News item in header menu. There are 20 books available initially. To load more books click on Show more button.
- To find books with publication date within specific period the on Recent page use 2 provided calendars. There are 20 books available initially. To load more books click on Show more button.
- The books tags section is available above the Main page footer. The tags name size depends on amount of books with particular tag.
- To observe books by particular tag click on tag on Main page.
- To observe book info click on particular book image on Main page.
- To change the book cover click on book image on book slug page and upload an image (book slug page is available by performing the previous point).


## How to run
1. Update spring.datasource.url, spring.datasource.username, spring.datasource.password in resource/application.properties file with your url, username and password accordingly.
2. - For windows execute scripts/run.cmd
   - For linux execute scripts/run.sh
3. Deploy war file
4. Open localhost:8085
