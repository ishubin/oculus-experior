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
package net.mindengine.oculus.experior.defaultframework.verification.text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mindengine.oculus.experior.defaultframework.verification.OculusVerificator;
import net.mindengine.oculus.experior.reporter.Report;
import net.mindengine.oculus.experior.reporter.ReportDesign;
import net.mindengine.oculus.experior.reporter.ReportLogo;

public class TextVerificator extends OculusVerificator {

    private TextVerifyable textVerifyable;

    public TextVerificator(Report report, String entityName, TextVerifyable textVerifyable) {
        super(report, entityName);
        this.setTextVerifyable(textVerifyable);
    }

    /**
     * Checking whether the specified texts are equal
     * 
     * @param expectedText
     *            The expected text for validation
     * @return true in case if the validation passed, false - if failed
     */
    public boolean is(String expectedText) {
        String realText = getRealText();

        if (realText != null) {
            if (realText.equals(expectedText)) {
                getReport().info("The text of " + getEntityName() + " is " + ReportDesign.shortString(realText) + " as expected", getReportDetails(realText, expectedText),
                        ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " is " + ReportDesign.shortString(realText) + " but it is not as expected", getReportDetails(realText, expectedText),
                        ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else {
            if (expectedText == null) {
                getReport().info("The text of " + getEntityName() + " is " + ReportDesign.nullValue() + " as expected", ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue() + " but it is not as expected", getReportDetails(realText, expectedText),
                        ReportLogo.VALIDATION_FAILED);
                return false;
            }
        }
    }

    /**
     * Checks whether the text is equal to any of the provided texts
     * 
     * @param expectedTexts
     *            Array of expected text cases
     * @return true if the text of the specified entity is equal to one of the
     *         specified texts
     */
    public boolean isOneOf(String... expectedTexts) {
        if (expectedTexts == null)
            throw new NullPointerException("Expected text array shouldn't be null");
        String realText = getRealText();
        for (String expected : expectedTexts) {
            if (expected == null)
                throw new NullPointerException("The expected text cannot be null");
            if (expected.equals(realText)) {
                getReport().info("The text of " + getEntityName() + " is as expected", getReportDetails(realText, expectedTexts), ReportLogo.VALIDATION_PASSED);
                return true;
            }
        }
        getReport().info("The text of " + getEntityName() + " is not as expected.", getReportDetails(realText, expectedTexts), ReportLogo.VALIDATION_FAILED);
        return false;
    }

    /**
     * Checks whether the text of the entity is not the same as expected
     * 
     * @param expectedText
     * @return true in case if the real text is not the same as expected, false
     *         - if it is equal to the expected text
     */
    public boolean isNot(String expectedText) {
        String realText = getRealText();

        if (realText != null) {
            if (!realText.equals(expectedText)) {
                getReport().info("The text of " + getEntityName() + " is not " + ReportDesign.shortString(expectedText) + " as expected",
                        getReportDetails("Real text", realText, "Not expected text", expectedText), ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " is " + ReportDesign.shortString(expectedText) + " but it is not expected",
                        getReportDetails("Real text", realText, "Not expected text", expectedText), ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else {
            if (expectedText == null) {
                getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue() + " but it is not as expected", getReportDetails(realText, expectedText),
                        ReportLogo.VALIDATION_FAILED);

                return true;
            } else {
                getReport().info("The text of " + getEntityName() + " is not " + ReportDesign.shortString(expectedText) + " as expected", getReportDetails(realText, expectedText),
                        ReportLogo.VALIDATION_PASSED);
                return false;
            }
        }
    }

    /**
     * Checks whether the text of an entity is not empty and contains at least
     * one symbol
     * 
     * @return
     */
    public boolean isNotEmpty() {
        String realText = getRealText();

        if (realText != null) {
            if (!realText.isEmpty()) {
                getReport().info("The text of " + getEntityName() + " is not empty as expected", ReportDesign.bold("Real text:") + ReportDesign.breakline() + ReportDesign.string(realText),
                        ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " is empty but it is not expected", ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else
            getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue() + " but it is not expected", ReportLogo.VALIDATION_FAILED);
        return false;
    }

    /**
     * Checks whether the text of an entity starts with the specified expected
     * text
     * 
     * @param expectedText
     * @return true in case if the real text starts with the specified text,
     *         false - otherwise
     */
    public boolean startsWith(String expectedText) {
        String realText = getRealText();
        if (realText != null) {
            if (realText.startsWith(expectedText)) {
                getReport().info("The text of " + getEntityName() + " starts with " + ReportDesign.shortString(expectedText) + " as expected",
                        getReportDetails("Real text", realText, "Expected to start with", expectedText), ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " does not start with " + ReportDesign.shortString(expectedText),
                        getReportDetails("Real text", realText, "Expected to start with", expectedText), ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else {
            getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue(), getReportDetails("Real text", realText, "Expected to start with", expectedText),
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    /**
     * Checks whether the text of an entity ends with the specified expected
     * text
     * 
     * @param expectedText
     * @return true in case if the real text ends with the specified text, false
     *         - otherwise
     */
    public boolean endsWith(String expectedText) {
        String realText = getRealText();
        if (realText != null) {
            if (realText.startsWith(expectedText)) {
                getReport().info("The text of " + getEntityName() + " ends with " + ReportDesign.shortString(expectedText) + " as expected",
                        getReportDetails("Real text", realText, "Expected to end with", expectedText), ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " does not end with " + ReportDesign.shortString(expectedText),
                        getReportDetails("Real text", realText, "Expected to end with", expectedText), ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else {
            getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue(), getReportDetails("Real text", realText, "Expected to end with", expectedText),
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    /**
     * Checks whether the text of an entity matches the specified regular
     * expression pattern
     * 
     * @param pattern
     *            Pattern for regular expression
     * @return true in case if real text matches the regular expression with
     *         specified pattern, false - otherwise
     */
    public boolean matches(String pattern) {
        if (pattern == null)
            throw new NullPointerException("The pattern should be null");
        String realText = getRealText();
        if (realText != null) {
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(realText);
            if (m.matches()) {
                getReport().info("The text of " + getEntityName() + " matches the specified pattern", getReportDetails("Real text", realText, "Expected regex pattern", pattern),
                        ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " doesn't match the specified pattern", getReportDetails("Real text", realText, "Expected regex pattern", pattern),
                        ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else {
            getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue(), getReportDetails("Real text", realText, "Expected regex pattern", pattern),
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    /**
     * Checks whether the text of an entity contains the specified expected text
     * 
     * @param expectedText
     * @return true in case if the real text contains the specified text, false
     *         - otherwise
     */
    public boolean contains(String expectedText) {
        String realText = getRealText();
        if (realText != null) {
            if (realText.contains(expectedText)) {
                getReport().info("The text of " + getEntityName() + " contains " + ReportDesign.shortString(expectedText) + " as expected",
                        getReportDetails("Real text", realText, "Should contain", expectedText), ReportLogo.VALIDATION_PASSED);
                return true;
            } else {
                getReport().error("The text of " + getEntityName() + " does not contain " + ReportDesign.shortString(expectedText),
                        getReportDetails("Real text", realText, "Should contain", expectedText), ReportLogo.VALIDATION_FAILED);
                return false;
            }
        } else {
            getReport().error("The text of " + getEntityName() + " is " + ReportDesign.nullValue(), getReportDetails("Real text", realText, "Should contain", expectedText),
                    ReportLogo.VALIDATION_FAILED);
            return false;
        }
    }

    private static String getReportDetails(String realLabel, String realText, String expectedLabel, String expectedText) {

        String strRealText;
        if (realText == null) {
            strRealText = ReportDesign.nullValue();
        } else
            strRealText = ReportDesign.string(realText);

        String strExpectedText;
        if (expectedText == null) {
            strExpectedText = ReportDesign.nullValue();
        } else
            strExpectedText = ReportDesign.string(expectedText);

        return realLabel + ": " + ReportDesign.breakline() + strRealText + ReportDesign.breakline() + ReportDesign.breakline() + expectedLabel + ":" + ReportDesign.breakline() + strExpectedText;
    }

    private static String getReportDetails(String realText, String expectedText) {
        return getReportDetails("Real text", realText, "Expected text", expectedText);
    }

    private static String getReportDetails(String realText, String[] expectedTexts) {
        String str = "Real text: " + ReportDesign.breakline() + ReportDesign.string(realText) + ReportDesign.breakline() + ReportDesign.breakline() + "Expected texts:";

        for (String text : expectedTexts) {
            str += ReportDesign.breakline() + ReportDesign.string(text);
        }
        return str;
    }

    /**
     * Obtaining the real text for validation from the specified entity
     * 
     * @return
     */
    private String getRealText() {
        if (textVerifyable == null)
            throw new NullPointerException("The TextVerifyable property wasn't set");
        String realText = textVerifyable.getTextForValidation();
        return realText;
    }

    public void setTextVerifyable(TextVerifyable textVerifyable) {
        this.textVerifyable = textVerifyable;
    }

    public TextVerifyable getTextVerifyable() {
        return textVerifyable;
    }

}
