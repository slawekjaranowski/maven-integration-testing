package org.apache.maven.it;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;

/**
 * This is a test set for <a href="https://issues.apache.org/jira/browse/MNG-781">MNG-781</a>.
 *
 * @author John Casey
 *
 */
public class MavenITmng0781PluginConfigVsExecConfigTest
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng0781PluginConfigVsExecConfigTest()
    {
        super( ALL_MAVEN_VERSIONS );
    }

    /**
     * Test that plugin-level configuration instances are not nullified by
     * execution-level configuration instances.
     *
     * @throws Exception in case of failure
     */
    public void testitMNG0781()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-0781" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "process-resources" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.verifyFilePresent( "target/exec-level.txt" );
        verifier.verifyFilePresent( "target/resources-resources.txt" );
    }

}
