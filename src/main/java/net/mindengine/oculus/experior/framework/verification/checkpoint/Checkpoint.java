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
