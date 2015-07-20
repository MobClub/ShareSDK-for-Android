package cn.sharesdk.demo;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

public class InLayoutAnim {

	public static LayoutAnimationController getAnimationController() {
		int duration = 300;
		AnimationSet set = new AnimationSet(true);

		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(duration);
		set.addAnimation(animation);

		animation = new TranslateAnimation(
				Animation.RELATIVE_TO_SELF, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.0f);
		animation.setDuration(duration);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);
		controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
		return controller;
	}

}
