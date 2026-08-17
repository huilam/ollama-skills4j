package hl.llm.skills4j.ollama;

import java.util.HashMap;
import java.util.Map;

public class LLMReqInput {
	
	private String userPrompt = null;
	private Map<String, String> mapImgBase64 = new HashMap<>();
	
	public LLMReqInput(String userPrompt) {
		this.userPrompt = userPrompt;
	}
	
	public String getUserPrompt() {
		return userPrompt;
	}
	
	public void setUserPrompt(String userPrompt) {
		this.userPrompt = userPrompt;
	}
	
	public Map<String, String> getImageBase64Map() {
		return this.mapImgBase64;
	}
	
	public void setImageBase64Map(Map<String, String> aMapImgBase64) {
		this.mapImgBase64 = aMapImgBase64;
	}
	
	public void clearImageBase64Map() {
		this.mapImgBase64.clear();
	}
	
	public void addImageBase64(String aImageBase64, String aImageMeta) {
		this.mapImgBase64.put(aImageBase64, aImageMeta);
	}
}