package nl.waisda.model;

public class Match {

	/** Het woord uit de woordenlijst dat het dichtst bij het invoerwoord zit. */
	final String match;

	/** De afstand tussen <code>match</code> en het ingevoerde woord. */
	final int distance;

	public Match(String match, int distance) {
		this.match = match;
		this.distance = distance;
	}

	public String getMatch() {
		return match;
	}

	public int getDistance() {
		return distance;
	}

}
