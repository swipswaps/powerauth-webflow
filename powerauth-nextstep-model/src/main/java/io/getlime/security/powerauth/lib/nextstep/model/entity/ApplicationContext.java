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
package io.getlime.security.powerauth.lib.nextstep.model.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Application context for OAuth 2.0 consent screen.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class ApplicationContext {

    private String id;
    private String name;
    private String description;
    private List<String> originalScopes;
    private Map<String, Object> extras;

    /**
     * Default constructor.
     */
    public ApplicationContext() {
        extras = new HashMap<>();
        originalScopes = new ArrayList<>();
    }

    /**
     * Constructor with all details.
     * @param id Application identifier.
     * @param name Application name.
     * @param description Application description
     */
    public ApplicationContext(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.originalScopes = new ArrayList<>();
        this.extras = new HashMap<>();
    }

    /**
     * Get application identifier.
     * @return Application identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Set application identifier.
     * @param id Application identifier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get application name.
     * @return Application name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set application name.
     * @param name Application name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get application description.
     * @return Application description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set application description.
     * @param description Application description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the list with the original OAuth 2.0 scopes used when requesting the operation.
     * @return List of originally requested scopes.
     */
    public List<String> getOriginalScopes() {
        return originalScopes;
    }

    /**
     * Get extra information for OAuth 2.0 consent screen.
     * @return Extra information for OAuth 2.0 consent screen.
     */
    public Map<String, Object> getExtras() {
        return extras;
    }

}
