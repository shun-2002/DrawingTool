import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;

public class PrintItem extends JMenuItem {
    private StateManager stateManager;

    public PrintItem(StateManager stateManager) {
        super("Print");

        addActionListener(new PrintListener());

        this.stateManager = stateManager;
    }

    class PrintListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PrinterJob p = PrinterJob.getPrinterJob();
            p.setPrintable(stateManager.getCanvas());
            if (p.printDialog()) {
                try {
                    p.print();
                    System.out.println("a");
                } catch (PrinterException pe) {
                }
            }
        }
    }
}
