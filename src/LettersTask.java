public class LettersTask
{
	String words = "moter, fater, tree, carefree, buritto, cetchup, tell";
	
	
	void CompleteTask()
	{
		words = words.replaceAll("t", "th");
		System.out.println(words);
	}
}
