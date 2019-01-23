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

analyze ;importnl.basjes.parse.useragent.analyze.WordRangeVisitor.
Range ;importorg.antlr.v4.runtime.tree.

ParseTree ;importjava.io.
Serializable ;importjava.util.

Set ; public interface Analyzer extends
    Serializable {voidinform (String path ,String value ,ParseTreectx

    ) ;voidinformMeAbout (MatcherAction matcherAction ,StringkeyPattern

    ) ;voidlookingForRange (String treeName ,Rangerange

    );Set< Range>getRequiredInformRanges (StringtreeName

    ) ;voidinformMeAboutPrefix (MatcherAction matcherAction ,String treeName ,Stringprefix

    );Set< Integer>getRequiredPrefixLengths (StringtreeName

)