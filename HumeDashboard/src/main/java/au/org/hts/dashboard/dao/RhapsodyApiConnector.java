package au.org.hts.dashboard.dao;

public interface RhapsodyApiConnector {
	
		// begin of transaction by retrieving token for next calls
		void setToken(String engine);
		
		void setToken();
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Alert API														//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// retrieves a list of all active alerts in the Rhapsody engine
		String getActiveAlert(String engine);
		
		// retrieves the current state of the specified active or user-dismissed alert
		String getActiveAlert(String engine, String id);
		
		// retrieves the default component threshold settings
		String getAlertDefaultSetting(String engine);
		
		// retrieves the system alert settings
		String getAlertSetting(String engine);
		
		// retrieves the delivery method settings
		String getAlertDeliveryMethods(String engine);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Archive API														//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// gets the archive cleanup status and results
		String getArchiveCleanup(String engine);
		
		// retrieves the archive cleanup settings
		String getArchiveCleanupSetting(String engine);
		
		// retrieve the defragmentation task status and results
		String getArchiveDefrag(String engine);
		
		// retrieve the defragmentation task settings
		String getArchiveDefragSetting(String engine);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Backup Schedule API												//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// gets a list of all backup schedules
		String getBackupSchedule(String engine);
		
		// gets the status of a specific backup schedule
		String getBackupSchedule(String engine, String id);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Basic Information API											//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// retrieves engine version, name and uptime duration
		String getBasicInfo(String engine);
		
		String getBasicInfo();
		
//		// retrieves engine name
//		String getEngineName(String engine);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Components Monitoring API										//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// retrieves the identifiers of the communication points, filters and routers in all lockers that the user has view permissions for. Return the identifier, UUID, name and current state
		String getComponents(String engine);
		
		// retrieves the identifiers of the communication points and routes in all lockers that the user has view permissions for. Returns the identifier, UUID, name and current state and the inbound, outbound and failed queue sizes for communication points
		String getComponentsStatus(String engine);
		
		String getComponentsStatus();		
			
		// retrieves a list of the ports in use by standard components in the engine 
		String getRegisteredPorts(String engine);
		
		// retrieves the information for a communication point with the given identifier or UUID. Returns the identifier, UUID, name, mode, type, path, state, inbound queue size, outbound queue size, failed queue size, received count, send count, failed count, input idle time, output idle time and schedule 
		String getCommpoint(String engine, String id);
		
		String getCommpoint(String id);
			
		// retrieves the state of the communication point with the given identifier. This state may be started, stopped, not configured and so forth 
		String getCommpointState(String engine, String id);
		
		// retrieves the number of sent, received and failed messages on the given commincation point
		String getCommpointMessageCount(String engine, String id);
		
		// retrieves the support notes for the communication point with a given identifier
		String getCommpointSupportNotes(String engine, String id);
			
		// retrieves the custom communication point alert settings 
		String getCommpointAlertSettings(String engine, String id);
		
		// retrieves the information for a route with the given identifier or UUID. Returns the identifier, UUID, name, path, state, processing queue size, waiting queue size, processed count, idle time 
		String getRoute(String engine, String id);
			
		// retrieves the state of the route with the given identifier. This state may be started, stopped, not configured and so on 
		String getRouteState(String engine, String id);
			
		// retrieves the number of processed messages on the given route 
		String getRouteMessageCount(String engine, String id);
			
		// retrieves the support notes for the route with a given identifier
		String getRouteSupportNotes(String engine, String id);
		
		// retrieves the custom route alert settings
		String getRouteAlertSettings(String engine, String id);
		
		// retrieves the support notes for the filter in the route with a given identifier
		String getFilterSupportNotes(String engine, String id);
		
		// retrieves the identifiers of the web services. Returns the identifier, UUID, name and current state 
		String getWebservice(String engine);
		
		// retrieves the information for a web service with the given identifier or UUID. Returns the identifier, UUID, name, state, input idle time, output idle time, received count, sent count, WSDL location 
		String getWebservice(String engine, String id);
		
