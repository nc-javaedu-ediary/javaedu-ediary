package com.ncjavaedu.ediary.client.props;

import com.ncjavaedu.ediary.client.model.RoleDTO;
import com.ncjavaedu.ediary.client.model.UserDTO;
import com.sencha.gxt.core.client.ValueProvider;

public class RoleValueProvider implements ValueProvider<UserDTO, String> {

    public String roleStr;

    public RoleValueProvider(){}

    public RoleValueProvider(String column) {
        this.roleStr = column;
    }

    public RoleValueProvider(RoleDTO roleDTO){
        this.roleStr = RoleDTOToString(roleDTO);
    }

    @Override
    public String getValue(UserDTO object) {
        if(object.getRole() == null) {
            return "";
        } else {
            return RoleDTOToString(object.getRole());
        }
    }

    @Override
    public void setValue(UserDTO userDTO, String s) {
    }

    @Override
    public String getPath() {
        return null;
    }

    public String RoleDTOToString(RoleDTO role){
        if(role == RoleDTO.Student){
            return "Студент";
        }
        else if(role == RoleDTO.Admin){
            return "Админ";
        }
        else
            return "Лектор";
    }
}
