package com.soso.domain.service;

import com.soso.domain.service.itf.DomainService;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainService {

    @Override
    public String domain() {
        return "domain";
    }
}
