package org.dwb.semantic_check;

/**
 * 符号表中的元素
 */
public class ParamNode {

    private String name;
    private String type;
    private String moduleName;
    private String structName;
    private String val;
    private ParamNode next;

    public ParamNode() {
        this.name = null;
        this.type = null;
        this.moduleName = null;
        this.structName = null;
        this.val = null;
        this.next = null;
    }

    public ParamNode(String name, String type, String moduleName, String structName, String val) {
        this.name = name;
        this.type = type;
        this.moduleName = moduleName;
        this.structName = structName;
        this.val = val;
        this.next = null;
    }

    @Override
    public String toString() {
        return "Module:" + this.moduleName + " @ " + "Struct:" + this.structName + " @ " + "Type:" + this.type
                + " @ " + "Name:" + this.name + " @ " + "Val:" + this.val;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleNAme) {
        this.moduleName = moduleNAme;
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public ParamNode getNext() {
        return next;
    }

    public void setNext(ParamNode next) {
        this.next = next;
    }
}
