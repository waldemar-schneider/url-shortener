package de.flaviait.redis.example;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

@RestController(value = "/url")
public class UrlShortenerController {

	private final static Logger LOGGER = LoggerFactory.getLogger(UrlShortenerController.class);

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@RequestMapping
	public void findValue(final @RequestParam String key, HttpServletResponse response) throws IOException {
		LOGGER.trace("looking for a key ({}) ..", key);
		if (stringRedisTemplate.hasKey(key)) {
			final String value = stringRedisTemplate.opsForValue().get(key);
			response.sendRedirect(value);
            return;
		}

		throw new NotFoundException(String.format("the key (%s) you are looking for does not exist", key));
	}

	@RequestMapping(method = RequestMethod.POST)
	public HttpEntity<String> putValue(@RequestBody String url) {
		Assert.hasText(url);
        final String[] schemes = {"http", "https"};
        final UrlValidator validator = new UrlValidator(schemes);

		if (!validator.isValid(url)) {
			throw new IllegalArgumentException(String.format("The given URL was invalid (%s", url));
		}

		String key = Hashing.md5().hashString(url, Charset.forName("UTF-8")).toString();
		stringRedisTemplate.opsForValue().append(key, url);
		LOGGER.trace("key ({}) and url ({}) are inserted to redis database..", key, url);

		return new ResponseEntity<>(key, HttpStatus.CREATED);
	}
}
