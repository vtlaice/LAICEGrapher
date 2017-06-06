package edu.vt.ece.laice.grapher.gui;


import edu.vt.ece.laice.grapher.chart.ChartContainer;
import org.jfree.chart.ChartUtilities;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SaveChartDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox formatComboBox;
    private JSpinner widthSpinner;
    private JSpinner heightSpinner;
    private JButton fileChooseButton;
    private JTextField filePathField;
    private String selectedPath = "";

    private void setSelectedPath(String path) {
        selectedPath = path;
        filePathField.setText(path);
    }

    public SaveChartDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        widthSpinner.setValue(800);
        heightSpinner.setValue(600);

        formatComboBox.addItem("jpeg");
        formatComboBox.addItem("png");

        fileChooseButton.addActionListener((ActionEvent e) -> {
            JFrame parentFrame = new JFrame();
            JFileChooser fileChooser = new JFileChooser();
            int selection = fileChooser.showSaveDialog(parentFrame);
            if (selection == JFileChooser.APPROVE_OPTION) {
                if (fileChooser.getSelectedFile().getPath().toLowerCase().contains(".jpeg")) {
                    formatComboBox.setSelectedItem("jpeg");
                    setSelectedPath(fileChooser.getSelectedFile().getPath());
                } else if (fileChooser.getSelectedFile().getPath().toLowerCase().contains(".png")) {
                    formatComboBox.setSelectedItem("png");
                    setSelectedPath(fileChooser.getSelectedFile().getPath());
                } else {
                    setSelectedPath(fileChooser.getSelectedFile().getPath() + "." + formatComboBox.getSelectedItem());
                }
            }
        });
    }

    private void onOK() {
        File file = new File(selectedPath);
        if (formatComboBox.getSelectedItem().equals("jpeg")) {
            try {
                ChartUtilities.saveChartAsJPEG(file, ChartContainer.INSTANCE.getChart(), (int) widthSpinner.getValue(), (int) heightSpinner.getValue());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(new JFrame(), "Error saving to JPEG file " + selectedPath, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            try {
                ChartUtilities.saveChartAsPNG(file, ChartContainer.INSTANCE.getChart(), (int) widthSpinner.getValue(), (int) heightSpinner.getValue());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(new JFrame(), "Error saving to PNG file " + selectedPath, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
