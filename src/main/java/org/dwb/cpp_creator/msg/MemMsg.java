package org.dwb.cpp_creator.msg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * member的信息结构体
 */
@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemMsg {
    public String type;
    public String name;
    public String val;
    public boolean hasVal;
    public boolean isArray;
    //Array才有
    public String valNum;
    //type检查
    public boolean isString;
    //大写的type
    public String upperType;
}
