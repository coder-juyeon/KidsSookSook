package com.app.member;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.app.Action;
import com.app.Result;
import com.app.member.dao.MemberDAO;

public class FindPwActionController implements Action {

	@Override
	public Result execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		MemberDAO memberDAO = new MemberDAO();
		String userEmail = null, userPassword = null;
		Result result = new Result();
		
		userEmail = req.getParameter("userEmail");
		
		userPassword = memberDAO.findPw("userEmail");
		if(userEmail != null) {
	         // 메일 인코딩
	           final String bodyEncoding = "UTF-8"; //콘텐츠 인코딩
	           
	           //원하는 메일 제목 작성
	           String subject = "KidsSookSook 아이디 찾기 이메일 발송";
	           
	           //****************건들지 마세요********************
	           String fromEmail = "jh940412@gmail.com";
	           String fromUsername = "kidsSookSook";
	           
	           //받는 이메일 req.getParameter("email") : 사용자가 입력한 이메일로 대체
	           String toEmail = userEmail; // 콤마(,)로 여러개 나열
	           
	         //****************건들지 마세요********************
	           final String username = "jh940412@gmail.com";         
	           final String password = "msoulgstclzdqnnw";
	           //*********************************************
	           
	           // 메일에 출력할 텍스트
	           String html = null;
	           StringBuffer sb = new StringBuffer();
	           sb.append("<h3>KidsSookSook 비밀번호 찾기</h3>\n");
	           sb.append("회원님의 비밀번호는" + userPassword + "입니다");    
	           html = sb.toString();
	           
	           // 메일 옵션 설정
	           Properties props = new Properties();    
	           props.put("mail.smtp.starttls.enable", "true");
	           props.put("mail.smtp.host", "smtp.gmail.com");
	           props.put("mail.smtp.auth", "true");
	           props.put("mail.smtp.port", "587");
	           props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	           props.put("mail.smtp.ssl.protocols", "TLSv1.2");
	           
	           try {
	             // 메일 서버  인증 계정 설정
	             Authenticator auth = new Authenticator() {
	               protected PasswordAuthentication getPasswordAuthentication() {
	                 return new PasswordAuthentication(username, password);
	               }
	             };
	             
	             // 메일 세션 생성
	             Session session = Session.getDefaultInstance(props, auth);
	             
	             // 메일 송/수신 옵션 설정
	             Message message = new MimeMessage(session);
	             message.setFrom(new InternetAddress(fromEmail, fromUsername));
	             message.setRecipients(RecipientType.TO, InternetAddress.parse(toEmail, false));
	             message.setSubject(subject);
	             message.setSentDate(new Date());
	             
//	             // 메일 콘텐츠 설정
	             Multipart mParts = new MimeMultipart();
	             MimeBodyPart mTextPart = new MimeBodyPart();
	             MimeBodyPart mFilePart = null;
	    //    
//	             // 메일 콘텐츠 - 내용
	             mTextPart.setText(html, bodyEncoding, "html");
	             mParts.addBodyPart(mTextPart);
//	                   
//	             // 메일 콘텐츠 설정
	             message.setContent(mParts);
	        
	             // 메일 발송
	             Transport.send( message );
	             
	           } catch ( Exception e ) {
	             e.printStackTrace();
	           }
	          
	           result.setPath("/login.member");
	           result.setRedirect(true);
	           
	      } else {
	         result.setPath("/findPw.member");
	         result.setRedirect(true);
	      }
		return null;
	}

}
