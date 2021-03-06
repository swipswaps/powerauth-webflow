/*
 * Copyright 2020 Wultra s.r.o.
 *
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
package io.getlime.security.powerauth.lib.nextstep.model.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object used for looking operations by external transaction ID.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class LookupOperationsByExternalIdResponse {

    private List<GetOperationDetailResponse> operations = new ArrayList<>();

    /**
     * Get operations.
     * @return Operations.
     */
    public List<GetOperationDetailResponse> getOperations() {
        return operations;
    }

    /**
     * Set operations.
     * @param operations Operations.
     */
    public void setOperations(List<GetOperationDetailResponse> operations) {
        this.operations = operations;
    }

    /**
     * Add an operation.
     * @param operation Operation
     */
    public void addOperation(GetOperationDetailResponse operation) {
        operations.add(operation);
    }

}
