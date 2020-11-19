package me.lynx.harrow.file;

public enum FileType {

    YAML(YAMLFile.class),
    TXT(TXTFile.class);

    private final Class myClass;

    FileType(Class myClass){
        this.myClass = myClass;
    }

    public Class getMyClass() {
        return myClass;
    }

}