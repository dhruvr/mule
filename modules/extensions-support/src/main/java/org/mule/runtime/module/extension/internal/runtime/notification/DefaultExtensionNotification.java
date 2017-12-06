/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.extension.internal.runtime.notification;

import static java.lang.String.format;
import static org.mule.runtime.api.util.Preconditions.checkArgument;
import org.mule.runtime.api.component.location.ComponentLocation;
import org.mule.runtime.api.event.Event;
import org.mule.runtime.api.metadata.DataType;
import org.mule.runtime.api.metadata.TypedValue;
import org.mule.runtime.api.notification.ExtensionNotification;
import org.mule.runtime.api.notification.NotificationType;
import org.mule.runtime.extension.api.notification.NotificationActionDefinition;
import org.mule.runtime.extension.internal.notification.ExtensionAction;

/**
 * Represents notifications fired by an extension.
 *
 * @since 1.1
 */
public class DefaultExtensionNotification implements ExtensionNotification {

  private static final long serialVersionUID = 6641419368045215517L;

  private final Event event;
  private final ComponentLocation componentLocation;
  private final NotificationActionDefinition actionDefinition;
  private final TypedValue<?> data;

  private ExtensionAction action;

  DefaultExtensionNotification(Event event, ComponentLocation componentLocation, NotificationActionDefinition actionDefinition,
                               TypedValue<?> data) {
    DataType actualDataType = data.getDataType();
    DataType expectedDataType = actionDefinition.getDataType();
    checkArgument(actualDataType.isCompatibleWith(expectedDataType),
                  format("The action data type (%s) does not match the actual data type received (%s)",
                         expectedDataType,
                         actualDataType));
    this.event = event;
    this.componentLocation = componentLocation;
    this.actionDefinition = actionDefinition;
    this.data = data;
  }

  @Override
  public Event getEvent() {
    return event;
  }

  @Override
  public ComponentLocation getComponentLocation() {
    return componentLocation;
  }

  @Override
  public TypedValue<?> getData() {
    return data;
  }

  public String getEventName() {
    // TODO: Figure out if this makes sense as a generic name, eg: HTTP Notification
    return "";
  }

  @Override
  public NotificationType getNotificationType() {
    return actionDefinition.getType();
  }

  @Override
  public boolean isSynchronous() {
    return actionDefinition.isSynchronous();
  }

  @Override
  public Action getAction() {
    if (action == null) {
      action = new ExtensionAction(createActionId(), actionDefinition.getDescription());
    }
    return action;
  }

  @Override
  public String toString() {
    // TODO: Figure out a proper representation
    return getEventName() + "{action=" + action.getId() + ", location: " + componentLocation + "}";
  }

  private String createActionId() {
    return componentLocation.getComponentIdentifier().getIdentifier().getNamespace().toUpperCase() + ":"
        + actionDefinition.toString();
  }

}
