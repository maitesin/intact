/*
Copyright (c) 2002-2004 The European Bioinformatics Institute, and others.
All rights reserved. Please see the file LICENSE 
in the root directory of this distribution.
*/
package uk.ac.ebi.intact.application.editor.struts.view.feature.test;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.struts.action.ActionErrors;
import uk.ac.ebi.intact.application.editor.business.EditUser;
import uk.ac.ebi.intact.application.editor.business.EditUserI;
import uk.ac.ebi.intact.application.editor.exception.SearchException;
import uk.ac.ebi.intact.application.editor.struts.view.AbstractEditKeyBean;
import uk.ac.ebi.intact.application.editor.struts.view.feature.RangeBean;
import uk.ac.ebi.intact.business.IntactException;
import uk.ac.ebi.intact.model.CvFuzzyType;

/**
 * The test class for RangeBean class.
 *
 * @author Sugath Mudali (smudali@ebi.ac.uk)
 * @version $Id$
 */
public class RangeBeanTest extends TestCase {

    /**
     * Constructs a RangeBeanTest instance with the specified name.
     * @param name the name of the test.
     */
    public RangeBeanTest(String name) {
        super(name);
    }

    /**
     * Sets up the test fixture. Called before every test case method.
     */
    protected void setUp() {
        // Write setting up code for each test.
    }

    /**
     * Tears down the test fixture. Called after every test case method.
     */
    protected void tearDown() {
        // Release resources for after running a test.
    }

    /**
     * Returns this test suite. Reflection is used here to add all
     * the testXXX() methods to the suite.
     */
    public static Test suite() {
        return new TestSuite(RangeBeanTest.class);
    }

