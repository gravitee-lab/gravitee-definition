/**
 * Copyright (C) 2015 The Gravitee team (http://gravitee.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.gravitee.definition.jackson.datatype.api.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import io.gravitee.definition.model.Endpoint;
import io.gravitee.definition.model.EndpointGroup;

import java.io.IOException;
import java.util.Set;

/**
 * @author David BRASSELY (david.brassely at graviteesource.com)
 * @author GraviteeSource Team
 */
public class EndpointGroupSerializer extends StdScalarSerializer<EndpointGroup> {

    public EndpointGroupSerializer(Class<EndpointGroup> vc) {
        super(vc);
    }

    @Override
    public void serialize(EndpointGroup group, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeStringField("name", group.getName());


        final Set<Endpoint> endpoints = group.getEndpoints();

        if (endpoints != null) {
            jgen.writeArrayFieldStart("endpoints");
            endpoints.forEach(endpoint -> {
                try {
                    jgen.writeObject(endpoint);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            jgen.writeEndArray();
        }

        if (group.getLoadBalancer() != null) {
            jgen.writeObjectField("load_balancing", group.getLoadBalancer());
        }

        jgen.writeEndObject();
    }
}