	
	This is a tutorial Android PHP  sample project. It demonstrates the interaction of Android app with a remote database. 
	The project built on the client-server technology. The client part is the Android app. The server part is a HTTP server that interacts with the database.  Accordingly, the project consists of two parts. One part of the project is an Android project, the second is a PHP project.
	 As the development environment was used NetBeans IDE 8.01.  Android application written in Java. The server application written on the PHP language. To test  the application was  used a standard Android emulator. The project was also tested on the emulator Genymotio 2.3.1 and on a real device Huawei 9500. As the server was used Apache 2.4.10 HTTP server /PHP 5.5.15/MySql 5.0.11.
	In this project I have used the MySQL Relational Database Management System (RDBMS) to create a database.To manage MySQL server was used the browser-based MySQL adminisration tools, phpMyAdin 4.2.7.1.Databases can be created using the script mytest.sql
	To connect to the HTTP server the Android app uses  an absolute URL http://192.168.1.2:7070/askJson/askjson_input.php.

	The client and server communicate using the JSON protocol.

	Here is presented the client side. This is a Android app.
The server part is represented in the project https://github.com/ask1612/PHPmysql.git

	Android application consists of three Activity. The first Activity is intended for registration a new user or a user connection to the database. After entering the user name and password the  data is transmitted on the server side.
	 In the case of new user registration the  server connects to the database and checks for the user. If the user is found, the Android app receives a response that the user already exists in the database . If the user is not found , the user is registered by the server. Along with this the password is encrypted.
	 In the case of  user login   the  server connects to the database and checks for the user. If the user is found and if the password is correct , you will successfully login in  to the database.
	After successful login, the screen displays the second Activity. This Activity is a form to enter personal data . After you enter two records on the screen appears the  third Activity , which shows the information returned by the server in the form of a list in reverse order.
 	All user actions are accompanied by hints in the form of pop-up Windows.

	





 
	
	





 
