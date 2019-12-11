package com.sample;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.kie.api.runtime.KieSession;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI {
	
	private Integer width;
	private JFrame frame;
	private JTextPane textPane;
	private ArrayList<JButton> buttons;
	private String chosenAnswer;
	private KieSession kSession;
	
	public GUI(KieSession kSession) {
		this.kSession = kSession;
		
		this.width = 520;
		
		this.frame = new JFrame("What TV show are you watching?");
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setSize(width, 250);
		this.frame.setResizable(false);
		this.frame.setLayout(new FlowLayout());
		
		this.textPane = new JTextPane();
		this.textPane.setPreferredSize(new Dimension(width-30, 70));
		this.textPane.setEditable(false);
		this.textPane.setOpaque(false);
		
		StyledDocument doc = this.textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		Font font = new Font("SansSerif", Font.BOLD, 16);
		this.textPane.setFont(font);
		
		JTextPane emptyPane = new JTextPane();
		emptyPane.setPreferredSize(new Dimension(width, 15));
		emptyPane.setEditable(false);
		emptyPane.setOpaque(false);
		
		this.frame.add(emptyPane, BorderLayout.NORTH);
		this.frame.add(this.textPane, BorderLayout.NORTH);
		
		this.chosenAnswer = "";
		this.buttons = new ArrayList<>();
	}
	
	private void drawButtons(ArrayList<String> answers)
	{
		for(JButton button: this.buttons) {
			this.frame.remove(button);
		}
		
		if(answers!=null) {
			for(String answer: answers)
			{
				JButton button = new JButton(answer);
				button.addActionListener( new ActionListener() 
				{
	    			public void actionPerformed(ActionEvent e) 
	    			{
	    				chosenAnswer = e.getActionCommand();
	    			}
	    		} );
				this.buttons.add(button);
				this.frame.add(button);
			}
		}

		this.frame.revalidate();
		this.frame.repaint();
	}
	
	public String questionHandler(String question, ArrayList<String> answers) throws InterruptedException
	{
		this.textPane.setText(question);
		this.drawButtons(answers);
		this.frame.setVisible(true);
		
		while(this.chosenAnswer == "") {
			Thread.sleep(100);
		}
		
		String answer = this.chosenAnswer;
		this.chosenAnswer = "";
		    		
		return answer;
	}
	
	public void showResult(String result) throws InterruptedException
	{
		this.textPane.setText("\nYou're watching "+result+"!");
		Font font = new Font("Serif", Font.BOLD|Font.ITALIC, 25);
		this.textPane.setFont(font);
		
		this.drawButtons(null);
		this.frame.setVisible(true);
		
		this.kSession.destroy();
	}
	
}