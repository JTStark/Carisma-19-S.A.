package com.mygdx.game.menus;


	import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import implementations.save.Save;
import box2dLight.Light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.mygdx.game.androidkeys.AndroidInput;
import com.mygdx.game.animate.Player;
import com.mygdx.game.levels.MyHUD;
import com.mygdx.game.levels.MyLevel;
import com.mygdx.game.text.TextHUB;
import com.mygdx.game.text.TextLevel;

import snake.engine.creators.ScreenCreator;
import snake.engine.models.HUD;
import snake.hud.SnakeHUD;


	/**                               Developed By:
	 *                                  NoDark
	 *                               sessaGlasses
	 *                               
	 * <br> Example menu for SnakeEngine </br>
	 * @author Mr.Strings
	 */

	public class MyHub  extends HUD {
		private BitmapFont font;
		private GlyphLayout layout;
		Light light;
		private float w, h;
		String instructions[]; //will be changed to buttons
		private int i = 0;
		public MyHub() {
			
	
			this.font = new BitmapFont(Gdx.files.internal("ak_sc_o.fnt"), false);
			this.layout = new GlyphLayout();



			w = Gdx.graphics.getWidth();
			h = Gdx.graphics.getHeight();
			
			instructions = new String[3];
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
		}
		
		/** updates Screen logic */
		@Override
		public void act(float delta) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
				Gdx.graphics.setDisplayMode(640, 480, true);
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
				Gdx.graphics.setDisplayMode(640, 480, false);
			}
			if (Gdx.input.isKeyPressed(Input.Keys.ENTER) || AndroidInput.getEnterB()) {
				if(i ==0){	
					try {
						ScreenCreator.addAndGo(new MyLevel("Mapas/MapaExterno.tmx"), new MyHUD("LevelData"));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(i ==1){
					try {
						Player.listaP = Save.loadGame();
						if(Player.listaP != null){
							try {
								ScreenCreator.addAndGo(new MyLevel("Mapas/MapaExterno.tmx"), new MyHUD("LevelData"));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} catch (SAXException | IOException
							| ParserConfigurationException e) {
						Player.getP();
					}
					
				}
				if(i ==2){
					Gdx.app.exit();
				}
			}if (Gdx.input.isKeyPressed(Input.Keys.R)) {
				if(i ==0){	
					try {
						ScreenCreator.addAndGo(new TextLevel("E comeca a aventura"), new TextHUB());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(i ==2){
					Gdx.app.exit();
				}
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
				if(i <= 0)
					i = 0;
				else{
					i--;
				}
			}
			if(AndroidInput.getUpB()){

				if(Gdx.input.justTouched()){
				if(i <= 0)
					i = 0;
				else{
					i--;
				}
				}
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
				if(i >= 2)
					i = 2;
				else{
					i++;
				}
			}
			if(AndroidInput.getDownB()){
				if(Gdx.input.justTouched()){
				if(i >= 2)
					i = 2;
				else{
					i++;
				}
				}
			}
			instructions[0] = "New Game";
			instructions[1] = "Load Game";
			instructions[2] = "Exit";
		}
		
		
		/** Draws figures on Screen */
		@Override
		public void draw(Batch batch, float parentAlpha){
			//Drawing touch input
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
				font.draw(batch, "You're touching it! (maybe pressing space button).", 0, 80);

			//Drawing instructions
			if(i==0){
				font.setColor(Color.RED);
				layout.setText(font, instructions[0]);
				font.draw(batch, layout, 100, 200);
				font.setColor(Color.WHITE);
			}else{
				layout.setText(font, instructions[0]);
				font.draw(batch, layout, 100, 200);
			}
			if(i==1){
				font.setColor(Color.RED);
				layout.setText(font, instructions[1]);
				font.draw(batch, layout, 100, 150);
				font.setColor(Color.WHITE);
			}else{
				layout.setText(font, instructions[1]);
				font.draw(batch, layout, 100, 150);
			}
			if(i==2){
				font.setColor(Color.RED);
				layout.setText(font, instructions[2]);
				font.draw(batch, layout, 100,100);
				font.setColor(Color.WHITE);
			}else{
				layout.setText(font, instructions[2]);
				font.draw(batch, layout, 100,100);
			}
		}

		@Override
		public void dispose() {
			font.dispose();
		}

		@Override
		public void hide() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void pause() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void resume() {
			// TODO Auto-generated method stub
			
		}
	}

