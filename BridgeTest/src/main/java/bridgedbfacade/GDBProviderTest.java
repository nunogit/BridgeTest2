package bridgedbfacade;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

import org.bridgedb.DataSource;
import org.bridgedb.IDMapperException;
import org.bridgedb.IDMapperStack;
import org.bridgedb.Xref;
import org.bridgedb.bio.Organism;
import org.bridgedb.rdb.GdbProvider;

public class GDBProviderTest {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		GdbProvider gdp = new GdbProvider();
		
		Set<Organism> gdpSet = gdp.getOrganisms();
		System.out.println("Showing organisms...");
		
		GdbProvider gdp2;
		try {
			//gdp2 = GdbProvider.fromConfigFile(new File("/home/nuno/eclipse-workspace-bridgedb/BridgeDb/gdb.config"), true);
			gdp2 = GdbProvider.fromConfigFile(new File("/data/gdb.config"), true);
			
			
			IDMapperStack idstack = gdp2.getStack();
			System.out.println("nr species"+idstack.getSize());
			
			//Xref xref1 = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
			//Xref xref2 = new Xref("H7C5Q7", DataSource.getByFullName("Uniprot-TrEMBL"));
			//Xref xref3 = new Xref("1234", DataSource.getBySystemCode("L"));
			
			Xref xref = new Xref("1234");
		         xref = new Xref("11748062_s_at");
			
			//Set<Organism> organismSet =  gdp2.getOrganisms();
			//for(Organism org : organismSet) {
			//	IDMapperStack orgIdStack = gdp2.getStack(org);
			//	Set<Xref> xrefset2 = orgIdStack.mapID(xref);
			//	for(Xref xreft : xrefset2) {
			//		System.out.println(xreft.getId() + " " + xreft.getDataSource() + " " + xreft.getDatabaseId());
			//		xreft.getDataSource();
			//	}	
			//}
				
			IDMapperStack idStack = gdp2.getStack(); 

			Set<Xref> xrefset = idstack.mapID(xref);
			
			System.out.println("response size "+xrefset.size());
			
			for(Xref xreft : xrefset) {
				System.out.println(" -- "+xreft.getId() + " " + xreft.getDataSource() + " " + xreft.getDatabaseId());
				xreft.getDataSource();
			}
			
		}
		catch (IDMapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Organism organism : gdpSet) {
			System.out.println(organism);
		}
		
		
	}
	
}
