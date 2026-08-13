package hl.llm.skills4j;

import hl.llm.skills4j.actions.ISkill4jAction;
import hl.llm.skills4j.ollama.LLMReqInput;
import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;

public class Skill {
	
	private Ollama client 				= null;
	private SkillConfig skillConfig 	= null;
	
	public Skill(SkillConfig aSkillConfig) {
		this.client = new Ollama(aSkillConfig.getLLM_host());
		this.skillConfig = aSkillConfig;
	}
	
	public SkillConfig getSkillConfig() {
		return skillConfig;
	}
	
	public String getSkillName() {
		return skillConfig.getSkill_name();
	}
	public boolean isOllamaReady()
	{
		try {
			return client.ping();
		} catch (OllamaException e) {
		}
		return false;
	}

	public OllamaResult sendRequest(LLMReqInput reqInput) throws OllamaException
	{
		return sendRequest(reqInput, null);
	}
	
	public OllamaResult sendRequest(LLMReqInput reqInput, OllamaGenerateStreamObserver streamObserver) throws OllamaException{
        
		if(isOllamaReady()==false)
			throw new OllamaException("Ollama is not ready. Please check the host and ensure Ollama is running.");
		
		SkillConfig config = this.skillConfig;
		ISkill4jAction skillAction = config.getSkill_action();
		
		if(skillAction!=null)
        {
			reqInput = skillAction.doPreExecAction(config, reqInput);
        }
		
		OllamaGenerateRequest request = OllamaGenerateRequest.builder()
                .withModel(config.getLLM_model_name())
                .withPrompt(reqInput.getUserPrompt());
		
		if(reqInput.getImageBase64List()!=null && reqInput.getImageBase64List().size()>0)
		{
			request.withImagesBase64(reqInput.getImageBase64List());
		}
        
        if(config.getLLM_System_prompt()!=null)
        	request.withSystem(config.getLLM_System_prompt());
        
        if(config.getLLM_timeout_secs()>0)
        	client.setRequestTimeoutSeconds(config.getLLM_timeout_secs());
        
        if(config.getLLM_options()!=null)
        	request.setOptions(config.getLLM_options().getOptionsMap());
        
        ///
        OllamaResult ollamaResult = client.generate(request.build(), streamObserver);
        
        
        if(skillAction!=null)
        {
        	ollamaResult = skillAction.doPostExecAction(config, reqInput , ollamaResult);
		}
        
        return ollamaResult;
	}
	
}