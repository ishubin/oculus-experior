/*******************************************************************************
 * Copyright 2011 Ivan Shubin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.mindengine.oculus.experior.samples;

import java.util.Collection;
import java.util.LinkedList;

public class SampleEvent {

    private Class<?> event; //class for the event annotation
    private String name; //Not needed in case if the event was specified
    private String method;
    private Object[] eventObjects;
    
    private SampleEvent(){
    }
    
    public static Collection<SampleEvent> collection(SampleEvent...events){
        Collection<SampleEvent> c = new LinkedList<SampleEvent>();
        for (SampleEvent testEvent : events){
            c.add(testEvent);
        }
        return c;
    }
    
    
    public static SampleEvent event(Class<?> event, String method, Object...eventObjects){
        SampleEvent te = new SampleEvent();
        te.setEvent(event);
        te.setMethod(method);
        te.setEventObjects(eventObjects);
        return te;
    }
    
    public static SampleEvent event(Class<?> event, Object...eventObjects){
        SampleEvent te = new SampleEvent();
        te.setEvent(event);
        te.setEventObjects(eventObjects);
        return te;
    }
    
    public static SampleEvent event(String name, String method, Object...eventObjects){
        SampleEvent te = new SampleEvent();
        te.setName(name);
        te.setMethod(method);
        te.setEventObjects(eventObjects);
        return te;
    }
    
    public static SampleEvent event(String name, Object...eventObjects){
        SampleEvent te = new SampleEvent();
        te.setName(name);
        te.setEventObjects(eventObjects);
        return te;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public void setEvent(Class<?> event) {
        this.event = event;
    }
    public Class<?> getEvent() {
        return event;
    }

    public void setEventObjects(Object[] eventObjects) {
        this.eventObjects = eventObjects;
    }

    public Object[] getEventObjects() {
        return eventObjects;
    }
    
    @Override
    public String toString() {
        String str = "";
        if(event!=null) {
            str+=event.toString()+" ";
        }
        if(name!=null) {
            str+=name;
        }
        return str;
    }
}
