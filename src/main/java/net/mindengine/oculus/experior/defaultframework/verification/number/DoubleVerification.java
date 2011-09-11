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
package net.mindengine.oculus.experior.defaultframework.verification.number;

import net.mindengine.oculus.experior.defaultframework.verification.OculusVerificator;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class DoubleVerification extends OculusVerificator {
    private DoubleVerifyable doubleVerifyable;

    public DoubleVerification(Report report, String entityName, DoubleVerifyable doubleVerifyable) {
        super(report, entityName);
        this.doubleVerifyable = doubleVerifyable;
    }

    public boolean is(Double expectedValue) {
        Double realValue = getRealValue();
        if (realValue.equals(expectedValue)) {
            getReport().info("The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " as expected", ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error("The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but " + ReportDesign.string(expectedValue.toString()) + " is expected",
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isNot(Double expectedValue) {
        Double realValue = getRealValue();
        if (!realValue.equals(expectedValue)) {
            getReport().info("The value of " + getEntityName() + " is not " + ReportDesign.string(realValue.toString()) + " as expected", ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error("The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it is not expected", ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isGreaterThan(Double expectedValue) {
        Double realValue = getRealValue();
        if (realValue > expectedValue) {
            getReport().info(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " and is greater than " + ReportDesign.string(expectedValue.toString()) + " as expected",
                    ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it should be greater than " + ReportDesign.string(expectedValue.toString()),
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isGreaterEqualsThan(Double expectedValue) {
        Double realValue = getRealValue();
        if (realValue > expectedValue) {
            getReport().info(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " and is greater than " + ReportDesign.string(expectedValue.toString())
                            + " or equals as expected", ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it should be greater than "
                            + ReportDesign.string(expectedValue.toString() + " or equals"), ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isLessThan(Double expectedValue) {
        Double realValue = getRealValue();
        if (realValue < expectedValue) {
            getReport().info(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " and is less than " + ReportDesign.string(expectedValue.toString()) + " as expected",
                    ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error("The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it should be less than " + ReportDesign.string(expectedValue.toString()),
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isLessEqualsThan(Double expectedValue) {
        Double realValue = getRealValue();
        if (realValue <= expectedValue) {
            getReport().info(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " and is less than " + ReportDesign.string(expectedValue.toString())
                            + " or equals as expected", ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it should be less than "
                            + ReportDesign.string(expectedValue.toString() + " or equals"), ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isInRange(Double range1, Double range2) {
        Double realValue = getRealValue();
        if (realValue >= range1 && realValue <= range2) {
            getReport().info(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " and is in range [" + ReportDesign.string(range1.toString()) + ","
                            + ReportDesign.string(range2.toString()) + "] as expected", ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error(
                    "The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it should be in range [" + ReportDesign.string(range1.toString()) + ","
                            + ReportDesign.string(range2.toString()) + "]", ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public boolean isOneOf(Double... values) {
        Double realValue = getRealValue();
        if (values == null || values.length == 0)
            throw new IllegalArgumentException("The expected values array is not specified");

        String details = "Expected values:" + ReportDesign.breakline();

        boolean passed = false;
        boolean comma = false;
        for (Double value : values) {
            if (comma)
                details += ", ";
            details += ReportDesign.string(value.toString());
            comma = true;
            if (realValue.equals(value)) {
                passed = true;
            }
        }

        if (passed) {
            getReport().info("The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " and is in expected range", details, ReportLogo.VALIDATION_PASSED);
            return true;
        } else {
            getReport().error("The value of " + getEntityName() + " is " + ReportDesign.string(realValue.toString()) + " but it is not in expected range", details, ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    public Double getRealValue() {
        if (doubleVerifyable == null)
            throw new NullPointerException("LongVerifyable wasn't set properly");
        return doubleVerifyable.getValueForValidation();
    }

    public void setNumberVerifyable(DoubleVerifyable doubleVerifyable) {
        this.doubleVerifyable = doubleVerifyable;
    }

    public DoubleVerifyable getNumberVerifyable() {
        return doubleVerifyable;
    }
}
