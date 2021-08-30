package com.dxc.eproc.estimate.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.hamcrest.TypeSafeMatcher;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

// TODO: Auto-generated Javadoc
/**
 * The Class TestUtil.
 */
public final class TestUtil {

	/** The Constant MAPPER. */
	private static final ObjectMapper MAPPER = createObjectMapper();

	/**
	 * Instantiates a new test util.
	 */
	private TestUtil() {
	}

	/**
	 * Creates the object mapper.
	 *
	 * @return the object mapper
	 */
	private static ObjectMapper createObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS, false);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		mapper.registerModule(new JavaTimeModule());
		return mapper;
	}

	/**
	 * Same instant.
	 *
	 * @param date the date
	 * @return the zoned date time matcher
	 */
	public static ZonedDateTimeMatcher sameInstant(ZonedDateTime date) {
		return new ZonedDateTimeMatcher(date);
	}

	/**
	 * Same number.
	 *
	 * @param number the number
	 * @return the number matcher
	 */
	public static NumberMatcher sameNumber(BigDecimal number) {
		return new NumberMatcher(number);
	}

	/**
	 * Convert object to json bytes.
	 *
	 * @param object the object
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
		return MAPPER.writeValueAsBytes(object);
	}

	/**
	 * Creates the byte array.
	 *
	 * @param size the size
	 * @param data the data
	 * @return the byte[]
	 */
	public static byte[] createByteArray(int size, String data) {
		byte[] byteArray = new byte[size];
		for (int i = 0; i < size; i++) {
			byteArray[i] = Byte.parseByte(data, 2);
		}
		return byteArray;
	}

	/**
	 * Equals verifier.
	 *
	 * @param <T>   the generic type
	 * @param clazz the clazz
	 * @throws Exception the exception
	 */
	public static <T> void equalsVerifier(Class<T> clazz) throws Exception {
		T domainObject1 = clazz.getConstructor().newInstance();
		assertThat(domainObject1.toString()).isNotNull();
		assertThat(domainObject1).isEqualTo(domainObject1);
		assertThat(domainObject1).hasSameHashCodeAs(domainObject1);
		// Test with an instance of another class
		Object testOtherObject = new Object();
		assertThat(domainObject1).isNotEqualTo(testOtherObject);
		assertThat(domainObject1).isNotEqualTo(null);
		// Test with an instance of the same class
		T domainObject2 = clazz.getConstructor().newInstance();
		assertThat(domainObject1).isNotEqualTo(domainObject2);
		// HashCodes are equals because the objects are not persisted yet
		assertThat(domainObject1).hasSameHashCodeAs(domainObject2);
	}

	/**
	 * Creates the formatting conversion service.
	 *
	 * @return the formatting conversion service
	 */
	public static FormattingConversionService createFormattingConversionService() {
		DefaultFormattingConversionService dfcs = new DefaultFormattingConversionService();
		DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
		registrar.setUseIsoFormat(true);
		registrar.registerFormatters(dfcs);
		return dfcs;
	}

	/**
	 * Find all.
	 *
	 * @param <T>  the generic type
	 * @param em   the em
	 * @param clss the clss
	 * @return the list
	 */
	public static <T> List<T> findAll(EntityManager em, Class<T> clss) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(clss);
		Root<T> rootEntry = cq.from(clss);
		CriteriaQuery<T> all = cq.select(rootEntry);
		TypedQuery<T> allQuery = em.createQuery(all);
		return allQuery.getResultList();
	}

	/**
	 * The Class ZonedDateTimeMatcher.
	 */
	public static class ZonedDateTimeMatcher extends TypeSafeDiagnosingMatcher<String> {

		/** The date. */
		private final ZonedDateTime date;

		/**
		 * Instantiates a new zoned date time matcher.
		 *
		 * @param date the date
		 */
		public ZonedDateTimeMatcher(ZonedDateTime date) {
			this.date = date;
		}

		/**
		 * Matches safely.
		 *
		 * @param item                the item
		 * @param mismatchDescription the mismatch description
		 * @return true, if successful
		 */
		@Override
		protected boolean matchesSafely(String item, Description mismatchDescription) {
			try {
				if (!date.isEqual(ZonedDateTime.parse(item))) {
					mismatchDescription.appendText("was ").appendValue(item);
					return false;
				}
				return true;
			} catch (DateTimeParseException e) {
				mismatchDescription.appendText("was ").appendValue(item)
						.appendText(", which could not be parsed as a ZonedDateTime");
				return false;
			}
		}

		/**
		 * Describe to.
		 *
		 * @param description the description
		 */
		@Override
		public void describeTo(Description description) {
			description.appendText("a String representing the same Instant as ").appendValue(date);
		}
	}

	/**
	 * The Class NumberMatcher.
	 */
	public static class NumberMatcher extends TypeSafeMatcher<Number> {

		/** The value. */
		final BigDecimal value;

		/**
		 * Instantiates a new number matcher.
		 *
		 * @param value the value
		 */
		public NumberMatcher(BigDecimal value) {
			this.value = value;
		}

		/**
		 * Describe to.
		 *
		 * @param description the description
		 */
		@Override
		public void describeTo(Description description) {
			description.appendText("a numeric value is ").appendValue(value);
		}

		/**
		 * Matches safely.
		 *
		 * @param item the item
		 * @return true, if successful
		 */
		@Override
		protected boolean matchesSafely(Number item) {
			BigDecimal bigDecimal = asDecimal(item);
			return bigDecimal != null && value.compareTo(bigDecimal) == 0;
		}

		/**
		 * As decimal.
		 *
		 * @param item the item
		 * @return the big decimal
		 */
		private static BigDecimal asDecimal(Number item) {
			if (item == null) {
				return null;
			}
			if (item instanceof BigDecimal) {
				return (BigDecimal) item;
			} else if (item instanceof Long) {
				return BigDecimal.valueOf((Long) item);
			} else if (item instanceof Integer) {
				return BigDecimal.valueOf((Integer) item);
			} else if (item instanceof Double) {
				return BigDecimal.valueOf((Double) item);
			} else if (item instanceof Float) {
				return BigDecimal.valueOf((Float) item);
			} else {
				return BigDecimal.valueOf(item.doubleValue());
			}
		}
	}

}
