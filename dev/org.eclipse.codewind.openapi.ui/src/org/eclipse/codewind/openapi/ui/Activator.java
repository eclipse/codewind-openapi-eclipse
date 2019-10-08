/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.codewind.openapi.ui;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.codewind.openapi.ui.util.UILogger;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.osgi.service.debug.DebugOptionsListener;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.codewind.openapi.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		context.registerService(DebugOptionsListener.class, UILogger.instance(), null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		ImageRegistry imageRegistry = plugin.getImageRegistry();
		ImageDescriptor descriptor = imageRegistry.getDescriptor(path);		
		if (descriptor == null) {
			ImageDescriptor imageDescriptorFromPlugin = imageDescriptorFromPlugin(PLUGIN_ID, path);
			imageRegistry.put(path, imageDescriptorFromPlugin);
			return imageDescriptorFromPlugin;
		}
		return descriptor;
	}
	
	public static void log(int severity, String message) {
		plugin.getLog().log(new Status(severity, PLUGIN_ID, message));
	}

	public static void log(int severity, String message, Exception e) {
		plugin.getLog().log(new Status(severity, PLUGIN_ID, message));
		log(severity, e);
	}

	public static void log(int severity, Exception e) {
		if (UILogger.ERROR) {
			StringWriter writer = new StringWriter();
			PrintWriter pw = new PrintWriter(writer);
			e.printStackTrace(pw);
			plugin.getLog().log(new Status(severity, PLUGIN_ID, writer.toString()));				
		}
	}

}
