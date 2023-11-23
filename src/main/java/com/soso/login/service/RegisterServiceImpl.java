package com.soso.login.service;

import com.soso.login.repository.itf.RegisterRAO;
import com.soso.login.service.itf.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterRAO rao;
}
