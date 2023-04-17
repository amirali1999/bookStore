package com.example.bookStore.controller;

import com.example.bookStore.exception.type.DuplicateFieldException;
import com.example.bookStore.exception.type.NotFoundException;
import com.example.bookStore.model.Roles;
import com.example.bookStore.request.RolesRequest;
import com.example.bookStore.service.RolesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "roles")
public class RolesController {

    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }
//    @PreAuthorize("hasAnyAuthority('admin')")
    @GetMapping(path = "getallroles/{page}")
    public ResponseEntity<?> getRoles(@PathVariable("page") int page) {
        return rolesService.getRoles(page).createResponseEntity();
    }
//    @PreAuthorize("hasAnyAuthority('admin')")
    @PostMapping(path = "addrole")
    public ResponseEntity<?> postRoles(@Valid @RequestBody RolesRequest rolesRequest) throws DuplicateFieldException {
        Roles roles = new Roles(rolesRequest.getName(),rolesRequest.getDescription());
        return rolesService.postRoles(roles).createResponseEntity();
    }
//    @PreAuthorize("hasAnyAuthority('admin')")
    @DeleteMapping(path = "deleterole")
    public ResponseEntity<?> deleteRoles(@Valid @RequestBody Roles roles) throws NotFoundException {
        return rolesService.deleteRoles(roles).createResponseEntity();
    }
//    @PreAuthorize("hasAnyAuthority('admin')")
    @PutMapping(path = "updaterole/{rolename}")
    public ResponseEntity<?> putRoles(@PathVariable("rolename") String roleName,@Valid @RequestBody Roles roles)
            throws DuplicateFieldException, NotFoundException {
        return rolesService.putRoles(roles,roleName).createResponseEntity();
    }
}
