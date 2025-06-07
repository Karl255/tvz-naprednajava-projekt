# Database setup

* Run Docker

* Run command in your terminal:
  ```shell
  docker run -d --name di-je-zet-pg -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=password -e POSTGRES_DB=di-je-zet-db -p 5432:5432 postgres
  ```
  
# Changes in the database model

* Any changes in the models should be made through a migration script in `resources/db/migration`
* Script should be named like `VX__name.sql` - `X` for next migration number
* Flyway will run the script on application startup
* **Hibernate is configured to check database schema on startup!**


* It is possible that Flyway won't run migration if you changed existing migrations.
* In that case, or if needed, you can clean the database by running **Maven** command through IntelliJ:
  ```
  mvn flyway:clean
  ```
