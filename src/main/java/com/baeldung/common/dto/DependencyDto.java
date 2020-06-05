package com.baeldung.common.dto;

import java.util.Objects;

public class DependencyDto {
    private String groupId;
    private String artifactId;

    public DependencyDto(String groupId, String artifactId) {
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getArtifactId() {
        return artifactId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DependencyDto dependencyDto = (DependencyDto) o;
        return Objects.equals(groupId, dependencyDto.groupId) &&
          Objects.equals(artifactId, dependencyDto.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId);
    }
}
