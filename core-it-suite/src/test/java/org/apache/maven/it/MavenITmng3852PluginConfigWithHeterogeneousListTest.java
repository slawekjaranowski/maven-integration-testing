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

import org.apache.maven.it.Verifier;
import org.apache.maven.it.util.ResourceExtractor;

import java.io.File;
import java.util.Properties;

/**
 * This is a test set for <a href="http://jira.codehaus.org/browse/MNG-3852">MNG-3852</a>.
 * 
 * @author Benjamin Bentmann
 * @version $Id$
 */
public class MavenITmng3852PluginConfigWithHeterogeneousListTest
    extends AbstractMavenIntegrationTestCase
{

    public MavenITmng3852PluginConfigWithHeterogeneousListTest()
    {
    }

    /**
     * Verify that list-valued plugin parameters respect the ordering of their elements as given in the POM, even
     * if these elements have different names.
     */
    public void testitMNG3852()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-3852" );

        Verifier verifier = new Verifier( testDir.getAbsolutePath() );
        verifier.setAutoclean( false );
        verifier.deleteDirectory( "target" );
        verifier.executeGoal( "validate" );
        verifier.verifyErrorFreeLog();
        verifier.resetStreams();

        Properties props = verifier.loadProperties( "target/plugin-config.properties" );

        assertEquals( "5", props.getProperty( "listParam" ) );
        assertEquals( "one", props.getProperty( "listParam.0" ) );
        assertEquals( "two", props.getProperty( "listParam.1" ) );
        assertEquals( "three", props.getProperty( "listParam.3" ) );
        assertEquals( "four", props.getProperty( "listParam.4" ) );
        assertEquals( "org.apache.maven.plugin.coreit.Bean", props.getProperty( "listParam.2" ).substring( 0, 35 )  );
    }

}