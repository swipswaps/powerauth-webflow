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
import axios from "axios";
import {dispatchError} from "../dispatcher/dispatcher";
import {handleAuthFailedError} from "./errorHandling";

/**
 * Authenticate SCA login for given username.
 * @param username Username.
 * @param organizationId Organization ID.
 * @returns {Function} No return value.
 */
export function authenticate(username, organizationId) {
    return function (dispatch) {
        dispatch({
            type: "SHOW_SCREEN_LOGIN_SCA",
            payload: {
                loading: true,
                error: false,
                message: ""
            }
        });
        axios.post("./api/auth/login-sca/authenticate", {
            username: username,
            organizationId: organizationId
        }, {
            headers: {
                'X-OPERATION-HASH': operationHash,
            }
        }).then((response) => {
            switch (response.data.result) {
                case 'CONFIRMED': {
                    if (response.data.mobileTokenEnabled) {
                        dispatch({
                            type: "SHOW_SCREEN_TOKEN",
                            payload: {
                                loading: true,
                                error: false,
                                message: "",
                                smsFallbackAvailable: true
                            }
                        });
                    } else {
                        dispatch({
                            type: "SHOW_SCREEN_SMS",
                            payload: {
                                loading: true,
                                error: false,
                                message: ""
                            }
                        });
                    }
                    break;
                }
                case 'AUTH_FAILED': {
                    if (!handleAuthFailedError(dispatch, response)) {
                        dispatch({
                            type: "SHOW_SCREEN_LOGIN_SCA",
                            payload: {
                                loading: false,
                                error: true,
                                message: response.data.message,
                                remainingAttempts: response.data.remainingAttempts
                            }
                        });
                    }
                    break;
                }
            }
            return null;
        }).catch((error) => {
            dispatchError(dispatch, error);
        })
    }
}

/**
 * Initialize SCA login.
 * @returns {Function} No return value.
 */
export function init() {
    return function (dispatch) {
        dispatch({
            type: "SHOW_SCREEN_LOGIN_SCA",
            payload: {
                loading: true,
                error: false,
                message: ""
            }
        });
        axios.post("./api/auth/login-sca/init", {}).then((response) => {
            // Handling of page refresh
            if (response.data.userAlreadyKnown) {
                if (response.data.mobileTokenEnabled) {
                    dispatch({
                        type: "SHOW_SCREEN_TOKEN",
                        payload: {
                            loading: true,
                            error: false,
                            message: "",
                            smsFallbackAvailable: true
                        }
                    });
                } else {
                    console.log(response.data);
                    dispatch({
                        type: "SHOW_SCREEN_SMS",
                        payload: {
                            loading: true,
                            error: false,
                            message: "",
                            certificateEnabled: response.data.clientCertificateAuthenticationEnabled,
                            certificateVerificationUrl: response.data.clientCertificateVerificationUrl
                        }
                    });
                }
                return null;
            }
            dispatch({
                type: "SHOW_SCREEN_LOGIN_SCA",
                payload: response.data
            });
            return null;
        }).catch((error) => {
            dispatchError(dispatch, error);
        })
    }
}

/**
 * Cancel SCA login.
 * @returns {Function} No return value.
 */
export function cancel() {
    return function (dispatch) {
        axios.post("./api/auth/login-sca/cancel", {}, {
            headers: {
                'X-OPERATION-HASH': operationHash,
            }
        }).then((response) => {
            dispatch({
                type: "SHOW_SCREEN_ERROR",
                payload: {
                    message: response.data.message
                }
            });
            return null;
        }).catch((error) => {
            dispatchError(dispatch, error);
        })
    }
}

/**
 * Select an organization.
 * @param organizationId Organization ID.
 * @returns {Function} No return value.
 */
export function selectOrganization(organizationId) {
    return function (dispatch) {
        dispatch({
            type: "SHOW_SCREEN_LOGIN_SCA",
            payload: {
                chosenOrganizationId: organizationId
            }
        });
    }
}

/**
 * Interrupt operation and show unexpected error about missing organization configuration.
 * @returns {Function} Missing organization configuration error is dispatched.
 */
export function organizationConfigurationError() {
    return function (dispatch) {
        dispatch({
            type: "SHOW_SCREEN_ERROR",
            payload: {
                message: "organization.configurationError"
            }
        })
    }
}

/**
 * Verify client TLS certificate.
 * @param callbackOnSuccess Callback in case of successful verification.
 * @param certificateVerificationUrl URL to be used to verify client TLS certificate.
 * @returns {Function} No return value.
 */
export function checkClientCertificate(callbackOnSuccess, certificateVerificationUrl) {
    return function (dispatch) {
        axios.post(certificateVerificationUrl, {}, {
            // Send cookies so that HTTP session is the same
            withCredentials: true
        }).then((response) => {
            callbackOnSuccess();
            return null;
        }).catch((error) => {
            // TODO - show user friendly error message
            dispatchError(dispatch, error);
        })
    }
}
