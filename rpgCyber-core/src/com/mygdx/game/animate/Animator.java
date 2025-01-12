package com.mygdx.game.animate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.mygdx.game.androidkeys.AndroidInput;

public class Animator{

    protected static final int        FRAME_COLS = 10;         
    protected static final int        FRAME_ROWS = 8;         
    private Animax walkRight, walkLeft,walkUp,walkDown;   
    private Animax stopRight, stopLeft,stopUp,stopDown;
    private boolean flagup = false, flagdown = false,flagright = false,flagleft = false;
    private int i;
    protected float x,y, width,height;
    public  Animator(String file) {
    	x = 1;
    	y = 1;
    	width = Gdx.graphics.getWidth()/13;
    	height = Gdx.graphics.getHeight()/8;
    	walkRight = new Animax();
    	/* Anda Para direita */
        walkRight.setTexture(file); 
        walkRight.setTextureRegions(FRAME_ROWS,FRAME_COLS,8,1,8,10);
        walkRight.setAnimation(0.10f);           
        walkRight.setStateTime(0f);
        /*Parado Para direita */
        stopRight = new Animax();
        stopRight.setTexture(file); 
        stopRight.setTextureRegions(FRAME_ROWS,FRAME_COLS,4,1,4,3);
        stopRight.setAnimation(1/3f);              
        stopRight.setStateTime(0f);
        /*Anda Para esquerda */
        walkLeft = new Animax();
        walkLeft.setTexture(file); 
        walkLeft.setTextureRegions(FRAME_ROWS,FRAME_COLS,6,1,6,10);
        walkLeft.setAnimation(0.10f);             
        walkLeft.setStateTime(0f);

        walkLeft.setSpriteBatch(walkRight.getSpriteBatch());
        /*Parado Para esquerda */
        stopLeft = new Animax();
        stopLeft.setTexture(file); 
        stopLeft.setTextureRegions(FRAME_ROWS,FRAME_COLS,2,1,2,3);
        stopLeft.setAnimation(1/3f);              
        stopLeft.setStateTime(0f);
        /*Anda Para Cima */
        walkUp = new Animax();
        walkUp.setTexture(file); 
        walkUp.setTextureRegions(FRAME_ROWS,FRAME_COLS,7,1,7,10);
        walkUp.setAnimation(0.10f);             
        walkUp.setStateTime(0f);
        /*Parado Para Cima */
        stopUp = new Animax();
        stopUp.setTexture(file); 
        stopUp.setTextureRegions(FRAME_ROWS,FRAME_COLS,3,1,3,1);
        stopUp.setAnimation(1f);                
        stopUp.setStateTime(0f);
        /*Anda Para Baixo */
        walkDown = new Animax();
        walkDown.setTexture(file); 
        walkDown.setTextureRegions(FRAME_ROWS,FRAME_COLS,5,1,5,10);
        walkDown.setAnimation(0.10f);             
        walkDown.setStateTime(0f);
        /*Anda Para Baixo */
        stopDown = new Animax();
        stopDown.setTexture(file); 
        stopDown.setTextureRegions(FRAME_ROWS,FRAME_COLS,1,1,1,3);
        stopDown.setAnimation(1/3f);               
        stopDown.setStateTime(0f);
        i=0;
    }
    public void setXY(float x, float y){
    	this.x=x;
    	this.y=y;
    }
    public float getX(){
    	return x;
    	
    }
    public float getY(){
    	return y;
    	
    }
    public float getWidth(){
    	return width;
    	
    }
    public float getHeight(){
    	return height;
    	
    }
    public void act(){
    	if(!flagup&&!flagdown&&!flagright&&!flagleft){
    		stopDown = animate(stopDown);
    		if((i >= 0 )&&( i< 240)){
	        	stopDown.setStateTime(0f);
	        	i++;
	        }else
	        	i++;
    	}
    	if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)||AndroidInput.getRightB()) {// #14
	        walkRight = animate(walkRight);
	        flagup = false;
	        flagdown = false;
	        flagright = true;
	        flagleft = false;
    	}
    	else if(Gdx.input.isKeyPressed(Input.Keys.UP)||AndroidInput.getUpB()){
    		walkUp = animate(walkUp);	
    		flagup = true;
	        flagdown = false;
	        flagright = false;
	        flagleft = false;
    	}
    	else if((Gdx.input.isKeyPressed(Input.Keys.LEFT)||AndroidInput.getLeftB())){
    		walkLeft = animate(walkLeft);
    		flagup = false;
	        flagdown = false;
	        flagright = false;
	        flagleft = true;
    	}
    	else if((Gdx.input.isKeyPressed(Input.Keys.DOWN)||AndroidInput.getDownB())){
    		walkDown = animate(walkDown);
    		flagup = false;
	        flagdown = true;
	        flagright = false;
	        flagleft = false;
    		
    	}
    	else if(!Gdx.input.isKeyPressed(Input.Keys.RIGHT)&&flagright) {// #14
	        stopRight = animate(stopRight);
	        if((i >= 0 )&&( i< 240)){
	        	stopRight.setStateTime(0f);
	        	i++;
	        }else
	        	i++;
    	}
    	else if(!Gdx.input.isKeyPressed(Input.Keys.UP)&&flagup){
    		stopUp = animate(stopUp);	
    	}
    	else if(!Gdx.input.isKeyPressed(Input.Keys.LEFT)&&flagleft){
    		stopLeft = animate(stopLeft);	
    		if((i >= 0 )&&( i< 240)){
	        	stopLeft.setStateTime(0f);
	        	i++;
	        }else
	        	i++;
    	}
    	else if(!Gdx.input.isKeyPressed(Input.Keys.DOWN)&&flagdown){
    		stopDown = animate(stopDown);
    		if((i >= 0 )&&( i< 240)){
	        	stopDown.setStateTime(0f);
	        	i++;
	        }else
	        	i++;
    	}
    	if(i >= 300)
    		i = 0;
    };
    public void render() {  
    	
    		
    }
    public Animax animate(Animax play){

    	play.addStateTime( Gdx.graphics.getDeltaTime());           // #15
        play.setTextureRegion( play.getAnimation().getKeyFrame(play.getStateTime(), true));  // #16
        Player.spritebatch.begin(); 
        Player.spritebatch.draw(play.getTextureRegion(), Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth()/13, Gdx.graphics.getHeight()/8);// #17
        Player.spritebatch.end();
        return play;
    }
}