    /**
     * Tests the constructor.
     */
    public void testConstructor1() {
        EditUserI user = null;
        try {
            user = new EditUser();
            doTestConstructor1(user);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
        finally {
            if (user != null) {
                try {
                    user.logoff();
                }
                catch (IntactException e) {}
            }
        }
    }

    /**
     * Tests validate method.
     */
    public void testValidate() {
        RangeBean bean = new RangeBean();

        // Collects errors.
        ActionErrors errors;

        // TEST: Empty values for from and to range (likely if user hasn't entered
        // anything).
        bean.setFromRange("");
        bean.setToRange("");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Must be from range.
        assertTrue(errors.get("new.fromRange").hasNext());
        // Not from to range.
        assertFalse(errors.get("new.toRange").hasNext());

        // TEST: Empty value for to range
        bean.setFromRange("2");
        assertTrue(bean.getFromRange().length() != 0);
        assertTrue(bean.getToRange().length() == 0);
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Not from 'from' range.
        assertFalse(errors.get("new.fromRange").hasNext());
        // From to range.
        assertTrue(errors.get("new.toRange").hasNext());

        // TEST: Non numeric values for both from and to ranges.
        bean.setFromRange("a");
        bean.setToRange("b");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Must be from range.
        assertTrue(errors.get("new.fromRange").hasNext());
        // Not from to range.
        assertFalse(errors.get("new.toRange").hasNext());

        // TEST: Non numeric values for to range.
        bean.setFromRange("2");
        bean.setToRange("b");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Not from 'from' range.
        assertFalse(errors.get("new.fromRange").hasNext());
        // From to range.
        assertTrue(errors.get("new.toRange").hasNext());

        // TEST: Numeric values for from and to ranges.
        bean.setFromRange("2");
        bean.setToRange("2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: from is >
        bean.setFromRange(">2");
        bean.setToRange("2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: from is > but to is non numeric.
        bean.setFromRange(">2");
        bean.setToRange("a");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Not from 'from' range.
        assertFalse(errors.get("new.fromRange").hasNext());
        // From to range.
        assertTrue(errors.get("new.toRange").hasNext());

        // TEST: from is < (valid case)
        bean.setFromRange("<2");
        bean.setToRange("2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: from is < alone.
        bean.setFromRange("<");
        bean.setToRange("2");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Must be from range.
        assertTrue(errors.get("new.fromRange").hasNext());
        // Not from to range.
        assertFalse(errors.get("new.toRange").hasNext());

        // TEST: from is < and to range has a number alone.
        bean.setFromRange("<2");
        bean.setToRange("2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: to is >
        bean.setFromRange("2");
        bean.setToRange(">2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: to is > but from is non numeric.
        bean.setFromRange("a");
        bean.setToRange(">2");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // From 'from' range.
        assertTrue(errors.get("new.fromRange").hasNext());
        // Not from to range.
        assertFalse(errors.get("new.toRange").hasNext());

        // TEST: from is < (valid case)
        bean.setFromRange("2");
        bean.setToRange("<2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: to is < alone.
        bean.setFromRange("2");
        bean.setToRange("<");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Not from 'from' range.
        assertFalse(errors.get("new.fromRange").hasNext());
        // From to range.
        assertTrue(errors.get("new.toRange").hasNext());

        // TEST: to and from have < (valid case)
        bean.setFromRange("<2");
        bean.setToRange("<2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: to and from have > (valid case)
        bean.setFromRange(">2");
        bean.setToRange(">2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // --------------------------------------------------------------------

        // Fuzzy range testing.

        // TEST: from has fuzzy range, to has non numerics.
        bean.setFromRange("1..2");
        bean.setToRange("a");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // Not from 'from' range.
        assertFalse(errors.get("new.fromRange").hasNext());
        // From to range.
        assertTrue(errors.get("new.toRange").hasNext());

        // TEST: to and from have fuzzy ranges (valid case)
        bean.setFromRange("1..2");
        bean.setToRange("2..3");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: test for incorrect 'dots' (one less).
        bean.setFromRange("1.2");
        bean.setToRange("1..2");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // From 'from' range.
        assertTrue(errors.get("new.fromRange").hasNext());
        // Not from to range.
        assertFalse(errors.get("new.toRange").hasNext());

        // TEST: test for incorrect 'dots' (one extra).
        bean.setFromRange("1...2");
        bean.setToRange("1..2");
        errors = bean.validate("new");
        // Should have some errors.
        assertFalse(errors.isEmpty());
        // From 'from' range.
        assertTrue(errors.get("new.fromRange").hasNext());
        // Not from to range.
        assertFalse(errors.get("new.toRange").hasNext());

        // TEST: from fuzzy to is not fuzzy (valid case).
        bean.setFromRange("1..2");
        bean.setToRange("2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: from fuzzy to is not fuzzy (valid case).
        bean.setFromRange("1..2");
        bean.setToRange(">2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: to is fuzzy from is not fuzzy (valid case).
        bean.setFromRange("1");
        bean.setToRange("1..2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // TEST: to is fuzzy from is not fuzzy (valid case).
        bean.setFromRange(">1");
        bean.setToRange("1..2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // --------------------------------------------------------------------

        // Valid cases.

        // Negative numbers.
        bean.setFromRange("-3");
        bean.setToRange("-2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // Negative numbers and fuzzy types.
        bean.setFromRange("-1");
        bean.setToRange("1..2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // Negative fuzzy values.
        bean.setFromRange("-4..-3");
        bean.setToRange("-2..2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // Negative fuzzy values.
        bean.setFromRange("-3..-1");
        bean.setToRange("1..2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);

        // Negative fuzzy values.
        bean.setFromRange("-4..-3");
        bean.setToRange("-3..-2");
        errors = bean.validate("new");
        // Shouldn't have errors.
        assertNull(errors);
    }

    /**
     * Tests validate method.
     */
    public void testClone() {
        RangeBean bean = new RangeBean();
        bean.setFromRange("2");
        bean.setToRange("5");

        // The copy.
        RangeBean copy = null;
        try {
            copy = (RangeBean) bean.clone();
        }
        catch (CloneNotSupportedException cnse) {
            assertTrue(true);
            return;
        }

        assertEquals(copy.getFromRange(), bean.getFromRange());
        assertEquals(copy.getToRange(), bean.getToRange());

        // Check the super class's attributes
        assertEquals(copy.getEditState(), bean.getEditState());
        assertEquals(copy.getKey(), bean.getKey());

        // Save the original's state.
        String fromRange = bean.getFromRange();
        String toRange = bean.getToRange();
        String editState = bean.getEditState();

        // Change copy.
        copy.setFromRange("5");
        copy.setEditState(AbstractEditKeyBean.SAVE);

        // Verify that values are set.
        assertEquals(copy.getFromRange(), "5");
        assertEquals(copy.getEditState(), AbstractEditKeyBean.SAVE);

        // The orginal shouldn't be affected.
        assertEquals(bean.getFromRange(), fromRange);
        assertEquals(bean.getToRange(), toRange);
        assertEquals(bean.getEditState(), editState);

        // From ranges do not match.
        assertFalse(copy.getFromRange().equals(bean.getFromRange()));
        // Edit state doesn't match.
        assertFalse(copy.getEditState().equals(bean.getEditState()));
        // to range match.
        assertTrue(copy.getToRange().equals(bean.getToRange()));
    }

    public void testToString() {
        RangeBean bean = new RangeBean();
        // Negative numbers.
        bean.setFromRange("-3");
        bean.setToRange("-2");
        assertEquals("-3--2", bean.toString());

        // Negative numbers and fuzzy types.
        bean.setFromRange("-1");
        bean.setToRange("1..2");
        assertEquals("-1-1..2", bean.toString());

        // Negative fuzzy values.
        bean.setFromRange("-4..-3");
        bean.setToRange("-2..2");
        assertEquals("-4..-3--2..2", bean.toString());

        // Negative fuzzy values.
        bean.setFromRange("-3..-1");
        bean.setToRange("1..2");
        assertEquals("-3..-1-1..2", bean.toString());

        bean.setFromRange("1..2");
        bean.setToRange("2..3");
        assertEquals("1..2-2..3", bean.toString());
    }

    /**
     * Tests the getRange(user).
     */
    public void testGetRange() {
        EditUserI user = null;
        try {
            user = new EditUser();
            doTestGetRange(user);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            fail(ex.getMessage());
        }
        finally {
            if (user != null) {
                try {
                    user.logoff();
                }
                catch (IntactException e) {}
            }
        }
    }

    // Helper methods

    private void doTestConstructor1(EditUserI user) throws SearchException {
        CvFuzzyType lessThan = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.LESS_THAN);
        CvFuzzyType greaterThan = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.GREATER_THAN);
        CvFuzzyType undetermined = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.UNDETERMINED);
        CvFuzzyType range = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.RANGE);
        CvFuzzyType ct = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.C_TERMINAL);
        CvFuzzyType nt = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.N_TERMINAL);

        RangeBean bean = null;

        // No fuzzy type
        bean = new RangeBean(user, "2", "3", false);
        assertEquals(bean.toString(), "2-3");
        // No fuzzy type associated.
        assertNull(bean.getRange().getFromCvFuzzyType());
        assertNull(bean.getRange().getToCvFuzzyType());

        // < fuzzy type for from range
        bean = new RangeBean(user, "<2", "3", false);
        assertEquals(bean.toString(), "<2-3");
        // Fuzzy type is greater for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), lessThan);
        // Fuzzy type is null for to type.
        assertNull(bean.getRange().getToCvFuzzyType());

        // > fuzzy type for from range
        bean = new RangeBean(user, ">2", "3", false);
        assertEquals(bean.toString(), ">2-3");
        // Fuzzy type is greater for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), greaterThan);
        // Fuzzy type is null for to type.
        assertNull(bean.getRange().getToCvFuzzyType());

        // < for both ranges
        bean = new RangeBean(user, "<2", "<3", false);
        assertEquals(bean.toString(), "<2-<3");
        // Fuzzy type is greater for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), lessThan);
        // Fuzzy type is greater for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), lessThan);

        // > for both ranges
        bean = new RangeBean(user, ">2", ">3", false);
        assertEquals(bean.toString(), ">2->3");
        // Fuzzy type is greater for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), greaterThan);
        // Fuzzy type is greater for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), greaterThan);

        // < for both ranges
        bean = new RangeBean(user, "<2", "<3", false);
        assertEquals(bean.toString(), "<2-<3");
        // Fuzzy type is greater for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), lessThan);
        // Fuzzy type is greater for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), lessThan);

        // > for from and < for to ranges
        bean = new RangeBean(user, ">2", "<3", false);
        assertEquals(bean.toString(), ">2-<3");
        // Fuzzy type is greater for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), greaterThan);
        // Fuzzy type is greater for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), lessThan);

        // fuzzy type is undetermined
        bean = new RangeBean(user, "?", "?", false);
        assertEquals(bean.toString(), "?-?");
        // Fuzzy type is undetermined for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), undetermined);
        // Fuzzy type is undetermined for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), undetermined);
        // Range is undetermined type.
        assertTrue(bean.getRange().isUndetermined());

        // One fuzzy type is undetermined
        bean = new RangeBean(user, "?", "3", true);
        assertEquals(bean.toString(), "?-3");
        // Fuzzy type is undetermined for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), undetermined);
        // Fuzzy type is unknown for to type.
        assertNull(bean.getRange().getToCvFuzzyType());
        // Range is NOT undetermined type.
        assertFalse(bean.getRange().isUndetermined());
        // Link is true.
        assertTrue(bean.getRange().isLinked());

        // from fuzzy type is range
        bean = new RangeBean(user, "1..2", "2", false);
        assertEquals(bean.toString(), "1..2-2");
        // Fuzzy type is range for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), range);
        // Fuzzy type is unknown for to type.
        assertNull(bean.getRange().getToCvFuzzyType());

        // to fuzzy type is range
        bean = new RangeBean(user, "1", "2..3", false);
        assertEquals(bean.toString(), "1-2..3");
        // Fuzzy type is unknown for from type.
        assertNull(bean.getRange().getFromCvFuzzyType());
        // Fuzzy type is range for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), range);

        // from and to fuzzy types are ranges
        bean = new RangeBean(user, "1..2", "2..3", false);
        assertEquals(bean.toString(), "1..2-2..3");
        // Fuzzy type is range for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), range);
        // Fuzzy type is range for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), range);

        // c for for range
        bean = new RangeBean(user, "c", "3", false);
        assertEquals(bean.toString(), "c-3");
        // Fuzzy type is c-terminal for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), ct);
        // Fuzzy type is null for to type.
        assertNull(bean.getRange().getToCvFuzzyType());

        // n for for range
        bean = new RangeBean(user, "n", "n", false);
        assertEquals(bean.toString(), "n-n");
        // Fuzzy type is n-terminal for from type.
        assertEquals(bean.getRange().getFromCvFuzzyType(), nt);
        // Fuzzy type is n-terminal for to type.
        assertEquals(bean.getRange().getToCvFuzzyType(), nt);
    }

    private void doTestGetRange(EditUserI user) throws SearchException {
        CvFuzzyType lessThan = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.LESS_THAN);
        CvFuzzyType greaterThan = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.GREATER_THAN);
        CvFuzzyType undetermined = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.UNDETERMINED);
        CvFuzzyType range = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.RANGE);
        CvFuzzyType ct = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.C_TERMINAL);
        CvFuzzyType nt = (CvFuzzyType) user.getObjectByLabel(CvFuzzyType.class,
                CvFuzzyType.N_TERMINAL);

        RangeBean bean = null;

        // from and to fuzzy types are ranges
        bean = new RangeBean(user, "1..2", "2..3", false);
        assertEquals(bean.toString(), "1..2-2..3");
        // Fuzzy type is range for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), range);
        // Fuzzy type is range for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), range);
        // Change it to non fuzzy type.
        bean.setFromRange("2");
        // No fuzzy type associated with the from range
        assertNull(bean.getRange(user).getFromCvFuzzyType());
        // Fuzzy type is range for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), range);

        // from and to fuzzy types are ranges
        bean = new RangeBean(user, "1..2", "2..3", false);
        assertEquals(bean.toString(), "1..2-2..3");
        // Fuzzy type is range for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), range);
        // Fuzzy type is range for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), range);
        // Change both to undetermined.
        bean.setFromRange("?");
        bean.setToRange("?");
        // Both have undetermined types.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), undetermined);
        assertEquals(bean.getRange(user).getToCvFuzzyType(), undetermined);

        // from and to fuzzy types are ranges
        bean = new RangeBean(user, "1..2", "2..3", false);
        assertEquals(bean.toString(), "1..2-2..3");
        // Fuzzy type is range for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), range);
        // Fuzzy type is range for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), range);
        // Change to < and from to >.
        bean.setFromRange("<1");
        bean.setToRange(">2");
        // To have less than type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), lessThan);
        // From have greater than type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), greaterThan);

        // n for for range
        bean = new RangeBean(user, "n", "n", false);
        assertEquals(bean.toString(), "n-n");
        // Fuzzy type is n-terminal for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), nt);
        // Fuzzy type is n-terminal for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), nt);
        // Change the to range.
        bean.setToRange("c");
        assertEquals(bean.toString(), "n-c");
        // Fuzzy type is n-terminal for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), nt);
        // Fuzzy type is c-terminal for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), ct);

        // n for for range
        bean = new RangeBean(user, "n", "n", false);
        assertEquals(bean.toString(), "n-n");
        // Fuzzy type is n-terminal for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), nt);
        // Fuzzy type is n-terminal for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), nt);
        // Change both to c terminals.
        bean.setFromRange("c");
        bean.setToRange("c");
        assertEquals(bean.toString(), "c-c");
        // Fuzzy type are c-terminals for both types.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), ct);
        assertEquals(bean.getRange(user).getToCvFuzzyType(), ct);

        // fuzzy type is undetermined
        bean = new RangeBean(user, "?", "?", false);
        assertEquals(bean.toString(), "?-?");
        // Fuzzy type is undetermined for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), undetermined);
        // Fuzzy type is undetermined for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), undetermined);
        // Range is undetermined type.
        assertTrue(bean.getRange(user).isUndetermined());
        // Change the to range.
        bean.setToRange("c");
        assertEquals(bean.toString(), "?-c");
        // Fuzzy type is undetermined for from type.
        assertEquals(bean.getRange(user).getFromCvFuzzyType(), undetermined);
        // Fuzzy type is c-terminal for to type.
        assertEquals(bean.getRange(user).getToCvFuzzyType(), ct);
        // Range is no longer undetermined type.
        assertFalse(bean.getRange(user).isUndetermined());
        // Link is still false.
        assertFalse(bean.getRange(user).isLinked());
    }
}
