
public class SpellChecker {


	public static void main(String[] args) {
		String word = args[0];
		int threshold = Integer.parseInt(args[1]);
		String[] dictionary = readDictionary("dictionary.txt");
		String correction = spellChecker(word, threshold, dictionary);
		System.out.println(correction);
	}

	public static String tail(String str) {
		if (str.length()>1) {
			return str.substring(1);
		} else {
			return ("");
		}
	}

	public static int levenshtein(String word1, String word2) {
		int a = word1.length();
		int b = word2.length();
		word1 = ((a != 0) ? word1.toLowerCase() : word1);
		word2 = ((b != 0) ? word2.toLowerCase() : word2);
		char headA = ((a != 0) ? word1.charAt(0) : ' ');
		char headB = ((b != 0) ? word2.charAt(0) : ' ');
		if (a == 0) {
			return b;
		} else if (b == 0) {
			return a;
		}
		if (headA == headB) {
			return levenshtein(tail(word1),tail(word2));
		} else {
			//return 0;
			int case1 = Math.min(levenshtein(tail(word1),word2),levenshtein(word1,tail(word2)));
			int case2 = Math.min(case1, levenshtein(tail(word1),tail(word2))); 
			return (1 + case2);
		}
	}

	public static String[] readDictionary(String fileName) {
		String[] dictionary = new String[3000];

		In in = new In(fileName);

		int n = 0;
		while (!in.isEmpty() && n < 3100) {
			String str = in.readLine();
			dictionary[n] = str;
			n++;
		}
		return dictionary;
	}

	public static String spellChecker(String word, int threshold, String[] dictionary) {
		String simWord = "";
		boolean found = false;
		for (int i = 0; i < 3000; i++) {
			if (levenshtein(word, dictionary[i]) <= threshold) {
				found = true;
				simWord = (levenshtein(word, dictionary[i]) < levenshtein(word, simWord)) ? dictionary[i] : simWord;
			}
		}
		if (found == true) {
			return simWord;
		} else {
			return word;
		}
	}

}
