package cn.sharesdk.demo.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {
	public static ExecutorService executorService = Executors.newSingleThreadExecutor();
}
