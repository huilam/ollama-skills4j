package hl.llm.skills4j.ollama;

import java.util.Map;

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
			String FONT_COLOR = OllamaSkillsMgr.CLI_FONT_BLACK;
			String FONT_DEF = OllamaSkillsMgr.CLI_FONT_DEF;
			if(!isQueitly)
			{
				System.out.println("Executing skill:["+skillConfig.getSkill_name()+"] ...");
				System.out.println("  - "+FONT_COLOR+"host: "+FONT_DEF+llmclient.getHost());
				System.out.println("  - "+FONT_COLOR+"model: "+FONT_DEF+llmclient.getModel_name());
				System.out.println("  - "+FONT_COLOR+"userPrompt: "+FONT_DEF+aUserPrompt.replaceAll("\n", " "));
				Map<String, Object> optionsMap = llmclient.getLLM_options().getOptionsMap();
				System.out.println("  - "+FONT_COLOR+"llmOptions: "+FONT_DEF+optionsMap.size());
				for(Map.Entry<String, Object> entry : optionsMap.entrySet())
				{
					System.out.println("      * "+entry.getKey()+": "+entry.getValue());
				}
				System.out.println();			}
			OllamaResult response = llmclient.sendRequest(aUserPrompt);
			return response.getResponse();
		}
		return null;
	}

}