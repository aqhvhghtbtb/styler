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
import org.apache.kylin.metadata.model.ExternalFilterDesc;
import org.apache.kylin.rest.request.ExternalFilterRequest;
import org.apache.kylin.rest.service.ExtFilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.
annotation .RequestMapping;importorg
. springframework.web.bind.annotation.RequestMethod;importorg
. springframework.web.bind.annotation.RequestParam;importorg
. springframework.web.bind.annotation.ResponseBody;importcom

. google.common.collect.Lists;/**
 * @author jiazhong
 */@

Controller
@RequestMapping
(value="/extFilter" ) publicclass
ExternalFilterController extends BasicController { private static
    final Logger logger = LoggerFactory . getLogger(ExternalFilterController.class);@Autowired

    @Qualifier
    ("extFilterService")privateExtFilterService
    extFilterService ; @RequestMapping

    (value="/saveExtFilter" , method= { RequestMethod . POST}, produces= { "application/json" } ) @ResponseBody
    publicMap
    < String,String> saveExternalFilter( @RequestBodyExternalFilterRequestrequest ) throwsIOException { Map <
        String,String> result= new HashMap ( );StringfilterProject
        = request . getProject();ExternalFilterDescdesc
        = JsonUtil . readValue(request.getExtFilter(),ExternalFilterDesc. class);desc.
        setUuid(RandomUtil.randomUUID().toString());extFilterService.
        saveExternalFilter(desc);extFilterService.
        syncExtFilterToProject(newString[ ]{desc . getName()}, filterProject) ;result.
        put("success","true") ;returnresult
        ; }@
    RequestMapping

    (value="/updateExtFilter" , method= { RequestMethod . PUT}, produces= { "application/json" } ) @ResponseBody
    publicMap
    < String,String> updateExternalFilter( @RequestBodyExternalFilterRequestrequest ) throwsIOException { Map <
        String,String> result= new HashMap ( );ExternalFilterDescdesc
        = JsonUtil . readValue(request.getExtFilter(),ExternalFilterDesc. class);extFilterService.
        updateExternalFilter(desc);extFilterService.
        syncExtFilterToProject(newString[ ]{desc . getName()}, request. getProject());result.
        put("success","true") ;returnresult
        ; }@
    RequestMapping

    (value="/{filter}/{project}" , method= { RequestMethod . DELETE}, produces= { "application/json" } ) @ResponseBody
    publicMap
    < String,String> removeFilter( @PathVariableStringfilter , @PathVariable Stringproject ) throwsIOException { Map <
        String,String> result= new HashMap < String,String> ();extFilterService.
        removeExtFilterFromProject(filter,project) ;extFilterService.
        removeExternalFilter(filter);result.
        put("success","true") ;returnresult
        ; }@
    RequestMapping

    (value="" , method= { RequestMethod . GET}, produces= { "application/json" } ) @ResponseBody
    publicList
    < ExternalFilterDesc>getExternalFilters( @RequestParam(value="project" , required= true ) Stringproject ) throwsIOException { List <
        ExternalFilterDesc>filterDescs= Lists . newArrayList();filterDescs.
        addAll(extFilterService.getProjectManager().listExternalFilterDescs(project).values());returnfilterDescs
        ; }}
    