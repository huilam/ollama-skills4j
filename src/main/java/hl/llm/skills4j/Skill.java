package hl.llm.skills4j;

import hl.llm.skills4j.actions.ISkill4jAction;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public OllamaResult sendRequest(String userprompt) throws OllamaException
	{
		return sendRequest(userprompt, null);
	}
	
	public OllamaResult sendRequest(String userprompt, OllamaGenerateStreamObserver streamObserver) throws OllamaException{
        
		SkillConfig config = this.skillConfig;
		ISkill4jAction skillAction = config.getSkill_action();
		
		if(skillAction!=null)
        {
			config = skillAction.doPreExecute(config, userprompt);
        }
		
		OllamaGenerateRequest request = OllamaGenerateRequest.builder()
                .withModel(config.getLLM_model_name())
                .withPrompt(userprompt);
        
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
        	ollamaResult = skillAction.doPostExecute(config, userprompt , ollamaResult);
		}
        
        return ollamaResult;
	}
	
}