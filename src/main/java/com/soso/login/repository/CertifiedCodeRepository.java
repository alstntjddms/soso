package com.soso.login.repository;

import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.Iterator;

public class CertifiedCodeRepository {
    public static ArrayList<CertifiedCodeDTO> repository;

    // repository의 CertifiedCodeDTO의 sendDate요소가 현재시간-3분 보다 과거이면 삭제한다.
    @Scheduled(fixedRate = 60 * 10 * 1000) // 600,000 밀리초 = 10분
    public void updateCertifiedCodeRepository() {
        long threeMinutesAgo = System.currentTimeMillis() - (3 * 60 * 1000);

        for (Iterator<CertifiedCodeDTO> iterator = repository.iterator(); iterator.hasNext();) {
            CertifiedCodeDTO certifiedCode = iterator.next();
            if (isCertifiedCodeExpired(certifiedCode, threeMinutesAgo)) {
                iterator.remove();
                System.out.println("certifiedCode = " + certifiedCode + "가 삭제되었습니다.");
            }
        }
    }
    private boolean isCertifiedCodeExpired(CertifiedCodeDTO certifiedCode, long thresholdTime) {
        return certifiedCode.getSendDate().getTime() < thresholdTime;
    }
}
