/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.rest.init;

import java.io.File;

import org.apache.kylin.
common .KylinConfig;importorg.apache.kylin.
common .KylinVersion;importorg.
slf4j .Logger;importorg.

slf4j . LoggerFactory ; public class

    ClientInfoTask extends InitialTask { private static finalLoggerlogger=LoggerFactory.getLogger(ClientInfoTask

    .class
    ) ; @Overridepublic void
        execute(){logger.info(getClientDetailInformation
    (

    ) ) ; }publicstatic String
        getClientDetailInformation ( ) { StringBuilderbuf=new

        StringBuilder();buf.append("kylin.home: ") .append(newFile(KylinConfig.getKylinHome()).getAbsolutePath()).append
        ("\n");buf.append("kylin.version:").append(KylinVersion.getCurrentVersion()).append
        ("\n");buf.append("commit:").append(KylinVersion.getGitCommitInfo()).append
        ("\n");buf.append("os.name:").append(System.getProperty("os.name")).append
        ("\n");buf.append("os.arch:").append(System.getProperty("os.arch")).append
        ("\n");buf.append("os.version:").append(System.getProperty("os.version")).append
        ("\n");buf.append("java.version:").append(System.getProperty("java.version")).append
        ("\n");buf.append("java.vendor:").append(System.getProperty(

        "java.vendor" ));returnbuf.
    toString
(