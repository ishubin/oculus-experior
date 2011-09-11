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
package net.mindengine.oculus.experior;

import net.mindengine.oculus.experior.test.TestRunner;
import net.mindengine.oculus.experior.test.descriptors.ActionInformation;
import net.mindengine.oculus.experior.test.descriptors.TestInformation;

/**
 * Listener for all basic events of {@link TestRunner}. Used by 3d-party
 * libraries which are using the test-run-framework for obtaining the
 * information about each test run in {@link TestRunner}.
 * 
 * @author Ivan Shubin
 * 
 */
public interface TestRunListener {
    public void onTestStarted(TestInformation testInformation);

    public void onTestAction(ActionInformation actionInformation);

    public void onTestFinished(TestInformation testInformation);
}
