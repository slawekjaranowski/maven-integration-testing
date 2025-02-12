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
 * This is a test set for <a href="https://issues.apache.org/jira/browse/MNG-3970">MNG-3970</a>.
 *
 * @author Benjamin Bentmann
 *
 */
public class MavenITmng3970DepResolutionFromProfileReposTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3970DepResolutionFromProfileReposTest()
    {
        super( ALL_MAVEN_VERSIONS );
    }

    /**
     * Test that dependencies can be resolved from remote repositories defined by (active) profiles in profiles.xml.
     *
     * @throws Exception in case of failure
     */
    public void testitFromProfilesXml()
        throws Exception
    {
        // support for profiles.xml removed from 3.x (see MNG-4060)
        requiresMavenVersion( "[2.0,3.0-alpha-1)" );

        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3970/test-1" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3970" );
        verifier.filterFile( "profiles.xml", "profiles.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.verifyArtifactPresent( "org.apache.maven.its.mng3970", "a", "0.1", "jar" );
    }

    /**
     * Test that dependencies can be resolved from remote repositories defined by (active) profiles in the POM.
     *
     * @throws Exception in case of failure
     */
    public void testitFromPom()
        throws Exception
    {
        requiresMavenVersion( "[2.0,3.0-alpha-1),[3.0-beta-1,)" );

        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3970/test-2" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3970" );
        verifier.filterFile( "pom.xml", "pom.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.verifyArtifactPresent( "org.apache.maven.its.mng3970", "a", "0.1", "jar" );
    }

    /**
     * Test that dependencies can be resolved from remote repositories defined by (active) profiles in settings.xml.
     *
     * @throws Exception in case of failure
     */
    public void testitFromSettings()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3970/test-3" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteArtifacts( "org.apache.maven.its.mng3970" );
        verifier.filterFile( "settings.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );
        verifier.addCliOption( "--settings" );
        verifier.addCliOption( "settings.xml" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        verifier.verifyArtifactPresent( "org.apache.maven.its.mng3970", "a", "0.1", "jar" );
    }

}
