package internalFrame;
import java.awt.Component;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;
import model.TbSpinfo;
import com.lzw.dao.Dao;
public class customCellEditor extends JComboBox implements TableCellEditor {
	private CellEditorListener list;
	private String gysName;
	private ChangeEvent ce = new ChangeEvent(this);//ChangeEvent用于通知有关方面事件源中的状态已更改。
	public customCellEditor() {
		super();
	}
	public Object getCellEditorValue() {
		return getSelectedItem();
	}
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		ResultSet set = Dao.query("select * from tb_spinfo where gysName='"
				+gysName+"'");//连接数据库部分，用于查询状态
		DefaultComboBoxModel dfcbm = (DefaultComboBoxModel) getModel();
		dfcbm.removeAllElements();
		dfcbm.addElement(new TbSpinfo());
		try {
			while (set.next()) {
				TbSpinfo spinfo=new TbSpinfo();
				spinfo.setId(set.getString("id").trim());//trim返回值为此字符串的字符串，并删除任何前导和尾随空格。
				spinfo.setSpname(set.getString("spname").trim());//此方法可用于从字符串的开始和结束修剪空格
				spinfo.setCd(set.getString("cd").trim());
				spinfo.setJc(set.getString("jc").trim());
				spinfo.setDw(set.getString("dw").trim());
				spinfo.setGg(set.getString("gg").trim());
				spinfo.setBz(set.getString("bz").trim());
				spinfo.setPh(set.getString("ph").trim());
				spinfo.setPzwh(set.getString("pzwh").trim());
				spinfo.setMemo(set.getString("memo").trim());
				spinfo.setGysname(set.getString("gysname").trim());
				dfcbm.addElement(spinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	public void addCellEditorListener(CellEditorListener arg0) {
		list = arg0;
	}
	public void cancelCellEditing() {
		list.editingCanceled(ce);
	}
	public boolean isCellEditable(EventObject arg0) {
		return true;
	}
	public void removeCellEditorListener(CellEditorListener arg0) {
	}
	public boolean shouldSelectCell(EventObject arg0) {
		return true;
	}
	public boolean stopCellEditing() {
		list.editingStopped(ce);
		return true;
	}
	public String getGysName() {
		return gysName;
	}
	public void setGysName(String gysName) {
		this.gysName = gysName;
	}
}
