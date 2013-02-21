/**
 * Copyright (C) 2011-2012 BonitaSoft S.A.
 * BonitaSoft, 32 rue Gustave Eiffel - 38000 Grenoble
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
package org.bonitasoft.studio.decision.ui;

import java.util.ArrayList;
import java.util.List;

import org.bonitasoft.studio.common.ExpressionConstants;
import org.bonitasoft.studio.condition.ui.expression.ComparisonExpressionValidator;
import org.bonitasoft.studio.expression.editor.filter.AvailableExpressionTypeFilter;
import org.bonitasoft.studio.expression.editor.viewer.ExpressionViewer;
import org.bonitasoft.studio.expression.editor.viewer.IExpressionValidationListener;
import org.bonitasoft.studio.model.expression.Expression;
import org.bonitasoft.studio.model.expression.ExpressionFactory;
import org.bonitasoft.studio.model.process.Element;
import org.bonitasoft.studio.model.process.decision.DecisionFactory;
import org.bonitasoft.studio.model.process.decision.DecisionTable;
import org.bonitasoft.studio.model.process.decision.DecisionTableAction;
import org.bonitasoft.studio.model.process.decision.DecisionTableLine;
import org.bonitasoft.studio.pics.Pics;
import org.bonitasoft.studio.pics.PicsConstants;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.emf.databinding.EMFDataBindingContext;

/**
 * @author Mickael Istria
 *
 */
public class DecisionTableWizardPage extends WizardPage {

