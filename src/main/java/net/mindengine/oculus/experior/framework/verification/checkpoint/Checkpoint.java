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
package net.mindengine.oculus.experior.framework.verification.checkpoint;

public abstract class Checkpoint implements Cloneable{

    public abstract void passed();
    
    public abstract void failed();

    protected Boolean status = true;
    
    protected Boolean checkStarted = false;
    
    public Checkpoint checkIf(boolean status) {
        checkStarted = true;
        if(!status) {
            this.status = false;
        }
        return this;
    }

    
    public void done() {
        if(checkStarted) {
            if(status) {
                passed();
            }
            else failed();
        }
    }

    public Checkpoint clear() {
        try {
            Checkpoint checkpoint = (Checkpoint) this.clone();
            checkpoint.checkStarted = false;
            checkpoint.status = true;
            return checkpoint;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

}
