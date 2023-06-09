package com.example.bookStore.service;

import com.example.bookStore.document.Elastic;
import com.example.bookStore.exception.type.DuplicateFieldException;
import com.example.bookStore.exception.type.NotFoundException;
import com.example.bookStore.model.Roles;
import com.example.bookStore.repository.ElasticRepository;
import com.example.bookStore.repository.RolesRepository;
import com.example.bookStore.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class RolesService {
    private final RolesRepository rolesRepository;
    private final MessageSource messageSource;
    private final ElasticRepository elasticRepository;

    public RolesService(RolesRepository rolesRepository, MessageSource messageSource, ElasticRepository elasticRepository) {
        this.rolesRepository = rolesRepository;
        this.messageSource = messageSource;
        this.elasticRepository = elasticRepository;
    }

    public Response getRoles(int page) {
        Page<Roles> pages = rolesRepository.findAll(PageRequest.of(page - 1, 10));
        log.info("get all roles successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "get all roles successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("get.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                pages.get(),
                pages.getTotalPages());
    }

    public Response postRoles(Roles roles) throws DuplicateFieldException {
        checkDuplicateRoles(roles.getName());
        rolesRepository.save(roles);
        log.info("add role "+ roles.getName() +" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "add role "+ roles.getName() +" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("add.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                roles,
                1);
    }

    public Response deleteRoles(Roles roles) throws NotFoundException {
        roles = rolesRepository.findByName(roles.getName())
                .orElseThrow(() -> new NotFoundException("Role not found!"));
        rolesRepository.deleteById(roles.getId());
        log.info("delete role "+roles.getName()+" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "delete role "+roles.getName()+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("delete.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                roles,
                1);
    }

    public Response putRoles(Roles roles, String roleName) throws DuplicateFieldException, NotFoundException {
        Roles previousRole = rolesRepository.findByName(roleName)
                .orElseThrow(() -> new NotFoundException("Role not found!"));
        checkDuplicateRoles(roles.getName());
        if (roles.getName() != null) {
            checkDuplicateRoles(roles.getName());
            previousRole.setName(roles.getName());
        }
        if (roles.getDescription() != null) {
            previousRole.setDescription(roles.getDescription());
        }
        rolesRepository.save(previousRole);
        log.info("edit role "+roles.getName()+" successfully");
        elasticRepository.save(
                new Elastic(
                        LocalDateTime.now().toString(),
                        HttpStatus.OK.toString(),
                        "info",
                        "edit role "+roles.getName()+" successfully"
                )
        );
        return new Response(HttpStatus.OK,
                messageSource.getMessage("update.role.successfully",
                        null,
                        LocaleContextHolder.getLocale()
                ),
                roles,
                1);
    }

    private void checkDuplicateRoles(String roleName) throws DuplicateFieldException {
        if (rolesRepository.findByName(roleName).isPresent()) {
            log.error("Role is duplicate!");
            throw new DuplicateFieldException("Role is duplicate!");
        }
    }
}
