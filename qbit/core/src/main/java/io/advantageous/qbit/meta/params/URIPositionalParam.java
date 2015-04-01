/*
 * Copyright (c) 2015. Rick Hightower, Geoff Chandler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * QBit - The Microservice lib for Java : JSON, WebSocket, REST. Be The Web!
 */
package io.advantageous.qbit.meta.params;

public class URIPositionalParam extends PositionalParam {

    private int indexIntoURI;

    public URIPositionalParam(final boolean required, final int position,
                              final Object defaultValue, int indexIntoURI){
        super(required, position, defaultValue, ParamType.PATH_BY_POSITION);
        this.indexIntoURI = indexIntoURI;
    }

    public int getIndexIntoURI() {
        return indexIntoURI;
    }
}