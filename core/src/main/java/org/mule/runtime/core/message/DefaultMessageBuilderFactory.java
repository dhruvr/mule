/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.core.message;

import org.mule.runtime.api.message.AbstractMuleMessageBuilderFactory;
import org.mule.runtime.core.api.InternalMessage;

/**
 *
 */
public class DefaultMessageBuilderFactory extends AbstractMuleMessageBuilderFactory {

  private static DefaultMessageBuilderFactory INSTANCE = new DefaultMessageBuilderFactory();

  public static DefaultMessageBuilderFactory getInstance() {
    return INSTANCE;
  }

  @Override
  public InternalMessage.Builder create() {
    return new DefaultMessageBuilder();
  }

  @Override
  public InternalMessage.Builder create(org.mule.runtime.api.message.Message message) {
    return new DefaultMessageBuilder(message);
  }

  public InternalMessage.Builder create(InternalMessage message) {
    return new DefaultMessageBuilder(message);
  }

}
