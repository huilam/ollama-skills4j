package hl.llm.skills4j.ollama;

import java.io.IOException;
import java.util.Properties;

import hl.common.PropUtil;

public class OllamaSkillsMgr {
	
	private String frameworkPropFileName 			= "ollama-skills4j.properties";
	private static String frameworkPropPrefix 		= "ollama-skill4j.";
	private static String DEF_skill_config_filename = "skill4j.properties";
	
	private static String DEF_ollama_host 	= "http://localhost:11434";
	private static String DEF_timeout_ms 	= "10";
	private static String DEF_model_name 	= "";
	
	private Properties propSkillConfig = null;
	
	
	public OllamaSkillsMgr() {
		propSkillConfig = loadProperties(this.frameworkPropFileName);
	}
	
	public OllamaSkillsMgr(String aPropFileName) {
		this.frameworkPropFileName = aPropFileName;
		propSkillConfig = loadProperties(this.frameworkPropFileName);
	}

	public Properties getOllamaPropForSkill(String sSkillName)
	{
		Properties prop = new Properties();
		if(propSkillConfig!=null)
		{
			propSkillConfig.keySet().stream().forEach(key -> {
				String sKey = (String)key;
				if(sKey.startsWith(frameworkPropPrefix+sSkillName+"."))
				{
					prop.put(sKey, propSkillConfig.getProperty(sKey));
				}
			});
		}
		return prop;
	}
	
	private Properties loadProperties(String aPropFileName)
	{
		if(aPropFileName==null || aPropFileName.trim().length()==0)
		{
			System.err.println("Invalid property file name: "+aPropFileName);
			return null;
		}
		
		try {
			return PropUtil.loadProperties(aPropFileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Skill getOllamaSkill(final String aSkillName)
	{
		String sSkillfolder = propSkillConfig.getProperty(frameworkPropPrefix+aSkillName+".folder");
		if(sSkillfolder!=null)
		{
			String sSkillPropFileName = propSkillConfig.getProperty(
					frameworkPropPrefix+aSkillName+".optional.properties",
					DEF_skill_config_filename);
			//
			SkillConfig skillConfig = getSkillConfig(sSkillfolder, sSkillPropFileName);
			if(skillConfig!=null)
			{
				Skill ollamaSkill = new Skill(skillConfig);
				return ollamaSkill;
			}
		}
		
		System.err.println("Skill not found: ["+aSkillName+"]");
		return null;
	}
	
	private SkillConfig getSkillConfig(final String aSkillFolder, final String aSkillPropFileName)
	{
		Properties prop = loadProperties(aSkillFolder+"/"+aSkillPropFileName);
		if(prop!=null)
		{
			String sSkillPrefix = "skill4j.llm.";
			String sSkillReqPrefix = sSkillPrefix+"request.";
			//
			SkillConfig skillConfig = new SkillConfig(aSkillFolder);
			skillConfig.setOllama_host(prop.getProperty(sSkillPrefix+"host", DEF_ollama_host));
			skillConfig.setOllama_model_name(prop.getProperty(sSkillReqPrefix+"model-name", DEF_model_name));
			//
			skillConfig.setLLM_timeout_secs(prop.getProperty(sSkillReqPrefix+"timeout-secs", DEF_timeout_ms));
			//
			String sSystemPrompt = prop.getProperty(sSkillReqPrefix+"system-prompt", null);
			if(sSystemPrompt!=null && sSystemPrompt.trim().length()>0)
			{
				skillConfig.setLLM_System_prompt(sSystemPrompt);
			}
			//
			
			String[] sLLMOptions = new String[] {"topK", "topP", "temperature", "repeatPenalty", "seed"};
			for(String sOpt : sLLMOptions)
			{
				String sVal = prop.getProperty(sSkillReqPrefix+"options."+sOpt, null);
				if(sVal!=null && sVal.trim().length()>0)
				{
					try {
						float fVal = Float.parseFloat(sVal); // validate number
						
						switch(sOpt)
						{
							case "top_k":
								skillConfig.setLLM_Options_topK((int)fVal);
								break;
							case "top_p":
								skillConfig.setLLM_Options_topP(fVal);
								break;
							case "temperature":
								skillConfig.setLLM_Options_temperature(fVal);
								break;
							case "repeat_penalty":
								skillConfig.setLLM_Options_repeatPenalty(fVal);
								break;
							case "seed":
								skillConfig.setLLM_Options_seed((int)fVal);
								break;
						}
					}catch (NumberFormatException e) {
						e.printStackTrace();
					}
				}
			}
			
			return skillConfig;
		}
		return null;
	}
	
	public static void main(String args[]) throws Exception
	{
		OllamaSkillsMgr skillsMgr = new OllamaSkillsMgr();
		Skill skill = skillsMgr.getOllamaSkill("hello");
		
		if(skill!=null)
		{
			String userprompt = "Explain what "+skill.getSkillName()+" is in two short sentences.";
			System.out.println(skill.execute(userprompt));
		}
	}
}