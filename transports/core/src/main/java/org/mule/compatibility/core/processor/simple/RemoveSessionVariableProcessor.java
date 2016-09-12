/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.compatibility.core.processor.simple;

import org.mule.runtime.core.PropertyScope;
import org.mule.runtime.core.api.Event;
import org.mule.runtime.core.processor.simple.AbstractRemoveVariablePropertyProcessor;

import java.util.Set;

public class RemoveSessionVariableProcessor extends AbstractRemoveVariablePropertyProcessor {

  @Override
  protected Event removeProperty(Event event, String propertyName) {
    event.getSession().removeProperty(propertyName);
    return event;
  }

  @Override
  protected Set<String> getPropertyNames(Event event) {
    return event.getSession().getPropertyNamesAsSet();
  }

  @Override
  protected String getScopeName() {
    return PropertyScope.SESSION_VAR_NAME;
  }

}
