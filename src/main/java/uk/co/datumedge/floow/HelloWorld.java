package uk.co.datumedge.floow;

import com.fasterxml.jackson.annotation.JsonCreator;

public class HelloWorld {
	private final String content;

	@JsonCreator
	public HelloWorld(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
}