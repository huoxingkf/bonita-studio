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
package org.bonitasoft.studio.businessobject.editor.editor.ui.contribution;

import org.bonitasoft.studio.businessobject.BusinessObjectPlugin;
import org.bonitasoft.studio.businessobject.editor.model.BusinessObjectModel;
import org.bonitasoft.studio.businessobject.i18n.Messages;
import org.bonitasoft.studio.ui.editors.xmlEditors.AbstractFormPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class CleanDeployContributionItem extends DeployContributionItem {

    public CleanDeployContributionItem(AbstractFormPage<BusinessObjectModel> formPage) {
        super(formPage);
    }

    @Override
    public void fill(ToolBar parent, int index) {
        item = new ToolItem(parent, SWT.PUSH);
        item.setImage(BusinessObjectPlugin.getImage("icons/cleanDeploy.png"));
        item.setText(Messages.cleanDeployTitle);
        item.setToolTipText(Messages.cleanDeployTitle);
        item.addListener(SWT.Selection, event -> deploy(true));
        item.setEnabled(isEnabled());
    }

}