/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.extension.file.common.api.command;

import org.mule.runtime.api.message.Message;
import org.mule.extension.file.common.api.FileAttributes;
import org.mule.extension.file.common.api.FileConnectorConfig;
import org.mule.extension.file.common.api.FileSystem;
import org.mule.extension.file.common.api.TreeNode;

import java.util.function.Predicate;

/**
 * Command design pattern for listing files
 *
 * @since 4.0
 */
public interface ListCommand {

  /**
   * Lists files under the considerations of {@link FileSystem#list(FileConnectorConfig, String, boolean, Message, Predicate)}
   *
   * @param config the config that is parameterizing this operation
   * @param directoryPath the path to the directory to be listed
   * @param recursive whether to include the contents of sub-directories
   * @param message the {@link Message} on which this operation was triggered
   * @param matcher a {@link Predicate} of {@link FileAttributes} used to filter the output list
   * @return a {@link TreeNode} object representing the listed directory
   * @throws IllegalArgumentException if {@code directoryPath} points to a file which doesn't exists or is not a directory
   */
  TreeNode list(FileConnectorConfig config, String directoryPath, boolean recursive, Message message,
                Predicate<FileAttributes> matcher);
}
