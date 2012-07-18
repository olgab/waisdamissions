package nl.waisda.domain;

public enum Gender {
	MALE("m", "Male"), FEMALE("v", "Female");

	private String prettyShortName;
	private String prettyLongName;

	private Gender(String prettyShortName, String prettyLongName) {
		this.prettyShortName = prettyShortName;
		this.prettyLongName = prettyLongName;
	}

	public String getPrettyShortName() {
		return prettyShortName;
	}

	public String getPrettyLongName() {
		return prettyLongName;
	}
}
