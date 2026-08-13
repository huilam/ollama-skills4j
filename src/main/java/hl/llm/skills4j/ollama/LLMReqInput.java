package hl.llm.skills4j.ollama;

import java.util.ArrayList;
import java.util.List;

public class LLMReqInput {
	
	private String userPrompt = null;
	private List<String> listImgBase64 = new ArrayList<>();
	
	public LLMReqInput(String userPrompt) {
		this.userPrompt = userPrompt;
	}
	
	public String getUserPrompt() {
		return userPrompt;
	}
	
	public void setUserPrompt(String userPrompt) {
		this.userPrompt = userPrompt;
	}
	
	public List<String> getImageBase64List() {
		return this.listImgBase64;
	}
	
	public void setImageBase64List(List<String> aListImgBase64) {
		this.listImgBase64 = aListImgBase64;
	}
	
	public void clearImageBase64List() {
		this.listImgBase64.clear();
	}
	
	public void addImageBase64(String aImageBase64) {
		this.listImgBase64.add(aImageBase64);
	}
}