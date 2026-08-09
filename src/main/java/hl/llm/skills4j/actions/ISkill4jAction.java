package hl.llm.skills4j.actions;

import hl.llm.skills4j.SkillConfig;
import io.github.ollama4j.models.response.OllamaResult;

public abstract class ISkill4jAction {
	
	public abstract float getPluginName();
	public abstract float getPluginVersion();
	public abstract SkillConfig doPreExecute(SkillConfig aSkillConfig, String aUserPrompt);
	public abstract OllamaResult doPostExecute(SkillConfig aSkillConfig, String aUserPrompt, OllamaResult aOllamaResult);

}