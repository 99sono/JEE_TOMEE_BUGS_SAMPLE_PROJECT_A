# JEE_TOMEE_BUGS_SAMPLE_PROJECT_A
sample project used to file bugs to TomeEE


This sample project is intended at comparing deployments of JEE code to different containers, namely Weblogic, Glassfish and Tomee.
It primarly targets finding out bugs on Tomee container.


At the date of the first push to the server the tomee.xml file contianing resources is of the form:

```xml

<?xml version="1.0" encoding="UTF-8"?>
<tomee>
  <!-- see http://tomee.apache.org/containers-and-resources.html -->

 
  <Resource id="jdbc/ORCL_XA" type="DataSource" classpath="D:/containers/apache-tomee-plume-1.7.1/ext/derbyclient.jar" >
    #Embedded Derby example
    JdbcDriver = org.apache.derby.jdbc.ClientDriver
    JdbcUrl = jdbc:derby://localhost:1527/ORCL;create=true
    UserName = ORCL
    Password = ORCL
	InitialSize = 7
	MaxIdle = 20
	MaxActive = 20
	JtaManaged = true
</Resource>


<Resource id="jdbc/ORCL_NON_XA" type="DataSource" classpath="D:/containers/apache-tomee-plume-1.7.1/ext/derbyclient.jar" >
    #Embedded Derby example
    JdbcDriver = org.apache.derby.jdbc.ClientDriver
    JdbcUrl = jdbc:derby://localhost:1527/ORCL;create=true
    UserName = ORCL
    Password = ORCL
	InitialSize = 1
	MaxIdle = 2
	MaxActive = 5
	JtaManaged = false
</Resource>



	<!-- JMS Resource Adapter -->
	<Container id="MY_JMS_CONTAINER" ctype="MESSAGE">
        ResourceAdapter = ActiveMQResourceAdapter
    </Container>
	<Resource id="ActiveMQResourceAdapter" type="ActiveMQResourceAdapter">
		BrokerXmlConfig =  broker:(tcp://localhost:7676)
        ServerUrl       =  tcp://localhost:7676
    </Resource>

	<!-- Connection Factory. -->
	<Resource id="jms/NotificationConnectionFactory" type="javax.jms.ConnectionFactory">
		ResourceAdapter = ActiveMQResourceAdapter
		PoolMaxSize = 250
		PoolMinSize = 1
		ConnectionMaxWaitMilliseconds = 10000
		ConnectionMaxIdleMinutes = 5
	</Resource>
	<Resource id="jms/QueueJmsFactory" type="javax.jms.ConnectionFactory">
		ResourceAdapter = ActiveMQResourceAdapter
		PoolMaxSize = 250
		PoolMinSize = 2
		ConnectionMaxWaitMilliseconds = 10000
		ConnectionMaxIdleMinutes = 5
	</Resource>
	<!-- Queues -->
	<Resource id="queue/TinyQueueA" type="javax.jms.Queue">		
		destination = queue_TinyQueueA
	</Resource>
	<Resource id="queue/TinyQueueB" type="javax.jms.Queue">		
		destination = queue_TinyQueueB
	</Resource>
	

</tomee>
```


Current Set of Bugs Associated to this Project:
<ul>
  <li> https://issues.apache.org/jira/browse/TOMEE-1580 (Resolved on build  1.7.3 snapshot build 32)
  <li> https://issues.apache.org/jira/browse/TOMEE-1586 (injection point on tomEE ok)
  <li> https://issues.jboss.org/browse/WELD-1950 (opened injection point issue on jboss)
</ul>

<p>
Please note that regardless of the container used to deploy the project the JTA and NON JTA data sources are needed to get the deployment through.
