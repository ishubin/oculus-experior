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
package net.mindengine.oculus.experior.reporter.nodes;

import net.mindengine.oculus.experior.reporter.ReportDesign;

public class ExceptionReportNode extends ReportNode {
    /**
     * 
     */
    private static final long serialVersionUID = -7964782842091110158L;
    private transient Throwable throwable;

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;

        setText(throwable.getMessage());
        setName(throwable.getClass().getName());
        // Adding stack trace to the details
        StringBuffer str = new StringBuffer();
        StackTraceElement[] stack = throwable.getStackTrace();
        for (int i = 0; i < stack.length; i++) {
            str.append(stack[i]);
            str.append(ReportDesign.breakline());
        }
        DescriptionReportNode detailsNode = new DescriptionReportNode();
        detailsNode.setText(ReportDesign.stackTrace(str.toString()));
        getChildren().add(detailsNode);
    }

    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public String getReason() {
        if (throwable != null) {
            return throwable.getClass().getName() + ": " + throwable.getMessage();
        }
        return super.getReason();
    }
}
