/**
 * Copyright (C) 2019 Bonitasoft S.A.
 * Bonitasoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.studio.businessobject.editor.editor.ui.control.businessObject;

import org.bonitasoft.studio.businessobject.editor.editor.ui.formpage.AbstractBdmFormPage;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;

public class ReadOnlyBusinessObjectList extends BusinessObjectList {

    public ReadOnlyBusinessObjectList(Composite parent, AbstractBdmFormPage formPage, DataBindingContext ctx) {
        super(parent, formPage, ctx);
    }

    @Override
    protected void createAddDeleteItems(AbstractBdmFormPage formPage, ToolBar toolBar) {
        // Nothing
    }

    @Override
    protected void enableButtons() {
        // Nothing
    }

    @Override
    protected void addEditingSupport(TreeViewer viewer, AbstractBdmFormPage formPage, TreeViewerColumn column) {
        // Nothing
    }

    @Override
    protected void addDNDSupport(AbstractBdmFormPage formPage) {
        // Nothing
    }

}