		// retrieves the state of the web service with the given identifier. This state may be started, stopped, not configured and so on 
		String getWebserviceState(String engine, String id);
		
		// retrieves the number of sent and received messages on the given web service 
		String getWebserviceMessageCount(String engine, String id);
			
		// retrieves the custom web service alert settings 
		String getWebserviceAlertSettings(String engine, String id);
		
		// retrieves a list of all active alerts that are associated with components on the specified watchlist. This allow the system administrator to group components as required, and so report the alerts separately. The response body is in the same format as when retrieving alerts for the entire engine 
		String getWatchlist(String engine, String name);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Engine Statistics API											//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// retrieves the simple memory usage (currently in use, used by the JVM, working and total allocated) for Rhapsody
		String getSimpleMemoryUsage(String engine);
		
		String getSimpleMemoryUsage();
		
		// retrieves the current memory usage for 30 mins (currently in use, used by the JVM, working and total allocated) for Rhapsody
		String getMemoryUsage(String engine);
		
		String getMemoryUsage();
		
		// retrieves the current available and total disk space for the installation and data directories on the Rhapsody engine
		String getDiskspace(String engine);
		
		String getDiskspace();

		// retrieves the current CPU usage of the system hosting the Rhapsody engine as a percentage
		String getCPUUsage(String engine);
		
		String getCPUUsage();
			
		// retrieves the number of received, processed, sent and failed messages on the engine
		String getTotalMessageCount(String engine);
		
		String getTotalMessageCount();
		
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Error and Hold Queue Monitoring API								//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// retrieves the number of messages on the error queue. Limits the count to only errors produced in lockers for which the user has the 'Search Error and Hold Queues' access right
		String getErrorqueueCount(String engine);
		
		// retrieves the number of messages on the hold queue, Limits the count to only errors produced in lockers for which the user has the 'Search Error and Hold Queues' access right
		String getHoldqueueCount(String engine);
		
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Message Retrieval API											//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// retrieves the message metadata for a specified message (primary, but not exclusively, the message properties)
		String getMessageMeta(String engine, String id);
		
		// retrieves the message path for a specified message and all the events associated with the message
		String getMessagePath(String engine, String id);
			
		// retrieves the message body for the specified message. The message itself is returned as b inary, but a prameter on the response Content-Type idicates the charater encoding associated with the message body if this has been explicityly set in Rhapsody. No character encoding information is returned if this has nver been explicitly set within Rhapsody
		String getMessageBody(String engine, String id);
		
		// retrieves a multi-part response containing both the message metadata and body for a specified message. Access Rights govern whether the message metadata or body or both are returned 
		String getMessage(String engine, String id);
		
		
		
			
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Configuration Administration API								//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns the status of the current configuration load
		String getConfigStatus(String engine, String id);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Custom Modules and Libraries API								//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns the custom modules
		String getCustomModule(String engine);
		
		// returns the module with the given name
		String getCustomModule(String engine, String name);
		
		// returns the custom libraries
		String getCustomLibrary(String engine);
		
		// returns the library with the given name
		String getCustomLibrary(String engine, String name);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										License Administration API										//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns the details of current licenses that are installed and the number of communication points in use which counts towards each licese limit
		String getLicense(String engine);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Lookuptable API													//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns a list of the lookup tables
		String getLookuptable(String engine);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Security Object API												//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns a list of all the security objects
		String getSecurity(String engine);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Users and Access Groups Administration API						//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns the list of users added to the engine
		String getUser(String engine);
		
		// returns the access groups
		String getGroup(String engine);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Variables Administration API									//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns the list of all variables and their values
		String getVariable(String engine);
		
		// returns the value of the named variable
		String getVariable(String engine, String name);
		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		//																										//			
		//										Web Service User Store Administration API						//	
		//																										//
		//////////////////////////////////////////////////////////////////////////////////////////////////////////
		// returns the web service user store with the passwords ommitted
		String getWebserviceUser(String engine);


}
