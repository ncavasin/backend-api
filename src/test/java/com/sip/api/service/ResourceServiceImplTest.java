package com.sip.api.service;

import com.sip.api.domains.resource.Resource;
import com.sip.api.dtos.resource.ResourceCreationDto;
import com.sip.api.dtos.resource.ResourceNameDto;
import com.sip.api.dtos.resource.ResourceUrlDto;
import com.sip.api.exceptions.BadRequestException;
import com.sip.api.exceptions.NotFoundException;
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

@RunWith(SpringRunner.class)
@ActiveProfiles("mem")
@SpringBootTest
public class ResourceServiceImplTest {
    @Autowired
    private ResourceService resourceService;
    private Resource savedResource;
    private Resource updatableResource;
    private final String name = "NEW_RESOURCE";
    private final String url = "/NEW_RESOURCE";
    private final String nameDeletable = "DELETABLE_RESOURCE";

    @Before
    @Transactional
    public void setUp() {
        savedResource = generateResource(name, url);
        updatableResource = generateResource("OLD_RESOURCE_NAME", "/OLD_RESOURCE_URL");
        generateResource(nameDeletable, "/NAME_DELETABLE");
    }

    @Test
    @Transactional
    public void createResource() {
        Assert.assertEquals(resourceService.findById(savedResource.getId()), savedResource);
    }

    @Test
    @Transactional
    public void createResourceWithRepeatingName_shouldThrowBadRequest() {
        Assert.assertThrows(BadRequestException.class, () -> generateResource(name, url));
    }

    @Test
    @Transactional
    public void updateResourceName() {
        String oldName = updatableResource.getName();
        String updatedName = "UPDATED_NAME";

        final Resource updatedResource = resourceService.updateResourceName(ResourceNameDto.builder()
                .id(updatableResource.getId())
                .name(updatedName)
                .build());

        Assert.assertNotEquals(oldName, updatedResource.getName());
        Assert.assertEquals(updatedResource.getName(), updatedName);
    }

    @Test
    @Transactional
    public void updateResourceUrl() {
        final Resource found = resourceService.findById(updatableResource.getId());
        String oldUrl = updatableResource.getUrl();
        String updatedUrl = "/UPDATED_URL";

        final Resource updatedResource = resourceService.updateResourceUrl(ResourceUrlDto.builder()
                .id(found.getId())
                .url(updatedUrl)
                .build());

        Assert.assertNotEquals(oldUrl, updatedResource.getUrl());
        Assert.assertEquals(updatedResource.getUrl(), updatedUrl);
    }

    @Test
    @Transactional
    public void updateNonExistingResource_shouldThrowNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> resourceService.updateResourceName(ResourceNameDto.builder()
                .id("NON_EXISTING_ID")
                .name("QWERTY NAME")
                .build()));
    }

    @Test
    @Transactional
    public void deleteResourceById() {
        resourceService.deleteById(savedResource.getId());
        Assert.assertThrows(NotFoundException.class, () -> resourceService.findById(savedResource.getId()));
    }

    @Test
    @Transactional
    public void deleteResourceByName() {
        resourceService.deleteByName(nameDeletable);
        Assert.assertThrows(NotFoundException.class, () -> resourceService.findByName(nameDeletable));
    }

    @Test
    @Transactional
    public void deleteNonExistingResource_shouldThrowNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> resourceService.deleteById("NON_EXISTING_ID"));
    }

    private Resource generateResource(String name, String url) {
        return resourceService.createResource(ResourceCreationDto.builder()
                .name(name)
                .url(url)
                .build());
    }
}
