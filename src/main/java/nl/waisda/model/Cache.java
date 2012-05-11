package nl.waisda.model;

import java.util.Date;

public class Cache<T> implements Value<T> {

	private Value<T> content;
	private long maxAgeMs;

	private T cachedValue;
	private long lastUpdate;

	public Cache(Value<T> content, long maxAgeMs) {
		this.content = content;
		this.maxAgeMs = maxAgeMs;
	}

	private synchronized long getCurrentAge() {
		return new Date().getTime() - lastUpdate;
	}

	public synchronized T get() {
		if (getCurrentAge() > maxAgeMs) {
			cachedValue = content.get();
			lastUpdate = new Date().getTime();
		}
		return cachedValue;
	}

	public synchronized void invalidate() {
		lastUpdate = 0;
	}

}
