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
package org.bonitasoft.studio.application.handler;

import static org.bonitasoft.studio.ui.wizard.WizardBuilder.newWizard;
import static org.bonitasoft.studio.ui.wizard.WizardPageBuilder.newPage;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.inject.Named;

import org.bonitasoft.studio.application.i18n.Messages;
import org.bonitasoft.studio.application.operation.DeployProjectOperation;
import org.bonitasoft.studio.application.operation.ValidateProjectOperation;
import org.bonitasoft.studio.application.ui.control.SelectArtifactToDeployPage;
import org.bonitasoft.studio.businessobject.core.operation.GenerateBDMOperation;
import org.bonitasoft.studio.businessobject.core.repository.BusinessObjectModelFileStore;
import org.bonitasoft.studio.businessobject.core.repository.BusinessObjectModelRepositoryStore;
import org.bonitasoft.studio.common.log.BonitaStudioLog;
import org.bonitasoft.studio.common.repository.RepositoryAccessor;
import org.bonitasoft.studio.common.repository.model.IRepositoryFileStore;
import org.bonitasoft.studio.importer.ui.dialog.SkippableProgressMonitorJobsDialog;
import org.bonitasoft.studio.ui.dialog.MultiStatusDialog;
import org.bonitasoft.studio.ui.wizard.WizardBuilder;
import org.eclipse.core.databinding.validation.ValidationStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DeployArtifactsHandler {

    @Execute
    public void deploy(@Named(IServiceConstants.ACTIVE_SHELL) Shell activeShell,
            RepositoryAccessor repositoryAccessor)
            throws InvocationTargetException, InterruptedException {
        Collection<IRepositoryFileStore> artifactsToDeploy = retrieveArtifactsToDeploy(activeShell, repositoryAccessor);
        if (!artifactsToDeploy.isEmpty()) {
            deployArtifacts(activeShell, repositoryAccessor, artifactsToDeploy);
        }
    }

    protected Collection<IRepositoryFileStore> retrieveArtifactsToDeploy(Shell activeShell,
            RepositoryAccessor repositoryAccessor) {
        SelectArtifactToDeployPage page = new SelectArtifactToDeployPage(repositoryAccessor);
        Optional<Collection<IRepositoryFileStore>> userChoices = createWizard(newWizard(), page,
                Messages.selectArtifactToDeployTitle, Messages.selectArtifactToDeploy)
                        .open(activeShell, Messages.deploy);
        return userChoices.orElse(new ArrayList<IRepositoryFileStore>());
    }

    protected void deployArtifacts(Shell activeShell, RepositoryAccessor repositoryAccessor,
            Collection<IRepositoryFileStore> artifactsToDeploy) throws InvocationTargetException, InterruptedException {
        if (artifactsToDeploy.stream().anyMatch(BusinessObjectModelFileStore.class::isInstance)) {
            IStatus generateBdmSatus = performBdmGeneration(repositoryAccessor);
            if (!generateBdmSatus.isOK()) {
                manageError(activeShell, generateBdmSatus, Messages.deployErrorTitle,
                        Optional.of(Messages.bdmGenerationError));
                return;
            }
        }
        IStatus validationStatus = performArtifactsValidation(activeShell, artifactsToDeploy);
        if (!validationStatus.isOK()) {
            manageError(activeShell, validationStatus, Messages.validationErrorTitle,
                    Optional.of(Messages.validationError));
            return;
        }
        IStatus deployStatus = performDeploy(repositoryAccessor, artifactsToDeploy);
        if (!deployStatus.isOK()) {
            manageError(activeShell, deployStatus, Messages.deployErrorTitle, Optional.empty());
        }
    }

    private WizardBuilder<Collection<IRepositoryFileStore>> createWizard(
            WizardBuilder<Collection<IRepositoryFileStore>> builder,
            SelectArtifactToDeployPage page,
            String title,
            String description) {
        return builder
                .withTitle(title)
                .withSize(1000, 700)
                .havingPage(newPage()
                        .withTitle(title)
                        .withDescription(description)
                        .withControl(page))
                .onFinish(container -> Optional.of(page.getElements()));

    }

    private void manageError(Shell activeShell, IStatus status, String title, Optional<String> message) {
        if (status instanceof MultiStatus) {
            new MultiStatusDialog(activeShell, title, message.orElse(""),
                    new String[] { IDialogConstants.OK_LABEL }, (MultiStatus) status).open();
        } else {
            MessageDialog.openError(Display.getDefault().getActiveShell(), title,
                    String.format("%s\n\n%s", message.orElse(""), status.getMessage()));
            BonitaStudioLog.error(status.getMessage(), status.getException());
        }
    }

    @SuppressWarnings("unchecked")
    private IStatus performBdmGeneration(RepositoryAccessor repositoryAccessor) {
        BusinessObjectModelFileStore bdmFileStore = (BusinessObjectModelFileStore) repositoryAccessor
                .getRepositoryStore(BusinessObjectModelRepositoryStore.class)
                .getChild(BusinessObjectModelFileStore.BOM_FILENAME);
        if (bdmFileStore != null) {
            try {
                PlatformUI.getWorkbench().getProgressService().run(true, false, new GenerateBDMOperation(bdmFileStore));
            } catch (InvocationTargetException | InterruptedException e) {
                return ValidationStatus.error(Messages.bdmGenerationError, e);
            }
        }
        return ValidationStatus.ok();
    }

    private IStatus performDeploy(RepositoryAccessor repositoryAccessor, Collection<IRepositoryFileStore> artifactsToDeploy)
            throws InterruptedException, InvocationTargetException {
        DeployProjectOperation operation = new DeployProjectOperation(repositoryAccessor, artifactsToDeploy);
        PlatformUI.getWorkbench().getProgressService().run(true, false, operation);
        return operation.getStatus();
    }

    private IStatus performArtifactsValidation(Shell activeShell, Collection<IRepositoryFileStore> artifactsToDeploy)
            throws InvocationTargetException, InterruptedException {
        SkippableProgressMonitorJobsDialog dialog = new SkippableProgressMonitorJobsDialog(activeShell);
        dialog.canBeSkipped();
        ValidateProjectOperation operation = new ValidateProjectOperation(artifactsToDeploy);
        dialog.run(true, false, operation);
        return operation.getStatus();
    }

}
