package org.dwb.ast;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


/**
 * 语法树结点信息，其中父结点信息可以用于描述边信息
 */
@Data
@Getter
@Setter
@Builder
public class AstNode {
    String nodeName;
    String parentName;
    int parentId;
    int nodeId;
    int nodeLayer;
}
