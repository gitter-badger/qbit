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

package io.advantageous.qbit.metrics.support;


import io.advantageous.qbit.metrics.StatReplicator;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by rhightower on 1/28/15.
 */
public class DebugReplicator implements StatReplicator {

    public AtomicInteger count = new AtomicInteger();

    public boolean out = false;

    public DebugReplicator(boolean out) {
        this.out = out;
    }

    public DebugReplicator() {
    }


    @Override
    public void replicateCount(String name, int count, long now) {
        this.count.addAndGet(count);
        if (out) System.out.println("DEBUG REPLICATOR" + name + count + now + this.count.get());
    }


    @Override
    public void replicateLevel(String name, int level, long now) {
        this.count.set(level);
        if (out) System.out.println("DEBUG REPLICATOR" + name + count + now + this.count.get());
    }

    @Override
    public void replicateTiming(String name, int level, long time) {
        this.count.set(level);
        if (out) System.out.println("DEBUG REPLICATOR" + name + count + time + this.count.get());

    }
}
