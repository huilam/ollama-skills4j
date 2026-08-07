package hl.llm.skills4j.ollama;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.generate.OllamaGenerateStreamObserver;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.utils.Options;

public class LLMClient {
	
	private Ollama client 			= null;
	private String ollama_host 		= "http://localhost:11434";
	private int ollama_timeout_secs = 30;
	//
	private String model_name 		= null;
	private String system_prompt 	= null;
	//
	private Options llm_options 	= null;
	
	
	public LLMClient(String host) {
		this.ollama_host = host;
		this.client = new Ollama(host);
	}

	public String getHost() {
		return this.ollama_host;
	}
	
	public void setRequestTimeoutSecs(int timeout_secs) {
		this.ollama_timeout_secs = timeout_secs;
		this.client.setRequestTimeoutSeconds(timeout_secs);
	}
	
	public int getRequestTimeoutSecs() {
		return this.ollama_timeout_secs;
	}
	
	public String getSystem_prompt() {
		return system_prompt;
	}

	public void setSystem_prompt(String system_prompt) {
		this.system_prompt = system_prompt;
	}

	public Options getLLM_options() {
		return llm_options;
	}
	
	public void setLLM_options(Options llm_options) {
		this.llm_options = llm_options;
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

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
		try {
			this.client.pullModel(model_name);
		} catch (OllamaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public OllamaResult sendRequest(String userprompt) throws OllamaException
	{
		return sendRequest(userprompt, null);
	}
	
	public OllamaResult sendRequest(String userprompt, OllamaGenerateStreamObserver streanObserver) throws OllamaException{
        OllamaGenerateRequest request = OllamaGenerateRequest.builder()
                .withModel(this.model_name)
                .withPrompt(userprompt);
        
        if(this.system_prompt!=null)
        {
        	request.withSystem(this.system_prompt);
        }
        
        if(this.llm_options!=null)
        {
        	request.setOptions(this.llm_options.getOptionsMap());
        }
        
        
        return client.generate(request.build(), streanObserver);
	}
	
}