package hl.llm.skills4j;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import hl.llm.skills4j.actions.ISkill4jAction;
import io.github.ollama4j.utils.Options;
import io.github.ollama4j.utils.OptionsBuilder;

public class SkillConfig {
	
	
	public static String DEF_ollama_host 	= "http://localhost:11434";
	public static String DEF_timeout_ms 	= "30";
	public static String DEF_model_name 	= "";
	
	private String skill_name 			= null;
	private File skill_path 			= null;
	private String llm_host 			= DEF_ollama_host;
	private String llm_model_name 		= null;
	private int llm_timeout_secs		= Integer.parseInt(DEF_timeout_ms);;
	private String llm_system_prompt	= null;
	private ISkill4jAction skill_action	= null;
	
	private OptionsBuilder llm_options 		= new OptionsBuilder();
	private Map<String, Object> mapProps 	= new java.util.HashMap<String, Object>();
	
	public SkillConfig(String aSkillName, File aSkillFolder)
	{
		clear();
		this.skill_name = aSkillName;
		this.skill_path = aSkillFolder;
	}
	
	public String getSkill_name() {
		return skill_name;
	}
	
	public File getSkillFolderPath() {
		return skill_path;
	}
	
	public Map<String, Object> getSkill_props() {
		return mapProps;
	}
	
	public ISkill4jAction getSkill_action() {
		return skill_action;
	}
	
	public void setSkill_action(ISkill4jAction skill_action) {
		this.skill_action = skill_action;
	}

	public void addSkill_properties(Properties aProps) {
		
		if(aProps!=null && aProps.size()>0)
		{
			for(String key : aProps.stringPropertyNames())
			{
				String value = aProps.getProperty(key);
				if(value!=null)
					mapProps.put(key, value);
			}
		}
	}
	
	public void addSkill_props(String key, Object value) {
		this.mapProps.put(key, value);
	}
	
	
	public void clear() {
		this.llm_host 			= DEF_ollama_host;
		this.llm_model_name 	= null;
		this.llm_timeout_secs	= Integer.parseInt(DEF_timeout_ms);
		this.llm_options		= new OptionsBuilder();
		this.llm_system_prompt	= null;
		
		this.skill_action 		= null;
		this.mapProps 			= new java.util.HashMap<String, Object>();
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
			setLLM_timeout_secs(Integer.parseInt(DEF_timeout_ms));
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
		sb.append("  mapProps: "+mapProps.toString()+"\n");
		sb.append("  skill_action: "+(skill_action!=null?skill_action.getClass().getName():null)+"\n");
		return sb.toString();
	}
}