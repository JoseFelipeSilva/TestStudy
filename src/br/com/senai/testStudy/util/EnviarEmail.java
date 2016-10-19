package br.com.senai.testStudy.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class EnviarEmail {
	public void enviarEmail(String msg){
		SimpleEmail email = new SimpleEmail();
		try {
			email.addTo("falecom.josefelipe@hotmail.com", "Jos�1");
			email.setFrom("falecom.josefelipe@gmail.com", "Jos�2");
			email.setSubject("Mensagem de teste");
			email.setMsg(msg);
			email.send();
		} catch (EmailException e) {
		throw new RuntimeException(e);
		}
		
	}
}
