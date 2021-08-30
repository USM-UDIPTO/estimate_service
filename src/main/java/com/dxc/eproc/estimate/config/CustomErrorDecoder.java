package com.dxc.eproc.estimate.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dxc.eproc.exceptionhandling.CustomMethodArgumentNotValidException;
import com.dxc.eproc.exceptionhandling.FieldErrorVM;
import com.dxc.eproc.exceptionhandling.RecordNotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * The Class CustomErrorDecoder.
 */
public class CustomErrorDecoder implements ErrorDecoder {

	/** The log. */
	private final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);

	/**
	 * Decode.
	 *
	 * @param methodKey the method key
	 * @param response  the response
	 * @return the exception
	 */
	@Override
	public Exception decode(String methodKey, Response response) {
		Reader reader = null;
		ExceptionMessage exceptionMessage = new ExceptionMessage();
		try {
			reader = response.body().asReader(StandardCharsets.UTF_8);
			// Easy way to read the stream and get a String object
			String result = CharStreams.toString(reader);
			// use a Jackson ObjectMapper to convert the Json String into a
			// Pojo
			ObjectMapper mapper = new ObjectMapper();
			// just in case you missed an attribute in the Pojo
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			// init the Pojo
			exceptionMessage = mapper.readValue(result, ExceptionMessage.class);
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {

			// It is the responsibility of the caller to close the stream.
			try {

				if (reader != null) {
					reader.close();
				}

			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

		switch (response.status()) {
		case 400:
			log.info("Feign Exception Handling. Response Code : 400 - {}", exceptionMessage.getTitle());
			return new CustomMethodArgumentNotValidException(exceptionMessage.getTitle(), exceptionMessage.fieldErrors);

		case 404:
			log.info("Feign Exception Handling. Response Code : 404 - {}", exceptionMessage.getTitle());
			return new RecordNotFoundException(exceptionMessage.getTitle(), exceptionMessage.getEntityName());

		default:
			log.info("Feign Exception Handling. Response Code : 400 - {}", exceptionMessage.getTitle());
			return new Exception("Generic error");

		}

	}

	/**
	 * The Class ExceptionMessage.
	 */
	public static class ExceptionMessage {

		/** The entity name. */
		private String entityName;

		/** The status. */
		private int status;

		/** The type. */
		private String type;

		/** The title. */
		private String title;

		/** The message. */
		private String message;

		/** The field errors. */
		private List<FieldErrorVM> fieldErrors;

		/**
		 * Gets the entity name.
		 *
		 * @return the entity name
		 */
		public String getEntityName() {
			return entityName;
		}

		/**
		 * Sets the entity name.
		 *
		 * @param entityName the new entity name
		 */
		public void setEntityName(String entityName) {
			this.entityName = entityName;
		}

		/**
		 * Gets the status.
		 *
		 * @return the status
		 */
		public int getStatus() {
			return status;
		}

		/**
		 * Sets the status.
		 *
		 * @param status the new status
		 */
		public void setStatus(int status) {
			this.status = status;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * Sets the type.
		 *
		 * @param type the new type
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * Gets the title.
		 *
		 * @return the title
		 */
		public String getTitle() {
			return title;
		}

		/**
		 * Sets the title.
		 *
		 * @param title the new title
		 */
		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * Gets the message.
		 *
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}

		/**
		 * Sets the message.
		 *
		 * @param message the new message
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		/**
		 * Gets the field errors.
		 *
		 * @return the field errors
		 */
		public List<FieldErrorVM> getFieldErrors() {
			return fieldErrors;
		}

		/**
		 * Sets the field errors.
		 *
		 * @param fieldErrors the new field errors
		 */
		public void setFieldErrors(List<FieldErrorVM> fieldErrors) {
			this.fieldErrors = fieldErrors;
		}

	}
}
