package com.towne.framework.core.utils.checkcode;

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedAndShearedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.RandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class CaptchaEngine extends ListImageCaptchaEngine {
	/**
	 * @see ListImageCaptchaEngine
	 */
	protected void buildInitialFactories() {
		WordGenerator wordGenerator = new RandomWordGenerator("023456789");
		// nteger minAcceptedWordLength, Integer maxAcceptedWordLength,Color[]
		// textColors
		TextPaster textPaster = new RandomTextPaster(4, 5, Color.WHITE);
		// Integer width, Integer height
		BackgroundGenerator backgroundGenerator = new FunkyBackgroundGenerator(
				100, 40);
		// Integer minFontSize, Integer maxFontSize
		FontGenerator fontGenerator = new TwistedAndShearedRandomFontGenerator(
				20, 22);
		WordToImage wordToImage = new ComposedWordToImage(fontGenerator,
				backgroundGenerator, textPaster);
		addFactory(new GimpyFactory(wordGenerator, wordToImage));
	}
}
