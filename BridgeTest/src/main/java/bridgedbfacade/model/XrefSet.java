package bridgedbfacade.model;

import java.util.List;
import java.util.Set;

import org.bridgedb.Xref;

public class XrefSet {
	private Set<SimpleXRef> xrefs;
	private String specie = "";
	private String id = "";
	
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public Set<SimpleXRef> getXrefs() {
		return xrefs;
	}
	public void setXrefs(Set<SimpleXRef> xrefs) {
		this.xrefs = xrefs;
	}
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
