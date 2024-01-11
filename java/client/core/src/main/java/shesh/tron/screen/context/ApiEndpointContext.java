package shesh.tron.screen.context;

import shesh.tron.utils.endpoint.ApiEndpoint;

public interface ApiEndpointContext extends Context {

    default void setApiEndpoint(ApiEndpoint apiEndpoint) {

        set("apiEndpoint", apiEndpoint);
    }

    default ApiEndpoint getApiEndpoint() {

        return (ApiEndpoint) get("apiEndpoint");
    }
}
