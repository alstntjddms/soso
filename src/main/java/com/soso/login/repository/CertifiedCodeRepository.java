package com.soso.login.repository;

import com.soso.login.dto.CertifiedCodeDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;

@Repository
public class CertifiedCodeRepository {
    public static ArrayList<CertifiedCodeDTO> repository = new ArrayList<>();

    public boolean checkEMailFromCertifiedCode(String email, String certifiedCode){
        for (CertifiedCodeDTO certifiedCodeDTO : repository) {
            if (certifiedCodeDTO.getEmail().equals(email) && certifiedCodeDTO.getCertifiedCode().equals(certifiedCode)) {
                if(isCertifiedCodeExpired(certifiedCodeDTO)){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    // 10분마다 repository의 CertifiedCodeDTO의 sendDate요소가 (현재시간-3분)보다 과거이면 삭제한다.
    @Scheduled(fixedRate = 60 * 3 * 1000) // 3분
    public void updateCertifiedCodeRepository() {
        for (Iterator<CertifiedCodeDTO> iterator = repository.iterator(); iterator.hasNext();) {
            CertifiedCodeDTO certifiedCodeDTO = iterator.next();
            System.out.println("certifiedCodeDTO = " + certifiedCodeDTO);
            if (isCertifiedCodeExpired(certifiedCodeDTO)) {
                repository.remove(certifiedCodeDTO);
                System.out.println("인증코드 삭제 = " + certifiedCodeDTO.getCertifiedCode() + " email = " + certifiedCodeDTO.getEmail());
            }
        }
    }
    private boolean isCertifiedCodeExpired(CertifiedCodeDTO certifiedCodeDTO) {
        return certifiedCodeDTO.getSendDate().getTime() < System.currentTimeMillis() - (60 * 3 * 1000);
    }
}
