/*
 * Copyright 2017 Wultra s.r.o.
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

import io.getlime.security.powerauth.lib.nextstep.model.entity.*;
import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.UserAccountStatus;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthMethod;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Response object used for getting the operation detail.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
public class GetOperationDetailResponse {

    private String operationId;
    private String operationName;
    private String userId;
    private String organizationId;
    private UserAccountStatus accountStatus;
    private String externalTransactionId;
    private AuthResult result;
    private Date timestampCreated;
    private Date timestampExpires;
    private String operationData;
    private List<AuthStep> steps;
    private List<OperationHistory> history;
    private List<AfsActionDetail> afsActions;
    private OperationFormData formData;
    private AuthMethod chosenAuthMethod;
    private Integer remainingAttempts;
    private ApplicationContext applicationContext;

    /**
     * Default constructor.
     */
    public GetOperationDetailResponse() {
        steps = new ArrayList<>();
        history = new ArrayList<>();
        afsActions = new ArrayList<>();
    }

    /**
     * Get operation ID.
     * @return Operation ID.
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * Set operation ID.
     * @param operationId Operation ID.
     */
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    /**
     * Get operation name.
     * @return Operation name.
     */
    public String getOperationName() {
        return operationName;
    }

    /**
     * Set operation name.
     * @param operationName Operation name.
     */
    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    /**
     * Get user ID of the user who is associated with the operation.
     * @return User ID.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Set user ID of the user who is associated with the operation.
     * @param userId User ID.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Get organization ID.
     * @return Organization ID.
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * Set organization ID.
     * @param organizationId Organization ID.
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    /**
     * Get current user account status.
     * @return User account status.
     */
    public UserAccountStatus getAccountStatus() {
        return accountStatus;
    }

    /**
     * Set current user account status.
     * @param accountStatus User account status.
     */
    public void setAccountStatus(UserAccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Get external transaction ID.
     * @return External transaction ID.
     */
    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    /**
     * Set external transaction ID.
     * @param externalTransactionId External transaction ID.
     */
    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    /**
     * Get the authentication step result.
     * @return Authentication step result.
     */
    public AuthResult getResult() {
        return result;
    }

    /**
     * Set the authentication step result.
     * @param result Authentication step result.
     */
    public void setResult(AuthResult result) {
        this.result = result;
    }

    /**
     * Get the timestamp of when the operation was created.
     * @return Timestamp when operation was created.
     */
    public Date getTimestampCreated() {
        return timestampCreated;
    }

    /**
     * Set the timestamp of when the operation was created.
     * @param timestampCreated Timestamp when operation was created.
     */
    public void setTimestampCreated(Date timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    /**
     * Get the timestamp of when the operation expires.
     * @return Timestamp when operation expires.
     */
    public Date getTimestampExpires() {
        return timestampExpires;
    }

    /**
     * Set the timestamp of when the operation expires.
     * @param timestampExpires Timestamp when operation expires.
     */
    public void setTimestampExpires(Date timestampExpires) {
        this.timestampExpires = timestampExpires;
    }

    /**
     * Get operation data.
     * @return Operation data.
     */
    public String getOperationData() {
        return operationData;
    }

    /**
     * Set operation data.
     * @param operationData Operation data.
     */
    public void setOperationData(String operationData) {
        this.operationData = operationData;
    }

    /**
     * Is the operation expired?
     *
     * @return true if expired
     */
    public boolean isExpired() {
        return new Date().after(timestampExpires);
    }

    /**
     * Get the list with optional extra parameters.
     * @return Extra parameters.
     */
    public List<AuthStep> getSteps() {
        return steps;
    }

    /**
     * Get the list with operation history records.
     * @return List with operation history records.
     */
    public List<OperationHistory> getHistory() {
        return history;
    }

    /**
     * Get the list with AFS action records.
     * @return List with AFS action records.
     */
    public List<AfsActionDetail> getAfsActions() {
        return afsActions;
    }

    /**
     * Get form data (title, message, other visual attributes, ...) of the operation.
     * @return Form data.
     */
    public OperationFormData getFormData() {
        return formData;
    }

    /**
     * Set form data object.
     * @param formData Set form data.
     */
    public void setFormData(OperationFormData formData) {
        this.formData = formData;
    }

    /**
     * Get chosen authentication method.
     * @return Chosen authentication method.
     */
    public AuthMethod getChosenAuthMethod() {
        return chosenAuthMethod;
    }

    /**
     * Set chosen authentication method.
     * @param chosenAuthMethod Chosen authentication method.
     */
    public void setChosenAuthMethod(AuthMethod chosenAuthMethod) {
        this.chosenAuthMethod = chosenAuthMethod;
    }

    /**
     * Get number of remaining authentication attempts.
     * @return Number of remaining attempts.
     */
    public Integer getRemainingAttempts() {
        return remainingAttempts;
    }

    /**
     * Set number of remaining authentication attempts.
     * @param remainingAttempts Number of remaining attempts.
     */
    public void setRemainingAttempts(Integer remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
    }

    /**
     * Get application context for OAuth 2.0 consent screen.
     * @return Application context for OAuth 2.0 consent screen.
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Set application context for OAuth 2.0 consent screen.
     * @param applicationContext Application context for OAuth 2.0 consent screen.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
