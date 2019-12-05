# Ubifeed Backend

The back-end part of the S-eat app from Ubifeed is mainly formed of two components: the MySQL database and the Java server

### Database
The database is made in SQL language with one of the most used and beloved open source RDBMS, MySQL. To run and demo it
1. make sure you have a  downloaded [MySQL workbench](https://dev.mysql.com/downloads/workbench/) and the [Connector/J](https://dev.mysql.com/downloads/connector/j/) (use this [installer](https://dev.mysql.com/downloads/installer/) if you use a Windows OS)
2. open a new connection on the workbench
3. copy/paste the content of the file named **DB_sql_tables_v3.sql**, present on the package you should have received from us (or from [our GitHub repo](https://github.com/MarcoWilhelm/Ubifeed)) on a new script
4. run the script and the Database should be running.

### Java Server
To run and demo the server
1. make sure you have any IDE in your PC (such as [Eclipse](https://www.eclipse.org/downloads/)) with the JDK installed
2. open the "**src/main/irl/tud/ubifeed**" folder you can get from [our GitHub repo](https://github.com/MarcoWilhelm/Ubifeed)
3. run the server