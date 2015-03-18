package db61b;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import static db61b.Utils.*;

/** A single table in a database.
 *  @author P. N. Hilfinger
 */
class Table implements Iterable<Row> {
    /** A new Table whose columns are given by COLUMNTITLES, which may
     *  not contain dupliace names. */
    Table(String[] columnTitles) {
        for (int i = columnTitles.length - 1; i >= 1; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (columnTitles[i].equals(columnTitles[j])) {
                    throw error("duplicate column name: %s",
                                columnTitles[i]);
                }
            }
        }
        for (String i: columnTitles) {
            newCol.add(i);
        }
    }
    /** A new Table whose columns are give by COLUMNTITLES. */
    Table(List<String> columnTitles) {
        this(columnTitles.toArray(new String[columnTitles.size()]));
    }

    /** Return the number of columns in this table. */
    public int columns() {
        return newCol.size();
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    public String getTitle(int k) {
        if (0 <= k && k < columns()) {
            return newCol.get(k);
        } else {
            throw new DBException();
        }
    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    public int findColumn(String title) {
        return newCol.indexOf(title);
    }

    /** Return the number of Rows in this table. */
    public int size() {
        return _rows.size();
    }
    /** Returns an iterator that returns my rows in an unspecfied order. */
    @Override
    public Iterator<Row> iterator() {
        return _rows.iterator();
    }

    /** Add ROW to THIS if no equal row already exists.  Return true if anything
     *  was added, false otherwise. */
    public boolean add(Row row) {
        if (_rows.contains(row)) {
            return false;
        } else {
            _rows.add(row);
            return true;
        }
    }
    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        input = null;
        table = null;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();
            if (header == null) {
                throw error("missing header in DB file");
            }
            String[] columnNames = header.split(",");
            table = new Table(columnNames);
            String restRow = input.readLine();
            while (restRow != null) {
                String[] rowElements = restRow.split(",");
                Row adder = new Row(rowElements);
                table.add(adder);
                restRow = input.readLine();
            }
        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    /* Ignore IOException */
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */
    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            String sep;
            sep = "";
            output = new PrintStream(name + ".db");
            for (Row i : _rows) {
                int rep = 0;
                int lngth = i.access().length;
                boolean check = true;
                for (String p : i.access()) {
                    rep += 1;
                    if (check) {
                        output.print("");
                        check = false;
                    }
                    output.print(p);
                    if (rep != lngth) {
                        output.print("");
                    }
                }
                output.println();
                check = false;
            }

        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output. */
    void print() {
        for (Row i : _rows) {
            int rep = 0;
            int lngth = i.access().length;
            boolean check = true;
            for (String p : i.access()) {
                rep += 1;
                if (check) {
                    System.out.print(" ");
                    check = false;
                }
                System.out.print(p);
                if (rep != lngth) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            check = false;
        }
    }
    /** Return a new Table whose columns are COLUMNNAMES, selected from
     *  rows of this table that satisfy CONDITIONS. */
    Table select(List<String> columnNames, List<Condition> conditions) {
        Table result = new Table(columnNames);
        List<Column> col = new ArrayList<Column>();
        for (String p : columnNames) {
            Column myCol2 = new Column(p, this);
            col.add(myCol2);
        }
        for (Row r: _rows) {
            if (conditions == null || Condition.test(conditions, r)) {
                Row newRow = new Row(col, r);
                result.add(newRow);
            }
        }
        return result;
    }
    /** Return a new Table whose columns are COLUMNNAMES, selected
     *  from pairs of rows from this table and from TABLE2 that match
     *  on all columns with identical names and satisfy CONDITIONS. */

    Table select(Table table2, List<String> columnNames,
                 List<Condition> conditions) {
        Table result = new Table(columnNames);
        ArrayList<Column> quol = new ArrayList<Column>();
        ArrayList<String>  yadel = new ArrayList<String>();
        ArrayList<Column> quol2 = new ArrayList<Column>();
        ArrayList<Column>  colNms = new ArrayList<Column>();
        for (int i = 0; i < this.columns(); i++) {
            for (int j = 0; j < table2.columns(); j++) {
                String a = this.getTitle(i);
                if (a.equals(table2.getTitle(j))) {
                    yadel.add(a);
                }
            }
        }

        for (String col: yadel) {
            Column c = new Column(col, this);
            quol.add(c);
        }
        for (String col: yadel) {
            Column c2 = new Column(col, table2);
            quol2.add(c2);
        }
        for (String col: columnNames) {
            Column c = new Column(col, this, table2);
            colNms.add(c);
        }
        for (Row r: this) {
            for (Row t: table2) {
                if (equijoin(quol, quol2, r, t)
                       &&
                    (Condition.test(conditions, r, t))) {

                    Row r0 = new Row(colNms, r, t);
                    result.add(r0);
                }
            }
        }

        return result;
    }

    /** Return true if the columns COMMON1 from ROW1 and COMMON2 from
     *  ROW2 all have identical values.  Assumes that COMMON1 and
     *  COMMON2 have the same number of elements and the same names,
     *  that the columns in COMMON1 apply to this table, those in
     *  COMMON2 to another, and that ROW1 and ROW2 come, respectively,
     *  from those tables. */
    private static boolean equijoin(List<Column> common1, List<Column> common2,
                                    Row row1, Row row2) {
        Row r = new Row(common1, row1);
        Row r2 = new Row(common2, row2);
        return r.equals(r2);
    }
    /** My rows. */
    private HashSet<Row> _rows = new HashSet<>();
    /** this will store my columns. */
    public ArrayList<String>  newCol =  new ArrayList<String>();
}