	/**
	 * @author Mickael Istria
	 *
	 */
	private final class ComparisonSymbolsLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if ("==".equals(element)) {
				return "=";
			} else if ("!=".equals(element)) {
				return "\u2260";
			} else if ("<=".equals(element)) {
				return "\u2264";
			} else if (">=".equals(element)) {
				return "\u2265";
			} else {
				return super.getText(element);
			}
		}
	}
	private ComparisonSymbolsLabelProvider symbolsLabelProvider = new ComparisonSymbolsLabelProvider();

	/**
	 * 
	 */
	private static final int DELETE_SIZE = 12;
	/**
	 * 
	 */
	private static final Color GRAY = new Color(Display.getCurrent(), 140, 140, 140);

	private TransactionalEditingDomain editingDomain;
	private DecisionTableLine lineWorkingCopy;
	private DecisionTableLine toEditLine;
	private EMFDataBindingContext dataBindingContext;

	private Element container;
	private DecisionTableWizard wizard;

	/**
	 * @param container 
	 * @param pageName
	 */
	protected DecisionTableWizardPage(DecisionTableWizard wizard, Element container, DecisionTable decisionTable) {
		super("Decision Table");
		this.wizard = wizard;
		this.container = container;
		this.decisionTable = decisionTable;
		setTitle(Messages.wizardPageTitle);
		setDescription(Messages.wizardPageDesc);
		setImageDescriptor(Pics.getWizban());
	}

	private DecisionTable decisionTable;
	private ScrolledComposite gridParentScrollable;
	private ScrolledComposite lineParentScrollable;
	private Button updateLineButton;
	private Composite gridPlaceHolder;
	private Composite linePlaceHolder;

	private DecisionTableGridPaintListener zebraGridPaintListener;

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	public void createControl(Composite arg0) {
		if (decisionTable.getDefaultAction() == null && wizard.getPossibleDefaultTableActions() != null
				&& wizard.getPossibleDefaultTableActions().length > 0) {
			decisionTable.setDefaultAction(wizard.getPossibleDefaultTableActions()[0]);
		}

		Composite res = new Composite(arg0, SWT.NONE);
		res.setLayout(new GridLayout(1, false));

		Group gridGroup = new Group(res, SWT.NONE);
		gridGroup.setText(Messages.table);
		gridGroup.setLayout(new GridLayout(1, false));
		Label tableDescriptionLabel = new Label(gridGroup, SWT.WRAP);
		tableDescriptionLabel.setText(Messages.tableDescriptionLabel);
		tableDescriptionLabel.setLayoutData(new GridData(SWT.FILL, SWT.DEFAULT, true, false));
		final GridData gridGrouplayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridGrouplayoutData.minimumHeight = 150;
		gridGroup.setLayoutData(gridGrouplayoutData);
		gridParentScrollable = new ScrolledComposite(gridGroup, SWT.H_SCROLL | SWT.V_SCROLL);
		gridParentScrollable.setLayout(new GridLayout(1,false));
		gridParentScrollable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		gridPlaceHolder = new Composite(gridParentScrollable, SWT.BORDER);
		zebraGridPaintListener = new DecisionTableGridPaintListener();
		gridPlaceHolder.addPaintListener(zebraGridPaintListener);
		gridParentScrollable.setContent(gridPlaceHolder);

		Group editLineGroup = new Group(res, SWT.NONE);
		editLineGroup.setText(Messages.editRow);
		editLineGroup.setLayout(new GridLayout(1, false));
		final GridData editLineGrouplayoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridGrouplayoutData.minimumHeight = 150;
		editLineGroup.setLayoutData(editLineGrouplayoutData);
		lineParentScrollable = new ScrolledComposite(editLineGroup, SWT.H_SCROLL | SWT.V_SCROLL);
		lineParentScrollable.setLayout(new FillLayout());
		lineParentScrollable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		linePlaceHolder = new Composite(lineParentScrollable, SWT.NONE);
		lineParentScrollable.setContent(linePlaceHolder);

		Composite buttonComposite = new Composite(editLineGroup, SWT.NONE);
		buttonComposite.setLayoutData(new GridData(SWT.RIGHT, SWT.DEFAULT, true, true));
		buttonComposite.setLayout(new GridLayout(3, false));
		updateLineButton = new Button(buttonComposite, SWT.PUSH);
		updateLineButton.setText(Messages.updateLine);
		updateLineButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				int i = decisionTable.getLines().indexOf(toEditLine);
				decisionTable.getLines().remove(toEditLine);
				addLine(i, toEditLine.getAction());
				refresh(true, true);
			}
		});

		updateLineButton.setEnabled(false);

		refreshGrid();
		refreshLine();

		setControl(res);
	}


	/**
	 * 
	 */
	protected void refreshGrid() {
		if (gridPlaceHolder != null) {
			for (Control child : gridPlaceHolder.getChildren()) {
				child.dispose();
			}
			createGrid(gridPlaceHolder);
			gridPlaceHolder.pack();
			gridPlaceHolder.layout(true);
			Point size = gridPlaceHolder.getSize();
			size.x = size.x - 15 ;
			gridParentScrollable.setExpandHorizontal(true);
			gridParentScrollable.setExpandVertical(false);
			gridParentScrollable.setMinSize(size);
		}
	}

	protected void refreshLine() {
		if (linePlaceHolder != null) {
			for (Control child : linePlaceHolder.getChildren()) {
				child.dispose();
			}
			createEditLine(linePlaceHolder);
			linePlaceHolder.pack();
			linePlaceHolder.layout(true);
			Point size = linePlaceHolder.getSize();
			size.x = size.x - 15 ;
			lineParentScrollable.setExpandHorizontal(false);
			lineParentScrollable.setExpandVertical(false);
			lineParentScrollable.setMinSize(size);
		}		
	}

	/**
	 * @param placeHolder
	 */
	public Composite createEditLine(final Composite placeHolder) {
		if (lineWorkingCopy == null) {
			placeHolder.setLayout(new FillLayout());
			Label label = new Label(placeHolder, SWT.NONE);
			label.setText(Messages.selectALine);
			return placeHolder;
		}

		placeHolder.setLayout(new GridLayout(6, false));

		final List<ExpressionViewer> operands = new ArrayList<ExpressionViewer>();

		GridData iconButtonGridData = new GridData(SWT.CENTER, SWT.CENTER, false, false);
		iconButtonGridData.widthHint = iconButtonGridData.heightHint = 16;

		final int nbConditions = lineWorkingCopy.getConditions().size();
		for (int i = 0; i < nbConditions; i++) {
			final int index = i;
			final Expression cond = lineWorkingCopy.getConditions().get(index);

			if (index != 0) {
				Label andLabel = new Label(placeHolder, SWT.NONE);
				andLabel.setText(Messages.and);
				andLabel.setForeground(GRAY);
			} else {
				new Composite(placeHolder, SWT.NONE).setLayoutData(new GridData(0, 0));
			}

			Composite cross = createImageButton(placeHolder, Pics.getImage(PicsConstants.remove));
			cross.setLayoutData(iconButtonGridData);
			cross.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					lineWorkingCopy.getConditions().remove(index);
					refresh(false, true);

				}
			});

			if (i != 0) {
				Composite upComposite = createImageButton(placeHolder, Pics.getImage(PicsConstants.arrowUp));
				upComposite.setLayoutData(iconButtonGridData);
				upComposite.addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent e) {
						lineWorkingCopy.getConditions().remove(index);
						lineWorkingCopy.getConditions().add(index - 1, cond);
						refresh(false, true);
					}
				});
			} else {
				new Composite(placeHolder, SWT.NONE).setLayoutData(new GridData(0, 0));
			}

			if (i != nbConditions - 1) {
				Composite downComposite = createImageButton(placeHolder, Pics.getImage(PicsConstants.arrowDown));
				downComposite.setLayoutData(iconButtonGridData);
				downComposite.addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent e) {
						lineWorkingCopy.getConditions().remove(index);
						lineWorkingCopy.getConditions().add(index + 1, cond);
						refresh(false, true);
					}
				});
			} else {
				new Composite(placeHolder, SWT.NONE).setLayoutData(new GridData(0, 0));
			}
			
			final ExpressionViewer op1widget = new ExpressionViewer(placeHolder, SWT.BORDER,null);
			op1widget.getControl().setLayoutData(GridDataFactory.fillDefaults().hint(500, SWT.DEFAULT).grab(true, false).span(2, 1).create());
			op1widget.addExpressionValidator(ExpressionConstants.CONDITION_TYPE,new ComparisonExpressionValidator());
			op1widget.addExpressionValidationListener(new IExpressionValidationListener(){

				@Override
				public void validationStatusChanged(int newStatus) {
					updateButtons(operands);
				}

			});
			op1widget.setContext(container);
			op1widget.addFilter(new AvailableExpressionTypeFilter(new String[]{ExpressionConstants.CONDITION_TYPE}));
			op1widget.setInput(lineWorkingCopy);
			op1widget.setSelection(new StructuredSelection(cond));
			op1widget.getEraseControl().addListener(SWT.Selection, new Listener() {

				@Override
				public void handleEvent(Event event) {
					updateButtons(operands);
				}
			});
			operands.add(op1widget);

			for (ExpressionViewer operand : operands) {
				operand.addExpressionEditorChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						updateButtons(operands);
					}
				});	
			}
		}
		Link addConditionLink = new Link(linePlaceHolder, SWT.NONE);
		addConditionLink.setText("<A>" + Messages.addCondition + "</A>");
		addConditionLink.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				final Expression exp = ExpressionFactory.eINSTANCE.createExpression();
				exp.setReturnType(Boolean.class.getName());
				exp.setReturnTypeFixed(true);
				exp.setType(ExpressionConstants.CONDITION_TYPE);


				lineWorkingCopy.getConditions().add(exp);
				updateLineButton.setEnabled(false);
				refresh(false, true);
			}
		});

		updateButtons(operands) ;

		return placeHolder;

	}


	/**
	 * @param operands
	 * @param operators
	 * @return
	 */
	protected void updateButtons(List<ExpressionViewer> operands) {
		boolean oneIsEmpty = false;
		for (ExpressionViewer operand : operands) {
			oneIsEmpty = oneIsEmpty || operand.getSelection() == null || operand.getMessage(IStatus.ERROR)!=null;
			oneIsEmpty = oneIsEmpty || ((Expression)((StructuredSelection)operand.getSelection()).getFirstElement()).getContent() == null;
			oneIsEmpty = oneIsEmpty || ((Expression)((StructuredSelection)operand.getSelection()).getFirstElement()).getContent().isEmpty();
		}
		updateLineButton.setEnabled(!oneIsEmpty && toEditLine != null);
	}

	/**
	 * 
	 */
	protected void refresh(boolean refreshGrid, boolean refreshLine) {
		if (refreshGrid) {
			refreshGrid();
		}
		if (refreshLine) {
			refreshLine();
		}
		//	gridParentScrollable.getParent().getParent().getParent().pack(true);
		//	gridParentScrollable.getParent().getParent().getParent().layout(true);
		gridParentScrollable.getShell().pack(true) ;
		gridParentScrollable.getShell().layout(true) ;
		getContainer().updateButtons();
	}

	/**
	 * @param gridPlaceholder 
	 * @param gridPlaceHolder
	 * @return 
	 */
	public Composite createGrid(final Composite gridPlaceholder) {
		gridPlaceholder.setBackgroundMode(SWT.INHERIT_FORCE);
		int nbColumns = 7; // Edit, delete, up, down, space (on the left) +  arrow & Action (on the right);
		int nbMaxConditions = 0;
		for (DecisionTableLine line : decisionTable.getLines()) {
			nbMaxConditions = Math.max(line.getConditions().size(), nbMaxConditions);
		}
		if (nbMaxConditions > 0) {
			nbColumns += 2 * nbMaxConditions - 1; 
		}
		final GridLayout layout = new GridLayout(nbColumns, false);
		gridPlaceholder.setLayout(layout);
		gridPlaceholder.setLayoutData(new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false));

		{ 
			Composite filler = new Composite(gridPlaceholder, SWT.NONE);
			GridData fillerLayoutData = new GridData(0, 0);
			fillerLayoutData.horizontalSpan = 5;
			filler.setLayoutData(fillerLayoutData);
			// TITLE
			if (nbMaxConditions > 0) {
				Label conditionsLabel = new Label(gridPlaceholder, SWT.NONE);
				conditionsLabel.setText(Messages.conditions);
				conditionsLabel.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, conditionsLabel));
				conditionsLabel.setLayoutData(new GridData(SWT.CENTER, SWT.NONE, false, false, 2 * nbMaxConditions - 1, 1));
			}

			new Composite(gridPlaceholder, SWT.NONE).setLayoutData(new GridData(0,0)) ;

			Label decisionLabel = new Label(gridPlaceholder, SWT.NONE);
			decisionLabel.setLayoutData(GridDataFactory.fillDefaults().align(SWT.CENTER, SWT.FILL).grab(false, false).span(1, 1).create());
			decisionLabel.setText(Messages.decision);
			decisionLabel.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, decisionLabel));
		}


		final GridData iconButtonLayoutData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		iconButtonLayoutData.heightHint = DELETE_SIZE;
		iconButtonLayoutData.widthHint = DELETE_SIZE;
		final GridData oneCellFillerLayoutData = new GridData(0, 0);
		final GridData andLayoutData = new GridData(SWT.FILL, SWT.CENTER, false, false);

		for (int i = 0; i < decisionTable.getLines().size(); i++) {
			final int index = i;
			final DecisionTableLine line = decisionTable.getLines().get(i);

			if (line != toEditLine) {
				Composite editLineComposite = createImageButton(gridPlaceholder, Pics.getImage(PicsConstants.edit));
				editLineComposite.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, editLineComposite));
				editLineComposite.setLayoutData(iconButtonLayoutData);
				editLineComposite.setToolTipText(Messages.editRow);
				editLineComposite.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						toEditLine = line;
						lineWorkingCopy = EcoreUtil.copy(toEditLine);
						updateLineButton.setEnabled(true);
						zebraGridPaintListener.setSelectedRow(index) ;
						refresh(true, true);
					}
				});
			} else {
				new Composite(gridPlaceholder, SWT.NONE).setLayoutData(new GridData(0, 0)) ;
			}

			Composite deleteLineComposite = createImageButton(gridPlaceholder, Pics.getImage(PicsConstants.remove));
			deleteLineComposite.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, deleteLineComposite)); 
			deleteLineComposite.setLayoutData(iconButtonLayoutData);
			deleteLineComposite.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					if (MessageDialog.openConfirm(gridPlaceholder.getShell(), Messages.deleteLineTitle, Messages.deleteLineMessage)) {
						deleteLine(line);
					}
				}
			});

			if (index != 0) {
				Composite lineUpComposite = createImageButton(gridPlaceholder, Pics.getImage(PicsConstants.arrowUp));
				lineUpComposite.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, lineUpComposite)); 
				lineUpComposite.setLayoutData(iconButtonLayoutData);
				lineUpComposite.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						decisionTable.getLines().remove(index);
						decisionTable.getLines().add(index - 1, line);
						zebraGridPaintListener.setSelectedRow(decisionTable.getLines().indexOf(line)) ;
						refreshGrid();
					}
				});
			} else {
				new Composite(gridPlaceholder, SWT.NONE).setLayoutData(oneCellFillerLayoutData);
			}

			if (i != decisionTable.getLines().size() - 1) {
				Composite lineDownComposite = createImageButton(gridPlaceholder, Pics.getImage(PicsConstants.arrowDown));
				lineDownComposite.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, lineDownComposite));
				lineDownComposite.setLayoutData(iconButtonLayoutData);
				lineDownComposite.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseDown(MouseEvent e) {
						decisionTable.getLines().remove(index);
						decisionTable.getLines().add(index + 1, line);
						zebraGridPaintListener.setSelectedRow(decisionTable.getLines().indexOf(line)) ;
						refreshGrid();
					}
				});
			} else {
				new Composite(gridPlaceholder, SWT.NONE).setLayoutData(oneCellFillerLayoutData);
			}

			if(!line.getConditions().isEmpty()){
				new Composite(gridPlaceholder, SWT.NONE).setLayoutData(new GridData(0, 20));
			}

			Label andLabel = null;
			for (Expression cond : line.getConditions()) {
				Label condLabel = new Label(gridPlaceholder, SWT.NONE);
				condLabel.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, condLabel)); 
				String stringCondition = cond.getContent();
				if (stringCondition.length() > 15) {
					condLabel.setToolTipText(stringCondition);
					stringCondition = stringCondition.substring(0, 12) + "...";
				}
				condLabel.setText(stringCondition);
				andLabel = new Label(gridPlaceholder, SWT.NONE);
				andLabel.setText(Messages.and);
				andLabel.setForeground(GRAY);
				andLabel.setLayoutData(andLayoutData);
				andLabel.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, andLabel));
			}
			if (andLabel != null) {
				andLabel.dispose();
			}

			if (line.getConditions().size() < nbMaxConditions) {
				Composite filler = new Composite(gridPlaceholder, SWT.NONE);
				int nbColumnsToFill = 0;
				if (line.getConditions().isEmpty()) {
					nbColumnsToFill = 2 * (nbMaxConditions - line.getConditions().size()) - 1;
				} else {
					nbColumnsToFill = 2 * (nbMaxConditions - line.getConditions().size());
				}
				GridData gd = new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false, nbColumnsToFill, 1);
				gd.widthHint = gd.heightHint = 0;
				filler.setLayoutData(gd);
			}

			if(line.getConditions().isEmpty()){
				Label condLabel = new Label(gridPlaceholder, SWT.NONE);
				condLabel.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, condLabel)); 
				condLabel.setText(Messages.noConditionDefined) ;
				condLabel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,1,1)) ;
			}

			Composite arrow = createImageButton(gridPlaceHolder, Pics.getImage(PicsConstants.arrowRight));
			arrow.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, arrow)); 
			final GridData arrowlayoutData = new GridData(16, 16);
			arrowlayoutData.horizontalAlignment = SWT.RIGHT;
			arrowlayoutData.grabExcessHorizontalSpace = true ;
			arrow.setLayoutData(arrowlayoutData);

			final ComboViewer lineValueCombo = new ComboViewer(gridPlaceholder, SWT.READ_ONLY);
			lineValueCombo.setContentProvider(new ArrayContentProvider());
			if (wizard.getActionsComparer() != null) {
				lineValueCombo.setComparer(wizard.getActionsComparer());
			}
			lineValueCombo.setLabelProvider(wizard.getActionLabelProvider());
			lineValueCombo.setInput(wizard.getPossibleLineActions());
			lineValueCombo.setSelection(new StructuredSelection(line.getAction()));
			lineValueCombo.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent arg0) {
					final DecisionTableAction action = EcoreUtil.copy( (DecisionTableAction) ((IStructuredSelection)lineValueCombo.getSelection()).getFirstElement() );
					line.setAction(action);
				}
			});
			lineValueCombo.getControl().setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		}

		{ // ADD LINE
			Link addRowLink = new Link(gridPlaceholder, SWT.TRANSPARENT);
			addRowLink.setText("<A>" + Messages.addRow + "</A>");
			addRowLink.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, addRowLink));
			addRowLink.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1)); // Edit, up, down, delete
			addRowLink.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					DecisionTableLine newRow = DecisionFactory.eINSTANCE.createDecisionTableLine();
					newRow.setAction(EcoreUtil.copy(wizard.getPossibleLineActions()[0]));
					decisionTable.getLines().add(newRow);
					toEditLine = newRow;
					lineWorkingCopy = EcoreUtil.copy(newRow);
					updateLineButton.setEnabled(true);
					zebraGridPaintListener.setSelectedRow(decisionTable.getLines().indexOf(newRow)) ;
					refresh(true, true);
				}
			});
			Composite filler = new Composite(gridPlaceholder, SWT.NONE);
			final GridData layoutData = new GridData(SWT.FILL, SWT.NONE, false, false, 2 * nbMaxConditions + 1, 1);
			layoutData.heightHint = layoutData.heightHint = 0;
			filler.setLayoutData(layoutData);
		}

		{ // DEFAULT
			Label defaultLabel = new Label(gridPlaceholder, SWT.NONE);
			defaultLabel.setText(Messages.defaultLine);
			defaultLabel.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, defaultLabel));
			defaultLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 5, 1)); // Edit, up, down, delete
			if (nbMaxConditions > 0) {
				Composite filler = new Composite(gridPlaceholder, SWT.NONE);
				final GridData layoutData = new GridData(SWT.DEFAULT, SWT.DEFAULT, false, false, 2 * nbMaxConditions - 1, 1);
				layoutData.heightHint = layoutData.widthHint = 0;
				filler.setLayoutData(layoutData);
			}
			Composite arrow = createImageButton(gridPlaceHolder, Pics.getImage(PicsConstants.arrowRight));
			arrow.setBackground(zebraGridPaintListener.getColorForControl(gridPlaceholder, arrow));
			final GridData arrowlayoutData = new GridData(16, 16);
			arrowlayoutData.horizontalAlignment = SWT.END;
			arrowlayoutData.grabExcessHorizontalSpace = true;
			arrow.setLayoutData(arrowlayoutData);

			final ComboViewer defaultValueCombo = new ComboViewer(gridPlaceholder, SWT.READ_ONLY);
			defaultValueCombo.setContentProvider(new ArrayContentProvider());
			if (wizard.getActionsComparer() != null) {
				defaultValueCombo.setComparer(wizard.getActionsComparer());
			}
			defaultValueCombo.setLabelProvider(wizard.getActionLabelProvider());
			defaultValueCombo.setInput(wizard.getPossibleDefaultTableActions());
			if (decisionTable.getDefaultAction() != null) {
				defaultValueCombo.setSelection(new StructuredSelection(decisionTable.getDefaultAction()));
			}
			defaultValueCombo.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent arg0) {
					final DecisionTableAction action = EcoreUtil.copy( (DecisionTableAction) ((IStructuredSelection)arg0.getSelection()).getFirstElement() );
					decisionTable.setDefaultAction(action);
				}
			});
			defaultValueCombo.getControl().setLayoutData(new GridData(SWT.END, SWT.CENTER, false, false));
		}	


		return gridPlaceholder;
	}

	/**
	 * @param line
	 */
	protected void deleteLine(DecisionTableLine line) {
		if (line == toEditLine) {
			toEditLine = null;
			zebraGridPaintListener.setSelectedRow(-1) ;
			updateLineButton.setEnabled(false);
			lineWorkingCopy = null ;
			decisionTable.getLines().remove(line);
		}else{
			decisionTable.getLines().remove(line);
			zebraGridPaintListener.setSelectedRow(decisionTable.getLines().indexOf(toEditLine)) ;
		}


		refresh(true, true);
	}

	/**
	 * @param parent
	 * @param image
	 * @return
	 */
	public Canvas createImageButton(Composite parent, final Image image) {
		final Canvas deleteButton = new Canvas(parent, SWT.TRANSPARENT);
		deleteButton.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, 0, 0, 16, 16, 0, 0, DELETE_SIZE, DELETE_SIZE);
			}
		});
		return deleteButton;
	}

	/**
	 * @param text
	 * @param location
	 * @param refCondition
	 */
	protected void addLine(int index, DecisionTableAction action) {
		final DecisionTableLine newLine = EcoreUtil.copy(lineWorkingCopy);
		if (action == null) {
			newLine.setAction(EcoreUtil.copy(wizard.getPossibleLineActions()[0]));	
		} else {
			newLine.setAction(EcoreUtil.copy(action));
		}
		decisionTable.getLines().add(index, newLine);
		toEditLine = newLine;
		updateLineButton.setEnabled(true);
	}

	@Override
	public boolean isPageComplete() {
		for(DecisionTableLine line : decisionTable.getLines()){
			if(line.getConditions().size() == 0){
				setErrorMessage(Messages.errorMessageNoConditionForLineInDecisionTable);
				return false;
			}
		}
		setErrorMessage(null);
		return super.isPageComplete();
	}

}
