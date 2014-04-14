/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.w3._1999.xhtml.validation;

import java.math.BigInteger;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.w3._1999.xhtml.DirType;
import org.w3._1999.xhtml.DisabledType1;
import org.w3._1999.xhtml.MultipleType;
import org.w3._1999.xhtml.OptgroupType;
import org.w3._1999.xhtml.OptionType;

/**
 * A sample validator interface for {@link org.w3._1999.xhtml.SelectType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface SelectTypeValidator {
	boolean validate();

	boolean validateGroup(FeatureMap value);
	boolean validateOptgroup(EList<OptgroupType> value);
	boolean validateOption(EList<OptionType> value);
	boolean validateClass(String value);

	boolean validateClass(List<String> value);
	boolean validateDir(DirType value);
	boolean validateDisabled(DisabledType1 value);
	boolean validateId(String value);
	boolean validateLang(String value);
	boolean validateLang1(String value);
	boolean validateMultiple(MultipleType value);
	boolean validateName(Object value);
	boolean validateOnblur(String value);
	boolean validateOnchange(String value);
	boolean validateOnclick(String value);
	boolean validateOndblclick(String value);
	boolean validateOnfocus(String value);
	boolean validateOnkeydown(String value);
	boolean validateOnkeypress(String value);
	boolean validateOnkeyup(String value);
	boolean validateOnmousedown(String value);
	boolean validateOnmousemove(String value);
	boolean validateOnmouseout(String value);
	boolean validateOnmouseover(String value);
	boolean validateOnmouseup(String value);
	boolean validateSize(BigInteger value);
	boolean validateStyle(String value);
	boolean validateTabindex(BigInteger value);
	boolean validateTitle(String value);
}
