import org.bridgedb.BridgeDb;
import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperException;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bridgedb.AttributeMapper;
import org.bridgedb.BridgeDb;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperException;
import org.bridgedb.Xref;
//import org.bridgedb.rdb.SimpleGdbImpl2;
import org.bridgedb.bio.DataSourceTxt;

public class Test {

	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		
		Class.forName("org.bridgedb.rdb.IDMapperRdb");
		//Class.forName("org.apache.derby.jdbc.ClientDriver");
		//DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
		DriverManager.registerDriver( new org.apache.derby.jdbc.EmbeddedDriver() );
		Class.forName("com.mysql.jdbc.Driver");
		
		//SimpleGdbImpl2 s = new SimpleGdbImpl2();
		try {
			// THIS IS REALY NEEDED BEFORE CONNECT
			DataSourceTxt.init(); //Initialize BrideDb data source 
			//BridgeDb.register("idmapper-pgdb", new DriverPgdb());
			System.out.println();
			List<List<String>> readData = (new TSVRead()).readData();
			System.out.println();
			//IDMapper gdb = BridgeDb.connect("idmapper-pgdb:/home/nuno/dev/data2services-idmapping/create-bridgedb-genedb/_target/Hs_Derby_Ensembl_80.bridge");
			IDMapper gdb = BridgeDb.connect("idmapper-jdbc:mysql://localhost/bridgedbcp?user=root&password=root");
			
			//gdb.
			
			//IDMapper gdb = BridgeDb.connect("idmapper-pgdb:/home/nuno/Downloads/Bs_Derby_Ensembl_83.bridge");
			
			int i = 0;
			Random rand = new Random();
			long iTime = System.currentTimeMillis();
			while(i++ != 0) {
				System.out.println(i);
				//System.out.println(readData.size());
				int n = rand.nextInt(readData.size());
				List<String> line = readData.get(n);
				Xref xref = new Xref(line.get(0), DataSource.getBySystemCode(line.get(1)));
				//Xref xref = new Xref("1234", DataSource.getByFullName("Entrez Gene"));
				//System.out.println(xref.getId() + " " + xref.getKnownUrl());	
				//System.out.println(gdb.mapID(xref));
				// DataSource.getByFullName("Ensembl");
				long siTime = System.currentTimeMillis();
				gdb.mapID(xref);
				long seTime = System.currentTimeMillis();
				long stTime= ((seTime - siTime));
				System.out.println("Timespent " + stTime );
				//System.out.println(gdb.mapID(xref, DataSource.getByFullName("Ensembl")));
			}
			
			Xref xref = new Xref("ENSG00000049541", DataSource.getByFullName("Ensembl"));
			xref = new Xref("H7C5Q7", DataSource.getByFullName("Uniprot-TrEMBL"));
			
			
			System.out.println(xref.getId() + " " + xref.getKnownUrl());	
			System.out.println("uniprot conv: "+gdb.mapID(xref, DataSource.getByFullName("Uniprot-TrEMBL")));
			
			gdb.mapID(xref, DataSource.getByFullName("Uniprot-TrEMBL"));
			
			long eTime = System.currentTimeMillis();
			long tTime= ((eTime - iTime));
			System.out.println("Timespent " + tTime );
			

			
		} catch (IDMapperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
