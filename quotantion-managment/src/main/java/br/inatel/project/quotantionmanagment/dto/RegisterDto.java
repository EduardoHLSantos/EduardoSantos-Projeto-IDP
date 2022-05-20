package br.inatel.project.quotantionmanagment.dto;

public class RegisterDto {
	
	private String host;
	private int port;
	
	public RegisterDto() {
		host = "localhost";
		port = 8081;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

}