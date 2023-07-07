package com.example.ecommerce.role;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepo roleRepo;

    public Role saveRole(Role role){
        Role roleFoundByName = roleRepo.findByRoleName(role.getRoleName());
        if(roleFoundByName != null){
            throw new IllegalStateException("Role already exists");
        }
        return roleRepo.save(role);
    }
}
