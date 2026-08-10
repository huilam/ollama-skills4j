package hl.llm.skills4j.ollama;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

import hl.common.PropUtil;
import hl.llm.skills4j.Skill;
import hl.llm.skills4j.SkillConfig;
import io.github.ollama4j.models.response.OllamaResult;

public class OllamaSkillsMgr {
	
	public static String CLI_FONT_DEF 	= "\u001B[0m";
	public static String CLI_FONT_BLACK = "\u001B[1;30m";
	public static String CLI_FONT_RED 	= "\u001B[31m";
	public static String CLI_FONT_GREEN = "\u001B[32m";
	public static String CLI_FONT_BLUE 	= "\u001B[34m";
	
	private String frameworkPropFileName 			= "ollama-skills4j.properties";
	private static String frameworkPropPrefix 		= "ollama-skills4j.";
	private static String DEF_skill_config_filename = "skill4j.properties";
	
	private static String DEF_ollama_host 	= "http://localhost:11434";
	private static String DEF_timeout_ms 	= "10";
	private static String DEF_model_name 	= "";
	
	private Properties propSkillConfig = null;
	public boolean isDebugMode 	= false;
	public boolean isSilentMode = false;
	
	
	public OllamaSkillsMgr() {
		propSkillConfig = loadProperties(this.frameworkPropFileName);
	}
	
	public OllamaSkillsMgr(String aPropFileName) {
		this.frameworkPropFileName = aPropFileName;
		propSkillConfig = loadProperties(this.frameworkPropFileName);
	}

	public boolean isDebugMode() {
		return isDebugMode;
	}

	public void setDebugMode(boolean aIsDebugMode) {
		isDebugMode = aIsDebugMode;
	}

	public boolean isSilentMode() {
		return isSilentMode;
	}

