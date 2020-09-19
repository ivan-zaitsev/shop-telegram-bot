# shop-telegram-bot
Shop in the telegram with the admin panel

## Technology stack
Java 8, Maven, Spring Boot, Spring MVC, Spring Data, Spring Security, Postgresql, Freemarker, HTML, Telegram Bots, Hibernate

## Quick start guide
1. Create postgres database and change the configuration in the properties `telegram-bot/src/main/resources/hibernate.cfg.xml` and `admin-panel/src/main/resources/application.properties`
2. Import the database schema `resources/db_schema.sql` and database data `resources/db_data.sql`
3. Create a telegram bot and enable inline mode in settings
4. Set up the telegram bot username and token in the properties for telegram bot `telegram-bot/src/main/resources/application.properties`
5. Run the telegram bot and admin panel, web UI is accessible on `http://localhost:8080/admin`, credentials (admin:admin)

Telegram bot sends images as a link, if they are uploaded from the localhost, the bot will not be able to send them

If you are deploying the application on the server don't forget to change server url and images upload path in the properties `admin-panel/src/main/resources/application.properties`

## Screenshots
#### Chatbot
![](resources/images/1.jpg)
![](resources/images/2.jpg)
![](resources/images/3.jpg)
![](resources/images/4.jpg)
![](resources/images/5.jpg)
#### Admin panel
![](resources/images/6.jpg)
![](resources/images/7.jpg)
![](resources/images/8.jpg)
![](resources/images/9.jpg)
![](resources/images/10.jpg)
