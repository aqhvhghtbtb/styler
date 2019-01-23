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

package org.apache.kylin.rest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kylin.common.util.JsonUtil;
import org.apache.kylin.common.util.RandomUtil;
import org.apache.kylin.metadata.

model .ExternalFilterDesc;importorg.apache.kylin.rest.
request .ExternalFilterRequest;importorg.apache.kylin.rest.
service .ExtFilterService;importorg.slf4j.Logger;importorg
. slf4j.LoggerFactory;importorg
. springframework.beans.factory.
annotation .Autowired;importorg.springframework.beans.factory.
annotation .Qualifier;importorg.springframework.stereotype.Controller;
import org.springframework.web.bind.
annotation .PathVariable;importorg.springframework.web.bind.
annotation .RequestBody;importorg.springframework.web.bind.
annotation .RequestMapping;importorg.springframework.web.bind.
annotation .RequestMethod;importorg.springframework.web.bind.
annotation .RequestParam;importorg.springframework.web.bind.
annotation .ResponseBody;importcom.google.common.collect.

Lists ;/**
 * @author jiazhong
 */@Controller@RequestMapping(value="/extFilter"

)
publicclass
ExternalFilterControllerextendsBasicController{ private staticfinal
Logger logger = LoggerFactory . getLogger
    ( ExternalFilterController . class ) ; @Autowired@Qualifier("extFilterService")privateExtFilterService

    extFilterService;
    @RequestMapping(value=
    "/saveExtFilter" , method=

    {RequestMethod.POST } ,produces = { "application/json" })@ ResponseBodypublic Map < String , String>
    saveExternalFilter(
    @ RequestBodyExternalFilterRequestrequest) throwsIOException {Map<String , String> result = new
        HashMap(); StringfilterProject = request . getProject();
        ExternalFilterDesc desc = JsonUtil.readValue(request.
        getExtFilter ( ) ,ExternalFilterDesc.class);desc.setUuid( RandomUtil.randomUUID()
        .toString());extFilterService.saveExternalFilter(desc);extFilterService.
        syncExtFilterToProject(newString[]{
        desc.getName() },filterProject ) ;result.put( "success", "true");
        returnresult;}@RequestMapping (value=
        "/updateExtFilter" ,method
    =

    {RequestMethod.PUT } ,produces = { "application/json" })@ ResponseBodypublic Map < String , String>
    updateExternalFilter(
    @ RequestBodyExternalFilterRequestrequest) throwsIOException {Map<String , String> result = new
        HashMap(); ExternalFilterDescdesc = JsonUtil . readValue(request.
        getExtFilter ( ) ,ExternalFilterDesc.class);extFilterService.updateExternalFilter( desc);extFilterService.
        syncExtFilterToProject(newString[]{
        desc.getName() },request . getProject()); result. put("success","true");
        returnresult;}@RequestMapping (value=
        "/{filter}/{project}" ,method
    =

    {RequestMethod.DELETE } ,produces = { "application/json" })@ ResponseBodypublic Map < String , String>
    removeFilter(
    @ PathVariableStringfilter, @PathVariable Stringproject)throws IOException {Map <String , String> result = new
        HashMap<String, String> ( ) ; extFilterService.removeExtFilterFromProject( filter,project);
        extFilterService.removeExternalFilter(filter) ;result.
        put("success","true");
        returnresult;}@RequestMapping (value=
        "" ,method
    =

    {RequestMethod.GET } ,produces = { "application/json" })@ ResponseBodypublic List < ExternalFilterDesc > getExternalFilters(
    @RequestParam
    ( value="project", required=true)Stringproject ) throwsIOException { List <ExternalFilterDesc > filterDescs= Lists . newArrayList
        ();filterDescs . addAll (extFilterService.getProjectManager()
        .listExternalFilterDescs(project).values());returnfilterDescs;}}