/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.internal.runtime.notification;

import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.core.api.context.notification.ServerNotificationManager;
import org.mule.runtime.core.api.event.CoreEvent;
import org.mule.runtime.extension.api.notification.ExtensionNotification;
import org.mule.runtime.extension.api.notification.NotificationHandler;
import org.mule.runtime.extension.api.notification.NotificationInfo;

/**
 * Default implementation of {@link NotificationHandler}.
 */
public class DefaultNotificationHandler implements NotificationHandler {

  private final ServerNotificationManager notificationManager;
  private final CoreEvent event;
  private final ComponentLocation componentLocation;

  public DefaultNotificationHandler(ServerNotificationManager notificationManager, CoreEvent event,
                                    ComponentLocation componentLocation) {
    this.notificationManager = notificationManager;
    this.event = event;
    this.componentLocation = componentLocation;
  }

  @Override
  public void fireWith(NotificationInfo notificationInfo) {
    notificationManager.fireNotification(ExtensionNotification.builder()
        .event(event)
        .componentLocation(componentLocation)
        .info(notificationInfo)
        .build());
  }

}
