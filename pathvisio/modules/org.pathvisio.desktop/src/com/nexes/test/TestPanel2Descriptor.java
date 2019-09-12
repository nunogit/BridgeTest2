package com.nexes.test;

import com.nexes.wizard.*;

import java.awt.*;
import java.awt.event.*;


public class TestPanel2Descriptor extends WizardPanelDescriptor implements ActionListener {

    public static final String IDENTIFIER = "CONNECTOR_CHOOSE_PANEL";

    TestPanel2 panel2;

    public TestPanel2Descriptor() {

    	super(IDENTIFIER);
        panel2 = new TestPanel2();
        panel2.addCheckBoxActionListener(this);

        setPanelComponent(panel2);

    }

    public Object getNextPanelDescriptor() {
        return TestPanel3Descriptor.IDENTIFIER;
    }

    public Object getBackPanelDescriptor() {
        return TestPanel1Descriptor.IDENTIFIER;
    }


    public void aboutToDisplayPanel() {
        setNextButtonAccordingToCheckBox();
    }

    public void actionPerformed(ActionEvent e) {
        setNextButtonAccordingToCheckBox();
    }


    private void setNextButtonAccordingToCheckBox() {
         if (panel2.isCheckBoxSelected())
            getWizard().setNextFinishButtonEnabled(true);
         else
            getWizard().setNextFinishButtonEnabled(false);

    }

	@Override
	protected Component createContents()
	{
		return new TestPanel2();
	}
}
