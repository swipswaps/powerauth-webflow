/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
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

package io.getlime.security.powerauth.lib.webauth.authentication.mtoken.controller;

import io.getlime.core.rest.model.base.response.ObjectResponse;
import io.getlime.core.rest.model.base.response.Response;
import io.getlime.push.client.PushServerClient;
import io.getlime.push.client.PushServerClientException;
import io.getlime.push.model.entity.PushMessage;
import io.getlime.push.model.entity.PushMessageBody;
import io.getlime.push.model.entity.PushSendResult;
import io.getlime.security.powerauth.lib.nextstep.model.entity.AuthStep;
import io.getlime.security.powerauth.lib.nextstep.model.entity.OperationDisplayDetails;
import io.getlime.security.powerauth.lib.nextstep.model.entity.OperationHistory;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthMethod;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthResult;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthStepResult;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetOperationDetailResponse;
import io.getlime.security.powerauth.lib.webauth.authentication.controller.AuthMethodController;
import io.getlime.security.powerauth.lib.webauth.authentication.exception.AuthStepException;
import io.getlime.security.powerauth.lib.webauth.authentication.mtoken.configuration.PushServiceConfiguration;
import io.getlime.security.powerauth.lib.webauth.authentication.mtoken.model.request.MobileTokenAuthenticationRequest;
import io.getlime.security.powerauth.lib.webauth.authentication.mtoken.model.response.MobileTokenAuthenticationResponse;
import io.getlime.security.powerauth.lib.webauth.authentication.mtoken.model.response.MobileTokenInitResponse;
import io.getlime.security.powerauth.lib.webauth.authentication.mtoken.service.WebSocketMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Petr Dvorak, petr@lime-company.eu
 */
@Controller
@RequestMapping(value = "/api/auth/token/web")
public class MobileTokenController extends AuthMethodController<MobileTokenAuthenticationRequest, MobileTokenAuthenticationResponse, AuthStepException> {

    @Autowired
    private PushServerClient pushServerClient;

    @Autowired
    private PushServiceConfiguration configuration;

    @Autowired
    private WebSocketMessageService webSocketMessageService;

    @Override
    protected String authenticate(MobileTokenAuthenticationRequest request) throws AuthStepException {
        final GetOperationDetailResponse operation = getOperation();
        final List<OperationHistory> history = operation.getHistory();
        for (OperationHistory h: history) {
            if (AuthMethod.POWERAUTH_TOKEN.equals(h.getAuthMethod())
                    && !AuthResult.FAILED.equals(h.getAuthResult())) {
                return operation.getUserId();
            }
        }
        return null;
    }

    @Override
    protected AuthMethod getAuthMethodName() {
        return AuthMethod.POWERAUTH_TOKEN;
    }

    @RequestMapping(value = "/init", method = RequestMethod.POST)
    public @ResponseBody MobileTokenInitResponse initPushMessage() {
        final GetOperationDetailResponse operation = getOperation();
        final String userId = operation.getUserId();

        PushMessage message = new PushMessage();
        message.setUserId(userId);
        message.setPersonal(true);
        message.setEncrypted(true);

        final OperationDisplayDetails displayDetails = operation.getDisplayDetails();

        PushMessageBody body = new PushMessageBody();
        if (displayDetails != null) {
            body.setTitle(displayDetails.getTitle());
            body.setBody(displayDetails.getMessage());
        } else {
            //TODO: Localize the messages
            body.setTitle("Confirm operation");
            body.setBody("Data: " + operation.getOperationData());
        }
        body.setSound("default");
        body.setCategory(operation.getOperationName());

        message.setMessage(body);

        final MobileTokenInitResponse initResponse = new MobileTokenInitResponse();
        initResponse.setWebSocketId(webSocketMessageService.generateWebSocketId(operation.getOperationId()));

        try {
            final ObjectResponse<PushSendResult> response = pushServerClient.sendNotification(configuration.getPushServerApplication(), message);
            if (response.getStatus().equals(Response.Status.OK)) {
                initResponse.setResult(AuthStepResult.CONFIRMED);
            } else {
                initResponse.setResult(AuthStepResult.AUTH_FAILED);
                initResponse.setMessage("authentication.fail"); // TODO: better message for initialization error
            }
        } catch (PushServerClientException ex) {
            initResponse.setResult(AuthStepResult.AUTH_FAILED);
            initResponse.setMessage("authentication.fail"); // TODO: better message for initialization error
        }
        return initResponse;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public @ResponseBody
    MobileTokenAuthenticationResponse checkOperationStatus(@RequestBody MobileTokenAuthenticationRequest request) {
        try {
            String userId = authenticate(request);
            final GetOperationDetailResponse operation = getOperation();
            if (userId == null) {
                if (operation.isExpired()) {
                    // handle operation expiration
                    // remove WebSocket session, it is expired
                    webSocketMessageService.removeWebSocketSession(operation.getOperationId());
                    final MobileTokenAuthenticationResponse response = new MobileTokenAuthenticationResponse();
                    response.setResult(AuthStepResult.AUTH_FAILED);
                    response.setMessage("authentication.timeout");
                    return response;
                }
                // WebSocket session can not be removed yet - authentication is in progress
                final MobileTokenAuthenticationResponse response = new MobileTokenAuthenticationResponse();
                response.setResult(AuthStepResult.AUTH_FAILED);
                response.setMessage("authentication.fail");
                return response;
            }
            return buildAuthorizationResponse(request, new AuthResponseProvider() {

                @Override
                public MobileTokenAuthenticationResponse doneAuthentication(String userId) {
                    // remove WebSocket session, authorization is finished
                    webSocketMessageService.removeWebSocketSession(operation.getOperationId());
                    final MobileTokenAuthenticationResponse response = new MobileTokenAuthenticationResponse();
                    response.setResult(AuthStepResult.CONFIRMED);
                    response.setMessage("authentication.success");
                    return response;
                }

                @Override
                public MobileTokenAuthenticationResponse failedAuthentication(String userId, String failedReason) {
                    // WebSocket session can not be removed yet - authentication is in progress
                    final MobileTokenAuthenticationResponse response = new MobileTokenAuthenticationResponse();
                    response.setResult(AuthStepResult.AUTH_FAILED);
                    response.setMessage(failedReason);
                    return response;
                }

                @Override
                public MobileTokenAuthenticationResponse continueAuthentication(String operationId, String userId, List<AuthStep> steps) {
                    // remove WebSocket session, authorization is finished
                    webSocketMessageService.removeWebSocketSession(operation.getOperationId());
                    final MobileTokenAuthenticationResponse response = new MobileTokenAuthenticationResponse();
                    response.setResult(AuthStepResult.CONFIRMED);
                    response.setMessage("authentication.success");
                    response.getNext().addAll(steps);
                    return response;
                }
            });
        } catch (AuthStepException e) {
            final MobileTokenAuthenticationResponse response = new MobileTokenAuthenticationResponse();
            response.setResult(AuthStepResult.AUTH_FAILED);
            response.setMessage(e.getMessage());
            return response;
        }
    }

}
