## ollama-skills4j

A Simple Ollama Skills framework for Java. 

Built on top of [Ollama4J](https://github.com/ollama4j/ollama4j) and implemented in Core Java, this library provides a lightweight, easy-to-use framework with a footprint of **less than 6MB** (including Ollama4J dependencies).


**Prerequisites:**
* **Java 17** or higher (adjust to your actual requirement)
* **Ollama** installed and running on your machine (or accessible via network).

<br>

- - -


**Sample Output:**

```text
Executing skill:[hello] ...
  - host: http://localhost:11434
  - model: phi4-mini:3.8b-q4_K_M
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
```

<br>

**Sample Java Code:**

```java
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
```

**Sample Skill4j.propeties File:**

```text
########################################
# skill4j.llm.host=http://localhost:11434
# skill4j.llm.request.model=phi4-mini:3.8b
# skill4j.llm.request.timeout=30
# skill4j.llm.request.system-prompt=${file:hello.system-prompt}
# skill4j.action.implementation=
#
# skill4j.llm.options.topK=40
# skill4j.llm.options.topP=0.9
# skill4j.llm.options.temperature=0.7
# skill4j.llm.options.repeatPenalty=1.1
# skill4j.llm.options.seed=42
########################################
skill4j.llm.host=http://localhost:11434
skill4j.llm.request.model-name=phi4-mini:3.8b-q4_K_M
skill4j.llm.request.timeout-secs=200
skill4j.llm.request.system-prompt=${file:hello.system-prompt}
```

<br>
