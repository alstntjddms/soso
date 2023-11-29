package com.soso.domain.controller;

import com.soso.common.aop.jwt.JwtValidationAOP;
import com.soso.domain.dto.TeamDTO;
import com.soso.domain.service.itf.TeamService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Tag(name = "팀 관련 API", description = "Swagger 팀 테스트용 API")
@JwtValidationAOP
@RequestMapping("/team")
@RestController
public class TeamController {

    @Autowired
    TeamService teamService;

    @GetMapping("/teams")
    public ResponseEntity<?> findTeamsByLoginMember(@CookieValue String sosoJwtToken) {
        return new ResponseEntity<>(teamService.findTeamsByLoginId(sosoJwtToken), HttpStatus.OK);
    }

    @PostMapping("/team")
    public ResponseEntity<?> createTeam(@CookieValue String sosoJwtToken, @RequestBody HashMap<String, String> reqData) {
        return new ResponseEntity<>(teamService.createTeam(sosoJwtToken, reqData), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginTeam(@CookieValue String sosoJwtToken, @RequestBody TeamDTO teamDTO, HttpServletResponse response) {
        return new ResponseEntity<>(teamService.loginTeam(sosoJwtToken, teamDTO, response), HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity<?> findTeamMembersByLoginMember(@CookieValue String sosoJwtToken) {
        return new ResponseEntity<>(teamService.findTeamMembersByLoginMember(sosoJwtToken), HttpStatus.OK);
    }

}
