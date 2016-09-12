/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.runtime.module.ws.functional;

import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;
import org.mule.runtime.core.api.InternalMessage;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.Test;

/**
 * Tests that envelope body unwrapping works fine when multiple namespaces are used
 */
public class MultipleNamespacesFunctionalTestCase extends AbstractWSConsumerFunctionalTestCase {

  private static final String EXPECTED_RESPONSE = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
      + "<ns2:echoResponse xmlns:ns2=\"http://consumer.ws.module.runtime.mule.org/\">\n"
      + "            <text xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:nil=\"false\">Hello</text>\n"
      + "</ns2:echoResponse>";

  @Override
  protected String getConfigFile() {
    return "multiple-namespaces-config.xml";
  }


  @Test
  public void validRequestReturnsExpectedAnswer() throws Exception {
    InternalMessage response = flowRunner("client").withPayload(ECHO_REQUEST).run().getMessage();

    XMLUnit.setIgnoreWhitespace(true);
    assertXMLEqual(EXPECTED_RESPONSE, getPayloadAsString(response));
  }

}
