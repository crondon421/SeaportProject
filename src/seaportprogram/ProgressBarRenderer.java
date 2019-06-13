import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressBarRenderer extends JProgressBar implements TableCellRenderer {
	
	public ProgressBarRenderer(int min, int max) {
		super(min, max);
	}
	
	public Component getTableCellRendererComponent(
      JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int column) {
      
		setValue(0);
		setStringPainted(true);
	
  
      return this;
   }
}
