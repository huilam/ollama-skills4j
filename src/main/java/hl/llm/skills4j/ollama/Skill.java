package hl.llm.skills4j.ollama;

import io.github.ollama4j.models.response.OllamaResult;

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
	
	public String execute(String aUserPrompt) throws Exception
	{
		return execute(aUserPrompt, false);
	}
	public String execute(String aUserPrompt, boolean isQueitly) throws Exception
	{
		if(llmclient!=null && aUserPrompt!=null && aUserPrompt.trim().length()>0)
		{
			if(!isQueitly)
			{
				System.out.println("Executing skill:["+skillConfig.getSkill_name()+"] ...");
				System.out.println("  - host: "+llmclient.getHost());
				System.out.println("  - model: "+llmclient.getModel_name());
				System.out.println("  - userPrompt: "+aUserPrompt.replaceAll("\n", " "));
				System.out.println();
			}
			OllamaResult response = llmclient.sendRequest(aUserPrompt);
			return response.getResponse();
		}
		return null;
	}

}