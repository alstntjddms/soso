package com.soso.login.service;

import com.soso.common.aop.exception.CustomException;
import com.soso.common.aop.exception.ExceptionStatus;
import com.soso.common.utils.hash.SHA256;
import com.soso.common.utils.pattern.Validate;
import com.soso.login.dto.RegisterMemberDTO;
import com.soso.login.dto.CertifiedCodeDTO;
import com.soso.login.repository.CertifiedCodeRepository;
import com.soso.login.repository.itf.RegisterRAO;
import com.soso.login.service.itf.RegisterService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RegisterRAO rao;

    @Autowired
    CertifiedCodeRepository certifiedCodeRepository;

    @Autowired
    SHA256 sha256;

    @Autowired
    JavaMailSender javaMailSender;

    @Override
    public boolean checkLoginIdDuplicated(HashMap<String, String> reqData) {
        // loginId 6자 이하 OR loginId 30자 이상
        if(reqData.get("loginId").length() < 6 || reqData.get("loginId").length() > 30){
            throw new CustomException(ExceptionStatus.LOGIN_ID_LENGTH);
        }

        // loginId 중복 검사
        if(!duplicatedCheckLoginId(reqData.get("loginId"))){
            throw new CustomException(ExceptionStatus.LOGIN_ID_DUPLICATED);
        }
        return true;
    }

    @Override
    public boolean checkEmailDuplicated(HashMap<String, String> reqData) {
        // 이메일 유효성 검사
        if(!Validate.isEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_WRONG_PATTERN);
        }
        
        // 이메일 중복 검사
        if(!duplicatedCheckEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }
        return true;
    }

    @Override
    public boolean sendCertifiedCodeToMail(HashMap<String, String> reqData) {
        // 이메일 중복체크
        if(!duplicatedCheckEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }

        // 이메일 유효성 검사
        if(!Validate.isEmail(reqData.get("email"))){
            throw new CustomException(ExceptionStatus.EMAIL_WRONG_PATTERN);
        }

        // 무작위 코드를 생성
        String certifiedCode = generateCertifiedCode();
        System.out.println("certifiedCode = " + certifiedCode);

        // 무작위 코드를 이메일로 전송
        sendMail(reqData.get("email"), certifiedCode);

        // 무작위 코드를 repository에 저장
        certifiedCodeRepository.repository.add(
                new CertifiedCodeDTO(reqData.get("email"), certifiedCode, new Timestamp(System.currentTimeMillis())));

        return true;
    }

    @Override
    public boolean checkEmailFromCertifiedCode(HashMap<String, String> reqData) {
        if(!checkCertifiedCodeToEmail(reqData.get("email"), reqData.get("certifiedCode"))){
            throw new CustomException(ExceptionStatus.CERTIFIED_CODE_WRONG);
        }
        return true;
    }

    @Override
    public synchronized String registerMember(RegisterMemberDTO registerMemberDTO) throws Exception {
        // 아이디 중복체크
        if(!duplicatedCheckLoginId(registerMemberDTO.getLoginId())){
            throw new CustomException(ExceptionStatus.LOGIN_ID_DUPLICATED);
        }

        // 이메일 중복체크
        if(!duplicatedCheckEmail(registerMemberDTO.getEmail())){
            throw new CustomException(ExceptionStatus.EMAIL_DUPLICATED);
        }

        // 인증코드 체크
        if(!checkCertifiedCodeToEmail(registerMemberDTO.getEmail(), registerMemberDTO.getCertifiedCode())){
            throw new CustomException(ExceptionStatus.CERTIFIED_CODE_WRONG);
        }

        // 아이디, 비밀번호, 이메일 유효성 검사 ex) 아이디 6자이상 영어, 비밀번호 8자 이상
        // loginId 6자 이하 OR loginId 30자 이상
        if(registerMemberDTO.getLoginId().length() < 6 || registerMemberDTO.getLoginId().length() > 30){
            throw new CustomException(ExceptionStatus.LOGIN_ID_LENGTH);
        }
        // password 8자 이하
        if(registerMemberDTO.getPassword().length() < 8 ){
            throw new CustomException(ExceptionStatus.PASSWORD_LENGTH);
        }
        // 이메일 유효성 검사
        if(!Validate.isEmail(registerMemberDTO.getEmail())){
            throw new CustomException(ExceptionStatus.EMAIL_WRONG_PATTERN);
        }

        // password = hash(평문 + salt) 설정
        registerMemberDTO.setSalt(sha256.generateSalt());
        registerMemberDTO.setPassword(sha256.hash(registerMemberDTO.getPassword(), registerMemberDTO.getSalt()));

        rao.registerMember(registerMemberDTO);

        return registerMemberDTO.getLoginId();
    }

    public boolean duplicatedCheckLoginId(String loginId) {
        if(rao.checkLoginIdDuplicated(loginId) == null){
            return true;
        }
        return false;
    }

    public boolean duplicatedCheckEmail(String email) {
        if(rao.checkEmailDuplicated(email) == null){
            return true;
        }
        return false;
    }

    public boolean checkCertifiedCodeToEmail(String email, String certifiedCode) {
        if(certifiedCodeRepository.checkEMailFromCertifiedCode(email, certifiedCode)){
            return true;
        }
        return false;
    }

    public static String generateCertifiedCode() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();

        return IntStream.range(0, 10)
                .mapToObj(i -> characters.charAt(random.nextInt(characters.length())))
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public boolean sendMail(String email, String certifiedCode){
        final String homepage = "http://211.205.47.30:9999/web";
        final String logo = "https://nacredit.kz/wp-content/uploads/2022/12/soso.png";
        final String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("소소한 프로젝트 <soso.project@gmail.com>");
            helper.setTo(email);
            helper.setSubject("SOSO PROJECT에서 인증코드를 알려드립니다.");

            // 개선된 이메일 내용
            String emailContent = "<html><body>";
            emailContent += "<center style=\"width: 100%; background-color: #F5F5F5;\">\n" +
                    "  <div style=\"display: none; font-size: 1px;max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n" +
                    "     &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;\n" +
                    "  </div>\n" +
                    "  <div style=\"width:100%; max-width: 480px; margin: 0 auto;\">\n" +
                    "    <!-- BEGIN BODY -->\n" +
                    "    <table align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"margin: auto;max-width: 480px;\">\n" +
                    "      <tbody><tr><td valign=\"top\" style=\"padding: 5em 3% 0 3%;max-width: 480px;\">\n" +
                    "          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"text-align: center;max-width: 480px;\">\n" +
                    "            <tbody><tr><td style=\"text-align: center;\">\n" +
                    "                <h1 style=\"padding-bottom: 10px;margin: 0; text-align: center; max-width: 480px; display: inline-block;\">\n" +
                    "                  <a href=\"" + homepage + "\" target=\"_blank\" rel=\"noreferrer noopener\">\n" +
                    "                    <img src=\"" + logo + "\" alt=\"soso\" style=\"width: 168px; margin: 0; display: block;\" loading=\"lazy\">\n" +
                    "                  </a>\n" +
                    "                </h1>\n" +
                    "              </td></tr>\n" +
                    "          </tbody></table>\n" +
                    "        </td></tr><!-- end tr -->\n" +
                    "      <tr><td valign=\"middle\" style=\"padding:2em 0 5em 0;\">\n" +
                    "          <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #fff; border-collapse: collapse; border-radius: 4px; border: 1px solid #e1e1e1; border-style: hidden; overflow: hidden; box-shadow: 0 0 0 1px #e1e1e1; max-width: 460px;\">\n" +
                    "            <tbody><tr><td style=\"padding: 6%; text-align: left;\">\n" +
                    "                <div>\n" +
                    "                  <h2 style=\"font-family: 'Pretendard',pretendard,Noto Sans CJK KR,Apple SD Gothic Neo,'나눔고딕','맑은고딕',sans-serif; color: #000; font-size: 24px; margin-bottom: 0; font-weight: 700; line-height: 1.5;letter-spacing: -0.03rem;\">\n" +
                    "\n" +
                    "                    <img src=\"https://i.ibb.co/N1SX1tQ/img-mail-mobile.png\" alt=\"soso\" style=\"width: 60px; margin-bottom: 1em; display: block;\" loading=\"lazy\">\n" +
                    "\n" +
                    "                    <p style=\"margin:0;margin-top:20px\">SOSO PROJECT에서<br>인증코드를 알려드립니다.</p>\n" +
                    "                  </h2>\n" +
                    "                </div>\n" +
                    "              </td></tr>\n" +
                    "            <tr><td style=\"text-align: center; padding: 0 2em 2em;\">\n" +
                    "                <div style=\"max-width: 100%; margin: 0 auto; padding: 3%; background-color: #FAFAFA; border-radius: 4px;\">\n" +
                    "                  <h4 style=\"font-size: 36px; font-weight:700;margin-top: 10px; margin-bottom: 10px;font-family:  'Pretendard',pretendard,Noto Sans CJK KR,Apple SD Gothic Neo,'나눔고딕','맑은고딕',sans-serif;\">\n" +
                    "                    <span> "+ certifiedCode + "</span>\n" +
                    "                  </h4>\n" +
                    "                </div>\n" +
                    "                <p style=\"font-size:16px; color:#909090;font-family: 'Pretendard',pretendard,Noto Sans CJK KR,Apple SD Gothic Neo,'나눔고딕','맑은고딕',sans-serif; margin:0;margin-top: 16px;letter-spacing: -0.03rem; text-align: left;\">\n" +
                    "                  인증 번호를 인증 번호 입력 창에 입력하시기 바랍니다.\n" +
                    "                </p>\n" +
                    "              </td></tr>\n" +
                    "\n" +
                    "            <tr><td style=\"text-align: left; padding: 1em 6% 6%;\">\n" +
                    "                <p style=\"font-size:16px; color:#000000;font-family: 'Pretendard',pretendard,Noto Sans CJK KR,Apple SD Gothic Neo,'나눔고딕','맑은고딕',sans-serif; margin:0;margin-bottom:10px;letter-spacing: -0.03rem; text-align: left;\">\n" +
                    "                  인증번호 정보\n" +
                    "                </p>\n" +
                    "                <div style=\" margin: 0 auto; max-width: 100%; padding: 3%;; background-color: #FAFAFA; border-radius: 4px;\">\n" +
                    "                  <p style=\"margin:0;\">\n" +
                    "                      <span style=\"font-size:16px; color:#98A2B3;font-family: 'Pretendard',pretendard,Noto Sans CJK KR,Apple SD Gothic Neo,'나눔고딕','맑은고딕',sans-serif;\">&nbsp;&nbsp;\n" +
                    "                        요청일시\n" +
                    "                      </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\n" +
                    "                    <span style=\"font-size:16px; color:#000000;font-family: 'Pretendard',pretendard,Noto Sans CJK KR,Apple SD Gothic Neo,'나눔고딕','맑은고딕',sans-serif;\">" + currentTime + "</span>\n" +
                    "                  </p>\n" +
                    "                </div>\n" +
                    "              </td></tr>\n" +
                    "\n" +
                    "\n" +
                    "          </tbody></table>\n" +
                    "        </td></tr><!-- end tr -->\n" +
                    "      <!-- 1 Column Text + Button : END -->\n" +
                    "    </tbody></table>\n" +
                    "\n" +
                    "\n" +
                    "  </div>\n" +
                    "</center>";
            emailContent += "</body></html>";

            helper.setText(emailContent, true);
            javaMailSender.send(message);

            // 무작위 코드를 repository에 저장
            certifiedCodeRepository.repository.add(
                    new CertifiedCodeDTO(email, certifiedCode, new Timestamp(System.currentTimeMillis())));
            return true;
        } catch (MessagingException e) {
            throw new CustomException(ExceptionStatus.FAIL_SEND_MAIL);
        }
    }

}
