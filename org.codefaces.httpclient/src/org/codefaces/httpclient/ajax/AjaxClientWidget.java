package org.codefaces.httpclient.ajax;

import org.codefaces.httpclient.ajax.internal.AjaxClientWidgetLCA;
import org.eclipse.rwt.RWT;
import org.eclipse.rwt.lifecycle.ILifeCycleAdapter;
import org.eclipse.rwt.service.ISessionStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class AjaxClientWidget extends Control {
	private AjaxClient client;

	private AjaxClientWidgetLCA widgetLCA;

	public AjaxClientWidget() {
		this(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
	}

	public AjaxClientWidget(Shell shell) {
		super(shell, SWT.NONE);
		client = new AjaxClient(shell.getDisplay());
	}

	public AjaxClient getClient() {
		return client;
	}

	public static AjaxClientWidget getCurrent() {
		final ISessionStore sessionStore = RWT.getSessionStore();
		AjaxClientWidget widget = (AjaxClientWidget) sessionStore
				.getAttribute(AjaxClientWidget.class.toString());
		if (widget == null) {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				@Override
				public void run() {
					sessionStore.setAttribute(
							AjaxClientWidget.class.toString(),
							new AjaxClientWidget());
				}
			});
		}

		return (AjaxClientWidget) sessionStore
				.getAttribute(AjaxClientWidget.class.toString());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		Object result;
		if (adapter == ILifeCycleAdapter.class) {
			if (widgetLCA == null) {
				widgetLCA = new AjaxClientWidgetLCA();
			}

			result = widgetLCA;
		} else {
			result = super.getAdapter(adapter);
		}
		return result;
	}

}
