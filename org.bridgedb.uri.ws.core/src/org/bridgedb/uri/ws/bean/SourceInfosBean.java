// BridgeDb,
// An abstraction layer for identifier mapping services, both local and online.
//
// Copyright 2006-2009  BridgeDb developers
// Copyright 2012-2013  Christian Y. A. Brenninkmeijer
// Copyright 2012-2013  OpenPhacts
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
package org.bridgedb.uri.ws.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;
import org.bridgedb.statistics.SourceInfo;

/**
 *
 * @author Christian
 */
@XmlRootElement(name="sourceInfos")
public class SourceInfosBean {

    private Set<SourceInfoBean> sourceInfo;
    
    /**
     * WS Constructor
     */
    public SourceInfosBean(){
        sourceInfo = new HashSet<SourceInfoBean>();
    }

    public List<SourceInfo> getSourceInfos() {
        ArrayList<SourceInfo> results = new ArrayList<SourceInfo>();
        for (SourceInfoBean bean:sourceInfo){
            results.add(bean.asSourceInfo());
        }
        return results;
    }

    public void addSourceInfo(SourceInfo info) {
        sourceInfo.add(new SourceInfoBean(info));
    }

    public boolean isEmpty() {
        return sourceInfo.isEmpty();
    }

    /**
     * @return the sourceInfo
     */
    public Set<SourceInfoBean> getSourceInfo() {
        return sourceInfo;
    }

    /**
     * @param sourceInfo the sourceInfo to set
     */
    public void setSourceInfo(Set<SourceInfoBean> sourceInfo) {
        this.sourceInfo = sourceInfo;
    }
    

}
