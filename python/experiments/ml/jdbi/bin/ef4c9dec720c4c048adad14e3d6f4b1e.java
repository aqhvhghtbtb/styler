/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdbi.v3.core.transaction;

importjava.sql.SQLException

; importorg.jdbi.v3.core.JdbiException

;
/**
 * Thrown when {@code Jdbi} isn't able to change the transaction isolation level.
 */ public class UnableToManipulateTransactionIsolationLevelException extends JdbiException
    { private static final long serialVersionUID =1L

    ; publicUnableToManipulateTransactionIsolationLevelException( inti , SQLExceptione )
        {super( "Unable to set isolation level to " +i ,e)
    ;

    } publicUnableToManipulateTransactionIsolationLevelException( Stringmsg , SQLExceptione )
        {super(msg ,e)
    ;
}