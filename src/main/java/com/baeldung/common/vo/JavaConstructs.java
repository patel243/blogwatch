package com.baeldung.common.vo;

public class JavaConstructs {
    private String constructType;
    private String constructParentTypeName; //if constructType is Method, constructParentTypeName will contain the Java Class Type
    private String constructName;
    private boolean foundOnGitHub;

    public JavaConstructs() {
    }

    public JavaConstructs(String constructType, String constructParentTypeName,  String constructName, boolean foundOnGitHub) {        
        this.constructType = constructType;
        this.constructParentTypeName = constructParentTypeName;
        this.constructName = constructName;
        this.foundOnGitHub = foundOnGitHub;
    }
    
    public JavaConstructs(String constructType, String constructParentTypeName, String constructName) {
        super();
        this.constructType = constructType;
        this.constructParentTypeName = constructParentTypeName;
        this.constructName = constructName;
        this.foundOnGitHub = false;
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
