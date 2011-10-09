/*******************************************************************************
 * 2011 Ivan Shubin http://mindengine.net
 * 
 * This file is part of Oculus Experior.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Oculus Experior.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package net.mindengine.oculus.experior.test.resolvers.dataprovider;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.LinkedList;

import net.mindengine.oculus.experior.annotations.DataSource;
import net.mindengine.oculus.experior.exception.TestConfigurationException;

/**
 * This is a default implementation of data-dependency-resolver for oculus-experior.
 * In order to get a list of dependency it check if the field has {@link DataSource} annotation and uses "depends" field from this annotation.<br>
 * It allows to specify dependencies in following ways:
 * <ul>
 *  <li><i>"object1 : refComponent"</i> - where <i>"object1"</i> is a name of field of a component and it will be set with a field <i>"refComponent"</i> from test</li>
 *  <li><i>"object1 &lt; refComponent"</i> - same result as above</li>
 *  <li><i>"refComponent &gt; object1"</i> - same result as above</li>
 *  <li><i>"object1 &lt; refComponent.childField.deepField"</i> - will fetch child field following the specified structure</li>
 *  <li><i>"refComponent.childField.deepField &gt; object1"</i> - same as above but specification order is different</li>
 *  <li><i>"object"</i> - means that the field of component has the same name as the referenced field from test</li> 
 * </ul>
 * @author Ivan Shubin
 *
 */
public class DefaultDataDependencyResolver implements DataDependencyResolver{

    @Override
    public Collection<DataDependency> resolveDependencies(Annotation[] annotations) throws TestConfigurationException {
        
        if(annotations!=null){
            DataSource dataSource = null;
            for(int i=0;i<annotations.length; i++){
                if(annotations[i] instanceof DataSource){
                    dataSource = (DataSource) annotations[i];
                } 
            }
            if(dataSource==null) throw new TestConfigurationException("Cannot find DataSource annotation");
            
            String[] dependcySpecs = dataSource.dependencies();
            if(dependcySpecs!=null && dependcySpecs.length>0) {
                Collection<DataDependency> dataDependencies = new LinkedList<DataDependency>();
                
                for(String spec : dependcySpecs) {
                    if(spec!=null && !spec.isEmpty()) {
                        DataDependency dataDependency = new DataDependency();
                        
                        if(spec.contains(">")){
                            int i = spec.indexOf(">");
                            dataDependency.setFieldName(spec.substring(i+1).trim());
                            dataDependency.setReferenceName(spec.substring(0, i).trim());
                        }
                        else if(spec.contains("<")){
                            int i = spec.indexOf("<");
                            dataDependency.setFieldName(spec.substring(0, i).trim());
                            dataDependency.setReferenceName(spec.substring(i+1).trim());
                        }
                        else if(spec.contains(":")){
                            int i = spec.indexOf(":");
                            dataDependency.setFieldName(spec.substring(0, i).trim());
                            dataDependency.setReferenceName(spec.substring(i+1).trim());
                        }
                        else {
                            dataDependency.setFieldName(spec.trim());
                            dataDependency.setReferenceName(dataDependency.getFieldName());
                        }
                        dataDependencies.add(dataDependency);
                    }
                }
                return dataDependencies;
            }
        }
        return null;
    }

}
