package db61b;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class BasicRowTest {
    @Test
    public void testRow() {
        Table students = new Table
        (new String[] {"SID", "Lastname", "Firstname", "Major"});
        Column cl = new Column(students.getTitle(1), students);
	Column cf = new Column(students.getTitle(2), students);
        Row r =  new Row(new String[]
        {"101", "Knowles", "Jason", "EECS"});
	Row f = new Row(new String[]
        {"102", "Chan", "Valerie", "Math"});
        students.add(r);
	students.add(f);
        List<Column> listOfColumns = new ArrayList<Column>();
	listOfColumns.add(cl);
	listOfColumns.add(cf);
        Table s = new Table(new String[] {"SID", "Major"});
	Row nw = new Row(listOfColumns, f);
	s.print();
    }
    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(BasicRowTest.class));
    }
}
