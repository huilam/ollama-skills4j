## ollama-skills4j

A Simple Ollama Skills framework for Java. 

Built on top of [Ollama4J](https://github.com/ollama4j/ollama4j) and implemented in Core Java, this library provides a lightweight, easy-to-use framework with a footprint of **less than 6MB**.


**Prerequisites:**
* **Java 17** or higher (adjust to your actual requirement)
* **Ollama** installed and running on your machine (or accessible via network).


```
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
