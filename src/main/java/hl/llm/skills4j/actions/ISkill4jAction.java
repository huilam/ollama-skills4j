package hl.llm.skills4j.actions;

import hl.llm.skills4j.SkillConfig;
import io.github.ollama4j.models.response.OllamaResult;

public interface ISkill4jAction {
	
	public abstract boolean init();
	public abstract String getName();
	public abstract float getVersion();
	public abstract String doPreExecAction(final SkillConfig aSkillConfig, String aUserPrompt);
	public abstract OllamaResult doPostExecAction(final SkillConfig aSkillConfig, final String aUserPrompt, OllamaResult aOllamaResult);

}