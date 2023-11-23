package com.soso.login.repository;

import com.soso.login.dto.CertifiedCodeDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;

@Repository
public class CertifiedCodeRepository {
    public static ArrayList<CertifiedCodeDTO> repository = new ArrayList<>();

    // 10분마다 repository의 CertifiedCodeDTO의 sendDate요소가 (현재시간-3분)보다 과거이면 삭제한다.
    @Scheduled(fixedRate = 60 * 10 * 1000) // 600,000 밀리초 = 10분
    public void updateCertifiedCodeRepository() {
        System.out.println("실행");
        long threeMinutesAgo = System.currentTimeMillis() - (60 * 3 * 1000);

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
