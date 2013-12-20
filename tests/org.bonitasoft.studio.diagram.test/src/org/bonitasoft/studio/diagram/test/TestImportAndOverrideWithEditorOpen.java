/**
 * Copyright (C) 2010 BonitaSoft S.A.
 * BonitaSoft, 31 rue Gustave Eiffel - 38000 Grenoble
 *
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
package org.bonitasoft.studio.diagram.test;

import org.bonitasoft.studio.common.emf.tools.ModelHelper;
import org.bonitasoft.studio.common.repository.RepositoryManager;
import org.bonitasoft.studio.diagram.custom.repository.DiagramRepositoryStore;
import org.bonitasoft.studio.engine.BOSEngineManager;
import org.bonitasoft.studio.model.process.MainProcess;
import org.bonitasoft.studio.test.swtbot.util.SWTBotTestUtil;
import org.bonitasoft.studio.test.swtbot.util.conditions.EditorOpenCondition;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.swtbot.eclipse.finder.widgets.SWTBotEditor;
import org.eclipse.swtbot.eclipse.gef.finder.SWTBotGefTestCase;
import org.eclipse.swtbot.eclipse.gef.finder.widgets.SWTBotGefEditor;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtbot.swt.finder.waits.Conditions;
import org.eclipse.swtbot.swt.finder.waits.ICondition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * @author Aurelien Pupier
 *
 */
@RunWith(SWTBotJunit4ClassRunner.class)
public class TestImportAndOverrideWithEditorOpen extends SWTBotGefTestCase {

	private DiagramRepositoryStore store = (DiagramRepositoryStore) RepositoryManager.getInstance().getRepositoryStore(DiagramRepositoryStore.class);

	@Before
	public void setUp() throws Exception {
		super.setUp();
		BOSEngineManager.getInstance().start();//wait start engine ended
		bot.saveAllEditors();
		bot.closeAllEditors();
	}


	@After
	public void tearDown() throws Exception {
		bot.saveAllEditors();
		bot.closeAllEditors();
	}

	@Test
	public void testImportAndOverrideWithProcessEditorOpen() throws Exception {

		/* import the base process to override */
		SWTBotTestUtil.importProcessWIthPathFromClass(bot, "ProcWithSameNameAndVersion_1_0.bos", "Bonita 6.x", "ProcWithSameNameAndVersion", getClass(), false);
		bot.waitUntil(new EditorOpenCondition(store.getChild("ProcWithSameNameAndVersion-1.0.proc").getResource()));
		SWTBotEditor botEditor = bot.activeEditor();
		final SWTBotGefEditor gmfEditor = bot.gefEditor(botEditor.getTitle());
		bot.waitUntil(Conditions.shellIsActive("Bonita BPM"));
		
		IGraphicalEditPart part = (IGraphicalEditPart)gmfEditor.mainEditPart().part();
		MainProcess model = (MainProcess)part.resolveSemanticElement();
		assertNotNull("no pool found (import failed?)",model);
		assertContains("ProcWithSameNameAndVersion", botEditor.getTitle());
		assertEquals("Unique description 0", model.getDocumentation());
			
		SWTBotTestUtil.importProcessWIthPathFromClass(bot, "_1ProcWithSameNameAndVersion_1_0.bos", "Bonita 6.x", "ProcWithSameNameAndVersion", getClass(), false);
		bot.waitUntil(new EditorOpenCondition(store.getChild("ProcWithSameNameAndVersion-1.0.proc").getResource()));
		botEditor = bot.activeEditor();
		final SWTBotGefEditor gmfEditor2 = bot.gefEditor(botEditor.getTitle());
		bot.waitUntil(Conditions.shellIsActive("Bonita BPM"));

		part = (IGraphicalEditPart)gmfEditor2.mainEditPart().part();
		model = (MainProcess)ModelHelper.getMainProcess(part.resolveSemanticElement());
		assertNotNull("no pool found (import failed?)",model);
		assertContains("ProcWithSameNameAndVersion", botEditor.getTitle());
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				MainProcess diagram = (MainProcess) ((IGraphicalEditPart)gmfEditor2.mainEditPart().part()).resolveSemanticElement();
				return "Unique description 1".equals(diagram.getDocumentation());
			}

			public void init(SWTBot bot) {

			}

			public String getFailureMessage() {
				MainProcess diagram = (MainProcess) ((IGraphicalEditPart)gmfEditor2.mainEditPart().part()).resolveSemanticElement();
				return "Unique description 1 was expected but was :"+diagram.getDocumentation();
			}
		},10000,500);
	}

	@Test
	public void testImportAndOverrideWithFormEditorOpen() throws Exception {
		SWTBotTestUtil.importProcessWIthPathFromClass(bot, "_1ProcWithSameNameAndVersion_1_0.bos", "Bonita 6.x", "ProcWithSameNameAndVersion", getClass(), false);
		bot.waitUntil(new EditorOpenCondition(store.getChild("ProcWithSameNameAndVersion-1.0.proc").getResource()));
		SWTBotEditor botEditor = bot.activeEditor();
		String title = botEditor.getTitle();
		SWTBotGefEditor gmfEditor = bot.gefEditor(title);
		bot.waitUntil(Conditions.shellIsActive("Bonita BPM"));


		SWTBotTestUtil.createFormWhenOnAProcessWithStep(bot, gmfEditor, "Step1");
		SWTBotTestUtil.importProcessWIthPathFromClass(bot, "_2ProcWithSameNameAndVersion_1_0.bos", "Bonita 6.x", "ProcWithSameNameAndVersion", getClass(), false);
		bot.waitUntil(new EditorOpenCondition(store.getChild("ProcWithSameNameAndVersion-1.0.proc").getResource()));
		bot.waitUntil(Conditions.shellIsActive("Bonita BPM"));

		final SWTBotGefEditor gmfEditor2 = bot.gefEditor(title);
		IGraphicalEditPart part = (IGraphicalEditPart)gmfEditor2.mainEditPart().part();
		MainProcess model = ModelHelper.getMainProcess(part.resolveSemanticElement());
		assertNotNull("no pool found (import failed?)",model);
		assertContains("ProcWithSameNameAndVersion", gmfEditor2.getTitle());
		bot.waitUntil(new ICondition() {

			public boolean test() throws Exception {
				MainProcess diagram = (MainProcess) ((IGraphicalEditPart)gmfEditor2.mainEditPart().part()).resolveSemanticElement();
				return "Unique description 2".equals(diagram.getDocumentation());
			}

			public void init(SWTBot bot) {

			}

			public String getFailureMessage() {
				MainProcess diagram = (MainProcess) ((IGraphicalEditPart)gmfEditor2.mainEditPart().part()).resolveSemanticElement();
				return "Unique description 2 was expected but was :"+diagram.getDocumentation();
			}
		},10000,500);
	}

}
