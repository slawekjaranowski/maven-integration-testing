package org.apache.maven.it;

import java.io.File;
import java.util.Properties;

import org.apache.maven.it.util.ResourceExtractor;

public class MavenITmng5774ConfigurationProcessorsTest
    extends AbstractMavenIntegrationTestCase
{
    public MavenITmng5774ConfigurationProcessorsTest()
    {
        super( "(3.2.5,)" );
    }

    public void testBehaviourWhereThereIsOneUserSuppliedConfigurationProcessor()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-5774-configuration-processors" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.filterFile( "settings-template.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );

        verifier = newVerifier( new File( testDir, "build-with-one-processor-valid" ).getAbsolutePath() );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.it-configuration-processors" );
        verifier.getCliOptions().add( "-s" );
        verifier.getCliOptions().add( new File( testDir, "settings.xml" ).getAbsolutePath() );
        verifier.executeGoal( "process-resources" );
        verifier.verifyErrorFreeLog();
        // Making sure our configuration processor executes
        verifier.verifyTextInLog( "[INFO] ConfigurationProcessorOne.process()" );
        // We have a property value injected by our configuration processor. Make sure it's correct
        verifier.verifyFilePresent( "target/classes/result.properties" );
        Properties result = verifier.loadProperties( "target/classes/result.properties" );
        assertEquals( "yes", result.getProperty( "configurationProcessorContributedValue" ) );
        verifier.resetStreams();
    }

    public void testBehaviourWhereThereAreTwoUserSuppliedConfigurationProcessor()
        throws Exception
    {
        File testDir = ResourceExtractor.simpleExtractResources( getClass(), "/mng-5774-configuration-processors" );

        Verifier verifier = newVerifier( testDir.getAbsolutePath() );
        verifier.filterFile( "settings-template.xml", "settings.xml", "UTF-8", verifier.newDefaultFilterProperties() );

        verifier = newVerifier( new File( testDir, "build-with-two-processors-invalid" ).getAbsolutePath() );
        verifier.deleteDirectory( "target" );
        verifier.deleteArtifacts( "org.apache.maven.its.it-configuration-processors" );
        verifier.getCliOptions().add( "-s" );
        verifier.getCliOptions().add( new File( testDir, "settings.xml" ).getAbsolutePath() );
        try
        {
            verifier.executeGoal( "process-resources" );
            fail( "We expected this invocation to fail because of too many user supplied configuration processors being present" );
        }
        catch ( VerificationException e )
        {
            verifier.verifyTextInLog( "There can only be one user supplied ConfigurationProcessor" );
        }
        verifier.resetStreams();
    }
}
