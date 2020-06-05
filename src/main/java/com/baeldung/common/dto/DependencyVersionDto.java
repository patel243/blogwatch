package com.baeldung.common.dto;

public class DependencyVersionDto {
    private final DependencyDto module;
    private final String version;

    public DependencyVersionDto(DependencyDto module, String version) {
        this.module = module;
        this.version = version;
    }

    public DependencyDto getModule() {
        return module;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "groupId: " + module.getGroupId() + "; artifactId: " + module.getArtifactId() + "; version: " + version;
    }
}
