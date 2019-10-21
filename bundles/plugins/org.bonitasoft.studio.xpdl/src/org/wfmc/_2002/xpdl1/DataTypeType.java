/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.wfmc._2002.xpdl1;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Data Type Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getBasicType <em>Basic Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getDeclaredType <em>Declared Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getSchemaType <em>Schema Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getExternalReference <em>External Reference</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getRecordType <em>Record Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getUnionType <em>Union Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getEnumerationType <em>Enumeration Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getArrayType <em>Array Type</em>}</li>
 *   <li>{@link org.wfmc._2002.xpdl1.DataTypeType#getListType <em>List Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType()
 * @model extendedMetaData="name='DataType_._type' kind='elementOnly'"
 * @generated
 */
public interface DataTypeType extends EObject {
	/**
	 * Returns the value of the '<em><b>Basic Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Basic Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Basic Type</em>' containment reference.
	 * @see #setBasicType(BasicTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_BasicType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='BasicType' namespace='##targetNamespace'"
	 * @generated
	 */
	BasicTypeType getBasicType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getBasicType <em>Basic Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Basic Type</em>' containment reference.
	 * @see #getBasicType()
	 * @generated
	 */
	void setBasicType(BasicTypeType value);

	/**
	 * Returns the value of the '<em><b>Declared Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Declared Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Declared Type</em>' containment reference.
	 * @see #setDeclaredType(DeclaredTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_DeclaredType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='DeclaredType' namespace='##targetNamespace'"
	 * @generated
	 */
	DeclaredTypeType getDeclaredType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getDeclaredType <em>Declared Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Declared Type</em>' containment reference.
	 * @see #getDeclaredType()
	 * @generated
	 */
	void setDeclaredType(DeclaredTypeType value);

	/**
	 * Returns the value of the '<em><b>Schema Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Schema Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Schema Type</em>' containment reference.
	 * @see #setSchemaType(SchemaTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_SchemaType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='SchemaType' namespace='##targetNamespace'"
	 * @generated
	 */
	SchemaTypeType getSchemaType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getSchemaType <em>Schema Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Schema Type</em>' containment reference.
	 * @see #getSchemaType()
	 * @generated
	 */
	void setSchemaType(SchemaTypeType value);

	/**
	 * Returns the value of the '<em><b>External Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Reference</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Reference</em>' containment reference.
	 * @see #setExternalReference(ExternalReferenceType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_ExternalReference()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ExternalReference' namespace='##targetNamespace'"
	 * @generated
	 */
	ExternalReferenceType getExternalReference();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getExternalReference <em>External Reference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>External Reference</em>' containment reference.
	 * @see #getExternalReference()
	 * @generated
	 */
	void setExternalReference(ExternalReferenceType value);

	/**
	 * Returns the value of the '<em><b>Record Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Record Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Record Type</em>' containment reference.
	 * @see #setRecordType(RecordTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_RecordType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='RecordType' namespace='##targetNamespace'"
	 * @generated
	 */
	RecordTypeType getRecordType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getRecordType <em>Record Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Record Type</em>' containment reference.
	 * @see #getRecordType()
	 * @generated
	 */
	void setRecordType(RecordTypeType value);

	/**
	 * Returns the value of the '<em><b>Union Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Union Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Union Type</em>' containment reference.
	 * @see #setUnionType(UnionTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_UnionType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='UnionType' namespace='##targetNamespace'"
	 * @generated
	 */
	UnionTypeType getUnionType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getUnionType <em>Union Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Union Type</em>' containment reference.
	 * @see #getUnionType()
	 * @generated
	 */
	void setUnionType(UnionTypeType value);

	/**
	 * Returns the value of the '<em><b>Enumeration Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enumeration Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enumeration Type</em>' containment reference.
	 * @see #setEnumerationType(EnumerationTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_EnumerationType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='EnumerationType' namespace='##targetNamespace'"
	 * @generated
	 */
	EnumerationTypeType getEnumerationType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getEnumerationType <em>Enumeration Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enumeration Type</em>' containment reference.
	 * @see #getEnumerationType()
	 * @generated
	 */
	void setEnumerationType(EnumerationTypeType value);

	/**
	 * Returns the value of the '<em><b>Array Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Array Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Array Type</em>' containment reference.
	 * @see #setArrayType(ArrayTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_ArrayType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ArrayType' namespace='##targetNamespace'"
	 * @generated
	 */
	ArrayTypeType getArrayType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getArrayType <em>Array Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Array Type</em>' containment reference.
	 * @see #getArrayType()
	 * @generated
	 */
	void setArrayType(ArrayTypeType value);

	/**
	 * Returns the value of the '<em><b>List Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>List Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>List Type</em>' containment reference.
	 * @see #setListType(ListTypeType)
	 * @see org.wfmc._2002.xpdl1.Xpdl1Package#getDataTypeType_ListType()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='ListType' namespace='##targetNamespace'"
	 * @generated
	 */
	ListTypeType getListType();

	/**
	 * Sets the value of the '{@link org.wfmc._2002.xpdl1.DataTypeType#getListType <em>List Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>List Type</em>' containment reference.
	 * @see #getListType()
	 * @generated
	 */
	void setListType(ListTypeType value);

} // DataTypeType
