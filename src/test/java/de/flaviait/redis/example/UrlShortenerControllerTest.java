package de.flaviait.redis.example;

import com.google.common.hash.Hashing;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;


public class UrlShortenerControllerTest {

	private final static Logger LOGGER = LoggerFactory.getLogger(UrlShortenerControllerTest.class);

	@Test
	public void testUrlHashing() {
		final String url = "http://facebook.com";
		final String hashedString = Hashing.md5().hashString(url, Charset.forName("UTF-8")).toString();

		LOGGER.info("Hashed url value was: {}", hashedString);
		assertThat(hashedString, notNullValue());
	}
}