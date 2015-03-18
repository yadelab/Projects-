package db61b;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**Tests if my code works */
public class BasicTests {
    @Test
    public void testRow() {
        Row r = new Row(new String[] {"this", "prints", "if", "pass", "test."});
        Row p = new Row(new String[] {"this", "prints", "if", "pass", "test."});
        assertEquals(r.size(), 5);
        assertEquals(r.get(1), "prints");
        assertEquals(r.equals(p), true);
    }
    @Test (expected = DBException.class)
    public void testget() {
        Row r = new Row(new String[] {"this", "prints", "if", "pass", "test."});
        r.get(-1);
    }
    @Test
    public void testTable() {
        Table a = new Table(new String[] {"this", "is", "Table"});
        Row r = new Row(new String[] {"this", "prints", "if", "pass", "test."});
        Row p = new Row(new String[] {"this", "prints", "if", "pass", "test."});
        Row d = new Row(new String[] {"this", "is", "the", "second", "one"});
        Row m = new Row(new String[] {"which", "means", "this", "is", "third"});
        String b = new String("is");
        assertEquals(a.columns(), 3);
	assertEquals(a.getTitle(0), "this");
	assertEquals(a.findColumn(b), 1);
	assertEquals(a.size(), 0);
	assertEquals(a.add(r), true);
	assertEquals(a.add(p), false);
        a.add(p);
	a.add(d);
	a.add(m);
	a.print();
    }
    @Test
    public void testReadTable() {
        Table v = Table.readTable("schedule");
    }
    @Test
    public void testSelect() {
        Table students = new Table(new String[]
        {"SID", "Lastname", "Firstname", "Major"});
        String cl = new String(students.getTitle(1));
        String cf = new String(students.getTitle(2));
        Row r =  new Row(new String[] {"101", "Knowles", "Jason", "EECS"});
        Row f = new Row(new String[] {"102", "Chan", "Valerie", "Math"});
        students.add(r);
        students.add(f);
        List<String> columnNames = new ArrayList<String>();
        columnNames.add(cl);
        columnNames.add(cf);
        Column col = new Column("Lastname", students);
        Column col2 = new Column("Firstname", students);
        Table s =  students.select(columnNames, null);
        s.print();
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(BasicTests.class));
    }
}
