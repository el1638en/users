package api.com.medhead.users.security.service;


import api.com.medhead.users.model.ERole;
import api.com.medhead.users.model.Role;
import api.com.medhead.users.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Optional<Role> findByName(ERole name) {
        return roleRepository.findByName(name);
    }
}