	public void setSilentMode(boolean isSilentMode) {
		this.isSilentMode = isSilentMode;
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
			//e.printStackTrace();
		}
		return null;
	}
	
	public String getTemplFrameworkProperties(String aSkillNameCandidate)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(CLI_FONT_GREEN);
		sb.append("########################################").append("\n");
		sb.append("# ollama-skills4j.[skill-name].folder=[skill-name]").append("\n");
		sb.append("# ollama-skills4j.[skill-name].optional.properties=skill4j.properties").append("\n");
		sb.append("########################################").append("\n");
		sb.append("\n");
		sb.append("ollama-skills4j.").append(aSkillNameCandidate).append(".folder=").append(aSkillNameCandidate).append("\n");
		sb.append("#ollama-skills4j.").append(aSkillNameCandidate).append(".optional.properties=").append(aSkillNameCandidate).append("-skill4j.properties").append("\n");
		sb.append("\n");
		sb.append("##-END-#################################").append("\n");
		sb.append(CLI_FONT_DEF);
		return sb.toString();
	}
	
	public String getTemplSkill4jProperties(String aSkillNameCandidate)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(CLI_FONT_GREEN);
		sb.append("########################################").append("\n");
		sb.append("# skill4j.llm.host=").append("http://localhost:11434").append("\n");
		sb.append("# skill4j.llm.request.model=").append("phi4-mini:3.8b").append("\n");
		sb.append("# skill4j.llm.request.timeout=").append("30").append("\n");
		sb.append("# skill4j.llm.request.system-prompt=").append("${file:"+aSkillNameCandidate+".system-prompt}").append("\n");
		sb.append("# skill4j.action.implementation=").append("\n");
		sb.append("#").append("\n");
		sb.append("# skill4j.llm.options.topK=").append("40").append("\n");
		sb.append("# skill4j.llm.options.topP=").append("0.9").append("\n");
		sb.append("# skill4j.llm.options.temperature=").append("0.7").append("\n");
		sb.append("# skill4j.llm.options.repeatPenalty=").append("1.1").append("\n");
		sb.append("# skill4j.llm.options.seed=").append("42").append("\n");
		sb.append("########################################").append("\n");
		sb.append("\n");
		sb.append("skill4j.llm.host=").append("http://localhost:11434").append("\n");
		sb.append("\n");
		sb.append("##-END-#################################").append("\n");
		sb.append(CLI_FONT_DEF);
		return sb.toString();
	}
	
	public Skill getOllamaSkill(final String aSkillName)
	{
		String sSkillfolder = propSkillConfig.getProperty(frameworkPropPrefix+aSkillName+".folder");
		return getOllamaSkill(aSkillName, sSkillfolder);
	}
	
	public Skill getOllamaSkill(final String aSkillName, final String aSkillFolderPath)
	{
		String sSkillPropFileName = null;
		if(aSkillFolderPath!=null)
		{
			sSkillPropFileName = propSkillConfig.getProperty(
					frameworkPropPrefix+aSkillName+".optional.properties",
					DEF_skill_config_filename);
		}
		return getOllamaSkill(aSkillName, aSkillFolderPath, sSkillPropFileName);
	}
	
	protected Skill getOllamaSkill(final String aSkillName, final String aSkillFolderPath,  final String aSkillPropFileName)
	{
		if(aSkillFolderPath!=null && aSkillPropFileName!=null)
		{
			String sSkillPropFileName = aSkillPropFileName;
			//
			SkillConfig skillConfig = getSkillConfig(aSkillFolderPath, sSkillPropFileName);
			if(skillConfig!=null)
			{
				Skill ollamaSkill = new Skill(skillConfig);
				return ollamaSkill;
			}
			else
			{
				System.err.println("Failed to initialise Skill:["+aSkillName+"], error loading properties file - "+ sSkillPropFileName);
				System.out.println("");
				System.out.println("Generating template for ["+sSkillPropFileName+"] ...");
				System.out.println("");
				System.out.println(getTemplSkill4jProperties(aSkillName));
			}
		}
		else
		{
			System.err.println("Skill folder not found in classpath: ["+aSkillName+"]");
			System.out.println("");
			System.out.println("Generating template for ["+frameworkPropFileName+"] ...");
			System.out.println("");
			System.out.println(getTemplFrameworkProperties(aSkillName));
		}
		return null;
	}
	
	private SkillConfig getSkillConfig(final String aSkillFolder, final String aSkillPropFileName)
	{
		Properties prop = loadProperties(aSkillFolder+"/"+aSkillPropFileName);
		if(prop!=null)
		{
			String sSkillPrefix = "skill4j.";
			String sSkillReqPrefix = sSkillPrefix+"llm.request.";
			//
			SkillConfig skillConfig = new SkillConfig(aSkillFolder);
			skillConfig.setLLM_host(prop.getProperty(sSkillPrefix+"llm.host", DEF_ollama_host));
			skillConfig.setLLM_model_name(prop.getProperty(sSkillReqPrefix+"model-name", DEF_model_name));
			//
			skillConfig.setLLM_timeout_secs(prop.getProperty(sSkillReqPrefix+"timeout-secs", DEF_timeout_ms));
			//
			String sSystemPrompt = prop.getProperty(sSkillReqPrefix+"system-prompt", null);
			if(sSystemPrompt!=null && sSystemPrompt.trim().length()>0)
			{
				skillConfig.setLLM_System_prompt(sSystemPrompt);
			}
			//
			String sActionClassName = prop.getProperty(sSkillPrefix+"action.implementation", null);
			if(sActionClassName!=null && sActionClassName.trim().length()>0)
			{
				skillConfig.setSkill_action_classname(sActionClassName);
			}
			//
			
			String[] sLLMOptions = new String[] {"topK", "topP", "temperature", "repeatPenalty", "seed"};
			for(String sOpt : sLLMOptions)
			{
				String sPropKey = sSkillPrefix+"llm.options."+sOpt;
				String sVal = prop.getProperty(sPropKey, null);
				if(sVal!=null && sVal.trim().length()>0)
				{
					try {
						float fVal = Float.parseFloat(sVal); // validate number
						
						switch(sOpt.toLowerCase())
						{
							case "topk":
								skillConfig.setLLM_Options_topK((int)fVal);
								break;
							case "topp":
								skillConfig.setLLM_Options_topP(fVal);
								break;
							case "temperature":
								skillConfig.setLLM_Options_temperature(fVal);
								break;
							case "repeatpenalty":
								skillConfig.setLLM_Options_repeatPenalty(fVal);
								break;
							case "seed":
								skillConfig.setLLM_Options_seed((int)fVal);
								break;
						}
					}catch (NumberFormatException e) {
						System.err.println("Error llm.option: "+sPropKey+"="+sVal);
					}
				}
			}
			
			return skillConfig;
		}
		return null;
	}
	

	public String execute(Skill aSkill, String aUserPrompt) throws Exception
	{
		String sResult = null;
		
		if(aSkill!=null && aUserPrompt!=null && aUserPrompt.trim().length()>0)
		{
			String FONT_COLOR = OllamaSkillsMgr.CLI_FONT_BLACK;
			String FONT_DEF = OllamaSkillsMgr.CLI_FONT_DEF;
			if(!isSilentMode)
			{
				SkillConfig skillConfig = aSkill.getSkillConfig();
				
				System.out.println("Executing "+FONT_COLOR+"skill:["+FONT_DEF+aSkill.getSkillName()+FONT_COLOR+"]"+FONT_DEF+" ...");
				System.out.println("  - "+FONT_COLOR+"host: "+FONT_DEF+skillConfig.getLLM_host());
				System.out.println("  - "+FONT_COLOR+"model: "+FONT_DEF+skillConfig.getLLM_model_name());
				System.out.println("  - "+FONT_COLOR+"userPrompt: "+FONT_DEF+aUserPrompt.replaceAll("\n", " "));
				Map<String, Object> optionsMap = skillConfig.getLLM_options().getOptionsMap();
				System.out.println("  - "+FONT_COLOR+"customOptions: "+FONT_DEF+optionsMap.size());
				
				if(isDebugMode)
				{
					for(Map.Entry<String, Object> entry : optionsMap.entrySet())
					{
						System.out.println("      * "+entry.getKey()+": "+entry.getValue());
					}
					
				}
			}
			long lTimeMs = System.currentTimeMillis();
			OllamaResult response = aSkill.sendRequest(aUserPrompt);
			sResult = response.getResponse();
			lTimeMs = System.currentTimeMillis() - lTimeMs;
			if(!isSilentMode)
			{
				if(isDebugMode)
				{
					System.out.println("  - "+FONT_COLOR+"response: "+FONT_DEF+sResult.replaceAll("\n", " ").replaceAll("\s{2,}", " "));
				}
				System.out.println("  - "+FONT_COLOR+"elapsedTime: "+FONT_DEF+lTimeMs+" ms");
				System.out.println();
			}
		}
		return sResult;
	}
	
	public static void main(String args[]) throws Exception
	{
		OllamaSkillsMgr skillsMgr = new OllamaSkillsMgr();
		
		skillsMgr.setSilentMode(false);
		skillsMgr.setDebugMode(true);
		
		Skill skill = skillsMgr.getOllamaSkill("hello");
		if(skill!=null)
		{
			String userprompt = "Explain what is '"+skill.getSkillName()+"'.";
			System.out.println();
			System.out.println(skillsMgr.execute(skill, userprompt));
		}
		
		if(skill!=null)
		{
			SkillConfig config = skill.getSkillConfig();
			config.setLLM_model_name("qwen2.5-coder:1.5b-instruct-q4_K_M");
			config.setLLM_Options_temperature(0.5f);
			String userprompt = "Explain what is '"+skill.getSkillName()+"'.";
			System.out.println();
			System.out.println(skillsMgr.execute(skill, userprompt));
		}
	}
}