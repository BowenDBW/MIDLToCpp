package org.dwb.semantic_check;

import lombok.*;

/**
 * 符号表中的元素
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParamNode {

    private String name;
    private String type;
    private String moduleName;
    private String structName;
    private String val;
    private ParamNode next;

    @Override
    public String toString() {
        return "Module:" + this.moduleName + " @ " + "Struct:" + this.structName + " @ " + "Type:" + this.type
                + " @ " + "Name:" + this.name + " @ " + "Val:" + this.val;
    }
}
