package com.baeldung.common.dto;

public class DependencyVersionDto {
    private final DependencyDto dependency;
    private final String version;

    public DependencyVersionDto(DependencyDto dependency, String version) {
        this.dependency = dependency;
        this.version = version;
    }

    public DependencyDto getDependency() {
        return dependency;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return "groupId: " + dependency.getGroupId() + "; artifactId: " + dependency.getArtifactId() + "; version: " + version;
    }
}
