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

import axios from "axios/index";
import {dispatchError} from "../dispatcher/dispatcher";

/**
 * Update operation form data on the server.
 * @param formData Operation form data.
 * @returns {Function} No response in case of OK status, otherwise error is dispatched.
 */
export function updateFormData(formData) {
    return function (dispatch) {
        axios.put("./api/auth/operation/formData", {
            formData: formData
        }, {
            headers: {
                'X-OPERATION-HASH': operationHash,
            }
        }).catch((error) => {
            dispatchError(dispatch, error);
        })
    }
}

/**
 * Interrupt operation and show unexpected error about missing bank accounts.
 * @returns {Function} Missing bank accounts error is dispatched.
 */
export function missingBankAccountsError() {
    return function (dispatch) {
        dispatch({
            type: "SHOW_SCREEN_ERROR",
            payload: {
                message: "operationReview.bankAccountsMissing"
            }
        })
    }
}
