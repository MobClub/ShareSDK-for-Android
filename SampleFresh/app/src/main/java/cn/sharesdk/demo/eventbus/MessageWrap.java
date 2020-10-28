package cn.sharesdk.demo.eventbus;

public class MessageWrap {
	public final int message;

	public MessageWrap(int message) {
		this.message = message;
	}

	public int getMessage() {
		return message;
	}
}
