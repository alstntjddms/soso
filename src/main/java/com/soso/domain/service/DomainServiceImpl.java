package com.soso.domain.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.JWT.JwtUtils;
import com.soso.domain.dto.*;
import com.soso.domain.repository.itf.DomainRAO;
import com.soso.domain.service.itf.DomainService;
import com.soso.login.repository.itf.LoginRAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    DomainRAO rao;

    @Autowired
    LoginRAO loginRAO;

    @Override
    public String domain() {
        return "domain";
    }
}
