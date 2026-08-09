package hl.llm.skills4j;

import java.lang.reflect.InvocationTargetException;

import hl.llm.skills4j.actions.ISkill4jAction;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;

public class SkillConfig {
	
	private String skill_name 			= null;
	private String llm_host 			= "http://localhost:11434";
	private String llm_model_name 		= null;
	private int llm_timeout_secs		= 30;
	private String llm_system_prompt	= null;
	private ISkill4jAction skill_action	= null;
	
	private OptionsBuilder llm_options 	= new OptionsBuilder();
	
	public SkillConfig(String aSkillFolder)
	{
		clear();
		this.skill_name = aSkillFolder;
	}
	
	public String getSkill_name() {
		return skill_name;
	}
	
	public ISkill4jAction getSkill_action() {
		return skill_action;
	}

	public void setSkill_action_classname(String aSkill_action_className) {
		Exception ex = null;
		try {
			setSkill_action((ISkill4jAction) null);
			Class<?> clazz = Class.forName(aSkill_action_className);
			Object instance = clazz.getDeclaredConstructor().newInstance();
			if (instance instanceof ISkill4jAction) {
				this.skill_action = (ISkill4jAction) instance;
				setSkill_action((ISkill4jAction) instance);
			} else {
				throw new IllegalArgumentException("Class " + aSkill_action_className + " does not implement ISkill4jAction.");
			}
		} catch (ClassNotFoundException e) {
			ex = e;
		} catch (InstantiationException e) {
			ex = e;
		} catch (IllegalAccessException e) {
			ex = e;
		} catch (IllegalArgumentException e) {
			ex = e;
		} catch (InvocationTargetException e) {
			ex = e;
		} catch (NoSuchMethodException e) {
			ex = e;
		}
		
		if(ex!=null)
		{
			throw new RuntimeException("Failed to instantiate skill action: " + aSkill_action_className, ex);
		}
	}
	
	public void setSkill_action(ISkill4jAction skill_action) {
		this.skill_action = skill_action;
	}

	public void clear() {
		this.llm_host 		= "http://localhost:11434";
		this.llm_model_name 	= null;
		this.llm_timeout_secs	= 30;
		this.llm_options		= new OptionsBuilder();
		this.llm_system_prompt	= null;
		
		this.skill_action 		= null;
	}

	public String getLLM_host() {
		return llm_host;
	}

	public void setLLM_host(String llm_host) {
		this.llm_host = llm_host;
	}

	public String getLLM_model_name() {
		return llm_model_name;
	}

	public void setLLM_model_name(String llm_model_name) {
		this.llm_model_name = llm_model_name;
	}
	
	////
	public int getLLM_timeout_secs() {
		return llm_timeout_secs;
	}
	
	public void setLLM_timeout_secs(String llm_timeout_secs) {
		try {
			int iSecs = Integer.parseInt(llm_timeout_secs);
			setLLM_timeout_secs(iSecs);
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public void setLLM_timeout_secs(int llm_timeout_secs) {
		this.llm_timeout_secs = llm_timeout_secs;
	}
	
	
	public void setLLM_Options_topK(int topK)
	{
		llm_options.setTopK(topK);
	}
	
	public void setLLM_Options_topP(float topP)
	{
		llm_options.setTopP(topP);
	}
	
	public void setLLM_Options_temperature(float temperature)
	{
		llm_options.setTemperature(temperature);
	}
	
	public void setLLM_Options_seed(int seed)
	{
		llm_options.setSeed(seed);
	}
	
	public void setLLM_Options_repeatPenalty(float setOption_repeatPenalty)
	{
		llm_options.setRepeatPenalty(setOption_repeatPenalty);
	}
	//
	public Options getLLM_options() {
		return llm_options.build();
	}
	
	public String getLLM_System_prompt() {
		return llm_system_prompt;
	}

	public void setLLM_System_prompt(String llm_system_prompt) {
		this.llm_system_prompt = llm_system_prompt;
	}
	//


	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SkillConfig: "+skill_name+"\n");
		sb.append("  llm_host: "+llm_host+"\n");
		sb.append("  llm_model_name: "+llm_model_name+"\n");
		sb.append("  llm_timeout_secs: "+llm_timeout_secs+"\n");
		sb.append("  llm_options: "+llm_options.toString()+"\n");
		sb.append("  llm_system_prompt: "+llm_system_prompt+"\n");
		sb.append("  skill_action: "+(skill_action!=null?skill_action.getClass().getName():null)+"\n");
		return sb.toString();
	}
}