package org.dwb.semantic_check;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 符号表中的元素链
 */
public class ParamList {
    public ParamNode head;

    public ParamList() {
        head = new ParamNode();
    }

    /**
     * 头插
     */
    public void insertNode(@NotNull ParamNode sn) {
        sn.setNext(head.getNext());
        head.setNext(sn);
    }

    public ParamNode getHead() {
        return head;
    }

    /**
     * to find中非null的值进行匹配
     */
    public ParamNode findNode(ParamNode toFind) {
        ParamNode tp = head;
        while ((tp = tp.getNext()) != null) {
            //tp = tp.getNext();
            if (toFind.getName() != null && !(toFind.getName().equals(tp.getName()))) {
                continue;
            }
            if (toFind.getModuleName() != null && !(toFind.getModuleName().equals(tp.getModuleName()))) {
                continue;
            }
            if (toFind.getStructName() != null && !(toFind.getStructName().equals(tp.getStructName()))) {
                continue;
            }
            if (toFind.getType() != null && !(toFind.getType().equals(tp.getType()))) {
                continue;
            }
            if (toFind.getVal() != null && !(toFind.getVal().equals(tp.getVal()))) {
                continue;
            }
            break;
        }
        return tp;
    }

    /**
     * to find中非null的值进行匹配所有的
     */
    public ArrayList<ParamNode> findNodes(ParamNode toFind) {
        ArrayList<ParamNode> ans = new ArrayList<ParamNode>();

        ParamNode tp = head;
        while ((tp = tp.getNext()) != null) {
            //tp = tp.getNext();
            if (toFind.getName() != null && !(toFind.getName().equals(tp.getName()))) {
                continue;
            }
            if (toFind.getModuleName() != null && !(toFind.getModuleName().equals(tp.getModuleName()))) {
                continue;
            }
            if (toFind.getStructName() != null && !(toFind.getStructName().equals(tp.getStructName()))) {
                continue;
            }
            if (toFind.getType() != null && !(toFind.getType().equals(tp.getType()))) {
                continue;
            }
            if (toFind.getVal() != null && !(toFind.getVal().equals(tp.getVal()))) {
                continue;
            }
            ans.add(tp);
        }
        return ans;
    }


    @Override
    public String toString() {
        ParamNode tp = head.getNext();
        String nodes = "";
        while (tp != null) {
            nodes += tp + "\n";
            tp = tp.getNext();
        }
        return nodes;
    }

}
