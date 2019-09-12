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
package org.bridgedb;

import java.util.Set;

import org.bridgedb.impl.InternalUtils;

/**
 * Contains default implementation of some IDMapper methods.
 */
public abstract class AbstractIDMapper implements IDMapper
{

	/**
	 * Default implementation of mapID.
	 * This just calls <pre>mapID (Set&lt;Xref&gt;, Set&lt;DataSource&gt;)</pre> for mapping
	 * multiple id's with a set containing only a single Xref.
	 * {@inheritDoc}
	 */
	public Set<Xref> mapID (Xref srcRef, DataSource... tgtDataSources) throws IDMapperException
	{
		return InternalUtils.mapSingleFromMulti(this, srcRef, tgtDataSources);
	}
}
