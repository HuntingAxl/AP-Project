import javax.swing.table.DefaultTableModel;
import java.util.Vector;
import java.util.List;

public class Query1_Table extends DefaultTableModel {
    private Object[][] data={};
    private String[] columns = {"<html><h3>S.NO</h3></html>", "<html><h3>AUTHOR</h3></html>", "<html><h3>TITLE</h3></html>", "<html><h3>PAGES</h3></html>", "<html><h3>YEAR</h3></html>", "<html><h3>VOLUME</h3></html>", "<html><h3>JOURNAL/BOOKTITLE</h3></html>", "<html><h3>URL</h3></html>"};
    Query1_Table(Object [][] data/*List<Record> result*/)//String sno,String author/*/List<String> author*/,String title,String pages,String year,String volume,String jbook,String url)
    {
        setColumnIdentifiers(columns);
        this.data = data;
    }
    @Override
    public int getRowCount() {
        return 20;
    }
    @Override
    public int getColumnCount() {
        return 8;
    }
    @Override
    public void setColumnIdentifiers(Vector columnIdentifiers) {
        super.setColumnIdentifiers(columnIdentifiers);
    }


    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }


}
