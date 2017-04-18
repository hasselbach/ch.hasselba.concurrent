package ch.hasselba.service;

import java.util.regex.Pattern;

public class RegexPatternCache {

	private Pattern patternFiledata;

	public RegexPatternCache() {
	}

	public Pattern getPatternFiledata() {

		if (this.patternFiledata == null) {
			this.patternFiledata = Pattern.compile("<filedata\n?>\n([a-zA-Z0-9/=+\\s]*)</filedata>");
		}

		return this.patternFiledata;
	}

}
