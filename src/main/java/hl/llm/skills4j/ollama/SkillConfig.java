package hl.llm.skills4j.ollama;

import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;

public class SkillConfig {
	
	private String skill_name 			= null;
	private String ollama_host 			= "http://localhost:11434";
	private String ollama_model_name 	= null;
	private int llm_timeout_secs		= 30;
	private String llm_system_prompt	= null;
	
	private OptionsBuilder llm_options 	= new OptionsBuilder();
	
	public SkillConfig(String aSkillFolder)
	{
		clear();
		this.skill_name = aSkillFolder;
	}
	
	public String getSkill_name() {
		return skill_name;
	}
	
	public void clear() {
		this.ollama_host 		= "http://localhost:11434";
		this.ollama_model_name 	= null;
		this.llm_timeout_secs	= 30;
		this.llm_options		= new OptionsBuilder();
		this.llm_system_prompt	= null;
	}

	public String getOllama_host() {
		return ollama_host;
	}

	public void setOllama_host(String ollama_host) {
		this.ollama_host = ollama_host;
	}

	public String getOllama_model_name() {
		return ollama_model_name;
	}

	public void setOllama_model_name(String ollama_model_name) {
		this.ollama_model_name = ollama_model_name;
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
		sb.append("  ollama_host: "+ollama_host+"\n");
		sb.append("  ollama_model_name: "+ollama_model_name+"\n");
		sb.append("  llm_timeout_secs: "+llm_timeout_secs+"\n");
		sb.append("  llm_options: "+llm_options.toString()+"\n");
		sb.append("  llm_system_prompt: "+llm_system_prompt+"\n");
		return sb.toString();
	}
}