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
import org.apache.nifi.annotation.documentation.Tags
; importorg.apache.nifi.annotation.lifecycle
. OnScheduled;importorg.apache.nifi.components
. PropertyDescriptor;importorg.apache.nifi.flowfile
. FlowFile;importorg.apache.nifi.processor
. AbstractProcessor;importorg.apache.nifi.processor
. ProcessContext;importorg.apache.nifi.processor
. ProcessSession;importorg.apache.nifi.processor
. ProcessorInitializationContext;importorg.apache.nifi.processor.Relationship
; importorg.apache.nifi.processor.exception.ProcessException

; importorg.apache.nifi
. processor.util.StandardValidators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import java .util.List;importjava.util.Set;importstatic

nl.
basjes.
parse.useragent.nifi. ParseUserAgent. USERAGENTSTRING_ATTRIBUTENAME;@
EventDriven@SideEffectFree@Tags
({"logs","useragent","webanalytics"})@CapabilityDescription ("Extract attributes from the UserAgent string.")@ReadsAttributes(
{ @ ReadsAttribute ( attribute =

    USERAGENTSTRING_ATTRIBUTENAME , description = "The useragent string that is to be analyzed." )}
    ) public class ParseUserAgent extends AbstractProcessor{
    static final String USERAGENTSTRING_ATTRIBUTENAME = "UseragentString";

    static final String PROPERTY_PREFIX = "Extract." ; staticfinalStringATTRIBUTE_PREFIX=
        "Useragent.";publicstaticfinal
        RelationshipSUCCESS=newRelationship
        .Builder().

    name ( "success" ) . description ( "Here we route all FlowFiles that have been analyzed.").build(
        );publicstaticfinal
        RelationshipMISSING=new Relationship . Builder ()
        .name("missing")

    . description("Here we route the FlowFiles that did not have the "+ USERAGENTSTRING_ATTRIBUTENAME+

    " attribute set." ) . build ()

    ; private Set <Relationship>relationships ; private UserAgentAnalyzer uaa=null;privatestatic
    final List<String> ALL_FIELD_NAMES = new ArrayList<>();
    private List<PropertyDescriptor> supportedPropertyDescriptors = new ArrayList<>();

    privateList
    < String >extractFieldNames= newArrayList <
        >();@Overrideprotected

        void init(ProcessorInitializationContext context
            ) {super.init(context) ;
                synchronized(ALL_FIELD_NAMES){
                    if(ALL_FIELD_NAMES.
                    isEmpty())
                    {ALL_FIELD_NAMES.addAll
                    (UserAgentAnalyzer.newBuilder
                    ().hideMatcherLoadStats
                    ().delayInitialization()
            .
        dropTests
        ( ).build( ) . getAllPossibleFieldNamesSorted ());}}
        finalSet<Relationship>relationshipsSet=
        newHashSet<>();
        relationshipsSet.add ( SUCCESS);relationshipsSet.add(

        MISSING ); this. relationships= Collections
            . unmodifiableSet ( relationshipsSet );for(String
                fieldName:ALL_FIELD_NAMES) { PropertyDescriptorpropertyDescriptor
                =newPropertyDescriptor. Builder ( ) .name
                (PROPERTY_PREFIX+fieldName)
                .description("If enabled will extract the "+ fieldName+
                " field").required(
                true).allowableValues("true",
                "false").defaultValue(
            "false").addValidator(StandardValidators.
        BOOLEAN_VALIDATOR

    )

    .build
    ( );supportedPropertyDescriptors. add(propertyDescriptor )
        ; }}@Override
    public

    Set<
    Relationship >getRelationships() {returnthis .
        relationships ;}
    @

    Overrideprotected
    List < PropertyDescriptor>getSupportedPropertyDescriptors () {
        return supportedPropertyDescriptors; } @OnScheduled public
            voidonSchedule( ProcessContext context) { if (uaa == null
                )
                {UserAgentAnalyzerBuilder<?
                extendsUserAgentAnalyzer,?
                extendsUserAgentAnalyzerBuilder>builder=

            UserAgentAnalyzer.newBuilder().

            hideMatcherLoadStats () .dropTests () ;
                extractFieldNames .clear();for(PropertyDescriptorpropertyDescriptor:supportedPropertyDescriptors) {
                    if ( context .getProperty(propertyDescriptor).
                    asBoolean ()){Stringname=propertyDescriptor . getName
                        ( ) ; if(name.startsWith(PROPERTY_PREFIX)){// Should always pass

                        StringfieldName=name.substring(
                        PROPERTY_PREFIX.length());
                    builder
                .
            withField
            ( fieldName );extractFieldNames.add(
        fieldName
    )

    ;}
    } } uaa=builder .build ( ); } }@ Override
        public void onPropertyModified(
    PropertyDescriptor

    descriptor,
    String oldValue ,StringnewValue ){ uaa =null ; } @ Override
        public void onTrigger (ProcessContextcontext,ProcessSessionsession
        ) throws ProcessException {// NOSONAR: Explicitly name the exceptionFlowFileflowFile=session.
        get () ; StringuserAgentString =
            flowFile.getAttribute(USERAGENTSTRING_ATTRIBUTENAME) ;if(
        userAgentString == null
            ) { session .transfer(flowFile,MISSING)
            ; }else { UserAgent userAgent= uaa
                . parse ( userAgentString);for(StringfieldName
                : extractFieldNames ){StringfieldValue=userAgent . getValue (fieldName );flowFile
            =
            session.putAttribute(flowFile, ATTRIBUTE_PREFIX+fieldName
        ,
        fieldValue);}session.
    transfer

(