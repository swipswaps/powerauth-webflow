/*
 * Copyright 2019 Wultra s.r.o.
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
package io.getlime.security.powerauth.lib.dataadapter.model.response;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Response for an anti-fraud system call.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class AfsResponse {

    /**
     * Whether AFS response should be applied in current authentication step.
     */
    private boolean afsResponseApplied = false;

    /**
     * AFS label specifying factors to be used during this authentication step.
     */
    private String afsLabel;

    /**
     * Configuration of authentication options available for the user.
     */
    private AuthStepOptions authStepOptions = new AuthStepOptions();

    /**
     * Extra parameters sent with the response which should be persisted together with the operation.
     */
    private final Map<String, Object> extras = new LinkedHashMap<>();

    /**
     * Default constructor.
     */
    public AfsResponse() {
    }

    /**
     * Constructor with all details.
     * @param afsResponseApplied Whether AFS response should be applied in Web Flow.
     * @param afsLabel AFS label to be stored with the operation.
     * @param authStepOptions Authentication step options for current step. Use null value case afsResponseApplied = false.
     * @param extras AFS extras.
     */
    public AfsResponse(boolean afsResponseApplied, String afsLabel, AuthStepOptions authStepOptions, Map<String, Object> extras) {
        this.afsResponseApplied = afsResponseApplied;
        this.afsLabel = afsLabel;
        this.authStepOptions = authStepOptions;
        this.extras.putAll(extras);
    }

    /**
     * Get whether AFS response should be applied in current authentication step.
     * @return Whether AFS response should be applied in current authentication step.
     */
    public boolean isAfsResponseApplied() {
        return afsResponseApplied;
    }

    /**
     * Set whether AFS response should be applied in current authentication step.
     * @param afsResponseApplied Whether AFS response should be applied in current authentication step.
     */
    public void setAfsResponseApplied(boolean afsResponseApplied) {
        this.afsResponseApplied = afsResponseApplied;
    }

    /**
     * Get AFS label specifying factors to be used during this authentication step.
     * @return AFS label.
     */
    public String getAfsLabel() {
        return afsLabel;
    }

    /**
     * Set AFS label specifying factors to be used during this authentication step.
     * @param afsLabel AFS label.
     */
    public void setAfsLabel(String afsLabel) {
        this.afsLabel = afsLabel;
    }

    /**
     * Get authentication step options available for the user.
     * @return Authentication step options available for the user.
     */
    public AuthStepOptions getAuthStepOptions() {
        return authStepOptions;
    }

    /**
     * Set authentication step options available for the user.
     * @param authStepOptions Authentication step options available for the user.
     */
    public void setAuthStepOptions(AuthStepOptions authStepOptions) {
        this.authStepOptions = authStepOptions;
    }

    /**
     * Get extra parameters sent with the response which should be persisted together with the operation.
     * @return Extra parameters.
     */
    public Map<String, Object> getExtras() {
        return extras;
    }
}
