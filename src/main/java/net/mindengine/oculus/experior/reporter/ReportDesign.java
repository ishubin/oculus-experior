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
package net.mindengine.oculus.experior.reporter;

import java.util.Collection;

/**
 * Static methods are used to defined text design elements
 * 
 * @author Ivan Shubin
 * 
 */
public class ReportDesign {
    public static String bold(String text) {
        return "[b]" + text + "[/b]";
    }

    public static String code(String text) {
        return "[code]" + text + "[/code]";
    }

    public static String customLink(String url, String title) {
        return "[clink='" + url + "']" + title + "[/clink]";
    }

    public static String stackTrace(String text) {
        return "[stack-trace]" + text + "[/stack-trace]";
    }

    public static String chart(Long chartId) {
        return "[chart]" + chartId + "[/chart]";
    }

    public static String table(String[] columns, String[][] cells) {
        StringBuffer str = new StringBuffer();
        str.append("[table]");
        str.append("[thead]");
        str.append("[tr]");
        for (int i = 0; i < columns.length; i++) {
            str.append("[td]");
            str.append(columns[i]);
            str.append("[/td]");
        }
        str.append("[/tr]");
        str.append("[/thead]");
        str.append("[tbody]");
        for (int row = 0; row < cells.length; row++) {
            str.append("[tr]");
            for (int col = 0; col < cells[row].length; col++) {
                str.append("[td]");
                str.append(cells[row][col]);
                str.append("[/td]");
            }
            str.append("[/tr]");
        }
        str.append("[/tbody]");
        str.append("[/table]");
        return str.toString();
    }

    public static String breakline() {
        return "[br/]";
    }

    public static String controlName(String name) {
        return "\"[b]" + name + "[/b]\"";
    }

    public static String italic(String text) {
        return "[i]" + text + "[/i]";
    }

    public static String newline() {
        return "[br]";
    }

    public static String string(String text) {
        if (text != null)
            return "\"[string]" + text + "[/string]\"";
        else
            return nullValue();
    }

    public static String nullValue() {
        return "[null-value/]";
    }

    public static String shortString(String text) {
        if (text != null) {
            if (text.length() > 50) {
                text = text.substring(0, 50) + "...";
            }
        }
        return "\"[string]" + text + "[/string]\"";
    }

    public static String url(String url) {
        if (url != null)
            return "[url]" + url + "[/url]";
        return "";
    }

    public static String locator(String xpath) {
        if (xpath != null)
            return "[locator]" + xpath + "[/locator]";
        return "";
    }

    public static String screenshot(String url) {
        if (url != null)
            return "[screenshot]" + url + "[/screenshot]";
        return "";
    }

    public static String textarea(String text) {
        return "[textarea]" + text + "[/textarea]";
    }

    public static String tip(String text) {
        return "[tip]" + text + "[/tip]";
    }
    
    public static String number(Number number) {
        return "[number]"+number.toString()+"[/number]";
    }
    
    public static String variableValue(String value) {
        if(value==null){
            return nullValue();
        }
        else return string(value);
    }
    
    public static String variableValue(Number value) {
        if(value==null){
            return nullValue();
        }
        else return number(value);
    }
    
    public static String listValues(Object...objects) {
        String result = "[list]";
        for(Object object : objects) {
            result+="[item]"+ReportDesign.variableValue(object.toString())+"[/item]";
        }
        result+= "[/list]";
        return result;
    }
    
    public static String listValues(Collection<Object> objects) {
        String result = "[list]";
        for(Object object : objects) {
            result+="[item]"+ReportDesign.variableValue(object.toString())+"[/item]";
        }
        result+= "[/list]";
        return result;
    }
    
    /**
     * Removes all decoration tags from the specified text
     * 
     * @param text
     * @return text without decoration tags
     */
    public static String removeDecorationTags(String text) {
        if (text != null) {
            text = removeTag("b", text);
            text = removeTag("code", text);
            text = removeTag("stack-trace", text);
            text = removeTag("chart", text);
            text = text.replace("[br/]", "");
            text = text.replace("[br]", "");
            text = text.replace("[null-value/]", "null");
            text = removeTag("string", text);
            text = removeTag("url", text);
            text = removeTag("xpath", text);
            text = text.replaceAll("\\[screenshot\\].*/report/screenshot(.*?)\\[/screenshot\\]", "");
            text = removeTag("textarea", text);

        }
        return text;
    }

    private static String removeTag(String tagName, String text) {
        if (text != null) {
            text = text.replace("[" + tagName + "]", "");
            text = text.replace("[/" + tagName + "]", "");
        }
        return text;
    }
}
