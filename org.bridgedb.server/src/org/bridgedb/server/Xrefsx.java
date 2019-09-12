package org.bridgedb.server;

import org.bridgedb.DataSource;
import org.bridgedb.Xref;
import org.restlet.resource.ResourceException;

public class Xrefsx extends Xrefs {

	protected void doInit() throws ResourceException {
		super.doInit();
		try {
		    System.out.println( "Xrefs.doInit start" );
			//Required parameters
			String id = urlDecode((String)getRequest().getAttributes().get(IDMapperService.PAR_ID));
			String dsName = urlDecode((String)getRequest().getAttributes().get(IDMapperService.PAR_SYSTEM));
			DataSource dataSource = parseDataSource(dsName);
			if(dataSource == null) {
				throw new IllegalArgumentException("Unknown datasource: " + dsName);
			}
			
			xref = new Xref(id, dataSource);
			
			//Optional parameters
			String targetDsName = (String)getRequest().getAttributes().get(IDMapperService.PAR_TARGET_SYSTEM);
			if(targetDsName != null) {
				targetDs = parseDataSource(urlDecode(targetDsName));
			}
		} catch(Exception e) {
			throw new ResourceException(e);
		}
	}

	
	
}
