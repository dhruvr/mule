/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.runtime.core.source.polling.watermark;

import org.mule.runtime.core.api.Event;
import org.mule.runtime.core.api.expression.InvalidExpressionException;
import org.mule.runtime.core.api.lifecycle.Initialisable;
import org.mule.runtime.core.api.lifecycle.InitialisationException;
import org.mule.runtime.core.api.store.ObjectStore;
import org.mule.runtime.core.config.i18n.I18nMessageFactory;
import org.mule.runtime.core.source.polling.MessageProcessorPollingInterceptor;

import java.io.NotSerializableException;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * Implementation of {@link Watermark} in which the value is updated through a MEL expression
 */
public class UpdateExpressionWatermark extends Watermark implements Initialisable {

  /**
   * The update expression to update the watermark value in the object store. It is optional so it can be null.
   */
  private final String updateExpression;
  private final MessageProcessorPollingInterceptor interceptor;

  public UpdateExpressionWatermark(ObjectStore<Serializable> objectStore, String variable, String defaultExpression,
                                   String updateExpression) {
    super(objectStore, variable, defaultExpression);
    this.updateExpression = updateExpression;
    this.interceptor = new WatermarkPollingInterceptor(this);
  }

  @Override
  public void initialise() throws InitialisationException {
    if (!StringUtils.isEmpty(this.updateExpression)) {
      try {
        this.muleContext.getExpressionLanguage().validate(this.updateExpression);
      } catch (InvalidExpressionException e) {
        throw new InitialisationException(I18nMessageFactory.createStaticMessage(String
            .format("update-expression requires a valid MEL expression. '%s' was found instead", this.updateExpression)), e,
                                          this);
      }
    }
  }

  /**
   * Returns the new watermark value by evaluating {@link #updateExpression} on the flowVar of the given name
   * 
   * @param event the @{link {@link Event} in which the watermark is being evaluated
   * @return a {@link Serializable} value
   */
  @Override
  protected Object getUpdatedValue(Event event) {
    try {
      return StringUtils.isEmpty(this.updateExpression) ? event.getVariable(this.resolveVariable(event))
          : WatermarkUtils.evaluate(this.updateExpression, event, muleContext);
    } catch (NotSerializableException e) {
      throw new IllegalArgumentException(e);
    }
  }

  @Override
  public MessageProcessorPollingInterceptor interceptor() {
    return this.interceptor;
  }
}
