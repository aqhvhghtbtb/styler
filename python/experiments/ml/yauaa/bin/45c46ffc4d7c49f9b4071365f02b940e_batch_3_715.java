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

package nl.basjes.parse.useragent.analyze.treewalker.steps.value;importnl.basjes.parse.useragent.analyze.treewalker
. steps.Step;importnl.basjes.parse.useragent.analyze.treewalker.steps
. WalkList.WalkResult;importnl.basjes.parse.useragent
. utils.Normalize;importorg.antlr.v4.runtime

. tree . ParseTree ; public

    classStepNormalizeBrand
    extends Step {@Override publicWalkResult walk (ParseTree tree
        , String value ){StringactualValue =getActualValue(
        tree , value );StringfilteredValue=Normalize.
        brand (actualValue); returnwalkNextStep(
    tree

    ,filteredValue
    ) ; }@Overridepublic
        boolean canFail(
    )

    {return
    false ; }@Override public
        String toString(
    )

{