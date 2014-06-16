package com.hmtcbhs.libgdx;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class HermitClubhouseLibGDXServer extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

	private int mWidth;
	private int mHeight;

	private static final String TAG = "HermitClubhouseLibGDX";
	
	//Set the constants for the absolute values of the speed in either direction
	private static final float SPEED_X = 400.0f;
	private static final float SPEED_Y = 300.0f;

	/*
	 * OneDVelocityVector represents a velocity in a single dimension;
	 * it has a speed and position and can calculate the new position
	 * based on that speed and a given time period. There are also
	 * methods for inverting the speed and setting the new position
	 * to some value.
	 * 
	 * Note that the intelligence related to collision is *outside* of this
	 * class, since this is used almost exclusively for convenience/data storage
	 * and not intelligently deciding what's happening.
	 */
	private class OneDVelocityVector {
		public int mPosition;
		public float mSpeed;

		public OneDVelocityVector(int startPos, float speed){
			mPosition = startPos;
			mSpeed = speed;
		}

		public int calculateNewPosition(float deltaTime){
			return mPosition + (int)(mSpeed * deltaTime);
		}

		public void invertSpeed(){
			mSpeed *= -1;
		}

		public void updatePosition(int newPos){
			mPosition = newPos;
		}

		public int getCurrentPosition(){
			return mPosition;
		}
	};

	//One vector for each of the two coordinates
	private OneDVelocityVector vectorX;
	private OneDVelocityVector vectorY;

	public static native int addThem (int num1, int num2)/*-{
		return $wnd.add(num1, num2);
	}-*/;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		
		//We need to set the LogLevel here because it's automatically
		//set to not print out anything for HTML. We need to get rid
		//of this line later!
		Gdx.app.setLogLevel(Application.LOG_INFO);
		
		//Call into the JavaScript file to find out what 5+6 is!
		int res = addThem(5,6);
		Gdx.app.log(TAG, "Result: "+res);
		
		mWidth = Gdx.graphics.getWidth();
		mHeight = Gdx.graphics.getHeight();

		//Initialize the picture in the middle with +ve velocity in both coordinates
		vectorX = new OneDVelocityVector((mWidth - img.getWidth())/2, SPEED_X);
		vectorY = new OneDVelocityVector((mHeight - img.getHeight())/2, SPEED_Y);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//If the user is pressing 'X' then update the X coordinate
		if (Gdx.input.isKeyPressed(Keys.X)){
			updatePosition(vectorX, Gdx.graphics.getDeltaTime(), mWidth - img.getWidth());	
		}

		//If the user is pressing 'Y' then update the Y coordinate
		if (Gdx.input.isKeyPressed(Keys.Y)){
			updatePosition(vectorY, Gdx.graphics.getDeltaTime(), mHeight - img.getHeight());	
		}

		//Draw the image at the proper position.
		batch.begin();
		batch.draw(img, vectorX.getCurrentPosition(), vectorY.getCurrentPosition());
		batch.end();
	}

	private void updatePosition(OneDVelocityVector velocityVector, float deltaTime, int upperBound){
		//Given a velocity vector (position + speed) as well as the deltaTime and the upper bound for
		//reflection, update the position of the velocity vector to its new state.
		int newPosition = velocityVector.calculateNewPosition(deltaTime);

		if (newPosition > upperBound || newPosition < 0){
			//Collision! Set the position to whatever boundary we hit... 
			if (newPosition > upperBound){
				newPosition = upperBound;
			} else {
				newPosition = 0;
			}

			//... and then invert the speed to bounce off next time.
			velocityVector.invertSpeed();
		}

		//Commit the new position!
		velocityVector.updatePosition(newPosition);
	}
}