/*******************************************************************************
* Copyright 2012 Ivan Shubin http://mindengine.net
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
package net.mindengine.oculus.experior.framework.verification.text;


public interface TextVerificator {

    public boolean is(String string);

    public boolean isNot(String string);

    public boolean contains(String string);

    public boolean doesNotContain(String string);

    public boolean startsWith(String string);

    public boolean doesNotStartWith(String string);
    
    public boolean endsWith(String string);

    public boolean doesNotEndWith(String string);

    public boolean matches(String string);
    
    public boolean doesNotMatch(String string);
    
    public boolean isOneOf(String... strings);
    
    public boolean isNotOneOf(String... strings);

    public TextVerificator toLowerCase();

    public TextVerificator toUpperCase();

    public TextVerificator replace(String seek, String replace);

    public TextVerificator substring(int id1, int id2);
    
    public TextVerificator substring(int id);
    
    public TextVerificator replaceAll(String target, String replacement);

    
    

}
