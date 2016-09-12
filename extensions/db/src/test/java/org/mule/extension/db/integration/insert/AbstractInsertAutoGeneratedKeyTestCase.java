/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.extension.db.integration.insert;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;

import org.mule.extension.db.api.StatementResult;
import org.mule.extension.db.integration.AbstractDbIntegrationTestCase;
import org.mule.extension.db.integration.TestDbConfig;
import org.mule.extension.db.integration.model.AbstractTestDatabase;
import org.mule.runtime.core.api.InternalMessage;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runners.Parameterized;

public abstract class AbstractInsertAutoGeneratedKeyTestCase extends AbstractDbIntegrationTestCase {

  public AbstractInsertAutoGeneratedKeyTestCase(String dataSourceConfigResource, AbstractTestDatabase testDatabase) {
    super(dataSourceConfigResource, testDatabase);
  }

  @Parameterized.Parameters
  public static List<Object[]> parameters() {
    return TestDbConfig.getResources();
  }

  @Test
  public void returnsAutoGeneratedKeys() throws Exception {
    InternalMessage response = flowRunner("insertWithAutoGeneratedKeys").run().getMessage();

    assertThat(response.getPayload().getValue(), is(instanceOf(StatementResult.class)));
    StatementResult result = (StatementResult) response.getPayload().getValue();
    assertThat(result.getAffectedRows(), is(1));
    Map<String, BigInteger> generatedKeys = result.getGeneratedKeys();
    assertThat(generatedKeys.size(), equalTo(1));
    assertThat(generatedKeys.values().toArray()[0], is(instanceOf(getIdFieldJavaClass())));
  }

  protected Class getIdFieldJavaClass() {
    return testDatabase.getIdFieldJavaClass();
  }
}
