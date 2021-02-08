package es.enxenio.sife1701.sms;

public interface SmsService {
	
	public void sendSms(String[] phoneNumber, String text);
	
	public void sendSms(String phoneNumber, String text);

}
