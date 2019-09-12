package bridgedbfacade.model;

import java.util.List;

public class SimpleXRef {
	private String id;
	private String datasource;
	
	//removed the getters/setters to avoid appearing in the WS result 
	private String specie;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}

}
