package org.codefaces.ui.internal.perspectives;

import org.codefaces.ui.internal.views.ProjectExplorerViewPart;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class CodeFacesPerspectiveFactory implements IPerspectiveFactory {
	public static final String ID = "org.codefaces.ui.perspective.codefaces";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.addStandaloneView(ProjectExplorerViewPart.ID, true,
				IPageLayout.LEFT, 0.25f, editorArea);
	}
}
