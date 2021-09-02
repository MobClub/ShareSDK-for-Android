package cn.sharesdk.demo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {
	private static ExecutorService executorService = Executors.newSingleThreadExecutor();

	public static final void execute(Runnable runnable) {
		executorService.execute(runnable);
	}
}
