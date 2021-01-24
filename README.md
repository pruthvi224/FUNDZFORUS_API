# Fundzforus-Project
Fundzforus-Project

Softwares Required:

1. MySQL5.7(https://dev.mysql.com/downloads/mysql/5.7.html)
2. Maven
3. Java
4. Postman

Installation Steps:

1. Install MySQL5.7 from following URL (https://dev.mysql.com/downloads/mysql/5.7.html)
	Create root password as "admin"

2. Connect to MySQL5.7 database using MySQL 5.7 Command Line Client.

3. Run the following 2 commands in MySQL 5.7 Command Line Client as below.
	mysql> drop database fundzforus;
	Query OK, 7 rows affected (0.12 sec)
	mysql> create database fundzforus;
	Query OK, 1 row affected (0.01 sec)

4. Import SQL script(sql\tables.sql) in Windows Command Prompt. Go to command prompt where MySQL 5.7 installed. 
	Ex: C:\Program Files\MySQL\MySQL Server 5.7\bin>mysql -u root -p fundzforus < C:\Ramesh\Projects\Fundzforus-Project\sql\tables.sql

5. Install Java8

6. Download Build Tool Maven 3.X

7. Set Maven in CLASSPATH Ex: 

8. Now to go to this directory.

9. Run "mvn clean install" for the application in the Command prompt. Ex: C:\Ramesh\Projects\Fundzforus-Project\

10. run "java -Xmx4072m -Xms4072m -jar target\Fundzforus-0.0.1-SNAPSHOT.jar"

11. From Web Browser, Run the following URL's http://localhost:8080/login, http://localhost:8080/registration, http://localhost:8080/home

12. For User Rest API's. Use Postman tool for this.

    a. http://localhost:8080/rest/getAllUsers (HTTP GET). This will get all existing Users. --> This will work from WebBrowser.
    
    b. http://localhost:8080/rest/user (HTTP POST). This will add new User.
        Example: {
                    "userName": "SITE_ADMIN_1",
                    "password": "SITE_ADMIN_1",
                    "firstName": "JACK1",
                    "lastName": "TEST1",
                    "phone": "1211232",
                    "email": "ADAF@TEST.COM",
                    "location": "Dallas, TX",
                    "receiveNotification": true,
                    "roleId": 1,
                    "tenantId": 1
                }
    
    c. http://localhost:8080/rest/user (HTTP PUT). This will update existing User.
        Example: {
                    "userName": "SITE_ADMIN_1",
                    "password": "SITE_ADMIN_1",
                    "firstName": "JACK1",
                    "lastName": "TEST1",
                    "phone": "1211232",
                    "email": "ADAF@TEST.COM",
                    "roleId": 1,
                    "tenantId": 1,
                    "location": "Dallas, TX",
                    "receiveNotification": true
                }
    d. http://localhost:8080/rest/user (HTTP DELETE). This will delete existing User.
        HTTP Headers:
            1. userName=admin
    
    e. http://localhost:8080/rest/user (HTTP GET). This will read existing User.
       HTTP Headers:
            1. userName=admin
            2. password=admin

13. For Roles Rest API's. Use Postman tool for this.

    a. http://localhost:8080/rest/getAllRoles (HTTP GET). This will get all existing Roles. --> This will work from WebBrowser.
    
    b. http://localhost:8080/rest/role (HTTP POST). This will add new Role.
        Example: {
                     "roleName": "SUPER_USER",
                     "description": "System Owner"
                 }
    
    c. http://localhost:8080/rest/role (HTTP PUT). This will update existing Role.
       Example: {
                      "roleName": "SUPER_USER",
                      "description": "System Owner"
                 }
    d. http://localhost:8080/rest/role (HTTP DELETE). This will delete existing Role.
       HTTP Headers:
            1. roleId=1
        
    e. http://localhost:8080/rest/role (HTTP GET). This will read existing Role.
       HTTP Headers:
            1. roleId=1
    
    f. http://localhost:8080/rest/roleByName (HTTP DELETE). This will delete existing Role.
       HTTP Headers:
            1. roleName=SUPER_USER
    
    g. http://localhost:8080/rest/roleByName (HTTP GET). This will read existing Role.
       HTTP Headers:
            1. roleName=SUPER_USER

14. For Tenants Rest API's. Use Postman tool for this.

    a. http://localhost:8080/rest/getAllTenants (HTTP GET). This will get all existing Tenants. --> This will work from WebBrowser.
    
    b. http://localhost:8080/rest/tenant (HTTP POST). This will add new Tenant.
        Example: {
                     "tenantName": "TENANT_NAME1",
                     "tenantUrl": "TENANT_URL1",
                     "phone": "214-418-0000",
                     "emailAddress": "Test@yahoo.com",
                     "addressLine1": "ADDRESS_LINE1",
                     "addressLine2": null,
                     "city": "ATLANTA",
                     "state": "GA",
                     "zipCode": "00001"
                 }
    
    c. http://localhost:8080/rest/tenant (HTTP PUT). This will update existing Tenant.
        Example: {
                       "tenantName": "TENANT_NAME1",
                       "tenantUrl": "TENANT_URL1",
                       "phone": "214-418-0000",
                       "emailAddress": "Test@yahoo.com",
                       "addressLine1": "ADDRESS_LINE1",
                       "addressLine2": null,
                       "city": "ATLANTA",
                       "state": "GA",
                       "zipCode": "00001"
                 }
    d. http://localhost:8080/rest/tenant (HTTP DELETE). This will delete existing Tenant.
        HTTP Headers:
            1. tenantId=1
    
    e. http://localhost:8080/rest/tenant (HTTP GET). This will read existing Tenant.
       HTTP Headers:
            1. tenantId=1
            
    f. http://localhost:8080/rest/tenantByName (HTTP DELETE). This will delete existing Tenant.
        HTTP Headers:
            1. tenantName=TENANT_NAME1
        
    g. http://localhost:8080/rest/tenantByName (HTTP GET). This will read existing Tenant.
       HTTP Headers:
            1. tenantName=TENANT_NAME1

15. For Partner Rest API's. Use Postman tool for this.

    a. http://localhost:8080/rest/getAllPartners (HTTP GET). This will get all existing Partners. --> This will work from WebBrowser.
    
    b. http://localhost:8080/rest/partner (HTTP POST). This will add new Partner.
        Example: {
                    "partnerName": "SUPER_USER",
                    "description": "System Owner",
                    "contactName": "RAMESH PITANI",
                    "contactPhone": "24418-0000",
                    "contactAddress": "716 CANAL ST, COPPELL, TX 75019",
                    "contactEmail": "RPITANI@YAHOO.COM"
                }
    
    c. http://localhost:8080/rest/partner (HTTP PUT). This will update existing Partner.
        Example: {
                      "id": 1,
                      "partnerName": "SUPER_USER",
                      "description": "System Owner",
                      "contactName": "RAMESH PITANI",
                      "contactPhone": "24418-0000",
                      "contactAddress": "716 CANAL ST, IRVING, TX 75063",
                      "contactEmail": "RPITANI@YAHOO.COM"
                 }
    d. http://localhost:8080/rest/partnerName (HTTP DELETE). This will delete existing Partner.
        HTTP Headers:
            1. partnerName=PARTNER1
    
    e. http://localhost:8080/rest/partnerName (HTTP GET). This will read existing Partner.
       HTTP Headers:
            1. partnerName=PARTNER1

    f. http://localhost:8080/rest/getAllPartnerImages (HTTP GET). This will get all existing Partner Images. --> This will work from WebBrowser.
    
    g. http://localhost:8080/rest/partnerImage (HTTP POST). This will add new PartnerImage.
        Example: {            
                    "imgName": "Partner Image",
                    "imgDescription": "Partner Image",
                    "imgURL": "http://hello.com",
                    "partnerId": 1
                }
    
    h. http://localhost:8080/rest/partnerImage (HTTP PUT). This will update existing PartnerImage.
        Example: {
                      "id": 1,  
                      "imgName": "Partner Image",
                      "imgDescription": "Partner Image",
                      "imgURL": "http://hello.com",
                      "partnerId": 1
                 }
    i. http://localhost:8080/rest/partnerImage (HTTP DELETE). This will delete existing PartnerImage.
        HTTP Headers:
            1. partnerId=1
    
    j. http://localhost:8080/rest/partnerImage (HTTP GET). This will read existing PartnerImage.
       HTTP Headers:
            1. partnerId=1
            
    f. http://localhost:8080/rest/getAllPartnerVideos (HTTP GET). This will get all existing PartnerVideos. --> This will work from WebBrowser.
        
    g. http://localhost:8080/rest/partnerVideo (HTTP POST). This will add new PartnerVideo.
        Example: {
                    "videoName": "Partner Video",
                    "videoDescription": "Partner Video",
                    "videoURL": "http://helloVideo.com",
                    "partnerId": 1
                }
    
    h. http://localhost:8080/rest/partnerVideo (HTTP PUT). This will update existing PartnerVideo.
        Example: {
                      "id": 1, 
                      "videoName": "Partner Video",
                      "videoDescription": "Partner Video",
                      "videoURL": "http://helloVideo.com",
                      "partnerId": 1
                 }
    i. http://localhost:8080/rest/partnerVideo (HTTP DELETE). This will delete existing PartnerVideo.
        HTTP Headers:
            1. partnerId=1
    
    j. http://localhost:8080/rest/partnerVideo (HTTP GET). This will read existing PartnerVideo.
       HTTP Headers:
            1. partnerId=1        

16. For User Messages Rest API's. Use Postman tool for this.

    a. http://localhost:8080/rest/allUserMessagesByTenantId (HTTP GET). This will get all messages for a User. --> This will work from WebBrowser.
        HTTP Headers:
            1. tenantId=1
            
    b. http://localhost:8080/rest/message (HTTP POST). This will add new message.
        Example: {
                    "messageTitle": "message title1",
                    "messageDescription": "message description1",
                    "tenantId": 1,
                    "messageCategory": "CATEGORY_1"
                }
    
    c. http://localhost:8080/rest/message (HTTP PUT). This will update existing message.
        Example: {
                      "id": 1, 
                      "messageTitle": "message title1",
                      "messageDescription": "message description1 Updated",
                      "tenantId": 1,
                      "messageCategory": "CATEGORY_1"
                 }
    d. http://localhost:8080/rest/message (HTTP DELETE). This will delete existing message.
        HTTP Headers:
            1. messageId=1
    
    e. http://localhost:8080/rest/message (HTTP GET). This will read all messages by userId.
       HTTP Headers:
            1. messageId=1
    
    f. http://localhost:8080/rest/allUserMessagesByByTenantIdAndCategory (HTTP GET). This will read all messages by userId and category.
       HTTP Headers:
            1. tenantId=1
            2. category=CATEGORY_1 // CATEGORY_1, CATEGORY_2, CATEGORY_3

17. For Program Rest API's. Use Postman tool for this.

    a. http://localhost:8080/rest/allProgramsByTenantId (HTTP GET). This will get all Programs for a Tenant. --> This will work from WebBrowser.
        HTTP Headers:
            1. tenantId=1
    
    b. http://localhost:8080/rest/todayProgramsByTenantId (HTTP GET). This will get Today Programs for a Tenant. --> This will work from WebBrowser.
        HTTP Headers:
            1. tenantId=1
    
    c. http://localhost:8080/rest/tomorrowProgramsByTenantId (HTTP GET). This will get Tomorrow Programs for a Tenant. --> This will work from WebBrowser.
        HTTP Headers:
            1. tenantId=1
    
    d. http://localhost:8080/rest/oneWeekProgramsByTenantId (HTTP GET). This will get One Week Programs for a Tenant. --> This will work from WebBrowser.
        HTTP Headers:
            1. tenantId=1
    
    e. http://localhost:8080/rest/program (HTTP POST). This will add new program.
        Example: {
                    "programName": "Program Name1",
                    "programDescription": "Program description1",
                    "programImageUrl": "Program Image1",
                    "programVideoUrl": "Program Video1",
                    "programAddress": "Address",
                    "programDateTimeField": "01-Dec-20 01:00",
                    "tenantId":1,
                    "bookings":10
                }
    
    f. http://localhost:8080/rest/program (HTTP PUT). This will update existing program.
        Example: {
                      "id": 1,
                      "programName": "Program Name1",
                      "programDescription": "Program description1",
                      "programImageUrl": "Program Image1",
                      "programVideoUrl": "Program Video1",
                      "programAddress": "Address",
                      "programDateTimeField": "01-Dec-20 01:00",
                      "tenantId":1,
                      "bookings": 11
                 }
    g. http://localhost:8080/rest/program (HTTP DELETE). This will delete existing program.
        HTTP Headers:
            1. programId=1
    
    h. http://localhost:8080/rest/program (HTTP GET). This will read existing program.
       HTTP Headers:
            1. programId=1           

    i. http://localhost:8080/rest/allProgramBookings (HTTP GET). This will get all Booking Programs for a User. --> This will work from WebBrowser.
    
    j. http://localhost:8080/rest/programBookingsByUserId (HTTP GET). This will read program bookings for user.
           HTTP Headers:
                1. userId=1
    k. http://localhost:8080/rest/programBookingsByProgramId (HTTP GET). This will read user bookings for program.
               HTTP Headers:
                    1. programId=1
    l. http://localhost:8080/rest/programBookingsByUserIdAndProgramId (HTTP GET). This will particular booking for a user and program.
                   HTTP Headers:
                        1. programId=1
                        2. userId=1
    m. http://localhost:8080/rest/programBooking (HTTP POST). This will add new program.
            Example: {
                        "programId": 1,
                        "numberOfPersons": 10,
                        "userId": 1
                    }
        
    n. http://localhost:8080/rest/programBooking (HTTP PUT). This will update existing program.
            Example: {
                          "id": 1,
                          "programId": 1,
                          "numberOfPersons": 10,
                          "userId": 1
                     }
18. For User Video Rest API's. Use Postman tool for this.
 
     a. http://localhost:8080/rest/allUserVideosByTenantId (HTTP GET). This will get all User Videos for a Tenant. --> This will work from WebBrowser.
         HTTP Headers:
             1. tenantId=1
     
     b. http://localhost:8080/rest/video (HTTP POST). This will add User Video.
         Example: {
                        "videoName": "VIDEO_NAME1",
                        "videoDescription": "VIDEO_NAME1",
                        "videoURL": "VIDEO_NAME1",
                        "tenantId": "2"
                 }
     
     c. http://localhost:8080/rest/video (HTTP PUT). This will update existing User Video.
         Example: {
                          "id": 2,
                          "videoName": "VIDEO_NAME1",
                          "videoDescription": "VIDEO_NAME1",
                          "videoURL": "VIDEO_NAME1",
                          "tenantId": "2"
                   }
     d. http://localhost:8080/rest/video (HTTP DELETE). This will delete existing User Video.
         HTTP Headers:
             1. videoId=1
     
     e. http://localhost:8080/rest/video (HTTP GET). This will read existing User Video.
        HTTP Headers:
             1. videoId=1
             
19. For  NEWS Rest API's. Use Postman tool for this.
 
     a. http://localhost:8080/rest/allNEWS (HTTP GET). This will get all NEWS  --> This will work from WebBrowser.
          
     
     b. http://localhost:8080/rest/news (HTTP POST). This will add  news.
         Example: {
                        "title": "VIDEO_NAME1",
                        "description": "VIDEO_NAME1",
                        "url": "NEWS URL",
                        "videoURL": "VIDEO_NAME1"
                 }
     
     c. http://localhost:8080/rest/news (HTTP PUT). This will update existing NEWS.
         Example: {
                          "id": 2,
                          "title": "VIDEO_NAME1",
                        "description": "VIDEO_NAME1",
                        "url": "NEWS URL",
                        "videoURL": "VIDEO_NAME1"
                   }
     d. http://localhost:8080/rest/news (HTTP DELETE). This will delete existing NEWS.
         HTTP Headers:
             1. id=1
     
     e. http://localhost:8080/rest/news (HTTP GET). This will read existing NEWS.
        HTTP Headers:
             1. id=1
                  
     