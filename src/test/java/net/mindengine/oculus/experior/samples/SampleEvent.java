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