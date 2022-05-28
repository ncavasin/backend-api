package com.sip.api.utils.mock;

import com.sip.api.domains.resource.Resource;

public class ResourceMocking {
    public static Resource generateRawResourceWithParams(String name, String url) {
        return Resource.builder()
                .name(name)
                .url(url)
                .build();
    }
}
