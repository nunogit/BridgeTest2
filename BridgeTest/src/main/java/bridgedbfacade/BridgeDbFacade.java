package bridgedbfacade;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperCapabilities;
import org.bridgedb.IDMapperException;
import org.bridgedb.IDMapperStack;
import org.bridgedb.Xref;
import org.bridgedb.bio.Organism;
import org.bridgedb.rdb.GdbProvider;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import bridgedbfacade.model.SimpleXRef;
import bridgedbfacade.model.XrefSet;
import bridgedbfacade.util.NullPrintStream;

public class BridgeDbFacade {
	private String config = "/data/gdb.config";
	private GdbProvider gdp = null;
	private PrintStream original;
		

	public BridgeDbFacade() throws ClassNotFoundException, IDMapperException, IOException {
		initConnection();
	}

	public static void main(String[] args) throws IDMapperException, ClassNotFoundException, IOException {
		BridgeDbFacade bdbf;
		
		if(args.length != 0) 
			bdbf = new BridgeDbFacade(args[0]);
		else
			bdbf = new BridgeDbFacade();

		ObjectMapper mapper = new ObjectMapper();

		//TODO split by space
		for(int i = 1; i < args.length; i++) {
			String jsonInString = mapper.writeValueAsString(bdbf.xrefs(args[i]));
			System.out.println(jsonInString);
		}
	}
	
	public BridgeDbFacade(String config) throws ClassNotFoundException, IDMapperException, IOException {
		//BridgeDb start is very verbose.
		//turn if off to avoid useless information for the command line conversion
		turnOffOutput();
		setConfig(config);
		initConnection();
		turnOnOutput();
	}
	
	
	//To avoid the default messages from BridgeDb
	private void turnOffOutput() {
		original = System.out;
		System.setOut(new NullPrintStream());
	
	}
	
	private void turnOnOutput() {
		System.setOut(original);		
	}
	
	
	
	private void initConnection() throws ClassNotFoundException, IDMapperException, IOException {
		gdp = GdbProvider.fromConfigFile(new File(this.config), true);
		//ClassLoader classLoader = getClass().getClassLoader();
		//gdp = GdbProvider.fromConfigFile(new File(classLoader.getResource(this.config).getFile()), true);
	}



	void setConfig(String config) {
		System.out.println("setting config "+config);
		this.config = config;
	}
	
	public Set<XrefSet> xrefs(String identifier) throws IDMapperException, ClassNotFoundException, IOException {
		//String retVal = "";
		Set<XrefSet> xrefSpecieResultSet = new HashSet<XrefSet>();
		
		IDMapperStack idstack = gdp.getStack();
		Xref xref = new Xref(identifier);

		IDMapperStack orgIdStack = gdp.getStack();

		List<IDMapper> mappers = orgIdStack.getMappers();


		for(IDMapper mp : mappers) {				
			Set<Xref> xrefset = mp.mapID(xref);

			if(xrefset.size()>0) {
				IDMapperCapabilities idc = mp.getCapabilities();
				
				String specie = idc.getProperty("SERIES");
				
				XrefSet xrefSpecieResult = new XrefSet();
				xrefSpecieResult.setSpecie(specie);
				xrefSpecieResult.setId(identifier);
				
				Set<SimpleXRef> xrefresult = new HashSet<SimpleXRef>();
				for(Xref xrefc : xrefset) {
					//Converting an XRef into a simplified XRef
					SimpleXRef xRefr = new SimpleXRef();
					xRefr.setDatasource(xrefc.getDataSource().getSystemCode());
					xRefr.setId(xrefc.getId());
					xrefresult.add(xRefr);
				}
				
				xrefSpecieResult.setXrefs(xrefresult);
				
				
				xrefSpecieResultSet.add(xrefSpecieResult);
			}
		}	
		return xrefSpecieResultSet;
	}
		
	
	String xrefs_alternative(String identifier) throws IDMapperException, ClassNotFoundException, IOException {
		GdbProvider gdp = GdbProvider.fromConfigFile(new File("/home/nuno/dev/BridgeDb-databases/gdb.config"), true);

		IDMapperStack idstack = gdp.getStack();
		//System.out.println("size: " + idstack.getSize() +  "/n");
		
		//Xref xref1 = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
		//Xref xref2 = new Xref("H7C5Q7", DataSource.getByFullName("Uniprot-TrEMBL"));
		//Xref xref3 = new Xref("1234", DataSource.getBySystemCode("L"));
		
		Xref xref = new Xref(identifier);
		//Xref xref = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
		
		Set<Organism> organismSet = gdp.getOrganisms();
		
		for(Organism org : organismSet) {
			IDMapperStack orgIdStack = gdp.getStack();
					
			//System.out.println("se lixou"+ orgIdStack);
			//if(true) { System.out.println("END"); return null; }
			
			Set<Xref> xrefset = orgIdStack.mapID(xref);

			if(xrefset.size()>0)
				System.out.println(xrefset);
			else
				System.out.println("set is 0");
			
			for(Xref xreft : xrefset) {
				System.out.println("result..........: "+xreft.getId() + " " + xreft.getDataSource() + " " + org.latinName());
				xreft.getDataSource();
			}	
		}
		
		if(true) { System.out.println("END"); return null; }
		return identifier;
	}
}
