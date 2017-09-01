/**
 * Copyright (C) 2011-2017 Arm Limited. All rights reserved.
 * SPDX-License-Identifier: Apache-2.0
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
package com.mbed.lwm2m;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LWM2MObject {

    private final LWM2MID id;
    private final List<LWM2MObjectInstance> instances;

    public LWM2MObject(LWM2MID id, List<LWM2MObjectInstance> instances) {
        if (id != null) {
            this.id = id;
            this.instances = new ArrayList<>(instances);
        } else {
            throw new NullPointerException("LWM2MID");
        }
    }

    public LWM2MObject(LWM2MID id, LWM2MObjectInstance... instances) {
        this(id, Arrays.asList(instances));
    }

    public LWM2MObject(List<LWM2MObjectInstance> instances) {
        this(new LWM2MID(null), instances);
    }

    public LWM2MObject(LWM2MObjectInstance... instances) {
        this(Arrays.asList(instances));
    }

    public LWM2MID getId() {
        return id;
    }

    public List<LWM2MObjectInstance> getInstances() {
        return instances;
    }

    public LWM2MObjectInstance getInstanceFor(LWM2MID instanceId) {
        for (LWM2MObjectInstance instance : instances) {
            if (instance.getId().equals(instanceId)) {
                return instance;
            }
        }
        return null;
    }

    public void addInstance(LWM2MObjectInstance newInstance) {
        instances.add(newInstance);
    }

    public void addInstances(List<LWM2MObjectInstance> newInstances) {
        instances.addAll(newInstances);
    }

    @Override
    public String toString() {
        return "LWM2MObject [id:" + id + ", instances: " + instances.toString() + "]";
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((instances == null) ? 0 : instances.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        LWM2MObject other = (LWM2MObject) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (instances == null) {
            if (other.instances != null) {
                return false;
            }
        } else if (!Arrays.deepEquals(instances.toArray(), other.instances.toArray())) {
            return false;
        }
        return true;
    }

}