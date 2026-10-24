package com.example.studentregister.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiExceptionResponse {

	private List<Message> message;

	private HttpStatus status;

	private Date time;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Message{
		private String title;

		private String userData;

		private String extraData;

		public Message(String title, String userData) {
			this.title = title;
			this.userData = userData;
		}
	}

}
