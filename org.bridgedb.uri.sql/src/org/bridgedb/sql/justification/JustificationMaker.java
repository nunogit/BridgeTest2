// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright      2012  Christian Y. A. Brenninkmeijer
// Copyright      2012  OpenPhacts
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
package org.bridgedb.sql.justification;

import org.bridgedb.utils.BridgeDBException;

/**
 *
 * @author Christian
 */
public interface JustificationMaker {

    public String combine(String left, String right) throws BridgeDBException;
    
    public String possibleCombine(String left, String right);
    
    public String getInverse(String justification);
    
    public String getForward(String justification);

}
