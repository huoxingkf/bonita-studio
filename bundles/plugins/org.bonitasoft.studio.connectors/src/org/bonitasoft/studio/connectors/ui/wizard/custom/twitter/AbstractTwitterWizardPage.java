/**
 * Copyright (C) 2020 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
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
package org.bonitasoft.studio.connectors.ui.wizard.custom.twitter;

import org.bonitasoft.studio.connector.model.definition.wizard.GeneratedConnectorWizardPage;

public abstract class AbstractTwitterWizardPage extends
        GeneratedConnectorWizardPage {

    protected TwitterTools twitterTool;

    public TwitterTools getTwitterTool() {
        return twitterTool;
    }

    public void setTwitterTool(TwitterTools twitterTool) {
        this.twitterTool = twitterTool;
    }
}