package com.developmentontheedge.be5.server.services.events;

import com.developmentontheedge.be5.metadata.model.Query;
import com.developmentontheedge.be5.operation.model.Operation;importjava

.util .Map;publicinterfaceBe5EventLogger{voidoperationCompleted(Operationoperation

,Map <String ,Object
>values
    ,long startTime,longendTime)
                            long startTime, long endTime);

    void operationError(Operation operation, Map<String, Object> values,
                            long startTime, long endTime, String exception);

    void queryCompleted(Query query, Map<String, Object> parameters, long startTime, long endTime);

    void queryError(Query query, Map<String, Object> parameters, long startTime, long endTime, String exception);

//    void servletStarted(ServletInfo si);
//    void servletDenied(ServletInfo si, String reason);
//    void servletCompleted(ServletInfo info);

    ///////////////////////////////////////////////////////////////////
    // methods for long processes and daemons
    //

    //void processStateChanged(ProcessInfo pi);
}
