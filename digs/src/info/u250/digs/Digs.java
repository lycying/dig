package info.u250.digs;

import info.u250.c2d.engine.DefaultEngineCallback;
import info.u250.c2d.engine.Engine;
import info.u250.c2d.engine.EngineDrive;
import info.u250.c2d.engine.load.Loading.LoadingComplete;
import info.u250.c2d.engine.load.in.InGameLoading;
import info.u250.c2d.engine.resources.AliasResourceManager;
import info.u250.digs.gdx_encrypt.EncryptOggRule;
import info.u250.digs.gdx_encrypt.Wahaha;

import java.util.Random;

import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.async.AsyncExecutor;


public class Digs extends Engine {
	public static final Random RND = new Random();
	private static final int LINGO_SOUND = 43;
	private static AsyncExecutor executor = new AsyncExecutor(1);
	private static GooglePlayServiceResolver googlePlayServiceResolver;
	private static Admob admob;
	
	public Digs(GooglePlayServiceResolver googlePlayServiceResolver,Admob admob){
		Digs.googlePlayServiceResolver = googlePlayServiceResolver;
		Digs.admob = admob;
		
		Engine.setEngineCallback(new DefaultEngineCallback(){
			@Override
			public void preLoad(DisplayMode mode, String[] assets) {
				//the texture
				Engine.getAliasResourceManager().addRule(new EncryptOggRule());
				//the texture atlas
				super.preLoad(mode, assets);
			}
		});

	}
	public static Admob getAdmob(){
		return admob;
	}
	//the google game play service interface 
	public static GooglePlayServiceResolver getGPSR() {
		return googlePlayServiceResolver;
	}
	//this is used for level load
	public static AsyncExecutor getExecutor() {
		return executor;
	}
	static String YEAP_SOUND_HANDEL = ""; //in order to solve the SoundPool bug at android~
	//delay load the npc's sound , because its so many and is not needed at the boot time
	public static void delayPlayActorSound(){
		final int soundIdx = RND.nextInt(LINGO_SOUND)+1;
		final String soundHandel = "SoundEnv"+soundIdx;
		final String dst = Wahaha.wahaha("lingo"+soundIdx)+".ogg";
		Sound sound = Engine.resource(soundHandel);
		if(null == sound){
			Engine.load(new String[]{dst,},new LoadingComplete() {
				@Override
				public void onReady(AliasResourceManager<String> reg) {
					reg.sound(soundHandel, dst);
					if(!YEAP_SOUND_HANDEL.equals("")){
						Engine.getSoundManager().playSound(YEAP_SOUND_HANDEL);
					}
					YEAP_SOUND_HANDEL = soundHandel;
				}
			});
		}else{
			Engine.getSoundManager().playSound(soundHandel);
		}
	}
	///////////////////////////////////////////////////////////////////////////
	@Override
	protected EngineDrive onSetupEngineDrive() {
		return new DigsEngineDrive();
	}
	//do nothing at in game loading
	@Override
	protected InGameLoading getInGameLoading(){
		return new InGameLoading() {
			@Override
			protected void inLoadingRender(float delta) {				
			}
			@Override
			protected void finishLoadingCleanup() {				
			}
		};
	}
	@Override
	public void dispose() {
		executor.dispose();
		super.dispose();
	}
	
}
