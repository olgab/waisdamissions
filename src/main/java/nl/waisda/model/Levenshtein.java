package nl.waisda.model;

import java.util.List;


/**
 * Utilities for computing the edit distance between two strings.
 */
public class Levenshtein {

	private static int minimum(int a, int b, int c) {
		int mi;

		mi = a;
		if (b < mi) {
			mi = b;
		}
		if (c < mi) {
			mi = c;
		}
		return mi;
	}

	/**
	 * Computes the edit distance between the two strings, i.e. the number of
	 * operations (insert, delete, modify) to transform the one string into the
	 * other.
	 */
	public static int distance(String s, String t) {
		int n = s.length();
		int m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}

		int d[][] = new int[n + 1][m + 1];
		for (int i = 0; i <= n; i++) {
			d[i][0] = i;
		}
		for (int j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= m; j++) {
				int cost = s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1;
				d[i][j] = minimum(d[i - 1][j] + 1, d[i][j - 1] + 1,
						d[i - 1][j - 1] + cost);
			}
		}

		return d[n][m];
	}

	/**
	 * Of all the words in the input list, return the word that is closest to
	 * the query, including its edit distance.
	 */
	public static Match getBestMatch(List<String> words, String query) {
		String bestWord = null;
		int bestDistance = Integer.MAX_VALUE;

		for (String word : words) {
			int distance = distance(word, query);
			if (bestWord == null || distance < bestDistance) {
				bestWord = word;
				bestDistance = distance;
			}
		}

		return bestWord == null ? null : new Match(bestWord, bestDistance);
	}

	public static void main(String[] args) {
		// For testing purposes.
		System.out.println(distance(args[0], args[1]));
	}

}
