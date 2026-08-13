package hl.llm.skills4j.actions;

import hl.llm.skills4j.SkillConfig;
import hl.llm.skills4j.ollama.LLMReqInput;
import io.github.ollama4j.models.response.OllamaResult;

public interface ISkill4jAction {
	
	public abstract boolean init();
	public abstract String getName();
	public abstract float getVersion();
	public abstract LLMReqInput doPreExecAction(final SkillConfig aSkillConfig, LLMReqInput reqInput);
	public abstract OllamaResult doPostExecAction(final SkillConfig aSkillConfig, final LLMReqInput reqInput, OllamaResult aOllamaResult);

}