# Safaricom News Banner

 an API app where user can add  coarses, add staff and News. a user should view only users and news for a particular coarses and also can view all general news all the users in the company.
## Behavior of the programm

 | Behavior                                       |  Input | Output    |
 | ---------------------------------------------- | ------ | --------- |
 |Adding new models.Coarses|  Click on the add models.Coarses tab   |  New record will be adde to the list of Engieers|
 |Adding a new models.Staff| click On add new staff tab  |  New form will be dispalayed to add more staff |
 |Displaying the list of News| click on home page |  all the news and thier details will be displayed|
 
## Setup/Installation Requirements

* create a new folder on your desired location in your local machine
* git init using your terminal
* git clone https://github.com/emakuthi/sitePlanner.git
* make sure you have intellij installed in you laptop.
* launch intellij and go to files>open project.
* enjoy the code.
## Database installation instructions
Make sure you have postgres Db installed locally in your machine and follow below commands.

###### Creating a role and credentials

postgres=# create user elvis with password 'elvis';


###### Creating database
postgres=# create database dept_news;


###### connecting into the created database
postgres=# \c dept_news;

###### Creating coarses table.

dept_news=# create table coarses (id int PRIMARY KEY, name varchar, description varchar);

###### Creating staff table

dept_news=# create table staff (id int PRIMARY KEY, employee_name varchar, employee_number varchar, departmentid integer);

###### Creating contents table
dept_news=# create table staff (id int PRIMARY KEY, content varchar, departmentid integer);

## Known Bugs

there are currently no known bugs experienced on the website but feedback on bugs encountered during viewing of the page will be highly appreciated and will go a long way into making the website better for the users. The only reason that the website may fail to load is if the Internet connection is slow or disconnected which will require you to troubleshoot your Internet connection

## Technologies Used
* java
* JUnit
* Spark
* postgressql
* postman

**Main Languages used:**

* java for web application and spark as a framework


**Other Technologies:**

* none

## live link:

<https://safarticles.herokuapp.com/>

# clone into repository

* git clone https://github.com/emakuthi/sitePlanner.git
* Open using your favorite editor and view the code or just open the index.html on the browser

## Feedback

Incase of any issues or feedback please add using any of below links.

* [Issues](https://github.com/emakuthi/sitePlanner/issues). To submit any issues.

* [email](emakuthi@gmail.com) for any other feedback.

## Support and contact details

 Contact me on +254722827172 or on my github account <https://github.com/emakuthi>


## License

This project is licensed under the MIT License

**_Elvis Makuthi_** Copyright (c) 2019