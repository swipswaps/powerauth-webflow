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

package io.getlime.security.powerauth.app.tppengine.errorhandling.error;

import io.getlime.core.rest.model.base.entity.Error;

import java.util.ArrayList;
import java.util.List;

/**
 * Error related to consent.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
public class TppAppError extends Error {

    private static final String code = "TPP_APP_ERROR";

    private List<String> causes;

    public TppAppError() {
        super();
        this.setCode(code);
    }

    public TppAppError(String message) {
        super(code, message);
    }

    public TppAppError(String message, List<String> causes) {
        super(code, message);
        this.causes = new ArrayList<>(causes);
    }

    public List<String> getCauses() {
        return causes;
    }
}
