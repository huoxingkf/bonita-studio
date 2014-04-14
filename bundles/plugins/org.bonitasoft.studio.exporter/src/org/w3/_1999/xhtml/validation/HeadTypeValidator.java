/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.w3._1999.xhtml.validation;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.w3._1999.xhtml.BaseType;
import org.w3._1999.xhtml.DirType;
import org.w3._1999.xhtml.LinkType;
import org.w3._1999.xhtml.MetaType;
import org.w3._1999.xhtml.ObjectType;
import org.w3._1999.xhtml.ScriptType;
import org.w3._1999.xhtml.StyleType;
import org.w3._1999.xhtml.TitleType;

/**
 * A sample validator interface for {@link org.w3._1999.xhtml.HeadType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface HeadTypeValidator {
	boolean validate();

	boolean validateGroup(FeatureMap value);
	boolean validateScript(EList<ScriptType> value);
	boolean validateStyle(EList<StyleType> value);
	boolean validateMeta(EList<MetaType> value);
	boolean validateLink(EList<LinkType> value);
	boolean validateObject(EList<ObjectType> value);
	boolean validateTitle(TitleType value);
	boolean validateGroup1(FeatureMap value);
	boolean validateScript1(EList<ScriptType> value);
	boolean validateStyle1(EList<StyleType> value);
	boolean validateMeta1(EList<MetaType> value);
	boolean validateLink1(EList<LinkType> value);
	boolean validateObject1(EList<ObjectType> value);
	boolean validateBase(BaseType value);
	boolean validateGroup2(FeatureMap value);
	boolean validateScript2(EList<ScriptType> value);
	boolean validateStyle2(EList<StyleType> value);
	boolean validateMeta2(EList<MetaType> value);
	boolean validateLink2(EList<LinkType> value);
	boolean validateObject2(EList<ObjectType> value);
	boolean validateBase1(BaseType value);
	boolean validateGroup3(FeatureMap value);
	boolean validateScript3(EList<ScriptType> value);
	boolean validateStyle3(EList<StyleType> value);
	boolean validateMeta3(EList<MetaType> value);
	boolean validateLink3(EList<LinkType> value);
	boolean validateObject3(EList<ObjectType> value);
	boolean validateTitle1(TitleType value);
	boolean validateGroup4(FeatureMap value);
	boolean validateScript4(EList<ScriptType> value);
	boolean validateStyle4(EList<StyleType> value);
	boolean validateMeta4(EList<MetaType> value);
	boolean validateLink4(EList<LinkType> value);
	boolean validateObject4(EList<ObjectType> value);
	boolean validateDir(DirType value);
	boolean validateId(String value);
	boolean validateLang(String value);
	boolean validateLang1(String value);
	boolean validateProfile(String value);
}
