
// CS3 Spreadsheet DateCell class.  Fill in the details.

public class DateCell extends Cell {
	// TODO: add a private attribute
	private String date;

	// TODO: add a constructor that initializes the attribute
	public DateCell(String date) {
		String[] dateParts = date.split("/");
		if (dateParts[0].length() == 1)
			dateParts[0] = "0" + dateParts[0];
		if (dateParts[1].length() == 1)
			dateParts[1] = "0" + dateParts[1];
		
		this.date = dateParts[0] + "/" + dateParts[1] + "/" + dateParts[2];
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
