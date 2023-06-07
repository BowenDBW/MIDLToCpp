package org.dwb.cpp_creator.msg;

import java.util.ArrayList;

/**
 * 外来引用的成员信息体
 */
public class ScopedMemMsg {
    String name;
    String type;
    ArrayList<MemMsg> members;

    public ScopedMemMsg() {

    }

    public ScopedMemMsg(String name, String type, ArrayList<MemMsg> members) {
        this.name = name;
        this.type = type;
        this.members = members;
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

    public ArrayList<MemMsg> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<MemMsg> members) {
        this.members = members;
    }
}
