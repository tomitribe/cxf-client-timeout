/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.tomitribe.cxf;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.ClientLifeCycleListener;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class TimeoutSetting implements ClientLifeCycleListener {
    @Override
    public void clientCreated(final Client client) {
        if (client.getConduit() instanceof HTTPConduit) {
            final HTTPConduit conduit = (HTTPConduit) client.getConduit();

            HTTPClientPolicy clientPolicy = conduit.getClient();
            if (clientPolicy == null) {
                clientPolicy = new HTTPClientPolicy();
                conduit.setClient(clientPolicy);
            }

            try {
                clientPolicy.setConnectionTimeout(Long.parseLong(System.getProperty("cxf.http.connection.timeout", "30000")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            try {
                clientPolicy.setReceiveTimeout(Long.parseLong(System.getProperty("cxf.http.receive.timeout", "60000")));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void clientDestroyed(final Client client) {
        // no-op
    }
}
