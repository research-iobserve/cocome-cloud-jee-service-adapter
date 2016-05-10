package de.kit.ipd.java.utils.parsing.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.kit.ipd.java.utils.framework.statemachine.ILexer;
import de.kit.ipd.java.utils.framework.statemachine.ILexerVisitor;
import de.kit.ipd.java.utils.framework.statemachine.IParser;
import de.kit.ipd.java.utils.framework.statemachine.IParserVisitor;
import de.kit.ipd.java.utils.framework.statemachine.IStateMachine;
import de.kit.ipd.java.utils.framework.table.Column;
import de.kit.ipd.java.utils.framework.table.Row;
import de.kit.ipd.java.utils.framework.table.Table;
import de.kit.ipd.java.utils.framework.table.TableHeader;
import de.kit.ipd.java.utils.parsing.csv.CSVLexer.State4;
import de.kit.ipd.java.utils.parsing.csv.CSVLexer.State5;
import de.kit.ipd.java.utils.parsing.csv.CSVLexer.State6;

/**
 *
 * @author unknown
 *
 */
public class CSVParser implements IParser<Table<String>>, ILexerVisitor<CharSequence> {

	/**************************************************************************
	 * FIELDS
	 *************************************************************************/

	private ILexer<CharSequence> lexer = new CSVLexer();

	private int colCounter = 0;

	private int rowCounter = 0;

	private Table<String> table = new Table<String>();

	private final List<TableHeader> header = new ArrayList<TableHeader>();

	private Row<String> currentRow;

	/**************************************************************************
	 * CONSTRUCTOR
	 *************************************************************************/

	public CSVParser() {
		this.lexer.getMachine().setEOLState(4);
		this.lexer.getMachine().addVisitor(this);

		this.currentRow = new Row<>();
		this.currentRow.setIndex(0);

		this.table.setHeader(this.header);

	}

	/**************************************************************************
	 * PUBLIC
	 *************************************************************************/

	public void setLexer(final ILexer<CharSequence> lexer) {
		this.lexer = lexer;
		lexer.getMachine().setEOLState(4);
		lexer.getMachine().addVisitor(this);
	}

	@Override
	public void parse(final String content) {
		this.lexer.getMachine().setInput(content);
		this.lexer.getMachine().run(0);
	}

	public Table<String> getTable() {
		return this.table;
	}

	@Override
	public void parse(final InputStream in) {
		try {
			if ((in != null) && (in.available() != -1)) {
				final BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = null;
				final StringBuilder content = new StringBuilder();
				while ((line = br.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
				this.lexer.getMachine().setInput(content);
				this.lexer.getMachine().run(0);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visit(final IStateMachine<CharSequence> machine, final int state, final CharSequence token) {
		final String val = String.valueOf(token);
		if (!val.isEmpty()) {
			switch (state) {
			case State5.INDEX:
				this.buildHeader(String.valueOf(token));
				break;
			case State6.INDEX:
				this.buildHeader(String.valueOf(token));
				break;
			case State4.INDEX:
				this.buildTable(String.valueOf(token));
				break;
			default:
				break;
			}
		}
	}

	@Override
	public void addVisitor(final IParserVisitor<Table<String>> visitor) {
		// TODO Auto-generated method stub

	}

	public void setTable(final Table<String> table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return this.generateCSVString();
	}

	@Override
	public Table<String> getModel() {
		return this.table;
	}

	@Override
	public void setModel(final Table<String> model) {
		this.table = model;
	}

	/**************************************************************************
	 * PRIVATE
	 *************************************************************************/

	private void buildHeader(final String token) {
		final TableHeader newHeader = new TableHeader();
		newHeader.setName(String.valueOf(token.trim()));
		newHeader.setIndex(this.header.size());
		this.header.add(newHeader);
	}

	private void buildTable(final String token) {
		final Column<String> col = new Column<>();
		col.setName(this.table.getHeader(this.colCounter).getName());
		col.setIndex(this.colCounter);
		col.setValue(String.valueOf(token));

		this.currentRow.getColumns().add(col);
		this.colCounter++;

		if (this.colCounter == this.table.getHeader().size()) {
			this.colCounter = 0;
			this.rowCounter++;
			this.table.getRows().add(this.currentRow);
			this.currentRow = new Row<String>();
			this.currentRow.setIndex(this.rowCounter);
		}
	}

	private String generateCSVString() {
		final StringBuilder builder = new StringBuilder();
		final List<TableHeader> csvHeader = this.table.getHeader();

		// HEADER
		final int len = csvHeader.size();
		for (int i = 0; i < len; i++) {
			builder.append(csvHeader.get(i).getName());
			if ((i + 1) < len) {
				builder.append(";"); // TODO externalize
			}
		}
		builder.append(System.lineSeparator());

		// BODY
		final List<Row<String>> rows = this.table.getRows();
		final int lenRow = rows.size();
		for (int j = 0; j < lenRow; j++) {
			for (int i = 0; i < len; i++) {
				builder.append(rows.get(j).getColumns().get(i).getValue());
				if ((i + 1) < len) {
					builder.append(";"); // TODO externalize
				}
			}
			if ((j + 1) < lenRow) {
				builder.append(System.lineSeparator()); // TODO externalize
			}
		}
		return builder.toString();
	}

}
