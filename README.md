# CS 340 - Ticket to Ride


## Basic Information

Full Java package name of your server's "main" class
- server.ServerDriver

Explanation of any command-line parameters accepted by your server
- see section titled "How to Run the Server"

Java package name of your client app (e.g., edu.byu.cs340.tickettoride)
- client


## Server

From the folder that contains the jar files, run the following command:

For Mac and Linux:
 > java -cp .:server.jar:shared.jar:gson-2.8.5.jar server.ServerDriver <port_number> <sql_provider | json_provider> <delta_update_interval>

For Windows:
 > java -cp .;server.jar;shared.jar;gson-2.8.5.jar server.ServerDriver <port_number> <sql_provider | json_provider> <delta_update_interval>


<port_number> : optional parameter that tells the server which port to point at. The default port is 9000.

<sql_provider> : optional parameter that indicates that the server should utilize the sql persistence provider plugin

<json_provider> : optional parameter that indicates that the server should utilize the json persistence provider plugin

<delta_update_interval> : optional parameter that indicates how many commands the server receives before updating in the database. The default is 10.

## How to Run the Client
Run the following command to see what devices are currently turned on:

 > adb devices

Pick and emulator, be sure that the application is uninstalled from the device in advance. Then run the following command to install the application.

 > adb -s <emulator> install client-debug.apk



