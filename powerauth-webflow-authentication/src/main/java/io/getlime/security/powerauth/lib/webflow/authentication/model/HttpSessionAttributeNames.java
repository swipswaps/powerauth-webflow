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
package io.getlime.security.powerauth.lib.webflow.authentication.model;

/**
 * Constants for storing attributes in HTTP session by individual steps.
 */
public class HttpSessionAttributeNames {

    public static final String MESSAGE_ID = "MESSAGE_ID";
    public static final String LAST_MESSAGE_TIMESTAMP = "LAST_MESSAGE_TIMESTAMP";
    public static final String INITIAL_MESSAGE_SENT = "INITIAL_MESSAGE_SENT";
    public static final String AUTH_STEP_OPTIONS = "AUTH_STEP_OPTIONS";
    public static final String PENDING_AUTH_OBJECT = "PENDING_AUTH_OBJECT";
    public static final String CONSENT_SKIPPED = "CONSENT_SKIPPED";
    public static final String USERNAME = "USERNAME";
    public static final String CLIENT_CERTIFICATE = "CLIENT_CERTIFICATE";

}
