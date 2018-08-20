/**
 * Copyright (C) 2018 Bonitasoft S.A.
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
package org.bonitasoft.studio.application.views.provider;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.studio.common.repository.RepositoryAccessor;
import org.bonitasoft.studio.common.repository.model.IRepositoryFileStore;
import org.eclipse.core.internal.resources.File;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.jdt.ui.actions.OpenAction;
import org.eclipse.jdt.ui.actions.OpenEditorActionGroup;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionConstants;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonViewerWorkbenchSite;

@SuppressWarnings("restriction")
public class CustomOpenActionProvider extends CommonActionProvider {

    private OpenEditorActionGroup openFileAction;
    private ICommonViewerWorkbenchSite viewSite = null;
    private boolean contribute = false;
    private ECommandService eCommandService;
    private EHandlerService eHandlerService;
    private RepositoryAccessor repositoryAccessor;

    @Override
    public void init(ICommonActionExtensionSite aConfig) {
        if (aConfig.getViewSite() instanceof ICommonViewerWorkbenchSite) {
            viewSite = (ICommonViewerWorkbenchSite) aConfig.getViewSite();
            openFileAction = new OpenEditorActionGroup((IViewPart) viewSite.getPart());
            contribute = true;
            eCommandService = viewSite.getSite().getService(ECommandService.class);
            eHandlerService = viewSite.getSite().getService(EHandlerService.class);
        }
        repositoryAccessor = new RepositoryAccessor();
        repositoryAccessor.init();
    }

    @Override
    public void fillContextMenu(IMenuManager aMenu) {

    }

    @Override
    public void fillActionBars(IActionBars theActionBars) {
        if (!contribute) {
            return;
        }
        openFileAction.setContext(getContext());

        OpenAction customOpenAction = createCustomOpenAction();

        if (openFileAction.getOpenAction().isEnabled()) {
            theActionBars.setGlobalActionHandler(ICommonActionConstants.OPEN,
                    customOpenAction);
        }
    }

    /**
     * Overwrite the default behavior for some files, in order to be able to open our 'wizard editors'
     */
    private OpenAction createCustomOpenAction() {
        return new OpenAction(((IViewPart) viewSite.getPart()).getSite()) {

            @Override
            public void run(Object[] elements) {
                List<Object> elementsToKeep = new ArrayList<>();

                for (Object element : elements) {
                    if (element instanceof File) {
                        IRepositoryFileStore fileStore = repositoryAccessor.getCurrentRepository()
                                .getFileStore((File) element);
                        if (fileStore != null) {
                            fileStore.open();
                            continue;
                        }
                    }
                    elementsToKeep.add(element);
                }

                ((OpenAction) openFileAction.getOpenAction()).run(elementsToKeep.toArray());
            }
        };
    }

}
