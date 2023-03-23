
// CS3 Spreadsheet DateCell class.  Fill in the details.

public class DateCell extends Cell {
	// TODO: add a private attribute
	private String date;

	// TODO: add a constructor that initializes the attribute
	public DateCell(String date) {
		this.date = date;
	}

	// TODO: override the abbreviatedCellText method
	@Override
	public String abbreviatedCellText() {
		String output = date;
		if (output.length() > 10) {
			output = output.substring(0, 10);
		} else {
			while (output.length() < 10) {
				output += " ";
			}
		}
		return output;
	}

	// TODO: override the fullCellText method
	@Override
	public String fullCellText() {
		return date;
	}
}
