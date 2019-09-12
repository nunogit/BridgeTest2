package bridgedbfacade;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.bridgedb.DataSource;
import org.bridgedb.IDMapperException;
import org.bridgedb.IDMapperStack;
import org.bridgedb.Xref;
import org.bridgedb.bio.Organism;
import org.bridgedb.rdb.GdbProvider;

public class GDBProviderTest2 {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		GdbProvider gdp = new GdbProvider();
		
		Set<Organism> gdpSet = gdp.getOrganisms();
		System.out.println("Showing organisms...");
		
		GdbProvider gdp2 = new GdbProvider();
		
		try {
			//gdp2 = GdbProvider.fromConfigFile(new File("/home/nuno/eclipse-workspace-bridgedb/BridgeDb/gdb.config"), true);
			gdp = GdbProvider.fromConfigFile(new File("/home/nuno/dev/BridgeDb-databases/gdb.config"), true);
			
			List<List<String>> readData = (new TSVRead()).readData();
			
			IDMapperStack idstack = gdp2.getStack();
			

			int i = 0;
			Random rand = new Random();
			long iTime = System.currentTimeMillis();
			while(i++ < 100) {
				System.out.println(i);
				//System.out.println(readData.size());
				int n = rand.nextInt(readData.size());
				List<String> line = readData.get(n);
				//Xref xref = new Xref(line.get(0), DataSource.getBySystemCode(line.get(1)));
				Xref xref = new Xref( line.get(0) );
				//System.out.println("Converting... "+line.get(0));
				
				//Xref xref = new Xref("1234", DataSource.getByFullName("Entrez Gene"));
				//System.out.println(xref.getId() + " " + xref.getKnownUrl());	
				//System.out.println(gdb.mapID(xref));
				// DataSource.getByFullName("Ensembl");
				
				long siTime = System.currentTimeMillis();
				Set<Xref> xrefset = idstack.mapID(xref);
				
				for(Xref xrefres : xrefset) {
					System.out.println("result: " + xrefres.getId() + " " + xrefres.getDataSource() + " " + xrefres.getDatabaseId());
				}
				
				long seTime = System.currentTimeMillis();
				long stTime= ((seTime - siTime));
				System.out.println("Timespent " + stTime );
				//System.out.println(gdb.mapID(xref, DataSource.getByFullName("Ensembl")));
			}
			
			Xref xref = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
			xref = new Xref("H7C5Q7", DataSource.getByFullName("Uniprot-TrEMBL"));
			
			
			System.out.println(xref.getId() + " " + xref.getKnownUrl());	
			System.out.println("uniprot conv: "+idstack.mapID(xref, DataSource.getByFullName("Uniprot-TrEMBL")));
			
			idstack.mapID(xref, DataSource.getByFullName("Uniprot-TrEMBL"));
			
			long eTime = System.currentTimeMillis();
			long tTime= ((eTime - iTime));
			System.out.println("Timespent " + tTime );
			
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
