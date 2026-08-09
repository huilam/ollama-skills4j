## ollama-skills4j

A Simple Ollama Skills framework for Java. 

Built on top of [Ollama4J](https://github.com/ollama4j/ollama4j) and implemented in Core Java, this library provides a lightweight, easy-to-use framework with a footprint of **less than 6MB** (including Ollama4J dependencies).


**Prerequisites:**
* **Java 17** or higher (adjust to your actual requirement)
* **Ollama** installed and running on your machine (or accessible via network).

<br>

- - -


**Sample Output:**

```
Executing [skill:[hello] ...
  - host: mhttp://localhost:11434
  - mmodel: phi4-mini:3.8b-q4_K_M
  - userPrompt: Explain what is 'hello'.
  - Options: 5
      * top_p: 0.9
      * seed: 42
      * top_k: 40
      * temperature: 0.7
      * repeat_penalty: 1.1
  - elapsedTime: 1250 ms

[English]: Greeting in English, meaning hello.
[Japanese]: こんにちは、これは挨拶の意味です。
[Chinese]: 《你好》，这是问候的意思。
````

<br>

**Sample Java Code:**

````
public static void main(String args[]) throws Exception
{
	OllamaSkillsMgr skillsMgr = new OllamaSkillsMgr();
	
	skillsMgr.setSilentMode(false);
	skillsMgr.setDebugMode(true);
	
	Skill skill = skillsMgr.getOllamaSkill("hello");
	if(skill!=null)
	{
		String userprompt = "Explain what is '"+skill.getSkillName()+"'.";
		System.out.println(skillsMgr.execute(skill, userprompt));
	}
}
````

<br>