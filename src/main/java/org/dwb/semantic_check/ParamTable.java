package org.dwb.semantic_check;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 符号表
 */
public class ParamTable {
    public static final int SIZE = 211;
    public static final int SHIFT = 4;
    public ParamList[] symTable;

    public ParamTable() {
        symTable = new ParamList[SIZE];
    }

    /**
     * 输入字符串,输出哈希值
     *
     * @param key
     * @return hashcode
     */
    public static int hash(@NotNull String key) {
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) {
            hashCode = ((hashCode << SHIFT) + key.charAt(i)) % SIZE;
        }
        return hashCode;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while ((line = bf.readLine()) != null) {
            int value = hash(line);
            System.out.println(value);
        }
    }

    /**
     * 属性信息插入符号表
     */
    public void insertST(@NotNull ParamNode sn) {
        int hashCode = hash(sn.getName());
        ParamList sl = symTable[hashCode];

        //该位置还没链表
        if (sl == null) {
            sl = new ParamList();
            sl.insertNode(sn);
        } else {
            sl.insertNode(sn);
        }
        symTable[hashCode] = sl;
    }

    //符合要求的节点,带姓名
    public ParamNode lookupSt(@NotNull ParamNode sn) {
        int hashCode = hash(sn.getName());
        ParamList sl = symTable[hashCode];
        if (sl == null) {
            return null;
        } else {
            return sl.findNode(sn);
        }
    }

    //任意符合要求的节点
    public ArrayList<ParamNode> lookupAll(ParamNode sn) {
        ArrayList<ParamNode> ans = new ArrayList<ParamNode>();
        ParamList sl;
        for (int i = 0; i < SIZE; i++) {
            ArrayList<ParamNode> tp;
            sl = symTable[i];
            if (sl != null) {
                tp = sl.findNodes(sn);
                ans.addAll(tp);
            }
        }
        return ans;
    }

    @Override
    public String toString() {
        StringBuilder syms = new StringBuilder();
        for (int i = 0; i < SIZE; i++) {
            if (symTable[i] != null) {
                syms.append(symTable[i].toString()).append("\n");
            }
        }
        return syms.toString();
    }


}
