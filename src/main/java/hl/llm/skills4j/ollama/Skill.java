package hl.llm.skills4j.ollama;

public class Skill {
	
	private LLMClient llmclient 	= null;
	private SkillConfig skillConfig = null;
	
	public Skill(SkillConfig aSkillConfig) {
		//
		this.skillConfig = aSkillConfig;
		//
		initLLMClient();
	}
	
	public String getSkillName()
	{
		if(skillConfig!=null)
		{
			return skillConfig.getSkill_name();
		}
		return null;
	}
	
	public SkillConfig getSkillConfig()
	{
		return skillConfig;
	}
	
	public LLMClient getLLMClient()
	{
		return llmclient;
	}
	
	private void initLLMClient()
	{
		if(skillConfig!=null)
		{
			llmclient = new LLMClient(skillConfig.getOllama_host());
			llmclient.setRequestTimeoutSecs(skillConfig.getLLM_timeout_secs());
			llmclient.setModel_name(skillConfig.getOllama_model_name());
			llmclient.setSystem_prompt(skillConfig.getLLM_System_prompt());
			//
			llmclient.setLLM_options(skillConfig.getLLM_options());
			//
		}
	}

}