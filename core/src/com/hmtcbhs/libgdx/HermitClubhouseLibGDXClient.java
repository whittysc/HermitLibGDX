package com.hmtcbhs.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class HermitClubhouseLibGDXClient extends ApplicationAdapter {
	private static final float CAMERA_WIDTH = 5.0f;
	private static final float CAMERA_HEIGHT = 3.0f;
	
	private static final float BUTTON_SIZE = 1.0f;
	private static final float BUTTONS_ORIGIN_X = 1.0f;
	private static final float BUTTONS_ORIGIN_Y = 1.0f;
	
	private ImageButton buttonX;
	private ImageButton buttonY;
	private Stage stage;
	
	@Override
	public void create(){
		stage = new Stage(new StretchViewport(CAMERA_WIDTH, CAMERA_HEIGHT));
		Gdx.input.setInputProcessor(stage);
		buildButtons();
	}
	
	public void buildButtons(){
		//Create a table for the X/Y buttons to live in
		// <->X<->Y<->, hence the camera is 5 units wide
		Table buttonTable = new Table();
		buttonTable.defaults().width(BUTTON_SIZE);
		buttonTable.defaults().height(BUTTON_SIZE);
		buttonTable.bottom().left();
		buttonTable.padLeft(BUTTONS_ORIGIN_X).padBottom(BUTTONS_ORIGIN_Y);
		
		buttonX = new ImageButton(
				new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttonX_up.png")))),
				new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttonX_down.png")))));
		//TODO: Add listeners to the button
		buttonTable.add(buttonX).padRight(BUTTON_SIZE);
		
		buttonY = new ImageButton(
				new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttonY_up.png")))),
				new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("buttonY_down.png")))));
		//TODO: Add listeners to the button
		buttonTable.add(buttonY);
		
		buttonTable.setFillParent(true);
		stage.addActor(buttonTable);
	}
	
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}
}
