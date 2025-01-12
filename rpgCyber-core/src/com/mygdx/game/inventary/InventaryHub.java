package com.mygdx.game.inventary;


	import implementations.inventario.Inventario;
import implementations.inventario.Item;
import box2dLight.Light;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.androidkeys.AndroidInput;
import com.mygdx.game.levels.MyHUD;
import com.mygdx.game.levels.MyLevel;
import com.mygdx.game.text.TextHUB;
import com.mygdx.game.text.TextLevel;

import snake.engine.creators.ScreenCreator;
import snake.engine.creators.WorldSettings;
import snake.engine.models.HUD;
import snake.hud.SnakeHUD;


	/**                               Developed By:
	 *                                  NoDark
	 *                               sessaGlasses
	 *                               
	 * <br> Example menu for SnakeEngine </br>
	 * @author Mr.Strings
	 */

	public class InventaryHub  extends HUD {
		private BitmapFont font;
		private GlyphLayout layout;
		Light light;
		private float w, h;
		String instructions[]; //will be changed to buttons
		private int i = 0, ini = 0, end = 9;
		private Inventario inv = Inventario.getInstancia();
		public InventaryHub() {
	
			this.font = new BitmapFont(Gdx.files.internal("ak_sc_o.fnt"), false);
			this.layout = new GlyphLayout();
			for(int i = 0; i<12;i++)


			w = WorldSettings.getWorldWidth();
			h = WorldSettings.getWorldHeight();
			layout.width = w;
			layout.height = h;
			
			instructions = new String[10];
		}

		@Override
		public void show() {
			// TODO Auto-generated method stub
		}
		
		/** updates Screen logic */
		@Override
		public void act(float delta) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)||(AndroidInput.getEnterB()&&Gdx.input.justTouched())) {
				try{inv.remover_item(inv.getMochila(ini+i,ini + i+ 1).get(0).getName());}
				catch(Exception e){
					e.printStackTrace();
				}
			}if (Gdx.input.isKeyPressed(Input.Keys.BACKSPACE)||(AndroidInput.getExit())&&Gdx.input.justTouched()) {
					try {
						ScreenCreator.backToPrevious();
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
			
			if(Gdx.input.isKeyJustPressed(Input.Keys.UP)||(AndroidInput.getUpB()&&Gdx.input.justTouched())){
		
				if(i <= 0){
					i = 0;
					if(ini<=0){
						ini = 0;
						end = 7;
					}else{
						end--;
						ini--;
					}	
				}else{
					i--;
				}
			}
			if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)||(AndroidInput.getDownB()&&Gdx.input.justTouched())){
			
				if(i >= 7){
					i = 7;
					if(end>=39){
						end = 39;
						ini = 32;
					
					}else{
						end++;
						ini++;
					}
						
				}else{
					i++;
				}
			}
			for(int i = 0;i<8;i++){
				try{
				instructions[i] = (ini + i+1  + ".   " + inv.getMochila(ini, end).get(i).getName());
				}catch(Exception e){
					instructions[i] = (ini + i+1  + ".   " + "----------------------------------");
				}
			}
		}
		
		
		/** Draws figures on Screen */
		@Override
		public void draw(Batch batch, float parentAlpha){
			//Drawing touch input
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
				font.draw(batch, "You're touching it! (maybe pressing space button).", 0, 80);

			//Drawing instructions
			for(int j = 0 ; j<8;j++){
				if(i==j){
					font.setColor(Color.RED);
					layout.setText(font, instructions[j]);
					font.draw(batch, layout, 320 - layout.width / 2 +345, 240- layout.height / 2 +410-j*75);
					font.setColor(Color.WHITE);
				}else{
					layout.setText(font, instructions[j]);
					font.draw(batch, layout, 320 - layout.width / 2 +345, 240 - layout.height / 2 + 410-j*75);
				}
			
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

