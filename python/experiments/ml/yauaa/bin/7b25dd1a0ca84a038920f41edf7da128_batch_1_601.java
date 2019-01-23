/*
 * Yet Another UserAgent Analyzer
 * Copyright (C) 2013-2018 Niels Basjes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.basjes.parse.useragent.nifi;

import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;
import nl.basjes.parse.useragent.UserAgentAnalyzer.UserAgentAnalyzerBuilder;
import org.apache.nifi.annotation.behavior.EventDriven;
import org.apache.nifi.annotation.behavior.ReadsAttribute;
import org.apache.nifi.annotation.behavior.ReadsAttributes;
import org.apache.nifi.annotation.behavior.SideEffectFree;
import org.apache.nifi.annotation.documentation.CapabilityDescription;
import org.apache.nifi.annotation.documentation.Tags;
import org.apache.nifi.annotation.lifecycle.OnScheduled;
import org.apache.nifi.components.PropertyDescriptor;
import org.apache.nifi.flowfile.FlowFile;
import org.apache.nifi.processor.AbstractProcessor;
import org.apache.nifi.processor.ProcessContext;
import org.apache.nifi.processor.ProcessSession;
import org.apache.nifi.processor.ProcessorInitializationContext;
import org.apache.nifi.processor.Relationship;
import org.apache.nifi.processor.exception.ProcessException;
import org.apache.nifi.processor.util.StandardValidators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static nl.basjes.parse.useragent.nifi.ParseUserAgent.USERAGENTSTRING_ATTRIBUTENAME;

@EventDriven
@SideEffectFree
@Tags({"logs", "useragent", "webanalytics"})
@CapabilityDescription( "Extract attributes from the UserAgent string.")@ReadsAttributes({
@ ReadsAttribute ( attribute = USERAGENTSTRING_ATTRIBUTENAME

    , description = "The useragent string that is to be analyzed." ) })
    public class ParseUserAgent extends AbstractProcessor {static
    final String USERAGENTSTRING_ATTRIBUTENAME = "UseragentString" ;static

    final String PROPERTY_PREFIX = "Extract." ; static finalStringATTRIBUTE_PREFIX="Useragent."
        ;publicstaticfinalRelationship
        SUCCESS=newRelationship.
        Builder().name

    ( "success" ) . description ( "Here we route all FlowFiles that have been analyzed." ).build()
        ;publicstaticfinalRelationship
        MISSING=newRelationship . Builder ( ).
        name("missing").

    description ("Here we route the FlowFiles that did not have the "+USERAGENTSTRING_ATTRIBUTENAME +" attribute set."

    ) . build ( );

    private Set < Relationship>relationships; private UserAgentAnalyzer uaa =null;privatestaticfinal
    List <String>ALL_FIELD_NAMES = new ArrayList <>();private
    List <PropertyDescriptor>supportedPropertyDescriptors = new ArrayList <>();private

    List<
    String > extractFieldNames=new ArrayList< >
        ();@Overrideprotectedvoid

        init (ProcessorInitializationContextcontext )
            { super.init(context); synchronized
                (ALL_FIELD_NAMES){if
                    (ALL_FIELD_NAMES.isEmpty
                    ()){
                    ALL_FIELD_NAMES.addAll(
                    UserAgentAnalyzer.newBuilder(
                    ).hideMatcherLoadStats(
                    ).delayInitialization().
            dropTests
        (
        ) .build() . getAllPossibleFieldNamesSorted ( ));}}final
        Set<Relationship>relationshipsSet=new
        HashSet<>();relationshipsSet
        .add( SUCCESS );relationshipsSet.add(MISSING

        ) ;this .relationships =Collections .
            unmodifiableSet ( relationshipsSet ) ;for(StringfieldName
                :ALL_FIELD_NAMES){ PropertyDescriptor propertyDescriptor=
                newPropertyDescriptor.Builder ( ) . name(
                PROPERTY_PREFIX+fieldName).
                description("If enabled will extract the "+fieldName +" field"
                ).required(true
                ).allowableValues("true","false"
                ).defaultValue("false"
            ).addValidator(StandardValidators.BOOLEAN_VALIDATOR
        )

    .

    build(
    ) ;supportedPropertyDescriptors.add (propertyDescriptor) ;
        } }@Overridepublic
    Set

    <Relationship
    > getRelationships(){ returnthis. relationships
        ; }@
    Override

    protectedList
    < PropertyDescriptor >getSupportedPropertyDescriptors( ){ return
        supportedPropertyDescriptors ;} @ OnScheduledpublic void
            onSchedule(ProcessContext context ){ if ( uaa== null )
                {
                UserAgentAnalyzerBuilder<?extends
                UserAgentAnalyzer,?extends
                UserAgentAnalyzerBuilder>builder=UserAgentAnalyzer

            .newBuilder().hideMatcherLoadStats

            ( ). dropTests( ); extractFieldNames
                . clear();for(PropertyDescriptorpropertyDescriptor:supportedPropertyDescriptors){ if
                    ( context . getProperty(propertyDescriptor).asBoolean
                    ( )){Stringname=propertyDescriptor. getName (
                        ) ; if (name.startsWith(PROPERTY_PREFIX)){// Should always passString

                        fieldName=name.substring(PROPERTY_PREFIX
                        .length());builder
                    .
                withField
            (
            fieldName ) ;extractFieldNames.add(fieldName
        )
    ;

    }}
    } uaa =builder. build( ) ;} } @Override public
        void onPropertyModified (PropertyDescriptor
    descriptor

    ,String
    oldValue , StringnewValue) {uaa = null; } @ Override public
        void onTrigger ( ProcessContextcontext,ProcessSessionsession)
        throws ProcessException { // NOSONAR: Explicitly name the exceptionFlowFileflowFile=session.get
        ( ); String userAgentString= flowFile
            .getAttribute(USERAGENTSTRING_ATTRIBUTENAME); if(userAgentString
        == null )
            { session . transfer(flowFile,MISSING);
            } else{ UserAgent userAgent =uaa .
                parse ( userAgentString );for(StringfieldName:
                extractFieldNames ) {StringfieldValue=userAgent. getValue ( fieldName) ;flowFile=
            session
            .putAttribute(flowFile,ATTRIBUTE_PREFIX +fieldName,
        fieldValue
        );}session.transfer
    (

flowFile