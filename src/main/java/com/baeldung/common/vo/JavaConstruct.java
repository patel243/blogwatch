package com.baeldung.common.vo;

import com.baeldung.common.GlobalConstants;

public class JavaConstruct {
    private String constructType;
    private String constructParentTypeName; // if constructType is Method, constructParentTypeName will contain the Java Class Type
    private String constructName;
    private boolean foundOnGitHub;

    public JavaConstruct() {
    }

    public JavaConstruct(String constructType, String constructParentTypeName, String constructName, boolean foundOnGitHub) {
        this.constructType = constructType;
        this.constructParentTypeName = constructParentTypeName;
        this.constructName = constructName;
        this.foundOnGitHub = foundOnGitHub;
    }

    public JavaConstruct(String constructType, String constructParentTypeName, String constructName) {
        super();
        this.constructType = constructType;
        this.constructParentTypeName = constructParentTypeName;
        this.constructName = constructName;
        this.foundOnGitHub = false;
    }

    public boolean equalsTo(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JavaConstruct other = (JavaConstruct) obj;
        if (constructName == null) {
            if (other.constructName != null)
                return false;
        } else if (!constructName.equals(other.constructName))
            return false;
        if (constructParentTypeName == null) {
            if (other.constructParentTypeName != null)
                return false;
        } else if (!constructParentTypeName.equals(other.constructParentTypeName) && !constructParentTypeName.equals(GlobalConstants.CONSTRUCT_DUMMY_CLASS_NAME))
            return false;
        if (constructType == null) {
            if (other.constructType != null)
                return false;
        } else if (!constructType.equals(other.constructType))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return constructType + " , " + (null == constructParentTypeName ? "" : constructParentTypeName.equals(GlobalConstants.CONSTRUCT_DUMMY_CLASS_NAME) ? "" : constructParentTypeName) + "," + constructName;
    }

    public String getConstructType() {
        return constructType;
    }

    public void setConstructType(String constructType) {
        this.constructType = constructType;
    }

    public String getConstructName() {
        return constructName;
    }

    public void setConstructName(String constructName) {
        this.constructName = constructName;
    }

    public boolean isFoundOnGitHub() {
        return foundOnGitHub;
    }

    public void setFoundOnGitHub(boolean foundOnGitHub) {
        this.foundOnGitHub = foundOnGitHub;
    }

    public String getConstructParentTypeName() {
        return constructParentTypeName;
    }

    public void setConstructParentTypeName(String constructParentTypeName) {
        this.constructParentTypeName = constructParentTypeName;
    }

}
