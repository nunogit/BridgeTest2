// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
// Copyright 2006-2009 BridgeDb developers
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package org.bridgedb.rdb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bridgedb.BridgeDb;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperException;
import org.bridgedb.IDMapperStack;
import org.bridgedb.bio.Organism;
import org.bridgedb.rdb.impl.ConfigFile;

/**
 * Utility class that maintains a list of synonym databases and the species they
 * apply to. This list can be read from a configuration file with on each line:
 * <pre>
 * species_latin_name[Tab]database_file_location
 * </pre>
 * If a database applies to all species (e.g. metabolites), use "*" as species.
 */
public class GdbProvider {
	Map<Organism, IDMapperStack> organism2gdb = new HashMap<Organism, IDMapperStack>();
	List<IDMapper> globalGdbs = new ArrayList<IDMapper>();
	
	public Set<Organism> getOrganisms()
	{
		return organism2gdb.keySet();
	}
	
	public void addOrganismGdb(Organism organism, IDMapper gdb) {
		IDMapperStack l = organism2gdb.get(organism);
		if(l == null) {
			organism2gdb.put(organism, l = new IDMapperStack());
			l.setDatabaseId(organism.latinName());
			//System.out.println("setting "+organism.latinName());
			l.setTransitive(transitive);
			for (IDMapper globalGdb : globalGdbs)
			{
				l.addIDMapper(globalGdb);
			}
		}
		l.addIDMapper(gdb);
	}
	
	public void removeOrganismGdb(Organism organism, IDMapperRdb gdb) {
		IDMapperStack l = organism2gdb.get(organism);
		if(l != null) {
			l.removeIDMapper(gdb);
		}
	}
	
	public void addGlobalGdb(IDMapper gdb) {
		if(!globalGdbs.contains(gdb)) 
		{
			globalGdbs.add(gdb);
			for (Organism org : organism2gdb.keySet())
			{
				organism2gdb.get(org).addIDMapper(gdb);
			}
		}
	}
	
	public void removeGlobalGdb(IDMapper gdb) {
		if (globalGdbs.contains(gdb))
		{
			globalGdbs.remove(gdb);
			for (Organism org : organism2gdb.keySet())
			{
				organism2gdb.get(org).removeIDMapper(gdb);
			}
		}
	}
	
	/** @deprecated use getStack(organism) instead */
	public List<IDMapper> getGdbs(Organism organism)
	{
		return getStack(organism).getMappers();
	}
	

	
	public IDMapperStack getStack(Organism organism) {
		
		System.out.println("GETTING "+ organism + " from " + organism2gdb.size()); 
		
		//IDMapperStack gdbs = null;
		//apparently not working
		IDMapperStack gdbs = organism2gdb.get(organism);
		
		//for (Map.Entry<Organism, IDMapperStack> entry : organism2gdb.entrySet()) {
		//	if( entry.getKey().latinName().equals( organism.latinName() )) {
		//		System.out.println("Comparing " + entry.getKey().latinName() + "  " + organism.latinName());
		//		gdbs=entry.getValue();
		//		System.out.println("se foudeu "+gdbs);
		//	}
		//}
		System.out.println("se foudeu2 "+gdbs);
		if(gdbs == null) {
			gdbs = new IDMapperStack();
			gdbs.setTransitive(transitive);
			for (IDMapper globalGdb : globalGdbs)
			{
				System.out.println("global " + globalGdb.toString());
				gdbs.addIDMapper(globalGdb);
			}
		} else {
			System.out.println("nao nulo");
		}
		return gdbs;
	}
	
	/*public IDMapperStack getStack() {
		IDMapperStack gdbs = new IDMapperStack();
		gdbs.setTransitive(transitive);
		for (IDMapper globalGdb : globalGdbs)
		{
			gdbs.addIDMapper(globalGdb);
		}
		return gdbs;
	}*/
	
	public IDMapperStack getStack() {
		IDMapperStack fullStack = new IDMapperStack();
		
		for (IDMapperStack stack : organism2gdb.values()) {
		    for(IDMapper idMapper : stack.getMappers()) {
		        //String databaseId = stack.getDatabaseId(); 
		    	//fullStack.addIDMapper(idMapper, databaseId);
		    	fullStack.addIDMapper(idMapper);
			}
		}
		return fullStack;
	}
	
	static final String DB_GLOBAL = "*";
	
	private final boolean transitive;
	public GdbProvider() { this(false); }
	public GdbProvider(boolean transitive)
	{
		this.transitive = transitive;
	}
	
	public static GdbProvider fromConfigFile(File f) throws IDMapperException, IOException, ClassNotFoundException
	{
		return fromConfigFile(f, false);
	}
	
	public static GdbProvider fromConfigFile(File f, boolean transitive) throws IDMapperException, IOException, ClassNotFoundException 
	{	
		ConfigFile cf = new ConfigFile(f);	
		GdbProvider gdbs = new GdbProvider(transitive);
		List<String> drivers = cf.getDrivers();
		
		// add a few defaults that will always be loaded
		drivers.add("org.bridgedb.rdb.IDMapperRdb");
		drivers.add("org.bridgedb.file.IDMapperText");
		drivers.add("com.mysql.jdbc.Driver");
		
		for (String driver : drivers)
		{
			try
			{
				Class.forName (driver);
				System.out.println ("Loaded driver: " + driver);
			}
			catch (ClassNotFoundException ex)
			{
				System.out.println ("Warning: driver '" + driver + "'  not in classpath, some features may not be available.");
			}
		}

		for (String key : cf.getMappers().keySet())
		{
			for (String value : cf.getMappers().get(key))
			{
				IDMapper mapper = BridgeDb.connect (value);
				Organism org = Organism.fromLatinName(key);
				
				if(org != null) {
					System.out.println("resolving "+org.latinName());
					mapper.setDatabaseId(org.latinName());
					gdbs.addOrganismGdb(org, mapper);
				} else if(DB_GLOBAL.equalsIgnoreCase(key)) {
					gdbs.addGlobalGdb(mapper);
				} else {
					System.out.println("Unable to parse organism: " + key);
				}
			}
		}

		return gdbs;
	}
}
