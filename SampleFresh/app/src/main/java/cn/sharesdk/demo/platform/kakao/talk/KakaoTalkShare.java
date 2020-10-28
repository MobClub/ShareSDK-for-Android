package cn.sharesdk.demo.platform.kakao.talk;


import com.mob.MobSDK;
import java.util.HashMap;
import cn.sharesdk.demo.entity.ResourcesManager;
import cn.sharesdk.demo.utils.DemoUtils;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.kakao.talk.KakaoTalk;

/**
 * Created by yjin on 2017/6/22.
 */

public class KakaoTalkShare {
	private PlatformActionListener platformActionListener;

	public KakaoTalkShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
		DemoUtils.isValidClient("com.kakao.talk");
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(KakaoTalk.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setText("kakao Text share Test");
		sp.setShareType(Platform.KAKAO_TEXT_TEMPLATE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(sp);
	}


	public void shareUrl() {
		Platform platform = ShareSDK.getPlatform(KakaoTalk.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setShareType(Platform.KAKAO_URL_TEMPLATE);
		sp.setUrl("https://www.baidu.com");
		platform.setPlatformActionListener(platformActionListener);
		platform.share(sp);
	}

	/**
	 * KAKAO_FEED_TEMPLATE
	 * **/
	public void kakaoFeedShare() {
		Platform platform = ShareSDK.getPlatform(KakaoTalk.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setShareType(Platform.KAKAO_FEED_TEMPLATE);
		sp.setTitle("kakao title");
		sp.setImageUrl("https://cdn.pixabay.com/photo/2019/08/08/11/33/stingray-4392776_960_720.jpg");
		sp.setKakaoWebUrl("https://developers.kakao.com");
		sp.setKakaoMobileWebUrl("https://developers.kakao.com");
		sp.setText("descrption");
		sp.setKakaoLikecount(286);
		sp.setKakaoCommentcount(45);
		sp.setKakaoSharecount(845);
		sp.setKakaoAddbuttonTitle("button01 title");
		sp.setKakaoAddbuttonWeburl("https://www.baidu.com");
		sp.setKakaoAddbuttonMobileweburl("https://www.mob.com");
		platform.setPlatformActionListener(platformActionListener);
		platform.share(sp);
	}


	/**
	 * commerceTemplate
	 * **/
	public void kakaoCommerceShare() {
		Platform platform = ShareSDK.getPlatform(KakaoTalk.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setShareType(Platform.KAKAO_COMMERCE_TEMPLATE);
		sp.setTitle("신메뉴 출시️ 체리블라썸라떼");
		sp.setImageUrl("https://cdn.pixabay.com/photo/2019/08/08/11/33/stingray-4392776_960_720.jpg");
		sp.setKakaoWebUrl("https://developers.kakao.com");
		sp.setKakaoMobileWebUrl("https://developers.kakao.com");
		sp.setText("이번 주는 체리블라썸라떼 1+1");
		sp.setKakaoRegularprice(12345);
		sp.setKakaoProductname("체리블라썸라떼 1+1");
		sp.setKakaoDiscountprice(10000);
		sp.setKakaoDiscountrate(20);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(sp);
	}

	/**
	 *  customTemplate
	 * **/
	public void kakaoCustomShare() {
		HashMap<String, String> templateArgs = new HashMap<String, String>();
		templateArgs.put("${title}", "프로방스 자동차 여행");
		templateArgs.put("${description}", "매년 7~8월에 프로방스 발랑솔을 중심으로 라벤더가 만개한다. " +
				"이 길을 라벤더로드라고 하며 라벤더와 해바라기 밭이 가득찬 풍경을 어디서나 볼 수 있다.");

		Platform platform = ShareSDK.getPlatform(KakaoTalk.NAME);
		Platform.ShareParams sp = new Platform.ShareParams();
		sp.setKakaoCustomTemplateId("11818");
		sp.setKakaoCustomTemplate(templateArgs);
		sp.setShareType(Platform.KAKAO_CUSTOM_TEMPLATE);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(sp);
	}


}
