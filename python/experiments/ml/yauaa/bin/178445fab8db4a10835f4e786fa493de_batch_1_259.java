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

package nl.basjes.parse.useragent.

beam;importnl.basjes.parse.useragent.annotate
. YauaaField;importorg.apache.beam.sdk.coders
. StringUtf8Coder;importorg.apache.beam.sdk.testing
. PAssert;importorg.apache.beam.sdk.testing
. TestPipeline;importorg.apache.beam.sdk.transforms
. Create;importorg.apache.beam.sdk.transforms
. DoFn;importorg.apache.beam.sdk.transforms
. ParDo;importorg.apache.beam.sdk.values
. PCollection;importorg.junit
. Rule;importorg.junit

. Test;importjava.io
. Serializable;importjava.util
. Arrays;importjava.util


. List ; public class TestUserAgentAnalysisDoFnInline

    implementsSerializable
    { @ Rule public final transient TestPipelinepipeline=TestPipeline.create

    ()
    ; @ Testpublicvoid testInlineDefinition
        (){List < String >useragents=Arrays
            . asList
                ( "Mozilla/5.0 (X11; Linux x86_64) "
                +"AppleWebKit/537.36 (KHTML, like Gecko) "

            + "Chrome/48.0.2564.82 Safari/537.36"
                , "Mozilla/5.0 (Linux; Android 7.0; Nexus 6 Build/NBD90Z) "
                +
        "AppleWebKit/537.36 (KHTML, like Gecko) "+

        "Chrome/53.0.2785.124 Mobile Safari/537.36"
        );// Apply Create, passing the list and the coder, to create the PCollection.PCollection < String >input=pipeline.apply(Create.of(useragents)).setCoder(StringUtf8Coder.of(

        ));PCollection < TestRecord >
            testRecords=input.apply
                ("Create testrecords from input",ParDo. of(newDoFn <String,TestRecord >
                    ()
                    { @ ProcessElementpublicvoid processElement( ProcessContext
                        c){c. output(newTestRecord(c.element()
                    )
                );}}

        ));PCollection < TestRecord >
            filledTestRecords=testRecords.apply
                ("Extract Elements from Useragent",ParDo. of(newUserAgentAnalysisDoFn<TestRecord> ( 15000
                    ) { // Setting the cacheSizepublicString getUserAgentString( TestRecord
                        record ){returnrecord
                    .

                    useragent;}@YauaaField
                    ( "DeviceClass" )publicvoid setDeviceClass( TestRecord record, String
                        value){ record .deviceClass
                    =

                    value;}@YauaaField
                    ( "AgentNameVersion" )publicvoid setAgentNameVersion( TestRecord record, String
                        value){ record .agentNameVersion
                    =
                value;}}

        ) ) ; TestRecord expectedRecord1=newTestRecord(useragents.get(0
        )); expectedRecord1 .deviceClass
        ="Desktop"; expectedRecord1 .agentNameVersion

        = "Chrome 48.0.2564.82" ; TestRecord expectedRecord2=newTestRecord(useragents.get(1
        )); expectedRecord2 .deviceClass
        ="Phone"; expectedRecord2 .agentNameVersion

        ="Chrome 53.0.2785.124";PAssert.that(filledTestRecords).containsInAnyOrder (expectedRecord1,

        expectedRecord2);pipeline.run().waitUntilFinish
    (

)