package com.sip.api.service;

import com.sip.api.domains.resource.Resource;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.services.ResourceService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class ResourceServiceImplTest {
    @Autowired
    private ResourceService resourceService;
    private Resource savedResource;
    private final String name = "TEST_RESOURCE";
    private final String url = "/test";

    @Before
    public void setUp() {
        savedResource = generateResource(name, url);
    }

    @Test
    public void createResource() {
        assertThat(resourceService.findAll()).hasSize(1).contains(savedResource);
    }

    @Test
    public void createResourceWithRepeatingName_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateResource(name, url));
    }

    @Test
    public void updateResourceName() {

    }

    public void updateResourceUrl() {

    }

    public void updateNonExistingResource_shouldThrowNotFound() {

    }

    public void deleteResourceById() {

    }

    public void deleteResourceByName() {

    }

    public void deleteNonExistingResource_shouldThrowNotFound() {

    }

    private Resource generateResource(String name, String url) {
        return resourceService.createResource(ResourceCreationDto.builder()
                .name(name)
                .url(url)
                .build());
    }
}
