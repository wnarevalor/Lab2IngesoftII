package co.edu.unal.software_engineering.labs.controller;

import co.edu.unal.software_engineering.labs.model.Role;
import co.edu.unal.software_engineering.labs.model.User;
import co.edu.unal.software_engineering.labs.pojo.LoginUserPOJO;
import co.edu.unal.software_engineering.labs.pojo.RegisterUserPOJO;
import co.edu.unal.software_engineering.labs.service.RoleService;
import co.edu.unal.software_engineering.labs.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
public class UserController{

    private UserService userService;

    private RoleService roleService;

    private PasswordEncoder passwordEncoder;

    public UserController( UserService userService, RoleService roleService, PasswordEncoder passwordEncoder ){
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping( value = { "/registro/nuevo-usuario/rol/{roleId}" } )
    public ResponseEntity<Void> registerNewUser( @PathVariable Integer roleId, @RequestBody RegisterUserPOJO userPOJO ){
        Role role = roleService.findById( roleId );
        User existingUser = userService.findByUsername( userPOJO.getUsername( ) );
        if( role == null || existingUser != null || !userService.isRightUser( userPOJO ) ){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }
        User newUser = new User( );
        newUser.setNames( userPOJO.getNames( ).toUpperCase( ) );
        newUser.setSurnames( userPOJO.getSurnames( ).toUpperCase( ) );
        newUser.setUsername( userPOJO.getUsername( ).toLowerCase( ) );
        newUser.setPassword( passwordEncoder.encode( userPOJO.getPassword( ) ) );
        newUser.setRoles( Collections.singletonList( role ) );
        userService.save( newUser );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

    @PostMapping( value = { "/registro/nuevo-rol/{roleId}" }, consumes = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<Void> registerRoleToUser( @PathVariable String roleId, @RequestBody LoginUserPOJO pojo ){
        int rolee = Integer.parseInt(roleId);
        Role role = roleService.findById( rolee );
        String username = SecurityContextHolder.getContext( ).getAuthentication( ).getName( );
        User existingUser = userService.findByUsername( username );
        if( role == null || existingUser.hasRole( role ) ){
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST );
        }else if( !passwordEncoder.matches( pojo.getPassword( ), existingUser.getPassword( ) ) ){
            return new ResponseEntity<>( HttpStatus.UNAUTHORIZED );
        }
        existingUser.addRole( role );
        userService.save( existingUser );
        return new ResponseEntity<>( HttpStatus.CREATED );
    }

}