/**
 * Copyright (C) 2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2.0 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.bonitasoft.studio.migration.migrator;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.studio.migration.model.report.Change;
import org.bonitasoft.studio.migration.model.report.MigrationReportFactory;
import org.eclipse.emf.edapt.migration.CustomMigration;

/**
 * @author Romain Bioteau
 *
 */
public abstract class ReportCustomMigration extends CustomMigration implements IReportMigration {

	private List<Change> changes = new ArrayList<Change>();
	
	
	public Change addReportChange(String elementUUID,String description,String propertyName, int status){
		Change change = MigrationReportFactory.eINSTANCE.createChange();
		change.setElementUUID(elementUUID);
		change.setDescription(description);
		change.setPropertyName(propertyName);
		changes.add(change);
		return change;
	}
	
	@Override
	public List<Change> getChanges() {
		return changes;
	}
}
