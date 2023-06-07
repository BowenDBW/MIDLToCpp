package org.dwb.ast;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
