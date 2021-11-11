package com.lemonado.smartmeet.web.rest.services;

import com.lemonado.smartmeet.core.data.exceptions.RoleNotFoundException;
import com.lemonado.smartmeet.core.data.exceptions.UserNotFoundException;
import com.lemonado.smartmeet.core.data.models.roles.RoleModel;
import com.lemonado.smartmeet.core.data.models.users.UserModel;
import com.lemonado.smartmeet.core.services.users.RoleService;
import com.lemonado.smartmeet.core.services.users.UserRolesService;
import com.lemonado.smartmeet.web.rest.models.auth.AuthorityRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorityService {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRolesService userRolesService;

    private final Map<Long, String> authorityPerRoleId = new HashMap<>();


    @PostConstruct
    private void postConstruct() throws RoleNotFoundException {
        addMapping(AuthorityRole.ADMIN, RoleModel.ADMIN);
        addMapping(AuthorityRole.MANAGER, RoleModel.MANAGER);
    }

    @Transactional(readOnly = true)
    public List<String> getAuthorityNames(long userId) throws UserNotFoundException {
        return userRolesService.streamUserRoles(userId)
                .stream()
                .map(x -> findAuthority(x.roleId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GrantedAuthority> getAuthorities(UserModel user) throws UserNotFoundException {
        return getAuthorityNames(user.id())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public Optional<String> findAuthority(long roleId) {
        return Optional.ofNullable(authorityPerRoleId.get(roleId));
    }

    private void addMapping(String authority, String roleName) throws RoleNotFoundException {
        var id = roleService.findId(roleName);

        authorityPerRoleId.put(id, authority);
    }
}
