package com.towne.framework.core.utils.checkcode;

import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;

public class CaptchaService extends DefaultManageableImageCaptchaService {
	public CaptchaService() {
		super();
	}

	public CaptchaService(int minSeconds, int maxStoreSize, int loadBefore) {
		super(minSeconds, maxStoreSize, loadBefore);
	}

	public CaptchaService(CaptchaStore captchaStore,
			CaptchaEngine captchaEngine, int minSeconds, int maxStoreSize,
			int loadBefore) {
		super(captchaStore, captchaEngine, minSeconds, maxStoreSize, loadBefore);
	}

	@Override
	public Boolean validateResponseForID(String ID, Object response) {
		Boolean isHuman;
		try {
			isHuman = super.validateResponseForID(ID, response);
		} catch (Exception e) {
			isHuman = false;
		}
		return isHuman;
	}
}
