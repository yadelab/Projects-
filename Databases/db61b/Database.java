package db61b;
import java.util.HashMap;


/** A collection of Tables, indexed by name.
 *  @author Yadel */
class Database {
    /** An empty database. */
    public Database() {
        tbl = new HashMap<String, Table>();
    }
    /** Return the Table whose name is NAME stored in this database, or null
     *  if there is no such table. */
    public Table get(String name) {
        if (tbl.containsKey(name) == false) {
            return null;
        } else {
            return tbl.get(name);
        }
    }

    /** Set or replace the table named NAME in THIS to TABLE.  TABLE and
     *  NAME must not be null, and NAME must be a valid name for a table. */
    public void put(String name, Table table) {
        if (name == null || table == null) {
            throw new IllegalArgumentException("null argument");
        }
        if (tbl.containsKey(name) == true) {
            tbl.put(name, table);
        } else {
            tbl.put(name, table);
        }
    }
    /** my hashMap stores the table names.*/
    private HashMap<String, Table> tbl;
}
