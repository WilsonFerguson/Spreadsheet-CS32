// CS3 Spreadsheet TextCell class.

public class TextCell extends Cell {

	// TODO: declare a private attribute of type String
	private String text;

	// TODO: add a constructor
	public TextCell(String text) {
		this.text = text;
	}

	/** Does not include enclosing quotes */
	@Override
	public String abbreviatedCellText() {
		String output = text;
		if (output.length() > 10) {
			output = output.substring(0, 10);
		} else {
			while (output.length() < 10) {
				output += " ";
			}
		}
		return output;
	}

	/** Includes enclosing quotes */
	@Override
	public String fullCellText() {
		// return "\"" + text + "\"";
		return text;
	}
}
