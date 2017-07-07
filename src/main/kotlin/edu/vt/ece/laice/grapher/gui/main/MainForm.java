package edu.vt.ece.laice.grapher.gui.main;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.extended.tree.WebCheckBoxTree;
import com.alee.laf.scroll.WebScrollPane;
import edu.vt.ece.laice.grapher.data.YBindings;

import javax.swing.*;

/**
 * Created by cameronearle on 6/23/17.
 */
public class MainForm {
    JPanel sizingPanel;
    JPanel leftSide;
    JPanel rightSide;
    JPanel graphingViewport;
    JPanel presetsViewport;
    JPanel controlsViewport;
    JPanel mainPanel;
    JComboBox intervalComboBox;
    JComboBox xAxisSelectComboBox;
    JButton startSelectButton;
    JRadioButton startDateRadioButton;
    JRadioButton startOrbitRadioButton;
    JSpinner startOrbitSpinner;
    WebDateField startOrbitCalendar;
    JList yAxisList;
    JButton addYAxisButton;
    JButton removeYAxisButton;
    JButton upYAxisButton;
    JButton downYAxisButton;

    private void createUIComponents() {
    }
}
