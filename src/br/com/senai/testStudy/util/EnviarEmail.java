package br.com.senai.testStudy.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import br.com.senai.testStudy.model.Mensagem;


public class EnviarEmail {
	public void enviarEmail(Mensagem msg){
		SimpleEmail email = new SimpleEmail();
		try {
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(587);
			email.addTo(msg.getRemetente().getEmail(), msg.getRemetente().getNome());
			email.setFrom(msg.getDestinatario().getEmail(), msg.getDestinatario().getNome());
			email.setSubject(msg.getTituloMensagem());
			email.setMsg(msg.getCorpoMensagem());
			email.send();
		} catch (EmailException e) {
		throw new RuntimeException(e);
		}
		
	}
}
