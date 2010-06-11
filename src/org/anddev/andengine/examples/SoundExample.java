package org.anddev.andengine.examples;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.FPSCounter;
import org.anddev.andengine.entity.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.IOnAreaTouchListener;
import org.anddev.andengine.input.touch.ITouchArea;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.view.MotionEvent;

/**
 * @author Nicolas Gramlich
 * @since 11:54:51 - 03.04.2010
 */
public class SoundExample extends BaseExampleGameActivity {
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	// ===========================================================
	// Fields
	// ===========================================================

	private Camera mCamera;
	private Texture mTexture;
	private TextureRegion mTankTextureRegion;

	// ===========================================================
	// Constructors
	// ===========================================================

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public Engine onLoadEngine() {
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera, false));
	}

	@Override
	public void onLoadResources() {
		this.mTexture = new Texture(128, 128);
		TextureRegionFactory.setAssetBasePath("gfx/");
		this.mTankTextureRegion = TextureRegionFactory.createFromAsset(this.mTexture, this, "click_to_unload.png", 0, 0);

		this.getEngine().getTextureManager().loadTexture(this.mTexture);
	}

	@Override
	public Scene onLoadScene() {
		this.getEngine().registerPreFrameHandler(new FPSCounter());

		final Scene scene = new Scene(1);
		scene.setBackgroundColor(0.09804f, 0.6274f, 0.8784f);

		final int x = (CAMERA_WIDTH - this.mTankTextureRegion.getWidth()) / 2;
		final int y = (CAMERA_HEIGHT - this.mTankTextureRegion.getHeight()) / 2;
		final Sprite clickToUnload = new Sprite(x, y, this.mTankTextureRegion);
		clickToUnload.setColor(0.0f, 1.0f, 0.0f);
		scene.getTopLayer().addEntity(clickToUnload);

		scene.registerTouchArea(clickToUnload);
		scene.setOnAreaTouchListener(new IOnAreaTouchListener() {
			@Override
			public boolean onAreaTouched(final ITouchArea pTouchArea, final MotionEvent pSceneMotionEvent) {
				SoundExample.this.mEngine.getTextureManager().unloadTexture(SoundExample.this.mTexture);
				return true;
			}
		});

		return scene;
	}

	@Override
	public void onLoadComplete() {

	}

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
