/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.internal.runtime.notification;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.core.api.context.notification.ServerNotificationManager;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.extension.api.notification.NotificationActionDefinition;
import org.mule.runtime.extension.api.notification.NotificationEmitter;

/**
 * Default implementation of {@link NotificationEmitter}.
 */
public class DefaultNotificationEmitter implements NotificationEmitter {

  private final ServerNotificationManager notificationManager;
  private final CoreEvent event;
  private final ComponentLocation componentLocation;

  public DefaultNotificationEmitter(ServerNotificationManager notificationManager, CoreEvent event,
                                    ComponentLocation componentLocation) {
    this.notificationManager = notificationManager;
    this.event = event;
    this.componentLocation = componentLocation;
  }

  @Override
  public void fire(NotificationActionDefinition action, TypedValue<?> data) {
    notificationManager.fireNotification(new DefaultExtensionNotification(event, componentLocation, action, data));
  }

}
